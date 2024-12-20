package ConnectFour;

public class AIPlayer {
    private final Board board;
    private final Seed aiSeed;
    private final Seed opponentSeed;

    public AIPlayer(Board board, Seed aiSeed) {
        this.board = board;
        this.aiSeed = aiSeed;
        this.opponentSeed = (aiSeed == Seed.RED) ? Seed.YELLOW : Seed.RED;
    }

    /**
     * Menghitung langkah terbaik untuk AI berdasarkan evaluasi.
     * @return Array berisi baris dan kolom langkah terbaik.
     */
    public int[] getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1}; // Inisialisasi langkah default

        for (int col = 0; col < Board.COLS; col++) {
            if (board.canDrop(col)) {
                // Simulasi langkah AI
                int row = board.dropDisk(aiSeed, col);
                int score = evaluateMove();
                board.undoMove(row, col);

                // Perbarui langkah terbaik jika diperlukan
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = new int[]{row, col};
                }
            }
        }

        return bestMove;
    }

    /**
     * Mengevaluasi papan untuk menentukan skor.
     * @return Skor evaluasi berdasarkan keuntungan AI dan lawan.
     */
    private int evaluateMove() {
        return board.evaluate(aiSeed) - board.evaluate(opponentSeed);
    }
}
