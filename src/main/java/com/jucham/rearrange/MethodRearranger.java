package com.jucham.rearrange;

import com.jucham.graph.MethodCallGraph;
import com.jucham.graph.MethodNode;

import java.util.ArrayList;
import java.util.List;

public class MethodRearranger {

    /**
     * Computes a rearranged list of methods according to "The Step-down Rule"
     *
     * @param methodCallGraph method call graph of a class
     * @return a rearranged list of method nodes
     */
    public static List<MethodNode> getRearrangedMethods(MethodCallGraph methodCallGraph) {
        List<MethodNode> rearrangedMethodNodes = new ArrayList<>();
        for (MethodNode methodNode : methodCallGraph.getRootMethodNodes())
            insert(methodNode, rearrangedMethodNodes);
        return rearrangedMethodNodes;
    }

    /**
     * Inserts a method node into a list of already rearranged method nodes, then recursively inserts the method nodes
     * corresponding to called methods.
     *
     * @param methodNodeToInsert method node to insert in the rearranged method node list
     * @param methodNodes        current list of rearranged method nodes
     */
    private static void insert(MethodNode methodNodeToInsert, List<MethodNode> methodNodes) {
        if (methodNodes.contains(methodNodeToInsert)) {
            return;
        }
        methodNodes.add(methodNodeToInsert);
        // then inserts its called methods
        for (MethodNode calledMethodNode : methodNodeToInsert.getCalledMethodNodes())
            insert(calledMethodNode, methodNodes);
    }
}
