package com.omvoid.community.corexp;

import jdk.jshell.spi.ExecutionControl;
import lombok.Getter;
import org.eclipse.collections.api.map.primitive.IntDoubleMap;
import org.eclipse.collections.api.map.primitive.ObjectIntMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntDoubleHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultGraphType;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
class ExtendedGraph<V,E> {

    private final Graph<V,E> graph;

    private IntDoubleHashMap vertexWeights = new IntDoubleHashMap();
    private ObjectIntHashMap<V> mappedVertex = new ObjectIntHashMap<V>();
    private FastutilMapIntVertexGraph<DefaultWeightedEdge> fastutilGraph;

    public ExtendedGraph(Graph<V,E> graph) {
        this.graph = graph;

        //Initializing mappedVertex and vertexWeights
        AtomicInteger increment = new AtomicInteger();
        graph.vertexSet().forEach( v -> {
            mappedVertex.addToValue(v, increment.incrementAndGet());
            vertexWeights.addToValue(increment.get(), 0);
        });

        fastutilGraph = new FastutilMapIntVertexGraph<DefaultWeightedEdge>(
                SupplierUtil.createIntegerSupplier(),
                SupplierUtil.createDefaultWeightedEdgeSupplier(),
                DefaultGraphType.simple().asWeighted()
        );
        graph.edgeSet().forEach(e -> {
            int src = mappedVertex.get(graph.getEdgeSource(e));
            int dst = mappedVertex.get(graph.getEdgeTarget(e));
            if (!fastutilGraph.containsVertex(src)) {fastutilGraph.addVertex(src);}
            if (!fastutilGraph.containsVertex(dst)) {fastutilGraph.addVertex(dst);}
            if (!fastutilGraph.containsEdge(src, dst)) {
                fastutilGraph.addEdge(src, dst);
            } else {
                System.err.printf(
                        "Warning! Multi-edges graphs don't support yet! Edge %s-%s has been ignored!",
                        graph.getEdgeSource(e).toString(),
                        graph.getEdgeTarget(e).toString()
                );
            }
        });
    }
}
