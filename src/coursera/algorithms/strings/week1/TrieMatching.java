package coursera.algorithms.strings.week1;

import java.io.*;
import java.util.*;

class Node {

    public static final int LETTERS = 4;
    public static final int NA = -1;
    public Node next[];

    Node() {
        next = new Node[LETTERS];
    }

    boolean isLeaf() {
        for (int i = 0; i < LETTERS; i++) {
            if (next[i] != null) {
                return false;
            }
        }
        return true;
    }
}

public class TrieMatching implements Runnable {

    int letterToIndex(char letter) {
        switch (letter) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
            default:
                assert (false);
                return Node.NA;
        }
    }

    Node buildTrie(List<String> patterns) {
        Node trie = new Node();
        for (String pattern : patterns) {
            Node currentNode = trie;
            int patternLength = pattern.length();
            for (int i = 0; i < patternLength; i++) {
                int currentSymbol = letterToIndex(pattern.charAt(i));
                if (currentNode.next[currentSymbol] != null) {
                    currentNode = currentNode.next[currentSymbol];
                } else {
                    Node newNode = new Node();
                    currentNode.next[currentSymbol] = newNode;
                    currentNode = newNode;
                }
            }
        }
        return trie;
    }

    Integer prefixTrieMatch(String text, Node trie, int startIndex) {
        char symbol = text.charAt(startIndex);
        int textLength = text.length();
        int matchIndex = startIndex;
        Node vertex = trie;
        while (startIndex <= textLength) {
            int symbolIndex = letterToIndex(symbol);
            if (vertex.isLeaf()) {
                return matchIndex;
            } else if (vertex.next[symbolIndex] != null) {
                startIndex++;
                if (startIndex < textLength) {
                    symbol = text.charAt(startIndex);
                }
                vertex = vertex.next[symbolIndex];
            } else {
                return null;
            }
        }
        return null;
    }

    List<Integer> solve(String text, int n, List<String> patterns) {
        List<Integer> result = new ArrayList<>();

        Node trie = buildTrie(patterns);
        for (int i = 0; i < text.length(); i++) {
            Integer idx = prefixTrieMatch(text, trie, i);
            if (idx != null) {
                result.add(idx);
            }
        }
        return result;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String text = in.readLine();
            int n = Integer.parseInt(in.readLine());
            List<String> patterns = new ArrayList<String>();
            for (int i = 0; i < n; i++) {
                patterns.add(in.readLine());
            }

            List<Integer> ans = solve(text, n, patterns);

            for (int j = 0; j < ans.size(); j++) {
                System.out.print("" + ans.get(j));
                System.out.print(j + 1 < ans.size() ? " " : "\n");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new Thread(new TrieMatching()).start();
    }
}