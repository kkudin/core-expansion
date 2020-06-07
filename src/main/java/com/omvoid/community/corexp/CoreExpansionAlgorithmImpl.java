package com.omvoid.community.corexp;

import com.omvoid.community.*;
import org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.jgrapht.Graph;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CoreExpansionAlgorithmImpl implements CommunityAlgorithm {

    private final VertexWeightProcessor vertexWeightProcessor = new VertexWeightProcessor();
    private final EdgeWeightProcessor edgeWeightProcessor = new EdgeWeightProcessor();
    private final CoresFinder coresFinder = new CoresFinder();
    private final ClosesVertexFinder closesVertexFinder = new ClosesVertexFinder();


    public <V,E> CoreExpansionResults<V> computeCommunities(Graph<V,E> graph) throws InterruptedException {
        System.out.println("Start main algorithm.");
        var extendedGraph = new ExtendedGraph<>(graph);
        System.out.printf(
                "Input graph has %d edges and %d vertices. Converting to weighted graph done.\n",
                extendedGraph.getFastutilGraph().vertexSet().size(),
                extendedGraph.getFastutilGraph().edgeSet().size()
        );

        LocalTime now;

        System.out.println("Start edge weights processing...");
        now = LocalTime.now();
        edgeWeightProcessor.calculateWeight(extendedGraph);
        System.out.printf("Done in %d seconds.\n", now.until(LocalTime.now(), ChronoUnit.SECONDS));

        System.out.println("Start vertex weights processing...");
        now = LocalTime.now();
        vertexWeightProcessor.calculateWeight(extendedGraph);
        System.out.printf("Done in %d seconds.\n", now.until(LocalTime.now(), ChronoUnit.SECONDS));

        System.out.println("Looking for cores...");
        now = LocalTime.now();
        var communityMap = coresFinder.find(extendedGraph);
        System.out.printf(
                "Done in %d seconds. Found %d cores.\n",
                now.until(LocalTime.now(), ChronoUnit.SECONDS),
                communityMap.keySet().size()
        );

        var coreVertexes = new IntObjectHashMap<IntHashSet>(communityMap.size());
        IntHashSet unclassifiedVertexes = new IntHashSet(graph.vertexSet().size());
        IntIntHashMap vertexCommMapping = new IntIntHashMap(graph.vertexSet().size());

        communityMap.forEachKeyValue((k, v) -> {
            coreVertexes.put(k, new IntHashSet(v));
            v.forEach(cv -> vertexCommMapping.put(cv, k));
        });

        extendedGraph.getMappedVertex().values().forEach( v -> {
            if(!vertexCommMapping.containsKey(v)) {
                vertexCommMapping.put(v, -1);
                unclassifiedVertexes.add(v);
            }
        });

        final AtomicInteger foundVertexes = new AtomicInteger(1);

        while (foundVertexes.get() > 0) {
            now = LocalTime.now();
            System.out.printf(
                    "Start an iteration. There are %d unclassified vertexes yet.\n",
                    unclassifiedVertexes.size()
            );
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

            System.out.printf("Done an iteration in %d seconds.\n", now.until(LocalTime.now(), ChronoUnit.SECONDS));
        }

        System.out.printf(
                "The algorithm finished. %d vertex hadn't been classified.\n",
                unclassifiedVertexes.size()
        );

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
