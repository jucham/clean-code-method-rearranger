package com.jucham.caret;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.psi.impl.source.PsiImportStatementImpl;
import com.intellij.psi.impl.source.PsiMethodImpl;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CaretPositionBackup {

    private int targetOffset = 0;
    private PsiElement targetElement;
    private PsiClass parentClassOfTargetElement;
    private int offsetInsideMethod;

    public void saveCaretLogicalPosition(PsiJavaFile javaFile, Project project, Editor editor) {

        PsiClass[] classes = javaFile.getClasses();

        // no class at all
        if (classes.length < 1) {
            return;
        }

        FileViewProvider viewProvider = PsiManager.getInstance(project).findViewProvider(javaFile.getVirtualFile());
        final int caretOffset = editor.getCaretModel().getOffset();
        this.targetOffset = caretOffset;
        PsiElement elementAtCaretOffset = viewProvider.findElementAt(caretOffset);

        if (elementAtCaretOffset == null) {
            return;
        }

        PsiClass topClass = classes[0];
        int textOffset = elementAtCaretOffset.getTextOffset();
        if (textOffset < topClass.getTextRange().getStartOffset() || textOffset > topClass.getTextRange().getEndOffset()) {
            targetElement = elementAtCaretOffset;
        }

        // from here we treat only code inside the main class

        targetElement = PsiTreeUtil.getParentOfType(elementAtCaretOffset, PsiFieldImpl.class, PsiMethodImpl.class, PsiClassImpl.class, PsiPackageStatement.class, PsiImportStatement.class);
        if (targetElement instanceof PsiFieldImpl) {
            parentClassOfTargetElement = ((PsiFieldImpl) targetElement).getContainingClass();
        } else if (targetElement instanceof PsiMethodImpl) {
            parentClassOfTargetElement = ((PsiMethodImpl) targetElement).getContainingClass();
            if (caretIsInsideMethodBody(caretOffset, (PsiMethod) targetElement)) {
                offsetInsideMethod = caretOffset - targetElement.getTextRange().getStartOffset();
            }
        } else if (targetElement instanceof PsiClassImpl) {
            if (caretIsInsideClassBody(textOffset, (PsiClass) targetElement)) {
                // find the nearest element under caret, or above if the caret is at the end of class body
                PsiElement neighbor = findNeighborElementOfType(elementAtCaretOffset, PsiField.class, PsiMethodImpl.class, PsiClassImpl.class);
                targetElement = (neighbor != null) ? neighbor : targetElement;
            }
            parentClassOfTargetElement = PsiTreeUtil.getParentOfType(targetElement, PsiClassImpl.class);
        }
    }

    private boolean caretIsInsideMethodBody(int caretOffset, PsiMethod method) {
        return caretOffset > method.getBody().getTextOffset();
    }

    private boolean caretIsInsideClassBody(int textOffset, PsiClass psiClass) {
        return textOffset > psiClass.getLBrace().getTextOffset();
    }

    private PsiElement findNeighborElementOfType(final PsiElement startElement, Class<? extends PsiElement>... classes) {
        PsiElement element = startElement.getNextSibling();
        if (isElementOfType(element, classes)) {
            return element;
        }
        element = startElement.getPrevSibling();
        if (isElementOfType(element, classes)) {
            return element;
        }
        return null;
    }

    private boolean isElementOfType(PsiElement element, Class<? extends PsiElement>... classes) {
        for (Class<? extends PsiElement> aClass : classes) {
            if (aClass.isInstance(element))
                return true;
        }
        return false;
    }

    public int restoreCaretOffset(PsiJavaFile javaFile) {
        if (targetElement == null) {
            return targetOffset;
        }

        if (targetElement instanceof PsiImportStatementImpl
                || targetElement instanceof PsiClassImpl
                || targetElement instanceof PsiPackageStatement) {
            return targetElement.getTextRange().getStartOffset();
        }

        parentClassOfTargetElement = findPsiClassByName(javaFile, parentClassOfTargetElement.getName());

        if (targetElement instanceof PsiFieldImpl) {
            for (PsiField field : parentClassOfTargetElement.getFields()) {
                if (field.getName().equals(((PsiFieldImpl) targetElement).getName())) {
                    return field.getTextRange().getStartOffset();
                }
            }
        } else if (targetElement instanceof PsiMethodImpl) {
            for (PsiMethod method : parentClassOfTargetElement.getMethods()) {
                if (method.getName().equals(((PsiMethodImpl) targetElement).getName())) {
                    return method.getTextRange().getStartOffset() + offsetInsideMethod;
                }
            }
        }
        return 0;
    }

    @Nullable
    private static <T extends PsiElement> T findPsiClassByName(@Nullable PsiElement element, String className) {
        if (element == null) return null;

        PsiElementProcessor.FindElement<PsiElement> processor = new PsiElementProcessor.FindElement<PsiElement>() {
            @Override
            public boolean execute(@NotNull PsiElement each) {
                if (PsiTreeUtil.instanceOf(each, PsiClassImpl.class) && ((PsiClassImpl) each).getName().equals(className)) {
                    return setFound(each);
                }
                return true;
            }
        };

        PsiTreeUtil.processElements(element, processor);
        @SuppressWarnings("unchecked") T t = (T) processor.getFoundElement();
        return t;
    }

}
