package com.omvoid.community.corexp;

import com.omvoid.community.CommunityAlgorithm;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;

class FacebookTest {

    @Test
    public void createTest() throws IOException, InterruptedException {

        CommunityAlgorithm ca = new CoreExpansionAlgorithmImpl();
        var graph = GraphReaderTestUtil.readCsvGraph(this.getClass().getResourceAsStream("/facebook_combined.txt"));


        System.out.println(LocalTime.now());
        var result = ca.computeCommunities(graph);
        System.out.println(LocalTime.now());
        assert result != null;
        System.out.println(result.size());
    }

}