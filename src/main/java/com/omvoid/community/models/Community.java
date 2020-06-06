package com.omvoid.community.models;

import java.util.List;

public interface Community<V> {

    V getCore();
    List<V> getVertexes();

}
