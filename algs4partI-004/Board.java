public class Board {
    private int N;
    private int[][] tiles;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N  = blocks.length;
        tiles = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int distance = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    continue;
                }

                if (tiles[i][j] != i * N + j + 1) {
                    distance++;
                }
            }
        }

        return distance;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distance = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }

                int expectedi = (tiles[i][j] - 1) / N;
                int expectedj = (tiles[i][j] - 1) % N;

                distance += Math.abs(i - expectedi);
                distance += Math.abs(j - expectedj);
            }
        }

        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    if (tiles[i][j] != 0) {
                        return false;
                    } else if (tiles[i][j] != i * N + j + 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }

        if (y == null) {
            return false;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;

        if (this.N != that.N) {
            return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()  {
        int emptyi = -1;
        int emptyj = -1;

        // find empty tile
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    emptyi = i;
                    emptyj = j;
                    break;
                }
            }
        }

        assert emptyi != -1;
        assert emptyj != -1;

        Queue<Board> neighbors = new Queue<Board>();

        // swap with left
        if (emptyi - 1 >= 0) {
            Board neighbor = new Board(tiles);
            neighbor.tiles[emptyi][emptyj] = tiles[emptyi - 1][emptyj];
            neighbor.tiles[emptyi - 1][emptyj] = 0;
            neighbors.enqueue(neighbor);
        }

        // swap with right
        if (emptyi + 1 < N) {
            Board neighbor = new Board(tiles);
            neighbor.tiles[emptyi][emptyj] = tiles[emptyi + 1][emptyj];
            neighbor.tiles[emptyi + 1][emptyj] = 0;
            neighbors.enqueue(neighbor);
        }

        // swap with up
        if (emptyj - 1 >= 0) {
            Board neighbor = new Board(tiles);
            neighbor.tiles[emptyi][emptyj] = tiles[emptyi][emptyj - 1];
            neighbor.tiles[emptyi][emptyj - 1] = 0;
            neighbors.enqueue(neighbor);
        }

        // swap with down
        if (emptyj + 1 < N) {
            Board neighbor = new Board(tiles);
            neighbor.tiles[emptyi][emptyj] = tiles[emptyi][emptyj + 1];
            neighbor.tiles[emptyi][emptyj + 1] = 0;
            neighbors.enqueue(neighbor);
        }

        return neighbors;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // StdOut.println(initial.manhattan());
        // StdOut.println(initial.manhattan());

        StdOut.println(initial);
        StdOut.println("neighbors");
        for (Board board : initial.neighbors()) {
            StdOut.println(board);
        }
    }

}