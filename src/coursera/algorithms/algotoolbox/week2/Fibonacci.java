package coursera.algorithms.algotoolbox.week2;

import java.util.Scanner;

public class Fibonacci {

    private static long calc_fib(int n) {
        if (n <= 1) {
            return n;
        }

        long[] fibonacciNumbers = new long[n + 1];
        fibonacciNumbers[0] = 0;
        fibonacciNumbers[1] = 1;

        for (int i = 2; i <= n; i++) {
            fibonacciNumbers[i] = fibonacciNumbers[i - 1] + fibonacciNumbers[i - 2];
        }

        return fibonacciNumbers[n];
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        System.out.println(calc_fib(n));
    }
}
