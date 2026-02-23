package es.nom.juanfranciscoruiz.ansiterm.model;

import static es.nom.juanfranciscoruiz.ansiterm.model.CSI.ESC;

/**
 * Enumeration for background colors with ANSI codes for the terminal
 *
 * @author Juan F. Ruiz
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


    /**
     * Background color (up to 256). Uses ESC[48;5;{0-255}m
     */
    public static final String BACKGROUND_COLOR256 = ESC + "[48;5;";

    /**
     * The ANSI color code.
     */
    private final int codigo;

    /**
     * Constructs a new BGColor with the specified code.
     * 
     * @param codigo The ANSI color code.
     */
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

    @Override
    public String toString() {
        return "BGColor{'ANSI escape codes for background colors'}";
    }
}
