package com.omvoid.community.utils;

import com.omvoid.community.exception.JsonWriterException;
import com.omvoid.community.models.DefaultCoreExpansionResults;

public interface ResultWriter {

    //TODO rework for using interface CoreExpansionResults
    public void writeResult(DefaultCoreExpansionResults results, String path) throws JsonWriterException;
}
