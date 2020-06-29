package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class RotateStringTest {

    @Test
    public void rotateString() {
        System.out.println("Test case: Testing the rotateString method.");
        RotateString rotateString = new RotateString();
        assertEquals("rvisJa", rotateString.rotateString("Jarvis", 2));
    }
}