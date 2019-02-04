/* Maximum Amount of Gold
 * Description: Given 𝑛 gold bars, find the maximum weight of gold that fits into a bag of capacity 𝑊.
 *
 * Input: The first line of the input contains the capacity 𝑊 of a knapsack and the number 𝑛 of bars of gold. 
 * The next line contains 𝑛 integers 𝑤0,𝑤1,...,𝑤[𝑛−1] defining the weights of the bars of gold.
 *
 * Output: Output the maximum weight of gold that fits into a knapsack of capacity 𝑊.
 *
 * Constraints: 1 ≤ 𝑊 ≤ 104; 1 ≤ 𝑛 ≤ 300; 0 ≤ 𝑤0,...,𝑤[𝑛−1] ≤ 10^5.
 */
package coursera.algorithms.algotoolbox.week6;

import java.util.*;

public class Knapsack {

    static int optimalWeight(int totalWeight, int[] weights) {
        int[][] computedWeights = new int[weights.length + 1][totalWeight + 1];

        for (int i = 0; i < weights.length; i++) {
            for (int currentWeight = 1; currentWeight <= totalWeight; currentWeight++) {
                computedWeights[i + 1][currentWeight] = computedWeights[i][currentWeight];
                if (weights[i] <= currentWeight) {
                    int val = computedWeights[i][currentWeight - weights[i]] + weights[i];
                    if (computedWeights[i + 1][currentWeight] < val) {
                        computedWeights[i + 1][currentWeight] = val;
                    }
                }
            }
        }

        return computedWeights[weights.length][totalWeight];
    }

    static int greedyWeight(int W, int[] w) {
        int result = 0;
        for (int i = 0; i < w.length; i++) {
            if (result + w[i] <= W) {
                result += w[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(W, w));
    }
}
