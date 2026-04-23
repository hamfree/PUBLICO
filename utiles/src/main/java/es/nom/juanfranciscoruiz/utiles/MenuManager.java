package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.model.MenuConstants;
import es.nom.juanfranciscoruiz.utiles.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static es.nom.juanfranciscoruiz.utiles.Stuff.error;
import static es.nom.juanfranciscoruiz.utiles.Stuff.warn;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.prt;

/**
 * The {@code MenuManager} class is responsible for managing and interacting
 * with menu objects in an application. It provides functionality to display
 * menus, handle user input, and manage menu lifecycles. This class ensures
 * that menu-related errors are properly handled and logged.
 */
public class MenuManager {
    private static final Logger logger = LoggerFactory.getLogger(MenuManager.class);
    /**
     * A ResourceBundle instance used for retrieving internationalized messages
     * for the application. This bundle is utilized by the class to provide
     * localized texts and error messages based on the user's locale settings.
     * <p>
     * The {@code getMessage} method interacts with this field to fetch
     * specific messages using a given key. If the key is not found in the
     * bundle, a default message is returned.
     * <p>
     * This field facilitates multilingual support and improves user experience
     * by adapting the displayed content to the user's language preferences.
     */
    private static ResourceBundle messages;

    static {
        try {
            messages = ResourceBundle.getBundle("messages");
        } catch (MissingResourceException e) {
            logger.warn("ResourceBundle 'messages' not found. Falling back to default messages.");
            messages = null;
        }
    }

    /**
     * Helper method to fetch internationalized messages, falling back to default if not found.
     *
     * @param key            The ResourceBundle key
     * @param defaultMessage The default message if key or bundle is not available
     * @return The resolved message
     */
    private static String getMessage(String key, String defaultMessage) {
        if (messages != null && messages.containsKey(key)) {
            return messages.getString(key);
        }
        return defaultMessage;
    }

    /**
     * Represents the menu associated with the MenuManager class.
     * This variable holds the current menu instance to be managed, displayed, or modified.
     */
    private Menu menu;

    /**
     * Constructs a new instance of the MenuManager class.
     * <p>
     * This constructor initializes the MenuManager object, which is responsible
     * for managing menu operations, including setting, displaying, and interacting
     * with menus within the application. The class provides utility methods for
     * customizing and handling menu-related functionality.
     * @throws MenuException if the menu cannot be instantiated
     */
    public MenuManager() throws MenuException {
        this.menu = Menu.getInstance();
    }

    /**
     * Constructs a new instance of the MenuManager class with the specified menu.
     *
     * @param menu The menu to be managed by the MenuManager.
     *             Must not be null.
     * @throws MenuManagerException if the provided menu is null.
     */
    public MenuManager(Menu menu) throws MenuManagerException {
        if (menu == null) {
            String msg = getMessage("err.menu.null", MenuErrors.ERR_MENU_OBJECT_CANNOT_BE_NULL);
            error(logger, msg);
            throw new MenuManagerException(msg);
        }
        this.menu = menu;
    }

    /**
     * Retrieves the menu managed by the MenuManager.
     *
     * @return the menu managed by the MenuManager.
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Sets the menu to be managed by the MenuManager.
     * If the provided menu is null, an error will be logged, and a
     * {@code MenuManagerException} will be thrown.
     *
     * @param menu The {@code Menu} object to be managed. Must not be null.
     * @throws MenuManagerException If the provided menu is null.
     */
    public void setMenu(Menu menu) throws MenuManagerException {
        if (menu == null) {
            String msg = getMessage("err.menu.null", MenuErrors.ERR_MENU_OBJECT_CANNOT_BE_NULL);
            error(logger, msg);
            throw new MenuManagerException(msg);
        }
        this.menu = menu;
    }

    /**
     * Displays the menu to the user. The menu is presented in plain text or
     * can be formatted with ANSI styles using the AnsiTerm library  based on
     * the provided parameter.
     * <p>
     * If the menu object is not set or its options are empty, an error is
     * logged, and a {@code MenuManagerException} is thrown.
     *
     * @param useAnsi a boolean indicating whether the menu should use ANSI
     *                styling. If {@code true}, the menu will be displayed with
     *                ANSI formatting. If {@code false}, plain text will be used.
     * @throws MenuManagerException if the menu object is {@code null} or if
     *                              the menu options are empty.
     */
    public void showMenu(boolean useAnsi) throws MenuManagerException {
        if (menu == null) {
            String msg = getMessage("err.menu.manager.show.null", MenuManagerErrors.ERR_CANNOT_SHOW_MENU_BECAUSE_MENU_IS_NULL);
            error(logger, msg);
            throw new MenuManagerException(msg);
        } else if (menu.getOptions().isEmpty()) {
            String msg = getMessage("err.menu.no.options", MenuErrors.ERR_NO_OPTIONS);
            error(logger, msg);
            throw new MenuManagerException(msg);
        }
        menu.generateMenuView();
        if (!useAnsi) {
            prt(menu.getMenuView());
        } else {
            //TODO: Futuro uso libreria AnsiTerm. Ahora lanzará una excepción.
            String msg = getMessage("err.menu.manager.ansi", MenuManagerErrors.ERR_CANNOT_SHOW_MENU_BECAUSE_ANSI_IS_NOT_SUPPORTED);
            error(logger, msg);
            throw new MenuManagerException(msg);
        }
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
     * @return The option selected by the user.
     * @throws  MenuException In case an error is detected.
     * @throws  MenuManagerException In case an error is detected.
     */
    public Long awaitResponse(String msg) throws MenuException, MenuManagerException {
        if (menu == null) {
            String errMsg = getMessage("err.menu.manager.read.null", MenuManagerErrors.ERR_CANNOT_READ_USER_RESPONSE_MENU_IS_NULL);
            error(logger, errMsg);
            throw new MenuManagerException(errMsg);
        }

        /*
         * We verify that the options property is not null (because a malicious
         * client class can use reflexion api to change the value of the options property)
         */
        if (this.menu.getOptions() == null) {
            String errMsg = getMessage("err.menu.options.null", MenuErrors.ERR_OPTIONS_CANNOT_BE_NULL);
            error(logger, errMsg);
            throw new MenuException(errMsg);
        }
        /* We verify that the Menu object has at least one option declared and
            that the menuView property has the textual representation of the menu.
            If not, it will throw a MenuException.
        */
        if (this.menu.getOptions().isEmpty()) {
            String errMsg = getMessage("err.menu.no.options", MenuErrors.ERR_NO_OPTIONS);
            error(logger, errMsg);
            throw new MenuException(errMsg);
        }

        var resp = MenuConstants.WRONG_OPTION;
        String respuesta;
        int opcMaxima = this.menu.getOptions().size() - 1;

        if (msg == null || msg.isEmpty()) {
            msg = getMessage("msg.menu.default", MenuConstants.DEFAULT_MSG);
        }

        prt(msg);

        // Reads user input from console safely
        try (Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in))) {
            respuesta = sc.nextLine();
        } catch (Exception ex) {
            error(logger, ex.getMessage());
            throw new MenuException(ex.getMessage());
        }

        if (Types.isNullOrEmpty(respuesta)) {
            String errMsg = getMessage("err.menu.blank", MenuErrors.ERR_BLANK_NULL);
            this.menu.setMessage(errMsg);
            error(logger, errMsg);
            throw new MenuException(errMsg);
        }

        if (!Types.isInteger(respuesta)) {
            String errMsg = getMessage("err.menu.no.number", MenuErrors.ERR_NO_NUMBER);
            this.menu.setMessage(errMsg);
            error(logger, errMsg);
            throw new MenuException(errMsg);
        }

        // Extracts and validates option; throws on failure
        try {
            resp = TypeConverter.extractLongFromString(respuesta);
            // Validates option is within allowed range; throws on failure
            if (Objects.equals(resp, MenuConstants.WRONG_OPTION)) {
                String errMsg = getMessage("err.menu.not.valid.number", MenuErrors.ERR_NOT_VALID_NUMBER);
                error(logger, errMsg);
                this.menu.setMessage(errMsg);
                throw new MenuException(errMsg);
            } else if (resp < 0 || resp > opcMaxima) {
                String errMsg = getMessage("err.menu.out.of.range", MenuErrors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE);
                error(logger, errMsg);
                this.menu.setMessage(errMsg);
                throw new MenuException(errMsg);
            }
        } catch (TypeConverterException ex) {
            error(logger, ex.getMessage());
            throw new MenuException(ex.getMessage());
        }
        this.menu.setSelectedOption(resp);

        return resp;
    }

    /**
     * Adds an option to the menu if it meets specific conditions.
     * <ul>
     * <li>The option text cannot be null or empty.</li>
     * <li>The option text cannot match reserved options like "Exit" or "Back".</li>
     * <li>The option text must not already exist in the menu options.</li>
     * </ul>
     * If the option is valid, it is appended to the menu's options list with automatic numbering.
     *
     * @param optionText The text of the menu option to be added. Must not be null,
     *                   empty, or match reserved keywords.
     * @throws MenuException If the option text is null, empty, reserved, or already exists
     *                       in the menu options.
     */
    public void addOptionToMenu(String optionText) throws MenuException {
        if (optionText == null || optionText.isEmpty()) {
            String msg = getMessage("err.menu.option.null.or.empty", MenuErrors.ERR_OPTION_CANNOT_BE_NULL_OR_EMPTY);
            error(logger, msg);
            throw new MenuException(msg);
        }
        if (this.menu.getOptions() == null) {
            String msg = getMessage("err.menu.options.null", MenuErrors.ERR_OPTIONS_CANNOT_BE_NULL);
            throw new MenuException(msg);
        }

        if (optionText.equals(getMessage("msg.menu.exit.opt", MenuConstants.EXITOPT))) {
            String msg = getMessage("err.menu.option.managed", MenuErrors.ERR_MANAGED_OPTION);
            throw new MenuException(msg);
        }

        if (optionText.equals(getMessage("msg.menu.back.opt", MenuConstants.BACKTOPARENTMENU))) {
            String msg = getMessage("err.menu.option.managed", MenuErrors.ERR_MANAGED_OPTION);
            throw new MenuException(msg);
        }

        //Due to the automatic numbering when inserting options into the options list, we must perform the comparison
        // against a temporary list generated from the options list, but WITHOUT the numbering.
        List<String> cleanOptions = this.menu.getOptions().stream()
                .map(opt -> opt.replaceFirst("^\\d+\\.\\s", ""))
                .toList();

        if (cleanOptions.contains(optionText)) {
            String msg = getMessage("err.menu.option.exists", MenuErrors.ERR_OPTION_ALREADY_EXISTS);
            error(logger, msg);
            throw new MenuException(msg);
        } else {
            optionText = addNumbertoOptionMenu(optionText, this.menu.getOptions().size());
        }
        this.menu.getOptions().add(optionText);
    }

    /**
     * Removes an option from the menu based on the specified option text.
     * If the option text is null or empty, an error is logged, and a {@code MenuException} is thrown.
     *
     * @param optionText The text of the menu option to be removed. Must not be null or empty.
     * @throws MenuException If the provided option text is null or empty.
     */
    public void removeOptionFromMenu(String optionText) throws MenuException {
        if (optionText == null || optionText.isEmpty()) {
            String msg = getMessage("err.menu.option.to.remove.null.or.empty", MenuErrors.ERR_OPTION_TO_REMOVE_CAN_T_BE_NULL_OR_EMPTY);
            error(logger, msg);
            throw new MenuException(msg);
        }
        this.menu.getOptions().remove(optionText);
    }

    /**
     * Configures the options presented in a menu by validating and setting the provided list of options.
     * Automatically adds a visual index to each valid option and ensures that the correct navigation option
     * ("exit" for root menus or "back" for non-root menus) is included as the first option.
     *
     * @param options a list of strings representing the menu options to set. Each option is validated, indexed,
     *                and added to the menu. Should not be null or contain reserved options managed by the menu system.
     * @throws MenuException if the provided list of options is null.
     */
    public void setOptionsInMenu(List<String> options) throws MenuException {
        String exitOpt = getMessage("msg.menu.exit.opt", MenuConstants.EXITOPT);
        String backOpt = getMessage("msg.menu.back.opt", MenuConstants.BACKTOPARENTMENU);
        int i = 0;
        int index = 1;
        List<String> menuOptions = this.menu.getOptions();

        if (options == null) {
            String msg = getMessage("err.menu.options.null", MenuErrors.ERR_OPTIONS_CANNOT_BE_NULL);
            throw new MenuException(msg);
        }

        // Validates the options and add the visual index to each option.
        for (String option : options) {
            if (!option.equals(exitOpt) || option.equals(backOpt)){
                options.set(i, addNumbertoOptionMenu(option, index));
                i++;
                index++;
            } else {
                warn(logger, getMessage("err.menu.option.managed", MenuErrors.ERR_MANAGED_OPTION));
            }
        }

        // Set the list of options.
        if (menuOptions != null) {
            menuOptions.addAll(options);
        } else {
            menuOptions = options;
        }

        // The first option of the menu is added automatically and depends from 'rootMenu' property.
        if (this.menu.isRootMenu() && !menuOptions.contains(exitOpt)) {
            menuOptions.removeFirst();
            menuOptions.addFirst(exitOpt);
        } else if (!this.menu.isRootMenu() && !menuOptions.contains(backOpt)) {
            menuOptions.removeFirst();
            menuOptions.addFirst(backOpt);
        }
    }

    /**
     * Adds a sub-menu to an existing menu and establishes the parent-child relationship.
     * This method ensures that the `childMenu` is valid, avoids cycles, and maintains
     * referential integrity between menus. Also updates the parent menu's list of sub-menus.
     *
     * @param childMenu The {@code Menu} object to be added as a sub-menu.
     *                  Must not be null, must not point to itself, and must not be a root menu.
     * @throws MenuException If the provided {@code childMenu} is null, points to itself,
     *                       or is a root menu pointing to another menu.
     */
    public void addSubMenuToMenu(Menu childMenu) throws MenuException {
        if (childMenu == null) {
            String msg = getMessage("err.menu.null", MenuErrors.ERR_MENU_OBJECT_CANNOT_BE_NULL);
            error(logger, msg);
            throw new MenuException(msg);
        }

        // Rule: Avoid cycles and ensure a single parent
        if (childMenu == this.menu) {
            String msg = getMessage("err.menu.point.itself", MenuErrors.ERR_MENU_CANNOT_POINT_TO_ITSELF);
            error(logger, msg);
            throw new MenuException(msg);
        }
        if (childMenu.getIsRootMenu()) {
            String msg = getMessage("err.menu.root.point.another", MenuErrors.ERR_ROOTMENU_CANT_POINT_TO_ANOTHER_MENU);
            error(logger, msg);
            throw new MenuException(msg);
        }

        // Ensures initialized and mutable list
        if (this.menu.getSubMenus() == null) {
            this.menu.setSubMenus(new ArrayList<>());
        } else {
            try {
                this.menu.getSubMenus().add(null);
                this.menu.getSubMenus().removeLast();
            } catch (UnsupportedOperationException ex) {
                this.menu.setSubMenus(new ArrayList<>(this.menu.getSubMenus()));
            }
        }

        childMenu.setParentMenu(this.menu);
        this.menu.getSubMenus().add(childMenu);
        this.addOptionToMenu(childMenu.getTitle());
    }

    /**
     * Removes a sub-menu from the current menu if it exists. This operation also
     * ensures referential integrity by removing the parent menu reference from the
     * sub-menu being removed.
     *
     * @param childMenu The {@code Menu} object to be removed from the sub-menus of the current menu.
     *                  Must not be {@code null} and must exist in the sub-menus of the current menu.
     * @throws MenuException If the provided {@code childMenu} is {@code null}, does not exist
     *                       in the current menu, or violates specific conditions such as being
     *                       a root menu with multiple sub-menus.
     */
    public void removeSubMenuFromMenu(Menu childMenu) throws MenuException {
        if (childMenu == null) {
            String msg = getMessage("err.menu.null", MenuErrors.ERR_MENU_OBJECT_CANNOT_BE_NULL);
            error(logger, msg);
            throw new MenuException(msg);
        }
        if (this.menu.getSubMenus() == null) {
            String msg = getMessage("err.menu.submenus.null", MenuErrors.ERR_SUBMENUS_CANNOT_BE_NULL);
            error(logger, msg);
            throw new MenuException(msg);
        }
        if (this.menu.getSubMenus().isEmpty()) {
            String msg = getMessage("err.menu.submenu.not.found", MenuErrors.ERR_SUBMENU_NOT_FOUND);
            error(logger, msg);
            throw new MenuException(msg);
        }

        if (!this.menu.getSubMenus().contains(childMenu)) {
            warn(logger, getMessage("err.menu.submenu.not.found", MenuErrors.ERR_SUBMENU_NOT_FOUND));
            return;
        }

        if (childMenu.getIsRootMenu() && this.menu.getSubMenus().size() > 1) {
            String msg = getMessage("err.menu.root.with.submenus.removed", MenuErrors.ERR_ROOTMENU_WITH_SUBMENUS_CANT_BE_REMOVED);
            error(logger, msg);
            throw new MenuException(msg);
        }
        childMenu.setParentMenu(null); //Needs to remove the reference to this in the child menu, too.
        this.removeOptionFromMenu(childMenu.getTitle());
        this.menu.getSubMenus().remove(childMenu);
    }

    //Helper methods
    // Helper methods
    /**
     * Adds a number as a prefix to the specified option in the menu.
     * If the option already contains a numeric prefix, it returns the option unchanged.
     *
     * @param theOption The menu option to which a numeric prefix needs to be added.
     *                  Must not be null or empty.
     * @param index     The numerical index to be added as a prefix to the given option.
     * @return A string representing the menu option with the numerical prefix added,
     * or the original option if it already contains a numeric prefix.
     * @throws MenuException If the provided option is null or empty.
     */
    protected String addNumbertoOptionMenu(String theOption, int index) throws MenuException {
        if (theOption == null || theOption.isEmpty()) {
            String msg = getMessage("err.menu.option.null.or.empty", MenuErrors.ERR_OPTION_CANNOT_BE_NULL_OR_EMPTY);
            error(logger, msg);
            throw new MenuException(msg);
        }
        //It's validates the argument 'theoption' has no number added yet.
        if (theOption.substring(1, 2).matches("\\d")) {
            return theOption;
        } else {
            return index + ". " + theOption;
        }
    }

    /**
     * Checks if the provided option corresponds to the title of a submenu
     * within the current menu's list of submenus.
     *
     * @param option the title of the option to check
     * @return true if the option matches the title of a submenu, false otherwise
     */
    private boolean isOptionALinkToMenu(String option) {
        for (Menu menu : this.menu.getSubMenus()) {
            if (menu.getTitle().equals(option)) return true;
        }
        return false;
    }

    /**
     * Formats the given option text by converting the content within parentheses
     * to uppercase while retaining the rest of the text unchanged.
     *
     * @param optionText the option text to be formatted, which must include
     *                   a substring enclosed in parentheses
     * @return the formatted text where the parentheses' content is uppercase
     */
    private static String formatOptionTextAsSubmenuOptionText(String optionText) throws MenuException {
        if (optionText == null || optionText.isEmpty()) {
            String msg = getMessage("err.menu.option.null.or.empty", MenuErrors.ERR_OPTION_CANNOT_BE_NULL_OR_EMPTY);
            throw new MenuException(msg);
        }
        if (!optionText.contains("(")) {
            optionText = "(" + optionText;
        }
        if (!optionText.contains(")")) {
            optionText = optionText + ")";
        }
        return optionText.toUpperCase();
    }

}