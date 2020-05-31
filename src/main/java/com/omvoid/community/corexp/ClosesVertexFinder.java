package com.omvoid.community.corexp;

import org.eclipse.collections.api.tuple.primitive.IntDoublePair;
import org.eclipse.collections.impl.map.mutable.primitive.IntDoubleHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.Set;

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
     * @param v
     * @param extendedGraph
     */
    int find(IntObjectHashMap<IntHashSet> cores, Integer v, ExtendedGraph extendedGraph) {
        FastutilMapIntVertexGraph g = extendedGraph.getFastutilGraph();

        Set<Integer> nn = NeighbourhoodFinder.find(g, v);
        IntDoubleHashMap candidates = new IntDoubleHashMap(nn.size());

        nn.forEach(
                n -> {
                    cores.forEachKeyValue(
                            (coreId, core) -> {
                                if (core.contains(n)) {
                                    candidates.put(
                                            coreId,
                                            candidates.getIfAbsent(coreId, .0) + g.getEdgeWeight(
                                                    g.getEdge(v, n)
                                            )
                                    );
                                }
                            }
                    );
                }
        );

        int closestCore = -1;
        double maxWeight = Double.NEGATIVE_INFINITY;
        int cnt = 1;

        for (IntDoublePair candidate : candidates.keyValuesView()) {
            if (candidate.getTwo() > maxWeight) {
                maxWeight = candidate.getTwo();
                closestCore = candidate.getOne();
                cnt = 1;
            }

            if (Double.compare(candidate.getTwo(), maxWeight) == 0) {
                cnt++;
            }
        }

        if (cnt == 1) {
            return closestCore;
        } else {
            return -1; // Vertex is left unclassified until the next iteration.
        }
    }

}
