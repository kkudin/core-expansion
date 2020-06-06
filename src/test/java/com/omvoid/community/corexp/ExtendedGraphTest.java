package com.omvoid.community.corexp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

class ExtendedGraphTest {

    @Test
    public void createTest() throws IOException, InterruptedException {

        CommunityAlgorithm ca = new CoreExpansionAlgorithmImpl();
        var graph = GraphReaderTestUtil.readCsvGraph(this.getClass().getResourceAsStream("/graph.cvs"));

        var result = ca.computeCommunities(graph);
        assert result != null;
        Assertions.assertEquals(result.getCommunities().size(), 2);
        Set<Integer> comm1 = result.getCommunities().values().iterator().next();
        if (comm1.size() == 6) {
            for (int expected : List.of(1, 2, 3, 4, 5, 11)) {
                assert comm1.contains(expected);
            }
        } else if (comm1.size() == 5) {
            for (int expected : List.of(6, 7, 8, 9, 10)) {
                assert comm1.contains(expected);
            }
        } else {
            throw new AssertionError("Communities should be of size 6 and 5!");
        }
    }

}