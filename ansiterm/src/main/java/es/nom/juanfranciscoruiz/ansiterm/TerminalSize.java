package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Represents the terminal size.
 * 
 * @author juanf
 */
public class TerminalSize {
    /**
     * Terminal width (in characters).
     */
   private int columnas;
   
   /**
    * Terminal height (in lines).
    */
   private int lineas;

   /**
    * Creates a new TerminalSize object.
    * 
    * @param width terminal width (columns)
    * @param height terminal height (lines)
    */
    public TerminalSize(int width, int height) {
        this.columnas = width;
        this.lineas = height;
    }

    /**
     * Gets the number of columns.
     * 
     * @return the number of columns
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Sets the number of columns.
     * 
     * @param columnas the number of columns
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    /**
     * Gets the number of lines.
     * 
     * @return the number of lines
     */
    public int getLineas() {
        return lineas;
    }

    /**
     * Sets the number of lines.
     * 
     * @param lineas the number of lines
     */
    public void setLineas(int lineas) {
        this.lineas = lineas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.columnas;
        hash = 41 * hash + this.lineas;
        return hash;
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TerminalSize{");
        sb.append("columns=").append(columnas);
        sb.append(", lines=").append(lineas);
        sb.append('}');
        return sb.toString();
    }
   
   
}
