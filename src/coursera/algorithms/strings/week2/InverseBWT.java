package coursera.algorithms.strings.week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {

    class FastScanner {

        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements()) {
                tok = new StringTokenizer(in.readLine());
            }
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    final int NUMBER_OF_LETTERS = 5;

    int letterToInt(char letter) {
        switch (letter) {
            case '$':
                return 0;
            case 'A':
                return 1;
            case 'C':
                return 2;
            case 'G':
                return 3;
            case 'T':
                return 4;
            default:
                assert (false);
                return -1;
        }
    }

    char[] sort(char[] bwt, int length) {
        char[] output = new char[length];

        int[] count = new int[NUMBER_OF_LETTERS];

        for (int i = 0; i < length; i++) {
            ++count[letterToInt(bwt[i])];
        }

        for (int i = 1; i <= NUMBER_OF_LETTERS - 1; i++) {
            count[i] += count[i - 1];
        }

        for (int i = length - 1; i >= 0; i--) {
            int letterIndex = letterToInt(bwt[i]);
            output[count[letterIndex] - 1] = bwt[i];
            --count[letterIndex];
        }

        return output;
    }

    String inverseBWT(String bwt) {
        int bwtLength = bwt.length();
        StringBuilder result = new StringBuilder(bwtLength);

        char[] sortedBwt = sort(bwt.toCharArray(), bwtLength);

        int[] leftShift = new int[bwtLength];
        int[] startIndexes = new int[NUMBER_OF_LETTERS];
        int[] letterCounts = new int[NUMBER_OF_LETTERS];

        for (int i = 0; i < bwtLength; i++) {
            int letterIndex = letterToInt(sortedBwt[i]);
            if (startIndexes[letterIndex] == 0) {
                startIndexes[letterIndex] = i;
            }
        }

        for (int i = 0; i < bwtLength; i++) {
            int letterIndex = letterToInt(bwt.charAt(i));
            ++letterCounts[letterIndex];
            leftShift[i] = letterCounts[letterIndex] + startIndexes[letterIndex] - 1;
        }

        int r = 0;
        char letter = bwt.charAt(r);
        while (letter != '$') {
            result.append(letter);
            r = leftShift[r];
            letter = bwt.charAt(r);
        }
        result = result.reverse();
        result.append('$');

        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
}
