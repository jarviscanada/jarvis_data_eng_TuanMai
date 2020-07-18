package ca.jrvs.practice.codingChallenge;

import java.util.*;

/**
 * Duplicate Characters Coding Challenge
 * Ticket URL : https://www.notion.so/Duplicate-Characters-1801737c945c4ae187c80a6f2af0428d
 */
public class DuplicateCharacters {

    /**
     * Approach 1
     * Time complexity : O(n)
     * Traverses the string only once
     */
    public Set<Character> checkDupChars(String str){
        Map<Character, Integer> dupMap = new HashMap<Character, Integer>();
        char[] tempCharArr = str.toCharArray();
        Set<Character> results = new HashSet<>();

        for (Character character : tempCharArr) {
            if (dupMap.containsKey(character)){
                dupMap.put(character, dupMap.get(character) + 1);
                if (dupMap.get(character) > 1) {
                    results.add(character);
                }
            } else {
                dupMap.put(character, 1);
            }
        }

        return results;
    }
}
