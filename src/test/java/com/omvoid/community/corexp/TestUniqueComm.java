package com.omvoid.community.corexp;

import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class TestUniqueComm {

    @Test
    public void createTest() throws IOException, InterruptedException {
        var graph = GraphReaderTestUtil.readCsvGraph(
                this.getClass().getResourceAsStream("/facebook_combined.txt")
        );

        CommunityAlgorithm ca = new CoreExpansionAlgorithmImpl(4, true);
        var result = ca.computeCommunities(graph);
        var comVertexMapping = result.getCommunitiesMap();

        var visited = new IntHashSet();
        var r = new AtomicInteger(0);
        comVertexMapping.values().forEach(vv -> {
            vv.forEach(v -> {
                if (visited.contains(v)) {
                    r.incrementAndGet();
                }

                visited.add(v);
            });
        });

        Assertions.assertEquals( 0, r.intValue());
    }
}
