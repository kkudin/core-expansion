package com.omvoid.community.corexp;

import com.omvoid.community.Community;
import com.omvoid.community.CommunityAlgorithm;
import com.omvoid.community.DefaultCommunity;
import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CoreExpansionAlgorithmImpl implements CommunityAlgorithm {

    private VertexWeightProcessor vertexWeightProcessor = new VertexWeightProcessor();
    private EdgeWeightProcessor edgeWeightProcessor = new EdgeWeightProcessor();
    private CoresFinder coresFinder = new CoresFinder();
    private ClosesVertexFinder closesVertexFinder = new ClosesVertexFinder();


    public List<Community> getCommunities(Graph graph) {
        List<Community> result = new ArrayList<>();

        ExtendedGraph extendedGraph = new ExtendedGraph(graph);

        vertexWeightProcessor.calculateWeight(extendedGraph);
        edgeWeightProcessor.calculateWeight(extendedGraph);

        Map<Integer, List<Integer>> communityMap = coresFinder.find(extendedGraph);
        List<Integer> unclassifedVertexes = new ArrayList<>();

        extendedGraph.getMappedVertex().values().forEach( v -> {
            if(!communityMap.containsKey(v)) {
                unclassifedVertexes.add(v);
            }
        });

        communityMap.values()
                .stream()
                .filter(l -> l.size() > 0)
                .forEach(unclassifedVertexes::removeAll);

        List<Integer> foundVertexes = new ArrayList<>();

        while(true) {

            int found_count = 0;

            for(var v : unclassifedVertexes) {
                int foundCommunity = closesVertexFinder.find(communityMap.keySet(), v, extendedGraph);
                if(foundCommunity != -1) {
                    foundVertexes.add(v);
                    communityMap.get(foundCommunity).add(v);
                    found_count++;
                }
            }

            if(found_count == 0) {
                break;
            }
            unclassifedVertexes.removeAll(foundVertexes);
        }

        return communityMap.keySet().stream()
                .map(c ->  new DefaultCommunity(c, communityMap.get(c)))
                .collect(Collectors.toList());

    }
}
