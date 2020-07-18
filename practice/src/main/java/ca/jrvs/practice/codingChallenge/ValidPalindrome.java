package ca.jrvs.practice.codingChallenge;

/**
 * Valid Palindrome Coding Challenge
 * Ticket URL : https://www.notion.so/Valid-Palindrome-bd34bb2c950346ebb5e6cdffa0149fd4
 */
public class ValidPalindrome {

    /**
     * Approach 1 - Brute force
     * Two pointers
     * Checking the string from both sides
     * Time Complexity : O(n)
     * Traverses the string once
     */
    public boolean isPalindromePointers (String str) {
        int left = 0, right = str.length() - 1;

        while (left < right) {

            // Check to see if the left character has not passed the right
            if (left < right) {
                if (Character.toUpperCase(str.charAt(left)) != Character.toUpperCase(str.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }

        }
        return true;
    }

    /**
     * Approach 2 - Recursion
     * Time Complexity : O(n)
     * Traverse the string once
     */
    public boolean isPalindromeRecursion(String str) {
        return isSubPalindrome(str, 0, str.length() -1, false);
    }

    private boolean isSubPalindrome(String str, int left, int right, boolean used) {
        if (left >= right) { // base case
            return true;
        }

        if (str.charAt(left) != str.charAt(right)) { // equal
            if (used == false) {
                if (isSubPalindrome(str, left + 1, right, true))
                {
                    return true; // test left deletion
                }
                else if (isSubPalindrome(str, left, right - 1, true)) {
                    return false; // test right deletion
                }
            } else {
                return false;
            }
        }

        return isSubPalindrome(str, left + 1, right - 1, used);
    }
}
