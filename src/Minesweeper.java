import java.util.Scanner;

public class Minesweeper {

    static boolean playing = true;
    static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {


        System.out.println("Minesweeper");

        Board board = chooseDifficulty();
        board.generateMines();
        board.setBoard();

        while(playing) {
            System.out.println("Number of mines: " + board.numberOfMines);
            System.out.println("Flags left: " + (board.numberOfMines - board.countNumberOfFlags(board.getBoard())));
            board.printBoard(board.getBoard());

            System.out.println("Enter reveal/flag/unflag followed by the row-column coordinate (i.e. \"reveal 2-3\")");


            String userInputStr = userInput.nextLine();
            String[] splitInput = userInputStr.split(" ");
            // input validation for command
            boolean validInput = false;
            while(!validInput) {
                String command = splitInput[0];
                String[] coords = splitInput[1].split("-");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);

                try {
                  if (!command.equals("reveal") && !command.equals("flag") && !command.equals("unflag")) {
                      System.out.println("Please enter 'reveal', 'flag' or 'unflag' for your command");
                  } else {
                      validInput = true;
                  }
                  validInput =  true;
                } catch (NumberFormatException e){
                    System.out.println("Please enter an integer for the row-column coordinates");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid row and column within range");
                }

            }

            String command = splitInput[0];
            String[] coords = splitInput[1].split("-");
            int x = Integer.parseInt(coords[0]);

            int y = Integer.parseInt(coords[1]);
            Location loc = new Location();
            loc.setX(x);
            loc.setY(y);
            switch (command) {
                case "flag":
                    board.getBoard()[x][y].flag(loc);
                    break;
                case "unflag":
                    board.getBoard()[x][y].unflag(loc);
                    break;
                case "reveal":
                    if (board.getBoard()[x][y].isMine) {
                        board.revealAll(board.getBoard());
                        board.printBoard(board.getBoard());
                        System.out.println("You chose a mine - game over!");
                        playing = false;
                        break;
                    } else {
                        board.getBoard()[x][y].floodfill(loc, board.getBoard());
                        if (board.countNumberOfRevealedCells(board.getBoard()) == board.numberOfAccurateRevealedCells) {
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
