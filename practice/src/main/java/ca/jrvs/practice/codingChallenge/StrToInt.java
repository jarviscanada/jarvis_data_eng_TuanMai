package ca.jrvs.practice.codingChallenge;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

/**
 * String to Integer (atoi) Coding Challenge
 * Ticket URL : https://www.notion.so/String-to-Integer-atoi-0bd28daae2784c52989ba51d486b100c
 */

public class StrToInt {

    final Logger logger = LoggerFactory.getLogger(StrToInt.class);

    /**
     * Approach 1
     * Using Java parsing method
     * Assuming the parameter is a string of only numbers and no letters
     * Time Complexity is O(n) where n is the number of characters in the string parameter
     * The parseInt traverses the string once
     * @param string
     * @return
     */
    public int atoiParse (String string) {
        BasicConfigurator.configure(); // Create configuration for log4j
        string = string.trim().replaceAll("\\s",""); // Trim off leading and ending white space as well as any space in between
        //Integer tempInt = null;

        try {
            Integer tempInt = Integer.parseInt(string);
            return tempInt;

        } catch (NumberFormatException ex) {
            logger.error(ex.getMessage(), ex);
            return -1;
        }
        //return tempInt;
    }

    /**
     * Approach 2
     * Assuming the string contains numbers
     * Time complexity is: O(n)
     * Only traverses the string one time, n is the number of characters in the string
     * @param string
     * @return
     */
    public int atoiAscii (String string) {

        // Ignore white space and spaces
        string = string.trim().replaceAll("\\s", "");

        int i = 0;
        int result = 0;
        while (i < string.length()) { // Go through each char of the string
            if (string.charAt(i) >= '0' && string.charAt(i) <= '9') {
                result = result + (string.charAt(i) - '0');
            }
            i++;
        }

        return result;
    }

}
