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

public class PrimitiveCalculator {

    private static List<Integer> greedySequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        while (n >= 1) {
            sequence.add(n);
            if (n % 3 == 0) {
                n /= 3;
            } else if (n % 2 == 0) {
                n /= 2;
            } else {
                n -= 1;
            }
        }
        Collections.reverse(sequence);
        return sequence;
    }

    private static int selectValue(int[] sequenceArr, int currentNum) {
        int value1 = currentNum % 3 == 0 ? sequenceArr[currentNum / 3] : Integer.MAX_VALUE;
        int value2 = currentNum % 2 == 0 ? sequenceArr[currentNum / 2] : Integer.MAX_VALUE;
        int value3 = sequenceArr[currentNum - 1];

        int value;

        if (value1 <= value2 && value1 <= value3) {
            value = currentNum / 3;
        } else if (value2 <= value1 && value2 <= value3) {
            value = currentNum / 2;
        } else {
            value = currentNum - 1;
        }

        return value;
    }

    private static List<Integer> optimalSequence(int n) {
        List<Integer> sequence = new ArrayList<>();

        int[] sequenceArr = new int[n + 1];
        sequenceArr[0] = 0;

        for (int i = 1; i <= n; i++) {
            int value = selectValue(sequenceArr, i);
            sequenceArr[i] = sequenceArr[value] + 1;
        }

        sequence.add(n);
        while (n > 0) {
            int value = selectValue(sequenceArr, n);
            if (value != 0) {
                sequence.add(value);
            }
            n = value;
        }

        Collections.reverse(sequence);
        return sequence;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimalSequence(n);
        System.out.println(sequence.size() - 1);
        StringBuilder sb = new StringBuilder();
        for (Integer x : sequence) {
            sb.append(x);
            sb.append(" ");
        }

        System.out.print(sb.toString());
    }
}
