/* Primitive Calculator
 * Description: Given an integer 𝑛, compute the minimum number of operations needed to obtain the number 𝑛
 * starting from the number 1.
 * You are given a primitive calculator that can perform the following three operations with
 * the current number 𝑥: multiply 𝑥 by 2, multiply 𝑥 by 3, or add 1 to 𝑥. Your goal is given a
 * positive integer 𝑛, find the minimum number of operations needed to obtain the number 𝑛
 * starting from the number 1. 
 *
 * Input: The input consists of a single integer 1 ≤ 𝑛 ≤ 10^6.
 *
 * Output: In the first line, output the minimum number 𝑘 of operations needed to get 𝑛 from 1.
 * In the second line output a sequence of intermediate numbers. That is, the second line should contain
 * positive integers 𝑎0, 𝑎2,...,𝑎[𝑘−1] such that 𝑎0 = 1, 𝑎[𝑘−1] = 𝑛 and for all 0 ≤ 𝑖 < 𝑘 − 1, 𝑎[𝑖+1] is equal to
 * either 𝑎𝑖 + 1, 2𝑎𝑖, or 3𝑎𝑖. If there are many such sequences, output any one of them.
 */
package coursera.algorithms.algotoolbox.week5;

import java.util.*;

public class EditDistance {

    private static int editDistance(String firstString, String secondString) {
        int firstStringLenght = firstString.length();
        int secondStringLength = secondString.length();
        int[][] distances = new int[firstStringLenght + 1][secondStringLength + 1];

        distances[0][0] = 0;

        for (int i = 0; i <= firstStringLenght; i++) {
            distances[i][0] = i;
        }

        for (int i = 0; i <= secondStringLength; i++) {
            distances[0][i] = i;
        }

        for (int i = 1; i <= firstStringLenght; i++) {

            for (int j = 1; j <= secondStringLength; j++) {

                if (firstString.charAt(i - 1) == secondString.charAt(j - 1)) {
                    distances[i][j] = distances[i - 1][j - 1];
                } else {
                    distances[i][j] = 1 + Math.min(distances[i][j - 1], Math.min(distances[i - 1][j], distances[i - 1][j - 1]));
                }
            }
        }

        return distances[firstStringLenght][secondStringLength];
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        String s = scan.next();
        String t = scan.next();

        System.out.println(editDistance(s, t));
    }
}
