package com.omvoid.community;

import org.jgrapht.Graph;

public interface CommunityAlgorithm {

    <V,E> CoreExpansionResults<V> computeCommunities(Graph<V,E> graph) throws InterruptedException;

}
