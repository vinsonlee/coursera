public class PercolationStats {
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        this.T = T;
        this.results = new double[T];
        
        for (int t = 0; t < T; t++) {
            this.results[t] = 0;
        }

        for (int t = 0; t < T; t++) {
            StdOut.println("trial: " + t);
            
            Percolation p = new Percolation(N);

            double count = 0;
            while (p.percolates() == false) {
                int i = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);

                //StdOut.println("opening " + i + " " + j);
                if (p.isOpen(i, j) == false) {
                    //StdOut.println("opening " + i + " " + j);
                    p.open(i, j);
                    count++;
                }

                //StdOut.println("count: " + count);
                assert(count < N*N);
            }
         
            StdOut.println("count: " + count + " " + count / (N*N));
            this.results[t] = count / (N*N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }
    
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }
    
    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }

    private double[] results;
    private int T;
    
    // test client, described below
    public static void main(String[] args)   {
        System.out.println("Hello, World");
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        StdOut.println(N);
        StdOut.println(T);
        
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddevn = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}
