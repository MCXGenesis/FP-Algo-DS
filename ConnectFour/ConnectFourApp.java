package ConnectFour;

import javax.swing.*;

public class ConnectFourApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Show the game mode selection dialog
            String[] options = {"1 Player", "2 Players"};
            int mode = JOptionPane.showOptionDialog(null,
                    "Select game mode", "Game Mode",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            // If the user closes the dialog or selects an invalid option, exit the application
            if (mode == JOptionPane.CLOSED_OPTION) {
                System.exit(0);
            }

            // Create the game instance based on the selected mode
            ConnectFour game = new ConnectFour(mode);

            // Set up the JFrame
            JFrame frame = new JFrame("Connect Four");
            frame.setContentPane(game);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}