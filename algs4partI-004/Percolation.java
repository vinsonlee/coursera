//package Percolation;

public class Percolation {
    // Use two WQUF
    // uf1 - two virtual nodes to check percolation
    // uf2 - only top virtual node to check full without backwash
    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;
    private int N;
    private boolean[] opened;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        this.N = N;

        // 0 is the virtual top site
        // N*N+1 is the vitual bottom site
        uf1 = new WeightedQuickUnionUF(N*N + 2);
        uf2 = new WeightedQuickUnionUF(N*N + 1);

        for (int j = 1; j <= N; j++) {
            // connect top row to virtual top site
            uf1.union(0, pid(1, j));
            uf2.union(0, pid(1, j));

            // connect bottom row to bottom virtual site
            uf1.union(N*N+1, pid(N, j));
        }

        opened = new boolean[N*N + 2];
        opened[0] = true;
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException();
        }

        if (!isOpen(i, j)) {
            if (isSite(i, j-1) && isOpen(i, j-1)) {
                uf1.union(pid(i, j), pid(i, j-1));
                uf2.union(pid(i, j), pid(i, j-1));
            }
            if (isSite(i, j+1) && isOpen(i, j+1)) {
                uf1.union(pid(i, j), pid(i, j+1));
                uf2.union(pid(i, j), pid(i, j+1));
            }
            if (isSite(i-1, j) && isOpen(i-1, j)) {
                uf1.union(pid(i, j), pid(i-1, j));
                uf2.union(pid(i, j), pid(i-1, j));
            }
            if (isSite(i+1, j) && isOpen(i+1, j)) {
                uf1.union(pid(i, j), pid(i+1, j));
                uf2.union(pid(i, j), pid(i+1, j));
            }

            opened[pid(i, j)] = true;

            // open the bottom virtual site if it's a bottom site
            if (i == N) {
                opened[N*N + 1] = true;
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException();
        }
        return opened[pid(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen(i, j) && uf2.connected(0, pid(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return opened[N*N + 1] && uf1.connected(0, N*N+1);
    }

    /*
     Ex: N=5
     (1,1) (1,2) (1,3) (1,4) (1,5)
     (2,1) (1,2) (1,3) (1,4) (1,5)
     (3,1) (1,2) (1,3) (1,4) (1,5)
     (4,1) (1,2) (1,3) (4,4) (1,5)
     (5,1) (5,2) (5,3) (5,4) (5,5)
     =>
      1  2  3  4  5
      6  7  8  9 10
     11 12 13 14 15
     16 17 18 19 20
     21 22 23 24 25

     (i - 1) * N + j
     */
    private int pid(int i, int j) {
        int pid = (i - 1) * N + j;
        //StdOut.println(N + " " + i + " " + j + " " + pid);
        return pid;
    }

    private boolean isSite(int i, int j) {
        if ((i < 1) || (i > N)) {
            return false;
        }

        if ((j < 1) || (j > N)) {
            return false;
        }
        return true;
    }
}
