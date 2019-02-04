/* Maximum Value of the Loot
 * Description: The goal of this code problem is to implement an algorithm for the fractional knapsack problem.
 *
 * Input: The first line of the input contains the number 𝑛 of items and the capacity 𝑊 of a knapsack.
 * The next 𝑛 lines define the values and weights of the items. The 𝑖-th line contains integers 𝑣𝑖 and 𝑤𝑖—the
 * value and the weight of 𝑖-th item, respectively.
 *
 * Output: Output the maximal value of fractions of items that fit into the knapsack. The absolute
 * value of the difference between the answer of your program and the optimal value should be at most
 * 10^−3. To ensure this, output your answer with at least four digits after the decimal point (otherwise
 * your answer, while being computed correctly, can turn out to be wrong because of rounding issues).
 *
 * Constraints: 1 ≤ 𝑛 ≤ 10^3, 0 ≤ 𝑊 ≤ 2*10^6; 0 ≤ 𝑣𝑖 ≤ 2*10^6, 0 < 𝑤𝑖 ≤ 2*10^6 for all 1 ≤ 𝑖 ≤ 𝑛. All the numbers are integers.
 */
package coursera.algorithms.algotoolbox.week3;

import java.util.Arrays;
import java.util.Scanner;

public class FractionalKnapsack {

    private static Item[] createItemList(int[] values, int[] weights) {

        Item[] items = new Item[values.length];

        for (int i = 0; i < values.length; i++) {
            items[i] = new Item(values[i], weights[i]);
        }

        return items;
    }

    private static void sortItemList(Item[] items) {

        Arrays.sort(items, (Item item1, Item item2) -> {
            double result2 = (item2.value + 0.0D) / (item2.weight + 0.0D);
            double result1 = (item1.value + 0.0D) / (item1.weight + 0.0D);

            if (result2 > result1) {
                return 1;
            } else if (result2 < result1) {
                return -1;
            }

            return 0;
        });
    }

    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double value = 0;
        Item[] items = createItemList(values, weights);
        sortItemList(items);

        int bestItem = 0;

        while (capacity != 0 && bestItem < items.length) {
            Item currentItem = items[bestItem];
            int currentWeight = Math.min(capacity, currentItem.weight);
            value += (currentWeight + 0.0D) * (currentItem.value + 0.0D) / (currentItem.weight + 0.0D);
            capacity -= currentWeight;
            bestItem++;
        }

        return value;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        System.out.printf("%.4f\n", getOptimalValue(capacity, values, weights));
    }

    private static class Item {

        private int value;
        private int weight;

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        @Override
        public String toString() {

            return String.format("Value=%d Weight=%d", this.value, this.weight);
        }
    }
}
