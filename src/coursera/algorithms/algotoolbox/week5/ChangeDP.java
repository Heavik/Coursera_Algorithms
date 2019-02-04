/* Money Change Again
 * Description: As we already know, a natural greedy strategy for the change problem does not work correctly for any set of
 * denominations. For example, if the available denominations are 1, 3, and 4, the greedy algorithm will change
 * 6 cents using three coins (4 + 1 + 1) while it can be changed using just two coins (3 + 3). Your goal now is
 * to apply dynamic programming for solving the Money Change Problem for denominations 1, 3, and 4.
 *
 * Input: Integer money.
 *
 * Output: The minimum number of coins with denominations 1, 3, 4 that changes money.
 *
 * Constraints: 1 ≤ money ≤ 10^3.
 */
package coursera.algorithms.algotoolbox.week5;

import java.util.Scanner;

public class ChangeDP {

    private static int getChange(int m) {

        int[] changes = new int[m + 1];
        int[] coins = {1, 3, 4};

        changes[0] = 0;

        for (int coin : coins) {
            for (int i = coin; i <= m; i++) {
                if (i == coin) {
                    changes[i] = 1;
                } else if (i > coin) {
                    int change = changes[coin] + changes[i - coin];
                    if (changes[i] == 0 || change < changes[i]) {
                        changes[i] = change;
                    }
                }
            }
        }

        return changes[m];
    }

    private static int getMinChange(int i, int[] changes) {

        final int one = 1, three = 3, four = 4;

        if (i == one || i == three || i == four) {
            return 1;
        }

        int changeValue4 = i - four >= 1 ? changes[i - four] : Integer.MAX_VALUE;
        int changeValue3 = i - three >= 1 ? changes[i - three] : Integer.MAX_VALUE;
        int changeValue1 = changes[i - one];

        if (changeValue4 <= changeValue3 && changeValue4 <= changeValue1) {
            return changes[i - four] + changes[four];
        }

        if (changeValue3 <= changeValue4 && changeValue3 <= changeValue1) {
            return changes[i - three] + changes[three];
        }

        return changes[i - one] + changes[one];
    }

    private static int getChangeUpdated(int m) {

        int[] changes = new int[m + 1];
        changes[0] = 0;

        for (int i = 1; i <= m; i++) {
            changes[i] = getMinChange(i, changes);
        }

        return changes[m];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChangeUpdated(m));
    }
}
