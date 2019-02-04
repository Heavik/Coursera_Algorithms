/* Last Digit of the Sum of Fibonacci Numbers Again
 * Description: Given two non-negative integers 𝑚 and 𝑛, where 𝑚 ≤ 𝑛, find the last digit of the sum 𝐹𝑚 + 𝐹𝑚+1 + ··· + 𝐹𝑛.
 * Now, we would like to find the last digit of a partial sum of Fibonacci numbers: 𝐹𝑚 + 𝐹𝑚+1 + ··· + 𝐹𝑛.
 *
 * Input: The input consists of two non-negative integers 𝑚 and 𝑛 separated by a space.
 * Output: Output the last digit of 𝐹𝑚 + 𝐹𝑚+1 + ··· + 𝐹𝑛.
 * Constraints: 0 ≤ 𝑚 ≤ 𝑛 ≤ 10^18.
 */
package coursera.algorithms.algotoolbox.week2;

import java.util.*;

public class FibonacciPartialSum {

    private static long getFibonacciPartialSum(long from, long to) {

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

        from = from % PISANO_PERIOD;
        to = to % PISANO_PERIOD;

        for (int i = (int) from; i <= to; ++i) {
            sum += pisanoNumbers[i];
        }

        return sum % 10;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        System.out.println(getFibonacciPartialSum(from, to));
    }
}
