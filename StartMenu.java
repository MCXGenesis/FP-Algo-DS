/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #3
 * 1 - 5026231107 - Naufal Maula Nabil
 * 2 - 5026231228 - Annisa Nur Fauzi
 * 3 - 5026231229 - Lailatul Fitaliqoh
 */
import javax.swing.*;
import sudoku.SoundPlayer;
import sudoku.SudokuMain;
import tictactoe.TicTacToe;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class StartMenu extends JFrame {
    private static final long serialVersionUID = 1L;
    private float opacity = 0.0f;
    private Timer fadeStartTimer;
    private Timer fadeEndTimer;
    private BufferedImage backg;

    public StartMenu() {
        try {
            backg = ImageIO.read(getClass().getResourceAsStream("image/menu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //setTitle("Welcome to Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        // Aktifkan fullscreen
        enableFullScreen();

        // Panel utama untuk seluruh komponen
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backg != null) {
                    g.drawImage(backg, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(null);
        add(mainPanel);

        // Custom font (fallback ke default)
        Font customFont, titleFont = null;
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/OMORI_GAME2.ttf")).deriveFont(96f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(titleFont);
            customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/OMORI_GAME2.ttf")).deriveFont(36f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
        } catch (Exception e) {
            customFont = new Font("Arial", Font.BOLD, 36);
        }

        // Judul
//        JLabel title = new JLabel("WELCOME", SwingConstants.CENTER);
//        title.setForeground(Color.BLACK);
//        title.setFont(titleFont);
//        title.setBounds(getWidth() / 2 - 200, 50, 400, 100);
//        mainPanel.add(title);

        // Kontainer tombol
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS)); // Ubah ke vertikal
        buttonContainer.setBackground(new Color(0, 0, 0, 0));
        buttonContainer.setBounds(getWidth() / 2 - 100, 300, 200, 300); // Reposisi dan sesuaikan ukuran
        mainPanel.add(buttonContainer);

        // Tombol
        String[] buttonNames = {"Sudoku", "Tictactoe", "Exit"};
        for (String buttonName : buttonNames) {
            JButton button = new JButton(buttonName);
            button.setFont(customFont.deriveFont(36f));
            button.setForeground(Color.BLACK);
            //button.setBackground(Color.WHITE);
            button.setBackground(new Color(0, 0, 0, 0)); // Background transparan
            button.setFocusPainted(false);
            button.setContentAreaFilled(true);
            button.setOpaque(true);
            button.setAlignmentX(Component.CENTER_ALIGNMENT); // Agar tombol sejajar di tengah

            // Efek hover dengan perubahan warna
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    // Ganti warna latar belakang saat mouse masuk
                    button.setBackground(new Color(92, 92, 179)); // Warna latar belakang saat hover
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    // Kembali ke warna latar belakang normal saat mouse keluar
                    button.setBackground(new Color(0, 0, 0, 0)); // Warna latar belakang tombol normal
                }
            });

            // Aksi tombol
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (button.getText()) {
                        case "Sudoku":
                            openSudoku();
                            break;
                        case "Tictactoe":
                            openTicTacToe();
                            break;
                        case "Exit":
                            fadeEndTimer.start();
                            break;
                    }
                }
            });

            buttonContainer.add(Box.createRigidArea(new Dimension(0, 30))); // Tambahkan spasi antar tombol
            buttonContainer.add(button);
        }
        buttonContainer.revalidate();
        buttonContainer.repaint();
        setVisible(true);
        SoundPlayer.playSound(getName());

        // Timer fade-in
        fadeStartTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    fadeStartTimer.stop();
                }
                setOpacity(opacity);
            }
        });

        // Timer fade-out
        fadeEndTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0.05f) {
                    opacity = 0f;
                    fadeEndTimer.stop();
                    System.exit(0);
                }
                setOpacity(opacity);
            }
        });

        fadeStartTimer.start();
        setOpacity(0.0f);
        setVisible(true);
    }

    private void enableFullScreen() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        device.setFullScreenWindow(this);
    }

    private void openSudoku() {
        SwingUtilities.invokeLater(() -> {
            dispose(); // Tutup menu utama
            SudokuMain sudokuMain = new SudokuMain();
            sudokuMain.setVisible(true);
            sudokuMain.showDifficultyDialog();
        });
    }

    private void openTicTacToe() {
        SwingUtilities.invokeLater(() -> {
            dispose(); // Tutup menu utama
            JFrame frame = new JFrame(TicTacToe.TITLE);
            TicTacToe ticTacToe = new TicTacToe();

            JPanel parentPanel = new JPanel();
            parentPanel.setLayout(new BorderLayout());
            if (ticTacToe.timerLabel != null) {
                parentPanel.add(ticTacToe.timerLabel, BorderLayout.NORTH);
            }
            parentPanel.add(ticTacToe, BorderLayout.CENTER);

            frame.setContentPane(parentPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
