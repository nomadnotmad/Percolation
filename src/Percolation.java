import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] unblocked;
    private final int n;
    private int countOpen = 0;
    private final WeightedQuickUnionUF weightedQuickUnionUFS;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int size) {
        if (size <= 0) throw new IllegalArgumentException();
        n = size;
        int sizeArray = n * n;
        weightedQuickUnionUFS = new WeightedQuickUnionUF(sizeArray + 4);
        unblocked = new boolean[sizeArray];
        for (int i = 0; i < sizeArray; i++) {
            unblocked[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!check(row, col)) throw new IllegalArgumentException();
        else if (!isOpen(row, col)) {
            countOpen++;
            int i = rcToIndex(row, col);
            unblocked[i] = true;
            if (row == 1) weightedQuickUnionUFS.union(i, n * n + 2);
            if (row == n) weightedQuickUnionUFS.union(i, n * n + 3);
            if ((col + 1 > 0 && col + 1 <= n) && (unblocked[i + 1])) weightedQuickUnionUFS.union(i, i + 1);
            if ((col - 1 > 0 && col - 1 <= n) && (unblocked[i - 1])) weightedQuickUnionUFS.union(i, i - 1);
            if ((row + 1 > 0 && row + 1 <= n) && (unblocked[i + n])) weightedQuickUnionUFS.union(i, i + n);
            if ((row - 1 > 0 && row - 1 <= n) && (unblocked[i - n])) weightedQuickUnionUFS.union(i, i - n);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!check(row, col)) throw new IllegalArgumentException();
        return unblocked[rcToIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!check(row, col)) throw new IllegalArgumentException();
        return weightedQuickUnionUFS.find(rcToIndex(row, col)) == weightedQuickUnionUFS.find(n * n + 2);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUFS.find(n * n + 2) == weightedQuickUnionUFS.find(n * n + 3);
    }

    private int rcToIndex(int row, int col) {
        return  (row - 1) * n + (col - 1);
    }

    private boolean check(int row, int col) {
        return (row > 0 && row <= n) && (col > 0 && col <= n);
    }
    // test client (optional)


}
