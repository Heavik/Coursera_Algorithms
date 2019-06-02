package coursera.algorithms.graphs.week3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite {
    
    static enum NodeColors {
        NO_COLOR,
        WHITE,
        BLACK
    }
    
    private static int bipartite(ArrayList<Integer>[] adj) {
        NodeColors[] colors = new NodeColors[adj.length];
        for(int i = 0; i < colors.length; i++) {
            colors[i] = NodeColors.NO_COLOR;
        }
        colors[0] = NodeColors.WHITE;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while(!queue.isEmpty()) {
            int traversed = queue.poll();
            NodeColors colorTo = colors[traversed] == NodeColors.BLACK ? NodeColors.WHITE : NodeColors.BLACK;
            for(Integer v : adj[traversed]) {
                if(colors[v] != NodeColors.NO_COLOR && colors[v] != colorTo) {
                    return 0;
                }
                if(colors[v] == NodeColors.NO_COLOR) {
                    queue.offer(v);
                    colors[v] = colorTo;
                }
            }
        }
        return 1;
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
        System.out.println(bipartite(adj));
    }
}
