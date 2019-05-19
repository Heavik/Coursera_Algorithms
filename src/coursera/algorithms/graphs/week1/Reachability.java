package coursera.algorithms.graphs.week1;

import java.util.ArrayList;
import java.util.Scanner;

public class Reachability {
    
    private static boolean[] visited;
    
    private static void Dfs(ArrayList<Integer>[] adj, int vertex) {
        visited[vertex] = true;
        
        for(Integer v : adj[vertex]) {
            if(!visited[v]) {
                Dfs(adj, v);
            }
        }
    }
    
    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
        visited = new boolean[adj.length];
        Dfs(adj, x);
        return visited[y] ? 1 : 0;
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
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(reach(adj, x, y));
    }
}