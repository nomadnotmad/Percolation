public class Percolation {

    private int[] id;
    private int[] weight;
    private boolean[] blocked;
    private int N, n, countOpen = 0;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        n = n;
        N = n * n + 2;
        id = new int[N];
        weight = new int[N];
        blocked = new boolean[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            weight[i] = 0;
            blocked[i] = true;
        }
        blocked[0] = false;
        blocked[N - 1] = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            countOpen++;
            blocked[row + (col - 1) * n] = false;
            if (col == 1) union(row, col, 0, 1);
            if (col == n) union(row, col, n + 1, n);
            union(row, col, row, col + 1);
            union(row, col, row, col - 1);
            union(row, col, row + 1, col);
            union(row, col, row - 1, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((col > 0 && col <= n) && (row >= 0 && row <= (n + 1))) {
            int i = row + (col - 1) * n;
            return !blocked[i];
        }
        else return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (root(0, 1) == root (row, col)) return true;
        else return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (root(0, 1) == root (n + 1, n)) return true;
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
        int i = row + (col - 1) * n;
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    private void print() {
        for (int i = 0; i < N; i++) {
            System.out.print("id: " + id[i] + " we: " + weight[i] + " bl: " + blocked[i] + " ");
            //if (i % n == 0) System.out.println();
        }
    }
    // test client (optional)
    public static void main(String[] args) {
        int n = 2;
        Percolation percolation = new Percolation(n);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
        percolation.open(1,1);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
        percolation.open(2,2);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
        percolation.open(1,2);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
        percolation.print();
    }
}
