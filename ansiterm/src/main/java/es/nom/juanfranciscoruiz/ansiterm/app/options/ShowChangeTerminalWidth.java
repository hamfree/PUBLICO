package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import static es.nom.juanfranciscoruiz.ansiterm.codes.WindowWidthCodes.setWidthTo132;
import static es.nom.juanfranciscoruiz.ansiterm.codes.WindowWidthCodes.setWidthTo80;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;

/**
 * The ChangeTerminalWidth class provides functionality to adjust
 * the terminal's width dynamically with specified delays and user prompts.
 * It is intended to demonstrate changes in terminal dimensions
 * and allow the user to interact with the process.
 * <p>
 * Note: These ANSI escape sequences do not work on either Windows or Linux.
 * Further research is needed.
 *
 * @author Juan F. Ruiz
 */
public class ShowChangeTerminalWidth {
    /**
     * Represents an instance of the {@code ANSITerm} class used for managing
     * terminal interactions, such as cursor blinking or printing text at
     * specific locations on the terminal screen.
     * <p>
     * This object is final and assigned during class construction, ensuring
     * consistent and reliable access throughout the lifecycle of the containing
     * class. It provides methods and utilities for terminal display management.
     */
    private final ANSITerm term;
    /**
     * Represents the title text for the MultipleStylesText object.
     * This field is used to store a string that acts as the title,
     * often styled and displayed prominently in terminal-based
     * demonstrations or other text-based UI elements of the class.
     */
    private final String title;
    /**
     * Represents the primary text message managed by the MultipleStylesText class.
     * This message is used as a content reference for various operations, including
     * styling demonstrations and terminal-based displays.
     */
    private String message;
    /**
     * A constant representing the delay time, in milliseconds, used within the application
     * for various operations that require timed pauses.
     */
    public static final long DELAY = 10L;
    /**
     * Constructs a new ChangeTerminalWidth object.
     */
    public ShowChangeTerminalWidth() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Changing terminal width";
        this.message = "The console width will be changed to 80 characters in " + DELAY + " ms.";
    }
    /**
     * Adjusts the terminal width in a stepwise manner with delay intervals and prompts
     * the user before returning to the main menu.
     *
     * @throws Exception if any error occurs during terminal operations or thread sleep
     */
    public void perform() throws Exception {
        int columns = term.getTerminalSize().getColumns();
        clearScreenAndPrintHeader(term, title, message, columns);
        System.out.print(setWidthTo80());
        message = "The console width will be changed to 132 characters in  " + DELAY + " ms.";
        term.printAt(message, 6, 1);
        pauseForMilliseconds(DELAY);
        System.out.print(setWidthTo132());
        Thread.sleep(DELAY);
        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }
}
