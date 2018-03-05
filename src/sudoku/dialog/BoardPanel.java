package sudoku.dialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.text.AttributedCharacterIterator;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;

import sudoku.model.Board;

import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;

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
    private static Color boardColor = new Color(70, 70, 70);
    private static Color invalidNum = new Color(228, 0, 124);

    /**
     * Board to be displayed.
     */
    private Board board;

    /**
     * Width and height of a square in pixels.
     */
    private int squareSize;
    public int sx;
    public int sy;
    public boolean highlightSqr;
    public boolean sounds;

    /**
     * Create a new board panel to display the given board.
     */
    BoardPanel(Board board, ClickListener listener) {
//        System.out.println("BoardPanel");
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
//        System.out.println("setBoard");
        this.board = board;
    }

    /**
     * Given a screen coordinate, return the indexes of the corresponding square
     * or -1 if there is no square.
     * The indexes are encoded and returned as x*100 + y,
     * where x and y are 0-based column/row indexes.
     */
    private int locateSquare(int x, int y) {
//        System.out.println("locateSquare");
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
//        System.out.println("paint");
        super.paint(g);

        // determine the square size
        Dimension dim = getSize();
        squareSize = Math.min(dim.width, dim.height) / board.size();

        // draw background
        g.setColor(boardColor);
        g.fillRect(0, 0, squareSize * board.size(), squareSize * board.size());

        // WRITE YOUR CODE HERE ...
        highlightInvalid(g);
        drawNumbers(g);
        highlightSelected(g);
        insideLines(g);
        outsideBox(g);
        solved();
    }

    private void drawNumbers(Graphics g) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (board.getElement(i, j) != 0) {
                    if (board.isValid(i, j)) {
                        g.setColor(Color.WHITE);
                        g.drawString(String.valueOf(board.getElement(i, j)), (j * squareSize) + (squareSize / 2 - 3), (i * squareSize) + (squareSize / 2 + 4));
                        sounds = false;
                    }
                    else if (!board.isValid(i, j)) {
                        g.setColor(Color.BLACK);
                        g.drawString(String.valueOf(board.getElement(i,j)), (j*squareSize)+(squareSize/2-3), (i*squareSize)+(squareSize/2+4));
                        sounds = true;
                    }
                }
            }
        }
    }

    private void highlightInvalid(Graphics g) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (!board.isValid(i,j) && board.getElement(i, j) != 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(j*squareSize, i*squareSize, squareSize, squareSize);
                }
            }
        }
    }

    private void solved() {
        if (board.isSolved()) {
            Object[] options = {"New Game", "Exit"};
            int solved = JOptionPane.showOptionDialog(null,"You Won!",
                    "Congratulations", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[1]);
            if (solved == JOptionPane.YES_OPTION) {
                board.reset();
            }
            else {
                System.exit(0);
            }
        }
    }

    /**
     * This method draw the outside lines to define the sub-grid of the board
     * @param g method receives the Graphics class in order to draw the lines
     * */
    private void outsideBox(Graphics g) {
//        System.out.println("outsideBox");
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
    /**
     * This method draw the inside lines to define the total rows and columns of the board
     * @param g method receives the Graphics class in order to draw the lines
     * */
    private void insideLines(Graphics g) {
//        System.out.println("insideLines");
        g.setColor(Color.gray);
        for (int i = 0; i < 276; i = i + squareSize) {
            g.drawLine(i, 0, i, squareSize * board.size());
            g.drawLine(0, i, squareSize * board.size(), i); //bottom line

        }
    }
    /**
     * This method draw the numbers stored in the matrix and highlights the invalid entries.
     * @param g method receives the Graphics class in order to draw the numbers
     * */
    private void playSound(Graphics g) {
        try {
            Clip clip;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/sound/button-3.wav").getAbsoluteFile());
            if(sounds){
                DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioInputStream);
                clip.start();
                sounds=false;
            }
        }
        catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    /**
    * This method paint the pixels of the square selected in the board
    * and in case of invalid or repeated numbers in the same sub-grid, column, or row then
    * would display red square in all the positions
    * @param g method receives the Graphics class in order to draw the actions
    * */
    private void highlightSelected(Graphics g) {
//        System.out.println("actions");
        if (highlightSqr) {
            g.setColor(Color.cyan);
            g.fillRect(sx*squareSize, sy*squareSize, squareSize, squareSize);
            highlightSqr = false;
            sounds = false;

        }
    }
}
