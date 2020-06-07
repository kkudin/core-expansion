package com.omvoid.community.models;

public class VertexPair {
    private String firstVertex;
    private String secondVertex;

    public VertexPair(){}

    public VertexPair(String firstVertex, String secondVertex) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
    }

    public String getFirstVertex() {
        return firstVertex;
    }

    public void setFirstVertex(String firstVertex) {
        this.firstVertex = firstVertex;
    }

    public String getSecondVertex() {
        return secondVertex;
    }

    public void setSecondVertex(String secondVertex) {
        this.secondVertex = secondVertex;
    }
}
