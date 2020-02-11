import edu.princeton.cs.algs4.StdOut ;
import edu.princeton.cs.algs4.StdRandom ;
import edu.princeton.cs.algs4.StdStats ;


public class PercolationStats {
	private final double[] itr ;
	private final int T ;
	private double mean,stdDev = 0.0;
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
		if(getMean() == 0.0) {
			setMean(StdStats.mean(itr));
		}
		
		return  getMean();
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		if(getStdDev()==0.0) {
			setStdDev(StdStats.stddev(itr));
		}
		
		return getStdDev() ;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - ((1.96 * (stddev())) / Math.sqrt(T)) ;
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + ((1.96 * (stddev())) / Math.sqrt(T)) ;
	}

	private double getMean() {
		return mean;
	}

	private void setMean(double mean) {
		this.mean = mean;
	}

	private double getStdDev() {
		return stdDev;
	}

	private void setStdDev(double stdDev) {
		this.stdDev = stdDev;
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