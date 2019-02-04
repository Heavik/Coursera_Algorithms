/* Improving Quick Sort
 * Description: To force the given implementation of the quick sort algorithm to efficiently process sequences with
 * few unique elements, your goal is replace a 2-way partition with a 3-way partition. That is, your new
 * partition procedure should partition the array into three parts: <𝑥 part, =𝑥 part, and >𝑥 part.
 *
 * Input: The first line of the input contains an integer 𝑛. The next line contains a sequence of 𝑛 integers 𝑎0, 𝑎1,...,𝑎[𝑛−1].
 *
 * Output: Output this sequence sorted in non-decreasing order.
 *
 * Constraints: 1 ≤ 𝑛 ≤ 10^5; 1 ≤ 𝑎𝑖 ≤ 10^9 for all 0 ≤ 𝑖 < 𝑛.
 */
package coursera.algorithms.algotoolbox.week4;

import java.io.*;
import java.util.*;

public class Sorting {

    private static Random random = new Random();

    private static void swap(int[] arr, int to, int from) {
        int t = arr[to];
        arr[to] = arr[from];
        arr[from] = t;
    }

    private static int[] partition3(int[] a, int l, int r) {
        int x = a[l];
        int m1 = l;
        for (int i = l + 1; i <= r; i++) {
            if (a[i] < x) {
                m1++;
                swap(a, i, m1);
            }
        }
        swap(a, l, m1);

        int m2 = m1;
        for (int i = m1 + 1; i <= r; i++) {
            if (a[i] == x) {
                m2++;
                swap(a, i, m2);
            }
        }
        swap(a, m1, m2);

        int[] m = {m1, m2};
        return m;
    }

    private static int partition2(int[] a, int l, int r) {
        int x = a[l];
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            if (a[i] <= x) {
                j++;
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        int t = a[l];
        a[l] = a[j];
        a[j] = t;
        return j;
    }

    private static void randomizedQuickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int k = random.nextInt(r - l + 1) + l;
        int t = a[l];
        a[l] = a[k];
        a[k] = t;
        int[] m = partition3(a, l, r);
        randomizedQuickSort(a, l, m[0] - 1);
        randomizedQuickSort(a, m[1] + 1, r);
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        randomizedQuickSort(a, 0, n - 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(a[i]);
            sb.append(" ");
        }

        System.out.println(sb.toString());
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
    }
}
