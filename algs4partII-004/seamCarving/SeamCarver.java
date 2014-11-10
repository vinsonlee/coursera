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
        // should be 195075.0
        StdOut.println(seamCarver.energy(0, 0));
        // should be 6
        StdOut.println(seamCarver.width());
        // should be 5
        StdOut.println(seamCarver.height());
        // should be 4
        StdOut.println(seamCarver.findVerticalSeam().length);
    }
}
