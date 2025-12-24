package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Enumeration for background colors with ANSI codes for the terminal
 * @author juanf
 */
public enum BGColor {
    /**
     * Black color 
     */
    NEGRO(40),
    /**
     * Red color
     */
    ROJO(41),
    /**
     * Green color
     */
    VERDE(42),
    /**
     * Yellow color
     */
    AMARILLO(43),
    /**
     * Blue color
     */
    AZUL(44),
    /**
     * Magenta color
     */
    MAGENTA(45),
    /**
     * Cyan color
     */
    CIAN(46),
    /**
     * White color
     */    
    BLANCO(47),
    /**
     * Bright black color (if that color can be bright, of course)
     */
    NEGRO_BRILLANTE(100),
    /**
     * Bright red color
     */
    ROJO_BRILLANTE(101),
    /**
     * Bright green color
     */
    VERDE_BRILLANTE(102),
    /**
     * Bright yellow color
     */
    AMARILLO_BRILLANTE(103),
    /**
     * Bright blue color
     */
    AZUL_BRILLANTE(104),
    /**
     * Bright magenta color
     */
    MAGENTA_BRILLANTE(105),
    /**
     * Bright cyan color
     */
    CIAN_BRILLANTE(106),
    /**
     * Bright white color
     */
    BLANCO_BRILLANTE(107),
    /**
     * Returns the background color to its default value.
     */
    DEFECTO(49);

    private final int codigo;

    BGColor(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Returns the code as a string.
     * @return a string with the textual representation of the code
     */
    String getAsString() {
        return String.valueOf(this.codigo);
    }
}
