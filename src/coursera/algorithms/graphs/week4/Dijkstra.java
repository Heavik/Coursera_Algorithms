package coursera.algorithms.graphs.week4;

import java.util.*;

public class Dijkstra {
    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        int[] dist = new int[adj.length];
        for(int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[s] = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>(dist.length, (o1, o2) -> {
            if(o1 < o2) return -1;
            if(o1 > o2) return 1;
            return 0;
        });
        for(int i = 0; i < adj.length; i++) {
            queue.offer(i);
        }
        while(!queue.isEmpty()) {
            int traversed = queue.poll();
            int costIndex = 0;
            for(Integer v : adj[traversed]) {
                int travelCost = cost[traversed].get(costIndex);
                if(dist[traversed] < Integer.MAX_VALUE && dist[v] > dist[traversed] + travelCost) {
                    dist[v] = dist[traversed] + travelCost;
                    queue.offer(v);
                }
                costIndex++;
            }
        }
        return dist[t] == Integer.MAX_VALUE ? -1 : dist[t];
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y));
    }
}
