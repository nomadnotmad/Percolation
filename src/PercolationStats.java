import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] opened;
    private final int trialsT;
    private final double mean, stddev;
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
                percolation.open(row, col);
            }
            opened[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(opened);
        stddev = StdStats.stddev(opened);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
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
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + Math.sqrt(percolationStats.stddev()));
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
