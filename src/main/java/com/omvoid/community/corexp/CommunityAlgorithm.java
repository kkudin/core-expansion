package com.omvoid.community.corexp;

import com.omvoid.community.models.CoreExpansionResults;
import org.jgrapht.Graph;

public interface CommunityAlgorithm {

    <V,E> CoreExpansionResults<V> computeCommunities(Graph<V,E> graph) throws InterruptedException;

}
