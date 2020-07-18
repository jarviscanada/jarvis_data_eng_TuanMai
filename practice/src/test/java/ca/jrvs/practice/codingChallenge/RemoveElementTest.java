package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class RemoveElementTest {

    @Test
    public void removeElement() {
        RemoveElement removeElement = new RemoveElement();

        assertEquals(5, removeElement.removeElement(new int[] { 1, 2, 3, 4, 4, 5, 6 }, 4));
    }
}