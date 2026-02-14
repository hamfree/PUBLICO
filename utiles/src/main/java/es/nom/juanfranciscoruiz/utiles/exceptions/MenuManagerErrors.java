package es.nom.juanfranciscoruiz.utiles.exceptions;

/**
 * The MenuManagerErrors class encapsulates a collection of static constant error messages
 * specific to certain operations and validations within a menu management system.
 * These messages are intended to ensure consistent and clear error reporting throughout
 * the application. This class serves as a centralized reference point for error
 * message definitions, making it easier to maintain and update error-related logic.
 * <p>
 * This utility class is non-instantiable.
 */
public class MenuManagerErrors {
    /**
     * Private constructor to prevent instantiation.
     */
    private MenuManagerErrors() { }
    /**
     * Error message used to indicate that a null menu object was provided when attempting to
     * read user response. This constant is primarily used for exception
     * messages or validation errors to improve clarity and maintain consistency in
     * error reporting.
     */
    public static final String ERR_CANNOT_READ_USER_RESPONSE_MENU_IS_NULL = "Menu is null, cannot read user response";
    /**
     * Error message used to indicate that a null menu object was provided when attempting to
     * show the menu. This constant is primarily used for exception
     * messages or validation errors to improve clarity and maintain consistency in
     * error reporting.
     */
    public static final String ERR_CANNOT_SHOW_MENU_BECAUSE_MENU_IS_NULL = "Menu is null, cannot show menu";
    /**
     * Error message used to indicate that ANSI is not supported when attempting to show the menu.
     */
    public static String ERR_CANNOT_SHOW_MENU_BECAUSE_ANSI_IS_NOT_SUPPORTED = "Cannot show menu " +
        "because ANSI is not supported";
}
