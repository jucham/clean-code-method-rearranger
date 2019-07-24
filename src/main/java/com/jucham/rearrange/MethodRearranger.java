package com.jucham.rearrange;

import com.jucham.graph.MethodCallGraph;
import com.jucham.graph.MethodNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodRearranger {

    private MethodCallGraph methodCallGraph;
    private Map<MethodNode, Integer> remainingTriesBeforeInserting;

    public MethodRearranger(MethodCallGraph methodCallGraph) {
        this.methodCallGraph = methodCallGraph;
        initRemainingTriesBeforeInserting(methodCallGraph);
    }

    private void initRemainingTriesBeforeInserting(MethodCallGraph methodCallGraph) {
        remainingTriesBeforeInserting = methodCallGraph.getNumberOfCallsByMethods();
        remainingTriesBeforeInserting.forEach((k,v) -> remainingTriesBeforeInserting.put(k, v-1)) ;
    }

    /**
     * Computes a rearranged list of methods according to "The Step-down Rule"
     *
     * @return a rearranged list of method nodes
     */
    public List<MethodNode> getRearrangedMethods() {
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
    private void insert(MethodNode methodNodeToInsert, List<MethodNode> methodNodes) {
        // a common method called in different other methods should be inserted after the last method using it
        Integer remainingTries = getRemainingTriesBeforeInserting(methodNodeToInsert);
        if (remainingTries > 0) {
            decrementRemainingTriesBeforeInserting(methodNodeToInsert);
            return;
        }

        if (methodNodes.contains(methodNodeToInsert))
            return;

        methodNodes.add(methodNodeToInsert);
        // then inserts its called methods
        for (MethodNode calledMethodNode : methodNodeToInsert.getCalledMethodNodes())
            insert(calledMethodNode, methodNodes);
    }

    private Integer getRemainingTriesBeforeInserting(MethodNode methodNode) {
        Integer remainingTries = remainingTriesBeforeInserting.get(methodNode);
        return remainingTries == null ? 0 : remainingTries;
    }

    private void decrementRemainingTriesBeforeInserting(MethodNode methodNode) {
        remainingTriesBeforeInserting.put(methodNode, remainingTriesBeforeInserting.get(methodNode)-1);
    }
}
