//package coursera.algorithms.graphs.week2;

import java.util.ArrayList;
import java.util.Scanner;

public class Acyclicity {
    
    private static boolean[] visited;
    private static boolean[] recursionStack;
    
    private static boolean isCyclic(ArrayList<Integer>[] adj, int vertex) {
        if(recursionStack[vertex]) {
            return true;
        }
        
        if(visited[vertex]) {
            return false;
        }
        
        visited[vertex] = true;
        recursionStack[vertex] = true;
        
        for(Integer v : adj[vertex]) {
            if(isCyclic(adj, v)) {
                return true;
            }
        }
        
        recursionStack[vertex] = false;
        return false;
    }
    
    private static int acyclic(ArrayList<Integer>[] adj) {
        visited = new boolean[adj.length];
        recursionStack = new boolean[adj.length];
        
        for(int i = 0; i < adj.length; i++) {
            if(isCyclic(adj, i)) {
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
        }
        System.out.println(acyclic(adj));
    }
}