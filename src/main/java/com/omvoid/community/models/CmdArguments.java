package com.omvoid.community.models;

public class CmdArguments {
    private String inputFile;
    private String delimiter = ",";
    private String commentStartWith = "#";
    private String outputDirectory = "result";

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getCommentStartWith() {
        return commentStartWith;
    }

    public void setCommentStartWith(String commentStartWith) {
        this.commentStartWith = commentStartWith;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

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
