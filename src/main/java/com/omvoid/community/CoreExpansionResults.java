package com.omvoid.community;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface CoreExpansionResults<V> {
    /**
     * Return Mapping from Communities cores to list of communities members.
     *
     * @return Map from cores to list of members
     */
    Map<V, Set<V>> getCommunitiesMap();

    /**
     * Return Mapping from Communities members to main community core.
     *
     * @return community or null
     */
    Map<V, V> getVertexCommunityMap();

    /**
     * Return Mapping from Communities members to set of community cores.
     *
     * @return List of cores
     */
    Map<V, Set<V>> getVertexCoresMap();
}
