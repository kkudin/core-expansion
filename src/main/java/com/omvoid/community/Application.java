package com.omvoid.community;

import com.omvoid.community.exception.GraphReaderException;
import com.omvoid.community.utils.CommandLineArgumentExtractor;
import com.omvoid.community.utils.GraphReader;
import org.apache.commons.cli.ParseException;

public class Application {

    public static void main(String[] args) throws ParseException, GraphReaderException {
        Arguments arguments = new CommandLineArgumentExtractor().extractArguments(args);

        GraphReader reader = new GraphReader();
        var graph = reader.readGraph(arguments);

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println(arguments);
    }
}
