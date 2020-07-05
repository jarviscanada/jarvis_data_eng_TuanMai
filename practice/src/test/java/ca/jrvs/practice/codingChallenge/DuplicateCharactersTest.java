package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DuplicateCharactersTest {

    @Test
    public void checkDupChars() {

        DuplicateCharacters duplicateCharacters = new DuplicateCharacters();

        Set<Character> test = new HashSet<>();
        test.add('a');
        test.add('b');
        assertEquals(test, duplicateCharacters.checkDupChars("abcdbefa") );
    }
}