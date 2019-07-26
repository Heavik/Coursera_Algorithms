// not working yet
package coursera.algorithms.strings.week1;

import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixTree {

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

    class Node {

        public List<Node> next;
        public int start;
        public int end;

        public Node() {
            start = end = -1;
            next = new ArrayList<>();
        }

        public boolean isLeaf() {
            return next.isEmpty();
        }
    }

    int matchingNode(Node node, int startPosition, String text) {
        for (int i = 0; i < node.next.size(); i++) {
            char currentSymbol = text.charAt(startPosition);
            Node next = node.next.get(i);
            if (currentSymbol == text.charAt(next.start)) {
                return i;
            }
        }
        return -1;
    }

    Node createNode(int start, int end) {
        Node newNode = new Node();
        newNode.start = start;
        newNode.end = end;
        return newNode;
    }

    boolean branchTree(Node node, String text, int startPosition, int textLength) {
        int currentStart = startPosition;
        char textSymbol = text.charAt(currentStart);
        int nodeStart = node.start;
        char nodeSymbol = text.charAt(nodeStart);
        while (textSymbol == nodeSymbol) {
            currentStart++;
            nodeStart++;
            if (nodeStart == node.end) {
                node.start = startPosition;
                node.end = currentStart;
                return false;
            }
            textSymbol = text.charAt(currentStart);
            nodeSymbol = text.charAt(nodeStart);
        }
        int newNodeEnd = currentStart;
        Node branchedNode = createNode(nodeStart, node.end);
        for(int i = 0; i < node.next.size(); i++) {
            branchedNode.next.add(node.next.get(i));
        }
        node.next = new ArrayList<>();
        node.next.add(createNode(newNodeEnd, textLength));
        node.next.add(branchedNode);
        node.start = startPosition;
        node.end = newNodeEnd;
        return true;
    }

    Node createSuffixTree(String text) {
        int textLength = text.length();
        Node tree = new Node();
        for (int i = textLength - 1; i >= 0; i--) {
            int startPosition = i;
            Node currentNode = tree;
            boolean isBranched = false;
            while (!isBranched && startPosition < textLength) {
                int matchNode = matchingNode(currentNode, startPosition, text);
                if (matchNode != -1) {
                    currentNode = currentNode.next.get(matchNode);
                    isBranched = branchTree(currentNode, text, startPosition, textLength);
                    startPosition++;
                } else {
                    Node newNode = new Node();
                    newNode.start = startPosition;
                    newNode.end = startPosition + (textLength - startPosition);
                    currentNode.next.add(newNode);
                    break;
                }
            }
        }
        return tree;
    }
    
    void populateList(List<String> list, Node suffixTree, String text) {
        if(suffixTree.start > -1 && suffixTree.end > -1) {
            list.add(text.substring(suffixTree.start, suffixTree.end));
        }
        for(int i = 0; i < suffixTree.next.size(); i++) {
            populateList(list, suffixTree.next.get(i), text);
        }
    }

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding 
    // substrings of the text) in any order.
    public List<String> computeSuffixTreeEdges(String text) {
        List<String> result = new ArrayList<>();

        Node suffixTree = createSuffixTree(text);
        populateList(result, suffixTree, text);
        return result;
    }

    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
    }
}
