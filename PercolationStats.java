/**
 * Created by admin on 4/21/17.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.SymbolDigraph;

public class PercolationStats {
    private double[] result;
    private int trials;
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.result = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);
            while (!test.percolates()) {
                int col = StdRandom.uniform(1, n + 1);
                int row = StdRandom.uniform(1, n + 1);
                test.open(row, col);
            }
            this.result[i] = (double) test.numberOfOpenSites() / (n * n);
        }
    } // perform trials independent experiments on an
        // n-by-n grid
    public double mean()  {
        return StdStats.mean(this.result);
    }                        // sample mean of percolation threshold
    public double stddev()  {
        return StdStats.stddev(this.result);
    }                      // sample standard deviation of percolation
        // threshold
    public double confidenceLo()       {
        return this.mean() - 1.96 * this.stddev()/ Math.sqrt(this.trials);
    }            // low  endpoint of 95% confidence interval
    public double confidenceHi()     {
        return this.mean() + 1.96 * this.stddev()/ Math.sqrt(this.trials);
    }              // high endpoint of 95% confidence interval

    public static void main(String[] args)   {
        PercolationStats test = new PercolationStats(100, 50);
        System.out.println(test.mean());
        System.out.println(test.stddev());
        System.out.println(test.confidenceLo());
        System.out.println(test.confidenceHi());
    }     // test client (described below)
}
