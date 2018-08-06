package com.jucham.action;

import com.jucham.graph.MethodCallGraph;
import com.jucham.graph.MethodNode;
import com.jucham.rearrange.MethodRearranger;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RearrangeMethodsProcessor {

    private final PsiJavaFile javaFile;
    private final Document document;
    private final PsiDocumentManager documentManager;

    RearrangeMethodsProcessor(PsiJavaFile javaFile, Document document, PsiDocumentManager documentManager) {
        this.javaFile = javaFile;
        this.document = document;
        this.documentManager = documentManager;
    }

    public void run() {
        PsiClass[] classes = javaFile.getClasses();
        if (classes.length == 0)
            return;
        WriteCommandAction.runWriteCommandAction(javaFile.getProject(), () -> rearrangeFileSourceCode(document, classes));
    }

    private void rearrangeFileSourceCode(Document document, PsiClass[] classes) {
        for (PsiClass psiClass : classes) {
            rearrangeClassSourceCode(document, psiClass, documentManager);
        }
        removeUselessLineFeeds(document);
        documentManager.commitDocument(document);
    }

    private void rearrangeClassSourceCode(Document document, PsiClass psiClass, PsiDocumentManager documentManager) {
        if (psiClass.getMethods().length > 1) {
            MethodCallGraph methodCallGraph = new MethodCallGraph(psiClass);
            List<MethodNode> rearrangedMethodNodes = MethodRearranger.getRearrangedMethods(methodCallGraph);
            String indent = computeIndent(document, psiClass);
            deleteMethodsInDocument(document, psiClass, documentManager);
            insertRearrangedMethodsInDocument(document, psiClass, documentManager, rearrangedMethodNodes, indent);
            for (PsiClass innerClass : psiClass.getInnerClasses()) {
                rearrangeClassSourceCode(document, innerClass, documentManager);
            }
        }
    }

    private String computeIndent(Document document, PsiClass psiClass) {
        int classOffset = psiClass.getTextRange().getStartOffset();
        int beforeFirstMethodOffset = psiClass.getMethods()[0].getTextRange().getStartOffset();
        String s = document.getText(new TextRange(classOffset, beforeFirstMethodOffset));
        int indentOffset = getIndentOffset(s);
        int indentStart = s.length() - indentOffset;
        int indentEnd = s.length();
        return s.substring(indentStart, indentEnd);
    }

    private int getIndentOffset(String sb) {
        int indentOffset = 0;
        for (int i = sb.length() - 1; i >= 0; i--) {
            if (!Character.isSpaceChar(sb.charAt(i)))
                break;
            indentOffset++;
        }
        return indentOffset;
    }

    private void deleteMethodsInDocument(Document document, PsiClass psiClass, PsiDocumentManager documentManager) {
        PsiMethod[] methods = psiClass.getMethods();
        for (int i = methods.length - 1; i >= 0; i--) {
            document.deleteString(methods[i].getTextRange().getStartOffset(), methods[i].getTextRange().getEndOffset());
        }
        documentManager.commitDocument(document);
    }

    private void insertRearrangedMethodsInDocument(Document document, PsiClass psiClass, PsiDocumentManager documentManager, List<MethodNode> rearrangedMethodNodes, String indent) {
        // find position of first method
        int offset;
        PsiField[] fields = psiClass.getFields();
        if (fields.length > 0) {
            offset = fields[fields.length - 1].getTextRange().getEndOffset();
        } else {
            offset = psiClass.getTextRange().getStartOffset() + psiClass.getText().indexOf("{") + 1;
        }
        offset = insertTwoLineFeeds(document, offset);
        // inserts code of rearranged methods
        for (MethodNode mn : rearrangedMethodNodes) {
            String textInserted = indent + mn.getMethodText();
            document.insertString(offset, textInserted);
            offset += textInserted.length();
            offset = insertTwoLineFeeds(document, offset);
        }
        documentManager.commitDocument(document);
    }

    private int insertTwoLineFeeds(Document document, final int offset) {
        document.insertString(offset, "\n\n");
        return offset + 2;
    }

    private void removeUselessLineFeeds(Document document) {
        List<Pair<Integer, Integer>> lineFeedsToRemove = new ArrayList<>();
        String patternStr = "([ \\t\\x0B\\f\\r]*\\n){3,}";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(document.getText());
        while (matcher.find()) {
            lineFeedsToRemove.add(new Pair<>(matcher.start() + 2, matcher.end()));
        }
        Collections.reverse(lineFeedsToRemove);
        for (Pair<Integer, Integer> pair : lineFeedsToRemove) {
            document.deleteString(pair.first, pair.second);
        }
        documentManager.commitDocument(document);
    }

}
