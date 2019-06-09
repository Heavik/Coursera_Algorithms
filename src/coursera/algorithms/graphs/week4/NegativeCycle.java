package coursera.algorithms.graphs.week4;

import java.util.ArrayList;
import java.util.Scanner;

public class NegativeCycle {
    
    private static int[] dist;
    private static int[] prev;
    
    private static void belmanFord(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        for(int i = 0; i < adj.length; i++) {
            int costIndex = 0;
            for(int v : adj[i]) {
                int travelCost = cost[i].get(costIndex);
                if(dist[i] < Integer.MAX_VALUE && dist[v] > dist[i] + travelCost) {
                    dist[v] = dist[i] + travelCost;
                    prev[v] = i;
                }
                costIndex++;
            }
        }
    }
    
    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        dist = new int[adj.length];
        prev = new int[adj.length];
        for(int i = 0; i < adj.length; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        
        for(int i = 0; i < adj.length - 1; i++) {
            if(dist[i] == Integer.MAX_VALUE) {
                dist[i] = 0;
            }
            belmanFord(adj, cost);
        }
        
        for(int i = 0; i < adj.length; i++) {
            int costIndex = 0;
            for(int v : adj[i]) {
                int travelCost = cost[i].get(costIndex);
                if(dist[i] < Integer.MAX_VALUE && dist[v] > dist[i] + travelCost) {
                    return 1;
                }
                costIndex++;
            }
        }               
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(negativeCycle(adj, cost));
    }
}