package com.omvoid.community.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omvoid.community.exception.JsonWriterException;
import com.omvoid.community.models.DefaultCoreExpansionResults;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonResultWriter implements ResultWriter {
    @Override
    public void writeResult(DefaultCoreExpansionResults results, String path) throws JsonWriterException {
        writeAsJson(results.getCommunities(), path + "\\communities.json");
        writeAsJson(results.getCores(), path + "\\cores.json");
        writeAsJson(results.getVertexCommunityMapping(), path + "\\vertexCommunityMapping.json");
    }

    private void writeAsJson(Object result, String path) throws JsonWriterException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String serialized = mapper.writeValueAsString(result);
            fileWriter(serialized, path);
        } catch (JsonProcessingException e) {
            throw new JsonWriterException(e);
        }
    }

    private void fileWriter(String content, String path) throws JsonWriterException {
        try {
            File file = new File(System.getProperty("user.dir") + "\\" + path);
            file.getParentFile().mkdirs();

            FileWriter fileWriter = new FileWriter(file);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.close();

        } catch (IOException e) {
            throw new JsonWriterException(e);
        }
    }
}