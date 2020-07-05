package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.dataStructure.map.HashJMap;
import ca.jrvs.practice.dataStructure.map.JMap;

import java.util.Map;

/**
 * Compare two Maps Coding Challenge
 * Ticket URL : https://www.notion.so/How-to-compare-two-maps-de3ef4e7d27b4b689d3cbe1121a4a3f4
 */

public class CompareMaps {

    /**
     * Approach 1
     * Using Java Collection .equals API
     * Time Complexity : O(n)
     * Searching inlined list is O(n) operation, thus searching every pair in the map
     */
    public <K, V> boolean compareMapsCollections(Map<K, V> map1, Map<K, V> map2){
        return map1.equals(map2);
    }

    /**
     * Approach 2
     * Implement equals in HashJMap
     * Time Complexity : O(n)
     * Searching inlined list is O(n) operation, thus searching every pair in the map
     */
    public <K, V> boolean compareJmaps(JMap<K, V> map1, JMap<K, V> map2) {
        return map1.equals(map2);
    }
}
