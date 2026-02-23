package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;

/**
 * Demonstrates enabling and disabling cursor blinking.
 *
 * @author Juan F. Ruiz
 */
public class ShowCursorBlinking {
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
     * Represents the fixed delay duration in milliseconds used for cursor blinking
     * or message display during terminal screen demonstrations.
     * <p>
     * This value is utilized in methods like `demo1` and `demo2` within the
     * `ShowCursorBlinking` class to introduce a pause when printing messages or
     * simulating blinking effects. It ensures consistent timing for visual effects
     * throughout the terminal behavior demonstration processes.
     */
    private final long DELAY = 100L;
    /**
     * Constructs a {@code ShowCursorBlinking} object, initializing the terminal instance,
     * a title, and a message to be displayed during the cursor blinking demonstration.
     * <p>
     * This constructor prepares the object for showcasing different cursor blinking styles
     * using ANSI terminal capabilities. The object initializes the required fields, including:
     * - A terminal instance for interacting with the ANSI terminal.
     * - A title specifying the name of the demonstration.
     * - A message describing the purpose of the demonstration.
     *
     * @throws ANSITermException if there is an error initializing or accessing the terminal.
     */
    public ShowCursorBlinking() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Cursor blinking";
        this.msg = "It displays the various cursor blinking styles";
    }
    /**
     * Performs the cursor blinking demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        demo1();
        demo2();
    }
    /**
     * Demonstrates the process of enabling cursor blinking and displaying
     * a message on the terminal screen step by step.
     * <p>
     * The method clears the terminal screen, displays a formatted header with
     * a title and a message, enables cursor blinking, and prints the accompanying
     * message character by character with a delay. It then pauses the program to
     * allow users to see the output.
     *
     * @throws Exception If an error occurs during execution.
     */
    private void demo1() throws Exception {
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        msg = "(1/2) - The cursor starts blinking.";
        term.printAt(msg, 6, 1);
        term.cursorBlink();
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, null);

    }
    /**
     * Demonstrates the process of disabling cursor blinking and displaying
     * a message on the terminal screen step by step.
     * <p>
     * The method clears the terminal screen, displays a formatted header with
     * a title and a message, disables cursor blinking, and prints the accompanying
     * message character by character with a delay. It then pauses the program to
     * allow users to return to the menu by pressing ENTER.
     *
     * @throws Exception If an error occurs during execution.
     */
    private void demo2() throws Exception{
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        msg = "(2/2) - Turn off cursor blinking";
        term.printAt(msg, 6, 1);
        term.cursorNoBlink();
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }
}