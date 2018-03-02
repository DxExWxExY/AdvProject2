package sudoku.model;

/** An abstraction of the Sudoku puzzle.
 * */
public class Board {

    /** Size of this board (number of columns/rows). */
    private final int size;
    private int[][] board;

    /** Create a new board of the given size. */
    public Board(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    /** Return the size of this board. */
    public int size() {
    	return size;
    }
    /**
     * This method receives a coordinate in the matrix and checks if it is allowed.
     * To check if the insertion is allowed, it relies on checkVertical, checkHorizontal,
     * checkRange, and checkSubGrid.
     * @param row This is the row at which the number would be inserted.
     * @param col This is the column at which the number would be inserted.
     * @param num This is the number to be inserted into the matrix.
     * @return Returns whether the insertion was allowed or not.
     * */
    private boolean ruleChecker(int row, int col, int num) {
        if (checkHorizontal(row, num) && checkVertical(col, num)
                && checkSubGrid(row, col, num) && checkRange(num)) {
            setElement(row, col, num);
            return true;
        }
        return false;
    }

    /**
     * This deletes the element at position row col by setting it back to 0.
     * @param row This is the row at which the number would be inserted deleted.
     * @param col This is the column at which the number would be deleted.
     * */
    public void deleteElement(int row, int col) {
        board[row][col] = 0;
    }

    /**
     * This  method retrieves the element at position row col.
     * @param row This is the row in the matrix.
     * @param col This is the column in the matrix.
     * */
    public int getElement(int row, int col) {
        return board[row][col];
    }

    /**
     * This stores num into the position row col.
     * @param row This is the row at which the number is inserted.
     * @param col This is the column at which the number is inserted.
     * @param num This is the number inserted into the matrix.
     * */
    public void setElement(int row, int col, int num) {
        board[row][col] = num;
    }

    /**
     * This performs the horizontal rule check of sudoku.
     * @param row This is the row which will be checked.
     * @param num This is the number to be compared to the rest of the row.
     * @return Returns if the number follows the rule.
     * */
    private boolean checkHorizontal(int row, int num) {
        if (num == 0) {
            return true;
        }
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
        if (num == 0) {
            return true;
        }
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
        if (num == 0) { // if the element to replace is 0
            return true;
        }
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
     * This checks if there are any 0's left in the matrix.
     * @return Returns if there are no 0's left in the matrix.
     * */
    public boolean isSolved() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (ruleChecker(i,j,board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}
