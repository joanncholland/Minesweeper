/**
 * Represents a location or coordinate of a cell on the minesweeper board.
 * @author Joann Holland
 */
public class Location {
    private int row;
    private int column;

    /**
     * Creates a new location.
     */
    public Location() {
        this.row = row;
        this.column = column;
    }

    /**
     * Gets the row number.
     * @return An integer specifying the row number.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row number.
     * @param row An integer specifying the row number.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the column number.
     * @return An integer specifying the column number.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets the column number.
     * @param column An integer specifying the column number.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Overrides the equals method to evaluate whether one Location object equals another Location object.
     * @param obj The Location object to be compared.
     * @return A boolean value indicating whether the two Location objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location loc = (Location)obj;
            return this.row == loc.row && this.column == loc.column;
        }
        return false;
    }

    /**
     * Overrides the toString method to output the [row, column] coordinates.
     * @return The String containing the row and column coordinates in an array format.
     */
    @Override
    public String toString() {
        return "[" + row + ", " + column + "]";
    }
}
