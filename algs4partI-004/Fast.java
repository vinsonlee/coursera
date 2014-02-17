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
        /*
        for (int i = 0; i < N; i++) {
            StdOut.println(points[i]);
        }
         */

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
            /*
            for (int j = 0; j < N; j++) {
                StdOut.print(pointsBySlope[j]);
                StdOut.print(p.slopeTo(pointsBySlope[j]) + " ");
            }
            StdOut.println();
             */

            for (int j = 1; j < N-1; j++) {
                double slope = p.slopeTo(pointsBySlope[j]);
                if (slope == p.slopeTo(pointsBySlope[j+1])) {
                    // found the segment
                    int start = j;
                    int end = j+1;
                    for (int k = j+1; k < N; k++) {
                        if (slope == p.slopeTo(pointsBySlope[k])) {
                            end = k;
                        } else {
                            break;
                        }
                    }

                    //StdOut.println(start + " " + end);
                    // check if the segment is long enough
                    // check if it is not a subsegment
                    if ((end - start >= 2)
                        && (p.compareTo(pointsBySlope[start]) == -1)) {
                        //StdOut.println("segment!");
                        StdOut.print(p);
                        for (int l = start; l <= end; l++) {
                            StdOut.print(" -> " + pointsBySlope[l]);
                        }
                        StdOut.println();
                        p.drawTo(pointsBySlope[end]);
                    }

                    break;
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);
    }
}
