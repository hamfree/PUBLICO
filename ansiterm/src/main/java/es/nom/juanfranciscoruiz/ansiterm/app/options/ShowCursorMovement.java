package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.clearScreenAndPrintHeader;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * Demonstrates cursor movement commands.
 *
 * @author Juan F. Ruiz
 */
public class ShowCursorMovement {
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
     * Stores the title to be displayed as part of the terminal output header.
     * <p>
     * This value represents a concise description or theme that is shown
     * during terminal operations, primarily used in demonstrations such as
     * enabling or disabling cursor blinking. It serves to provide context for
     * the displayed content within the terminal.
     */
    private final String title;
    /**
     * The message to be displayed during cursor blinking demonstrations.
     * <p>
     * This variable is used in methods that showcase cursor blinking functionality
     * and is displayed alongside other terminal outputs. The value of this field
     * represents a textual message to guide users or complement the demonstration.
     */
    private String msg;
    /**
     * Defines the fixed delay duration in milliseconds used for controlling the
     * timing of actions, such as cursor movement animations in the terminal.
     * <p>
     * This constant ensures consistent timing intervals across methods that
     * involve timed operations and provides a standard reference for delay
     * configuration within the application.
     */
    private static final long DELAY = 10L;
    /**
     * Constructs a new ShowCursorMovement.
     *
     * @throws ANSITermException if an error occurs during terminal initialization
     */
    public ShowCursorMovement() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Shows cursor movement";
        this.msg = "Move the cursor along the terminal borders";
    }
    /**
     * Performs the cursor movement demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {

        TerminalSize screenSize = term.getTerminalSize();
        clearScreenAndPrintHeader(term, title, msg, screenSize.getColumns());

        term.moveCursorToBegin();
        for (int i = 0; i < screenSize.getLines(); i++) {
            Thread.sleep(DELAY);
            term.moveCursorDown(1);
        }
        for (int i = 0; i < screenSize.getColumns(); i++) {
            Thread.sleep(DELAY);
            term.moveCursorRight(1);
        }
        for (int i = 0; i < screenSize.getLines(); i++) {
            Thread.sleep(DELAY);
            term.moveCursorUp(1);
        }
        for (int i = 0; i < screenSize.getColumns(); i++) {
            Thread.sleep(DELAY);
            term.moveCursorLeft(1);
        }
        pauseWithMessage(2000L, null);
    }
}