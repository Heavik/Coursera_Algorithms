/* Last Digit of the Sum of Fibonacci Numbers
 * Description: Given an integer 𝑛, find the last digit of the sum 𝐹0 + 𝐹1 + · · · + 𝐹𝑛.
 * The goal in this problem is to find the last digit of a sum of the first 𝑛 Fibonacci numbers.
 * Instead of computing this sum in a loop, try to come up with a formula for 𝐹0 + 𝐹1 + 𝐹2 + ··· + 𝐹𝑛. 
 * For this, play with small values of 𝑛. Then, use a solution for the previous problem.
 *
 * Input: The input consists of a single integer 𝑛.
 * Output: Output the last digit of 𝐹0 + 𝐹1 + · · · + 𝐹𝑛.
 * Constraints: 0 ≤ 𝑛 ≤ 10^18.
 */
package coursera.algorithms.algotoolbox.week2;

import java.util.*;

public class FibonacciSumLastDigit {

    private static long getFibonacciSum(long n) {
        if (n <= 1) {
            return n;
        }

        final int PISANO_PERIOD = 60;

        int previous = 0, current = 1, next = 1;

        int[] pisanoNumbers = new int[PISANO_PERIOD];
        pisanoNumbers[0] = 0;
        pisanoNumbers[1] = 1;

        long sum = 0;

        for (int period = 2; period < PISANO_PERIOD; period++) {
            next = (previous + current) % 10;
            previous = current;
            current = next;
            pisanoNumbers[period] = current;
        }

        n = n % PISANO_PERIOD;

        for (int i = 0; i <= n; ++i) {
            sum += pisanoNumbers[i];
        }

        return sum % 10;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long s = getFibonacciSum(n);
        System.out.println(s);
    }
}
