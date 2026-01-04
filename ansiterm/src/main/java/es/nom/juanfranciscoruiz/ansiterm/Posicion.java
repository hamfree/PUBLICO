package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Stores the cursor position in the terminal. Used in the Terminal class
 *
 * @author juanf
 * @see es.nom.juanfranciscoruiz.ansiterm.ANSITerm
 */
public class Posicion {

    /**
     * The current cursor column
     */
    private final int col;

    /**
     * The current cursor line
     */
    private final int lin;

    /**
     * Generates a new Position object with the parameters passed to it
     *
     * @param col an integer containing the column number of the terminal where
     *            the cursor is currently located
     * @param lin an integer containing the line number of the terminal where
     *            the cursor is currently located
     */
    public Posicion(int col, int lin) {
        this.col = col;
        this.lin = lin;
    }

    /**
     * Implements equality check based on column and line
     * @param obj   the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Posicion other)) {
            return false;
        }

        return getCol() == other.getCol() && getLin() == other.getLin();
    }

    /**
     * Gets the column number where the cursor is currently located
     *
     * @return an integer containing the column number of the terminal where
     * the cursor is currently located
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the row number where the cursor is currently located.
     *
     * @return an integer containing the line number of the terminal where the
     * cursor is currently locatedv
     */
    public int getLin() {
        return lin;
    }

    /**
     * Overrides the hashCode method of Object
     *
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return (col + lin) * (col + lin + 1) / 2 + lin;
    }

    /**
     * Overrides the toString method of Object
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("Posicion[%d,%d]", col, lin);
    }
}
