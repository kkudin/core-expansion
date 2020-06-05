package com.omvoid.community;

import lombok.Data;

@Data
public class Arguments {
    private String inputFile = "input.txt";
    private String delimiter = ",";
    private String commentStartWith = "#";
    private String outputDirectory = "result/";
}
