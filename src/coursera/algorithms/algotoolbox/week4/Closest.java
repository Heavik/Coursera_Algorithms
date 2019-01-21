package coursera.algorithms.algotoolbox.week4;

import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class Closest {

    static class Point {
        long x, y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    static double calculateDistance(Point p1, Point p2) {
        
        long xDiff = p1.x - p2.x;
        long yDiff = p1.y - p2.y;
        
        return sqrt(xDiff * xDiff + yDiff * yDiff);
    }
    
    static double bruteForceMinDistance(Point[] points, int left, int right) {
        double result = Double.POSITIVE_INFINITY;
        
        for(int i = left; i <= right; i++) {
            for(int j = i + 1; j <= right; j++) {
                result = min(result, calculateDistance(points[i], points[j]));
            }
        }
        
        return result;
    }
    
    static double totalMinDistance(Point[] points, int left, int right, int mid, double wide) {
        
        List<Point> filteredPoints = new ArrayList<>();
        double separateLine = (points[mid].x + points[mid + 1].x) / 2;
        
        for(int i = left; i <= right; i++) {
            if(abs(points[i].x - separateLine) <= wide) {
                filteredPoints.add(points[i]);
            }
        }
      
        filteredPoints.sort((Point p1, Point p2) -> {
            if(p1.y > p2.y) return 1;
            else if(p1.y < p2.y) return -1;
            else return 0;
        });
              
        double result = wide;
        
        for(int i = 0; i < filteredPoints.size(); i++) {
            for(int j = i + 1; j < min(filteredPoints.size(), 8); j++) {
                result = min(result, calculateDistance(filteredPoints.get(i), filteredPoints.get(j)));
            }
        }
        
        return result;
    }
    
    static double recursiveMinDistance(Point[] points, int left, int right) {
        
        if(right - left <= 3) {
            return bruteForceMinDistance(points, left, right);
        }
        
        int mid = left + (right - left) / 2;
        
        double leftMinDistance = recursiveMinDistance(points, left, mid);
        double rightMinDistance = recursiveMinDistance(points, mid + 1, right);
        
        double minSeparatedDistance = min(leftMinDistance, rightMinDistance);       
        double totalMindistance = totalMinDistance(points, left, right, mid, minSeparatedDistance);
        
        return min(totalMindistance, minSeparatedDistance);
    }
    
    static double minimalDistance(Point[] points) {
        
        Arrays.sort(points, (Point p1, Point p2) -> {
            if(p1.x > p2.x) return 1;
            else if(p1.x < p2.x) return -1;
            else return 0;
        });
        
        return recursiveMinDistance(points, 0, points.length - 1);
    }

    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);
        int n = nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = nextInt();
            int y = nextInt();
            points[i] = new Point(x, y); 
        }
        System.out.printf("%.4f\n", minimalDistance(points));
        writer.close();
    }

    static BufferedReader reader;
    static PrintWriter writer;
    static StringTokenizer tok = new StringTokenizer("");


    static String next() {
        while (!tok.hasMoreTokens()) {
            String w = null;
            try {
                w = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (w == null)
                return null;
            tok = new StringTokenizer(w);
        }
        return tok.nextToken();
    }

    static int nextInt() {
        return Integer.parseInt(next());
    }
}