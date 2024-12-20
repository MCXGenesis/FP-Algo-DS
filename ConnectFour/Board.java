package ConnectFour;

import java.awt.Graphics;
import java.awt.Color;

class Board {
    public static final int ROWS = 6;
    public static final int COLS = 7;
    private final Seed[][] grid;

    public Board() {
        grid = new Seed[ROWS][COLS];
        reset();
    }

    public void reset() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col] = Seed.EMPTY;
            }
        }
    }

    public boolean canDrop(int col) {
        return col >= 0 && col < COLS && grid[0][col] == Seed.EMPTY;
    }

    public int dropDisk(Seed seed, int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (grid[row][col] == Seed.EMPTY) {
                grid[row][col] = seed;
                return row; // Mengembalikan nilai tunggal tipe int
            }
        }
        return -1;
    }

    public void undoMove(int row, int col) {
        grid[row][col] = Seed.EMPTY;
    }

    public State checkGameState(Seed seed, int row, int col) {
        if (isWinningMove(seed, row, col)) {
            return (seed == Seed.RED) ? State.RED_WON : State.YELLOW_WON;
        } else if (isFull()) {
            return State.DRAW;
        }
        return State.PLAYING;
    }

    private boolean isWinningMove(Seed seed, int row, int col) {
        return checkDirection(seed, row, col, 1, 0) || // Horizontal
               checkDirection(seed, row, col, 0, 1) || // Vertical
               checkDirection(seed, row, col, 1, 1) || // Diagonal (
               checkDirection(seed, row, col, 1, -1);  // Diagonal (/)
    }

    private boolean checkDirection(Seed seed, int row, int col, int dRow, int dCol) {
        int count = 1;
        for (int i = 1; i < 4; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLS && grid[r][c] == seed) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i < 4; i++) {
            int r = row - i * dRow;
            int c = col - i * dCol;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLS && grid[r][c] == seed) {
                count++;
            } else {
                break;
            }
        }
        return count >= 4;
    }

    public boolean isFull() {
        for (int col = 0; col < COLS; col++) {
            if (grid[0][col] == Seed.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public void paint(Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = col * Cell.SIZE;
                int y = row * Cell.SIZE;
                g.setColor(Color.BLUE);
                g.fillRect(x, y, Cell.SIZE, Cell.SIZE);
                g.setColor(Color.WHITE);
                g.fillOval(x + 5, y + 5, Cell.SIZE - 10, Cell.SIZE - 10);

                if (grid[row][col] == Seed.RED) {
                    g.setColor(Color.RED);
                    g.fillOval(x + 5, y + 5, Cell.SIZE - 10, Cell.SIZE - 10);
                } else if (grid[row][col] == Seed.YELLOW) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(x + 5, y + 5, Cell.SIZE - 10, Cell.SIZE - 10);
                }
            }
        }
    }

    public int evaluate(Seed seed) {
        int score = 0;
        score += countAligned(seed, 2) * 10;
        score += countAligned(seed, 3) * 100;
        score += countAligned(seed, 4) * 1000;
        return score;
    }

    private int countAligned(Seed seed, int count) {
        int total = 0;
        // Implementasi logika untuk menghitung disk berturut-turut
        return total;
    }
}

class Cell {
    public static final int SIZE = 50; // Ukuran sel
}
