package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.constants.MenuConstants;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuManagerException;
import es.nom.juanfranciscoruiz.utiles.exceptions.TypeConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Scanner;

import static es.nom.juanfranciscoruiz.utiles.exceptions.Errors.*;
import static es.nom.juanfranciscoruiz.utiles.exceptions.Errors.ERR_BLANK_NULL;
import static es.nom.juanfranciscoruiz.utiles.exceptions.Errors.ERR_NOT_VALID_NUMBER;
import static es.nom.juanfranciscoruiz.utiles.exceptions.Errors.ERR_NO_NUMBER;
import static es.nom.juanfranciscoruiz.utiles.exceptions.Errors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE;

/**
 * Represents the manager for menu operations.
 * This class provides methods to manage menu-related functionality, including setting, displaying, and interacting with menus.
 */
public class MenuManager {
    private static Logger logger = LoggerFactory.getLogger(Menu.class);

    /**
     * Represents the menu associated with the MenuManager class.
     * This variable holds the current menu instance to be managed, displayed, or modified.
     */
    private Menu menu;

    /**
     * Constructs a new instance of the MenuManager class.
     *
     * This constructor initializes the MenuManager object, which is responsible
     * for managing menu operations, including setting, displaying, and interacting
     * with menus within the application. The class provides utility methods for
     * customizing and handling menu-related functionality.
     */
    public MenuManager() throws MenuException {
        this.menu = new Menu();
    }

    /**
     * Constructs a new instance of the MenuManager class with the specified menu.
     * @param menu The menu to be managed by the MenuManager.
     */
    public MenuManager(Menu menu) throws MenuManagerException {
        if (menu == null) {
            logger.error(ERR_MENU_NULL);
            throw new MenuManagerException(ERR_MENU_NULL);
        }
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    /**
     * Displays the menu to the user. The menu is presented in plain text or
     * can be formatted with ANSI styles using the AnsiTerm library  based on
     * the provided parameter.
     *
     * If the menu object is not set or its options are empty, an error is
     * logged, and a {@code MenuManagerException} is thrown.
     *
     * @param useAnsi a boolean indicating whether the menu should use ANSI
     *                styling. If {@code true}, the menu will be displayed with
     *                ANSI formatting. If {@code false}, plain text will be used.
     * @throws MenuManagerException if the menu object is {@code null} or if
     *                               the menu options are empty.
     */
    public void showMenu(boolean useAnsi) throws MenuManagerException {
        if (menu == null) {
            logger.error(ERR_CANNOT_SHOW_MENU_BECAUSE_MENU_IS_NULL);
            throw new MenuManagerException(ERR_CANNOT_SHOW_MENU_BECAUSE_MENU_IS_NULL);
        } else if (menu.getOptions().isEmpty()) {
            logger.error(ERR_NO_OPTIONS);
            throw new MenuManagerException(ERR_NO_OPTIONS);
        }
        String menuView = menu.getMenuView();
        if (!useAnsi){
            IO.prt(menuView);
        } else {
            //TODO: Se usará la libreria AnsiTerm y se podrá configurar y personalizar más la presentación del menú.
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
     * @throws es.nom.juanfranciscoruiz.utiles.exceptions.MenuException In case
     *                                                                  an error is detected.
     */
    public Long awaitResponse(String msg) throws MenuException, MenuManagerException {
        if (menu == null) {
            logger.error(ERR_CANNOT_READ_USER_RESPONSE_MENU_IS_NULL);
            throw new MenuManagerException(ERR_CANNOT_READ_USER_RESPONSE_MENU_IS_NULL);
        }

        /*
         * We verify that the options property is not null (because a malicious
         * client class can use reflexion api to change the value of the options property)
         */
        if (this.menu.getOptions() == null) {
            if (logger.isErrorEnabled()) logger.error(ERR_OPTIONS_CANNOT_BE_NULL);
            throw new MenuException(ERR_OPTIONS_CANNOT_BE_NULL);
        }
    /* We verify that the Menu object has at least one option declared and
        that the menuView property has the textual representation of the menu.
        If not, it will throw a MenuException.
    */
        if (this.menu.getOptions().isEmpty()) {
            if (logger.isErrorEnabled()) logger.error(ERR_NO_OPTIONS);
            throw new MenuException(ERR_NO_OPTIONS);
        }

        var resp = MenuConstants.WRONG_OPTION;
        String respuesta;
        int opcMaxima = this.menu.getOptions().size() - 1;

        if (msg == null || msg.isEmpty()) {
            msg = MenuConstants.DEFAULT_MSG;
        }

        IO.prt(msg);

        // Reads user input from console safely
        try (Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in))) {
            respuesta = sc.nextLine();
        } catch (Exception ex) {
            if (logger.isErrorEnabled()) logger.error(ex.getMessage());
            throw new MenuException(ex.getMessage());
        }

        if (Types.isNullOrEmpty(respuesta)) {
            this.menu.setMessage(ERR_BLANK_NULL);
            if (logger.isErrorEnabled()) logger.error(ERR_BLANK_NULL);
            throw new MenuException(ERR_BLANK_NULL);
        }

        if (!Types.isInteger(respuesta)) {
            this.menu.setMessage(ERR_NO_NUMBER);
            if (logger.isErrorEnabled()) logger.error(ERR_NO_NUMBER);
            throw new MenuException(ERR_NO_NUMBER);
        }

        // Extracts and validates option; throws on failure
        try {
            resp = TypeConverter.extractLongFromString(respuesta);
            // Validates option is within allowed range; throws on failure
            if (Objects.equals(resp, MenuConstants.WRONG_OPTION)) {
                if (logger.isErrorEnabled()) logger.error(ERR_NOT_VALID_NUMBER);
                this.menu.setMessage(ERR_NOT_VALID_NUMBER);
                throw new MenuException(ERR_NOT_VALID_NUMBER);
            } else if (resp < 0 || resp > opcMaxima) {
                if (logger.isErrorEnabled()) logger.error(ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE);
                this.menu.setMessage(ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE);
                throw new MenuException(ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE);
            }
        } catch (TypeConverterException ex) {
            if (logger.isErrorEnabled()) logger.error(ex.getMessage());
            throw new MenuException(ex.getMessage());
        }
        this.menu.setSelectedOption(resp);

        return resp;
    }
}
