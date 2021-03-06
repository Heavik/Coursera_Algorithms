/* Maximum Pairwise Product Problem
 * Description: Find the maximum product of two distinct numbers in a sequence of non-negative integers.
 * Given a sequence of non-negative integers a[1],...,a[n], compute max (a[i] * a[j])
 * Note that i and j should be different, though it may be the case that a[i] = a[j].
 *
 * Input: The first line contains an integer n. The next line contains n non-negative integers a[1],...,a[n] (separated by spaces).
 * Output: The maximum pairwise product.
 * Constraints: 2 <= n <= 2*10^5; 0 <= a[1],...,a[n] <= 2*10^5.
 */
package coursera.algorithms.algotoolbox.week1;

import java.util.*;
import java.io.*;

public class MaxPairwiseProduct {

    static long getMaxPairwiseProduct(long[] numbers) {
        int n = numbers.length;
        int index1 = 0;
        int index2 = 0;

        for (int i = 1; i < n; i++) {
            if (numbers[i] > numbers[index1]) {
                index1 = i;
            }
        }

        if (index1 == 0) {
            index2 = 1;
        }

        for (int i = 1; i < n; i++) {
            if (i != index1 && numbers[i] > numbers[index2]) {
                index2 = i;
            }
        }

        return numbers[index1] * numbers[index2];
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        long[] numbers = new long[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextLong();
        }
        System.out.println(getMaxPairwiseProduct(numbers));
    }

    static class FastScanner {

        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }
}
