package com.omvoid.community;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface CoreExpansionResults<V> {
    /**
     * Return Mapping from Communities cores to list of communities members.
     * @return Map from cores to list of members
     */
    Map<V, Set<V>> getCommunities();

    /**
     * Return community for given vertex.
     * Return null if vertex hasn't been classified.
     *
     * @param vertex vertex
     * @return community or null
     */
    Optional<V> getCommunity(V vertex);

    /**
     * Return core vertices for given community.
     * Return null of there isn't such community.
     *
     * @param community community
     * @return List of cores
     */
    Optional<Set<V>> getCores(V community);
}
