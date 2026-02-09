package es.nom.juanfranciscoruiz.utiles.exceptions;

/**
 * The MenuErrors class encapsulates a collection of static constant error messages
 * used for validation and error reporting in a Menu system. These messages are primarily
 * intended for use in exception handling and input validation to ensure consistent
 * and informative error messages.
 * <p>
 * This class acts as a centralized repository for error messages related to menu
 * operations such as adding, removing, or interacting with menu items or structures.
 * <p>
 * It serves as a utility class and does not provide behavior or functionality beyond
 * defining error messages as string constants.
 */
public class MenuErrors {


    /**
     * Private constructor to prevent instantiation.
     */
    private MenuErrors() {
    }

    // Errors for the invalid inputs of user in awaitResponse()
    /**
     * Message of error when the user enters an invalid option in awaitResponse().
     */
    public static final String ERR_BLANK_NULL = "You must enter a number with the option to choose.";
    /**
     * Message of error when the user enters something that is not a number.
     */
    public static final String ERR_NO_NUMBER = "What was entered is not a number.";
    /**
     * Message of error when the user enters a negative or decimal number
     */
    public static final String ERR_NOT_VALID_NUMBER = "You have not entered a valid number.";
    /**
     * Message of error when the user enters a valid number but it's outside the
     * allowed range.
     */
    public static final String ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE = "The selected option is outside the allowed range";

    // Errors for the invalid data into a Menu Object.
    /**
     * Error message indicating that a menu option cannot be null or empty.
     */
    public static final String ERR_OPTION_CANNOT_BE_NULL_OR_EMPTY = "A menu option cannot be null or empty";
    /**
     * Message of error when the option already exists in the menu.
     */
    public static final String ERR_OPTION_ALREADY_EXISTS = "This option already exists in the menu";
    /**
     * Message of error when it's trying to use a Menu Object with no menu options.
     */
    public static final String ERR_NO_OPTIONS = "There are no defined options in this menu";
    /**
     * Message of error when the options list is null.
     */
    public static final String ERR_OPTIONS_CANNOT_BE_NULL = "Menu options cannot be null";
    /**
     * Message of error when the title is null.
     */
    public static final String ERR_TITLE_CANNOT_BE_NULL = "Menu title cannot be null";
    /**
     * Message of error when the message is null.
     */
    public static final String ERR_MESSAGE_CANNOT_BE_NULL = "Menu message cannot be null";
    /**
     * Message of error when the root menu has a parent menu.
     */
    public static final String ERR_ROOTMENU_CANT_HAVE_A_PARENT_MENU = "Root menu cannot have a parent menu";

    /**
     * Message of error when a submenu is not found.
     */
    public static final String ERR_SUBMENU_NOT_FOUND = "Submenu not found" ;
    /**
     * Message of error when a submenu has no parent menu.
     */
    public static final String ERR_SUBMENU_MUST_HAVE_A_PARENT_MENU = "Submenu must have a parent menu";
    /**
     * Message of error when a submenu is set as root menu.
     */
    public static final String ERR_SUBMENU_SET_AS_ROOT = "A submenu must be removed from the menu " +
            "hierarchy before making it the root menu.";
    /**
     * Message of error when a submenu list is null.
     */
    public static final String ERR_SUBMENUS_CANNOT_BE_NULL = "Submenus list cannot be null";
    /**
     * Message of error when a menu is pointed to itself.
     */
    public static final String ERR_MENU_CANNOT_POINT_TO_ITSELF = "A menu cannot point to itself.";
    /**
     * Message of error when a menu has itself as parent.
     */
    public static final String ERR_MENU_CANNOT_HAVE_ITSELF_AS_PARENT = "Menu cannot have itself as parent";
    /**
     * Message of error when a root menu is pointed to another menu.
     */
    public static final String ERR_ROOTMENU_CANT_POINT_TO_ANOTHER_MENU = "The root menu cannot be " +
            "a child of another menu.";
    /**
     * Message of error when a root menu with child menu is removed from the menu hierarchy.
     */
    public static final String ERR_ROOTMENU_WITH_SUBMENUS_CANT_BE_REMOVED = "A root menu can't be removed if it " +
            "has menu childs!";
    /**
     * Message of error when a submenu is removed from the menu hierarchy.
     */
    public static final String ERR_MENU_OBJECT_CANNOT_BE_NULL = "Menu object can't be null!";
    /**
     * Error message used to indicate that a null title was provided when attempting to
     * remove an option from the menu. This constant is primarily used for exception
     * messages or validation errors to improve clarity and maintain consistency in
     * error reporting.
     */
    public static final String ERR_OPTION_TO_REMOVE_CAN_T_BE_NULL_OR_EMPTY = "The option (title) to remove can't be null or empty!";
}
