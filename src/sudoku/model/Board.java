package sudoku.model;

/** An abstraction of the Sudoku puzzle.
 * */
public class Board {

    /** Size of this board (number of columns/rows). */
    private final int size;
    private int[][] board;
    private boolean[][] valid;

    /** Create a new board of the given size.
     * @param size This will be the size of the board. */
    public Board(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.valid = new boolean[size][size];
    }

    /** Return the size of this board.
     * @return Returns the size of the board*/
    public int size() {
    	return size;
    }

    /**
     * This method receives a coordinate in the matrix and checks if it is allowed.
     * To check if the insertion is allowed, it relies on checkVertical, checkHorizontal,
     * checkRange, and checkSubGrid.
     * @param row This is the row to be checked.
     * @param col This is the column to be checked.
     * @param num This is the number to be compared against the matrix.
     * @return Returns whether the insertion was allowed or not.
     * */
    private boolean ruleChecker(int row, int col, int num) {
        return (checkHorizontal(row, num) && checkVertical(col, num)
                && checkSubGrid(row, col, num) && checkRange(num));
    }

    /**
     * This deletes the element at position row col by setting it back to 0.
     * @param row This is the row at which the number would be inserted deleted.
     * @param col This is the column at which the number would be deleted.
     * */
    public void deleteElement(int row, int col) {
        board[row][col] = 0;
        valid[row][col] = false;
    }

    /**
     * This  method retrieves the element at position row col.
     * @param row This is the row in the matrix.
     * @param col This is the column in the matrix.
     * @return Returns the element at the index.
     * */
    public int getElement(int row, int col) {
        return board[row][col];
    }

    /**
     * This stores num into the position row col and determines if the insertion is valid.
     * @param row This is the row at which the number is inserted.
     * @param col This is the column at which the number is inserted.
     * @param num This is the number inserted into the matrix.
     * */
    public void setElement(int row, int col, int num) {
        valid[row][col] = ruleChecker(row, col, num);
        board[row][col] = num;
    }

    /**
     * This performs the horizontal rule check of sudoku.
     * @param row This is the row which will be checked.
     * @param num This is the number to be compared to the rest of the row.
     * @return Returns if the number follows the rule.
     * */
    private boolean checkHorizontal(int row, int num) {
        for (int i = 0; i < size; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * This performs the vertical rule check of sudoku.
     * @param col This is the column which will be checked.
     * @param num This is the number to be compared to the rest of the column.
     * @return Returns if the number follows the rule.
     * */
    private boolean checkVertical(int col, int num) {
        for (int i = 0; i < size; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * This performs the sub-grid rule check of sudoku.
     * @param row This is the row which will be checked.
     * @param col This is the column which will be checked.
     * @param num This is the number to be compared to the rest of the sub-grid.
     * @return Returns if the number follows the rule.
     * */
    private boolean checkSubGrid(int row, int col, int num) {
        /*the starting position is determined by modding the
         * row/col num by the sqrt of the size*/
        int rowS = (int) Math.sqrt(size) * (int) Math.floor(Math.abs(row/Math.sqrt(size)));
        int colS = (int) Math.sqrt(size) * (int) Math.floor(Math.abs(col/Math.sqrt(size)));
        int rowE = (int) (rowS + (Math.sqrt(size)));
        int colE = (int) (colS + (Math.sqrt(size)));
        for (int i = rowS; i < rowE; i++) {
            for (int j = colS; j < colE; j++) {
                if (board[i][j] == num) {
                    return false; //if a matching number is found
                }
            }
        }
        return true;
    }

    /**
     * This checks if the input number is in range
     * @param num This is the number to be checked.
     * @return Returns if the number follows the rule.
     * */
    private boolean checkRange(int num) {
        return num <= size && num > 0;
    }

    /**
     * This returns whether the value was a valid insertion.
     * @param row This is the row to be checked.
     * @param col This is the col to be checked.
     * @return Returns the value stored at the index.
     * */
    public boolean isValid(int row, int col) {
        return valid[row][col];
    }

    /**
     * This checks if there are any 0's left in the matrix.
     * @return Returns if there are no 0's left in the matrix.
     * */
    public boolean isSolved() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!valid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method rests the values in the matrix back to 0;
     * */
    public void reset() {
        board = new int[size][size];
        valid = new boolean[size][size];
    }
}
