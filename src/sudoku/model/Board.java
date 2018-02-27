package sudoku.model;

/** An abstraction of Sudoku puzzle. */
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

    public boolean insertElement(int row, int col, int num) {
        if (checkHorizontal(row, num) && checkVertical(col, num)
                && checkSubGrid(row, col, num) && checkRange(num)) {
            setElement(row, col, num);
            return true;
        }
        return false;
    }

    /*this sets the element at position i,j back to 0*/
    public void deleteElement(int row, int col) {
        board[row][col] = 0;
    }

    /*this  method retrieves the element at position i,j*/
    public int getElement(int i, int j) {
        return board[i][j];
    }

    /*this stores n into the position i,j*/
    private void setElement(int row, int col, int num) {
        board[row][col] = num;
    }

    /*this performs the horizontal check*/
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

    /*this performs the vertical line check*/
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

    /*this checks the sub grid*/
    private boolean checkSubGrid(int row, int col, int num) {
        if (num == 0) { // if the element to replace is 0
            return true;
        }
        /*the starting position is determined by modding the
         * row/col num by the sqrt of the size*/
        int rowStart = (int) (row - (row % Math.sqrt(size)));
        int colStart = (int) (col - (col % Math.sqrt(size)));
        int rowEnd = (int) (rowStart + (Math.sqrt(size)));
        int colEnd = (int) (colStart + (Math.sqrt(size)));
        for (; rowStart <= rowEnd; rowStart++) {
            for (; colStart < colEnd; colStart++) {
                if (board[rowStart][colStart] == num) {
                    return false; //if a matching number is found
                }
            }
        }
        return true;
    }

    /*this tells if the input number is in range*/
    private boolean checkRange(int num) {
        return num <= size && num > 0;
    }

    /*this checks if there are any 0's left in the matrix*/
    public boolean isSolved() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (board[y][x] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
