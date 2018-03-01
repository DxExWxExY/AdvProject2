package sudoku.dialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.JPanel;

import sudoku.model.Board;

/**
 * A special panel class to display a Sudoku board modeled by the
 * {@link Board} class. You need to write code for
 * the paint() method.
 *
 * @see Board
 * @author Yoonsik Cheon
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

    public interface ClickListener {

        /**
         * Callback to notify clicking of a square.
         *
         * @param x 0-based column index of the clicked square
         * @param y 0-based row index of the clicked square
         */
        void clicked(int x, int y);
    }

    /**
     * Background color of the board.
     */
    private static final Color boardColor = new Color(247, 223, 150);

    /**
     * Board to be displayed.
     */
    private Board board;

    /**
     * Width and height of a square in pixels.
     */
    public int squareSize;
    public int sx;
    public int sy;
    public boolean highlightSqr;
    public boolean notAllowed;

    /**
     * Create a new board panel to display the given board.
     */
    public BoardPanel(Board board, ClickListener listener) {
        System.out.println("BoardPanel");
        this.board = board;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int xy = locateSquare(e.getX(), e.getY());
                if (xy >= 0) {
                    listener.clicked(xy / 100, xy % 100);
                }
            }
        });
    }

    /**
     * Set the board to be displayed.
     */
    public void setBoard(Board board) {
        System.out.println("setBOard");
        this.board = board;
    }

    /**
     * Given a screen coordinate, return the indexes of the corresponding square
     * or -1 if there is no square.
     * The indexes are encoded and returned as x*100 + y,
     * where x and y are 0-based column/row indexes.
     */
    private int locateSquare(int x, int y) {
        System.out.println("locateSquare");
        if (x < 0 || x > board.size() * squareSize
                || y < 0 || y > board.size() * squareSize) {
            return -1;
        }
        int xx = x / squareSize;
        int yy = y / squareSize;
        return xx * 100 + yy;
    }

    /**
     * Draw the associated board.
     */
    @Override
    public void paint(Graphics g) {
        System.out.println("paint");
        super.paint(g);

        // determine the square size
        Dimension dim = getSize();
        squareSize = Math.min(dim.width, dim.height) / board.size();

        // draw background
        final Color oldColor = g.getColor();
        g.setColor(boardColor);
        g.fillRect(0, 0, squareSize * board.size(), squareSize * board.size());

        // WRITE YOUR CODE HERE ...
        actions(g);
        drawBoard(g);
        insideLines(g);
        outsideBox(g);
    }

    /*This method is used for drawing the box border and the grid of the board size*/
    private void outsideBox(Graphics g) {
        System.out.println("outsideBox");
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, squareSize * board.size(), 0);             //top line
        g.drawLine(0, 0, 0, squareSize * board.size());             //left line
        g.drawLine(0, squareSize * board.size(), squareSize * board.size(), squareSize * board.size()); //bottom line
        g.drawLine(squareSize * board.size(), 0, squareSize * board.size(), squareSize * board.size()); //right line
        /*this draw the grid in the rectangle*/
        for (int i = 0; i < 276; i++) {
            if ((i % (squareSize * Math.sqrt(board.size())) == 0)) {
                g.drawLine(i, 0, i, squareSize * board.size());
                g.drawLine(0, i, squareSize * board.size(), i); //bottom line
            }
        }
    }

    private void insideLines(Graphics g) {
        System.out.println("insideLines");
        g.setColor(Color.gray);
        for (int i = 0; i < 276; i = i + squareSize) {
            g.drawLine(i, 0, i, squareSize * board.size());
            g.drawLine(0, i, squareSize * board.size(), i); //bottom line

        }
    }

    private void drawBoard(Graphics g) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (board.getElement(i, j) != 0) {
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(board.getElement(i,j)), (i*squareSize)+(squareSize/2-3), (j*squareSize)+(squareSize/2+4));
                }
            }
        }
    }

    private void actions(Graphics g) {
        System.out.println("actions");
        if (highlightSqr) {
            g.setColor(Color.PINK);
            g.fillRect(sx, sy, squareSize, squareSize);
            highlightSqr = false;
        }
        if (notAllowed) {
            g.setColor(Color.RED);
            g.fillRect(sx, sy, squareSize, squareSize);
            notAllowed = false;
        }
    }
}
