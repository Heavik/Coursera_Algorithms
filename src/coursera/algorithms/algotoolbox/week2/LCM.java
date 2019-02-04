/* Least Common Multiple
 * Description: Given two integers 𝑎 and 𝑏, find their least common multiple.
 *
 * Input: The two integers 𝑎 and 𝑏 are given in the same line separated by space.
 * Output: Output the least common multiple of 𝑎 and 𝑏.
 * Constraints: 1 ≤ 𝑎, 𝑏 ≤ 2*10^9.
 */
package coursera.algorithms.algotoolbox.week2;

import java.util.*;

public class LCM {

    private static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }

        return gcd(b, a % b);
    }

    private static long lcm(long a, long b) {

        return (a * b) / gcd(a, b);
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        long a = scanner.nextLong();
        long b = scanner.nextLong();

        System.out.println(lcm(a, b));
    }
}
