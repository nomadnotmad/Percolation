public class Percolation {

    private int[] id;
    private int[] weight;
    private boolean[] unblocked;
    private final int n;
    private int countOpen = 0;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int size) {
        if (size <= 0) throw new IllegalArgumentException();
        n = size;
        int sizeArray = n * n + 3;
        id = new int[sizeArray];
        weight = new int[sizeArray];
        unblocked = new boolean[sizeArray];
        for (int i = 0; i < sizeArray; i++) {
            id[i] = i;
            weight[i] = 1;
            unblocked[i] = false;
        }
        unblocked[sizeArray - 2] = true;
        unblocked[sizeArray - 1] = true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (((row > 0 && row <= n) && (col > 0 && col <= n)) && !isOpen(row, col)) {
            countOpen++;
            unblocked[(row - 1)  * n + (col - 1)] = true;
            if (row == 1) union(row, col, n, n+ 2);
            if (row == n) union(row, col, n, n+ 3);
            union(row, col, row, col + 1);
            union(row, col, row, col - 1);
            union(row, col, row + 1, col);
            union(row, col, row - 1, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row > 0 && row <= n) && (col > 0 && col <= n)) {
            int i = (row - 1)  * n + (col - 1);
            return unblocked[i];
        }
        else
            return (((col == n + 2) || (col == n + 3)) && (row == n));
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return root(n, n + 2) == root(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return root(n, n + 2) == root(n, n + 3);
    }

    private void union(int rowCen, int colCen, int rowOut, int colOut) {
        if (isOpen(rowOut, colOut)) {
            int i = root(rowCen, colCen);
            int j = root(rowOut, colOut);
            if (i == j) return;
            if (weight[i] < weight[j]) {
                id[i] = j;
                weight[j] += weight[i];
            }
            else {
                id[j] = i;
                weight[i] += weight[j];
            }

        }
    }

    private int root(int row, int col)
    {
        int i = (row - 1) * n + (col - 1);
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
    // test client (optional)
}
