package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Represents the dimensions of a terminal, including its width in columns
 * and height in lines.
 *
 * @author Juan F. Ruiz
 */
public class TerminalSize {
    /**
     * Terminal width (in characters).
     */
   private int columns;
   
   /**
    * Terminal height (in lines).
    */
   private int lines;

   /**
    * Creates a new TerminalSize object.
    * 
    * @param width terminal width (columns)
    * @param height terminal height (lines)
    */
    public TerminalSize(int width, int height) {
        this.columns = width;
        this.lines = height;
    }

    /**
     * Gets the number of columns.
     * 
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Sets the number of columns.
     * 
     * @param columns the number of columns
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * Gets the number of lines.
     * 
     * @return the number of lines
     */
    public int getLines() {
        return lines;
    }

    /**
     * Sets the number of lines.
     * 
     * @param lines the number of lines
     */
    public void setLines(int lines) {
        this.lines = lines;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.columns;
        hash = 41 * hash + this.lines;
        return hash;
    }

    /**
     * Implements equality check based on columns and lines
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TerminalSize other = (TerminalSize) obj;
        if (this.columns != other.columns) {
            return false;
        }
        return this.lines == other.lines;
    }

    /**
     * Returns string representation of terminal size
     */
    @Override
    public String toString() {
        String sb = "TerminalSize{" +
                "columns=" + columns +
                ", lines=" + lines +
                '}';
        return sb;
    }
}
