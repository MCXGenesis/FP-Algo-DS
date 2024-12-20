package ConnectFour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConnectFour extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Board board;
    private State currentState;
    private Seed currentPlayer;
    private final JLabel statusBar;
    private int gameMode; // 1: Player vs AI, 2: Player vs Player
    private AIPlayer aiPlayer;
    private final Seed aiPlayerSeed = Seed.YELLOW; // Atur AI sebagai warna kuning

    public ConnectFour() {
        board = new Board();

        // Pilihan mode permainan
        String[] options = {"1 Player", "2 Players"};
        int mode = JOptionPane.showOptionDialog(null,
                "Select game mode", "Game Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        gameMode = (mode == 0) ? 1 : 2;
        if (gameMode == 1) {
            aiPlayer = new AIPlayer(board, aiPlayerSeed);
        }

        currentState = State.PLAYING;
        currentPlayer = Seed.RED;

        statusBar = new JLabel("Red's Turn");
        statusBar.setFont(new Font("Arial", Font.BOLD, 16));
        statusBar.setHorizontalAlignment(SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.PAGE_END);
        setPreferredSize(new Dimension(Board.COLS * Cell.SIZE, (Board.ROWS * Cell.SIZE) + 30));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (board.canDrop(col)) {
                        int row = board.dropDisk(currentPlayer, col);
                        currentState = board.checkGameState(currentPlayer, row, col);

                        if (currentState == State.PLAYING) {
                            currentPlayer = (currentPlayer == Seed.RED) ? Seed.YELLOW : Seed.RED;
                            statusBar.setText((currentPlayer == Seed.RED) ? "Red's Turn" : "Yellow's Turn");

                            if (gameMode == 1 && currentPlayer == aiPlayerSeed) {
                                performAIMove();
                            }
                        } else {
                            updateStatusBar();
                        }
                    }
                } else {
                    resetGame();
                }

                repaint();
            }
        });

        resetGame();
    }

    public ConnectFour(int mode) {
        //TODO Auto-generated constructor stub
    }

    private void performAIMove() {
        if (aiPlayer != null) {
            int col = aiPlayer.getBestMove();
            if (board.canDrop(col)) {
                int row = board.dropDisk(aiPlayerSeed, col);
                currentState = board.checkGameState(aiPlayerSeed, row, col);

                if (currentState == State.PLAYING) {
                    currentPlayer = Seed.RED;
                    statusBar.setText("Red's Turn");
                } else {
                    updateStatusBar();
                }
            }
        }
    }

    private void updateStatusBar() {
        switch (currentState) {
            case RED_WON -> statusBar.setText("Red Wins! Click to restart.");
            case YELLOW_WON -> statusBar.setText("Yellow Wins! Click to restart.");
            case DRAW -> statusBar.setText("It's a Draw! Click to restart.");
            case PLAYING -> statusBar.setText((currentPlayer == Seed.RED) ? "Red's Turn" : "Yellow's Turn");
        }
    }

    private void resetGame() {
        board.reset();
        currentState = State.PLAYING;
        currentPlayer = Seed.RED;
        statusBar.setText("Red's Turn");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.paint(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Connect Four");
            ConnectFour game = new ConnectFour();
            frame.setContentPane(game);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
