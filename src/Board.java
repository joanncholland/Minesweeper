import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Board extends Minesweeper {
    int numCols;
    int numRows;
    Location[] mineLocations;
    int numberOfMines;
    Cell[][] board;
    int totalNumberOfCells;
    int numberCellsHidden;
    public ArrayList<Location> flagLocations = new ArrayList<>();
    public int numberOfAccurateRevealedCells;

    public Board(int numCols, int numRows, int numberOfMines) {
        this.numCols= numCols;
        this.numRows = numRows;
        this.numberOfMines = numberOfMines;
        this.totalNumberOfCells = this.numCols*this.numRows;
        this.numberCellsHidden = 0;
        setNumberOfAccurateRevealedCells((numCols * numRows) - numberOfMines);
    }

    public int countNumberOfRevealedCells(Cell[][] board) {
        int count = 0;
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                if (board[i][j].isRevealed) count++;
            }
        }
        return count;
    }

    public int countNumberOfFlags(Cell[][] board) {
        int count = 0;
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                if (board[i][j].isFlagged) count++;
            }
        }
        return count;
    }

    public void setNumberOfAccurateRevealedCells(int numberOfAccurateRevealedCells) {
        this.numberOfAccurateRevealedCells = numberOfAccurateRevealedCells;
    }

    public Cell[][] getBoard() {
        return this.board;
    }

    public Cell[][] setBoard() {
        board = new Cell[this.numCols][this.numRows];

        // fill array with 0s
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                Location loc = new Location();
                loc.setX(i);
                loc.setY(j);

                Cell cell = new Cell(loc, this.numCols, this.numRows, this.numberOfMines, false, false, false);;

                board[i][j] = cell;
            }
        }

        // place mines
        for (Location location : mineLocations) {
            Cell cell = new Cell(location, this.numCols, this.numRows, this.numberOfMines, true, false, false);
            board[location.x][location.y] = cell;
        }

        // count surrounding mines
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                board[i][j].setNumberSurroundingMines(countSurroundingMines(i,j, this.board));
            }
        }

        return board;
    }

    public void generateMines() {
        mineLocations = new Location[this.numberOfMines];
        for(int i = 0; i < this.numberOfMines; i++) {
            Location loc = new Location();
            // generate random x, y within board limits
            int x = getRandomNumber(this.numCols);
            int y = getRandomNumber(this.numRows);
            loc.setX(x);
            loc.setY(y);
            mineLocations[i] = loc;
        }
    }

    public int getRandomNumber(int max) {
        return (int) ((Math.random() * (max - 0)) + 0);
    }

    public int countSurroundingMines(int x, int y, Cell[][] board) {
        int mines = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i + x >= 0 && i + x < numCols && j + y >= 0 && j + y < numRows) { // check if valid index
                    if (board[i+x][j+y].isMine) {
                        mines++;
                    }
                }
            }
        }
        return mines;
    }

    // reveal all cells
    public void revealAll(Cell[][] board) {
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                if (board[i][j].isMine) {
                    board[i][j].isFlagged = false;
                }
                board[i][j].isRevealed = true;
            }
        }
    }

    public void printBoard(Cell[][] board) {
        System.out.print("\t");
        for (int i = 0; i < numCols; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < numCols; i++) {
            System.out.print("____");
        }
        System.out.print("\n");
        for (int i = 0; i < numCols; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < numRows; j++) {
                System.out.print("\t" + board[i][j]);
            }
            System.out.print(" | \n");
        }

        System.out.print("  ");
        for (int i = 0; i < numCols; i++) {
            System.out.print("----");
        }
        System.out.println();
    }

    // if there are no mines, recursively reveal cells
    public void floodfill(Location location, Cell[][] board) {
        if (!board[location.x][location.y].isRevealed) {
            int numberSurroundingMines = countSurroundingMines(location.x, location.y, board);

            try {
                if (numberSurroundingMines > 0) {
                    board[location.x][location.y].isRevealed = true;
                } else {
                    board[location.x][location.y].isRevealed = true;
                    if (location.x > 0) {
                        Location loc1 = new Location();
                        loc1.setX(location.x-1);
                        loc1.setY(location.y);
                        floodfill(loc1, board);
                    }
                    if (location.x < numRows) {
                        Location loc2 = new Location();
                        loc2.setX(location.x+1);
                        loc2.setY(location.y);
                        floodfill(loc2, board);
                    }
                    if (location.y > 0) {
                        Location loc3 = new Location();
                        loc3.setX(location.x);
                        loc3.setY(location.y-1);
                        floodfill(loc3, board);
                    }
                    if (location.y < numCols) {
                        Location loc4 = new Location();
                        loc4.setX(location.x);
                        loc4.setY(location.y+1);
                        floodfill(loc4, board);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return ;
            }
        }
    }

    // check if cell is on the board
    public boolean validCell(int x, int y) {
        if (x < 0 || y < 0) return false;
        if(x >= this.numCols || y >= this.numRows) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Minesweeper");

        Board board = new Board(9, 9, 10);
        board.generateMines();
        board.setBoard();
        System.out.println("Mines left: " + board.numberOfMines);
        System.out.println(board);

        System.out.println("Enter column-row combination e.g. 0-2");
        Scanner userInput = new Scanner(System.in);
        String userInputStr = userInput.nextLine();
        String[] splitInput = userInputStr.split("-");
        int col = Integer.parseInt(splitInput[0]);
        int row = Integer.parseInt(splitInput[1]);
        Location cellLoc = new Location();
        cellLoc.setX(col);
        cellLoc.setY(row);
        board.getBoard()[col][row].floodfill(cellLoc, board.getBoard());
        System.out.println(board);

    }

    @Override
    public String toString() {
        return Arrays.deepToString(board).replace("[["," ").replace("[","").replace(", ", " ").replace("]", "\n");
    }
}
