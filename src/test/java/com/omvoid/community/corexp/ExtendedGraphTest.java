package com.omvoid.community.corexp;

import com.omvoid.community.CommunityAlgorithm;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ExtendedGraphTest {

    @Test
    public void createTest() throws IOException {

        CommunityAlgorithm ca = new CoreExpansionAlgorithmImpl();
        var graph = GraphReaderTestUtil.readCsvGraph("/graph.cvs");

        var result = ca.computeCommunities(graph);
        assert result != null;
    }

}