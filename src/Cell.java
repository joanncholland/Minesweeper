public class Cell extends Board {
    Location location;
    boolean isRevealed;
    boolean isMine;
    int numberSurroundingMines;
    boolean isFlagged;

    public Cell(Location location, int numRows, int numCols, int numberOfMines, boolean isMine, boolean isRevealed, boolean isFlagged) {
        super(numRows, numCols, numberOfMines);
        this.location = location;
        this.isMine = isMine;
        this.isRevealed = isRevealed;
        this.isFlagged = isFlagged;
    }

    public void setNumberSurroundingMines(int numberSurroundingMines) {
        this.numberSurroundingMines = numberSurroundingMines;
    }

    public Location getLocation() {
        return location;
    }

    public void flag(Location location) {
        this.isFlagged = true;
        flagLocations.add(location);
    }

    public void unflag(Location location) {
        this.isFlagged = false;
        flagLocations.remove(location);
    }

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
