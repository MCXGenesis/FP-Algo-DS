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
                showWelcomePage(); // Tampilkan halaman sambutan terlebih dahulu
            }
        });
    }

    /**
     * Menampilkan halaman sambutan untuk memilih permainan
     */
    private static void showWelcomePage() {
        String[] options = {"Sudoku", "Tic Tac Toe", "About", "Exit"};
        String choice = (String) JOptionPane.showInputDialog(
            null,
            "Welcome! Please choose an option:",
            "Welcome Menu",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0] // Default pilihan: Sudoku
        );

        // Pilihan pengguna
        if (choice == null || choice.equals("Exit")) {
            System.exit(0); // Keluar dari program
        } else if (choice.equals("Sudoku")) {
            new SudokuMain(); // Buka permainan Sudoku
        } else if (choice.equals("Tic Tac Toe")) {
            JOptionPane.showMessageDialog(null, "Tic Tac Toe is under construction!", "Tic Tac Toe", JOptionPane.INFORMATION_MESSAGE);
        } else if (choice.equals("About")) {
            JOptionPane.showMessageDialog(
                null,
                "Sudoku & Tic Tac Toe\nCreated by [Your Name]",
                "About",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}