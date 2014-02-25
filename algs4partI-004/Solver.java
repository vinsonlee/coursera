public class Solver {
    private boolean solvable;
    private MinPQ<Node> pq;
    private Queue<Board> solution;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        solvable = false;
        pq = new MinPQ<Node>();
        solution = new Queue<Board>();
        moves = 0;

        Node initialNode = new Node();
        initialNode.board = initial;
        initialNode.moves = 0;
        initialNode.prev = null;

        pq.insert(initialNode);

        int step = 0;

        while (true) {
            /*
            StdOut.println("Step: " + step);
            for (Node n : pq) {
                StdOut.println(n);
            }
             */
            step++;

            Node node = pq.delMin();
            solution.enqueue(node.board);

            // check node isGoal
            if (node.board.isGoal()) {
                solvable = true;
                moves = node.moves;
                break;
            } else {
                for (Board board : node.board.neighbors()) {
                    Node neighborNode = new Node();
                    neighborNode.board = board;
                    neighborNode.moves = node.moves + 1;
                    neighborNode.prev = node;

                    if (node.prev == null
                        || !neighborNode.board.equals(node.prev.board)) {
                        pq.insert(neighborNode);
                    }
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return solution;
    }

    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private Node prev;

        public int compareTo(Node that) {
            return this.priority() - that.priority();

            /*
            if (this.priority() != that.priority()) {
                return this.priority() - that.priority();
            } else {
                return this.moves - that.moves;
            }
             */
        }

        public int priority() {
            return moves + board.manhattan();
        }

        public String toString() {
            String s = "";
            s += "priority  = " + priority() + "\n";
            s += "moves     = " + moves + "\n";
            s += "manhattan = " + board.manhattan() + "\n";
            s += board;

            return s.toString();
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
