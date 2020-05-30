package com.omvoid.community.corexp;

import org.jgrapht.graph.DefaultUndirectedGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtendedGraphTest {

    @Test
    public void createTest() {
        ExtendedGraph exg = new ExtendedGraph(new DefaultUndirectedGraph(Object.class));
    }

}