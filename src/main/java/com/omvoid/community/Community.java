package com.omvoid.community;

import java.util.List;

public interface Community<V> {

    V getCore();
    List<V> getVertexes();

}
