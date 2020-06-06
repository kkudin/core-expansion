package com.omvoid.community.corexp;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

class FacebookTest {

    @Test
    public void createTest() throws IOException, InterruptedException {

        CommunityAlgorithm ca = new CoreExpansionAlgorithmImpl();
        var graph = GraphReaderTestUtil.readCsvGraph(this.getClass().getResourceAsStream("/facebook_combined.txt"));


        var now = LocalTime.now();
        var result = ca.computeCommunities(graph);
        System.out.printf("Done in %d seconds\n", now.until(LocalTime.now(), ChronoUnit.SECONDS));
        assert result != null;
        System.out.printf("Found %d communities.\n", result.getCommunities().size());

        TreeMap<Integer, Integer> commSizes = new TreeMap<>(
                Comparator.comparing(k -> -k)
        );

        result.getCommunities().values().forEach(comm -> {
            int size = comm.size();
            commSizes.put(
                    size,
                    commSizes.getOrDefault(size, 0) + 1
            );
        });

        Iterator<Map.Entry<Integer, Integer>> iter = commSizes.entrySet().iterator();
        for (int i = 0; i < 100; i++) {
            if (iter.hasNext()) {
                Map.Entry<Integer, Integer> e = iter.next();
                System.out.printf("Number of communities of size %d : %d\n", e.getKey(), e.getValue());
            } else {
                break;
            }
        }
    }

}