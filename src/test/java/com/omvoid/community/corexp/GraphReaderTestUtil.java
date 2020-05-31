package com.omvoid.community.corexp;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.nio.csv.CSVExporter;
import org.jgrapht.nio.csv.CSVImporter;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class GraphReaderTestUtil {

    @Test
    public void GraphReaderTestUtilTest() throws IOException {
        readCsvGraph("/graph.cvs");
    }

    public static Graph<Integer, DefaultWeightedEdge> readCsvGraph(String resourceName) throws IOException {
        Graph<Integer, DefaultWeightedEdge> result = new DefaultUndirectedGraph<>(DefaultWeightedEdge.class);
        var csvStream = GraphReaderTestUtil.class.getResourceAsStream(resourceName);

        CSVImporter<Integer, DefaultWeightedEdge> exporter = new CSVImporter<>();
        exporter.importGraph(result, csvStream);

        return result;
    }

}
