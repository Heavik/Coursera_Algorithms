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
