package es.nom.juanfranciscoruiz.utiles.model;

/**
 * Constants used in the Menu class.
 */
public class MenuConstants {
  /**
   
   * Private constructor to prevent instantiation.
   */
  private MenuConstants() { }
  
  /**
   * Default value for the title property of a Menu Object.
   */
  public static final String NO_TITLE = "Untitled";
  /**
   * Represents the predefined text for the "RETURN" option in a menu system.
   * <p>
   * This constant is typically used to display an option that allows the user
   * to navigate back to the previous menu or context. The format "0. [RETURN]"
   * includes an option number ("0") followed by the label "[RETURN]" to indicate
   * going back.
   */
  public static final String BACKOPT = "0. [RETURN]";
  /**
   * For a Menu object that is the Home Menu this is the option 0 of the menu
   * for exiting the application.
   */
  public static final String EXITOPT = "0. Exit the application";
  /**
   * Default vale for the message shown to the user below the list options.
   */
  public static final String DEFAULT_MSG = "Make your selection:";
  /**
   * Constant for the wrong option.
   */
  public static final Long WRONG_OPTION = -1L;
  /**
   * Constant for the option to go back to the parent menu.
   */
  public static final String BACKTOPARENTMENU = "0. [VOLVER]";
}
