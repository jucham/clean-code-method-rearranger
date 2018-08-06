package com.jucham.graph;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.ElementType;
import com.intellij.psi.tree.IElementType;

import java.util.LinkedHashSet;


/**
 * This visitor lets to extract the methods called from a method
 */
class MethodVisitor extends PsiRecursiveElementWalkingVisitor {

    private final LinkedHashSet<PsiMethod> calledMethods;
    private final PsiClass visitedClass;

    MethodVisitor(PsiClass visitedClass) {
        this.visitedClass = visitedClass;
        calledMethods = new LinkedHashSet<>();
    }

    /**
     * Visits a method element, search method calls and store the corresponding methods.
     *
     * @param element the method element visited
     */
    @Override
    public void visitElement(PsiElement element) {
       IElementType elementType = element.getNode().getElementType();
        if (elementType == ElementType.REFERENCE_EXPRESSION && element.getParent() instanceof PsiMethodCallExpression) {
            PsiReference[] refs = element.getReferences();
            PsiElement elem = null;
            for (PsiReference ref : refs) {
                elem = ref.resolve();
            }
            PsiMethod method = (PsiMethod) elem;
            // takes account only method call of method contained in the visited class
            if(elem != null && method.getContainingClass().equals(visitedClass)) {
                calledMethods.add(method);
            }
        }
        super.visitElement(element);
    }

    public LinkedHashSet<PsiMethod> harvestCalledMethods() {
        LinkedHashSet<PsiMethod> calledMethods = new LinkedHashSet<>(this.calledMethods);
        this.calledMethods.clear();
        return calledMethods;
    }
}
