package com.omvoid.community;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;

import java.util.ArrayList;
import java.util.List;

public interface Community<V> {

    V getCore();
    List<V> getVertexes();

}
