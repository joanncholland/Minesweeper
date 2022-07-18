import java.util.Scanner;

/**
 * Represents the minesweeper game.
 */
public class Minesweeper {

    private static boolean playing = true;
    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Minesweeper");

        playGame();

    }

    /**
     * Initiates a new game of minesweeper.
     */
    public static void playGame() {
        Board board = chooseDifficulty();
        board.generateMines();
        board.setBoard();

        while(playing) {
            System.out.println("Number of mines: " + board.getNumberOfMines());
            System.out.println("Flags left: " + (board.getNumberOfMines() - board.countNumberOfFlags(board.getBoard())));
            board.printBoard(board.getBoard());

            System.out.println("Enter reveal/flag/unflag followed by the row-column coordinate (i.e. \"reveal 2-3\")");

            String[] splitInput = new String[2];

            // input validation for command
            boolean validInput = false;
            while(!validInput) {
                String userInputStr = userInput.nextLine();
                splitInput = userInputStr.split(" ");

                try {
                    System.out.println(userInputStr);
                    // use regex to check for valid input string
                    if (!userInputStr.matches("[reveal|flag|unflag]+\\s\\d{1,2}-\\d{1,2}")) {
                        System.out.println("Incorrect input - please enter reveal/flag/unflag followed by the row-column coordinate (i.e. \"reveal 2-3\")");
                    } else { // if regex accurate, check row-col values in board range
                        String[] coordinates = splitInput[1].split("-");
                        int row = Integer.parseInt(coordinates[0]);
                        int column = Integer.parseInt(coordinates[1]);

                        if (row < 0 || row > board.getNumRows() || column < 0 || column > board.getNumCols()) {
                            System.out.println("Invalid row-column coordinates, please make sure they're in range, from 0 to " + board.getNumRows());
                            throw new ArrayIndexOutOfBoundsException();
                        } else {
                            validInput = true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid row and column within range");
                }

            }

            String command = splitInput[0];
            String[] coords = splitInput[1].split("-");
            int row = Integer.parseInt(coords[0]);

            int column = Integer.parseInt(coords[1]);
            Location loc = new Location();
            loc.setRow(row);
            loc.setColumn(column);
            switch (command) {
                case "flag":
                    board.getBoard()[row][column].flag(loc);
                    break;
                case "unflag":
                    board.getBoard()[row][column].unflag(loc);
                    break;
                case "reveal":
                    if (board.getBoard()[row][column].isMine()) {
                        board.revealAll(board.getBoard());
                        board.printBoard(board.getBoard());
                        System.out.println("You chose a mine - game over!");
                        playing = false;
                        break;
                    } else {
                        board.getBoard()[row][column].floodfill(loc, board.getBoard());
                        if (board.countNumberOfRevealedCells(board.getBoard()) == board.getNumberOfAccurateRevealedCells()) {
                            board.revealAll(board.getBoard());
                            board.printBoard(board.getBoard());
                            System.out.println("Congrats - you won!");
                            playing = false;
                            break;
                        }
                    }
            }
        }
    }

    /**
     * Prompts the player to choose a difficulty level (1-easy,
     *                                                  2-medium,
     *                                                  3-hard).
     *
     * @return A new board based on the chosen difficulty level (easy: 8x8 with 10 mines,
     *                                                          medium: 14x14 with 40 mines,
     *                                                          hard: 20x20 with 99 mines).
     */
    public static Board chooseDifficulty() {
        // choose difficulty
        int difficulty;
        boolean validDifficulty = false;
        Board board = new Board(0,0,0);

        // input validation for difficulty
        while(!validDifficulty) {
            try {
                System.out.println("Please enter your difficulty level:");
                System.out.println("1 - easy");
                System.out.println("2 - medium");
                System.out.println("3 - hard");
                difficulty = Integer.parseInt(userInput.nextLine());
                if (difficulty != 1 && difficulty != 2 && difficulty != 3) {
                    System.out.println("Please enter 1, 2 or 3.");
                } else {
                    if (difficulty == 1) {
                        board = new Board(8, 8, 10);
                        validDifficulty = true;
                    } else if (difficulty == 2) {
                        board = new Board(14, 14, 40);
                        validDifficulty = true;
                    } else {
                        board = new Board(20, 20, 99);
                        validDifficulty = true;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number - 1, 2 or 3.");
            }
        }
        return board;
    }
}
