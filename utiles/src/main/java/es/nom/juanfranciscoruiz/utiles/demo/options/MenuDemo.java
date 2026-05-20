package es.nom.juanfranciscoruiz.utiles.demo.options;

import es.nom.juanfranciscoruiz.utiles.Menu;
import es.nom.juanfranciscoruiz.utiles.MenuManager;
import es.nom.juanfranciscoruiz.utiles.TermCtl;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuErrors;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuManagerException;
import es.nom.juanfranciscoruiz.utiles.impl.TermCtlImpl;
import es.nom.juanfranciscoruiz.utiles.model.Using;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static es.nom.juanfranciscoruiz.utiles.Stuff.*;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.prtln;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.title;

/**
 * The MenuDemo class serves as an example of a hierarchical menu navigation system.
 * It demonstrates how to navigate through a menu structure using terminal-based operations.
 * <p>
 * The class integrates with the TermCtl interface to perform terminal control operations,
 * enabling consistent manipulation of the terminal across various environments.
 */
public class MenuDemo {
    /**
     * Logger instance used for logging messages within the context of the MenuDemo class.
     * This logger is utilized to record events, errors, and informational messages
     * during the execution of terminal-based menu operations.
     * <p>
     * The logger is static and final, ensuring a single, consistent logging mechanism
     * is available throughout the class lifecycle.
     */
    private static final Logger logger = LoggerFactory.getLogger(MenuDemo.class);

    /**
     * Represents an instance of the TermCtl interface used for terminal control operations
     * such as clearing the screen, retrieving console size, and setting console dimensions.
     * <p>
     * The `tc` variable is primarily utilized within classes to perform actions related to
     * terminal manipulation. It provides abstraction over the platform-specific terminal
     * control logic, enabling consistent functionality across different environments.
     */
    private TermCtl tc;

    /**
     * Default constructor for the MenuDemo class.
     * Initializes the MenuDemo instance by setting up a default implementation of
     * the TermCtl interface used for terminal control operations.
     */
    public MenuDemo() {
        info(logger, "Entering MenuDemo ...");
        this.tc = new TermCtlImpl();
    }

    /**
     * Retrieves the TermCtl instance associated with the MenuDemo class.
     * The TermCtl interface provides methods for terminal control operations,
     * such as clearing the screen, retrieving console size, and modifying console dimensions.
     *
     * @return the TermCtl instance used by the MenuDemo class for terminal control.
     */
    public TermCtl getTc() {
        return tc;
    }

    /**
     * Sets the TermCtl instance associated with this class.
     * The TermCtl interface provides methods for terminal control operations such as
     * clearing the screen, retrieving console dimensions, and setting the terminal size.
     *
     * @param tc the TermCtl instance to be associated with this class.
     */
    public void setTc(TermCtl tc) {
        this.tc = tc;
    }

    /**
     * Executes the demo for navigating a hierarchical menu structure in the terminal.
     * <p>
     * This method performs the following actions:
     * <ul>
     * <li>Clears the terminal screen using the `TermCtl` instance associated with the class.</li>
     * <li>Displays a formatted title message indicating the purpose of the demo.</li>
     * <li>Outputs a message stating that the demo is not yet implemented.</li>
     * <li>Pauses the program execution for a predefined duration.</li>
     * </ul>
     *
     * @throws Exception if an error occurs during terminal operations or execution pause.
     */
    public void run() throws Exception {
        final long PAUSE_DURATION = 3000L;
        String title = "Hierarchical Menu Demo";
        String subtitle = "This demo shows you how to manage the hierarchical menus of an application. " +
                "We'll use the \"edit\" program from the Windows 11 console as an example.";
        String message = "Show and navigate menu hierarchical structure...";

        this.getTc().clearScreen(Using.ANSI);
        prtln(2, title(message, '*', 80));

        // First we create the hierarchical menu
        // Generate five menus, one for the first level, and four menus for submenus.
        Menu mainMenu = Menu.getInstance();
        Menu fileMenu = Menu.getInstance();
        Menu editMenu = Menu.getInstance();
        Menu viewMenu = Menu.getInstance();
        Menu helpMenu = Menu.getInstance();
        Menu menuSelected;

        mainMenu.setTitle(title);
        mainMenu.setMessage(subtitle);
        mainMenu.setRootMenu(true);

        // The menu manager to manage the menu structure
        MenuManager menuManager = new MenuManager(mainMenu);

        // The four options list of the submenus
        List<String> optionsFile = new ArrayList<>();
        List<String> optionsEdit = new ArrayList<>();
        List<String> optionsView = new ArrayList<>();
        List<String> optionsHelp = new ArrayList<>();

        // We don't need to add the option "Salir" because is implemented by the menu
        optionsFile.add("Nuevo archivo");
        optionsFile.add("Abrir archivo...");
        optionsFile.add("Guardar");
        optionsFile.add("Guardar como...");
        optionsFile.add("Cerrar archivo");

        optionsEdit.add("Deshacer");
        optionsEdit.add("Rehacer");
        optionsEdit.add("Cortar");
        optionsEdit.add("Copiar");
        optionsEdit.add("Pegar");
        optionsEdit.add("Buscar");
        optionsEdit.add("Reemplazar");
        optionsEdit.add("Seleccionar todo");

        optionsView.add("Enfocar barra de estado");
        optionsView.add("Ir a archivo...");
        optionsView.add("Ir a línea:columna...");
        optionsView.add("Ajuste de línea");

        optionsHelp.add("Acerca de");

        // Set the properties of the submenus
        fileMenu.setTitle("Archivo");
        fileMenu.setIsRootMenu(false);
        fileMenu.setMessage("Seleccione una opción del menú Archivo");
        editMenu.setTitle("Editar");
        editMenu.setIsRootMenu(false);
        editMenu.setMessage("Seleccione una opción del menú Editar");
        viewMenu.setTitle("Ver");
        viewMenu.setIsRootMenu(false);
        viewMenu.setMessage("Seleccione una opción del menú Ver");
        helpMenu.setTitle("Ayuda");
        helpMenu.setIsRootMenu(false);
        helpMenu.setMessage("Seleccione una opción del menú Ayuda");

        // The four options of the first level of menu are submenu options...
        // We add this submenus to the menu 'theMenu' object.
        menuManager.addSubMenuToMenu(fileMenu);
        menuManager.addSubMenuToMenu(editMenu);
        menuManager.addSubMenuToMenu(viewMenu);
        menuManager.addSubMenuToMenu(helpMenu);

        // Set the options for the submenus using the proper method of the MenuManager class.
        menuManager.setOptionsInSubMenu(optionsFile, fileMenu);
        menuManager.setOptionsInSubMenu(optionsEdit, editMenu);
        menuManager.setOptionsInSubMenu(optionsView, viewMenu);
        menuManager.setOptionsInSubMenu(optionsHelp, helpMenu);

        // Shows and manage the main menu to the user...
        do {
            try {
                getTc().clearScreen(Using.ANSI);
                menuManager.getMenu().setSelectedOption(Menu.WRONG_OPTION);
                menuManager.showMenu(false);
                menuManager.awaitResponse("Select an option, please : ");
            } catch (MenuException e) {
                message = e.getMessage();
                error(logger, message);
                if (message.contains(MenuErrors.ERR_BLANK_NULL) ||
                        message.contains(MenuErrors.ERR_NO_NUMBER) ||
                        message.contains(MenuErrors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE)) {
                    mainMenu.setMessage(message);
                    menuManager.getMenu().setSelectedOption(Menu.WRONG_OPTION);
                }
            }
            int optionSelected = menuManager.getMenu().getSelectedOption().intValue();
            if (optionSelected < 5) {
                if (optionSelected != 0) {
                    menuSelected = menuManager.getMenu().getSubMenus().get(optionSelected - 1);
                    callProperMethodForMenuSelected(menuSelected);
                }
            } else {
                warn(logger, "Invalid option selected. Please try again.");
            }
        } while (menuManager.getMenu().getSelectedOption() != 0);

        pause(FOREVER, "This demonstration has ended. Press <ENTER> to return to the menu.");
        info(logger, "Leaving MenuDemo ...");
    }

    /**
     * Calls the appropriate method based on the selected menu's title.
     * This method determines which submenu to display depending on the title
     * of the passed {@code Menu} object and delegates the handling of the
     * corresponding menu options to a specific method.
     *
     * @param menuSelected the {@code Menu} object representing the selected menu.
     *                     This object contains the title and other details
     *                     necessary to identify and display the appropriate menu.
     * @throws Exception if an error occurs while processing the menu selection
     *                   or invoking the corresponding submenu method.
     */
    private void callProperMethodForMenuSelected(Menu menuSelected) throws Exception {
        String title = menuSelected.getTitle();
        switch (title) {
            case "Archivo" -> showMenuFile(menuSelected);
            case "Editar" -> showMenuEdit(menuSelected);
            case "Ver" -> showMenuView(menuSelected);
            case "Ayuda" -> showMenuHelp(menuSelected);
            default -> dbg(logger, "You have choose the option '" + title + "' of the menu.");
        }
    }

    /**
     * Displays the "File" menu and provides information about the currently selected menu option.
     * This method demonstrates the selected option from the menu and presents a message
     * regarding the action associated with that option.
     *
     * @param menu the Menu object representing the hierarchical file menu structure.
     *             It contains the title, options, and selected option to be displayed.
     * @throws Exception if an error occurs during the display or handling of the menu.
     */
    private void showMenuFile(Menu menu) throws Exception {
        getTc().clearScreen(Using.ANSI);
        MenuManager submenuManager = new MenuManager(menu);
        Menu thisMenu = submenuManager.getMenu();
        String message;
        String msgOptionSelected;

        do {
            try {
                getTc().clearScreen(Using.ANSI);
                submenuManager.getMenu().setSelectedOption(Menu.WRONG_OPTION);
                submenuManager.showMenu(false);
                submenuManager.awaitResponse("Select an option, please : ");
            } catch (MenuException e) {
                message = e.getMessage();
                error(logger, message);
                if (message.contains(MenuErrors.ERR_BLANK_NULL) ||
                        message.contains(MenuErrors.ERR_NO_NUMBER) ||
                        message.contains(MenuErrors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE)) {
                    thisMenu.setMessage(message);
                    thisMenu.setSelectedOption(Menu.WRONG_OPTION);
                }
            }
            // The submenu 'File' has five options
            int optionSelected = thisMenu.getSelectedOption().intValue();
            switch (optionSelected) {
                case 1:
                    msgOptionSelected = thisMenu.getOptions().get(1);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 2:
                    msgOptionSelected = thisMenu.getOptions().get(2);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 3:
                    msgOptionSelected = thisMenu.getOptions().get(3);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 4:
                    msgOptionSelected = thisMenu.getOptions().get(4);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 5:
                    msgOptionSelected = thisMenu.getOptions().get(5);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                default:
                    warn(logger, "Invalid option selected. Please try again.");
                    break;
            }
        } while (submenuManager.getMenu().getSelectedOption() != 0);
    }

    /**
     * Displays the "Edit" menu and handles user interactions with its options.
     * This method processes the menu navigation for the "Edit" options, including:
     * clearing the screen, displaying the menu, handling user input, processing
     * selected options, and displaying error messages for invalid inputs.
     * The menu interaction continues in a loop until the exit option is selected.
     *
     * @param menu the root Menu object representing the hierarchical "Edit" menu structure.
     *             This menu contains the title, options, and submenus to be displayed
     *             and navigated by the user.
     * @throws Exception if an error occurs during screen clearing, menu display,
     *                   input processing, or other related operations.
     */
    private void showMenuEdit(Menu menu) throws Exception {
        getTc().clearScreen(Using.ANSI);
        MenuManager submenuManager = new MenuManager(menu);
        Menu thisMenu = submenuManager.getMenu();
        String message;
        String msgOptionSelected;
        do {
            try {
                getTc().clearScreen(Using.ANSI);
                thisMenu.setSelectedOption(Menu.WRONG_OPTION);
                submenuManager.showMenu(false);
                submenuManager.awaitResponse("Select an option, please : ");
            } catch (MenuException e) {
                message = e.getMessage();
                error(logger, message);
                if (message.contains(MenuErrors.ERR_BLANK_NULL) ||
                        message.contains(MenuErrors.ERR_NO_NUMBER) ||
                        message.contains(MenuErrors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE)) {
                    thisMenu.setMessage(message);
                    thisMenu.setSelectedOption(Menu.WRONG_OPTION);
                }
            }
            // The submenu 'Edit' has eight options
            // TODO: automate the management of the variable number of options
            int optionSelected = submenuManager.getMenu().getSelectedOption().intValue();
            switch (optionSelected) {
                case 1:
                    msgOptionSelected = thisMenu.getOptions().get(1);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 2:
                    msgOptionSelected = thisMenu.getOptions().get(2);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 3:
                    msgOptionSelected = thisMenu.getOptions().get(3);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 4:
                    msgOptionSelected = thisMenu.getOptions().get(4);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 5:
                    msgOptionSelected = thisMenu.getOptions().get(5);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 6:
                    msgOptionSelected = thisMenu.getOptions().get(6);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 7:
                    msgOptionSelected = thisMenu.getOptions().get(7);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 8:
                    msgOptionSelected = thisMenu.getOptions().get(8);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                default:
                    warn(logger, "Invalid option selected. Please try again.");
                    break;
            }
        } while (submenuManager.getMenu().getSelectedOption() != 0);
    }

    /**
     * Displays the "View" menu and handles user interactions with its options.
     * This method processes the menu navigation for the "View" options, including:
     * clearing the screen, displaying the menu, handling user input, processing
     * selected options, and managing error conditions. The menu interaction loops
     * until the exit option is selected.
     *
     * @param menu the root Menu object representing the hierarchical "View" menu structure.
     *             This menu contains the title, options, and submenus to be displayed
     *             and navigated by the user.
     * @throws Exception if an error occurs during screen clearing, menu display,
     *                   input processing, or other related operations.
     */
    private void showMenuView(Menu menu) throws Exception {
        getTc().clearScreen(Using.ANSI);
        MenuManager submenuManager = new MenuManager(menu);
        Menu thisMenu = submenuManager.getMenu();
        String message;
        String msgOptionSelected;
        do {
            try {
                getTc().clearScreen(Using.ANSI);
                thisMenu.setSelectedOption(Menu.WRONG_OPTION);
                submenuManager.showMenu(false);
                submenuManager.awaitResponse("Select an option, please : ");
            } catch (MenuException e) {
                message = e.getMessage();
                error(logger, message);
                if (message.contains(MenuErrors.ERR_BLANK_NULL) ||
                        message.contains(MenuErrors.ERR_NO_NUMBER) ||
                        message.contains(MenuErrors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE)) {
                    thisMenu.setMessage(message);
                    thisMenu.setSelectedOption(Menu.WRONG_OPTION);
                }
            }
            // The submenu 'View' has four options
            int optionSelected = thisMenu.getSelectedOption().intValue();
            switch (optionSelected) {
                case 1:
                    msgOptionSelected = thisMenu.getOptions().get(1);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 2:
                    msgOptionSelected = thisMenu.getOptions().get(2);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 3:
                    msgOptionSelected = thisMenu.getOptions().get(3);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                case 4:
                    msgOptionSelected = thisMenu.getOptions().get(4);
                    showFinalOptionSelected(thisMenu, msgOptionSelected);
                    break;
                default:
                    warn(logger, "Invalid option selected. Please try again.");
                    break;
            }
        } while (submenuManager.getMenu().getSelectedOption() != 0);
    }

    /**
     * Displays the help menu and allows the user to navigate through menu options.
     * This method handles user interactions with a submenu, providing options and
     * processing responses until the exit option is selected. It also manages and
     * displays error messages for invalid user inputs.
     *
     * @param menu the root menu object from which the submenu is derived. This menu
     *             provides the structure and options to be displayed and navigated
     *             by the user.
     * @throws MenuManagerException if an error occurs during menu management operations.
     * @throws MenuException        if an invalid menu operation or user input is encountered.
     */
    private void showMenuHelp(Menu menu) throws Exception {
        getTc().clearScreen(Using.ANSI);
        MenuManager submenuManager = new MenuManager(menu);
        Menu thisMenu = submenuManager.getMenu();
        String message;
        String msgOptionSelected;
        do {
            try {
                getTc().clearScreen(Using.ANSI);
                submenuManager.getMenu().setSelectedOption(Menu.WRONG_OPTION);
                submenuManager.showMenu(false);
                submenuManager.awaitResponse("Select an option, please : ");
            } catch (MenuException e) {
                message = e.getMessage();
                error(logger, message);
                if (message.contains(MenuErrors.ERR_BLANK_NULL) ||
                        message.contains(MenuErrors.ERR_NO_NUMBER) ||
                        message.contains(MenuErrors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE)) {
                    thisMenu.setMessage(message);
                    thisMenu.setSelectedOption(Menu.WRONG_OPTION);
                }
            }
            // The submenu 'Help' has one option
            if (thisMenu.getSelectedOption().intValue() == 1) {
                msgOptionSelected = thisMenu.getOptions().get(0);
                showFinalOptionSelected(thisMenu, msgOptionSelected);
            } else {
                warn(logger, "Invalid option selected. Please try again.");
            }
        } while (submenuManager.getMenu().getSelectedOption() != 0);
    }

    /**
     * Displays the final option selected by the user in the hierarchical menu.
     * This method outputs the selected option along with a message indicating
     * the potential action or process triggered by the selection.
     *
     * @param menu the Menu object representing the hierarchical menu structure.
     *             It contains the title, available options, and the selected option
     *             to be displayed to the user.
     * @throws Exception if an error occurs during screen clearing, display, or pausing operations.
     */
    private void showFinalOptionSelected(Menu menu, String optionSelected) throws Exception {
        getTc().clearScreen(Using.ANSI);
        String title = "Hierarchical Menu Demo - " + menu.getTitle();
        String subtitle = "This is the option you are selected :'" +
                optionSelected + "'";
        String message = "In a real program this option selected by the user do an action with something or runs a process...";
        prtln(2, title(title, '*', 80));
        prtln(2, subtitle);
        prtln(3, message);
        pause(FOREVER, "Press <ENTER> to return to the menu '" + menu.getTitle() + "'.");
    }
}
