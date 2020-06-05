package com.omvoid.community.utils;

import com.omvoid.community.Arguments;
import org.apache.commons.cli.*;

public class CommandLineArgumentExtractor {

    public Arguments extractArguments(String[] args) throws ParseException {
        Arguments arguments = new Arguments();

        CommandLine cmd = parseArgs(args);

        if (cmd.hasOption("i")) {
            arguments.setInputFile(cmd.getOptionValue("i"));
        }

        if (cmd.hasOption("d")) {
            arguments.setDelimiter(cmd.getOptionValue("d"));
        }

        if (cmd.hasOption("c")) {
            arguments.setCommentStartWith(cmd.getOptionValue("c"));
        }

        if (cmd.hasOption("r")) {
            arguments.setOutputDirectory(cmd.getOptionValue("r"));
        }

        return arguments;
    }

    private Options buildOptions() {
        Options options = new Options();
        options.addOption("i", "inputPath", true, "Input file");
        options.addOption("d", "delimiter", true, "Delimiter char");
        options.addOption("c", "comment", true, "Comment char");
        options.addOption("r", "resultPath", true, "Result path");
        return options;
    }

    private CommandLine parseArgs(String[] args) throws ParseException {
        return new DefaultParser().parse(buildOptions(), args);
    }
}
