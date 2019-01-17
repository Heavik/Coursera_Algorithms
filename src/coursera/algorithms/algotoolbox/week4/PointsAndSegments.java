package coursera.algorithms.algotoolbox.week4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PointsAndSegments {

    private static void fillArray(Point[] arr, Point[] source, int startIndex) {

        for (int i = 0; i < source.length; i++) {
            arr[startIndex] = source[i];
            startIndex++;
        }
    }

    private static int[] fastCountSegments(Point[] starts, Point[] ends, Point[] points) {
        int[] cnt = new int[points.length];

        Point[] allPoints = new Point[starts.length + ends.length + points.length];

        fillArray(allPoints, starts, 0);
        fillArray(allPoints, ends, starts.length);
        fillArray(allPoints, points, starts.length + ends.length);

        Arrays.sort(allPoints, (Point p1, Point p2) -> {
            if (p1.coordinate != p2.coordinate) {
                return p1.coordinate - p2.coordinate;
            }

            return p1.category.value - p2.category.value;
        });

        int count = 0;
        Map<Integer, Integer> pointCounts = new HashMap<>();
        for (int i = 0; i < allPoints.length; i++) {
            PointCategory currentCategory = allPoints[i].category;
            if (currentCategory == PointCategory.LEFT) {
                count++;
            } else if (currentCategory == PointCategory.RIGHT) {
                count--;
            } else {
                pointCounts.put(allPoints[i].coordinate, count);
            }
        }

        for (int i = 0; i < points.length; i++) {
            cnt[i] = pointCounts.get(points[i].coordinate);
        }

        return cnt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        Point[] starts = new Point[n];
        Point[] ends = new Point[n];
        Point[] points = new Point[m];
        for (int i = 0; i < n; i++) {
            starts[i] = new Point(scanner.nextInt(), PointCategory.LEFT);
            ends[i] = new Point(scanner.nextInt(), PointCategory.RIGHT);
        }
        for (int i = 0; i < m; i++) {
            points[i] = new Point(scanner.nextInt(), PointCategory.POINT);
        }
        int[] cnt = fastCountSegments(starts, ends, points);
        StringBuilder sb = new StringBuilder();
        for (int x : cnt) {
            sb.append(x);
            sb.append(" ");
        }
        System.out.print(sb.toString());
    }

    private enum PointCategory {
        LEFT(1),
        POINT(2),
        RIGHT(3);

        int value;

        PointCategory(int value) {
            this.value = value;
        }
    }

    private static class Point {
        int coordinate;
        PointCategory category;

        public Point(int coordinate, PointCategory category) {
            this.coordinate = coordinate;
            this.category = category;
        }
    }
}