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
    public Map<V, Set<V>> getCommunities() {
        return mapOfCommunities;
    }

    @Override
    public Optional<V> getCommunity(V vertex) {
        if (vertexCommunityMapping.containsKey(vertex)) {
            return Optional.of(vertexCommunityMapping.get(vertex));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Set<V>> getCores(V community) {
        if (cores.containsKey(community)) {
            return Optional.of(cores.get(community));
        } else {
            return Optional.empty();
        }
    }
}
