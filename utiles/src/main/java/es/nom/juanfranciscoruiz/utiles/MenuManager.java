package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.model.MenuConstants;
import es.nom.juanfranciscoruiz.utiles.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

import static es.nom.juanfranciscoruiz.utiles.Util.error;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.prt;

/**
 * The {@code MenuManager} class is responsible for managing and interacting
 * with menu objects in an application. It provides functionality to display
 * menus, handle user input, and manage menu lifecycles. This class ensures
 * that menu-related errors are properly handled and logged.
 */
public class MenuManager {
    private static final Logger logger = LoggerFactory.getLogger(MenuManager.class);
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
        this.menu = new Menu();
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
}
