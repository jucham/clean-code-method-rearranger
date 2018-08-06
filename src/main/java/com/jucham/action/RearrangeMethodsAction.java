package com.jucham.action;

import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiBundle;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

/**
 * This action is intended to rearrange methods according to Robert C. Martin's Step-down Rule
 */
public class RearrangeMethodsAction extends AnAction {

    @Override
    public void update(AnActionEvent anActionEvent) {
        PsiFile file = CommonDataKeys.PSI_FILE.getData(anActionEvent.getDataContext());
        boolean enabled = file != null && file.getLanguage().is(Language.findLanguageByID("JAVA"));
        anActionEvent.getPresentation().setEnabled(enabled);
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        final Project project = anActionEvent.getProject();
        if (project == null) {
            return;
        }
        final Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        PsiFile file = CommonDataKeys.PSI_FILE.getData(anActionEvent.getDataContext());
        if (!file.getLanguage().is(Language.findLanguageByID("JAVA"))) {
            return;
        }

        PsiDocumentManager documentManager = PsiDocumentManager.getInstance(project);
        Document document = editor.getDocument();
        documentManager.commitDocument(document);

        PsiJavaFile javaFile = (PsiJavaFile) anActionEvent.getData(LangDataKeys.PSI_FILE);

        if (!FileDocumentManager.getInstance().requestWriting(document, project)) {
            Messages.showMessageDialog(project, PsiBundle.message("cannot.modify.a.read.only.file", javaFile.getName()),
                    CodeInsightBundle.message("error.dialog.readonly.file.title"),
                    Messages.getErrorIcon()
            );
            return;
        }

        new RearrangeMethodsProcessor(javaFile, document, documentManager).run();
    }

}
