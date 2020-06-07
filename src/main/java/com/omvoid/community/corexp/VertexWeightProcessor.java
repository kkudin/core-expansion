package com.omvoid.community.corexp;

import org.eclipse.collections.impl.map.mutable.primitive.IntDoubleHashMap;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class VertexWeightProcessor {

    private int threadCount = Runtime.getRuntime().availableProcessors();

    public VertexWeightProcessor(int threadCount) {
        this.threadCount = threadCount;
    }

    public VertexWeightProcessor() {
    }

    /**
     * Given the weighted graph obtained from step 1, compute the weight
     * of each node n in V by calling the function Compute_Node_Weights (cf. Algorithm 3).
     * The weight of a node n is the sum of the weights of the out-links of n.
     * @param extendedGraph
     */
    public <V,E> void calculateWeight(ExtendedGraph<V,E> extendedGraph) throws InterruptedException {

        final FastutilMapIntVertexGraph<DefaultWeightedEdge> graph = extendedGraph.getFastutilGraph();
        final var vertexMap = extendedGraph.getVertexWeights();

        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        graph.vertexSet().forEach(v -> pool.submit(new calculateWeightTask(v, graph, vertexMap)));

        pool.shutdown();
        pool.awaitTermination(15L, TimeUnit.MINUTES);
    }

    static class calculateWeightTask implements Runnable {
        private final int v;
        private final FastutilMapIntVertexGraph<DefaultWeightedEdge> graph;
        private final IntDoubleHashMap vertexMap;

        public calculateWeightTask(
                int v,
                FastutilMapIntVertexGraph<DefaultWeightedEdge> graph,
                IntDoubleHashMap vertexMap) {
            this.v = v;
            this.graph = graph;
            this.vertexMap = vertexMap;
        }

        @Override
        public void run() {
            double w = .0;
            for (DefaultWeightedEdge e : graph.edgesOf(v)) {
                w += graph.getEdgeWeight(e);
            }

            vertexMap.put(v, w);
        }
    }

    public int getThreadCount() {
        return threadCount;
    }
}
