package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Valid Parentheses Coding Challenge
 * Ticket URL : https://www.notion.so/Valid-Parentheses-22b583bbb3d146f3b1fcf3cf502b5629
 */
public class ValidParentheses {

    /**
     * Approach 1
     * Using HashMap and Stack
     * Store the possible valid parentheses in a HashMap
     * Time Complexity : O(n)
     * Traverse the string once
     */
    public boolean isValidParentheses(String str) {
        // Assuming the valid parentheses are : (), [], {}
        Map<Character, Character> validParentheses = new HashMap<>();
        validParentheses.put('(', ')');
        validParentheses.put('[', ']');
        validParentheses.put('{', '}');

        Stack<Character> parenthesis = new Stack<>();

        for (int idx = 0; idx < str.length(); idx++) {
            char character = str.charAt(idx);

            if (validParentheses.containsKey(character)) {
                parenthesis.push(validParentheses.get(character));
            } else if (validParentheses.containsValue(character)) {
                if (parenthesis.isEmpty() || parenthesis.pop() != character) {
                    return false;
                }
            }
        }
        return parenthesis.isEmpty();
    }
}
