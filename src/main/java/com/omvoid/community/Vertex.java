package com.omvoid.community;

import java.util.List;

public interface Vertex {

    long getId();

    double getWeight();
    void setWight(double weight);

    List<Edge> getEdges();


}
