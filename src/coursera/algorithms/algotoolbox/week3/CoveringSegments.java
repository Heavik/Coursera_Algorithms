/* Collecting Signatures
 * Description: Given a set of 𝑛 segments {[𝑎0, 𝑏0], [𝑎1, 𝑏1], . . . , [𝑎𝑛−1, 𝑏𝑛−1]} with integer coordinates on a line, find 
 * the minimum number 𝑚 of points such that each segment contains at least one point. That is, find a
 * set of integers 𝑋 of the minimum size such that for any segment [𝑎𝑖, 𝑏𝑖] there is a point 𝑥 ∈ 𝑋 such
 * that 𝑎𝑖 ≤ 𝑥 ≤ 𝑏𝑖.
 *
 * Input: The first line of the input contains the number 𝑛 of segments. Each of the following 𝑛 lines
 * contains two integers 𝑎𝑖 and 𝑏𝑖 (separated by a space) defining the coordinates of endpoints of the 𝑖-th
 * segment.
 *
 * Output: Output the minimum number 𝑚 of points on the first line and the integer coordinates
 * of 𝑚 points (separated by spaces) on the second line. You can output the points in any order. If there
 * are many such sets of points, you can output any set. (It is not difficult to see that there always exist
 * a set of points of the minimum size such that all the coordinates of the points are integers.)
 *
 * Constraints: 1 ≤ 𝑛 ≤ 100; 0 ≤ 𝑎𝑖 ≤ 𝑏𝑖 ≤ 10^9 for all 0 ≤ 𝑖 < 𝑛.
 */
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
