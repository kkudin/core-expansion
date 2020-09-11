package com.omvoid.community.corexp;

import com.omvoid.community.similarities.AvailableSimilarities;
import com.omvoid.community.similarities.CosineSimilarity;
import com.omvoid.community.similarities.JaccardSimilarity;
import com.omvoid.community.similarities.SimilarityMetric;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class EdgeWeightProcessor {
    private int threadCount;
    private SimilarityMetric algorithm;

    public EdgeWeightProcessor(int threadCount, AvailableSimilarities algorithm) {
        this.threadCount = threadCount;
        switch (algorithm) {
            case COSINE_SIMILARITY:
                this.algorithm = new CosineSimilarity();
                break;
            case JACCARD_SIMILARITY:
                this.algorithm = new JaccardSimilarity();
                break;
        }
    }

    /**
     * Given the network graph G(V,E), compute the weight of
     * each edge e in E by calling the function Compute_ Edge_Weights (cf. Algorithm 2).
     * This function assigns the neighborhood overlap weight to each edge
     * @param extendedGraph
     */
    public <V,E> void calculateWeight(ExtendedGraph<V,E> extendedGraph) throws InterruptedException {
        final var graph = extendedGraph.getFastutilGraph();

        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        var weights = new HashMap<DefaultWeightedEdge, Double>(graph.edgeSet().size());
        graph.edgeSet().forEach(e -> weights.put(e, .0));
        graph.edgeSet().forEach(e -> pool.submit(new processEdgeTask(graph, e, algorithm, weights)));

        pool.shutdown();
        pool.awaitTermination(25L, TimeUnit.MINUTES);

        weights.entrySet().forEach(
                entry -> graph.setEdgeWeight(entry.getKey(), entry.getValue())
        );
    }

    static class processEdgeTask implements Runnable {
        private final FastutilMapIntVertexGraph<DefaultWeightedEdge> graph;
        private final DefaultWeightedEdge e;
        private final SimilarityMetric algo;
        private final HashMap<DefaultWeightedEdge, Double> weights;

        public processEdgeTask(
                FastutilMapIntVertexGraph<DefaultWeightedEdge> graph,
                DefaultWeightedEdge e,
                SimilarityMetric algo,
                HashMap<DefaultWeightedEdge, Double> weights
        ) {
            this.graph = graph;
            this.e = e;
            this.algo = algo;
            this.weights = weights;
        }

        @Override
        public void run() {
            int src = graph.getEdgeSource(e);
            int dst = graph.getEdgeTarget(e);

            weights.put(e, algo.similarity(graph, src, dst));
        }
    }
}
