package com.omvoid.community.utils;

import com.omvoid.community.exception.JsonWriterException;
import com.omvoid.community.models.CoreExpansionResults;

public interface ResultWriter {

    void writeResult(CoreExpansionResults<String> results, String path) throws JsonWriterException;
}
