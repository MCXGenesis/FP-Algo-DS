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
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        setUndecorated(true); // Remove window decorations for fullscreen mode
        setMinimumSize(new Dimension(800 ,600));
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(board, BorderLayout.CENTER);

               // Add the game board panel to the center with a wrapper
               JPanel boardWrapper = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Ensure the board remains square
                    int size = Math.min(getWidth(), getHeight());
                    board.setBounds((getWidth() - size) / 2, (getHeight() - size) / 2, size, size);
                }
            };
            //boardWrapper.setLayout(null); // Use null layout for manual resizing
            boardWrapper.add(board); // Add the board to the wrapper
            cp.add(boardWrapper, BorderLayout.CENTER);
    
            // Add a resize listener to keep the board square
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    boardWrapper.repaint(); // Repaint on resize to update the square aspect ratio
                }
            });

        // Menambahkan dropdown untuk memilih mode tampilan
        modeSelector = new JComboBox<>(new String[]{"Light Mode", "Dark Mode"});
        modeSelector.addActionListener(e -> changeMode(modeSelector.getSelectedItem().toString()));

        // Panel bawah untuk tombol dan dropdown
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(modeSelector, BorderLayout.EAST);
        cp.add(bottomPanel, BorderLayout.SOUTH);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu menuFile = new JMenu("Menu");
        JMenuItem menuNewGame = new JMenuItem("New Game");
        JMenuItem menuExitGame = new JMenuItem("Exit");
        menuExitGame.addActionListener(e -> System.exit(0));
        menuNewGame.addActionListener(e -> showDifficultyDialog());

        menuFile.add(menuNewGame);
        menuFile.addSeparator();
        menuFile.add(menuExitGame);

        // Add "File" menu to the menu bar
        menuBar.add(menuFile);
        cp.add(menuBar, BorderLayout.PAGE_START);

        pack(); // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
    }

    /**
     * Mengubah mode tampilan berdasarkan pilihan pengguna
     */
    private void changeMode(String mode) {
        if (mode.equals("Dark Mode")) {
            getContentPane().setBackground(Color.DARK_GRAY);
            modeSelector.setBackground(Color.GRAY);
            modeSelector.setForeground(Color.WHITE);
            board.setBackground(Color.BLACK); // Sesuaikan dengan implementasi `GameBoardPanel`
        } else {
            getContentPane().setBackground(Color.WHITE);
            modeSelector.setBackground(Color.WHITE);
            modeSelector.setForeground(Color.BLACK);
            board.setBackground(Color.WHITE); // Sesuaikan dengan implementasi `GameBoardPanel`
        }
    }

    /**
     * Menampilkan dialog pemilihan tingkat kesulitan
     */
    private void showDifficultyDialog() {
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

    /**
     * Menampilkan halaman sambutan untuk memilih permainan
     */
    public static void showWelcomePage() {
        // Membuat opsi yang akan muncul di halaman selamat datang
        Object[] options = {"Start Game", "Exit"};

        // Menampilkan dialog sambutan
        int choice = JOptionPane.showOptionDialog(
                null,
                "Welcome to Sudoku Game!\nChoose an option to proceed:",
                "Welcome",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Tindakan berdasarkan pilihan pengguna
        if (choice == 0) {
            // Memulai permainan dengan dialog tingkat kesulitan
            new SudokuMain().showDifficultyDialog();
        } else {
            // Keluar dari aplikasi
            System.exit(0);
        }
    }

    /** The entry main() entry method */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> showWelcomePage());
    }
}
