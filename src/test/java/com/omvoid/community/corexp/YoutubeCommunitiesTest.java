package com.omvoid.community.corexp;

import com.omvoid.community.CommunityAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

class YoutubeCommunitiesTest {

    @Test
    public void createTest() throws IOException {

        CommunityAlgorithm ca = new CoreExpansionAlgorithmImpl();
        var graph = GraphReaderTestUtil.readCsvGraph(new FileInputStream("d:\\Downloads\\com-youtube.ungraph.txt\\com-youtube.ungraph.txt"));


        System.out.println(LocalTime.now());
        var result = ca.computeCommunities(graph);
        System.out.println(LocalTime.now());
        assert result != null;
        System.out.println(result.size());
    }

}