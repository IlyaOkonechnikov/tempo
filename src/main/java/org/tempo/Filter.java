package org.tempo;

import java.util.Arrays;
import java.util.function.IntPredicate;

public class Filter {

    public static Hierarchy filter(Hierarchy hierarchy, IntPredicate nodeIdPredicate) {
        int size = hierarchy.size();
        int[] filteredNodeIds = new int[size];
        int[] filteredDepths = new int[size];
        int filteredSize = 0;

        for (int i = 0; i < size; i++) {
            int nodeId = hierarchy.nodeId(i);
            int depth = hierarchy.depth(i);

            if (nodeIdPredicate.test(nodeId) && isAncestorValid(hierarchy, nodeIdPredicate, i)) {
                filteredNodeIds[filteredSize] = nodeId;
                filteredDepths[filteredSize] = depth;
                filteredSize++;
            }
        }

        return new ArrayBasedHierarchy(
                Arrays.copyOf(filteredNodeIds, filteredSize),
                Arrays.copyOf(filteredDepths, filteredSize)
        );
    }

    private static boolean isAncestorValid(Hierarchy hierarchy, IntPredicate nodeIdPredicate, int index) {
        for (int i = index - 1; i >= 0; i--) {
            int ancestorNodeId = hierarchy.nodeId(i);
            int ancestorDepth = hierarchy.depth(i);
            if (!nodeIdPredicate.test(ancestorNodeId) || ancestorDepth >= hierarchy.depth(index)) {
                return false;
            }
        }
        return true;
    }
}
