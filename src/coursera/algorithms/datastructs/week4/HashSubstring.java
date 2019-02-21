package coursera.algorithms.datastructs.week4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;

    private static final int PRIME = 1000000007;
    private static final int MULTIPLIER = 263;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrencesNaive(Data input) {
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<>();
        for (int i = 0; i + m <= n; ++i) {
            boolean equal = true;
            for (int j = 0; j < m; ++j) {
                if (s.charAt(j) != t.charAt(i + j)) {
                    equal = false;
                    break;
                }
            }
            if (equal) {
                occurrences.add(i);
            }
        }
        return occurrences;
    }

    private static int getHash(String str) {
        long hash = 0;
        for (int i = str.length() - 1; i >= 0; --i) {
            hash = (hash * MULTIPLIER + str.charAt(i));
            hash = ((hash % PRIME) + PRIME) % PRIME;
        }
        return (int) hash;
    }

    private static int[] precomputeHashes(String str, String pattern) {
        int strLength = str.length();
        int patternLength = pattern.length();
        long[] hashes = new long[strLength - patternLength + 1];
        String sub = str.substring(strLength - patternLength, strLength);
        hashes[strLength - patternLength] = getHash(sub);
        long y = 1;
        for (int i = 0; i < patternLength; ++i) {
            y = (y * MULTIPLIER) % PRIME;
        }
        for (int i = strLength - patternLength - 1; i >= 0; --i) {
            hashes[i] = (MULTIPLIER * hashes[i + 1] + str.charAt(i) - y * str.charAt(i + patternLength));
            hashes[i] = ((hashes[i] % PRIME) + PRIME) % PRIME;
        }

        int[] resultHashes = new int[hashes.length];
        for (int i = 0; i < resultHashes.length; ++i) {
            resultHashes[i] = (int) hashes[i];
        }

        return resultHashes;
    }

    private static List<Integer> getOccurrences(Data input) {
        String str = input.text;
        String pattern = input.pattern;
        int strLength = str.length();
        int patternLength = pattern.length();
        List<Integer> occurrences = new ArrayList<>();
        int patternHash = getHash(pattern);
        int[] hashes = precomputeHashes(str, pattern);
        for (int i = 0; i < strLength - patternLength + 1; ++i) {
            if (patternHash != hashes[i]) {
                continue;
            }
            String subStr = str.substring(i, i + patternLength);
            if (pattern.equals(subStr)) {
                occurrences.add(i);
            }
        }
        return occurrences;
    }

    static class Data {

        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {

        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
