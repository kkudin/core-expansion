package com.omvoid.community.metrics;

import org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

import java.util.concurrent.atomic.DoubleAdder;

public class Modularity {
  public static double unweighted(
      FastutilMapIntVertexGraph<DefaultWeightedEdge> g,
      IntIntHashMap communities
  ) {
    var modularity = new DoubleAdder();
    double m = g.edgeSet().stream().map(g::getEdgeWeight).reduce(Double::sum).orElse(1.0);

    g.edgeSet().forEach(
        e -> {
          int src = g.getEdgeSource(e);
          int dst = g.getEdgeTarget(e);

          if (communities.get(src) == communities.get(dst)) {
            double v = g.getEdgeWeight(e);

            var w1sum = new DoubleAdder();
            var w2sum = new DoubleAdder();

            g.edgesOf(src).forEach(e_ -> w1sum.add(g.getEdgeWeight(e_)));
            g.edgesOf(dst).forEach(e_ -> w2sum.add(g.getEdgeWeight(e_)));

            modularity.add(v - w1sum.doubleValue() * w2sum.doubleValue() / (2 * m));
          }
        }
    );

    return modularity.doubleValue() / (2 * m);
  }
}
