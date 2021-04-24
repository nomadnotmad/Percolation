import java.util.Random;

public class PercolationStats {
    private double[] opened;
    private int T;
    private double mean, stddev;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if ( n <= 0 || trials <= 0 ) throw new IllegalArgumentException();
        int row, col;
        opened = new double[trials];
        T = trials;
        Random random = new Random();
        for (int i = 0; i < trials; ++i) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                row = random.nextInt(n + 1);
                col = random.nextInt(n + 1);;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            opened[i] = (double) percolation.numberOfOpenSites() / (n * n) ;
            //System.out.format("\nIterator: %d OpenSites: %d ", i, percolation.numberOfOpenSites());
        }
        //System.out.println(String.format("\nmean = %,.2f \nstddev = %,.2f \n95% confidence interval = [%,.2f, %,.2f]", mean(), stddev(), confidenceLo(), confidenceHi()));
        System.out.println(mean() + " " + Math.sqrt(stddev()) + " " + confidenceLo() + " " + confidenceHi());
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = 0.0;
        for (int i = 0; i < T; ++i) {
            mean += opened[i];
        }
        mean = mean/(T);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        mean = mean();
        stddev = 0.0;
        for (int i = 0; i < T; ++i) {
            stddev += Math.pow((opened[i] - mean), 2.0);
        }
        stddev = stddev/(T-1);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean - 1.96 * Math.sqrt(stddev) / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean + 1.96 * Math.sqrt(stddev) / Math.sqrt(T));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(200, 10000);
    }
}
