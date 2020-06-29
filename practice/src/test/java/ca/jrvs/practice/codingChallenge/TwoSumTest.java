package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoSumTest {

    private TwoSum twoSum = new TwoSum();

    @Test
    public void twoSumBrute() {
        System.out.println("Test case: Testing the twoSumBrute method.");
        int[] array = new int[] { 5, 1, 4, 2, 3 };
        int[] result = twoSum.twoSumBrute(array, 9);
        assertEquals(5, result[0]);
        assertEquals(4, result[1]);
    }

    @Test
    public void twoSumSort() {
        System.out.println("Test case: Testing the twoSumSort method.");
        int[] array = new int[] { 5, 1, 4, 2, 3 };
        int[] result = twoSum.twoSumBrute(array, 9);
        assertEquals(5, result[0]);
        assertEquals(4, result[1]);
    }

    @Test
    public void twoSumHashMap() {
        System.out.println("Test case: Testing the twoSumHashMap method.");
        int[] array = new int[] { 5, 1, 4, 2, 3 };
        int[] result = twoSum.twoSumBrute(array, 9);
        assertEquals(5, result[0]);
        assertEquals(4, result[1]);
    }
}