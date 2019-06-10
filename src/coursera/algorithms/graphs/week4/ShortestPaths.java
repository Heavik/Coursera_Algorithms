package coursera.algorithms.graphs.week4;

import java.util.*;

public class ShortestPaths {

    private static void bellmanFord(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, long[] dist) {
        for(int i = 0; i < adj.length; i++) {
            int costIndex = 0;
            for(int v : adj[i]) {
                long travelCost = cost[i].get(costIndex);
                if(dist[i] < Long.MAX_VALUE && dist[v] > dist[i] + travelCost) {
                    dist[v] = dist[i] + travelCost;
                }
                costIndex++;
            }
        }
    }
    
    private static void markAllReachebleFromStart(ArrayList<Integer>[] adj, int[] reachable, int s) {
        reachable[s] = 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        while(!queue.isEmpty()) {
            int traversed = queue.poll();
            for(int v : adj[traversed]) {
                if(reachable[v] == 0) {
                    reachable[v] = 1;
                    queue.offer(v);
                }
            }
        }
    }
    
    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, long[] distance, int[] reachable, int[] shortest) {
        distance[s] = 0;
        
        for(int i = 0; i < adj.length - 1; i++) {
            bellmanFord(adj, cost, distance);
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < adj.length; i++) {
            int costIndex = 0;
            for(int v : adj[i]) {
                long travelCost = cost[i].get(costIndex);
                if(distance[i] < Long.MAX_VALUE && distance[v] > distance[i] + travelCost) {
                    shortest[v] = 0;
                    queue.offer(v);
                }
                costIndex++;
            }
        }

        while(!queue.isEmpty()) {
            int traversed = queue.poll();
            for(Integer v : adj[traversed]) {
                if(reachable[v] == 0) {
                    reachable[v] = 1;
                    shortest[v] = 0;
                    queue.offer(v);
                }
            }
        }
        
        markAllReachebleFromStart(adj, reachable, s);
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
        int s = scanner.nextInt() - 1;
        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adj, cost, s, distance, reachable, shortest);
        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }
}