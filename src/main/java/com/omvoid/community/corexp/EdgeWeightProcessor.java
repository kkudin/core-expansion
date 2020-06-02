package com.omvoid.community.corexp;

import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
        Map<Integer, IntHashSet> cache = new ConcurrentHashMap<>(graph.vertexSet().size());
        int bigNNThreshold = (int) (graph.vertexSet().size() * 0.15);

        graph.edgeSet().forEach(e -> pool.submit(new processEdgeTask(graph, e, cache, bigNNThreshold)));

        pool.shutdown();
        pool.awaitTermination(15L, TimeUnit.MINUTES);
    }

    static class processEdgeTask implements Runnable {
        private final FastutilMapIntVertexGraph<DefaultWeightedEdge> graph;
        private final DefaultWeightedEdge e;
        private final Map<Integer, IntHashSet> cache;
        private final int bigNNThreshold;

        public processEdgeTask(
                FastutilMapIntVertexGraph<DefaultWeightedEdge> graph,
                DefaultWeightedEdge e,
                Map<Integer, IntHashSet> cache,
                int bigNNThreshold
        ) {
            this.graph = graph;
            this.e = e;
            this.cache = cache;
            this.bigNNThreshold = bigNNThreshold;
        }

        @Override
        public void run() {
            int src = graph.getEdgeSource(e);
            int dst = graph.getEdgeTarget(e);

            IntHashSet srcNN, dstNN;

            if (cache.containsKey(src)) {
                srcNN = cache.get(src);
            } else {
                srcNN = NeighbourhoodFinder.find(graph, src);
                if (srcNN.size() >= bigNNThreshold) {
                    cache.put(src, srcNN);
                }
            }

            if (cache.containsKey(dst)) {
                dstNN = cache.get(dst);
            } else {
                dstNN = NeighbourhoodFinder.find(graph, dst);
                if (dstNN.size() >= bigNNThreshold) {
                    cache.put(dst, dstNN);
                }
            }

            double w = (srcNN.count(dstNN::contains) * 1.0);

            IntHashSet union;
            if (srcNN.size() > dstNN.size()) {
                union = new IntHashSet(srcNN);
                union.addAll(dstNN);
            } else {
                union = new IntHashSet(dstNN);
                union.addAll(srcNN);
            }

            if (union.size() > 2) {
                w /= (union.size() - 2);
            } else {
                w = 0.0;
            }

            graph.setEdgeWeight(e, w);
        }
    }
}
