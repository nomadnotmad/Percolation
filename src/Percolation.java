public class Percolation {

    private int[] id;
    private int[] weight;
    private boolean[] unblocked;
    private int N, n, countOpen = 0;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int size) {
        n = size;
        N = n * n + 3;
        id = new int[N];
        weight = new int[N];
        unblocked = new boolean[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            weight[i] = 1;
            unblocked[i] = false;
        }
        unblocked[N - 2] = true;
        unblocked[N - 1] = true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            countOpen++;
            unblocked[(row - 1) + (col - 1) * n] = true;
            if (col == 1) union(row, col, n + 2, n);
            if (col == n) union(row, col, n + 3, n);
            union(row, col, row, col + 1);
            union(row, col, row, col - 1);
            union(row, col, row + 1, col);
            union(row, col, row - 1, col);
        }
        else System.out.format("\n( %d : %d ) is close \n", row, col);
    }

    // is the site (row, col) open?
    //refreshed
    public boolean isOpen(int row, int col) {
        if ((row > 0 && row <= n) && (col > 0 && col <= n)) {
            int i = (row - 1) + (col - 1) * n;
            return unblocked[i];
        }
        else if (( (row == n + 2) || (row == n + 3) ) && (col == n)) return true;
            else {
                return false;
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (root(n + 2, n) == root (row, col)) return true;
        else return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (root(n + 2, n) == root (n + 3, n)) return true;
        else return false;
    }

    private void union(int rowCen, int colCen, int rowOut, int colOut) {
        if (isOpen(rowOut, colOut)) {
            int i = root(rowCen, colCen);
            int j = root(rowOut, colOut);
            if (i == j) return;
            if (weight[i] < weight[j]) { id[i] = j; weight[j] += weight[i]; }
            else { id[j] = i; weight[i] += weight[j]; }

        }
    }

    private int root(int row, int col)
    {
        int i = (row - 1) + (col - 1) * n;
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    private void print() {
        for (int i = 0; i < N - 3; i++) {
            if (i % n == 0) System.out.println();
            System.out.format("%d ", id[i]);

        }
    }
    // test client (optional)
    public static void main(String[] args) {
        int n = 2;
        Percolation percolation = new Percolation(n);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
        //System.out.println(percolation.isFull(1,1));
        percolation.open(1,1);
        //System.out.println(percolation.isFull(1,1));
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
        percolation.open(2,2);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
        percolation.open(1,2);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
        percolation.print();
        System.out.println(percolation.isFull(2,1));
    }
}
