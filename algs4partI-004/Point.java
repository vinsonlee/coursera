/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null) {
            throw new NullPointerException();
        }

        // StdOut.println(that.y + "-" + this.y + "/" + that.x + "-" + this.x);
        double n = that.y - this.y;
        double d = that.x - this.x;

        if (n == 0 && d == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (d == 0) {
            return Double.POSITIVE_INFINITY;
        } else if (n == 0) {
            // avoid negative zero
            return 0;
        } else {
            return n / d;
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null) {
            throw new NullPointerException();
        }

        if ((this.x == that.x) && (this.y == that.y)) {
            return 0;
        } else if ((this.y < that.y)
                   || ((this.y == that.y) && (this.x < that.x))) {
            return -1;
        } else {
            return 1;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point q, Point r) {
            if (q == null || r == null) {
                throw new NullPointerException();
            }

            if (slopeTo(q) < slopeTo(r)) {
                return -1;
            } else if (slopeTo(q) == slopeTo(r)) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p;
        Point q;
        Point r;
        double slope;

        p = new Point(102, 113);
        q = new Point(387, 383);
        assert Math.abs(p.slopeTo(q) - 0.9473684210526315) <= 0.000001;

        // positive infinite slope, where p and q have coordinates in [0, 500)
        p = new Point(24, 311);
        q = new Point(24, 52);
        assert p.slopeTo(q) == Double.POSITIVE_INFINITY;

        // negative infinite slope, where p and q have coordinates in [0, 500)
        p = new Point(213, 410);
        q = new Point(213, 410);
        assert p.slopeTo(q) == Double.NEGATIVE_INFINITY;

        // positive zero slope, where p and q have coordinates in [0, 500)
        p = new Point(463, 456);
        q = new Point(212, 456);
        assert p.slopeTo(q) == Double.doubleToRawLongBits(0.0);

        // sign of compareTo(), where p and q have coordinates in [0, 500)
        p = new Point(171, 307);
        q = new Point(171, 195);
        assert p.compareTo(q) == 1;

        // sign of compare(), where p, q, and r have coordinates in [0, 500)
        p = new Point(363, 106);
        q = new Point(241, 129);
        r = new Point(256, 245);
        assert p.SLOPE_ORDER.compare(q, r) == 1;
    }
}
