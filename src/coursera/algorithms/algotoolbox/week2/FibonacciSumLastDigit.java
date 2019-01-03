package coursera.algorithms.algotoolbox.week2;

import java.util.*;

public class FibonacciSumLastDigit {
    private static long getFibonacciSum(long n) {
        if (n <= 1)
            return n;

        final int PISANO_PERIOD = 60;
        
        int previous = 0, current = 1, next = 1;
        
        int[] pisanoNumbers = new int[PISANO_PERIOD];
        pisanoNumbers[0] = 0;
        pisanoNumbers[1] = 1;
                
        long sum = 0;
        
        for(int period = 2;period < PISANO_PERIOD; period++) {
            next = (previous + current) % 10;
            previous = current;
            current = next;
            pisanoNumbers[period] = current;
        }
        
        n = n % PISANO_PERIOD;

        for(int i = 0; i <= n; ++i) {
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