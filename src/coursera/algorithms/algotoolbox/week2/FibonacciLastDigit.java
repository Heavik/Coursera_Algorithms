/* Last Digit of a Large Fibonacci Number
 * Description: Given an integer 𝑛, find the last digit of the 𝑛th Fibonacci number 𝐹𝑛 (that is, 𝐹𝑛 mod 10). 
 *
 * Input: The input consists of a single integer 𝑛.
 * Output: Output the last digit of 𝐹𝑛.
 * Constraints: 0 ≤ 𝑛 ≤ 10^7.
 */
package coursera.algorithms.algotoolbox.week2;

import java.util.*;

public class FibonacciLastDigit {

    private static int getFibonacciLastDigit(int n) {
        if (n <= 1) {
            return n;
        }

        int[] fibonacciLastDigits = new int[n + 1];
        fibonacciLastDigits[0] = 0;
        fibonacciLastDigits[1] = 1;

        for (int i = 2; i <= n; i++) {
            fibonacciLastDigits[i] = (fibonacciLastDigits[i - 1] + fibonacciLastDigits[i - 2]) % 10;
        }

        return fibonacciLastDigits[n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = getFibonacciLastDigit(n);
        System.out.println(c);
    }
}
