/* Binary Search
 * Description: The goal in this code problem is to implement the binary search algorithm.
 *
 * Input: The first line of the input contains an integer 𝑛 and a sequence 𝑎0 < 𝑎1 <...< 𝑎[𝑛−1]
 * of 𝑛 pairwise distinct positive integers in increasing order. The next line contains an integer 𝑘 and 𝑘
 * positive integers 𝑏0, 𝑏1,..., 𝑏[𝑘−1].
 *
 * Output: For all 𝑖 from 0 to 𝑘 − 1, output an index 0 ≤ 𝑗 ≤ 𝑛 − 1 such that 𝑎𝑗 = 𝑏𝑖 or −1 if there is no such index.
 *
 * Constraints: 1 ≤ 𝑛, 𝑘 ≤ 10^4; 1 ≤ 𝑎𝑖 ≤ 10^9 for all 0 ≤ 𝑖 < 𝑛; 1 ≤ 𝑏𝑗 ≤ 10^9 for all 0 ≤ 𝑗 < 𝑘;
 */
package coursera.algorithms.algotoolbox.week4;

import java.io.*;
import java.util.*;

public class BinarySearch {

    static int binarySearch(int key, int low, int high, int[] arr) {
        if(low > high) {
            return -1;
        }
        
        int mid = low + (high - low) / 2;
        int midValue = arr[mid];
        if(midValue == key) {
            return mid;
        }
        
        if(midValue > key) {
            return binarySearch(key, low, mid - 1, arr);
        }
        
        return binarySearch(key, mid + 1, high, arr);
    }
    
    static int binarySearch(int[] a, int x) {
        
        return binarySearch(x, 0, a.length - 1, a);
    }

    static int linearSearch(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int m = scanner.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
          b[i] = scanner.nextInt();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            //replace with the call to binarySearch when implemented
            sb.append(binarySearch(a, b[i]));
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