package com.omvoid.community;

import java.util.List;

public class DefaultCommunity implements Community {

    private final int id;
    private final List<Integer> vertexes;

    public DefaultCommunity(int id, List<Integer> vertexes) {
        this.id = id;
        this.vertexes = vertexes;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<Integer> getVertexes() {
        return vertexes;
    }
}
