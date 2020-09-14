package com.omvoid.community.corexp;


import org.eclipse.collections.api.iterator.IntIterator;
import org.eclipse.collections.impl.map.mutable.primitive.IntDoubleHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.Set;

class CoresFinder {
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
    public <V, E> IntObjectHashMap<IntHashSet> find(ExtendedGraph<V, E> graph) {
        Set<Integer> vertices = graph.getFastutilGraph().vertexSet();
        IntHashSet visited = new IntHashSet(vertices.size());
        IntObjectHashMap<IntHashSet> cores = new IntObjectHashMap<>();
        for (int v : vertices) {
            applyAdditionalCores(v, graph.getVertexWeights(), visited, cores, graph.getFastutilGraph());
        }

        return cores;
    }

    private void applyAdditionalCores(
            int v, IntDoubleHashMap vertexWeights,
            IntHashSet visited, IntObjectHashMap<IntHashSet> cores,
            FastutilMapIntVertexGraph<DefaultWeightedEdge> g) {

        if (visited.contains(v)) {
            return;
        }

        double vW = vertexWeights.get(v);

        if (Double.compare(vW, .0) == 0) {
            return;
        }

        IntHashSet nn = NeighbourhoodFinder.find(g, v);
        IntHashSet coreVertices = new IntHashSet();
        coreVertices.add(v);

        if (nn.size() == 0) {
            visited.add(v);
            cores.put(v, coreVertices);
            return;
        }

        IntHashSet equalsWeightsNodes = new IntHashSet();

        IntIterator iter = nn.intIterator();
        int n;

        while (iter.hasNext())
        {
            n = iter.next();
            if (vertexWeights.get(n) > vW) {
                visited.add(v);
                return;
            }
            if (Double.compare(vertexWeights.get(n), vW) == 0) {
                equalsWeightsNodes.add(n);
            }
        }

        equalsWeightsNodes.forEach(
                candidate -> {
                    if (visited.contains(candidate)) {
                        return;
                    }

                    visited.add(candidate);
                    coreVertices.add(candidate);
                }
        );

        cores.put(v, coreVertices);
        visited.add(v);
    }
}
