package com.omvoid.community.corexp;


import org.eclipse.collections.impl.map.mutable.primitive.IntDoubleHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.*;
import java.util.stream.Collectors;

class CoresFinder {
    private FastutilMapIntVertexGraph<DefaultWeightedEdge> g;
    private IntDoubleHashMap vertexWeights;
    private IntObjectHashMap<IntHashSet> cores;
    private IntHashSet visited;

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
    public IntObjectHashMap<IntHashSet> find(ExtendedGraph graph) {
        g = graph.getFastutilGraph();

        vertexWeights = graph.getVertexWeights();
        visited = new IntHashSet(g.vertexSet().size());
        cores = new IntObjectHashMap<>();
        for (int v : g.vertexSet()) {
            checkCore(v);
        }

        return cores;
    }

    private void checkCore(int v) {
        double vW = vertexWeights.get(v);
        Set<Integer> nn = getNeighbourHood(v);
        if (nn.size() == 0) {
            visited.add(v);
            return;
        }

        IntHashSet equalsWeightsNodes = new IntHashSet();

        for (int n : nn) {
            if (vertexWeights.get(n) > vW) {
                visited.add(v);
                return;
            }
            if (Double.compare(vertexWeights.get(n), vW) == 0) {
                equalsWeightsNodes.add(n);
            }
        }

        IntHashSet coreVertices = new IntHashSet();
        coreVertices.add(v);

        equalsWeightsNodes.forEach(
                candidate -> {
                    if (visited.contains(candidate)) {
                        visited.add(v);
                        return;
                    }

                    coreVertices.add(candidate);
                }
        );

        cores.put(v, coreVertices);
        visited.add(v);
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
