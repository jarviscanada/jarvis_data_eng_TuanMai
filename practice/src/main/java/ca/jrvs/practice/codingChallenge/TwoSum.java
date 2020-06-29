package ca.jrvs.practice.codingChallenge;


import java.util.Arrays;
import java.util.HashMap;

/**
 * Two Sum Coding Challenge
 * Ticket URL : https://www.notion.so/Two-Sum-05d5ea6f5b514bb885b5d22fdcd98d11
 */

public class TwoSum {

    /**
     * Approach 1
     * Brute force method
     * Go through each integer while comparing it against with every other number to check for a matching sum
     * Very inefficient as it uses two loops nested
     * Time complexity is O(n^2)
     */
    public int[] twoSumBrute (int[] intArr, int targetSum) {
        for (int idx = 0; idx < intArr.length; idx++) {
            for (int secIdx = idx + 1; secIdx < intArr.length; secIdx++)
                if (intArr[idx] + intArr[secIdx] == targetSum) {
                    return new int[] { intArr[idx], intArr[secIdx] };
                }
        }
        return null;
    }

    /**
     * Approach 2
     * Using Java's sorting algorithm
     * We can use a two pointer slide
     * Very efficient
     * Time Complexity is O(n*log(n))
     */
    public int[] twoSumSort (int[] intArr, int targetSum) {
        Arrays.sort(intArr);
        int left = 0;
        int right = intArr.length - 1;
        while (left < right){
            if (intArr[left] + intArr[right] == targetSum){
                return new int[] { intArr[left], intArr[right]};
            }
            else if(intArr[left] + intArr[right] < targetSum){
                left++;
            }
            else
                right--;
        }
        return null;
    }

    /**
     * Approach 3
     * Requirements: O(n)
     * Improving on brute force method, instead of iterating through the array twice,
     * we can use a HashMap to save the complement of each number (targetSum - element)
     * then check to see if the HashMap contains a complement to a number in the array
     */
    public int[] twoSumHashMap (int[] intArr, int targetSum) {
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

        for (int idx = 0; idx < intArr.length; idx++) {
            int complement = targetSum - intArr[idx];
            if(hashMap.containsKey(complement)){
                return new int[] { intArr[idx], hashMap.get(complement) };
            }
            else
                hashMap.put(intArr[idx], idx);
        }
        return null;
    }
}
