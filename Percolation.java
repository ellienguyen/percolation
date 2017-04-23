/******************************************************************************
 *  Compilation:  javac-algs4 TestAlgs4.java
 *  Execution:    java-algs4 TestAlgs4
 *  Dependencies: StdDraw.java
 *                http://introcs.cs.princeton.edu/mac/cover.jpg
 *
 *  Draws a blue bullseye and textbook graphic.
 *
 ******************************************************************************/


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] data;
    private int width;
    private WeightedQuickUnionUF quickUnion;
    public Percolation(int n) {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException("Invalid input");
        }
        this.width = n;
        this.data = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.data[i][j] = 0;
            }
        }
        this.quickUnion = new WeightedQuickUnionUF(n * n + 2);
    }           // create n-by-n grid, with all sites blocked

    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > this.width || col > this.width) {
            throw new java.lang.IndexOutOfBoundsException("Invalid input");
        }
        this.unionAround(row, col);

        if (row == 1) {
            this.data[row - 1][col - 1] = 2;
        }
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.data[i][j] == 1) {
                    int origin ;
                    int des = i * this.width + j;
                    if (this.quickUnion.connected(origin, des)) {
                        this.data[i][j] = 2;
                    }
                }
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
        return this.data[row - 1][col - 1] == 2;
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
