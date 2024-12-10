import javax.swing.*;

import sudoku.SoundPlayer;
import sudoku.SudokuMain;
import tictactoe.TicTacToe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame {
    private static final long serialVersionUID = 1L;

    public StartMenu() {
        // Use 'this' as the JFrame
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
        add(panel); // Add the panel to 'this' JFrame

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

        //Supporting text

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
                            SwingUtilities.invokeLater(() -> new SudokuMain().showDifficultyDialog());
                            break;
                        case "Tictactoe":
                            SwingUtilities.invokeLater(() -> new TicTacToe().show());
                            break;
                        case "Exit":
                            System.exit(0);
                            break;
                    }
                }
            });

            buttonContainer.add(button);
        }

        SoundPlayer.playSound(getName());

        // Set the JFrame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the menu
        SwingUtilities.invokeLater(() -> {
            StartMenu menu = new StartMenu();
            menu.setVisible(true);
        });
    }
}