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