package com.omvoid.community;

import java.util.List;

public class  DefaultCommunity<V> implements Community<V> {

    private final V core;
    private final List<V> vertexes;

    public DefaultCommunity(V core, List<V> vertexes) {
        this.core = core;
        this.vertexes = vertexes;
    }

    @Override
    public V getCore() {
        return core;
    }

    @Override
    public List<V> getVertexes() {
        return vertexes;
    }
}
