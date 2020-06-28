package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ContainsDuplicateTest {

    @Test
    public void containsDuplicateBrute() {
        ContainsDuplicate containsDuplicate = new ContainsDuplicate();
        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        assertEquals(set, containsDuplicate.containsDuplicateBrute(new int[] { 1, 1, 1, 2, 3, 2, 2, 4 }));
    }

    @Test
    public void containsDuplicateSets() {
        ContainsDuplicate containsDuplicate = new ContainsDuplicate();
        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        assertEquals(set, containsDuplicate.containsDuplicateBrute(new int[] { 1, 1, 1, 2, 3, 2, 2, 4 }));
    }
}