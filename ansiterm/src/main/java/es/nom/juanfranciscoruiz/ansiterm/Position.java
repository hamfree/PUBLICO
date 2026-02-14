package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Stores the cursor position in the terminal. Used in the Terminal class
 * @see es.nom.juanfranciscoruiz.ansiterm.ANSITerm
 *
 * @author Juan F. Ruiz
 */
public class Position {

    /**
     * The current column of the cursor
     */
    private final int col;
    
    /**
     * The current line of the cursor
     */
    private final int lin;

    /**
     * Generates a new Position object with the parameters passed to it
     * @param col an integer with the terminal column number where the cursor is currently located
     * @param lin an integer with the terminal line number where the cursor is currently located
     */
    public Position(int col, int lin) {
        this.col = col;
        this.lin = lin;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position other)) {
            return false;
        }
      
      return getCol() == other.getCol() && getLin() == other.getLin();
    }

    /**
     * Gets the column number where the cursor is currently located
     * @return an integer with the terminal column number where the cursor is currently located
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the row number where the cursor is currently located.
     * @return an integer with the terminal line number where the cursor is currently located
     */
    public int getLin() {
        return lin;
    }

    @Override
    public int hashCode() {
        return (col + lin) * (col + lin + 1) / 2 + lin;
    }

    @Override
    public String toString() {
        return String.format("Position[%d,%d]", col, lin);
    }
}
