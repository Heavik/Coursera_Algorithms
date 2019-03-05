package coursera.algorithms.datastructs.week6;

import java.util.*;
import java.io.*;

public class TreeOrders {

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

    public class BinaryTree {

        int n;
        int[] key, left, right;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            key = new int[n];
            left = new int[n];
            right = new int[n];
            for (int i = 0; i < n; i++) {
                key[i] = in.nextInt();
                left[i] = in.nextInt();
                right[i] = in.nextInt();
            }
        }

        private void inOrder(int index, List<Integer> result) {
            if (index == -1) {
                return;
            }
            inOrder(left[index], result);
            result.add(key[index]);
            inOrder(right[index], result);
        }

        List<Integer> inOrder() {
            ArrayList<Integer> result = new ArrayList<>();
            inOrder(0, result);
            return result;
        }

        private void preOrder(int index, List<Integer> result) {
            if (index == -1) {
                return;
            }
            result.add(key[index]);
            preOrder(left[index], result);
            preOrder(right[index], result);
        }

        List<Integer> preOrder() {
            ArrayList<Integer> result = new ArrayList<>();
            preOrder(0, result);
            return result;
        }

        private void postOrder(int index, List<Integer> result) {
            if (index == -1) {
                return;
            }
            postOrder(left[index], result);
            postOrder(right[index], result);
            result.add(key[index]);
        }

        List<Integer> postOrder() {
            ArrayList<Integer> result = new ArrayList<>();
            postOrder(0, result);
            return result;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, () -> {
            try {
                new TreeOrders().run();
            } catch (IOException e) {
            }
        }, "1", 1 << 26).start();
    }

    public void print(List<Integer> x) {
        for (Integer a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        BinaryTree tree = new BinaryTree();
        tree.read();
        print(tree.inOrder());
        print(tree.preOrder());
        print(tree.postOrder());
    }
}
