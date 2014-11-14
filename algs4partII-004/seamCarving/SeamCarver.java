public class SeamCarver {
    private Picture picture;

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

    private double yGradientSquared(int x, int y) {
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
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
    }

    public static void main(String[] args) {
        Picture picture;
        SeamCarver seamCarver;

        picture = new Picture("seamCarving/6x5.png");
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
        // should be 4
        // StdOut.println(seamCarver.findVerticalSeam().length);
    }
}
