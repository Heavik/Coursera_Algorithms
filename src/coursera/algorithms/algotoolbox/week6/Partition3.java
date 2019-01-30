package coursera.algorithms.algotoolbox.week6;

import java.util.*;

public class Partition3 {

    private static int[] filterArr(int[][] dp, int[] arr, int sum) {
        List<Integer> filteredNumbers = new ArrayList<>();

        for (int currentNum = arr.length; currentNum > 0; currentNum--) {
            if (arr[currentNum - 1] <= sum) {
                int sumWithNumber = dp[currentNum - 1][sum - arr[currentNum - 1]] + arr[currentNum - 1];
                int sumWithoutNumber = dp[currentNum - 1][sum];
                if (sumWithNumber > sumWithoutNumber) {
                    filteredNumbers.add(arr[currentNum - 1]);
                    sum -= arr[currentNum - 1];
                }
            }
        }

        int[] result = new int[filteredNumbers.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = filteredNumbers.get(i);
        }

        return result;
    }

    private static int[][] knapsack(int[] arr, int sum) {
        int[][] dp = new int[arr.length + 1][sum + 1];

        for (int i = 0; i < arr.length; i++) {
            for (int currentSum = 1; currentSum <= sum; currentSum++) {
                dp[i + 1][currentSum] = dp[i][currentSum];
                if (arr[i] <= currentSum) {
                    int val = dp[i][currentSum - arr[i]] + arr[i];
                    if (dp[i + 1][currentSum] < val) {
                        dp[i + 1][currentSum] = val;
                    }
                }
            }
        }

        return dp;
    }

    private static int partition3(int[] A) {
        final int parts = 3;
        if (A.length < parts) {
            return 0;
        }

        int totalSum = 0;
        for (int i = 0; i < A.length; i++) {
            totalSum += A[i];
        }

        if (totalSum % parts != 0) {
            return 0;
        }

        int partSum = totalSum / parts;

        int[][] firstPart = knapsack(A, partSum);
        int[] filteredFirstPart = filterArr(firstPart, A, partSum);
        int[][] secondPart = knapsack(filteredFirstPart, partSum);

        return secondPart[filteredFirstPart.length][partSum] == partSum ? 1 : 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }
        System.out.println(partition3(A));
    }
}
