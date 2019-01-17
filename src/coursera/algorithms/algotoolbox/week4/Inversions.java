package coursera.algorithms.algotoolbox.week4;

import java.util.*;

public class Inversions {

    private static long merge(int[] arr, int left, int mid, int right) {
        long numberOfPairs = 0;
        
        int leftArrLength = mid - left + 1;
        int rightArrLength = right - mid;
        
        int[] leftArr = new int[leftArrLength];
        int[] rightArr = new int[rightArrLength];
        
        for(int i = 0; i < leftArrLength; i++) {
            leftArr[i] = arr[left + i];
        }
        
        for(int i = 0; i < rightArrLength; i++) {
            rightArr[i] = arr[mid + 1 + i];
        }
        
        int i = 0, j = 0;
        int k = left;
        
        while(i < leftArrLength && j < rightArrLength) {
            if(leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                for(int p = 0; p < leftArrLength; p++) {
                    if(leftArr[p] > rightArr[j]) {
                        numberOfPairs++;
                    }
                }
                j++;
            }
            k++;
        }
        
        while(i < leftArrLength) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }
        
        while(j < rightArrLength) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
        
        return numberOfPairs;
    }
    
    private static long getNumberOfInversions(int[] a, int left, int right) {
        long numberOfInversions = 0;
        if (right <= left) {
            return numberOfInversions;
        }
        int ave = (left + right) / 2;
        numberOfInversions += getNumberOfInversions(a, left, ave);
        numberOfInversions += getNumberOfInversions(a, ave + 1, right);
        numberOfInversions += merge(a, left, ave, right);
        return numberOfInversions;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        System.out.println(getNumberOfInversions(a, 0, a.length - 1));
    }
}