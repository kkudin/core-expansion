package com.omvoid.community.utils;

import com.omvoid.community.models.CmdArguments;
import org.apache.commons.cli.*;

public class CommandLineArgumentExtractor {

    public CmdArguments extractArguments(String[] args) throws ParseException {
        CmdArguments cmdArguments = new CmdArguments();

        CommandLine cmd = parseArgs(args);

        if (cmd.hasOption("i")) {
            cmdArguments.setInputFile(cmd.getOptionValue("i"));
        }

        if (cmd.hasOption("d")) {
            cmdArguments.setDelimiter(cmd.getOptionValue("d"));
        }

        if (cmd.hasOption("c")) {
            cmdArguments.setCommentStartWith(cmd.getOptionValue("c"));
        }

        if (cmd.hasOption("r")) {
            cmdArguments.setOutputDirectory(cmd.getOptionValue("r"));
        }

        return cmdArguments;
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
