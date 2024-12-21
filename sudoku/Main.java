/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #3
 * 1 - 5026231107 - Naufal Maula Nabil
 * 2 - 5026231228 - Annisa Nur Fauzi
 * 3 - 5026231229 - Lailatul Fitaliqoh
 */
package sudoku;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuMain sudo = new SudokuMain();
            sudo.showDifficultyDialog();
        });
    }
}
