package coursera.algorithms.algotoolbox.week3;

import java.util.*;

public class CoveringSegments {

    private static void sortSegments(Segment[] segments) {
        
        Arrays.sort(segments, (Segment segment1, Segment segment2) -> segment1.end - segment2.end);
    }
    
    private static List<Integer> optimalPoints(Segment[] segments) {
        
        sortSegments(segments);
        List<Integer> points = new ArrayList<>();
        
        int point = segments[0].end;
        points.add(point);

        for (int i = 1; i < segments.length; ++i) {
            if (point < segments[i].start || point > segments[i].end) { 
                point = segments[i].end;
                points.add(point);
            }
        }
        
        return points;
    }

    private static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        @Override
        public String toString() {
            
            return String.format("Start=%d End=%d", this.start, this.end);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        List<Integer> points = optimalPoints(segments);
        System.out.println(points.size());
        for (int point : points) {
            System.out.print(point + " ");
        }
    }
}