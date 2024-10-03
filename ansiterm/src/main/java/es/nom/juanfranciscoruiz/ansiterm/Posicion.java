package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Almacena la posición del cursor en la terminal. Usada en la clase Terminal
 * @see Terminal
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

    public int getCol() {
        return col;
    }

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
