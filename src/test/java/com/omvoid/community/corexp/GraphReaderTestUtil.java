package com.omvoid.community.corexp;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class GraphReaderTestUtil {

    @Test
    public void GraphReaderTestUtilTest() throws IOException {
        readCsvGraph(GraphReaderTestUtil.class.getResourceAsStream("/Email-Enron.csv"));
    }

    public static Graph<Integer, DefaultWeightedEdge> readCsvGraph(InputStream inputStream) throws IOException {
        Graph<Integer, DefaultWeightedEdge> result = new DefaultUndirectedGraph<>(DefaultWeightedEdge.class);
        var reader = new BufferedReader(new InputStreamReader(inputStream));
        while(reader.ready()) {
            var vertexes = List.of(reader.readLine().split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
            if(vertexes.size() != 2) {
                continue;
            }
            if(!result.containsVertex(vertexes.get(0))) {
                result.addVertex(vertexes.get(0));
            }
            if(!result.containsVertex(vertexes.get(1))) {
                result.addVertex(vertexes.get(1));
            }
            result.addEdge(vertexes.get(0), vertexes.get(1));

        }
        return result;
    }

}
