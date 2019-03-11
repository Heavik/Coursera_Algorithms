package coursera.algorithms.datastructs.week6;

import java.util.ArrayDeque;
import java.util.Queue;

public class SplayTreeDemo {

    static class SubtreePair {

        Node left;
        Node right;

        public SubtreePair(Node left, Node right) {
            this.left = left;
            this.right = right;
        }
    }

    static class Node {

        private int key;
        private Node left;
        private Node right;
        private Node parent;

        public Node(int key) {
            this(key, null, null, null);
        }

        public Node(int key, Node left, Node right, Node parent) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    static class SplayTree {

        private Node root;

        public SplayTree(int key) {
            this.root = new Node(key);
        }

        private void setParent(Node child, Node parent) {
            if (child != null) {
                child.parent = parent;
            }
        }

        private void keepParent(Node vertex) {
            setParent(vertex.left, vertex);
            setParent(vertex.right, vertex);
        }

        private void rotate(Node parent, Node child) {
            Node grandParent = parent.parent;
            if (grandParent != null) {
                if (grandParent.left == parent) {
                    grandParent.left = child;
                } else {
                    grandParent.right = child;
                }
            }

            if (parent.left == child) {
                parent.left = child.right;
                child.right = parent;
            } else {
                parent.right = child.left;
                child.left = parent;
            }

            keepParent(child);
            keepParent(parent);
            child.parent = grandParent;
        }

        private Node splay(Node vertex) {
            if (vertex.parent == null) {
                return vertex;
            }
            Node parent = vertex.parent;
            Node grandParent = parent.parent;
            if (grandParent == null) {
                rotate(parent, vertex);
                return vertex;
            } else {
                boolean zigZig = (grandParent.left == parent) == (parent.left == vertex);
                if (zigZig) {
                    rotate(grandParent, parent);
                    rotate(parent, vertex);
                } else {
                    rotate(parent, vertex);
                    rotate(grandParent, vertex);
                }
                return splay(vertex);
            }
        }

        private Node find(Node vertex, int key) {
            if (vertex == null) {
                return null;
            }
            if (key == vertex.key) {
                return vertex;
            }
            if (key < vertex.key && vertex.left != null) {
                return find(vertex.left, key);
            }
            if (key > vertex.key && vertex.right != null) {
                return find(vertex.right, key);
            }
            return vertex;
        }

        public Node find(int key) {
            Node foundNode = find(root, key);
            root = splay(foundNode);
            return root;
        }

        public SubtreePair split(int key) {
            root = find(root, key);
            if (root.key == key) {
                setParent(root.left, null);
                setParent(root.right, null);
                return new SubtreePair(root.left, root.right);
            }
            if (root.key < key) {
                Node right = root.right;
                root.right = null;
                setParent(right, null);
                return new SubtreePair(root, right);
            }
            Node left = root.left;
            root.left = null;
            setParent(left, null);
            return new SubtreePair(left, root);
        }

        public Node insert(int key) {
            Node foundNode = find(root, key);
            Node newNode = new Node(key, null, null, foundNode);
            if (key < foundNode.key) {
                foundNode.left = newNode;
            } else {
                foundNode.right = newNode;
            }
            root = splay(newNode);
            keepParent(root);
            return root;
        }

        public Node merge(Node left, Node right) {
            if (right == null) {
                return left;
            }
            if (left == null) {
                return right;
            }
            left = find(left, right.key);
            left.right = right;
            right.parent = left;
            return left;
        }

        public Node remove(int key) {
            root = find(root, key);
            splay(root);
            setParent(root, null);
            setParent(root, null);
            root = merge(root.left, root.right);
            return root;
        }

        private int treeHeight(Node vertex) {
            if (vertex == null) {
                return 0;
            }

            int maxChildHeight = 0;
            int leftChildHeight = treeHeight(vertex.left);
            if (leftChildHeight > maxChildHeight) {
                maxChildHeight = leftChildHeight;
            }
            int rightChildHeight = treeHeight(vertex.right);
            if (rightChildHeight > maxChildHeight) {
                maxChildHeight = rightChildHeight;
            }

            return maxChildHeight + 1;
        }

        public int treeHeight() {
            return treeHeight(root);
        }

        private void traverseInOrder(Node vertex) {
            if (vertex != null) {
                traverseInOrder(vertex.left);
                System.out.printf("%d ", vertex.key);
                traverseInOrder(vertex.right);
            }
        }

        public void traverse() {
            traverseInOrder(root);
            System.out.println();
        }

        public void breadthTraverse() {
            if (root == null) {
                return;
            }

            Queue<Node> queue = new ArrayDeque<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                Node traversed = queue.poll();
                System.out.printf("%d ", traversed.key);

                if (traversed.left != null) {
                    queue.offer(traversed.left);
                }

                if (traversed.right != null) {
                    queue.offer(traversed.right);
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SplayTree tree = new SplayTree(0);
        tree.insert(7);
        tree.insert(1);
        tree.insert(4);
        tree.insert(5);
        tree.remove(4);
        tree.breadthTraverse();
    }
}
