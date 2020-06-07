package com.omvoid.community;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DefaultCoreExpansionResults<V> implements CoreExpansionResults<V> {
    private final Map<V, Set<V>> mapOfCommunities;
    private final Map<V, V> vertexCommunityMapping;
    private final Map<V, Set<V>> cores;

    public DefaultCoreExpansionResults(
            Map<V, Set<V>> mapOfCommunities,
            Map<V, V> vertexCommunityMapping,
            Map<V, Set<V>> cores
    ) {
        this.mapOfCommunities = mapOfCommunities;
        this.vertexCommunityMapping = vertexCommunityMapping;
        this.cores = cores;
    }

    @Override
    public Map<V, Set<V>> getCommunitiesMap() {
        return mapOfCommunities;
    }

    @Override
    public Map<V, V> getVertexCommunityMap() {
        return vertexCommunityMapping;
    }

    @Override
    public Map<V, Set<V>> getVertexCoresMap() {
        return cores;
    }
}
