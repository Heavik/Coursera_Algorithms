/* Maximum Value of an Arithmetic Expression
 * Description: Find the maximum value of an arithmetic expression by specifying the order of applying its arithmetic
 * operations using additional parentheses.
 *
 * Input: The only line of the input contains a string 𝑠 of length 2𝑛 + 1 for some 𝑛, with symbols 𝑠0, 𝑠1,...,𝑠2𝑛.
 * Each symbol at an even position of 𝑠 is a digit (that is, an integer from 0 to 9) while each symbol at an odd position is one of three operations from {+,-,*}.
 *
 * Output: Output the maximum possible value of the given arithmetic expression among different orders of applying arithmetic operations.
 *
 * Constraints: 1 ≤ 𝑛 ≤ 14 (hence the string contains at most 29 symbols).
 */
package coursera.algorithms.algotoolbox.week6;

import java.util.Scanner;

public class PlacingParentheses {

    private static long[] findMinMax(long[][] max, long[][] min, String[] operations, int i, int j) {
        long maximum = Long.MIN_VALUE;
        long minimum = Long.MAX_VALUE;
        for (int k = i; k < j; k++) {
            long a = eval(max[i][k], max[k + 1][j], operations[k + 1]);
            long b = eval(max[i][k], min[k + 1][j], operations[k + 1]);
            long c = eval(min[i][k], max[k + 1][j], operations[k + 1]);
            long d = eval(min[i][k], min[k + 1][j], operations[k + 1]);
            minimum = Math.min(minimum, Math.min(Math.min(a, b), Math.min(c, d)));
            maximum = Math.max(maximum, Math.max(Math.max(a, b), Math.max(c, d)));
        }

        long[] result = new long[2];
        result[0] = minimum;
        result[1] = maximum;

        return result;
    }

    private static long getMaximValue(String exp) {
        String[] numbers = exp.split("[+\\-*]");
        String[] operations = exp.split("\\d+");

        long[][] min = new long[numbers.length][numbers.length];
        long[][] max = new long[numbers.length][numbers.length];

        for (int i = 0; i < min.length; i++) {
            long number = Long.parseLong(numbers[i]);
            min[i][i] = number;
            max[i][i] = number;
        }

        for (int s = 1; s < min.length; s++) {
            for (int i = 0; i < min.length - s; i++) {
                int j = i + s;
                long[] result = findMinMax(max, min, operations, i, j);
                min[i][j] = result[0];
                max[i][j] = result[1];
            }
        }
        return max[0][max.length - 1];
    }

    private static long eval(long a, long b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            default:
                assert false;
                return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
}
