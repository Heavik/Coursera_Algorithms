package coursera.algorithms.graphs.week2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class StronglyConnected {
    
    private static boolean[] visited;
    private static Stack<Integer> stack;
    
    private static void dfs(ArrayList<Integer>[] adj, int vertex, boolean trackVertex) {
        visited[vertex] = true;
        
        for(Integer v : adj[vertex]) {
            if(!visited[v]) {
                dfs(adj, v, trackVertex);
            }
        }
        
        if(trackVertex) {
            stack.push(vertex);
        }
    }
    
    private static ArrayList<Integer>[] createReversedGraph(ArrayList<Integer>[] adj) {
        ArrayList<Integer>[] reversed = (ArrayList<Integer>[])new ArrayList[adj.length];
        
        for(int i = 0; i < adj.length; i++) {
            reversed[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < adj.length; i++) {
            for(Integer vertex : adj[i]) {
                reversed[vertex].add(i);
            }
        }
        
        return reversed;
    }
    
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
        visited = new boolean[adj.length];
        stack = new Stack<>();
        for(int i = 0; i < adj.length; i++) {
            if(!visited[i]) {
                dfs(adj, i, true);
            }
        }
        
        ArrayList<Integer>[] reversedGraph = createReversedGraph(adj);        
        for(int i = 0; i < adj.length; i++) {
            visited[i] = false;
        }
        int count = 0;
        while(!stack.empty()) {
            int vertex = stack.pop();
            if(!visited[vertex]) {
                dfs(reversedGraph, vertex, false);
                count++;
            }
        }
        return count;
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
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}