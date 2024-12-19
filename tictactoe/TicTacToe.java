package tictactoe;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import javax.swing.*;

public class TicTacToe extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    public JLabel timerLabel; // Timer tidak diinisialisasi untuk gameMode == 1

    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Color COLOR_CROSS = new Color(239, 105, 80);  // Red #EF6950
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue #409AE1
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private final JLabel statusBar;
    private int gameMode;
    private int timeLeft;
    private Timer countdownTimer;
    private Timer aiMoveDelayTimer;
    private final int TURN_TIME_LIMIT = 10;

    public TicTacToe() {
        String[] options = {"1 Player", "2 Players"};
        int mode = JOptionPane.showOptionDialog(null,
                "Pilih mode permainan", "Mode Permainan",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        gameMode = (mode == 0) ? 1 : 2;

        if (gameMode == 2) {
            timerLabel = new JLabel("Time left: " + TURN_TIME_LIMIT + "s");
            timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            timerLabel.setOpaque(true);
            timerLabel.setBackground(new Color(0, 0, 0, 0)); // Transparan awal
            timerLabel.setForeground(Color.BLACK);
        }

        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame(currentPlayer, row, col);
                        playSound(currentPlayer);

                        if (gameMode == 2) {
                            switchPlayer();
                        } else if (gameMode == 1 && currentState == State.PLAYING) {
                            statusBar.setText("Computer is thinking...");
                            initiateAIMove();
                        }
                    }
                } else {
                    newGame();
                }

                repaint();
            }
        });

        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END);
        super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        initGame();
        newGame();
    }

    private void initGame() {
        board = new Board();

        countdownTimer = new Timer(1000, e -> {
            if (currentState == State.PLAYING && gameMode == 2) {
                if (timeLeft > 0) {
                    timeLeft--;
                    if (timerLabel != null) {
                        timerLabel.setText("Time left: " + timeLeft + "s");
                    }
                } else {
                    switchPlayer();
                }
            }
        });

        aiMoveDelayTimer = new Timer(3000, e -> performAIMove());
        aiMoveDelayTimer.setRepeats(false);
    }

    private void startTurnTimer() {
        if (gameMode == 2 && timerLabel != null) {
            timeLeft = TURN_TIME_LIMIT;
            timerLabel.setText("Time left: " + TURN_TIME_LIMIT + "s");
            countdownTimer.start();
        }
    }

    private void switchPlayer() {
        if (currentState == State.PLAYING) {
            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
            updateStatus();
            updateTimerLabelColor(); // Perbarui warna berdasarkan giliran pemain
            startTurnTimer();
        }
    }

    private void updateTimerLabelColor() {
        if (gameMode == 2 && timerLabel != null) {
            if (currentPlayer == Seed.CROSS) {
                timerLabel.setBackground(COLOR_CROSS);
                timerLabel.setForeground(Color.WHITE);
                timerLabel.setOpaque(true);
            } else if (currentPlayer == Seed.NOUGHT) {
                timerLabel.setBackground(COLOR_NOUGHT);
                timerLabel.setForeground(Color.WHITE);
                timerLabel.setOpaque(true);
            }
        }
    }

    private void initiateAIMove() {
        countdownTimer.stop(); // Pastikan timer tidak berjalan untuk AI
        aiMoveDelayTimer.start();
    }

    private void performAIMove() {
        if (currentState == State.PLAYING) {
            AIPlayer aiPlayer = new AIPlayer(board, Seed.CROSS);
            int[] move = aiPlayer.getBestMove();

            if (move != null) {
                currentState = board.stepGame(Seed.CROSS, move[0], move[1]);
                playSound(Seed.CROSS);
            }

            currentPlayer = Seed.NOUGHT;
            updateStatus();
            repaint();
        }
    }

    private void playSound(Seed player) {
        if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON) {
            SoundEffect.WINNER.play();
        } else if (currentState == State.DRAW) {
            SoundEffect.DRAW.play();
        } else {
            if (player == Seed.CROSS) {
                SoundEffect.PLAYER_X.play();
            } else {
                SoundEffect.PLAYER_O.play();
            }
        }
    }

    private void updateStatus() {
        if (currentState == State.PLAYING) {
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    public void newGame() {
        countdownTimer.stop();
        aiMoveDelayTimer.stop();

        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.NOUGHT;
        currentState = State.PLAYING;
        updateStatus();

        if (gameMode == 2) {
            startTurnTimer();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);

        board.paint(g);

        if (currentState == State.PLAYING) {
            if (gameMode == 1 && currentPlayer == Seed.CROSS) {
                statusBar.setText("AI is thinking...");
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            TicTacToe ticTacToe = new TicTacToe();

            JPanel parentPanel = new JPanel();
            parentPanel.setLayout(new BorderLayout());
            if (ticTacToe.timerLabel != null) {
                parentPanel.add(ticTacToe.timerLabel, BorderLayout.NORTH);
            }
            parentPanel.add(ticTacToe, BorderLayout.CENTER);

            frame.setContentPane(parentPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
