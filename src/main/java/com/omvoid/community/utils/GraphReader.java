package com.omvoid.community.utils;

import com.omvoid.community.models.CmdArguments;
import com.omvoid.community.exception.GraphReaderException;
import com.omvoid.community.models.VertexPair;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.*;

public class GraphReader {

    public Graph<String, DefaultWeightedEdge> readGraph(CmdArguments cmdArguments) throws GraphReaderException {

        Graph<String, DefaultWeightedEdge> graph = new DefaultUndirectedGraph<>(DefaultWeightedEdge.class);

        BufferedReader reader = new BufferedReader(getFileReader(cmdArguments));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(cmdArguments.getCommentStartWith())) continue;
                VertexPair vertexPair = getVertexPair(line, cmdArguments.getDelimiter());

                double edgeWeight;
                if (cmdArguments.getIsWeighted()) {
                    edgeWeight = Double.parseDouble(line.split(cmdArguments.getDelimiter())[2].strip());
                } else {
                    edgeWeight = 1.0;
                }

                if (!graph.containsVertex(vertexPair.getFirstVertex())) {
                    graph.addVertex(vertexPair.getFirstVertex());
                }

                if (!graph.containsVertex(vertexPair.getSecondVertex())) {
                    graph.addVertex(vertexPair.getSecondVertex());
                }

                DefaultWeightedEdge e = graph.addEdge(vertexPair.getFirstVertex(), vertexPair.getSecondVertex());
                graph.setEdgeWeight(e, edgeWeight);
            }
        } catch (IOException e) {
            throw new GraphReaderException(e);
        }
        return graph;
    }

    private FileReader getFileReader(CmdArguments cmdArguments) throws GraphReaderException {
        try {
            return new FileReader(cmdArguments.getInputFile());
        } catch (FileNotFoundException e) {
            throw new GraphReaderException(e);
        }
    }

    private VertexPair getVertexPair(String line, String delimiter) throws GraphReaderException {
        String[] rawVertex = line.split(delimiter);

        if (rawVertex.length < 2) throw new GraphReaderException("Line " + line + " has less then 2 vertex");

        VertexPair vertexPair = new VertexPair();

        vertexPair.setFirstVertex(rawVertex[0].trim());
        vertexPair.setSecondVertex(rawVertex[1].trim());

        if (vertexPair.getFirstVertex().equals(vertexPair.getSecondVertex()))
            throw new GraphReaderException("Line " + line + " this algorithm does not support loop");

        return vertexPair;
    }
}
