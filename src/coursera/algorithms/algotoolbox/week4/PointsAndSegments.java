/* Organizing a Lottery
 * Description: You are given a set of points on a line and a set of segments on a line. The goal is to compute, for
 * each point, the number of segments that contain this point.
 *
 * Input: The first line contains two non-negative integers 𝑠 and 𝑝 defining the number of segments
 * and the number of points on a line, respectively. The next 𝑠 lines contain two integers 𝑎𝑖, 𝑏𝑖 defining
 * the 𝑖-th segment [𝑎𝑖, 𝑏𝑖]. The next line contains 𝑝 integers defining points 𝑥1, 𝑥2,..., 𝑥𝑝.
 *
 * Output: Output 𝑝 non-negative integers 𝑘0, 𝑘1, . . . , 𝑘[𝑝−1] where 𝑘𝑖 is the number of segments which contain 𝑥𝑖.
 *
 * Constraints: 1 ≤ 𝑠,𝑝 ≤ 50000; −10^8 ≤ 𝑎𝑖 ≤ 𝑏𝑖 ≤ 10^8 for all 0 ≤ 𝑖 < 𝑠; −10^8 ≤ 𝑥𝑗 ≤ 10^8 for all 0 ≤ 𝑗 < 𝑝.
 */
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