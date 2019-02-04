/* Maximum Advertisement Revenue
 * Description: Given two sequences 𝑎1, 𝑎2,...,𝑎𝑛 (𝑎𝑖 is the profit per click of the 𝑖-th ad) and 𝑏1, 𝑏2,...,𝑏𝑛 (𝑏𝑖 is
 * the average number of clicks per day of the 𝑖-th slot), we need to partition them into 𝑛 pairs (𝑎𝑖, 𝑏𝑗)
 * such that the sum of their products is maximized.
 *
 * Input: The first line contains an integer 𝑛, the second one contains a sequence of integers
 * 𝑎1, 𝑎2,...,𝑎𝑛, the third one contains a sequence of integers 𝑏1, 𝑏2,...,𝑏𝑛.
 *
 * Output: Output the maximum value of sum(𝑎𝑖*𝑐𝑖), where 𝑐1, 𝑐2,..., 𝑐𝑛 is a permutation of 𝑏1, 𝑏2,...,𝑏𝑛.
 *
 * Constraints: 1 ≤ 𝑛 ≤ 10^3; −10^5 ≤ 𝑎𝑖, 𝑏𝑖 ≤ 10^5 for all 1 ≤ 𝑖 ≤ 𝑛.
 */
package coursera.algorithms.algotoolbox.week3;

import java.util.*;

public class DotProduct {

    private static void reverseSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {

            int maxIdx = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] > arr[maxIdx]) {
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
            result += (long) a[i] * (long) b[i];
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
