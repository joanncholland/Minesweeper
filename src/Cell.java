/**
 * Represents a cell on the minesweeper board.
 * @author Joann Holland
 */
public class Cell extends Board {
    private Location location;
    private boolean isRevealed;
    private boolean isMine;
    private int numberSurroundingMines;
    private boolean isFlagged;

    /**
     * Creates a new cell.
     * @param location The row, column location of the cell on the board.
     * @param numRows The number of rows on the board.
     * @param numCols The number of columns on the board.
     * @param numberOfMines The number of mines on the board.
     * @param isMine If the cell is a mine.
     * @param isRevealed If the cell has been revealed on the board.
     * @param isFlagged If the cell has been flagged as a potential mine location.
     */
    public Cell(Location location, int numRows, int numCols, int numberOfMines, boolean isMine, boolean isRevealed, boolean isFlagged) {
        super(numRows, numCols, numberOfMines);
        this.location = location;
        this.isMine = isMine;
        this.isRevealed = isRevealed;
        this.isFlagged = isFlagged;
    }

    /**
     * Gets if the cell is revealed.
     * @return A boolean indicating if the cell is revealed on the board.
     */
    public boolean isRevealed() {
        return this.isRevealed;
    }

    /**
     * Sets the revealed status of the cell.
     * @param value The boolean value to set the revealed status of the cell.
     */
    public void setRevealed(boolean value) {
        this.isRevealed = value;
    }

    /**
     * Gets if the cell is a mine.
     * @return A boolean indicating if the cell is a mine.
     */
    public boolean isMine() {
        return this.isMine;
    }

    /**
     * Gets if the cell is flagged.
     * @return A boolean indicating if the cell is flagged.
     */
    public boolean isFlagged() {
        return this.isFlagged;
    }

    /**
     * Sets the flag status of the cell.
     * @param value The boolean value to set the flag status of the cell.
     */
    public void setFlagged(boolean value) {
        this.isFlagged = value;
    }

    /**
     * Set the value of the cell to be the number of surrounding mines.
     * @param numberSurroundingMines The integer specyfing the number of mines surrounding the cell.
     */
    public void setNumberSurroundingMines(int numberSurroundingMines) {
        this.numberSurroundingMines = numberSurroundingMines;
    }

    /**
     * Flags the cell.
     * @param location A Location object specifying the [row, column] location of the cell.
     */
    public void flag(Location location) {
        if (!this.isRevealed) {
            this.isFlagged = true;
            getFlagLocations().add(location);
        } else {
            System.out.println("That cell has been revealed already.");
        }
    }

    /**
     * Unflags the cell.
     * @param location A Location object specifying the [row, column] location of the cell.
     */
    public void unflag(Location location) {
        this.isFlagged = false;
        getFlagLocations().remove(location);
    }

    /**
     * Overrides the toString method to to output the contents of the cell based on what it contains.
     * @return If cell is not revealed - "?", if cell is mine - "*", if cell is flagged - "!".
     */
    @Override
    public String toString() {
        String str;
        if (isRevealed) {
            str = Integer.toString(numberSurroundingMines);
        } else {
            str = "?";
        }
        if (isMine && isRevealed) {
            str = "*";
        } else if (isMine && !isRevealed) {
            str = "?";
        }
        if (isFlagged) {
            str = "!";
        }
        return str;
    }
}
