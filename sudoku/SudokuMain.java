package sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L; // to prevent serial warning

    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");

    // Constructor
    public SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);

        // Add a button to the south to re-start the game via board.newGame()
        // ......
        cp.add(btnNewGame, BorderLayout.SOUTH);

        // Event handler untuk tombol New Game
        btnNewGame.addActionListener(e -> showDifficultyDialog());

        // Tampilkan dialog pemilihan tingkat kesulitan saat aplikasi pertama kali dijalankan
        showDifficultyDialog();

        pack(); // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
    }

    /**
     * Menampilkan dialog pemilihan tingkat kesulitan
     */
    private void showDifficultyDialog() {
        String[] options = {"1", "2", "3"}; // 1: Easy, 2: Medium, 3: Hard
        String input = (String) JOptionPane.showInputDialog(
            null,
            "Enter difficulty level:\n1 - Easy\n2 - Medium\n3 - Hard", // Pesan dialog
            "Select Difficulty", // Judul dialog
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1] // Default pilihan: Medium
        );

        System.out.println("Input received: " + input); // Debugging untuk memeriksa input yang diberikan

        // Validasi input
        if (input != null) { // Pastikan input tidak null (tidak dibatalkan oleh pengguna)
            try {
                int difficulty = Integer.parseInt(input); // Parsing string input ke integer
                if (difficulty >= 1 && difficulty <= 3) {
                    board.newGame(difficulty); // Memulai permainan baru berdasarkan input pengguna
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid difficulty (1, 2, or 3).");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number (1, 2, or 3).");
            }
        }
    }

    /** The entry main() entry method */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuMain();
            }
        });
    }
}
