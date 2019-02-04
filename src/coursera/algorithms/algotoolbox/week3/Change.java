/* Money Change
 * Description: The goal in this problem is to find the minimum number of coins needed to change the input value
 * (an integer) into coins with denominations 1, 5, and 10.
 *
 * Input: The input consists of a single integer 𝑚.
 * Output: Output the minimum number of coins with denominations 1, 5, 10 that changes 𝑚.
 * Constraints: 1 ≤ 𝑚 ≤ 10^3.
 */
package coursera.algorithms.algotoolbox.week3;

import java.util.Scanner;

public class Change {

    private static int getChange(int m) {

        final int ten = 10, five = 5, one = 1;
        int changes = 0;

        while (m > 0) {
            if (m >= ten) {
                m -= ten;
            } else if (m >= five) {
                m -= five;
            } else {
                m -= one;
            }

            changes++;
        }

        return changes;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));
    }
}
