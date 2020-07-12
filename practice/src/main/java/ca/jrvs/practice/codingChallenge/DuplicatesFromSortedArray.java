package ca.jrvs.practice.codingChallenge;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Duplicates from Sorted Array Coding Challenge
 * Ticket URL : https://www.notion.so/Duplicates-from-Sorted-Array-8b2063f78248444fb7f10f7e1e812623
 */
public class DuplicatesFromSortedArray {

    /**
     * Approach 1 - Two pointers
     * Take an array input
     * Check for duplicates
     * if dup, add to a set
     * return set of all dup integers
     * Time Complexity : O(n)
     * Since this is a sorted array, we iterate through the array once because the next element will tell us if there
     * is a duplicate or not
     * @param nums
     * @return
     */
    public Set<Integer> dupsFromSortedArray (int[] nums) {

        Set<Integer> results = new HashSet<>();

        int idx = 0;
        for (int secondIdx = 1; secondIdx < nums.length; secondIdx++) {
            if (nums[secondIdx] != nums[idx]) {
                idx++;
                nums[idx] = nums[secondIdx];
            } else {
                results.add(nums[idx]);
            }
        }

        return results;
    }
}
