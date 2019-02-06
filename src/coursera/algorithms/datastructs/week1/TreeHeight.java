package coursera.algorithms.datastructs.week1;

import java.util.*;
import java.io.*;

public class TreeHeight {

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

    public class Node {

        private final int value;
        private final List<Node> children = new ArrayList<>();

        public Node(int value) {
            this.value = value;
        }

        public void addChild(Node childNode) {
            children.add(childNode);
        }

        public List<Node> getChildren() {
            return children;
        }

        public boolean isLeaf() {
            return children.isEmpty();
        }
    }

    public class Tree {

        private int n;
        private int root;
        private int[] parent;
        private Node[] nodes;

        public void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = in.nextInt();
            }
            nodes = new Node[n];
            for (int i = 0; i < n; i++) {
                nodes[i] = new Node(i);
            }
            for (int childIndex = 0; childIndex < n; childIndex++) {
                int parentIndex = parent[childIndex];
                if (parentIndex == -1) {
                    root = childIndex;
                } else {
                    nodes[parentIndex].addChild(nodes[childIndex]);
                }
            }
        }

        public int computeHeightNaive() {
            int maxHeight = 0;
            for (int vertex = 0; vertex < n; vertex++) {
                int height = 0;
                for (int i = vertex; i != -1; i = parent[i]) {
                    height++;
                }
                maxHeight = Math.max(maxHeight, height);
            }
            return maxHeight;
        }

        private int findMax(int[] heights) {
            int max = heights[0];

            for (int i = 1; i < heights.length; i++) {
                if (heights[i] > max) {
                    max = heights[i];
                }
            }

            return max;
        }

        private int computeHeight(Node node) {
            if (node.isLeaf()) {
                return 1;
            }

            List<Node> children = node.getChildren();
            int[] heights = new int[children.size()];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = 1 + computeHeight(children.get(i));
            }

            return findMax(heights);
        }

        public int computeHeight() {
            return computeHeight(nodes[root]);
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, () -> {
            try {
                new TreeHeight().run();
            } catch (IOException e) {}
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        Tree tree = new Tree();
        tree.read();
        System.out.println(tree.computeHeight());
    }
}
