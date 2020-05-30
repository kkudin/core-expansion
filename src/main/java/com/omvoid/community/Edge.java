package com.omvoid.community;

import com.omvoid.community.Vertex;

public interface Edge {

    Vertex getSourceVertex();
    Vertex getTargetVertex();
    void setSourceVertex(Vertex source);
    void setTargetVertex(Vertex target);


    double getWeight();
    void setWight(double weight);

}
