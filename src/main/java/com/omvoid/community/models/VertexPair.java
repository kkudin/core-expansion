package com.omvoid.community.models;

public class VertexPair {
    private Integer firstVertex;
    private Integer secondVertex;

    public VertexPair() {}

    public VertexPair(Integer firstVertex, Integer secondVertex) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
    }

    public Integer getFirstVertex() {
        return firstVertex;
    }

    public void setFirstVertex(Integer firstVertex) {
        this.firstVertex = firstVertex;
    }

    public Integer getSecondVertex() {
        return secondVertex;
    }

    public void setSecondVertex(Integer secondVertex) {
        this.secondVertex = secondVertex;
    }
}
