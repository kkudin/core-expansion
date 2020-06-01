package com.omvoid.community.corexp;

import com.omvoid.community.CommunityAlgorithm;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

class YoutubeCommunitiesTest {

    @Test
    public void createTest() throws IOException, InterruptedException {

        CommunityAlgorithm ca = new CoreExpansionAlgorithmImpl();
        var graph = GraphReaderTestUtil.readCsvGraph(this.getClass().getResourceAsStream("/com-youtube.ungraph.txt"));


        var now = LocalTime.now();
        var result = ca.computeCommunities(graph);
        System.out.printf("Done in %d seconds\n", now.until(LocalTime.now(), ChronoUnit.SECONDS));
        assert result != null;
        System.out.printf("Found %d communities.", result.size());
    }
}