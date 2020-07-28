package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidPalindromeTest {

    ValidPalindrome validPalindrome = new ValidPalindrome();
    @Test
    public void isPalindromePointers() {
        assertTrue(validPalindrome.isPalindromePointers("racecar"));
        assertFalse(validPalindrome.isPalindromePointers("racecars"));
    }

    @Test
    public void isPalindromeRecursion() {
        assertTrue(validPalindrome.isPalindromeRecursion("racecar"));
        assertFalse(validPalindrome.isPalindromeRecursion("racecars"));
    }
}