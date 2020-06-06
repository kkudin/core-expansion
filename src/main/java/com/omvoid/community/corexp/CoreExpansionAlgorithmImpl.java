package com.omvoid.community.corexp;

import com.omvoid.community.models.CoreExpansionResults;
import com.omvoid.community.models.DefaultCoreExpansionResults;
import org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.Graph;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CoreExpansionAlgorithmImpl implements CommunityAlgorithm {

    private final VertexWeightProcessor vertexWeightProcessor = new VertexWeightProcessor();
    private final EdgeWeightProcessor edgeWeightProcessor = new EdgeWeightProcessor();
    private final CoresFinder coresFinder = new CoresFinder();
    private final ClosesVertexFinder closesVertexFinder = new ClosesVertexFinder();


    public <V,E> CoreExpansionResults<V> computeCommunities(Graph<V,E> graph) throws InterruptedException {

        var extendedGraph = new ExtendedGraph<>(graph);

        edgeWeightProcessor.calculateWeight(extendedGraph);
        vertexWeightProcessor.calculateWeight(extendedGraph);

        var communityMap = coresFinder.find(extendedGraph);
        var coreVertexes = new IntObjectHashMap<>(communityMap);

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

        final AtomicInteger foundVertexes = new AtomicInteger(1);

        while (foundVertexes.get() > 0) {
            foundVertexes.set(0);
            closesVertexFinder.findAll(
                    vertexCommMapping, extendedGraph, unclassifiedVertexes
            ).forEachKeyValue(
                    (k, v) -> {
                        if (v != -1) {
                            unclassifiedVertexes.remove(k);
                            communityMap.get(v).add(k);
                            vertexCommMapping.put(k, v);
                            foundVertexes.incrementAndGet();
                        }
                    }
            );

            if(foundVertexes.get() == 0) {
                break;
            }
        }

        Map<Integer,V> reversed = new HashMap<>();
        extendedGraph.getMappedVertex().forEachKeyValue( (k,v) -> {
            reversed.put(v, k);
        });

        var communities = new HashMap<V, Set<V>>();
        var newCores = new HashMap<V, Set<V>>();
        communityMap.forEachKeyValue((k, v) -> {
            Set<V> vertexes = new HashSet<>();
            Set<V> cores = new HashSet<>();
            v.forEach(cv -> vertexes.add(reversed.get(cv)));
            coreVertexes.get(k).forEach(cv -> cores.add(reversed.get(cv)));
            communities.put(reversed.get(k), vertexes);
            newCores.put(reversed.get(k), cores);
        });

        var vCommMapping = new HashMap<V, V>();
        vertexCommMapping.forEachKeyValue((k, v) -> vCommMapping.put(reversed.get(k), reversed.get(v)));

        return new DefaultCoreExpansionResults<>(
                communities, vCommMapping, newCores
        );
    }
}
