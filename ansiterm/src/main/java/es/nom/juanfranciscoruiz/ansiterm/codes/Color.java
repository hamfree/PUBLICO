package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Enumeration for foreground colors with ANSI codes for the terminal
 *
 * @author Juan F. Ruiz
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
     * Note: For some, black is not a color but the absence of light... and can the color black be bright? :-D
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

    /**
     * Foreground color (up to 256). Uses ESC[38;5;{0-255}m
     */
    public static final String FOREGROUND_COLOR256 = ESC + "[38;5;";
    
    /**
     * The ANSI color code.
     */
    private final int code;
    
    /**
     * Constructs a new Color with the specified code.
     * 
     * @param code The ANSI color code.
     */
    Color(int code){
        this.code = code;
    }
    
    /**
     * Returns the code as a string.
     * @return a string with the textual representation of the code
     */
    public String getAsString() {
        return String.valueOf(this.code);
    }
}
