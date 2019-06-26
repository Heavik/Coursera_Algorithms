package coursera.algorithms.graphs.week6;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class DistWithCoords {
    private static class Impl {
        // Number of nodes
        int n;
        // Coordinates of nodes
        int[] x;
        int[] y;
        // See description of these fields in the starters for friend_suggestion
        ArrayList<Integer>[][] adj;
        ArrayList<Integer>[][] cost;
        Long[][] distance;
        ArrayList<PriorityQueue<Entry>> queues;
        boolean[][] visited;
        ArrayList<Integer> workset;
        final Long INFINITY = Long.MAX_VALUE / 4;
        
        final int INITIAL_GRAPH = 0;
        final int REVERSED_GRAPH = 1;

        Impl(int n) {
            this.n = n;
            visited = new boolean[2][n];
            x = new int[n];
            y = new int[n];
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

        // See the description of this method in the starters for friend_suggestion
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
        
        Double euclidianPotential(int x1, int y1, int x2, int y2) {
            
           return  Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }
        
        Long pi(int side, int vertex, int start, int target) {
            Double piF = euclidianPotential(x[vertex], y[vertex], x[target], y[target]);
            Double piR = euclidianPotential(x[start], y[start], x[vertex], y[vertex]);
            Long result = Math.round((piF - piR) / 2);
            result *= (1 - side) + -1 * side;
            return result;
        } 

        // See the description of this method in the starters for friend_suggestion
        void visit(int side, int traversed, Long dist) {
            ArrayList<Integer>[] graph = adj[side];
            Long[] distances = distance[side];
            PriorityQueue<Entry> queue = queues.get(side);
            if(distances[traversed] > dist) {
                distances[traversed] = dist;
                queue.offer(new Entry(dist, traversed));
            }
        }
        
         Long shortestPath(int start, int target, int side) {
            Long result = INFINITY;
            for(Integer u : workset) {
                if(distance[INITIAL_GRAPH][u] + distance[REVERSED_GRAPH][u] >= INFINITY) {
                    continue;
                }
                if(distance[INITIAL_GRAPH][u] + distance[REVERSED_GRAPH][u] < result) {
                    result = distance[INITIAL_GRAPH][u] + distance[REVERSED_GRAPH][u];
                }
            }
            Long potential = -pi(side, start, start, target) + pi(side, target, start, target);
            potential *= (1 - side) + -1 * side;
            
            return result - potential;
        }

        // Returns the distance from s to t in the graph.
        Long query(int s, int t) {
            clear();
            visit(INITIAL_GRAPH, s, 0L);
            visit(REVERSED_GRAPH, t, 0L);
            
            PriorityQueue<Entry> initialQueue = queues.get(INITIAL_GRAPH);
            PriorityQueue<Entry> reversedQueue = queues.get(REVERSED_GRAPH);
            
            while(!initialQueue.isEmpty() && !reversedQueue.isEmpty()) {
                Entry entry = initialQueue.poll();               
                int costIndex = 0;
                for(Integer v : adj[INITIAL_GRAPH][entry.node]) {
                    if(!visited[INITIAL_GRAPH][v]) {
                        int travelCost = cost[INITIAL_GRAPH][entry.node].get(costIndex);
                        Long potential = -pi(INITIAL_GRAPH, entry.node, s, t) + pi(INITIAL_GRAPH, v, s, t);
                        Long potentialDistance = distance[INITIAL_GRAPH][entry.node] + travelCost + potential;
                        visit(INITIAL_GRAPH, v, potentialDistance);
                    }
                    costIndex++;
                }
                
                workset.add(entry.node);
                visited[INITIAL_GRAPH][entry.node] = true;

                if(visited[REVERSED_GRAPH][entry.node]) {
                    return shortestPath(s, t, REVERSED_GRAPH);
                }
                
                Entry reversedEntry = reversedQueue.poll();
                costIndex = 0;
                for(Integer v : adj[REVERSED_GRAPH][reversedEntry.node]) {
                    if(!visited[REVERSED_GRAPH][v]) {
                        int travelCost = cost[REVERSED_GRAPH][reversedEntry.node].get(costIndex);
                        Long potential = -pi(REVERSED_GRAPH, reversedEntry.node, s, t) + pi(REVERSED_GRAPH, v, s, t);
                        Long potentialDistance = distance[REVERSED_GRAPH][reversedEntry.node] + travelCost + potential;
                        visit(REVERSED_GRAPH, v, potentialDistance);
                    }
                    costIndex++;
                }
                
                workset.add(reversedEntry.node);
                visited[REVERSED_GRAPH][reversedEntry.node] = true;
                
                if(visited[INITIAL_GRAPH][reversedEntry.node]) {
                    return shortestPath(s, t, INITIAL_GRAPH);
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
        Impl DistWithCoords = new Impl(n);
        DistWithCoords.adj = (ArrayList<Integer>[][])new ArrayList[2][];
        DistWithCoords.cost = (ArrayList<Integer>[][])new ArrayList[2][];
        for (int side = 0; side < 2; ++side) {
            DistWithCoords.adj[side] = (ArrayList<Integer>[])new ArrayList[n];
            DistWithCoords.cost[side] = (ArrayList<Integer>[])new ArrayList[n];
            for (int i = 0; i < n; i++) {
                DistWithCoords.adj[side][i] = new ArrayList<Integer>();
                DistWithCoords.cost[side][i] = new ArrayList<Integer>();
            }
        }

        for (int i = 0; i < n; i++) { 
            int x, y;
            x = in.nextInt();
            y = in.nextInt();
            DistWithCoords.x[i] = x;
            DistWithCoords.y[i] = y;
        }

        for (int i = 0; i < m; i++) {
            int x, y, c;
            x = in.nextInt();
            y = in.nextInt();
            c = in.nextInt();
            DistWithCoords.adj[0][x - 1].add(y - 1);
            DistWithCoords.cost[0][x - 1].add(c);
            DistWithCoords.adj[1][y - 1].add(x - 1);
            DistWithCoords.cost[1][y - 1].add(c);
        }

        int t = in.nextInt();

        for (int i = 0; i < t; i++) {
            int u, v;
            u = in.nextInt();
            v = in.nextInt();
            System.out.println(DistWithCoords.query(u-1, v-1));
        }
    }
}