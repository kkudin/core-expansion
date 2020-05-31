package com.omvoid.community.corexp;

import com.omvoid.community.CommunityAlgorithm;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExtendedGraphTest {

    @Test
    public void createTest() throws IOException {

        CommunityAlgorithm ca = new CoreExpansionAlgorithmImpl();
        var graph = GraphReaderTestUtil.readCsvGraph("/graph.cvs");

        var result = ca.computeCommunities(graph);
        assert result != null;

    }

}