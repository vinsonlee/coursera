import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        Point[] pointsBySlope = new Point[N];

        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            pointsBySlope[i] = points[i];
            points[i].draw();
        }

        assert points.length == N;

        // natually sort array
        Arrays.sort(points);

        for (int i = 0; i < N; i++) {
            // Think of p as origin
            Point p = points[i];

            // For each other point q, determine the slope it makes with p.
            // Sort the points according to the slopes they makes with p.
            Arrays.sort(pointsBySlope);
            Arrays.sort(pointsBySlope, p.SLOPE_ORDER);

            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with p, are collinear.

            // There can be multiple segments for each point.

            int j = 1;
            while (j < N - 1) {
                double slope = p.slopeTo(pointsBySlope[j]);

                if (slope == p.slopeTo(pointsBySlope[j+1])) {
                    // Found the start of a segment.
                    // Now look for the end of the segment.
                    int start = j;
                    int end = j+1;
                    for (int k = j+1; k < N; k++) {
                        if (slope == p.slopeTo(pointsBySlope[k])) {
                            end = k;
                        } else {
                            break;
                        }
                    }

                    // A segment must be 4 or more points.
                    // A segment must not be a subsegment.
                    if ((end - start >= 2)
                        && (p.compareTo(pointsBySlope[start]) < 0)) {
                        StdOut.print(p);
                        for (int l = start; l <= end; l++) {
                            StdOut.print(" -> " + pointsBySlope[l]);
                        }
                        StdOut.println();
                        p.drawTo(pointsBySlope[end]);
                    }

                    j = end + 1;
                } else {
                    j++;
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);
    }
}
