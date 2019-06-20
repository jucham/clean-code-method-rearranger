package com.jucham.graph;

import com.intellij.psi.PsiMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a graph method node
 */
public class MethodNode {

    private final PsiMethod method;
    private final List<MethodNode> calledMethodNodes;
    private boolean isRoot; // the method node is root when no one calls it in the scope of class

    MethodNode(PsiMethod method) {
        this.method = method;
        this.calledMethodNodes = new ArrayList<>();
        this.isRoot = true;
    }

    public String getMethodText() {
        return method.getText();
    }

    void addCalledMethod(MethodNode methodNode) {
        methodNode.isRoot = false;
        calledMethodNodes.add(methodNode);
    }

    boolean isRoot() {
        return isRoot;
    }

    public List<MethodNode> getCalledMethodNodes() {
        return calledMethodNodes;
    }

    public String toString() {
        String s = method.getName() + " Root : " + isRoot + ", Callees : [";
        for (int i = 0; i < calledMethodNodes.size(); i++) {
            MethodNode mn = calledMethodNodes.get(i);
            s += ((i>0)?", ":"") + mn.method.getName();
        }
        s += "]";
        return s;
    }
}
