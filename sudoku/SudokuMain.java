package sudoku;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.Flow;
import javax.swing.*;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L; // to prevent serial warning

    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JComboBox<String> modeSelector; // Dropdown untuk memilih mode tampilan

    // Constructor
    public SudokuMain() {

        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Remove window decorations for fullscreen mode
        setSize(800 ,600);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout()); // Use null layout for manual positioning
    
        // Container
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setSize(600, 600);

        // Background color and panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null); // Use null layout for manual positioning

        // Add the board to the panel
        panel.add(board);
        board.setBounds(0, 0, 600, 600); // Initial bounds

        // Add a component listener to keep the board square
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int size = Math.min(panel.getWidth(), panel.getHeight());
                board.setBounds((panel.getWidth() - size) / 2, (panel.getHeight() - size) / 2, size, size);
            }
        });

        // Add the panel to the content pane
        cp.add(panel, BorderLayout.CENTER);


    

//         // Menambahkan dropdown untuk memilih mode tampilan
//         modeSelector = new JComboBox<>(new String[]{"Light Mode", "Dark Mode"});
//         modeSelector.addActionListener(e -> changeMode(modeSelector.getSelectedItem().toString()));

//         // Panel bawah untuk tombol dan dropdown
//         JPanel bottomPanel = new JPanel(new BorderLayout());
//         bottomPanel.add(modeSelector, BorderLayout.EAST);
//         cp.add(bottomPanel, BorderLayout.SOUTH);


        JMenuBar menuBar = new JMenuBar();

        // Create the "File" menu
        JMenu menuFile = new JMenu("File");

        // Create menu items
        JMenuItem menuNewGame = new JMenuItem("New Game");
        JMenuItem menuExitGame = new JMenuItem("Exit");

        menuNewGame.addActionListener(e -> showDifficultyDialog());
        menuExitGame.addActionListener(e -> System.exit(0));

        // Add menu items to the "File" menu
        menuFile.add(menuNewGame);
        menuFile.addSeparator();
        menuFile.add(menuExitGame);

        // Add "File" menu to the menu bar
        menuBar.add(menuFile);

        // Add the menu bar to the frame
        setJMenuBar(menuBar);



//     /**
//      * Mengubah mode tampilan berdasarkan pilihan pengguna
//      */
//     private void changeMode(String mode) {
//         if (mode.equals("Dark Mode")) {
//             getContentPane().setBackground(Color.DARK_GRAY);
//             modeSelector.setBackground(Color.GRAY);
//             modeSelector.setForeground(Color.WHITE);
//             board.setBackground(Color.BLACK); // Sesuaikan dengan implementasi `GameBoardPanel`
//         } else {
//             getContentPane().setBackground(Color.WHITE);
//             modeSelector.setBackground(Color.WHITE);
//             modeSelector.setForeground(Color.BLACK);
//             board.setBackground(Color.WHITE); // Sesuaikan dengan implementasi `GameBoardPanel`
//         }
//     }
    }
    /**
     * Menampilkan dialog pemilihan tingkat kesulitan
     */
    public void showDifficultyDialog() {
        String[] options = {"1", "2", "3"}; // 1: Easy, 2: Medium, 3: Hard
        String input = (String) JOptionPane.showInputDialog(
                this,
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
                    JOptionPane.showMessageDialog(this, "Please enter a valid difficulty (1, 2, or 3).");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number (1, 2, or 3).");
            }
        }
    }

    
    /** The entry main() entry method */
    public static void main(String[] args) {
        SudokuMain sudoku = new SudokuMain();
        sudoku.setVisible(true);
        sudoku.showDifficultyDialog();
        

    }
}