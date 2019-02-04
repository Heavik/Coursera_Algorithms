/* Sum of Two Digits Problem 
 * Description: Compute the sum of two single digit numbers.
 * Input: Integers a and b on the same line (separated by a space).
 * Output: The sum of a and b.
 * Constraints: 0 <= a,b <= 9.
 */
package coursera.algorithms.algotoolbox.week1;

import java.util.Scanner;

public class APlusB {

    static int sumOfTwoDigits(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int b = s.nextInt();
        System.out.println(sumOfTwoDigits(a, b));
    }
}
