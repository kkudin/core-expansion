package com.omvoid.community.corexp;

import org.eclipse.collections.api.tuple.primitive.IntDoublePair;
import org.eclipse.collections.impl.map.mutable.primitive.IntDoubleHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ClosesVertexFinder {

    /**
     * A node is called unclassified if it is not part of any core.
     * For each unclassified node, try finding its closest core by
     * calling the function Find_Closest_Core (cf. Algorithm 4).
     * When every unclassified node is processed, perform the addition
     * operations, i.e., add the nodes to the corresponding cores.
     * A node n has C as closest core if this maximizes the sum of
     * weights of out-links from n to the nodes in C. Note that sometimes
     * we cannot find such a core, this happens if the sum of weights
     * out of n is zero, or in case two or more cores result in the same
     * value of sum. In such cases n is left unclassified until the next iteration.
     * @return
     * @param cores
     * @param extendedGraph
     * @param unclassifiedVertexes
     */
    <V, E> IntIntHashMap findAll(
            IntIntHashMap cores, ExtendedGraph<V, E> extendedGraph,
            IntHashSet unclassifiedVertexes
    ) throws InterruptedException {
        FastutilMapIntVertexGraph<DefaultWeightedEdge> g = extendedGraph.getFastutilGraph();
        IntIntHashMap results = new IntIntHashMap(unclassifiedVertexes.size());

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 12);

        unclassifiedVertexes.forEach(
                v -> pool.submit(new findCoreTask(cores, v, g, results))
        );

        pool.shutdown();
        pool.awaitTermination(15L, TimeUnit.MINUTES);

        return results;
    }

    static class findCoreTask implements Runnable {
        private final FastutilMapIntVertexGraph<DefaultWeightedEdge> g;
        private final IntIntHashMap cores;
        private final int v;
        private final IntIntHashMap results;

        findCoreTask(
                IntIntHashMap cores, Integer v,
                FastutilMapIntVertexGraph<DefaultWeightedEdge> g,
                IntIntHashMap results
        ) {
            this.g = g;
            this.v = v;
            this.cores = cores;
            this.results = results;
        }

        @Override
        public void run() {
            IntDoubleHashMap candidates = new IntDoubleHashMap();

            g.edgesOf(v).forEach(e -> {
                int n;
                if (g.getEdgeSource(e).equals(v)) {
                    n = g.getEdgeTarget(e);
                } else {
                    n = g.getEdgeSource(e);
                }

                int neighbourComm = cores.get(n);

                if (neighbourComm != -1) {
                    candidates.put(
                            neighbourComm,
                            candidates.getIfAbsent(neighbourComm, 0.0) + g.getEdgeWeight(e)
                    );
                }
            });

            int closestCore = -1;
            double maxWeight = Double.NEGATIVE_INFINITY;
            int cnt = 1;

            for (IntDoublePair candidate : candidates.keyValuesView()) {
                if (candidate.getTwo() > maxWeight) {
                    maxWeight = candidate.getTwo();
                    closestCore = candidate.getOne();
                    cnt = 1;
                } else if (Double.compare(candidate.getTwo(), maxWeight) == 0) {
                    cnt++;
                }
            }

            if (cnt == 1) {
                results.put(v, closestCore);
            } else {
                results.put(v, -1); // Vertex is left unclassified until the next iteration.
            }
        }
    }
}
