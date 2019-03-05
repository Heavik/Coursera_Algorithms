package coursera.algorithms.datastructs.week6;

import java.util.*;
import java.io.*;

public class IsBST {

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

    public class Tree {

        class Node {

            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        private boolean isBinarySearchTree(Node node, int min, int max) {
            if (node == null) {
                return true;
            }

            if (node.key < min || node.key > max) {
                return false;
            }

            Node leftNode = node.left == -1 ? null : tree[node.left];
            Node rightNode = node.right == -1 ? null : tree[node.right];

            return isBinarySearchTree(leftNode, min, node.key - 1)
                    && isBinarySearchTree(rightNode, node.key + 1, max);
        }

        boolean isBinarySearchTree() {
            if (tree == null || tree.length == 0) {
                return true;
            }
            return isBinarySearchTree(tree[0], Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, () -> {
            try {
                new IsBST().run();
            } catch (IOException e) {
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        Tree tree = new Tree();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
