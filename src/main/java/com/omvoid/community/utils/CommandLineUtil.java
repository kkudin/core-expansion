package com.omvoid.community.utils;

import com.omvoid.community.models.CmdArguments;
import org.apache.commons.cli.*;

public class CommandLineUtil {

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

        if (cmd.hasOption("w")) {
            cmdArguments.setIsWeighted(Boolean.parseBoolean(cmd.getOptionValue("w")));
        }

        return cmdArguments;
    }

    private Options getOptions() {
        Options options = new Options();
        options.addRequiredOption("i", "inputFile", true, "Input file");
        options.addOption("d", "delimiter", true, "Delimiter char");
        options.addOption("c", "comment", true, "Comment char");
        options.addOption("r", "resultPath", true, "Result path");
        options.addOption("w", "isWeighted", false, "Is graph weighted?");
        return options;
    }

    private CommandLine parseArgs(String[] args) throws ParseException {
        return new DefaultParser().parse(getOptions(), args);
    }

    public void printHelp() {
        String header = "Input file is required option\n";
        String footer = "Default options : \n" +
                "    Delimiter = ':'\n" +
                "    Comment line start with symbol = '#'\n" +
                "    Output directory = 'result'\n" +
                "    Is weighted = false\n" +
                "For source code you can follow https://github.com/kkudin/core-expansion";
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Core expansion algorithm", header, getOptions(), footer);
    }
}
