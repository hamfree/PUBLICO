package es.nom.juanfranciscoruiz.ansiterm.codes;

/**
 * Enumeration for background colors with ANSI codes for the terminal
 * @author juanf
 */
public enum BGColor {
    /**
     * Black color 
     */
    BLACK(40),
    /**
     * Red color
     */
    RED(41),
    /**
     * Green color
     */
    GREEN(42),
    /**
     * Yellow color
     */
    YELLOW(43),
    /**
     * Blue color
     */
    BLUE(44),
    /**
     * Magenta color
     */
    MAGENTA(45),
    /**
     * Cyan color
     */
    CYAN(46),
    /**
     * White color
     */    
    WHITE(47),
    /**
     * Bright black color (if that color can be bright, of course)
     */
    GLOSSY_BLACK(100),
    /**
     * Bright red color
     */
    GLOSSY_RED(101),
    /**
     * Bright green color
     */
    GLOSSY_GREEN(102),
    /**
     * Bright yellow color
     */
    GLOSSY_YELLOW(103),
    /**
     * Bright blue color
     */
    GLOSSY_BLUE(104),
    /**
     * Bright magenta color
     */
    GLOSSY_MAGENTA(105),
    /**
     * Bright cyan color
     */
    GLOSSY_CYAN(106),
    /**
     * Bright white color
     */
    GLOSSY_WHITE(107),
    /**
     * Returns the background color to its default value.
     */
    DEFAULT(49);

    private final int codigo;

    BGColor(int codigo) {
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
