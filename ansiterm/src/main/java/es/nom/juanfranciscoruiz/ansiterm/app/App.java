package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.app.options.*;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import es.nom.juanfranciscoruiz.utiles.Menu;
import es.nom.juanfranciscoruiz.utiles.MenuManager;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuErrors;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static es.nom.juanfranciscoruiz.utiles.Menu.WRONG_OPTION;
import static es.nom.juanfranciscoruiz.utiles.Util.*;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.*;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.prtln;

/**
 * Main application class to demonstrate the functionality of the ANSITerm library.
 * It provides a menu-driven interface to test various ANSI escape sequence features
 * like cursor movement, text styles, colors, and more.
 *
 * @author Juan F. Ruiz
 */
public class App {

    /**
     * Instantiates a new App object.
     */
    public App() {
    }

    /**
     * Logger used for tracing and debugging.
     */
    public static final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * Application entry point.
     *
     * @param args Command line arguments received from the operating system.
     * @throws Exception In case of any error
     */
    public static void main(String[] args) throws Exception {
        info(logger,"Application launch");

        ANSITerm term;
        List<String> opciones;
        App app = new App();
        MenuManager mm;
        Menu menu;

        try {
            term = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }

        try {
            mm = new MenuManager();
            opciones = getOptions();
            menu = mm.getMenu();
            menu.setRootMenu(true);
            menu.setTitle("Testing the ANSITerm library");
            menu.setOptions(opciones);
            menu.generateMenuView();
            menu.setSelectedOption(WRONG_OPTION);
        } catch (MenuException e) {
            throw new RuntimeException(e);
        }


        do {
            term.clearTerminal();
            term.moveCursorToBegin();
            if (menu.getMessage().isEmpty()) {
                menu.setMessage("Running on ".concat(System.getProperty("os.name")));
            }
            menu.generateMenuView();
            prt(menu.getMenuView());

            try {
                mm.awaitResponse("Please, choose an option: ");
            } catch (MenuException e) {
                String message = e.getMessage();
                if (message.contains(MenuErrors.ERR_BLANK_NULL) ||
                        message.contains(MenuErrors.ERR_NO_NUMBER) ||
                        message.contains(MenuErrors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE)) {
                    mm.getMenu().setMessage(message);
                }
                mm.getMenu().setSelectedOption(Menu.WRONG_OPTION);
            }

            switch (menu.getSelectedOption().intValue()) {
                case 1 -> {
                    menu.setMessage("");
                    app.enableRawModeKeyboard();
                }
                case 2 -> {
                    menu.setMessage("");
                    app.recoverCursorPosition(1L);
                }
                case 3 -> {
                    menu.setMessage("");
                    app.testAndShowScreenSize();
                }
                case 4 -> {
                    menu.setMessage("");
                    app.drawBorderedRectangle();
                }
                case 5 -> {
                    menu.setMessage("");
                    app.showScreenLineDeletionCommands();
                }
                case 6 -> {
                    menu.setMessage("");
                    app.displayTextStyles();
                }
                case 7 -> {
                    menu.setMessage("");
                    app.showMultipleStyles();
                }
                case 8 -> {
                    menu.setMessage("");
                    app.displayTextColors();
                }
                case 9 -> {
                    menu.setMessage("");
                    app.display256ColorPalette();
                }
                case 10 -> {
                    menu.setMessage("");
                    app.displayCursorStyles();
                }
                case 11 -> {
                    menu.setMessage("");
                    app.showCursorBlinkingEffect();
                }
                case 12 -> {
                    menu.setMessage("");
                    app.showCursorAnimation(100L);
                }
                case 13 -> {
                    menu.setMessage("");
                    app.setTerminalWidth();
                }
                default -> menu.setMessage("Invalid option. Please, try again.");
            }
        } while (menu.getSelectedOption() != 0L);
        info(logger,"Application exit");
    }


    /**
     * Returns a list of menu options.
     *
     * @return A list of menu options.
     */
    private static List<String> getOptions() {
        //TODO: Incluir las demos que faltan (creando las clases de demostración, etc.)
        List<String> opciones = new ArrayList<>();

        opciones.add("Raw console mode test");
        opciones.add("Cursor position recovery test");
        opciones.add("Screen size test");
        opciones.add("Rectangle drawing test");
        opciones.add("Text deletion capabilities");
        opciones.add("Text styles");
        opciones.add("Multiple text styles");
        opciones.add("Text colors");
        opciones.add("Text palette of 256 colors");
        opciones.add("Cursor styles");
        opciones.add("Cursor blinking");
        opciones.add("Shows cursor movement");
        opciones.add("Setting Terminal Width");
        return opciones;
    }

    // Useful methods for displaying functionalities.

    /**
     * Perform a keystroke capture test once the terminal's 'raw' mode is
     * enabled. Display the keys pressed in a loop until the "q" key is pressed.
     *
     * @throws Exception In case of any error
     */
    private void enableRawModeKeyboard() throws Exception {
        ShowRawMode rawMode = new ShowRawMode();
        rawMode.perform();
    }

    /**
     * Displays the cursor movement in the terminal with the delay in
     * milliseconds between each movement. The 'path' will be a rectangle,
     * taking into account the current dimensions of the terminal.
     *
     * @param retardo The delay in milliseconds
     * @throws Exception In case of any error
     */
    private void showCursorAnimation(long retardo) throws Exception {
        ShowCursorMovement showCursorMovement = new ShowCursorMovement();
        showCursorMovement.perform();
    }

    /**
     * It prints an 'X' character in each position of the terminal, and for each movement
     * of the cursor it recovers its position by printing it on the last line of the
     * terminal.
     *
     * @param retardo The delay in milliseconds
     * @throws Exception In case of any error
     */
    private void recoverCursorPosition(long retardo) throws Exception {
        ShowRecoverCursorPosition recoverCursorPosition = new ShowRecoverCursorPosition();
        recoverCursorPosition.perform();
    }

    /**
     * Draw a rectangle with asterisks that borders the screen
     *
     * @throws Exception In case of any error
     */
    private void drawBorderedRectangle() throws Exception {
        ShowDrawsRectangle dr = new ShowDrawsRectangle();
        dr.perform();
    }

    /**
     * Tests and displays the screen size (lines and columns).
     *
     * @throws Exception In case of any error
     */
    private void testAndShowScreenSize() throws Exception {
        ShowScreenSize screenSize = new ShowScreenSize();
        screenSize.perform();
    }

    /**
     * Displays various text styles (bold, dim, italic, etc.) applicable to text.
     *
     * @throws Exception In case of any error
     */
    private void displayTextStyles() throws Exception {
        ShowTextStyles textStyles = new ShowTextStyles();
        textStyles.perform();
    }

    /**
     * Displays combinations of multiple text styles.
     *
     * @throws Exception In case of any error
     */
    private void showMultipleStyles() throws Exception {
        ShowMultipleStylesText multipleStylesText = new ShowMultipleStylesText();
        multipleStylesText.perform();
    }

    /**
     * Displays various foreground and background text colors.
     *
     * @throws Exception In case of any error
     */
    private void displayTextColors() throws Exception {
        ShowTextColors showTextColors = new ShowTextColors();
        showTextColors.perform();
    }

    /**
     * Displays the 256-color palette.
     *
     * @throws Exception In case of any error
     */
    private void display256ColorPalette() throws Exception {
        ShowTextColors256 showTextColors256 = new ShowTextColors256();
        showTextColors256.perform();
    }

    /**
     * Displays various cursor styles (block, bar, underline, blinking, etc.).
     *
     * @throws Exception In case of any error
     */
    private void displayCursorStyles() throws Exception {
        ShowCursorStyles showCursorStyles = new ShowCursorStyles();
        showCursorStyles.perform();
    }

    /**
     * Demonstrates screen and line deletion commands from the current cursor position.
     *
     * @throws Exception In case of any error
     */
    private void showScreenLineDeletionCommands() throws Exception {
        ShowScreenAndLineDeletions screenAndLineDeletions = new ShowScreenAndLineDeletions();
        screenAndLineDeletions.perform();
    }

    /**
     * Demonstrates enabling and disabling cursor blinking.
     *
     * @throws Exception In case of any error
     */
    private void showCursorBlinkingEffect() throws Exception {
        ShowCursorBlinking showCursorBlinking = new ShowCursorBlinking();
        showCursorBlinking.perform();
    }

    /**
     * Configures the terminal width based on the given ANSITerm instance.
     * Note: Doesn't work on Windows neither Linux
     */
    private void setTerminalWidth() throws Exception {
        ShowChangeTerminalWidth changeTerminalWidth = new ShowChangeTerminalWidth();
        changeTerminalWidth.perform();
    }


    /**
     * Placeholder method indicating functionality that has not been implemented yet.
     * The terminal is cleared, and an informational message is displayed.
     *
     * @param term An instance of ANSITerm used for terminal operations.
     * @param msg A message to display, indicating the unimplemented status.
     * @throws Exception In case of any error during terminal operations.
     */
    private void notImplementedYet(ANSITerm term, String msg) throws Exception {
        term.clearTerminal();
        term.moveCursorToBegin();
        final long PAUSE_DURATION = 3000L;
        prtln(2, title(msg, '*', 80));
        prtln(3, "This function is not implemented yet.");
        pause(PAUSE_DURATION, null);
    }
}
