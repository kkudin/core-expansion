package com.omvoid.community.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omvoid.community.exception.JsonWriterException;
import com.omvoid.community.models.CoreExpansionResults;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JsonResultWriter implements ResultWriter {

    private final String COMMUNITIES_MAP_FILE_NAME = "CommunitiesMap.json";
    private final String VERTEX_CORES_MAP_FILE_NAME = "VertexCoresMap.json";
    private final String VERTEX_COMMUNITY_MAP_FILE_NAME = "VertexCommunityMap.json";

    @Override
    public void writeResult(CoreExpansionResults<String> results, String path) throws JsonWriterException {
        writeAsJson(results.getCommunitiesMap(), path + File.separator + COMMUNITIES_MAP_FILE_NAME);
        writeAsJson(results.getVertexCoresMap(), path + File.separator + VERTEX_CORES_MAP_FILE_NAME);
        writeAsJson(results.getVertexCommunityMap(), path + File.separator + VERTEX_COMMUNITY_MAP_FILE_NAME);
    }

    private void writeAsJson(Object result, String path) throws JsonWriterException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            new File(path).getParentFile().mkdirs();
            mapper.writeValue(new FileOutputStream(path), result); ;
        } catch (IOException e) {
            throw new JsonWriterException(e);
        }
    }
}
