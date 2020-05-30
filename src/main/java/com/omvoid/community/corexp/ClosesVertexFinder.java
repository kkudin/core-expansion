package com.omvoid.community.corexp;

import java.util.List;
import java.util.Set;

class ClosesVertexFinder {

    /**
     * A node is called unclassified if it is not part of any core.
     * For each unclassified node, try finding its closest core by
     * calling the function Find_Closest_Core (cf. Algorithm 4).
     * When every unclassified node is processed, perform the addition
     * operations, i.e., add the nodes to the corresponding cores.
     * A node n has C as closest core if this maximizes the sum of
     * weights of out-links from n to the nodes in C. Note that sometimes
     * we cannot find such a core, this happens if the sum of weights
     * out of n is zero, or in case two or more cores result in the same
     * value of sum. In such cases n is left unclassified until the next iteration.
     * @return
     * @param cores
     * @param v
     * @param extendedGraph
     */
    int find(Set<Integer> cores, Integer v, ExtendedGraph extendedGraph) {
        //TODO
        return -1;
    }

}
