package com.omvoid.community.corexp;

import lombok.Getter;
import org.eclipse.collections.api.map.primitive.IntDoubleMap;
import org.eclipse.collections.api.map.primitive.ObjectIntMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntDoubleHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;
import org.jgrapht.Graph;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
class ExtendedGraph<V,E> {

    private final Graph<V,E> graph;

    private IntDoubleHashMap vertexWeights = new IntDoubleHashMap();
    private ObjectIntHashMap<V> mappedVertex = new ObjectIntHashMap<V>();
    private FastutilMapIntVertexGraph<E> fastutilGraph;

    public ExtendedGraph(Graph<V,E> graph) {
        this.graph = graph;

        //Initializing mappedVertex and vertexWeights
        AtomicInteger increment = new AtomicInteger();
        graph.vertexSet().forEach( v -> {
            mappedVertex.addToValue(v, increment.getAndIncrement());
            vertexWeights.addToValue(increment.get(), 0);
        });

        //TODO initilize fastutilGraph
    }
}
