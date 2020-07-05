package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.dataStructure.map.HashJMap;
import ca.jrvs.practice.dataStructure.map.JMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CompareMapsTest {

    CompareMaps compareMaps = new CompareMaps();

    @Test
    public void compareMapsCollections() {
        Map<Integer, String> map1 = new HashMap<>();
        map1.put(2, "John Smith");
        Map<Integer, String> map2 = new HashMap<>();
        map2.put(2, "John Smith");

        assertTrue(compareMaps.compareMapsCollections(map1, map2));

        map2.put(3, "Lisa Smith");
        assertFalse(compareMaps.compareMapsCollections(map1, map2));
    }

    @Test
    public void compareJmaps() {
        JMap<Integer, String> map1 = new HashJMap<>();
        map1.put(2, "John Smith");
        JMap<Integer, String> map2 = new HashJMap<>();
        map2.put(2, "John Smith");

        assertTrue(compareMaps.compareJmaps(map1, map2));

        map2.put(3, "Lisa Smith");
        assertFalse(compareMaps.compareJmaps(map1, map2));
    }
}