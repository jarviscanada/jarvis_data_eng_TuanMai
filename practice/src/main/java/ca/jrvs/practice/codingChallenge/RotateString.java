package ca.jrvs.practice.codingChallenge;

/**
 * Rotate String Coding Challenge
 * Ticket URL : https://www.notion.so/Rotate-String-9eab4cf8b7ec4ade9aab401e9fd3e1ec
 */
public class RotateString {

    /**
     * Approach 1
     * Assuming that the rotation of string by shift number is counterclockwise
     * - Can rotate to the right by inputing (string.length - shiftNum) for shiftNum param
     * Time complexity is O(n) where n is the numbers of substrings
     * Linear time complexity because of creating a copy of the string
     * @param string
     * @param shiftNum
     * @return
     */
    public String rotateString(String string, int shiftNum) {
        String tempStr = string.substring(shiftNum) + string.substring(0, shiftNum);
        return tempStr;
    }
}
