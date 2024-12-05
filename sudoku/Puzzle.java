package sudoku;
/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle { //superclass nya adalah subject
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(int difficulty) {
        int clues;
        switch (difficulty){
            case 1: clues = 70; break; //easy
            case 2: clues = 60; break; // medium
            case 3: clues = 40; break; //hard
            default: clues = 70; //default ke easy
        }
        // I hardcode a puzzle here for illustration and testing.
        int[][] hardcodedNumbers =
                       {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                        {6, 7, 2, 1, 9, 5, 3, 4, 8},
                        {1, 9, 8, 3, 4, 2, 5, 6, 7},
                        {8, 5, 9, 7, 6, 1, 4, 2, 3},
                        {4, 2, 6, 8, 5, 3, 7, 9, 1},
                        {7, 1, 3, 9, 2, 4, 8, 5, 6},
                        {9, 6, 1, 5, 3, 7, 2, 8, 4},
                        {2, 8, 7, 4, 1, 9, 6, 3, 5},
                        {3, 4, 5, 2, 8, 6, 1, 7, 9}};

        // Copy from hardcodedNumbers into the array "numbers"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
            }
        }
        // Set semua kotak sebagai tidak diberikan (false) 
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) { 
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) { 
                isGiven[row][col] = false; 
            } 
        } 

        //boolean[][] hardcodedIsGiven =
                //{{true, true, true, true, true, false, true, true, true},
                        //{true, true, true, true, true, true, true, true, false},
                        //{true, true, true, false, true, true, true, true, true},
                        //{true, true, true, true, true, true, true, true, true},
                        //{true, true, true, true, true, true, true, true, true},
                        //{true, true, true, true, true, true, true, true, true},
                        //{true, true, true, true, true, true, true, true, true},
                        //{true, true, true, true, true, true, true, true, true},
                        //{true, true, true, true, true, true, true, true, true}};

        // Copy from hardcodedIsGiven into array "isGiven"
        //for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            //for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                //isGiven[row][col] = hardcodedIsGiven[row][col];

                // Randomly tentukan kotak yang akan diberikan berdasarkan jumlah clues 
            java.util.Random rand = new java.util.Random(); 
            for (int i = 0; i < clues; i++) { 
                int row, col; 
                do { 
                    row = rand.nextInt(SudokuConstants.GRID_SIZE); 
                    col = rand.nextInt(SudokuConstants.GRID_SIZE); 
                } while (isGiven[row][col]); // Hindari mengganti kotak yang sudah diisi 
                isGiven[row][col] = true; 
            }
        }
    

    //(For advanced students) use singleton design pattern for this class
}
