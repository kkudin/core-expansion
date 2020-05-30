package com.omvoid.community;

import org.jgrapht.Graph;

public interface EdgeWeightProcessor {

    void compute(Graph<Vertex, Edge> graph);

}
