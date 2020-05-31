package com.omvoid.community;

import org.jgrapht.Graph;

import java.util.List;

public interface CommunityAlgorithm {

    <V,E> List<Community<V>> computeCommunities(Graph<V,E> graph);

}
