package ca.jrvs.practice.codingChallenge;

/**
 * Print Letters with Numbers Coding Challenge
 * Ticket URL : https://www.notion.so/Print-letter-with-number-4e76c7a9b9c74c93a1d22fc2860b9426
 */
public class PrintLetterWithNumber {

    /**
     * Approach 1
     * input contains ASCII chars, lower/upper letters, no white spaces
     * Java Collection not allowed.
     * Time Complexity : O(n)
     * Traverse the string once
     * @param str
     * @return
     */
    public String printLetterNumber (String str) {

        String result = "";

        // String will be ASCII only lower/upper case letters
        // Print number after letter
        char[] charArray = str.toCharArray();

        for (char character : charArray) {
            int tempInt = character;
            result = result + character + tempInt;
        }

        return result;
    }
}
