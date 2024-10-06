package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Representa el tamaño de la terminal
 * 
 * @author juanf
 */
public class TerminalSize {
    /**
     * Ancho del terminal (en caracteres)
     */
   private int columnas;
   
   /**
    * Alto del terminal (en líneas)
    */
   private int lineas;

   /**
    * 
    * @param width
    * @param height 
    */
    public TerminalSize(int width, int height) {
        this.columnas = width;
        this.lineas = height;
    }

    /**
     * 
     * @return 
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * 
     * @param columnas 
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    /**
     * 
     * @return 
     */
    public int getLineas() {
        return lineas;
    }

    /**
     * 
     * @param lineas 
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
        sb.append("columnas=").append(columnas);
        sb.append(", lineas=").append(lineas);
        sb.append('}');
        return sb.toString();
    }
   
   
}
