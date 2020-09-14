package com.omvoid.community.similarities;

import com.omvoid.community.corexp.NeighbourhoodFinder;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.graph.AbstractBaseGraph;

public class JaccardSimilarity implements SimilarityMetric {
    public JaccardSimilarity() {}

    @Override
    public double similarity(AbstractBaseGraph g, int v1, int v2) {
        IntHashSet srcNN, dstNN;

        srcNN = NeighbourhoodFinder.find(g, v1);
        dstNN = NeighbourhoodFinder.find(g, v2);

        double w = (srcNN.count(dstNN::contains) * 1.0);

        IntHashSet union;
        if (srcNN.size() > dstNN.size()) {
            union = new IntHashSet(srcNN);
            union.addAll(dstNN);
        } else {
            union = new IntHashSet(dstNN);
            union.addAll(srcNN);
        }

        if (union.size() > 2) {
            w /= (union.size() - 2);
        } else {
            w = 0.0;
        }

        return w;
    }
}
