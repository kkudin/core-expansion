package com.omvoid.community;

import org.jgrapht.Graph;

import java.util.List;

public interface CommunityAlgorithm {

    List<Community> computeCommunities(Graph graph);

}
