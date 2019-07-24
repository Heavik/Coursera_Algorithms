package coursera.algorithms.strings.week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Trie {

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

    List<Map<Character, Integer>> buildTrie(String[] patterns) {
        List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();
        trie.add(new HashMap<>());
        int nodeCount = 0;
        for (String pattern : patterns) {
            Map<Character, Integer> currentNode = trie.get(0);
            int patternLength = pattern.length();
            for (int i = 0; i < patternLength; i++) {
                char currentSymbol = pattern.charAt(i);
                if (currentNode.containsKey(currentSymbol)) {
                    currentNode = trie.get(currentNode.get(currentSymbol));
                } else {
                    currentNode.put(currentSymbol, ++nodeCount);
                    Map<Character, Integer> newNode = new HashMap<>();
                    trie.add(newNode);
                    currentNode = newNode;
                }
            }
        }
        return trie;
    }

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }
}
