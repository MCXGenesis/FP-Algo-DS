package connect4;

/**
 * Computer move based on simple table lookup of preferences
 */
public class AILookUp extends AIPlayer {

    // Moves {row, col} in order of preferences. {0, 0} at top-left corner
    private int[][] preferredMoves = {
            {5, 3}, {4, 3}, {3, 3}, {2, 3}, {1, 3}, {0, 3}, // Center column
            {5, 2}, {4, 2}, {3, 2}, {2, 2}, {1, 2}, {0, 2}, // Left of center
            {5, 4}, {4, 4}, {3, 4}, {2, 4}, {1, 4}, {0, 4}, // Right of center
            {5, 1}, {4, 1}, {3, 1}, {2, 1}, {1, 1}, {0, 1}, // Further left
            {5, 5}, {4, 5}, {3, 5}, {2, 5}, {1, 5}, {0, 5}, // Further right
            {5, 0}, {4, 0}, {3, 0}, {2, 0}, {1, 0}, {0, 0}, // Leftmost
            {5, 6}, {4, 6}, {3, 6}, {2, 6}, {1, 6}, {0, 6}  // Rightmost
    };

    /** Constructor */
    public AILookUp(Board board) {
        super(board);
    }

    /** Search for the first empty cell, according to the preferences
     *  Assume that next move is available, i.e., not gameover
     *  @return int[2] of {row, col}
     */
    @Override
    public int[] move(String difficulty) {
        for (int[] move : preferredMoves) {
            if (cells[move[0]][move[1]].content == Seed.NO_SEED) {
                return move;
            }
        }
        throw new IllegalStateException("No empty cell!");
    }
}
