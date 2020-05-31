package com.omvoid.community.corexp;


import org.eclipse.collections.impl.map.mutable.primitive.IntDoubleHashMap;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.*;
import java.util.stream.Collectors;

class CoresFinder {
    private FastutilMapIntVertexGraph<DefaultWeightedEdge> g;
    private IntDoubleHashMap vertexWeights;
    private Map<Integer, List<Integer>> cores;

    /**
     * Given the node weights, find the initial community cores,
     * where each core is a local maximum node. A node is local
     * maximum if its weight is higher than all of its neighbors.
     * Note that it is possible to obtain several connected nodes
     * having the same local maximum weight, in such case, these
     * connected nodes constitute one core.
     *
     * @param graph
     */
    public Map<Integer, List<Integer>> find(ExtendedGraph graph) {
        g = graph.getFastutilGraph();
        vertexWeights = graph.getVertexWeights();

        cores = new HashMap<>();
        for (int v : g.vertexSet()) {
            checkCore(v);
        }

        return cores;
    }

    private void checkCore(int v) {
        double vW = vertexWeights.get(v);
        Set<Integer> nn = getNeighbourHood(v);
        if (nn.size() == 0) {return;}

        Set<Integer> equalsWeightsNodes = new HashSet<>();

        for (int n : nn) {
            if (vertexWeights.get(n) > vW) {
                return;
            }
            if (Double.compare(vertexWeights.get(n), vW) == 0) {
                equalsWeightsNodes.add(n);
            }
        }

        ArrayList<Integer> coreVertices = new ArrayList<>(List.of(v));
        for (int coreCandidate : coreVertices) {
            if (cores.containsKey(coreCandidate)) {
                return;
            }
            else {
                coreVertices.add(coreCandidate);
            }
        }

        cores.put(v, coreVertices);
    }

    private Set<Integer> getNeighbourHood(int v) {
        return g.edgesOf(v).stream().map(e -> {
            if (g.getEdgeSource(e) == v) {
                return g.getEdgeTarget(e);
            }
            else {
                return g.getEdgeSource(e);
            }
        }).collect(Collectors.toSet());
    }
}
