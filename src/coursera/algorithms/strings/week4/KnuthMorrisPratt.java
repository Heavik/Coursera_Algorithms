package coursera.algorithms.strings.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KnuthMorrisPratt {

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

    private int[] computePrefixes(String str, int strLength) {
        int[] result = new int[strLength];
        result[0] = 0;
        int border = 0;
        for (int i = 1; i < strLength; i++) {
            char currentSymbol = str.charAt(i);
            while (border > 0 && currentSymbol != str.charAt(border)) {
                border = result[border - 1];
            }
            if (currentSymbol == str.charAt(border)) {
                border++;
            } else {
                border = 0;
            }
            result[i] = border;
        }
        return result;
    }

    // Find all the occurrences of the pattern in the text and return
    // a list of all positions in the text (starting from 0) where
    // the pattern starts in the text.
    public List<Integer> findPattern(String pattern, String text) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int patternLength = pattern.length();
        int textLength = text.length();
        if (patternLength > textLength) {
            return result;
        }
        String str = pattern + "$" + text;
        int strLength = str.length();
        int[] prefixes = computePrefixes(str, strLength);
        for (int i = patternLength + 1; i < strLength; i++) {
            if (prefixes[i] == patternLength) {
                result.add(i - 2 * patternLength);
            }
        }
        return result;
    }

    static public void main(String[] args) throws IOException {
        new KnuthMorrisPratt().run();
    }

    public void print(List<Integer> x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String pattern = scanner.next();
        String text = scanner.next();
        List<Integer> positions = findPattern(pattern, text);
        print(positions);
    }
}