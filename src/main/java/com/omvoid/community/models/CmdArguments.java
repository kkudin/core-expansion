package com.omvoid.community.models;

import lombok.Data;

@Data
public class CmdArguments {
    private String inputFile = "input.txt";
    private String delimiter = ",";
    private String commentStartWith = "#";
    private String outputDirectory = "result/";

    @Override
    public String toString() {
        return "Algorithm starting with this options {\n" +
                "    Input file = '" + inputFile + "\'\n" +
                "    Delimiter = '" + delimiter + "\'\n" +
                "    Comment line start with symbol = '" + commentStartWith + "\'\n" +
                "    Output directory = '" + outputDirectory + "\'\n" +
                '}';
    }
}
