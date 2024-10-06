package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Enumeración para los colores de fondo con códigos ANSI para la terminal
 * @author juanf
 */
public enum BGColor {
    /**
     * Color negro 
     */
    NEGRO(40),
    /**
     * Color rojo
     */
    ROJO(41),
    /**
     * Color verde
     */
    VERDE(42),
    /**
     * Color amarillo
     */
    AMARILLO(43),
    /**
     * Color azul
     */
    AZUL(44),
    /**
     * Color magenta
     */
    MAGENTA(45),
    /**
     * Color cian
     */
    CIAN(46),
    /**
     * Color blanco
     */    
    BLANCO(47),
    /**
     * Color negro brillante (si ese color lo puede ser, claro)
     */
    NEGRO_BRILLANTE(100),
    /**
     * Color rojo brillante
     */
    ROJO_BRILLANTE(101),
    /**
     * Color verde brillante
     */
    VERDE_BRILLANTE(102),
    /**
     * Color amarillo brillante
     */
    AMARILLO_BRILLANTE(103),
    /**
     * Color azul brillante
     */
    AZUL_BRILLANTE(104),
    /**
     * Color magenta brillante
     */
    MAGENTA_BRILLANTE(105),
    /**
     * Color cian brillante
     */
    CIAN_BRILLANTE(106),
    /**
     * Color blanco brillante
     */
    BLANCO_BRILLANTE(107),
    /**
     * Hace que el color de fondo vuelva a su valor por defecto.
     */
    DEFECTO(49);

    private final int codigo;

    private BGColor(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el código como una cadena.
     * @return una cadena con la representación textual del código
     */
    String getAsString() {
        return String.valueOf(this.codigo);
    }
}
