/* Maximum Number of Prizes
 * Description: The goal of this problem is to represent a given positive integer 𝑛 as a sum of as many pairwise
 * distinct positive integers as possible. That is, to find the maximum 𝑘 such that 𝑛 can be written as
 * 𝑎1 + 𝑎2 + ··· + 𝑎𝑘 where 𝑎1,...,𝑎𝑘 are positive integers and 𝑎𝑖 ̸= 𝑎𝑗 for all 1 ≤ 𝑖 < 𝑗 ≤ 𝑘.
 *
 * Input: The input consists of a single integer 𝑛
 *
 * Output: In the first line, output the maximum number 𝑘 such that 𝑛 can be represented as a sum
 * of 𝑘 pairwise distinct positive integers. In the second line, output 𝑘 pairwise distinct positive integers
 * that sum up to 𝑛 (if there are many such representations, output any of them).
 *
 * Constraints: 1 ≤ 𝑛 ≤ 10^9.
 */
package coursera.algorithms.algotoolbox.week3;

import java.util.*;

public class DifferentSummands {

    private static List<Integer> optimalSummands(int n) {
        List<Integer> summands = new ArrayList<>();

        int number = 0;

        while (n > 0) {
            number++;
            if (n - number == 0 || n - number > number) {
                summands.add(number);
                n -= number;
            }
        }

        return summands;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
}
