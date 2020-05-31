package com.omvoid.community.corexp;

import com.omvoid.community.Community;
import com.omvoid.community.CommunityAlgorithm;
import com.omvoid.community.DefaultCommunity;
import org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoreExpansionAlgorithmImpl implements CommunityAlgorithm {

    private final VertexWeightProcessor vertexWeightProcessor = new VertexWeightProcessor();
    private final EdgeWeightProcessor edgeWeightProcessor = new EdgeWeightProcessor();
    private final CoresFinder coresFinder = new CoresFinder();
    private final ClosesVertexFinder closesVertexFinder = new ClosesVertexFinder();


    public <V,E> List<Community<V>> computeCommunities(Graph<V,E> graph) throws InterruptedException {

        var extendedGraph = new ExtendedGraph<>(graph);

        edgeWeightProcessor.calculateWeight(extendedGraph);
        vertexWeightProcessor.calculateWeight(extendedGraph);

        var communityMap = coresFinder.find(extendedGraph);

        IntHashSet unclassifiedVertexes = new IntHashSet();
        IntIntHashMap vertexCommMapping = new IntIntHashMap(graph.vertexSet().size());

        extendedGraph.getMappedVertex().values().forEach( v -> {
            if(!communityMap.containsKey(v)) {
                unclassifiedVertexes.add(v);
                vertexCommMapping.put(v, -1);
            } else {
                vertexCommMapping.put(v, v);
            }
        });

        communityMap.values()
                .stream()
                .filter(l -> l.size() > 0)
                .forEach(unclassifiedVertexes::removeAll);

        final int[] foundVertexes = new int[1];

        while (true) {
            foundVertexes[0] = 0;
            closesVertexFinder.findAll(
                    vertexCommMapping, extendedGraph, unclassifiedVertexes
            ).forEachKeyValue(
                    (k, v) -> {
                        if (v != -1) {
                            unclassifiedVertexes.remove(k);
                            communityMap.get(v).add(k);
                            vertexCommMapping.put(k, v);
                            foundVertexes[0]++;
                        }
                    }
            );

            if(foundVertexes[0] == 0) {
                break;
            }
        }


        Map<Integer,V> reversed = new HashMap<>();
        extendedGraph.getMappedVertex().forEachKeyValue( (k,v) -> {
            reversed.put(v, k);
        });

        var result = new ArrayList<Community<V>>();
        communityMap.forEachKeyValue((k, v) -> {
            List<V> vertexes = new ArrayList<>();
            v.forEach(cv -> vertexes.add(reversed.get(cv)));
            result.add(new DefaultCommunity<>(reversed.get(k), vertexes));
        });

        return result;
    }
}
