package coursera.algorithms.graphs.week6;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class FriendSuggestion {
    private static class Impl {
        // Number of nodes
        int n;
        // adj[0] and cost[0] store the initial graph, adj[1] and cost[1] store the reversed graph.
        // Each graph is stored as array of adjacency lists for each node. adj stores the edges,
        // and cost stores their costs.
        ArrayList<Integer>[][] adj;
        ArrayList<Integer>[][] cost;
        // distance[0] and distance[1] correspond to distance estimates in the forward and backward searches.
        Long[][] distance;
        // Two priority queues, one for forward and one for backward search.
        ArrayList<PriorityQueue<Entry>> queues;
        // visited[v] == true if v was visited either by forward or backward search.
        boolean[][] visited;
        // List of all the nodes which were visited either by forward or backward search.
        ArrayList<Integer> workset;
        final Long INFINITY = Long.MAX_VALUE / 4;
        
        final int INITIAL_GRAPH = 0;
        final int REVERSED_GRAPH = 1;

        Impl(int n) {
            this.n = n;
            visited = new boolean[2][n];
            Arrays.fill(visited[INITIAL_GRAPH], false);            
            Arrays.fill(visited[REVERSED_GRAPH], false);
            workset = new ArrayList<Integer>();
            distance = new Long[][] {new Long[n], new Long[n]};
            for (int i = 0; i < n; ++i) {
                distance[INITIAL_GRAPH][i] = distance[REVERSED_GRAPH][i] = INFINITY;
            }
            queues = new ArrayList<PriorityQueue<Entry>>();
            queues.add(new PriorityQueue<Entry>(n));
            queues.add(new PriorityQueue<Entry>(n));
        }

        // Reinitialize the data structures before new query after the previous query
        void clear() {
            Long[] initDistnaces = distance[INITIAL_GRAPH];
            Long[] reversedDistances = distance[REVERSED_GRAPH];
            
            Arrays.fill(initDistnaces, INFINITY);
            Arrays.fill(reversedDistances, INFINITY);
            Arrays.fill(visited[INITIAL_GRAPH], false);            
            Arrays.fill(visited[REVERSED_GRAPH], false);
            
            workset.clear();
            queues.get(INITIAL_GRAPH).clear();
            queues.get(REVERSED_GRAPH).clear();
        }

        // Try to relax the distance from direction side to node v using value dist.
        void visit(int side, int traversed, Long dist) {
            ArrayList<Integer>[] graph = adj[side];
            Long[] distances = distance[side];
            PriorityQueue<Entry> queue = queues.get(side);
            if(distances[traversed] > dist) {
                distances[traversed] = dist;
                queue.offer(new Entry(distances[traversed], traversed));
            }
        }

        Long shortestPath() {
            Long result = INFINITY;
            for(Integer u : workset) {
                if(distance[INITIAL_GRAPH][u] + distance[REVERSED_GRAPH][u] >= INFINITY) {
                    continue;
                }
                if(distance[INITIAL_GRAPH][u] + distance[REVERSED_GRAPH][u] < result) {
                    result = distance[INITIAL_GRAPH][u] + distance[REVERSED_GRAPH][u];
                }
            }
            return result;
        }
        
        // Returns the distance from s to t in the graph.
        Long query(int s, int t) {
            clear();
            visit(INITIAL_GRAPH, s, 0L);
            visit(REVERSED_GRAPH, t, 0L);
            
            PriorityQueue<Entry> initialQueue = queues.get(INITIAL_GRAPH);
            PriorityQueue<Entry> reversedQueue = queues.get(REVERSED_GRAPH);
            
            while(!initialQueue.isEmpty() && !reversedQueue.isEmpty()) {
                if(!initialQueue.isEmpty()) {
                    Entry entry = initialQueue.poll();
                    int costIndex = 0;
                    for(Integer v : adj[INITIAL_GRAPH][entry.node]) {
                        int travelCost = cost[INITIAL_GRAPH][entry.node].get(costIndex);
                        visit(INITIAL_GRAPH, v, distance[INITIAL_GRAPH][entry.node] + travelCost);
                        costIndex++;
                    }
                    workset.add(entry.node);
                    visited[INITIAL_GRAPH][entry.node] = true;
                    
                    if(visited[REVERSED_GRAPH][entry.node]) {
                        return shortestPath();
                    }
                }
                
                if(!reversedQueue.isEmpty()) {
                    Entry reversedEntry = reversedQueue.poll();
                    int costIndex = 0;
                    for(Integer v : adj[REVERSED_GRAPH][reversedEntry.node]) {
                        int travelCost = cost[REVERSED_GRAPH][reversedEntry.node].get(costIndex);
                        visit(REVERSED_GRAPH, v, distance[REVERSED_GRAPH][reversedEntry.node] + travelCost);
                        costIndex++;
                    }
                    workset.add(reversedEntry.node);
                    visited[REVERSED_GRAPH][reversedEntry.node] = true;
                    
                    if(visited[INITIAL_GRAPH][reversedEntry.node]) {
                        return shortestPath();
                    }
                }
            }
 
            return -1L;
        }

        class Entry implements Comparable<Entry>
        {
            Long cost;
            int node;
          
            public Entry(Long cost, int node)
            {
                this.cost = cost;
                this.node = node;
            }
         
            public int compareTo(Entry other)
            {
                return cost < other.cost ? -1 : cost > other.cost ? 1 : 0;
            }
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        Impl bidij = new Impl(n);
        bidij.adj = (ArrayList<Integer>[][])new ArrayList[2][];
        bidij.cost = (ArrayList<Integer>[][])new ArrayList[2][];
        for (int side = 0; side < 2; ++side) {
            bidij.adj[side] = (ArrayList<Integer>[])new ArrayList[n];
            bidij.cost[side] = (ArrayList<Integer>[])new ArrayList[n];
            for (int i = 0; i < n; i++) {
                bidij.adj[side][i] = new ArrayList<Integer>();
                bidij.cost[side][i] = new ArrayList<Integer>();
            }
        }

        for (int i = 0; i < m; i++) {
            int x, y, c;
            x = in.nextInt();
            y = in.nextInt();
            c = in.nextInt();
            bidij.adj[0][x - 1].add(y - 1);
            bidij.cost[0][x - 1].add(c);
            bidij.adj[1][y - 1].add(x - 1);
            bidij.cost[1][y - 1].add(c);
        }

        int t = in.nextInt();

        for (int i = 0; i < t; i++) {
            int u, v;
            u = in.nextInt();
            v = in.nextInt();
            System.out.println(bidij.query(u-1, v-1));
        }
    }
}