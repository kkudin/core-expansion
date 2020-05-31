package com.omvoid.community.corexp;

import org.jgrapht.Graph;

import java.util.Set;
import java.util.stream.Collectors;

class NeighbourhoodFinder {
    public static Set<Integer> find(Graph<Integer, Object> g, Integer v) {
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
