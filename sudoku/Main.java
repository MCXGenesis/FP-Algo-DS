/*
Algoritma Struktur Data
Kelas   : C
Anggota :   Naufal Maula Nabil  - 5026231107
            Annisa Nur Fauzi    - 5026231228
            Lailatul Fitaliqoh  - 5026231229
 */
package sudoku;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuMain sudo = new SudokuMain();
            sudo.showWelcomePage();
        });
    }
}
