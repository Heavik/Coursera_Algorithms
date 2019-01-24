package coursera.algorithms.algotoolbox.week5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class EditDistanceWithBacktracking {

    final static int INSERT_DELETE_COST = 1;
    final static int SUBSTITUTION_COST = 2;

    final static char MATCH_ACTION = 'M';
    final static char SUBSTITUTE_ACTION = 'S';
    final static char INSERT_ACTION = 'I';
    final static char DELETE_ACTION = 'D';

    private static int[][] editDistance(String firstString, String secondString) {
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
                    int insert_delete_min = Math.min(distances[i][j - 1], distances[i - 1][j]);
                    int substitution = distances[i - 1][j - 1];

                    int cost = insert_delete_min <= substitution ? INSERT_DELETE_COST : SUBSTITUTION_COST;
                    distances[i][j] = cost + Math.min(insert_delete_min, substitution);
                }
            }
        }

        return distances;
    }

    private static int editDistanceLength(String firstString, String secondString) {
        int[][] distances = editDistance(firstString, secondString);
        return distances[firstString.length()][secondString.length()];
    }

    private static List<Character> editTranscript(int[][] distances) {
        List<Character> editList = new ArrayList<>();

        int i = distances.length - 1;
        int j = distances[0].length - 1;

        while (i > 0 || j > 0) {
            int insertion = j > 0 ? distances[i][j - 1] : Integer.MAX_VALUE;
            int deletion = i > 0 ? distances[i - 1][j] : Integer.MAX_VALUE;
            int substitution;

            if (i > 0 && j > 0) {
                substitution = distances[i - 1][j - 1];
            } else if (i > 0) {
                substitution = deletion;
            } else {
                substitution = insertion;
            }

            if (insertion <= deletion && insertion <= substitution) {
                editList.add(INSERT_ACTION);
                j--;
            } else if (deletion <= insertion && deletion <= substitution) {
                editList.add(DELETE_ACTION);
                i--;
            } else {
                char action = distances[i][j] == substitution ? MATCH_ACTION : SUBSTITUTE_ACTION;
                editList.add(action);
                i--;
                j--;
            }
        }

        Collections.reverse(editList);
        return editList;
    }

    private static void print(List<Character> editList, String firstString, String secondString) {
        StringBuilder firstWord = new StringBuilder();
        StringBuilder secondWord = new StringBuilder();

        for (int i = 0; i < editList.size(); i++) {
            char action = editList.get(i);
            switch (action) {
                case INSERT_ACTION:
                    firstWord.append('-');
                    break;
                case DELETE_ACTION:
                    secondWord.append('-');
                    break;
            }
            if (i < firstString.length()) {
                firstWord.append(firstString.charAt(i));
            }
            if (i < secondString.length()) {
                secondWord.append(secondString.charAt(i));
            }
        }

        System.out.println(firstWord.toString());
        System.out.println(secondWord.toString());
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        String s = scan.next();
        String t = scan.next();

        int[][] distances = editDistance(s, t);
        print(editTranscript(distances), s, t);
    }
}
