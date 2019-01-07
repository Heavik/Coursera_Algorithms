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