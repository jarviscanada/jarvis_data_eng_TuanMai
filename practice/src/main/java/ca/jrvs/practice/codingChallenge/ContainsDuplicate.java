package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Contains Duplicate Coding Challenge
 * Ticket URL : https://www.notion.so/Contains-Duplicate-2c9b05f58612410eb0813d89b81f5382
 */
public class ContainsDuplicate {

    /**
     * Approach 1
     * Brute Force solution: if we create a set and store numbers into it, then compare with the next number, check for duplicate, if yes, save in result set
     * Very inefficient solution as it has many nested loops. We iterate through two different array and set.
     * Time complexity is O(n^2)
     * @param intArr
     * @return
     */
    public Set<Integer> containsDuplicateBrute (int[] intArr) {
        Set<Integer> tempSet = new HashSet<Integer>();
        Set<Integer> resultSet = new HashSet<Integer>();
        for (int i = 0; i < intArr.length; i++) {
            if (tempSet.isEmpty()){
                tempSet.add(intArr[i]);
            }
            for (int number : tempSet ) {
                if (number == intArr[i]) {
                    resultSet.add(intArr[i]);
                }

            }
            tempSet.add(intArr[i]);
        }
        return resultSet;
    }

    /**
     * Approach 2
     * Utilizing sets and Java set.contains, able to condense and reduce the amount of for loops
     * Time complexity is O(n) as we only traverse the list once
     * @param integerList
     * @return
     */
    public Set<Integer> containsDuplicateSets (List<Integer> integerList) {
        Set<Integer> resultSet = new HashSet<Integer>();
        Set<Integer> tempSet = new HashSet<Integer>();
        for (Integer number : integerList) {
            if (tempSet.contains(number)) {
                resultSet.add(number);
            }
        }
        return resultSet;
    }
}
