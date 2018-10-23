/**
 * @author Tanvir Hossain
 */

/**
 * Sudoku
 */
public class Sudoku {

	/**
	 * Constructor for Sudoku
	 *
	 * @param g
	 *            The grid that defines the sudoku
	 */
	public Sudoku(int[][] g) {
		theGrid = g;
	}
	
	/**
	 * Secondary constructor for Sudoku
	 *
	 * @param g The grid that defines the sudoku
	 * @param e The value that denotes an empty cell
	 */
	public Sudoku(int[][] g, int e) {
		theGrid = g;
		emptyCell = e;
	}

	/**
	 * The n x m grid that defines the Sudoku
	 */
	private int[][] theGrid;
	
	/**
	 * Value that denotes an empty cell
	 */
	private int emptyCell =-1;
	
	/**
	 * Attempt to compute a solution to the Sudoku
	 *
	 * @return A grid with possibly less empty cells than in theGrid (but not more)
	 *
	 * @note If there is no empty cell in the result, then the Sudoku is solved,
	 *       otherwise it is not
	 */
	public int[][] solve() {
		solver();
		return theGrid;
	}
	
	/**
	 * Main helper method for the solve (). It's a recursive method.
	 * @return boolean status if it is solved or not
	 */
	public boolean solver() {
	    for (int r = 0; r < theGrid.length; r++) {
	        for (int c = 0; c < theGrid[0].length; c++) {
	            if (theGrid[r][c] == 0) {
	                for (int k = 1; k <= theGrid.length; k++) {
	                	theGrid[r][c] = (0 + k);
	                    if (isValid() && solver()) {
	                        return true;
	                    } else {
	                    	theGrid[r][c] = 0;
	                    }
	                 }
	                return false;
	             }
	         }
	     }
	    return true;
	}

	/**
	 * Check validity of a Sudoku grid
	 *
	 * @return true if and only if theGrid is a valid Sudoku
	 */
	public boolean isValid() {
		// default return 
		boolean cond = false;
		/**
		 * Check size of grid - null
		 * @return false if the array is null
		 */
		if (theGrid == null)
			return false; 
		/**
		 * Check size of grid - 0
		 * @return true if the length is 0
		 */
		else if (theGrid.length == 0)
			return true;
		/**
		 * Check size of array of array
		 * @return false if an array got empty arrays (2D)
		 */
		if (theGrid.length == 1 && theGrid[0].length == 0)
			return false;
		/**
		 * Check size of grid - single element
		 * @return true if an array got a only 1 element
		 */
		if (theGrid.length == 1)
			return true;
		/**
		 * Check size of grid - 2x2
		 * @return false if it's a 2x2 Sudoku because it violates the Sudoku rule
		 */
		else if (theGrid.length == 2 && theGrid[0].length == 2)
			return false;
		
		// If grid is not empty
		else if (theGrid.length == theGrid[0].length) {
			for (int i = 0; i < theGrid.length; i++) {
				for (int j = 0; j < theGrid[i].length; j++) {
					if (theGrid[i][j] > theGrid.length || theGrid[i][j] < 1) {
						if (theGrid[i][j] != emptyCell)
							return false;
					}
					cond = !isFound(theGrid[i][j], i, j);
					if (isFound(theGrid[i][j], i, j))
						return false;

				}
			}
			int sq = (int) Math.sqrt(theGrid.length);	//Getting square root of the grid's length
			// Checking if it follows Sudoku's sqaure rule
			for (int block = 0; block < theGrid.length; block++) {
				boolean[] m = new boolean[theGrid.length];
				for (int i = block / sq * sq; i < block / sq * sq + sq; i++) {
					for (int j = block % sq * sq; j < block % sq * sq + sq; j++) {
						if (theGrid[i][j] != 0 && theGrid[i][j] !=emptyCell) {
							if (m[(theGrid[i][j] - 1)]) {
								return false;
							}
							m[(theGrid[i][j] - 1)] = true;
						}
					}
				}
			}

			return true;
		} else
			return cond;
	}
	/**
	   * This method is used to find if the provided value of a cell
	   * is found in it's row and column range.
	   * @param n This paramter is used to provide value of the targeted cell
	   * @param r This paramter is used to provide row of the targeted cell
	   * @param c This paramter is used to provide column of the targeted cell
	   * @return boolean This returns boolean states of n depends on availability
	   * of the value in its ranged row and column.
	   */
	public boolean isFound(int n, int r, int c) {
		for (int i = r; i <= r; i++) {
			for (int j = 0; j < theGrid[i].length; j++) {
				if (c != j) {
					if (theGrid[i][j] == n && theGrid[i][j] != emptyCell)
						return true;
				}

			}
		}

		for (int i = 0; i < theGrid.length; i++) {
			for (int j = c; j <= c; j++) {
				if (r != i)
					if (theGrid[i][j] == n && theGrid[i][j] != emptyCell)
						return true;
			}
		}
		return false;
	}
	
	/* Some algorithm logic for dynamic solver were taken from Jason Winter (Tutorial Horizon Algorithms) */
	/**
	 * Attempt to efficiently compute a solution to the Sudoku
	 *
	 * @return A grid with possibly less empty cells than in theGrid (but not more)
	 *
	 * @note If there is no empty cell in the result, then the Sudoku is solved,
	 *       otherwise it is not
	 */
	public int[][] fastSolve() {
		dynamicSolver();
		return theGrid;
	}
	
	public boolean dynamicSolver() {
		int row;
		int col;
		int[] emptyCellX = emptyCellFinder();
		row = emptyCellX[0];
		col = emptyCellX[1];
		if (row == -1) {
			return true;
		}
		// filling cells
		for (int i = 1; i <= theGrid.length; i++) {
		
			if (isValidV2(row, col, i)) {
				
				theGrid[row][col] = i;
				
				if (dynamicSolver()) {
					return true;
				}
				theGrid[row][col] = 0;
			}
		}
		return false;
	}
	
	/**
	 * Checks row and column contain number n
	 *
	 * @param row
	 *            row location of the cell
	 * @param col
	 *            column location of the cell
	 * @param n
	 *            the number going to be looked for
	 *            
	 * @return true/false state of the grid
	 */

	public boolean isValidV2(int row, int col, int n) {
	
		if (!UsedInRow(row, n) && !UsedInColumn(col, n) && !subProblem(row - row % (int)Math.sqrt(theGrid.length), col - col % (int)Math.sqrt(theGrid.length), n)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if n not in particular row
	 *
	 * @param row
	 *            row location of the cell
	 * @param n
	 *            the number going to be looked for
	 *            
	 * @return true/false state of the cell
	 */
	public boolean UsedInRow(int row, int n) {
		for (int i = 0; i < theGrid.length; i++) {
			if (theGrid[row][i] == n) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if n not in particular column
	 *
	 * @param col
	 *            row location of the cell
	 * @param n
	 *            the number going to be looked for
	 *            
	 * @return true/false state of the cell
	 */
	public boolean UsedInColumn(int col, int n) {
		for (int i = 0; i < theGrid.length; i++) {
			if (theGrid[i][col] == n) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if n not in particular sub problem
	 *
	 * @param rowS
	 *            row starting location of the cell
	 * @param colS
	 *            column starting location of the cell
	 * @param n
	 *            the number going to be looked for
	 *            
	 * @return true/false state of the cell
	 */
	public boolean subProblem(int rowS, int colS, int n) {
		for (int i = 0; i < (int)Math.sqrt(theGrid.length); i++) {
			for (int j = 0; j < (int)Math.sqrt(theGrid.length); j++) {
				if (theGrid[i + rowS][j + colS] == n) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Finds empty cell
	 */
	public int[] emptyCellFinder() {
		int[] cell = new int[2]; 
		for (int i = 0; i < theGrid.length; i++) {
			for (int j = 0; j < theGrid.length; j++) {
				if (theGrid[i][j] == 0) {
					cell[0] = i;
					cell[1] = j;
					return cell;
				}
			}
		}
		cell[0] = -1;
		cell[1] = -1;
		return cell;
	}

}