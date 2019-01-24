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
