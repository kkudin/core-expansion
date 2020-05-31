package com.omvoid.community.corexp;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.opt.graph.fastutil.FastutilMapIntVertexGraph;

class VertexWeightProcessor {

    /**
     * Given the weighted graph obtained from step 1, compute the weight
     * of each node n in V by calling the function Compute_Node_Weights (cf. Algorithm 3).
     * The weight of a node n is the sum of the weights of the out-links of n.
     * @param extendedGraph
     */
    public void calculateWeight(ExtendedGraph extendedGraph) {

        final FastutilMapIntVertexGraph<DefaultWeightedEdge> graph = extendedGraph.getFastutilGraph();
        final var vertexMap = extendedGraph.getVertexWeights();

        extendedGraph.getMappedVertex().forEach( v -> vertexMap.put(v,
            graph.edgesOf(v).stream().mapToDouble(graph::getEdgeWeight).sum()
        ));
    }
}
