package sudoku;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    // Board width/height in pixels

    // Define properties
    /** The game board composes of 9x9 Cells (customized JTextFields) */
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    /** It also contains a Puzzle with array numbers and isGiven */
    private Puzzle puzzle = new Puzzle();

    /** Constructor */
    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // JPanel
            }

        }

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        //  Cells (JTextFields)
        // .........
        CellInputListener listener = new CellInputListener();

        // [TODO 4] Adds this common listener to all editable cells
        // .........
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /**
     * Generate a new puzzle; and reset the game board of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame(int difficulty) {
        // Generate a new puzzle
        puzzle.newPuzzle(difficulty);// passing difficulty

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    // .........
private class CellInputListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get a reference of the JTextField that triggers this action event
        Cell sourceCell = (Cell)e.getSource();

        // Retrieve the int entered
        int numberIn = Integer.parseInt(sourceCell.getText());
        // For debugging
        System.out.println("You entered " + numberIn);

        // Check for conflicts in the row, column, and sub-grid
        boolean conflictDetected = false;
        //List<Cell> conflictingCells = new ArrayList<>(); // List to hold conflicting cells

        // Check row and column for conflicts
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (i != sourceCell.col && cells[sourceCell.row][i].getText().equals(String.valueOf(numberIn))) {
                conflictDetected = true;
                cells[sourceCell.row][i].status = CellStatus.WRONG_GUESS; // Mark conflicting cell
                ///conflictingCells.add(cells[sourceCell.row][i]); // Add to list
            }
            if (i != sourceCell.row && cells[i][sourceCell.col].getText().equals(String.valueOf(numberIn))) {
                conflictDetected = true;
                cells[i][sourceCell.col].status = CellStatus.WRONG_GUESS; // Mark conflicting cell
                //conflictingCells.add(cells[i][sourceCell.col]); // Add to list
            }
        }

        // Check sub-grid for conflicts
        int startRow = (sourceCell.row / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
        int startCol = (sourceCell.col / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
        for (int r = startRow; r < startRow + SudokuConstants.SUBGRID_SIZE; r++) {
            for (int c = startCol; c < startCol + SudokuConstants.SUBGRID_SIZE; c++) {
                if (r != sourceCell.row && c != sourceCell.col && cells[r][c].getText().equals(String.valueOf(numberIn))) {
                    conflictDetected = true;
                    cells[r][c].status = CellStatus.WRONG_GUESS; // Mark conflicting cell
                    //conflictingCells.add(cells[r][c]); // Add to list
                }
            }
        }

        // Update the status of the source cell
        if (conflictDetected) {
            sourceCell.status = CellStatus.WRONG_GUESS;
        } else if (numberIn == sourceCell.number) {
            sourceCell.status = CellStatus.CORRECT_GUESS;
        } else {
            sourceCell.status = CellStatus.WRONG_GUESS;
        }

        // Repaint all affected cells
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            cells[sourceCell.row][i].paint();
            cells[i][sourceCell.col].paint();
        }
        for (int r = startRow; r < startRow + SudokuConstants.SUBGRID_SIZE; r++) {
            for (int c = startCol; c < startCol + SudokuConstants.SUBGRID_SIZE; c++) {
                cells[r][c].paint();
            }
        }
        sourceCell.paint();   // Repaint this cell based on its status

        // If there are conflicts, set a timer to reset the status after a delay
        if (conflictDetected) {
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    // Reset status of all cells that were marked as wrong guess
                    for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
                        if (cells[sourceCell.row][i].status == CellStatus.WRONG_GUESS) {
                            cells[sourceCell.row][i].status = CellStatus.GIVEN; // Reset status
                            cells[sourceCell.row][i].paint(); // Repaint to reflect the change
                        }
                        if (cells[i][sourceCell.col].status == CellStatus.WRONG_GUESS) {
                            cells[i][sourceCell.col].status = CellStatus.GIVEN; // Reset status
                            cells[i][sourceCell.col].paint(); // Repaint to reflect the change
                        }
                    }
                    for (int r = startRow; r < startRow + SudokuConstants.SUBGRID_SIZE; r++) {
                        for (int c = startCol; c < startCol + SudokuConstants.SUBGRID_SIZE; c++) {
                            if (cells[r][c].status == CellStatus.WRONG_GUESS) {
                                cells[r][c].status = CellStatus.GIVEN; // Reset status
                                cells[r][c].paint(); // Repaint to reflect the change
                            }
                        }
                    }
                    sourceCell.status = CellStatus.TO_GUESS; // Reset source cell status
                    sourceCell.paint(); // Repaint to reflect the change
                }
            });
            timer.setRepeats(false); // Only execute once
            timer.start(); // Start the timer
        }

        // Check if the player has solved the puzzle after this move
        if (isSolved()) {
            JOptionPane.showMessageDialog(null, "Congratulations! You have solved the puzzle!");
        }
    }
}

}
