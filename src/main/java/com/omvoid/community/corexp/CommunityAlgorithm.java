package com.omvoid.community.corexp;

import com.omvoid.community.models.Community;
import org.jgrapht.Graph;

import java.util.List;

public interface CommunityAlgorithm {

    <V,E> List<Community<V>> computeCommunities(Graph<V,E> graph) throws InterruptedException;

}
