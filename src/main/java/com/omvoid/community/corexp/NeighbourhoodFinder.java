package com.omvoid.community.corexp;

import org.jgrapht.graph.AbstractBaseGraph;

import java.util.Set;
import java.util.stream.Collectors;

class NeighbourhoodFinder {
    public static <E> Set<Integer> find(AbstractBaseGraph<Integer, E> g, Integer v) {
        return g.edgesOf(v).stream().map(e -> {
            if (g.getEdgeSource(e).equals(v)) {
                return g.getEdgeTarget(e);
            }
            else {
                return g.getEdgeSource(e);
            }
        }).collect(Collectors.toSet());
    }
}
