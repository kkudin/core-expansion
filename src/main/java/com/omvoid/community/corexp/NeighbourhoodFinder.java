package com.omvoid.community.corexp;

import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.graph.AbstractBaseGraph;

import java.util.Set;

class NeighbourhoodFinder {
    public static <E> IntHashSet find(AbstractBaseGraph<Integer, E> g, Integer v) {
        Set<E> edges = g.edgesOf(v);
        IntHashSet result = new IntHashSet(edges.size());

        for (E e : edges) {
            if (g.getEdgeSource(e).equals(v)) {
                result.add(g.getEdgeTarget(e));
            }
            else {
                result.add(g.getEdgeSource(e));
            }
        }

        return result;
    }
}
