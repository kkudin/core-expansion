package com.omvoid.community.corexp;

import com.omvoid.community.Community;
import com.omvoid.community.CommunityAlgorithm;
import com.omvoid.community.DefaultCommunity;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
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


    public <V,E> List<Community<V>> computeCommunities(Graph<V,E> graph) {

        var extendedGraph = new ExtendedGraph<>(graph);

        edgeWeightProcessor.calculateWeight(extendedGraph);
        vertexWeightProcessor.calculateWeight(extendedGraph);

        var communityMap = coresFinder.find(extendedGraph);
        IntArrayList unclassifedVertexes = new IntArrayList();

        extendedGraph.getMappedVertex().values().forEach( v -> {
            if(!communityMap.containsKey(v)) {
                unclassifedVertexes.add(v);
            }
        });

        communityMap.values()
                .stream()
                .filter(l -> l.size() > 0)
                .forEach(unclassifedVertexes::removeAll);

        IntArrayList foundVertexes = new IntArrayList();

        while(true) {
            unclassifedVertexes.forEach(v -> {
                int foundCommunity = closesVertexFinder.find(communityMap, v, extendedGraph);
                if(foundCommunity != -1) {
                    foundVertexes.add(v);
                    communityMap.get(foundCommunity).add(v);
                }
            });

            if(foundVertexes.size() == 0) {
                break;
            }
            unclassifedVertexes.removeAll(foundVertexes);
            foundVertexes.clear();
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
