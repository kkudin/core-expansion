package com.omvoid.community.corexp;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.concurrent.AsSynchronizedGraph;

import java.util.Set;
import java.util.stream.Collectors;

class EdgeWeightProcessor {
    private AsSynchronizedGraph<Integer, DefaultWeightedEdge> g;

    /**
     * Given the network graph G(V,E), compute the weight of
     * each edge e in E by calling the function Compute_ Edge_Weights (cf. Algorithm 2).
     * This function assigns the neighborhood overlap weight to each edge
     * @param extendedGraph
     */
    public void calculateWeight(ExtendedGraph extendedGraph) {
        g = new AsSynchronizedGraph<>(extendedGraph.getFastutilGraph());

        g.edgeSet().forEach(this::processEdge);
    }

    private void processEdge(DefaultWeightedEdge e) {
        int src = g.getEdgeSource(e);
        int dst = g.getEdgeTarget(e);

        Set<Integer> srcNN = getNeighbourHood(src);
        Set<Integer> dstNN = getNeighbourHood(dst);

        double w = (srcNN.stream().filter(dstNN::contains).count() * 1.0);
        srcNN.addAll(dstNN);
        if (srcNN.size() > 2) {
            w /= (srcNN.size() - 2);
        } else {
            w = 0.0;
        }

        g.setEdgeWeight(e, w);
    }

    private Set<Integer> getNeighbourHood(int v) {
        return g.edgesOf(v).stream().map(e -> {
            if (g.getEdgeSource(e) == v) return g.getEdgeTarget(e);
            else return g.getEdgeSource(e);
        }).collect(Collectors.toSet());
    }
}
