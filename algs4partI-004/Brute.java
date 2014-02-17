public class Brute {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        boolean[] draw = new boolean[N]; // which points to draw
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (j == i) {
                    continue;
                }
                for (int k = 0; k < points.length; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    for (int l = 0; l < points.length; l++) {
                        if (l == i || l == j || l == k) {
                            continue;
                        }
                        // StdOut.println(i + " " + j + " " + k + " " + l);

                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];

                        if (p.slopeTo(q) != q.slopeTo(r)) {
                            continue;
                        }
                        if (q.slopeTo(r) != r.slopeTo(s)) {
                            continue;
                        }

                        if (p.compareTo(q) >= 0) {
                            continue;
                        }

                        if (q.compareTo(r) >= 0) {
                            continue;
                        }

                        if (r.compareTo(s) >= 0) {
                            continue;
                        }

                        StdOut.println(p + " -> " + q + " -> " + r + " -> " + s);
                        draw[i] = true;
                        draw[j] = true;
                        draw[k] = true;
                        draw[l] = true;
                        p.drawTo(s);
                    }
                }
            }
        }

        for (int i = 0; i < draw.length; i++) {
            if (draw[i]) {
                points[i].draw();
            }
            // StdOut.println(points[i]);
        }

        // display to screen all at once
        StdDraw.show(0);
    }
}
