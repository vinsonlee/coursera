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

    private double xGradientSquared(int x, int y) {
        double result = 0;
        // result +=
        return 0;
    }

    private double yGradientSquared(int x, int y) {
        return 0;
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
        StdOut.println(seamCarver.energy(0, 0));  // should be 195075.0
        StdOut.println(seamCarver.energy(1, 1));  // should be 56334.0

        // should be 6
        StdOut.println(seamCarver.width());
        // should be 5
        StdOut.println(seamCarver.height());
        // should be 4
        // StdOut.println(seamCarver.findVerticalSeam().length);
    }
}
