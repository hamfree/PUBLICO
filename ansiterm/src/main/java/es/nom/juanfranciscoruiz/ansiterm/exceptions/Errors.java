package es.nom.juanfranciscoruiz.ansiterm.exceptions;

/**
 * Utility class providing predefined error messages used within the application.
 * This class is not meant to be instantiated.
 *
 * @author Juan F. Ruiz
 */
public class Errors {
    /**
     * Error message for empty or whitespace-only messages.
     */
    public static final String EX_NO_MSG = "No message, it is empty or only contains whitespace";
    /**
     * Error message for invalid color.
     */
    public static final String EX_NO_COL = "Invalid color";
    /**
     * Error message for invalid background color.
     */
    public static final String EX_NO_BACKCOL = "Invalid background color";
    /**
     * Error message for invalid style.
     */
    public static final String EX_STYLE_INVALID = "Invalid style";
    /**
     * Error message for unknown style.
     */
    public static final String EX_STYLE_UNKNOWN = "Unknown style";
    /**
     * Error message for invalid number of characters.
     */
    public static final String EX_CARS_INVALID = "Invalid number of characters";
    /**
     * Error message for invalid number of white spaces.
     */
    public static final String EX_WHITE_SPACE_INVALID = "Invalid number of white spaces";
    /**
     * Error message for invalid number of lines.
     */
    public static final String EX_LINES_INVALID = "Invalid number of lines";

    /**
     * This class is not meant to be instantiated.
     */
    private Errors() {}
}
