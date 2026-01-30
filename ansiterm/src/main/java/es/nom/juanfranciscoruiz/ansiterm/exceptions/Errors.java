package es.nom.juanfranciscoruiz.ansiterm.exceptions;

/**
 * Utility class providing predefined error messages used within the application.
 * This class is not meant to be instantiated.
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
     * This class is not meant to be instantiated.
     */
    private Errors() {}
}
