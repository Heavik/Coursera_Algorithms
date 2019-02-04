/* Maximum Salary
 * Description: Compose the largest number out of a set of integers.
 *
 * Input: The first line of the input contains an integer 𝑛. The second line contains integers 𝑎1, 𝑎2,...,𝑎𝑛.
 *
 * Output: Output the largest number that can be composed out of 𝑎1, 𝑎2,...,𝑎𝑛.
 *
 * Constraints: 1 ≤ 𝑛 ≤ 100; 1 ≤ 𝑎𝑖 ≤ 10^3 for all 1 ≤ 𝑖 ≤ 𝑛.
 */
package coursera.algorithms.algotoolbox.week3;

import java.util.*;

public class LargestNumber {

    private static boolean isGreaterOrEqual(String digit, String maxDigit) {

        String firstCompositeNumber = digit + maxDigit;
        String secondCompositeNumber = maxDigit + digit;

        for (int i = 0; i < firstCompositeNumber.length(); ++i) {

            int firstCompositeDigit = Integer.parseInt(Character.toString(firstCompositeNumber.charAt(i)));
            int secondCompositeDigit = Integer.parseInt(Character.toString(secondCompositeNumber.charAt(i)));

            if (firstCompositeDigit != secondCompositeDigit) {
                return firstCompositeDigit > secondCompositeDigit;
            }
        }

        return true;
    }

    private static String largestNumber(String[] a) {
        List<String> digits = new ArrayList<>(Arrays.asList(a));
        String result = "";

        while (!digits.isEmpty()) {
            String maxDigit = "0";
            for (String digit : digits) {
                if (isGreaterOrEqual(digit, maxDigit)) {
                    maxDigit = digit;
                }
            }
            result += maxDigit;
            digits.remove(maxDigit);
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        System.out.println(largestNumber(a));
    }
}
