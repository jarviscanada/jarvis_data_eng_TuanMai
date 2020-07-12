package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrintLetterWithNumberTest {

    @Test
    public void printLetterNumber() {
        PrintLetterWithNumber prwn = new PrintLetterWithNumber();

        assertEquals("a97b98c99e101e101", prwn.printLetterNumber("abcee"));
    }
}