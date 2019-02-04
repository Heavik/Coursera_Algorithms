/* Longest Common Subsequence of Three Sequences
 * Description: Compute the length of a longest common subsequence of three sequences.
 * Given three sequences 𝐴 = (𝑎1, 𝑎2,..., 𝑎𝑛), 𝐵 = (𝑏1, 𝑏2,..., 𝑏𝑚), and 𝐶 = (𝑐1, 𝑐2,...,𝑐𝑙), find the
 * length of their longest common subsequence, i.e., the largest non-negative integer 𝑝 such that there
 * exist indices 1 ≤ 𝑖1 < 𝑖2 < ··· < 𝑖𝑝 ≤ 𝑛, 1 ≤ 𝑗1 < 𝑗2 < ··· < 𝑗𝑝 ≤ 𝑚, 1 ≤ 𝑘1 < 𝑘2 < ··· < 𝑘𝑝 ≤ 𝑙 such
 * that 𝑎𝑖1 = 𝑏𝑗1 = 𝑐𝑘1,...,𝑎𝑖𝑝 = 𝑏𝑗𝑝 = 𝑐𝑘𝑝
 *
 * Input: First line: 𝑛. Second line: 𝑎1, 𝑎2,...,𝑎𝑛. Third line: 𝑚. Fourth line: 𝑏1, 𝑏2,...,𝑏𝑚. Fifth line:𝑙. Sixth line: 𝑐1, 𝑐2,...,𝑐𝑙.
 *
 * Output: Output 𝑝.
 *
 * Constraints: 1 ≤ 𝑛,𝑚,𝑙 ≤ 100; −10^9 < 𝑎𝑖,𝑏𝑖,𝑐𝑖 < 10^9.
 */
package coursera.algorithms.algotoolbox.week5;

import java.util.*;

public class LCS3 {

    private static int findMax(int... params) {
        int maxValue = params[0];
        for (int i = 1; i < params.length; i++) {
            if (params[i] > maxValue) {
                maxValue = params[i];
            }
        }

        return maxValue;
    }

    private static int lcs3(int[] a, int[] b, int[] c) {
        int[][][] sequences = new int[a.length + 1][b.length + 1][c.length + 1];

        for (int i = 1; i <= a.length; i++) {
            for (int j = 1; j <= b.length; j++) {
                for (int k = 1; k <= c.length; k++) {
                    if (a[i - 1] == b[j - 1] && b[j - 1] == c[k - 1]) {
                        sequences[i][j][k] = sequences[i - 1][j - 1][k - 1] + 1;
                    } else {
                        sequences[i][j][k]
                                = findMax(sequences[i][j][k - 1],
                                        sequences[i - 1][j][k],
                                        sequences[i][j - 1][k],
                                        sequences[i][j - 1][k - 1],
                                        sequences[i - 1][j - 1][k],
                                        sequences[i - 1][j][k - 1]);
                    }
                }
            }
        }

        return sequences[a.length][b.length][c.length];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int an = scanner.nextInt();
        int[] a = new int[an];
        for (int i = 0; i < an; i++) {
            a[i] = scanner.nextInt();
        }
        int bn = scanner.nextInt();
        int[] b = new int[bn];
        for (int i = 0; i < bn; i++) {
            b[i] = scanner.nextInt();
        }
        int cn = scanner.nextInt();
        int[] c = new int[cn];
        for (int i = 0; i < cn; i++) {
            c[i] = scanner.nextInt();
        }
        System.out.println(lcs3(a, b, c));
    }
}
