package com.omvoid.community.corexp;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class EdgeWeightProcessor {

    /**
     * Given the network graph G(V,E), compute the weight of
     * each edge e in E by calling the function Compute_ Edge_Weights (cf. Algorithm 2).
     * This function assigns the neighborhood overlap weight to each edge
     * @param extendedGraph
     */
    public <V,E> void calculateWeight(ExtendedGraph<V,E> extendedGraph) throws InterruptedException {
        final var graph = extendedGraph.getFastutilGraph();

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 12);

        graph.edgeSet().forEach(e -> pool.submit(new processEdgeTask(graph, e)));

        pool.shutdown();
        pool.awaitTermination(15L, TimeUnit.MINUTES);
    }

    static class processEdgeTask implements Runnable {
        private final FastutilMapIntVertexGraph<DefaultWeightedEdge> graph;
        private final DefaultWeightedEdge e;

        public processEdgeTask(FastutilMapIntVertexGraph<DefaultWeightedEdge> graph, DefaultWeightedEdge e) {
            this.graph = graph;
            this.e = e;
        }

        @Override
        public void run() {
            int src = graph.getEdgeSource(e);
            int dst = graph.getEdgeTarget(e);

            Set<Integer> srcNN = NeighbourhoodFinder.find(graph, src);
            Set<Integer> dstNN = NeighbourhoodFinder.find(graph, dst);

            double w = (srcNN.stream().filter(dstNN::contains).count() * 1.0);
            srcNN.addAll(dstNN);
            if (srcNN.size() > 2) {
                w /= (srcNN.size() - 2);
            } else {
                w = 0.0;
            }

            graph.setEdgeWeight(e, w);
        }
    }
}
