package com.omvoid.community.corexp;

import com.omvoid.community.CommunityAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class ExtendedGraphTest {

    @Test
    public void createTest() throws IOException {

        CommunityAlgorithm ca = new CoreExpansionAlgorithmImpl();
        var graph = GraphReaderTestUtil.readCsvGraph(this.getClass().getResourceAsStream("/graph.cvs"));

        var result = ca.computeCommunities(graph);
        assert result != null;
        Assertions.assertEquals(result.size(), 2);
        List<Integer> comm1 = result.get(0).getVertexes();
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