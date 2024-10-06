package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Almacena la posición del cursor en la terminal. Usada en la clase Terminal
 * @see es.nom.juanfranciscoruiz.ansiterm.ANSITerm
 * @author juanf
 */
public class Posicion {

    /**
     * La columna actual del cursor
     */
    private final int col;
    
    /**
     * La línea actual del cursor
     */
    private final int lin;

    /**
     * Genera un nuevo objeto Posicion con los parámetros que se le pasan
     * @param col un entero con el número de columna de la terminal donde está el cursor actualmente
     * @param lin un entero con el número de línea de la terminal donde está el cursor actualmente
     */
    public Posicion(int col, int lin) {
        this.col = col;
        this.lin = lin;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Posicion)) {
            return false;
        }

        Posicion other = (Posicion) obj;
        return getCol() == other.getCol() && getLin() == other.getLin();
    }

    /**
     * Obtiene el número de columna donde está el cursor actualmente
     * @return un entero con el número de columna de la terminal donde está el cursor actualmente
     */
    public int getCol() {
        return col;
    }

    /**
     * Devuelve el número de fila donde está el cursor actualmente.
     * @return un entero con el número de línea de la terminal donde está el cursor actualmente
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
        return String.format("Posicion[%d,%d]", col, lin);
    }
}
