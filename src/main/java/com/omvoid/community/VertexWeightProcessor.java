package com.omvoid.community;

import org.jgrapht.Graph;

public interface VertexWeightProcessor {

    void compute(Graph<Vertex,Edge> graph);

}
