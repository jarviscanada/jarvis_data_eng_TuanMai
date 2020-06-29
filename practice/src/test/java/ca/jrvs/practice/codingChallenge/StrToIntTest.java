package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class StrToIntTest {

    StrToInt strToInt = new StrToInt();

    @Test
    public void atoiParse() {

        assertEquals(123, strToInt.atoiParse(" 1 2 3"));
    }

    @Test
    public void atoiAscii() {
        assertEquals(32321, strToInt.atoiParse(" 323 2 1"));
    }
}