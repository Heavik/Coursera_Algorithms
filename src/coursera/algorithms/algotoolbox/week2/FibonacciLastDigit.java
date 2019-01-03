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