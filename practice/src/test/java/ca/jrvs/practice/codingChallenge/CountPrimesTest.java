package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountPrimesTest {

    @Test
    public void countPrimes() {
        CountPrimes countPrimes = new CountPrimes();

        assertEquals(15, countPrimes.countPrimes(50));
    }
}