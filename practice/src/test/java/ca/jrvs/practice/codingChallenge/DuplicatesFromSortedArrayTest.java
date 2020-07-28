package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DuplicatesFromSortedArrayTest {

    @Test
    public void dupsFromSortedArray() {
        DuplicatesFromSortedArray dfsa = new DuplicatesFromSortedArray();
        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);

        assertEquals(expected, dfsa.dupsFromSortedArray(new int[] { 1, 1, 2, 2, 3, 3}));
    }
}