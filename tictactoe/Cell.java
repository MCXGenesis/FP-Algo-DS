package tictactoe;

import java.awt.*;
import java.awt.Toolkit;

/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    // Define named constants for drawing
    public static int SIZE; // cell width/height (square), now dynamic
    public static int PADDING; // padding is based on SIZE
    public static int SEED_SIZE; // size of the seed
    public static final int SEED_STROKE_WIDTH = 8; // pen's stroke width

    // Define properties (package-visible)
    /** Content of this cell (Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT) */
    Seed content;
    /** Row and column of this cell */
    int row, col;

    /** Static block to calculate dynamic SIZE based on screen size */
    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Subtract space for UI elements (e.g., title bar, status bar)
        int uiHeight = 100; // Approximate height for title and status bar
        int availableHeight = screenHeight - uiHeight;

        // Calculate size based on smallest dimension (square grid)
        int cellWidth = screenWidth / TicTacToe.COLS;
        int cellHeight = availableHeight / TicTacToe.ROWS;
        SIZE = Math.min(cellWidth, cellHeight);

        // Update dependent constants
        PADDING = SIZE / 5;
        SEED_SIZE = SIZE - PADDING * 2;
    }


    /** Constructor to initialize this cell with the specified row and col */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    /** Reset this cell's content to EMPTY, ready for new game */
    public void newGame() {
        content = Seed.NO_SEED;
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g) {
        // Use Graphics2D which allows us to set the pen's stroke
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(SEED_STROKE_WIDTH,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // Draw the Seed if it is not empty
        int x1 = col * SIZE + PADDING;
        int y1 = row * SIZE + PADDING;
        if (content == Seed.CROSS) {
            g2d.setColor(TicTacToe.COLOR_CROSS);  // draw a 2-line cross
            int x2 = (col + 1) * SIZE - PADDING;
            int y2 = (row + 1) * SIZE - PADDING;
            g2d.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x2, y1, x1, y2);
        } else if (content == Seed.NOUGHT) {  // draw a circle
            g2d.setColor(TicTacToe.COLOR_NOUGHT);
            g2d.drawOval(x1, y1, SEED_SIZE, SEED_SIZE);
        }
    }
}
