import edu.princeton.cs.algs4.StdOut ;
import edu.princeton.cs.algs4.StdRandom ;
import edu.princeton.cs.algs4.StdStats ;


public class PercolationStats {
	private final double[] itr ;
	private final int T ;
	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if(n < 1 || trials < 1) {
			throw new IllegalArgumentException();
		}
		Percolation p ;
		itr = new double[trials] ;
		T = trials ;
		int row , col  ;
		for (int j  = 0 ; j < trials  ;j++) {
			p = new Percolation(n) ;
			while (!p.percolates()) {
				row = StdRandom.uniform(1 , n + 1) ;
				col = StdRandom.uniform(1 , n + 1) ;
				if (!p.isOpen(row, col)) {
					p.open(row , col) ;
				}
			}

			itr[j] = ((double)p.numberOfOpenSites() / (double)(n * n)) ; 
			
		}


	}

	// sample mean of percolation threshold
	public double mean() {

		return StdStats.mean(itr) ;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(itr) ;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - ((1.97 * Math.sqrt(StdStats.stddev(itr))) / Math.sqrt(T)) ;
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + ((1.97 * Math.sqrt(StdStats.stddev(itr))) / Math.sqrt(T)) ;
	}

	// test client (see below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]) ;
		int T = Integer.parseInt(args[1]) ;
		PercolationStats ps= new  PercolationStats(n , T) ;
		StdOut.print("mean = " + ps.mean() + "\n") ;
		StdOut.print("stddev = " + ps.stddev() + "\n") ;
		StdOut.print("95% confidence interval = " + ps.confidenceLo() + " , " + ps.confidenceHi()) ;
	}

}