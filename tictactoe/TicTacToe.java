package tictactoe;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import javax.swing.*;

/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class TicTacToe extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L; // to prevent serializable warning

    // Define named constants for the drawing graphics
    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Color COLOR_CROSS = new Color(239, 105, 80);  // Red #EF6950
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue #409AE1
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    // Define game objects
    private Board board;         // the game board
    private State currentState;  // the current state of the game
    private Seed currentPlayer;  // the current player
    private final JLabel statusBar;    // for displaying status message
    private int gameMode; // 1 untuk mode 1 pemain, 2 untuk mode 2 pemain

    /** Constructor to set up the UI and game components */
    public TicTacToe() {
        String[] options = {"1 Player", "2 Players"};
        int mode = JOptionPane.showOptionDialog(null, 
            "Pilih mode permainan", "Mode Permainan", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
            null, options, options[0]);

        if (mode == 0) {
            // 1 Player (melawan AI)
            gameMode = 1;
        } else {
            // 2 Players
            gameMode = 2;
        }

        // This JPanel fires MouseEvent
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Get the row and column clicked
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        // Update cells[][] and return the new game state after the move
                        currentState = board.stepGame(currentPlayer, row, col);
                        // Switch player
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

                        // Play sound based on the current player
                        if (currentPlayer == Seed.CROSS) {
                            SoundEffect.PLAYER_X.play();  // Play sound when X makes a move
                        } else {
                            SoundEffect.PLAYER_O.play();  // Play sound when O makes a move
                        }

                        // AI move after player move (only in 1 player mode)
                        if (gameMode == 1 && currentState == State.PLAYING) {
                            AIPlayer aiPlayer = new AIPlayer(board, Seed.CROSS); // AI plays as X
                            int[] move = aiPlayer.getBestMove(); // Get best AI move
                            currentState = board.stepGame(Seed.CROSS, move[0], move[1]);
                            currentPlayer = Seed.NOUGHT; // Switch back to player O

                            // Play AI move sound
                            SoundEffect.PLAYER_X.play();
                        }

                        // Play sound when game is over (win or draw)
                        if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON) {
                            SoundEffect.WINNER.play();  // Play winner sound for both players
                        } else if (currentState == State.DRAW) {
                            SoundEffect.DRAW.play();    // Play draw sound
                        }
                    }
                } else {  // Game over
                    newGame();  // Restart the game
                }

                // Refresh the drawing canvas
                repaint();  // Callback paintComponent().
            }
        });

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
        super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
        // account for statusBar in height
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        // Set up Game
        initGame();
        newGame();
    }

    /** Initialize the game (run once) */
    public void initGame() {
        board = new Board();  // allocate the game-board
    }

    /** Reset the game-board contents and the current-state, ready for new game */
    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED; // all cells empty
            }
        }
        currentPlayer = Seed.NOUGHT;    // O plays first
        currentState = State.PLAYING;  // ready to play
    }

    @Override
    public void paintComponent(Graphics g) {  // Callback via repaint()
        super.paintComponent(g);
        setBackground(COLOR_BG); // set its background color
    
        board.paint(g);  // ask the game board to paint itself
    
        // Print status-bar message
        if (currentState == State.PLAYING) {
            if (gameMode == 1 && currentPlayer == Seed.CROSS) {
                statusBar.setForeground(Color.GRAY);
                statusBar.setText("It's the computer's turn...");
            } else {
                statusBar.setForeground(Color.BLACK);
                statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
            }
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }
   
    /** The entry "main" method */
    public static void main(String[] args) {
        // Run GUI construction codes in Event-Dispatching thread for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                // Set the content-pane of the JFrame to an instance of main JPanel
                frame.setContentPane(new TicTacToe());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // center the application window
                frame.setVisible(true);            // show it
            }
        });
    }

    public void show() {
        JFrame frame = new JFrame(TITLE);
        frame.setContentPane(this); // Menambahkan panel TicTacToe sebagai konten
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
