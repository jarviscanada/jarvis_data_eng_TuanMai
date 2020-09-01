package ca.jrvs.practice.codingChallenge;

/**
 * Remove Element Coding Challenge
 * Ticket URL : https://www.notion.so/Remove-Element-a7534d63fd7d4ab38e6158ac15bcd923
 */
public class RemoveElement {

    /**
     * Approach 1 - Two pointers
     * Time Complexity : O(n)
     * Traverse the array once, n amount in array
     * @param intArray
     * @param num
     * @return
     */
    public int removeElement(int[] intArray, int num) {

        int result = 0;

        for (int idx = 0; idx < intArray.length; idx++) {
            if (intArray[idx] != num) {
                intArray[result] = intArray[idx];
                result++;
            }
        }

        return result;
    }
}
