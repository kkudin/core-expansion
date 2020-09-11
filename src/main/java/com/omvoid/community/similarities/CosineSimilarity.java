package com.omvoid.community.similarities;

import com.omvoid.community.corexp.NeighbourhoodFinder;
import org.eclipse.collections.impl.map.mutable.primitive.IntDoubleHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.graph.AbstractBaseGraph;

import java.util.concurrent.atomic.DoubleAdder;

public class CosineSimilarity implements SimilarityMetric {
    public CosineSimilarity() {}

    @Override
    public double similarity(AbstractBaseGraph g, int v1, int v2) {
        IntHashSet srcNN, dstNN;

        srcNN = NeighbourhoodFinder.find(g, v1);
        dstNN = NeighbourhoodFinder.find(g, v2);
        IntHashSet union;

        if (srcNN.size() > dstNN.size()) {
            union = new IntHashSet(srcNN);
            union.addAll(dstNN);
        } else {
            union = new IntHashSet(dstNN);
            union.addAll(srcNN);
        }

        IntDoubleHashMap w1k = new IntDoubleHashMap();
        IntDoubleHashMap w2k = new IntDoubleHashMap();
        var weightedDeg1 = new DoubleAdder();
        var weightedDeg2 = new DoubleAdder();

        if (union.size() == 0) {
            return .0;
        }

        srcNN.forEach(vOther -> {
            double w = g.getEdgeWeight(g.getEdge(v1, vOther));
            if (union.contains(vOther)) {
                w1k.put(vOther, w);
                weightedDeg1.add(w);
            } else {
                weightedDeg1.add(w);
            }
        });

        dstNN.forEach(vOther -> {
            double w = g.getEdgeWeight(g.getEdge(v2, vOther));
            if (union.contains(vOther)) {
                w2k.put(vOther, w);
                weightedDeg2.add(w);
            } else {
                weightedDeg2.add(w);
            }
        });

        var dotProd = new DoubleAdder();
        w1k.keySet().forEach(vOther -> dotProd.add(w1k.get(vOther) * w2k.get(vOther)));

        return dotProd.doubleValue() / (weightedDeg1.doubleValue() * weightedDeg2.doubleValue());
    }
}
