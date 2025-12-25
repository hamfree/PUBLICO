package es.nom.juanfranciscoruiz.ansiterm.codes;

/**
 * Enumeration for foreground colors with ANSI codes for the terminal
 * @author juanf
 */
public enum Color {
    /**
     * Black foreground color.
     */
    NEGRO (30), 
    /**
     * Red foreground color.
     */
    ROJO (31), 
    /**
     * Green foreground color.
     */
    VERDE (32), 
    /**
     * Yellow foreground color.
     */
    AMARILLO (33), 
    /**
     * Blue foreground color.
     */
    AZUL (34), 
    /**
     * Magenta foreground color.
     */
    MAGENTA (35), 
    /**
     * Cyan foreground color.
     */
    CIAN (36),
    /**
     * White foreground color.
     */
    BLANCO (37),
    /**
     * Returns the foreground color to its default value.
     */
    DEFECTO (39),
    /**
     * Bright black foreground color.
     */
    NEGRO_BRILLANTE (90),
    /**
     * Bright red foreground color.
     */
    ROJO_BRILLANTE (91), 
    /**
     * Bright green foreground color.
     */
    VERDE_BRILLANTE (92), 
    /**
     * Bright yellow foreground color.
     */
    AMARILLO_BRILLANTE (93), 
    /**
     * Bright blue foreground color.
     */
    AZUL_BRILLANTE (94), 
    /**
     * Bright magenta foreground color.
     */
    MAGENTA_BRILLANTE (95), 
    /**
     * Bright cyan foreground color.
     */
    CIAN_BRILLANTE (96),
    /**
     * Bright white foreground color.
     */
    BLANCO_BRILLANTE (97),;
    
    private final int codigo;
    
    Color(int codigo){
        this.codigo = codigo;
    }
    
    /**
     * Returns the code as a string.
     * @return a string with the textual representation of the code
     */
    public String getAsString() {
        return String.valueOf(this.codigo);
    }
}
