package com.omvoid.community.corexp;

import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class PolbookTest {

    @RepeatedTest(1000)
    public void createTest() throws IOException, InterruptedException {

        var expectedCommunity1 = List.of(
                51,  2,  0,  5, 52,  1,  7,  6, 50, 18, 14, 25, 22, 19, 29, 49,
                16, 55,  3,  9, 53, 15, 21, 27, 11, 26, 20, 48, 10, 12,  8, 40,
                24, 46, 57, 38, 23, 17, 41, 47, 13, 45, 56, 33, 54, 37, 39, 32,
                44, 34, 36, 35, 42, 43
        );

        var expectedCommunity2 = IntStream.range(0, 105).boxed().collect(Collectors.toList());
        expectedCommunity2.removeAll(expectedCommunity1);

        CommunityAlgorithm ca = new CoreExpansionAlgorithmImpl();
        var graph = GraphReaderTestUtil.readCsvGraph(this.getClass().getResourceAsStream("/polbook.csv"));


        var result = ca.computeCommunities(graph);
        assertThat(result).isNotNull();
        assertThat(result.getCommunities().size()).isEqualTo(2);
        var iter = result.getCommunities().values().iterator();
        var comm1 = iter.next();
        var comm2 = iter.next();


        if(comm1.contains(0)) {
            assertThat(comm1.containsAll(expectedCommunity1));
            assertThat(comm2.containsAll(expectedCommunity2));
        } else {
            assertThat(comm1.containsAll(expectedCommunity2));
            assertThat(comm2.containsAll(expectedCommunity1));
        }
    }

}