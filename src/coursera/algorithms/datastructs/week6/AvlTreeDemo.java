package coursera.algorithms.datastructs.week6;

import java.util.ArrayDeque;
import java.util.Queue;

public class AvlTreeDemo {

    static class Node {

        int key;
        Node left;
        Node right;
        Node parent;

        public Node(int key, Node left, Node right, Node parent) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public Node(int key) {
            this(key, null, null, null);
        }

        public Node() {
            this(0);
        }

        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }
    }

    static class BinarySearchTree {

        Node root;

        public BinarySearchTree(int key) {
            this.root = new Node(key);
        }

        public BinarySearchTree() {
            this.root = null;
        }

        public Node find(int key) {
            return find(root, key);
        }

        private Node find(Node vertex, int key) {
            if (vertex == null) {
                return null;
            }

            if (vertex.key == key) {
                return vertex;
            }

            if (key < vertex.key && vertex.left != null) {
                return find(vertex.left, key);
            }

            if (key >= vertex.key && vertex.right != null) {
                return find(vertex.right, key);
            }

            return vertex;
        }

        public boolean isBst() {
            return isBst(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        private boolean isBst(Node vertex, int min, int max) {
            if (vertex == null) {
                return true;
            }

            if (vertex.key < min || vertex.key > max) {
                return false;
            }

            return isBst(vertex.left, min, vertex.key - 1) && isBst(vertex.right, vertex.key, max);
        }

        public Node insert(int key) {
            Node closeParent = find(root, key);
            Node newNode = new Node(key, null, null, closeParent);
            if (closeParent == null) {
                root = newNode;
                return root;
            }
            if (key < closeParent.key) {
                closeParent.left = newNode;
            } else {
                closeParent.right = newNode;
            }
            return newNode;
        }

        public Node remove(int key) {
            Node foundNode = find(root, key);
            if (foundNode == null || foundNode.key != key) {
                return null;
            }

            if (foundNode.isLeaf()) {
                return removeNode(foundNode, null);
            }

            if (foundNode.left == null) {
                return removeNode(foundNode, foundNode.right);
            }

            if (foundNode.right == null) {
                return removeNode(foundNode, foundNode.left);
            }

            Node closestNode = findClosest(foundNode);
            foundNode.key = closestNode.key;
            removeNode(closestNode, null);
            return closestNode;
        }

        private Node removeNode(Node vertex, Node child) {
            if (vertex.parent == null) {
                root = child;
                if(root != null) {
                    root.parent = null;
                }
                return vertex;
            }
            if (vertex.key < vertex.parent.key) {
                vertex.parent.left = child;
            } else {
                vertex.parent.right = child;
            }
            return child;
        }
        
        protected Node findClosest(Node vertex) {
            Node min = vertex.right;
            while (min.left != null) {
                min = min.left;
            }
            return min;
        }

        public void breathTraverse() {
            Queue<Node> queue = new ArrayDeque<>();
            if (root != null) {
                queue.offer(root);
            }

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

        public void preOrderTraverse() {
            preOrderTraverse(root);
            System.out.println();
        }

        private void preOrderTraverse(Node vertex) {
            if (vertex != null) {
                System.out.printf("%d ", vertex.key);
                preOrderTraverse(vertex.left);
                preOrderTraverse(vertex.right);
            }
        }
    }

    static class AvlTree extends BinarySearchTree {

        public AvlTree(int key) {
            super(key);
        }

        public AvlTree() {
            super();
        }

        private int getHeight(Node vertex) {
            if (vertex == null) {
                return 0;
            }

            return Math.max(getHeight(vertex.left), getHeight(vertex.right)) + 1;
        }

        private int getBalanceFactor(Node vertex) {
            if (vertex == null) {
                return 0;
            }

            return getHeight(vertex.left) - getHeight(vertex.right);
        }

        private Node rightRotate(Node vertex) {
            if(vertex == null) {
                return vertex;
            }
            
            Node pivot = vertex.left;
            if(pivot == null) {
                return vertex;
            }
            
            Node vertexParent = vertex.parent;
            boolean isLeftChild = (vertexParent != null) && vertexParent.left == vertex;
            
            vertex.left = pivot.right;
            pivot.right = vertex;
            
            vertex.parent = pivot;
            pivot.parent = vertexParent;
            
            if(vertex.left != null) {
                vertex.left.parent = vertex;
            }
            
            if(vertexParent != null) {
                if(isLeftChild) {
                    vertexParent.left = pivot;
                } else {
                    vertexParent.right = pivot;
                }
            }

            return pivot;
        }

        private Node leftRotate(Node vertex) {
            
            if(vertex == null) {
                return vertex;
            }
            
            Node pivot = vertex.right;
            
            if(pivot == null) {
                return vertex;
            }
            Node vertexParent = vertex.parent;
            boolean isLeftChild = (vertexParent != null) && vertexParent.left == vertex;
            
            vertex.right = pivot.left;
            pivot.left = vertex;
            
            vertex.parent = pivot;
            pivot.parent = vertexParent;
            
            if(vertex.right != null) {
                vertex.right.parent = vertex;
            }

            if(vertexParent != null) {
                if(isLeftChild) {
                    vertexParent.left = pivot;
                } else {
                    vertexParent.right = pivot;
                }
            }
            return pivot;
        }

        private Node checkAndRotate(Node vertex, int key) {
            int balanceFactor = getBalanceFactor(vertex);

            if (balanceFactor > 1 && getBalanceFactor(vertex.left) == -1) {
               leftRotate(vertex.left);
               rightRotate(vertex);
            } else if (balanceFactor < -1 && getBalanceFactor(vertex.right) == 1) {
               rightRotate(vertex.right);
               leftRotate(vertex);
            } else if (balanceFactor > 1) {
               rightRotate(vertex);
            } else if (balanceFactor < -1) {
               leftRotate(vertex);
            }
            
            if(vertex.parent != null) {
                return checkAndRotate(vertex.parent, key);
            }
            return vertex;
        }
        
        @Override
        protected Node findClosest(Node vertex) {
            Node max = vertex.left;
            while(max.right != null) {
                max = max.right;
            }
            return max;
        }

        @Override
        public Node insert(int key) {
            Node vertex = super.insert(key);
            root = checkAndRotate(vertex, key);
            return vertex;
        }
        
        @Override
        public Node remove(int key) {
            Node removed = super.remove(key);
            if(removed == null) {
                return removed;
            }
            
            root = checkAndRotate(removed.parent, key);
            return removed;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);
        System.out.println(tree.isBst());
        tree.breathTraverse();

        tree.remove(20);
        System.out.println(tree.isBst());
        tree.breathTraverse();

        tree.remove(30);
        System.out.println(tree.isBst());
        tree.breathTraverse();

        tree.remove(50);
        System.out.println(tree.isBst());
        tree.breathTraverse();

        AvlTree tree2 = new AvlTree();
        tree2.insert(10);
        tree2.insert(20);
        tree2.insert(30);
        tree2.insert(40);
        tree2.insert(50);
        tree2.insert(25);
        tree2.insert(75);
        tree2.insert(5);
        System.out.println(tree2.isBst());
        tree2.breathTraverse();
        tree2.remove(30);
        tree2.remove(40);
        tree2.remove(25);
        System.out.println(tree2.isBst());
        tree2.breathTraverse();
    }
}
