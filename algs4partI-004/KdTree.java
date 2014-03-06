public class KdTree {
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private Node root;
    private int size;

    private static class Node {
        // the point
        private Point2D p;

        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;

        // the left/bottom subtree
        private Node lb;

        // the right/top subtree
        private Node rt;

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(p.toString());
            s.append(" ");
            s.append(rect.toString());
            if (lb != null) {
                s.append("\nlb: ");
                s.append(lb.toString());
            }
            if (rt != null) {
                s.append("\nrt: ");
                s.append(rt.toString());
            }
            return s.toString();
        }
    }

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insert(root, p, VERTICAL, new RectHV(0, 0, 1, 1));
        size++;
    }

    private Node insert(Node x, Point2D p, boolean orientation, RectHV rect) {
        if (x == null) {
            return new Node(p, rect);
        }

        double cmp;
        if (orientation == VERTICAL) {
            cmp = p.x() - x.p.x();
        } else {
            assert orientation == HORIZONTAL;
            cmp = p.y() - x.p.y();
        }

        if (cmp < 0) {
            RectHV subrect;
            if (orientation == VERTICAL) {
                assert p.x() <= rect.xmax();
                subrect = new RectHV(rect.xmin(), rect.ymin(), x.p.x(), rect.ymax());
            } else {
                subrect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.p.y());
            }
            x.lb = insert(x.lb, p, !orientation, subrect);
        } else if (cmp >= 0) {
            RectHV subrect;
            if (orientation == VERTICAL) {
                assert p.x() >= rect.xmin();
                subrect = new RectHV(x.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            } else {
                subrect = new RectHV(rect.xmin(), x.p.y(), rect.xmax(), rect.ymax());
            }
            x.rt = insert(x.rt, p, !orientation, subrect);
        } else {
            assert false;
            x.p = p;
        }

        return x;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node x, Point2D p, boolean orientation) {
        if (x == null) {
            return false;
        }

        double cmp;
        if (orientation == VERTICAL) {
            cmp = p.x() - x.p.x();
        } else {
            cmp = p.y() - x.p.y();
        }


        if (cmp < 0) {
            return contains(x.lb, p, !orientation);
        } else if (cmp > 0) {
            return contains(x.rt, p, !orientation);
        } else {
            return true;
        }

    }

    // draw all of the points to standard draw
    public void draw() {
        draw(root, VERTICAL);
    }

    private void draw(Node x, boolean orientation) {
        if (orientation == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }

        if (x.lb != null) {
            draw(x.lb, !orientation);
        }

        if (x.rt != null) {
            draw(x.rt, !orientation);
        }

        // draw point last to be on top of line
        StdDraw.setPenColor(StdDraw.BLACK);
        x.p.draw();
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> q) {
        if (x != null) {
            if (rect.contains(x.p)) {
                q.enqueue(x.p);
            }

            // FIXME - pruning
            range(x.lb, rect, q);
            range(x.rt, rect, q);
        }
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        return new Point2D(0, 0);
    }


    /*
    public String toString() {
        return root.toString();
    }
     */

    public static void main(String[] args) {
        StdOut.println("hello world");
        KdTree kdtree = new KdTree();
        assert kdtree.size() == 0;
        kdtree.insert(new Point2D(.7, .2));
        assert kdtree.size() == 1;
        kdtree.insert(new Point2D(.5, .4));
        kdtree.insert(new Point2D(.2, .3));
        kdtree.insert(new Point2D(.4, .7));
        kdtree.insert(new Point2D(.9, .6));
        assert kdtree.size() == 5;
        //StdOut.println(kdtree);

        kdtree = new KdTree();
        kdtree.insert(new Point2D(0.206107, 0.095492));
        kdtree.insert(new Point2D(0.975528, 0.654508));
        kdtree.insert(new Point2D(0.024472, 0.345492));
        kdtree.insert(new Point2D(0.793893, 0.095492));
        kdtree.insert(new Point2D(0.793893, 0.904508));
        kdtree.insert(new Point2D(0.975528, 0.345492));
        assert kdtree.size() == 6;
        kdtree.insert(new Point2D(0.206107, 0.904508));
        StdOut.println(kdtree);
        assert kdtree.size() == 7;


        /*



                             0.500000 0.000000
                             0.024472 0.654508
                             0.500000 1.000000)
         */
        StdOut.println("size: " + kdtree.size());

        /*
        StdDraw.show(0);
        StdDraw.setPenRadius(.02);
        kdtree.draw();
        StdDraw.show(0);
         */


        /*
        String filename;
        In in;
        // Test 1a: Insert N distinct points and check size() after each insertion
        // 100000 random distinct points in 100000-by-100000 grid
        filename = "kdtree/input100K.txt";
        in = new In(filename);
        kdtree = new KdTree();
        for (int i = 0; i < 100000; i++) {
            double x = in.readDouble();
            double y = in.readDouble();

        }
         */

    }
}