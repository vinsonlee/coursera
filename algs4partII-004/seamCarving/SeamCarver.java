import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private double[][] energyTo;
    private int[][] xTo;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    /// energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= picture.width()) {
            throw new IndexOutOfBoundsException();
        }

        if (y < 0 || y >= picture.height()) {
            throw new IndexOutOfBoundsException();
        }

        if (x == 0 || x == picture.width() - 1
            || y == 0 || y == picture.height() - 1) {
            return 195075;
        } else {
            return xGradientSquared(x, y) + yGradientSquared(x, y);
        }
    }

    private int xGradientSquared(int x, int y) {
        int redDiff = Math.abs(picture.get(x - 1, y).getRed()
                               - picture.get(x + 1, y).getRed());

        int greenDiff = Math.abs(picture.get(x - 1, y).getGreen()
                               - picture.get(x + 1, y).getGreen());

        int blueDiff = Math.abs(picture.get(x - 1, y).getBlue()
                               - picture.get(x + 1, y).getBlue());

        // StdOut.println(redDiff + " " + greenDiff + " " + blueDiff);
        // StdOut.println(redDiff*redDiff + greenDiff*greenDiff + blueDiff*blueDiff);
        return redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }

    private int yGradientSquared(int x, int y) {
        int redDiff = Math.abs(picture.get(x, y - 1).getRed()
                               - picture.get(x, y + 1).getRed());

        int greenDiff = Math.abs(picture.get(x, y - 1).getGreen()
                               - picture.get(x, y + 1).getGreen());

        int blueDiff = Math.abs(picture.get(x, y - 1).getBlue()
                               - picture.get(x, y + 1).getBlue());

        // StdOut.println(redDiff + " " + greenDiff + " " + blueDiff);
        // StdOut.println(redDiff*redDiff + greenDiff*greenDiff + blueDiff*blueDiff);
        return redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // Transpose picture.
        Picture original = picture;
        Picture transpose = new Picture(original.height(), original.width());

        for (int w = 0; w < transpose.width(); w++) {
            for (int h = 0; h < transpose.height(); h++) {
                transpose.set(w, h, original.get(h, w));
            }
        }

        this.picture = transpose;

        // call findVerticalSeam
        int[] seam = findVerticalSeam();

        // Transpose back.
        this.picture = original;

        return seam;
    }

    // sequence of indices for vertical seam
    /*
     (x, y)
       x---->
      y
      |
      |
      v
      
     (0, 0)  (1, 0)  (2, 0)
     (0, 1)  (1, 1)  (2, 1)
     (0, 2)  (1, 2)
     (0, 3)  (1, 3)
     */
    public int[] findVerticalSeam() {
        energyTo = new double[width()][height()];
        xTo = new int[width()][height()];

        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                energyTo[x][y] = Double.POSITIVE_INFINITY;
            }
        }

        for (int x = 0; x < width(); x++) {
            energyTo[x][0] = 195075;
        }

        /*
        Topological topological = new Topological(G);
        for (int v : topological.order())
            for (DirectedEdge e : G.adj(v))
                relax(e);
        */
        for (int y = 0; y < height() - 1; y++) {
            for (int x = 0; x < width(); x++) {
                // StdOut.println("visiting vertex (" + x + "," + y + ")");
                if (x > 0) {
                    relax(x, y, x - 1, y + 1);
                }

                relax(x, y, x, y + 1);

                if (x < width() - 1) {
                    relax(x, y, x + 1, y + 1);
                }
            }
        }

        // print energyTo
        /*
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                StdOut.println("energyTo(" + x + "," + y + "): " + energyTo[x][y]);
            }
        }
         */

        // find minimum energy path
        double minEnergy = Double.POSITIVE_INFINITY;
        int minEnergyX = -1;
        for (int w = 0; w < width(); w++) {
            if (energyTo[w][height() - 1] < minEnergy) {
                minEnergyX = w;
                minEnergy = energyTo[w][height() - 1];
            }
        }
        assert minEnergyX != -1;

        // StdOut.println(minEnergyX);
        // StdOut.println("Total energy: " + minEnergy);

        int[] seam = new int[height()];
        seam[height() - 1] = minEnergyX;
        int prevX = xTo[minEnergyX][height() - 1];

        for (int h = height() - 2; h >= 0; h--) {
            seam[h] = prevX;
            prevX = xTo[prevX][h];
        }

        // print seam
        /*
        for (int i = 0; i < seam.length; i++) {
            StdOut.print(seam[i] + " ");
        }
        StdOut.println();
         */

        return seam;
    }

    /*
    private void relax(DirectedEdge e)
    {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight())
            {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
    }
    */
    private void relax(int x1, int y1, int x2, int y2) {
        if (energyTo[x2][y2] > energyTo[x1][y1] + energy(x2, y2)) {
            energyTo[x2][y2] = energyTo[x1][y1] + energy(x2, y2);
            xTo[x2][y2] = x1;
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        // Transpose picture.
        Picture original = picture;
        Picture transpose = new Picture(original.height(), original.width());

        for (int w = 0; w < transpose.width(); w++) {
            for (int h = 0; h < transpose.height(); h++) {
                transpose.set(w, h, original.get(h, w));
            }
        }

        this.picture = transpose;
        transpose = null;
        original = null;

        // call removeVerticalSeam
        removeVerticalSeam(seam);

        // Transpose back.
        original = picture;
        transpose = new Picture(original.height(), original.width());

        for (int w = 0; w < transpose.width(); w++) {
            for (int h = 0; h < transpose.height(); h++) {
                transpose.set(w, h, original.get(h, w));
            }
        }

        this.picture = transpose;
        transpose = null;
        original = null;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }

        if (seam.length != height()) {
            throw new IllegalArgumentException();
        }

        Picture original = this.picture;
        Picture carved = new Picture(original.width() - 1, original.height());

        for (int h = 0; h < carved.height(); h++) {
            for (int w = 0; w < seam[h]; w++) {
                carved.set(w, h, original.get(w, h));
            }
            for (int w = seam[h]; w < carved.width(); w++) {
                carved.set(w, h, original.get(w + 1, h));
            }
        }

        this.picture = carved;
    }

    public static void main(String[] args) {
        Picture picture;
        SeamCarver seamCarver;
        int[] seam;

        picture = new Picture("seamCarving/4x6.png");
        // picture = new Picture("seamCarving/6x5.png");

        seamCarver = new SeamCarver(picture);
        // StdOut.println(seamCarver.energy(0, 0));  // should be 195075.0
        // StdOut.println(seamCarver.energy(1, 1));  // should be 56334.0
        for (int i = 0; i < seamCarver.width(); i++) {
            for (int j = 0; j < seamCarver.height(); j++) {
                StdOut.println("energy(" + i + "," + j + "): "
                               + seamCarver.energy(i, j));
            }
        }

        // should be 6
        StdOut.println(seamCarver.width());
        // should be 5
        StdOut.println(seamCarver.height());

        // should be 5
        seam = seamCarver.findVerticalSeam();
        StdOut.println(seam.length);
        StdOut.println(Arrays.toString(seam));
        seamCarver.removeVerticalSeam(seam);

        for (int i = 0; i < seamCarver.width(); i++) {
            for (int j = 0; j < seamCarver.height(); j++) {
                StdOut.println("energy(" + i + "," + j + "): "
                               + seamCarver.energy(i, j));
            }
        }

        seam = seamCarver.findHorizontalSeam();
        StdOut.println(Arrays.toString(seam));
        StdOut.println(seam.length);
        seamCarver.removeHorizontalSeam(seam);
    }
}
