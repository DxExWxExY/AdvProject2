package sudoku.model;

/** An abstraction of Sudoku puzzle. */
public class Board {

    /** Size of this board (number of columns/rows). */
    public final int size;
    public int[][] board;

    /** Create a new board of the given size. */
    public Board(int size) {
        this.size = size;
        this.board = new int[size][size];

        // WRITE YOUR CODE HERE ...
    }

    /** Return the size of this board. */
    public int size() {
    	return size;
    }

    // WRITE YOUR CODE HERE ..
}
