/* Longest Common Subsequence of Two Sequences
 * Description: Compute the length of a longest common subsequence of three sequences.
 * Given two sequences 𝐴 = (𝑎1, 𝑎2,...,𝑎𝑛) and 𝐵 = (𝑏1, 𝑏2,...,𝑏𝑚), find the length of their longest
 * common subsequence, i.e., the largest non-negative integer 𝑝 such that there exist indices 1 ≤ 𝑖1 < 𝑖2 < ··· < 𝑖𝑝 ≤ 𝑛 
 * and 1 ≤ 𝑗1 < 𝑗2 < ··· < 𝑗𝑝 ≤ 𝑚, such that 𝑎𝑖1 = 𝑏𝑗1,...,𝑎𝑖𝑝 = 𝑏𝑗𝑝.
 *
 * Input: First line: 𝑛. Second line: 𝑎1, 𝑎2,...,𝑎𝑛. Third line: 𝑚. Fourth line: 𝑏1, 𝑏2,...,𝑏𝑚.
 *
 * Output: Output 𝑝.
 *
 * Constraints: 1 ≤ 𝑛,𝑚 ≤ 100; −10^9 < 𝑎𝑖, 𝑏𝑖 < 10^9.
 */
package coursera.algorithms.algotoolbox.week5;

import java.util.*;

public class LCS2 {

    private static int lcs2(int[] a, int[] b) {
        int[][] sequences = new int[a.length + 1][b.length + 1];

        for (int i = 1; i <= a.length; i++) {
            for (int j = 1; j <= b.length; j++) {
                if (a[i - 1] == b[j - 1]) {
                    sequences[i][j] = sequences[i - 1][j - 1] + 1;
                } else {
                    sequences[i][j] = Math.max(sequences[i][j - 1], sequences[i - 1][j]);
                }
            }
        }

        return sequences[a.length][b.length];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        int m = scanner.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            b[i] = scanner.nextInt();
        }

        System.out.println(lcs2(a, b));
    }
}
