package com.jucham.graph;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.util.MethodSignature;

import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class MethodCallGraph {

    private Map<MethodNode, Integer> numberOfCallsByMethod = new HashMap<>();
    private List<MethodNode> methodNodes;

    public MethodCallGraph(PsiClass psiClass) {
        buildGraph(psiClass);
    }

    /**
     * Builds method call graph from a given class. Each method of the class are visited to
     * @param psiClass the class from which the graph is built
     */
    private void buildGraph(PsiClass psiClass) {
        MethodVisitor methodVisitor = new MethodVisitor(psiClass);
        PsiMethod[] methods = psiClass.getMethods();
        Map<MethodSignature, MethodNode> methodNodeById = new LinkedHashMap<>();
        for (PsiMethod method : methods) {
            MethodNode methodNode = getOrCreateMethodNode(methodNodeById, method);
            methodVisitor.visitElement(method);
            LinkedHashSet<PsiMethod> calledMethods = methodVisitor.harvestCalledMethods();
            for (PsiMethod calledMethod : calledMethods) {
                // do not add method if it's a recursive call
                if(calledMethod.equals(method))
                    continue;
                MethodNode calledMethodNode = getOrCreateMethodNode(methodNodeById, calledMethod);
                updateNumberOfCallsOfMethod(calledMethodNode);
                methodNode.addCalledMethod(calledMethodNode);
            }
        }
        methodNodes = new ArrayList<>(methodNodeById.values());
    }

    private static MethodNode getOrCreateMethodNode(Map<MethodSignature, MethodNode> methodNodeById, PsiMethod method) {
        return methodNodeById.computeIfAbsent(method.getSignature(PsiSubstitutor.EMPTY), k -> new MethodNode(method));
    }

    private void updateNumberOfCallsOfMethod(MethodNode calledMethodNode) {
        numberOfCallsByMethod.merge(calledMethodNode, 1, Integer::sum);
    }

    public List<MethodNode> getRootMethodNodes() {
        return methodNodes.stream()
                .filter(MethodNode::isRoot)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    public Map<MethodNode, Integer> getNumberOfCallsByMethods() {
        return numberOfCallsByMethod;
    }
}
