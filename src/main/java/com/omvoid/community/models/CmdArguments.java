package com.omvoid.community.models;

import lombok.Data;

@Data
public class CmdArguments {
    private String inputFile = "input.txt";
    private String delimiter = ",";
    private String commentStartWith = "#";
    private String outputDirectory = "result/";
}
