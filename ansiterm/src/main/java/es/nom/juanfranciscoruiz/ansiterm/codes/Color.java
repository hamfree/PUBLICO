package es.nom.juanfranciscoruiz.ansiterm.codes;

/**
 * Enumeration for foreground colors with ANSI codes for the terminal
 * @author juanf
 */
public enum Color {
    /**
     * Black foreground color.
     */
    BLACK(30),
    /**
     * Red foreground color.
     */
    RED(31),
    /**
     * Green foreground color.
     */
    GREEN(32),
    /**
     * Yellow foreground color.
     */
    YELLOW(33),
    /**
     * Blue foreground color.
     */
    BLUE(34),
    /**
     * Magenta foreground color.
     */
    MAGENTA (35), 
    /**
     * Cyan foreground color.
     */
    CYAN(36),
    /**
     * White foreground color.
     */
    WHITE(37),
    /**
     * Returns the foreground color to its default value.
     */
    DEFAULT(39),
    /**
     * Bright black foreground color.
     */
    GLOSSY_BLACK (90),
    /**
     * Bright red foreground color.
     */
    GLOSSY_RED(91),
    /**
     * Bright green foreground color.
     */
    GLOSSY_GREEN(92),
    /**
     * Bright yellow foreground color.
     */
    GLOSSY_YELLOW(93),
    /**
     * Bright blue foreground color.
     */
    GLOSSY_BLUE(94),
    /**
     * Bright magenta foreground color.
     */
    GLOSSY_MAGENTA(95),
    /**
     * Bright cyan foreground color.
     */
    GLOSSY_CYAN(96),
    /**
     * Bright white foreground color.
     */
    GLOSSY_WHITE(97),;
    
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
