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

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println(cmdArguments);
    }
}
