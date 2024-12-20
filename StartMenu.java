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
            backg = ImageIO.read(getClass().getResourceAsStream("image/apcb.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("Welcome to Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background color and panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 800, 600);
        panel.setLayout(null);
        add(panel);

        // Custom font (fallback to a default)
        Font customFont, titleFont = null;
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/OMORI_GAME2.ttf")).deriveFont(96f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(titleFont);
            customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/OMORI_GAME2.ttf")).deriveFont(36f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
        } catch (Exception e) {
            customFont = new Font("Arial", Font.BOLD, 36);
        }

        // Title
        JLabel title = new JLabel("WELCOME", SwingConstants.CENTER);
        title.setForeground(Color.BLACK);
        title.setFont(titleFont);
        title.setBounds(200, 50, 400, 100);
        panel.add(title);

        // Button container
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.setBounds(100, 450, 600, 80);
        panel.add(buttonContainer);

        // Buttons
        String[] buttonNames = {"Sudoku", "Tictactoe", "Exit"};
        for (String buttonName : buttonNames) {
            JButton button = new JButton(buttonName);
            button.setFont(customFont.deriveFont(36f));
            button.setForeground(Color.BLACK);
            button.setBackground(Color.WHITE);
            button.setFocusPainted(false);

            // Button hover effects
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.LIGHT_GRAY);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.WHITE);
                }
            });

            // Button actions
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (button.getText()) {
                        case "Sudoku":
                            SwingUtilities.invokeLater(() -> {
                                SudokuMain sudokuMain = new SudokuMain();
                                sudokuMain.setVisible(true);
                                sudokuMain.showDifficultyDialog();
                            });
                            break;
                        case "Tictactoe":
                            SwingUtilities.invokeLater(() -> {
                                JFrame frame = new JFrame(TicTacToe.TITLE);
                                TicTacToe ticTacToe = new TicTacToe();

                                JPanel parentPanel = new JPanel();
                                parentPanel.setLayout(new BorderLayout());
                                if (ticTacToe.timerLabel != null) {
                                    parentPanel.add(ticTacToe.timerLabel, BorderLayout.NORTH);
                                }
                                parentPanel.add(ticTacToe, BorderLayout.CENTER);

                                frame.setContentPane(parentPanel);
                                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Jangan tutup aplikasi utama
                                frame.pack();
                                frame.setLocationRelativeTo(null);
                                frame.setVisible(true);
                            });
                            break;
                        case "Exit":
                            fadeEndTimer.start();
                            break;
                    }
                }
            });

            buttonContainer.add(button);
        }

        SoundPlayer.playSound(getName());

        // Set the JFrame visible
        setOpacity(0.0f);
        setVisible(true);

        // Initialize and start the fade-in timer
        fadeStartTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    fadeStartTimer.stop();
                }
                setOpacity(opacity);
                repaint();
            }
        });

        fadeEndTimer = new Timer(100, new ActionListener() { // plus system exit
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0.05f) {
                    opacity = 0f;
                    fadeEndTimer.stop();
                    System.exit(0); // exit after fade
                }
                setOpacity(opacity);
                repaint();
            }
        });

        fadeStartTimer.start();
    }
}