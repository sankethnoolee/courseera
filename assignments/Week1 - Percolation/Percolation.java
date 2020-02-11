import edu.princeton.cs.algs4.WeightedQuickUnionUF ;

public class Percolation {
	private final int gridSize ;
	private final WeightedQuickUnionUF w ;
	private final WeightedQuickUnionUF bw ;
	private final boolean[][] grid ;
	private final int[][] idMap ;
	private int openSites  ;
	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		openSites = 0 ;
		if (n <= 0) {
			throw new IllegalArgumentException() ;
		}
		gridSize = n ;
		// n+2 for 2 extra for virtual nodes.

		w = new WeightedQuickUnionUF((n * n) + 2) ;
		bw = new WeightedQuickUnionUF((n * n) + 1) ;

		int id = 1 ;
		grid = new boolean[n][n] ;
		idMap = new int[n][n] ;
		for (int i = 0 ;i < n ;i++) {
			for (int j = 0 ;j < n ;j++) {
				grid[i][j] = false ;
				idMap[i][j] = id ;
				id++ ;

			}
		}
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if (!isOpen(row, col)) {
			validateRowCol(row, col) ;
			// connecting virtual top node
			if (row == 1) {
				w.union( 0 , idMap[row-1][col-1]) ;
				bw.union( 0 , idMap[row-1][col-1]) ;
			}

			if (row - 1 != 0) {
				// upper element check
				if(isOpen(row - 1 , col)) {
					w.union(idMap[(row - 1) - 1][(col - 1)],idMap[row - 1][col - 1]) ;
					bw.union(idMap[(row - 1) - 1][(col - 1)],idMap[row - 1][col - 1]) ;
				}
			}

			if (row != gridSize) {
				// bottom element check
				if(isOpen(row + 1, col)) {
					w.union(idMap[row][col - 1],idMap[row - 1][col - 1]) ;
					bw.union(idMap[row][col - 1],idMap[row - 1][col - 1]) ;
				}

			}
			// connecting virtual bottom node
			if (row == gridSize) {
				w.union( gridSize * gridSize + 1,idMap[row - 1][col - 1]) ;
			}

			if (col - 1 != 0) {
				// upper element check
				if(isOpen(row, col - 1)) {
					w.union(idMap[(row - 1)][(col - 1) - 1],idMap[row - 1][col - 1]) ; 
					bw.union(idMap[(row - 1)][(col - 1) - 1],idMap[row - 1][col - 1]) ; 
				}
			}

			if (col != gridSize) {
				// bottom element check
				if(isOpen(row , col + 1)) {
					w.union(idMap[row - 1][col],idMap[row - 1][col - 1]) ;
					bw.union(idMap[row - 1][col],idMap[row - 1][col - 1]) ;
				}
			}

			grid[row - 1][col - 1] = true ;
			openSites++ ;
		}

	}

	// is the site (row, col) open?
	public boolean isOpen(int row , int col) {
		validateRowCol(row , col) ;
		return grid[row - 1][col - 1] ;
	}

	// is the site (row, col) full?
	public boolean isFull(int row , int col) {
		validateRowCol(row , col) ;
		if (bw.connected(0 , idMap[row - 1][col - 1] )) {
			return true ;
		}
		return false ;
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return openSites ;
	}

	// does the system percolate?
	public boolean percolates() {
		return w.connected(0, gridSize * gridSize + 1) ;
	}

	private void validateRowCol(int row , int col) {
		if ((row <= 0 || row > gridSize) || (col <= 0 || col > gridSize)) {
			throw new IllegalArgumentException() ;
		}
	}
	// test client (optional)
	public static void main(String[] args) {
		// UncommentedEmptyMethodBody
	}
}
