package com.omvoid.community;

import com.omvoid.community.corexp.CoreExpansionAlgorithmImpl;
import com.omvoid.community.exception.GraphReaderException;
import com.omvoid.community.models.CmdArguments;
import com.omvoid.community.utils.CommandLineArgumentExtractor;
import com.omvoid.community.utils.GraphReader;
import org.apache.commons.cli.ParseException;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Application {

    public static void main(String[] args) throws ParseException, GraphReaderException, InterruptedException {
        CmdArguments cmdArguments = new CommandLineArgumentExtractor().extractArguments(args);

        GraphReader reader = new GraphReader();
        var graph = reader.readGraph(cmdArguments);

        CoreExpansionAlgorithmImpl coreExpansionAlgorithm = new CoreExpansionAlgorithmImpl();
        var communities = coreExpansionAlgorithm.computeCommunities(graph);

        ////
        TreeMap<Integer, Integer> commSizes = new TreeMap<>(
                Comparator.comparing(k -> -k)
        );

        communities.forEach(comm -> {
            int size = comm.getVertexes().size();
            commSizes.put(
                    size,
                    commSizes.getOrDefault(size, 0) + 1
            );
        });

        Iterator<Map.Entry<Integer, Integer>> iter = commSizes.entrySet().iterator();
        for (int i = 0; i < 1000; i++) {
            if (iter.hasNext()) {
                Map.Entry<Integer, Integer> e = iter.next();
                System.out.printf("Number of communities of size %d : %d\n", e.getKey(), e.getValue());
            } else {
                break;
            }
        }
        ////

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println(cmdArguments);
    }
}
