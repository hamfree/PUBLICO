package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.constants.MenuConstants;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import es.nom.juanfranciscoruiz.utiles.exceptions.TypeConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static es.nom.juanfranciscoruiz.utiles.exceptions.Errors.*;

/**
 * It's the representation of an options menu, capable of generating a view of
 * this menu for output devices or streams and for getting the user's
 * response.
 * <p>
 * When you want to display the menu, assuming the Menu instance is called
 * principalMenu, it would look something like this:
 * <p>
 * IO io = new IO(); io.prt(principalMenu.getMenuView());
 *
 * @author Juan F. Ruiz
 */
public class Menu {
  // static variables and constants
  /**
   * For debugging.
   */
  private final static Logger logger = LoggerFactory.getLogger(Menu.class);
  
  /**
   * Default value for the title property of a Menu Object.
   */
  private static final String NO_TITLE = "Untitled";
  /**
   * For a Menu object that is the Home Menu this is the option 0 of the menu
   * for exiting the application.
   */
  private static final String EXITOPT = "0. Exit the application";
  /**
   * Default vale for the message shown to the user below the list options.
   */
  private static final String DEFAULT_MSG = "Make your selection:";
  /**
   * Constant for the wrong option.
   */
  private static final Long WRONG_OPTION = -1L;
 
  //Properties
  /**
   * List of options for the menu.
   */
  private List<String> options;
  /**
   * Title of the menu.
   */
  private String title;
  /**
   * Message to be displayed below the list of options.
   */
  private String message;
  /**
   * The option selected by the user.
   */
  private Long selectedOption;
  /**
   * Indicates whether this menu is the application's main menu.
   */
  private boolean isRootMenu;
  /**
   * The view of the menu, which can be used to customize the appearance.
   */
  private String menuView;
  /**
   * The submenus of this menu, or null if this is a leaf menu.
   */
  private List<Menu> subMenus;
  /**
   * The parent menu of this menu, or null if this is a root menu.
   */
  private Menu parentMenu;
  
  // Constructors
  /**
   * Instantiate a Menu object.
   * By default, the menu is not the root menu and has no title or message, but it has a default
   * value for the title property (because title is mandatory), a blank string for the message
   * property (message can't be null), and an empty list for the options property.
   */
  public Menu() throws MenuException {
    this.setOptions(new ArrayList<>());
    this.setSubMenus(new ArrayList<>());
    this.setRootMenu(false);
    this.setTitle(MenuConstants.NO_TITLE);
    this.setMessage("");
    this.setMenuView("");
    this.setSelectedOption(0L);
  }
  
  /**
   * Instantiate a Menu object with the specified parameters
   *
   * @param options    A list of options
   * @param title      A string with the menu title
   * @param message    A string containing the message that will be displayed
   *                   below the list of options
   * @param isRootMenu Boolean indicating whether it is the application's main
   *                   menu, which will add the option "0. Exit the application" to the options
   *                   property.
   */
  public Menu(List<String> options, String title, String message,
              boolean isRootMenu) throws MenuException {
    if (options != null && !options.isEmpty()) this.setOptions(options);
    else {
      this.setOptions(new ArrayList<>());
    }
    if (title != null && !title.isEmpty()) {
      this.setTitle(title);
    } else {
      this.setTitle(MenuConstants.NO_TITLE);
    }
    this.setMessage(message);
    
    this.setRootMenu(isRootMenu);
    if (this.isRootMenu()) {
      this.getOptions().addFirst(MenuConstants.EXITOPT);
    }
  }
  
  /**
   * Instantiate a Menu object with the specified parameters and submenus.
   *
   * @param options    A list of options
   * @param title      A string with the menu title
   * @param message    A string containing the message that will be displayed below the
   *                   list of options
   * @param isRootMenu Boolean indicating whether it is the application's main menu,
   *                   which will add the option "0. Exit the application" to the
   *                   options property.
   * @param subMenus   A list of submenus (can't be null because throws MenuException)
   * @param parentMenu The parent menu of this menu, or null if this is the
   *                   main menu (if the client class tries to set a parent menu to a root menu,
   *                   it will throw a MenuException) because a root menu can't have a parent menu.
   * @throws MenuException In case of error
   */
  public Menu(List<String> options, String title, String message, boolean isRootMenu,
              List<Menu> subMenus, Menu parentMenu) throws MenuException {
    this(options, title, message, isRootMenu);
    if (subMenus == null) {
      throw new MenuException(ERR_SUBMENUS_CANNOT_BE_NULL);
    }
    this.setSubMenus(subMenus);
    if (isRootMenu) {
      if (parentMenu != null) {
        throw new MenuException(ERR_ROOTMENU_CANT_HAVE_A_PARENT_MENU);
      }
    } else {
      if (parentMenu != null) {
        this.setParentMenu(parentMenu);
      } else {
        throw new MenuException(ERR_SUBMENU_MUST_HAVE_A_PARENT_MENU);
      }
    }
  }
  
  //Getters and Setters
  
  /**
   * Gets the list of menu options
   *
   * @return A list of strings with the options.
   */
  public List<String> getOptions() {
    return options;
  }
  
  /**
   * Sets the menu options list
   *
   * @param options A list of strings with the new menu options.
   */
  public void setOptions(List<String> options) throws MenuException {
    if (options == null) {
      throw new MenuException(ERR_OPTIONS_CANNOT_BE_NULL);
    }
    
    this.options = options;
    if (this.isRootMenu()) {
      this.options.addFirst(MenuConstants.EXITOPT);
    }
  }
  
  /**
   * Gets the menu title
   *
   * @return a string with the menu title
   */
  public String getTitle() {
    return title;
  }
  
  /**
   * Set the menu title.
   * The title can't be null for a valid menu object.
   *
   * @param title A chain with the new menu title
   * @throws MenuException If the title is null
   */
  public void setTitle(String title) throws MenuException {
    if (title == null) {
      throw new MenuException(ERR_TITLE_CANNOT_BE_NULL);
    }
    this.title = title;
  }
  
  /**
   * Gets the message from the menu
   *
   * @return a string with the current menu message
   */
  public String getMessage() {
    return message;
  }
  
  /**
   * Set a new message in the menu.
   * A valid message can't be null.
   *
   * @param message A string with the new menu message
   * @throws MenuException If the message is null
   */
  public void setMessage(String message) throws MenuException {
    if (message == null) {
      throw new MenuException(ERR_MESSAGE_CANNOT_BE_NULL);
    }
    this.message = message;
  }
  
  /**
   * Gets the option currently selected by the user (after calling the
   * awaitResponse() method).
   *
   * @return a long with the option selected by the user
   */
  public Long getSelectedOption() {
    return selectedOption;
  }
  
  /**
   * Set the selected option
   *
   * @param selectedOption A long file with the selected option
   */
  public void setSelectedOption(Long selectedOption) {
    this.selectedOption = selectedOption;
  }
  
  /**
   * Returns a boolean indicating whether this menu is the application's main
   * menu.
   *
   * @return a boolean, if true it indicates that the current menu is the main
   * menu (root menu) of the application and false otherwise.
   */
  public boolean getIsRootMenu() {
    return isRootMenu();
  }
  
  /**
   * Sets the boolean value indicating whether the current menu is the main
   * menu. If 'true' is passed, the option "0. Exit the application" will be
   * added as the first option in the list of options.
   *
   * @param isRootMenu boolean, if true it indicates that the current menu is
   *                   the main menu of the application and false otherwise.
   */
  public void setIsRootMenu(boolean isRootMenu) throws MenuException {
    if (this.getParentMenu() != null) {
      throw new MenuException(ERR_SUBMENU_SET_AS_ROOT);
    }
    this.setRootMenu(isRootMenu);
    if (this.getOptions() == null) {
      this.setOptions(new ArrayList<>());
    }
    if (this.isRootMenu()) {
      this.getOptions().addFirst(MenuConstants.EXITOPT);
    } else {
      this.getOptions().remove(MenuConstants.EXITOPT);
    }
  }
  
  /**
   * Returns the string containing the contents of the menuView property
   *
   * @return a string containing the menu content in text form
   */
  public String getMenuView() {
    return menuView;
  }
  
  /**
   * Sets the value of menuView as the value for the menuView property
   *
   * @param menuView a string with the content to be set (although it can be
   *                 any content, logically it should be the textual representation of a
   *                 menu).
   */
  public void setMenuView(String menuView) {
    this.menuView = menuView;
  }
  
  // Methods
  
  /**
   * Generates a text representation in String format of the menu in its
   * current state in the menuView attribute. This property can then be
   * displayed to standard output or in a stream by another client class.
   */
  public void generateMenuView() {
    final String LS = System.lineSeparator();
    StringBuilder sb = new StringBuilder();
    StringBuilder sbMenuView = new StringBuilder();
    
    sb.append(LS);
    int longitud = this.getTitle().length();
    String repeat = "*".repeat(Math.max(0, longitud + 5));
    sb.append(repeat);
    
    sb.append(LS);
    sb.append("  ").append(this.getTitle()).append("  ");
    sb.append(LS);
    sb.append(repeat);
    
    String tit = sb.toString();
    sbMenuView
        .append(tit)
        .append(LS)
        .append(LS);
    
    for (String opcion : this.getOptions()) {
      sbMenuView.append(opcion).append(LS);
    }
    sbMenuView.append(LS);
    sbMenuView.append(this.getMessage());
    sbMenuView.append(LS);
    this.setMenuView(sbMenuView.toString());
  }
  
  /**
   * It awaits the user's response, setting their response in the
   * <code>optionSelected</code> property as a long, or the value -1 if an error
   * occurs. This method handles errors, throwing a MenuException in the
   * following cases:
   * <ul>
   * <li>Entered value is not a number.</li>
   * <li>Entered value is a number, but outside the range of the options list.</li>
   * <li>Enter is pressed directly.</li>
   * <li>The options property are empty.</li>
   * <li>The menuview property is empty.</li>
   * </ul>
   * <p>
   * It also sets the 'message' field with the error message, so that the
   * display() method can then display it on the screen.
   *
   * @param msg An optional string with the text to be printed to the left of
   *            the user prompt. If nothing is specified, the phrase "Make your
   *            selection: " will be printed.
   * @throws es.nom.juanfranciscoruiz.utiles.exceptions.MenuException In case
   *                                                                  an error is detected.
   */
  public void awaitResponse(String msg) throws MenuException {
    /*
     * We verify that the options property is not null (because a malicious
     * client class can use reflexion api to change the value of the options property)
     */
    if (this.getOptions() == null) {
      if (logger.isErrorEnabled()) logger.error(ERR_OPTIONS_CANNOT_BE_NULL);
      throw new MenuException(ERR_OPTIONS_CANNOT_BE_NULL);
    }
    /* We verify that the Menu object has at least one option declared and
        that the menuView property has the textual representation of the menu.
        If not, it will throw a MenuException.
    */
    if (this.getOptions().isEmpty()) {
      if (logger.isErrorEnabled()) logger.error(ERR_NO_OPTIONS);
      throw new MenuException(ERR_NO_OPTIONS);
    }
    
    var resp = MenuConstants.WRONG_OPTION;
    String respuesta;
    int opcMaxima = this.getOptions().size() - 1;
    
    if (msg == null || msg.isEmpty()) {
      msg = MenuConstants.DEFAULT_MSG;
    }
    
    IO.prt(msg);
    
    try (Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in))) {
      respuesta = sc.nextLine();
    } catch (Exception ex) {
      if (logger.isErrorEnabled()) logger.error(ex.getMessage());
      throw new MenuException(ex.getMessage());
    }
    
    if (Types.isNullOrEmpty(respuesta)) {
      this.setMessage(ERR_BLANK_NULL);
      if (logger.isErrorEnabled()) logger.error(ERR_BLANK_NULL);
      throw new MenuException(ERR_BLANK_NULL);
    }
    
    if (!Types.isInteger(respuesta)) {
      this.setMessage(ERR_NO_NUMBER);
      if (logger.isErrorEnabled()) logger.error(ERR_NO_NUMBER);
      throw new MenuException(ERR_NO_NUMBER);
    }
    
    try {
      resp = TypeConverter.extractLongFromString(respuesta);
      if (Objects.equals(resp, MenuConstants.WRONG_OPTION)) {
        if (logger.isErrorEnabled()) logger.error(ERR_NOT_VALID_NUMBER);
        this.setMessage(ERR_NOT_VALID_NUMBER);
        throw new MenuException(ERR_NOT_VALID_NUMBER);
      } else if (resp < 0 || resp > opcMaxima) {
        if (logger.isErrorEnabled()) logger.error(ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE);
        this.setMessage(ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE);
        throw new MenuException(ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE);
      }
    } catch (TypeConverterException ex) {
      if (logger.isErrorEnabled()) logger.error(ex.getMessage());
      throw new MenuException(ex.getMessage());
    }
    this.setSelectedOption(resp);
  }
  
  /**
   * Returns a string representation of the Menu object, including its options,
   * title, message, selected option, and whether it is the root menu.
   *
   * @return a string containing the internal state of the Menu object.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Menu{");
    sb.append("options=").append(Util.CollectionToString(this.getOptions(), true, 10));
    sb.append(", title=").append(this.getTitle());
    sb.append(", message=").append(this.getMessage());
    sb.append(", selectedOption=").append(this.getSelectedOption());
    sb.append(", isRootMenu=").append(this.getIsRootMenu());
    sb.append(", parentMenu=").append(this.getParentMenu() != null ?
        this.getParentMenu().getTitle() : "null");
    sb.append(", menuView='").append(this.getMenuView()).append('\'');
    sb.append(", subMenus=").append(Util.CollectionToString(this.getSubMenus(), true, 10));
    sb.append('}');
    return sb.toString();
  }
  
  /**
   * In the case that an application has several menus, if isRootMenu is true,
   * then this is the main menu of the application and the option "0. Exit the
   * application" will be added as the first option.
   */
  public boolean isRootMenu() {
    return isRootMenu;
  }
  
  public void setRootMenu(boolean rootMenu) {
    isRootMenu = rootMenu;
  }
  
  /**
   * The submenus of this menu, or null if this is a leaf menu.
   */
  public List<Menu> getSubMenus() {
    return subMenus;
  }
  
  public void setSubMenus(List<Menu> subMenus) {
    this.subMenus = subMenus;
  }
  
  /**
   * The parent menu of this menu, or null if this is the main menu.
   */
  public Menu getParentMenu() {
    return parentMenu;
  }
  
  public void setParentMenu(Menu parentMenu) {
    this.parentMenu = parentMenu;
  }
  
  // Methods
  
  /**
   * Adds a submenu to this menu.
   * Note: when adding a submenu, their title will be added to the options list.
   * 
   * @param childMenu The submenu to add.
   * @throws MenuException If the child menu is this menu or if the child menu is a root menu.
   */
  public void addSubMenu(Menu childMenu) throws MenuException {
    // Regla: Evitar ciclos y asegurar padre único
    if (childMenu == this) {
      throw new MenuException(ERR_MENU_POINTED_TO_SELF);
    }
    if (childMenu.getIsRootMenu()) {
      throw new MenuException(ERR_ROOTMENU_CANT_POINT_TO_ANOTHER_MENU);
    }
    
    childMenu.setParentMenu(this);
    this.subMenus.add(childMenu);
    this.addOption(childMenu.getTitle());
  }
  
  /**
   * Removes a submenu from this menu.
   * Note: when removing a submenu, their title will be removed from the options list.
   * 
   * @param childMenu The submenu to remove.
   * @throws MenuException If the child menu is not a submenu of this menu.
   */
  public void removeSubMenu(Menu childMenu) throws MenuException {
    if (childMenu == null) {
      throw new MenuException(ERR_MENU_NULL);
    }
    if (childMenu.getIsRootMenu() && this.subMenus.size() > 1){
      throw new MenuException(ERR_ROOTMENU_CANT_BE_REMOVED);
    }
    this.removeOption(childMenu.getTitle());
    this.subMenus.remove(childMenu);
  }
  
  /**
   * Adds an option to the options list.
   * @param optionText The option to add.
   * @throws MenuException If the option is null or already exists.
   */
  public void addOption(String optionText) throws MenuException {
    if (optionText == null || optionText.isEmpty()) {
      throw new MenuException(ERR_OPTION_NULL);
    }
    if (this.getOptions().contains(optionText)) {
      throw new MenuException(ERR_OPTION_ALREADY_EXISTS);
    } else {
      this.getOptions().add(optionText);
    }
  }
  
  /**
   * Removes an option from the options list.
   * @param optionText The option to remove.
   */
  public void removeOption(String optionText) throws MenuException{
    if (optionText == null || optionText.isEmpty()) {
      throw new MenuException(ERR_OPTION_NULL);
    }
    this.getOptions().remove(optionText);
  }
}