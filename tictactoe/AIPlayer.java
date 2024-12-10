package tictactoe;

public class AIPlayer {
    private Board board; // Referensi ke papan permainan
    private Seed aiSeed; // Simbol AI (X)
    private Seed opponentSeed; // Simbol lawan (O)

    // Konstruktor
    public AIPlayer(Board board, Seed aiSeed) {
        this.board = board;
        this.aiSeed = aiSeed;
        this.opponentSeed = (aiSeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    // Metode untuk mendapatkan langkah terbaik AI
    public int[] getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        // Iterasi seluruh sel untuk mencari langkah terbaik
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                if (board.cells[row][col].content == Seed.NO_SEED) {
                    // Simulasi langkah
                    board.cells[row][col].content = aiSeed;
                    int score = minimax(board, false);
                    // Batalkan langkah
                    board.cells[row][col].content = Seed.NO_SEED;

                    // Perbarui langkah terbaik
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[] { row, col };
                    }
                }
            }
        }
        return bestMove;
    }

    // Algoritma Minimax
    private int minimax(Board board, boolean isMaximizing) {
        // Evaluasi kondisi menang/kalah/seri
        if (hasWon(aiSeed)) return 10; // AI menang
        if (hasWon(opponentSeed)) return -10; // Lawan menang
        if (isDraw()) return 0; // Seri

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Iterasi seluruh sel
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                if (board.cells[row][col].content == Seed.NO_SEED) {
                    // Simulasi langkah
                    board.cells[row][col].content = isMaximizing ? aiSeed : opponentSeed;
                    int score = minimax(board, !isMaximizing);
                    // Batalkan langkah
                    board.cells[row][col].content = Seed.NO_SEED;

                    if (isMaximizing) {
                        bestScore = Math.max(score, bestScore);
                    } else {
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }

    // Metode bantu
    private boolean hasWon(Seed seed) {
        return board.checkWin(seed);
    }

    private boolean isDraw() {
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                if (board.cells[row][col].content == Seed.NO_SEED) {
                    return false;
                }
            }
        }
        return true;
    }
}
