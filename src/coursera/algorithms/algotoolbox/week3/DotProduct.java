package coursera.algorithms.algotoolbox.week3;

import java.util.*;

public class DotProduct {
    
    private static void reverseSort(int[] arr) {
        
        for(int i = 0; i < arr.length - 1; i++) {
        
            int maxIdx = i;
            
            for(int j = i+1; j < arr.length;j++) {
                if(arr[j] > arr[maxIdx]) {
                    maxIdx = j;
                }
            }
            
            int temp = arr[maxIdx];
            arr[maxIdx] = arr[i];
            arr[i] = temp;
        }
    }
    
    private static long maxDotProduct(int[] a, int[] b) {
        
        reverseSort(a);
        reverseSort(b);
        
        long result = 0;
        for (int i = 0; i < a.length; i++) {
            result += (long)a[i] * (long)b[i];
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }
        System.out.println(maxDotProduct(a, b));
    }
}