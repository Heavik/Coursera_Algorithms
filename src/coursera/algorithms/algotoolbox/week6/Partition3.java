/* Partitioning Souvenirs
 * Description: You and two of your friends have just returned back home after visiting various countries. 
 * Now you would like to evenly split all the souvenirs that all three of you bought.
 *
 * Input: The first line contains an integer 𝑛. The second line contains integers 𝑣1, 𝑣2,...,𝑣𝑛 separated by spaces.
 *
 * Output: Output 1, if it possible to partition 𝑣1, 𝑣2,...,𝑣𝑛 into three subsets with equal sums, and 0 otherwise.
 *
 * Constraints: 1 ≤ 𝑛 ≤ 20, 1 ≤ 𝑣𝑖 ≤ 30 for all 𝑖.
 */
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
