package coursera.algorithms.strings.week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
    
    String getRotation(StringBuilder sourceText, int startFrom, int textLength) {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j != textLength; j++) {
            sb.append(sourceText.charAt(startFrom + j));
        }
        return sb.toString();
    }

    String BWT(String text) {
        StringBuilder result = new StringBuilder();
        StringBuilder sourceText = new StringBuilder(text);
        sourceText.append(text);
        int textLength = text.length();
        
        String[] strings = new String[textLength];
        for(int i = 0; i < textLength; i++) {
            strings[i] = getRotation(sourceText, i, textLength);
        }
        Arrays.sort(strings);
        for(int i = 0; i < strings.length; i++) {
            result.append(strings[i].charAt(textLength - 1));
        }
        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}