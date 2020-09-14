package com.omvoid.community.similarities;

import org.jgrapht.graph.AbstractBaseGraph;

public interface SimilarityMetric {
    double similarity(AbstractBaseGraph g, int v1, int v2);
}
