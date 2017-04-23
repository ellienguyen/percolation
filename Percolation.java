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
    private int VIRTUAL_TOP;
    private int VIRTUAL_BOTTOM;
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
        VIRTUAL_TOP = n * n;
        VIRTUAL_BOTTOM = n * n + 1;
        grid = new WeightedQuickUnionUF(n * n + 2);
        auxGrid = new WeightedQuickUnionUF(n * n + 2);
    }

    private int getIndex(int row, int col) {
        if (row < 1 || col < 1 || row > this.width || col > this.width) {
            return -1;
        }
        return (row - 1) * this.width + col - 1;
    }

    public void open(int row, int col) {
        if (getIndex(row, col) == -1) {
            throw new java.lang.IndexOutOfBoundsException("Invalid input");
        }
        this.unionAround(row, col);
        this.data[getIndex(row, col)] = true;
        if (row == 1) {
            this.grid.union(getIndex(row, col), VIRTUAL_TOP);
            this.auxGrid.union(getIndex(row, col), VIRTUAL_TOP);
        }
        if (row == width) {
            this.auxGrid.union(getIndex(row, col), VIRTUAL_BOTTOM);
        }
    }

    public boolean isOpen(int row, int col) {
        if (getIndex(row, col) == -1) {
            throw new java.lang.IndexOutOfBoundsException("Invalid input");
        }
        return this.data[getIndex(row, col)];
    }
    public boolean isFull(int row, int col) {
        if (getIndex(row, col) == -1) {
            throw new java.lang.IndexOutOfBoundsException("Invalid input");
        }
        return this.grid.connected(VIRTUAL_TOP, getIndex(row, col));
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
        if (getIndex(row - 1,  col) != -1 && this.isOpen(row - 1, col)) {
            this.grid.union(getIndex(row, col), getIndex(row - 1, col));
            this.auxGrid.union(getIndex(row, col), getIndex(row - 1, col));
        }
    }

    private void unionSouth(int row, int col) {
        if (getIndex(row + 1,  col) != -1 && this.isOpen(row + 1, col)) {
            this.grid.union(getIndex(row, col), getIndex(row + 1, col));
            this.auxGrid.union(getIndex(row, col), getIndex(row + 1, col));
        }
    }

    private void unionEast(int row, int col) {
        if (getIndex(row,  col - 1) != -1 && this.isOpen(row, col - 1)) {
            this.grid.union(getIndex(row, col), getIndex(row, col - 1));
            this.auxGrid.union(getIndex(row, col), getIndex(row, col - 1));
        }
    }
    private void unionWest(int row, int col) {
        if (getIndex(row,  col + 1) != -1 && this.isOpen(row, col + 1)) {
            this.grid.union(getIndex(row, col), getIndex(row, col + 1));
            this.auxGrid.union(getIndex(row, col), getIndex(row, col + 1));
        }
    }

    public boolean percolates()   {
        return this.auxGrid.connected(VIRTUAL_TOP, VIRTUAL_BOTTOM);
    }           // does the system percolate?

    public static void main(String[] args) {

    }  // test client (optional)
}
