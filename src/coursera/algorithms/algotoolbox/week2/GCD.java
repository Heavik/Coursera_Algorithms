/* Greatest Common Divisor
 * Description: Given two integers 𝑎 and 𝑏, find their greatest common divisor.
 *
 * Input: The two integers 𝑎, 𝑏 are given in the same line separated by space.
 * Output: Output GCD(𝑎, 𝑏).
 * Constraints: 1 ≤ 𝑎, 𝑏 ≤ 2*10^9.
 */
package coursera.algorithms.algotoolbox.week2;

import java.util.*;

public class GCD {

    private static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }

        return gcd(b, a % b);
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        long a = scanner.nextLong();
        long b = scanner.nextLong();

        System.out.println(gcd(a, b));
    }
}
