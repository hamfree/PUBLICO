package es.nom.juanfranciscoruiz.utiles.exceptions;

/**
 * This class contains error messages used throughout the application to provide
 * meaningful feedback to users and developers. Each error message is defined as
 * a static final String constant, ensuring that they are accessible and
 * immutable across the application.
 */
public class Errors {
  /**
   * Private constructor to prevent instantiation.
   */
  private Errors() {
  }
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
  /**
   * Message of error when it's trying to use a Menu Object with no menu options.
   */
  public static final String ERR_NO_OPTIONS = "There are no defined options in this menu";
  /** Message of error when the options list is null. */
  public static final String ERR_OPTIONS_CANNOT_BE_NULL = "Menu options cannot be null";
  /**
   * Message of error when the option already exists in the menu.
   */
  public static final String ERR_OPTION_ALREADY_EXISTS = "This option already exists in the menu";
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
  /*+
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
  public static final String ERR_MENU_POINTED_TO_SELF = "A menu cannot point to itself.";
  /**
   * Message of error when a root menu is pointed to another menu.
   */
  public static final String ERR_ROOTMENU_CANT_POINT_TO_ANOTHER_MENU = "El menú raíz no puede ser " +
      "hijo de otro menú.";
  /**
   * Message of error when a root menu with child menu is removed from the menu hierarchy.
   */
  public static final String ERR_ROOTMENU_CANT_BE_REMOVED = "A root menu can't be removed if it " +
      "has menu childs!";
  /**
   * Message of error when a submenu is removed from the menu hierarchy.
   */
  public static final String ERR_MENU_NULL = "Menu object can't be null!";
  /**
   * Error message used to indicate that a null title was provided when attempting to 
   * remove an option from the menu. This constant is primarily used for exception 
   * messages or validation errors to improve clarity and maintain consistency in 
   * error reporting.
   */
  public static final String ERR_OPTION_NULL = "The option (title) to remove can't be null or empty!";


  // Errors for MenuManager operations
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
