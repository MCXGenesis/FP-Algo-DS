### SUDOKU

1. Reset/Restart the game.
2. A good Sudoku engine shall accept any "valid" number at the time of input (no duplicate in row, column and sub-grid), but signal a conflict whenever it is detected. Highlight the conflicting cells.
3. After entering a guess, highlight all boxes with the same value of the guess and signal the conflicting cells if any.
4. Choice of puzzles and difficulty levels (e.g., easy, intermediate, difficult).
5. Create a "menu bar" for options such as "File" ("New Game", "Reset Game", "Exit"), "Options", and "Help" (Use JMenuBar, JMenu, and JMenuItem classes).
6. Create a "status bar" (JTextField at the south zone of BorderLayout) to show the messages (e.g., number of cells remaining) (google "java swing statusbar").
7. Beautify your graphical interface, e.g., theme (color, font, images), layout, etc.
8. Timer (pause/resume), score, progress bar.
9. A side panel for command, display, strategy?
10. Automatic puzzle generation with various difficulty level.
11. The sample program processes ActionEvent of the JTextField, which requires user to push the ENTER key. Try KeyEvent with keyTyped() handler; or other means that does not require pushing of ENTER key.
12. Sound effect, background music, enable/disable sound?
13. High score and player name?
14. Hints and cheats (reveal a cell, or reveal all cells with number 8)?
15. Theme?
16. Use of images and icons?
17. Welcome screen?
18. Mouse-less interface?
19. (Very difficult) Multi-Player network game.