package ca.jrvs.practice.codingChallenge;

/**
 * Count Primes Coding Challenge
 * Ticket URL : https://www.notion.so/Count-Primes-c6f98224681147febe75dbc77ea12777
 */
public class CountPrimes {

    /**
     * Approach 1
     * Time complexity : O(n)
     * first for loop is O(n), check is constant, inner for loop runs O(sqrt n) times
     * Thus, applying the Big O Notation , we get O(n)
     * @param num
     * @return
     */
    public int countPrimes(int num) {
        // Check if the number entered is greater than 2
        // if not, no primes
        // We also know that no even numbers can be prime except 2

        int result = 0;
        boolean[] notPrime = new boolean[num];

        // Start index at 2
        for (int i = 2; i < num; i++) {
            if (!notPrime[i]) {
                result++;

                for (int j = 2; i * j < num; j++) {
                    notPrime[i * j] = true;
                }
            }
        }

        return result;
    }


}
