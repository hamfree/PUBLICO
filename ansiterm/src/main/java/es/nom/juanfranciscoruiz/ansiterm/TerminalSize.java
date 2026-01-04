package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Represents the size of the terminal.
 * 
 * @author juanf
 */
public class TerminalSize {
    /**
     * Width of the terminal (in characters).
     */
   private int columnas;
   
   /**
    * Height of the terminal (in lines).
    */
   private int lineas;

    /**
     * Constructs a new TerminalSize with the specified width and height.
     *   
     * @param width The width of the terminal.
     * @param height The height of the terminal.
     */
    public TerminalSize(int width, int height) {
        this.columnas = width;
        this.lineas = height;
    }

    /**
     * Gets the width of the terminal.
     * 
     * @return The number of columns.
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Sets the width of the terminal.
     * 
     * @param columnas The number of columns to set.
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    /**
     * Gets the height of the terminal.
     * 
     * @return The number of lines.
     */
    public int getLineas() {
        return lineas;
    }

    /**
     * Sets the height of the terminal.
     * 
     * @param lineas The number of lines to set.
     */
    public void setLineas(int lineas) {
        this.lineas = lineas;
    }

    /**
     * Returns a hash code value for the object.
     * 
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.columnas;
        hash = 41 * hash + this.lineas;
        return hash;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
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
        if (this.columnas != other.columnas) {
            return false;
        }
        return this.lineas == other.lineas;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TerminalSize{");
        sb.append("columnas=").append(columnas);
        sb.append(", lineas=").append(lineas);
        sb.append('}');
        return sb.toString();
    }
   
   
}
