/******************************************************************************
 *  Compilation:  javac-algs4 TestAlgs4.java
 *  Execution:    java-algs4 TestAlgs4
 *  Dependencies: StdDraw.java
 *                http://introcs.cs.princeton.edu/mac/cover.jpg
 *
 *  Draws a blue bullseye and textbook graphic.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int VIRTUAL_TOP;
    private static final int VIRTUAL_BOTTOM;
    private boolean[] data;
    private int width;
    private WeightedQuickUnionUF grid;    // grid data, WeightedQuickUnionUF object
    private WeightedQuickUnionUF auxGrid;
    public Percolation(int n) {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException("Invalid input");
        }
        this.width = n;
        this.data = new boolean[n * n];
        for (int i = 0; i < n * n; i++) {
            this.data[i] = false;
        }
        grid = new WeightedQuickUnionUF(n * n + 2);
        auxGrid = new WeightedQuickUnionUF(n * n + 2);
    }           // create n-by-n grid, with all sites blocked

    private int getIndexArray(int row, int col) {
        return row * this.width + col;
    }

    private int getIndex() {
        return row * this.width + col + 1;
    }
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > this.width || col > this.width) {
            throw new java.lang.IndexOutOfBoundsException("Invalid input");
        }
        this.unionAround(row, col);
        this.data[row - 1][col - 1] = 1;
        this.topVirtual();
    }

    private void topVirtual() {
        for (int i = 1; i < this.width; i++) {
            if (this.isOpen(1, i)) {
                this.quickUnion.union(this.width * this.width + 1, i - 1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > this.width || col > this.width) {
            throw new java.lang.IndexOutOfBoundsException("Invalid input");
        }
        return this.data[row - 1][col - 1] != 0;
    }
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > this.width || col > this.width) {
            throw new java.lang.IndexOutOfBoundsException("Invalid input");
        }
        return this.quickUnion.connected((row - 1) * this.width + col - 1, (this.width * this
                .width ));
    }

    public int numberOfOpenSites()  {
        int result = 0;
        for (int i = 1; i <= this.width; i++) {
            for (int j = 1; j <= this.width; j++) {
                if (this.isOpen(i, j)) {
                    result++;
                }
            }
        }
        return result;
    }

    private void unionAround(int row, int col) {
        this.unionNorth(row, col);
        this.unionEast(row, col);
        this.unionSouth(row, col);
        this.unionWest(row, col);
    }


    private void unionNorth(int row, int col) {
        if (row - 1 >= 1 && this.isOpen(row - 1, col)) {
            this.quickUnion.union((row - 1) * this.width + col - 1, (row - 2) * this.width + col -
                    1);
            if (this.isFull(row - 1, col)) {
                this.data[row - 1][col - 1] = 2;
            }
        }
    }

    private void unionSouth(int row, int col) {
        if (row + 1 <= this.width && this.isOpen(row + 1, col)) {
            this.quickUnion.union((row - 1) * this.width + col - 1, row * this.width + col - 1);
            if (this.isFull(row + 1, col)) {
                this.data[row - 1][col - 1] = 2;
            }
        }
    }

    private void unionEast(int row, int col) {
        if (col + 1 <= this.width && this.isOpen(row, col + 1)) {
            this.quickUnion.union((row - 1) * this.width + col - 1, (row - 1) * this.width + col);
            if (this.isFull(row, col + 1)) {
                this.data[row - 1][col - 1] = 2;
            }
        }
    }
    private void unionWest(int row, int col) {
        if (col - 1 >= 1 && this.isOpen(row, col - 1)) {
            this.quickUnion.union((row - 1) * this.width + col - 2,
                    (row -1) * this.width + col - 1);
            if (this.isFull(row, col - 1)) {
                this.data[row - 1][col - 1] = 2;
            }
        }
    }
    public boolean percolates()   {
        for (int i = 0; i < this.width; i++) {
            if (this.data[this.width - 1][i] == 2) {
                return true;
            }
        }
        return false;
    }           // does the system percolate?

    public static void main(String[] args) {

    }  // test client (optional)
}
