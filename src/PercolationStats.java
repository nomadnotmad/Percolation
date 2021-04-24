import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    final private double[] opened;
    final private int trialsT;
    private double mean, stddev;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        int row, col;
        opened = new double[trials];
        trialsT = trials;
        for (int i = 0; i < trials; ++i) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                row = StdRandom.uniform(1, trialsT + 1);
                col = StdRandom.uniform(1, trialsT + 1);
                if (!percolation.isOpen(row, col)) percolation.open(row, col);
            }
            opened[i] = (double) percolation.numberOfOpenSites() / (n * n) ;
        }
        System.out.println("mean = " + mean());
        System.out.println("stddev = " + Math.sqrt(stddev()));
        System.out.println("95% confidence interval = [" + confidenceLo() + ", " + confidenceHi() + "]");
    }

    // sample mean of percolation threshold
    public double mean() {
        /*mean = 0.0;
        for (int i = 0; i < trialsT; ++i) {
            mean += opened[i];
        }
        mean = mean/(trialsT);
        return mean;
        */
        mean = StdStats.mean(opened);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        /*mean = mean();
        stddev = 0.0;
        for (int i = 0; i < trialsT; ++i) {
            stddev += Math.pow((opened[i] - mean), 2.0);
        }
        stddev = stddev/(trialsT-1);
        */
        stddev = StdStats.stddev(opened);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean - CONFIDENCE_95 * Math.sqrt(stddev) / Math.sqrt(trialsT));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean + CONFIDENCE_95 * Math.sqrt(stddev) / Math.sqrt(trialsT));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}
