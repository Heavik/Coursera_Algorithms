package coursera.algorithms.graphs.week5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Clustering {
    
    static class Edge implements Comparable<Edge>{
        int src;
        int dest;
        double weight;
        
        public Edge(int src, int dest, double weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge anotherEdge) {
            if(this.weight < anotherEdge.weight) return -1;
            if(this.weight > anotherEdge.weight) return 1;
            return 0;
        }
    }
    
    static class VertexSubset {
        int parent;
        int rank;
        
        public VertexSubset(int parent, int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }
    
    private static double calculateWeight(int x1, int y1, int x2, int y2) {
        
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    
    private static List<Edge> getEdges(int[] x, int[] y, int numberOfVertices) {
        List<Edge> edges = new ArrayList<>();
        for(int i = 0; i < numberOfVertices; i++) {
            for(int j = 0; j < numberOfVertices; j++) {
                if(i != j) {
                    edges.add(new Edge(i, j, calculateWeight(x[i], y[i], x[j], y[j])));
                }
            }
        }
        
        return edges;
    }
    
    private static int find(VertexSubset[] subsets, int vertex) {
        
        if(subsets[vertex].parent != vertex) {
            subsets[vertex].parent = find(subsets, subsets[vertex].parent);
        }
        
        return subsets[vertex].parent;
    }
    
    private static void union(VertexSubset[] subsets, int firstSubset, int secondSubset) {
        int firstRoot = find(subsets, firstSubset);
        int secondRoot = find(subsets, secondSubset);
        
        if(subsets[firstRoot].rank < subsets[secondRoot].rank) {
            subsets[firstRoot].parent = secondRoot;
        } else if(subsets[firstRoot].rank > subsets[secondRoot].rank) {
            subsets[secondRoot].parent = firstRoot;
        } else {
            subsets[secondRoot].parent = firstRoot;
            subsets[firstRoot].rank++;
        }
    }
    
    private static double clustering(int[] x, int[] y, int k) {
        List<Double> result = new ArrayList<>(); 
        int numberOfVertices = x.length;
        List<Edge> edges = getEdges(x, y, numberOfVertices);
        Collections.sort(edges);
        VertexSubset[] subsets = new VertexSubset[numberOfVertices];
        for(int i = 0; i < numberOfVertices; i++) {
            subsets[i] = new VertexSubset(i, 0);
        }
        
        for(Edge nextEdge : edges) {
                        
            int srcSubset = find(subsets, nextEdge.src);
            int destSubset = find(subsets, nextEdge.dest);
            
            if(srcSubset != destSubset) {
                result.add(nextEdge.weight);
                union(subsets, srcSubset, destSubset);
            }
        }
        
        Collections.sort(result, (w1, w2) -> {
            if(w1 < w2) return 1;
            if(w2 < w1) return -1;
            return 0;
        });
        return result.get(k - 2);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        int k = scanner.nextInt();
        System.out.printf("%.9f\n", clustering(x, y, k));
    }
}