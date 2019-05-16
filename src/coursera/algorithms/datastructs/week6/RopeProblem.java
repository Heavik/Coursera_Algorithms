package coursera.algorithms.datastructs.week6;

import java.io.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

class RopeProblem {

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

    class Rope {

        class Vertex {

            char key;
            long size;
            Vertex left;
            Vertex right;
            Vertex parent;

            Vertex(char key, long size, Vertex left, Vertex right, Vertex parent) {
                this.key = key;
                this.size = size;
                this.left = left;
                this.right = right;
                this.parent = parent;
            }
        }

        class VertexPair {

            Vertex left;
            Vertex right;

            VertexPair() {
            }

            VertexPair(Vertex left, Vertex right) {
                this.left = left;
                this.right = right;
            }
        }

        String s;
        Vertex root = null;

        void update(Vertex v) {
            if (v == null) {
                return;
            }
            v.size = 1 + (v.left != null ? v.left.size : 0) + (v.right != null ? v.right.size : 0);
            if (v.left != null) {
                v.left.parent = v;
            }
            if (v.right != null) {
                v.right.parent = v;
            }
        }

        void smallRotation(Vertex v) {
            Vertex parent = v.parent;
            if (parent == null) {
                return;
            }
            Vertex grandparent = v.parent.parent;
            if (parent.left == v) {
                Vertex m = v.right;
                v.right = parent;
                parent.left = m;
            } else {
                Vertex m = v.left;
                v.left = parent;
                parent.right = m;
            }
            update(parent);
            update(v);
            v.parent = grandparent;
            if (grandparent != null) {
                if (grandparent.left == parent) {
                    grandparent.left = v;
                } else {
                    grandparent.right = v;
                }
            }
        }

        void bigRotation(Vertex v) {
            if (v.parent.left == v && v.parent.parent.left == v.parent) {
                // Zig-zig
                smallRotation(v.parent);
                smallRotation(v);
            } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
                // Zig-zig
                smallRotation(v.parent);
                smallRotation(v);
            } else {
                // Zig-zag
                smallRotation(v);
                smallRotation(v);
            }
        }

        Vertex splay(Vertex v) {
            if (v == null) {
                return null;
            }
            while (v.parent != null) {
                if (v.parent.parent == null) {
                    smallRotation(v);
                    break;
                }
                bigRotation(v);
            }
            return v;
        }

        VertexPair find(Vertex root, long index) {
            Vertex v = root;
            Vertex last = root;
            Vertex next = null;
            while (v != null) {
//                if (v.key >= key && (next == null || v.key < next.key)) {
//                    next = v;
//                }
//                last = v;
//                if (v.key == key) {
//                    break;
//                }
//                if (v.key < key) {
//                    v = v.right;
//                } else {
//                    v = v.left;
//                }
//                if(v.left == null) {
//                    break;
//                }
                long leftSize = v.left != null ? v.left.size : 0;
//                if (v.size >= index && (next == null || v.size < next.size)) {
//                    next = v;
//                }
                //long currentSize = v.left.size;
                if (index == leftSize + 1) {
                    next = v;
                    break;
                }

                if (index < leftSize + 1) {
                    v = v.left;
                } else {
                    v = v.right;
                    index = index - leftSize - 1;
                }
            }
            root = splay(last);
            return new VertexPair(next, root);
        }

        VertexPair split(Vertex root, int index) {
//            if(index < 1) {
//                return new VertexPair(null, root);
//            }
//            
//            if(index > root.size) {
//                return new VertexPair(root, null);
//            }
//            
//            Vertex right = find(root, index);
//            Vertex left;
//            //right.left = null;
//            if(right == null) {
//                left = root;
//                return new VertexPair(left, right);
//            }
//            
//            //right = splay(right);
//            left = right.left;
//            right.left = null;
//            if(left != null) {
//                left.parent = null;
//            }
//            update(left);
//            update(right);
//            return new VertexPair(left, right);
            VertexPair result = new VertexPair();
            VertexPair findAndRoot = find(root, index);
            root = findAndRoot.right;
            result.right = findAndRoot.left;
            if (result.right == null) {
                result.left = root;
                return result;
            }
            result.right = splay(result.right);
            result.left = result.right.left;
            result.right.left = null;
            if (result.left != null) {
                result.left.parent = null;
            }
            update(result.left);
            update(result.right);
            return result;
        }

        Vertex merge(Vertex left, Vertex right) {
            if (left == null) {
                return right;
            }
            if (right == null) {
                return left;
            }
            while (right.left != null) {
                right = right.left;
            }
            right = splay(right);
            right.left = left;
            update(right);
            return right;
        }

        Vertex insert(Vertex root, String s, int left, int right) {
            if (right < left) {
                return null;
            }

            int middle = (right + left) / 2;
            if (middle >= s.length()) {
                return null;
            }
            Vertex v = new Vertex(s.charAt(middle), 0, null, null, null);
            v.parent = root;
            v.left = insert(v, s, left, middle - 1);
            v.right = insert(v, s, middle + 1, right);
            update(v);

            return v;
        }

        void processNaive(int i, int j, int k) {
            // Replace this code with a faster implementation
            String t = s.substring(0, i) + s.substring(j + 1);
            s = t.substring(0, k) + s.substring(i, j + 1) + t.substring(k);
        }

        void process(int i, int j, int k) {
            VertexPair leftPair = split(root, j + 1);
            printTree(leftPair.left);
            System.out.println();
            printTree(leftPair.right);
//            Vertex left = leftPair.left;
//            Vertex middle = leftPair.right;
//            VertexPair middlePair = split(middle, j - i + 2);
//            middle = middlePair.left;
//            Vertex right = middlePair.right;
//            
//            if(k == 0) {
//                root = merge(merge(middle, left), right);
//                return;
//            }
//            long leftSize = left != null ? left.size : 0;
//            
//            if(k < leftSize) {
//                VertexPair rightPair = split(left, k + 1);
//                root = merge(merge(merge(rightPair.left, middle), rightPair.right), right);
//            } else if(k == leftSize) {
//                root = merge(merge(left, middle), right);
//            } else {
//                VertexPair rightPair = split(right, k - (int)leftSize + 1);
//                root = merge(merge(merge(left, rightPair.left), middle), rightPair.right);
//            }
//            
////            VertexPair rightPair = split(root, j + 1);
////            VertexPair leftPair = split(root, i);
////            root = merge(leftPair.left, rightPair.right);
////            VertexPair middlePair = split(root, k + 1);
////            root = merge(merge(root.left, middlePair.right), root.right);
//            //Vertex tempRoot = root;
//            VertexPair pair1 = split(root, j + 1);
//            System.out.println("Pair 1 traversal:");
//            System.out.println(inOrderTraverse(pair1.left));
//            System.out.println(inOrderTraverse(pair1.right));
//            VertexPair pair2 = split(pair1.left, i);
//            //System.out.printf("pair 1: %s, %s\n", pair1.left != null ? pair1.left.key : "---", pair1.right != null ? pair1.right.key : "---");
//            //System.out.printf("pair 2: %s, %s\n", pair2.left != null ? pair2.left.key : "---", pair2.right != null ? pair2.right.key : "---");
//            System.out.println("Pair 2 traversal:");
//            System.out.println(inOrderTraverse(pair2.left));
//            System.out.println(inOrderTraverse(pair2.right));
//            Vertex middle = merge(pair2.left, pair1.right);            
//            System.out.println("Traverse middle:");
//            System.out.println(inOrderTraverse(middle));
//            //Vertex middle = pair2.right;
//            VertexPair pair3 = split(middle, k + 1);
//            System.out.println("Pair 3 traversal:");
//            System.out.println(inOrderTraverse(pair3.left));
//            System.out.println(inOrderTraverse(pair3.right));
//            //root = merge(pair2.left, root);
//            //VertexPair middlePair = split(root, k + 1);
//            root = merge(merge(pair3.left, pair2.right), pair3.right);
        }

        String inOrderTraverse(Vertex root) {
            StringBuilder sb = new StringBuilder();
            if (root != null) {
                sb.append(inOrderTraverse(root.left));
                sb.append(root.size).append("-").append(root.key).append(" ");
                sb.append(inOrderTraverse(root.right));
            }
            return sb.toString();
        }

        String result() {
            return inOrderTraverse(root);
        }

        void printTree(Vertex root) {
            if (root == null) {
                return;
            }
            if (root.left != null) {
                printTree(root.left);
            }
            System.out.print(root.key);
            if (root.right != null) {
                printTree(root.right);
            }
        }

        void printTree() {
            printTree(root);
            System.out.println();
        }

        Rope(String s) {
            this.s = s;
            root = insert(root, s, 0, s.length());
        }
    }

    public static void main(String[] args) throws IOException {
        new RopeProblem().run();
    }

    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        Rope rope = new Rope(in.next());
        for (int q = in.nextInt(); q > 0; q--) {
            int i = in.nextInt();
            int j = in.nextInt();
            int k = in.nextInt();
            rope.process(i, j, k);
        }
        //out.println(rope.result());
        rope.printTree();
        out.close();
    }
}
