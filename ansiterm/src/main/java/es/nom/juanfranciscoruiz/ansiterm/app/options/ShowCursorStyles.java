package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.codes.CursorStylesCodes;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;

/**
 * Demonstrates various cursor styles and visibility.
 *
 * @author Juan F. Ruiz
 */
public class ShowCursorStyles {
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
     * Constructs a new ShowCursorStyles.
     */
    public ShowCursorStyles() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Cursor styles";
        this.msg = "It displays the nine different cursor styles";
    }
    /**
     * Performs the cursor styles demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        demo1();
        demo2();
        demo3();
        demo4();
        demo5();
        demo6();
        demo7();
        demo8();
        demo9();
    }
    /**
     * Demonstrates the use of the steady bar cursor style by interacting with
     * the terminal to display a styled message, change the cursor style,
     * and manage delays for a sequential visual effect.
     * <p>
     * The method operates as follows:
     * - Sets a formatted message indicating the demo step and cursor style.
     * - Clears the terminal screen and displays the message using a styled header.
     * - Changes the cursor style to a steady bar using ANSI terminal capabilities.
     * - Displays the message character by character with a configured delay.
     * - Pauses the execution for user interaction or further processing.
     *
     * @throws Exception If an error occurs during terminal operations or message display.
     */
    private void demo1() throws Exception {
        msg = "(1/9) - Stable bar cursor shape";
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_STEADY_BAR_SHAPE);
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, null);

    }
    /**
     * Demonstrates the use of the blinking bar cursor shape by interacting with
     * the terminal to display a styled message, change the cursor style,
     * and manage delays for a sequential visual effect.
     * <p>
     * The method operates as follows:
     * - Sets a formatted message indicating the demo step and cursor style.
     * - Clears the terminal screen and displays the message using a styled header.
     * - Changes the cursor style to a blinking bar using ANSI terminal capabilities.
     * - Displays the message character by character with a configured delay.
     * - Pauses the execution, allowing user interaction or further processing.
     *
     * @throws Exception If an error occurs during terminal operations or message display.
     */
    private void demo2() throws Exception {
        msg = "(2/9) - Blinking bar cursor shape";
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_BLINKING_BAR_SHAPE);
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, null);
    }
    /**
     * Demonstrates the use of the steady block cursor shape by interacting with
     * the terminal to display a styled message, change the cursor style,
     * and manage delays for a sequential visual effect.
     * <p>
     * The method operates as follows:
     * - Sets a formatted message indicating the demo step and cursor style.
     * - Clears the terminal screen and displays the message using a styled header.
     * - Changes the cursor style to a steady block using ANSI terminal capabilities.
     * - Displays the message character by character with a configured delay.
     * - Pauses the execution for user interaction or further processing.
     *
     * @throws Exception If an error occurs during terminal operations or message display.
     */
    private void demo3() throws Exception {
        msg = "(3/9) - Stable block cursor shape";
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_STEADY_BLOCK_SHAPE);
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, null);
    }
    /**
     * Demonstrates the use of the blinking block cursor shape by interacting with
     * the terminal to display a styled message, change the cursor style,
     * and manage delays for a sequential visual effect.
     * <p>
     * The method performs the following steps:
     * - Sets a formatted message indicating the demo step and cursor style.
     * - Clears the terminal screen and displays the message using a styled header.
     * - Changes the cursor style to a blinking block using ANSI terminal capabilities.
     * - Displays the message character by character with a configured delay.
     * - Pauses the execution, allowing user interaction or further processing.
     *
     * @throws Exception If an error occurs during terminal operations or message display.
     */
    private void demo4() throws Exception {
        msg = "(4/9) - Blinking block cursor shape";
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_BLOCK_SHAPE);
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, null);
    }
    /**
     * Demonstrates the use of the steady underline cursor shape by interacting with
     * the terminal to display a styled message, change the cursor style,
     * and manage delays for a sequential visual effect.
     * <p>
     * The method operates as follows:
     * - Sets a formatted message indicating the demo step and cursor style.
     * - Clears the terminal screen and displays the message using a styled header.
     * - Changes the cursor style to a steady underline using ANSI terminal capabilities.
     * - Displays the message character by character with a configured delay.
     * - Pauses the execution for user interaction or further processing.
     *
     * @throws Exception If an error occurs during terminal operations or message display.
     */
    private void demo5() throws Exception {
        msg = "(5/9) - Stable underline cursor shape";
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_STEADY_UNDERLINE_SHAPE);
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, null);
    }
    /**
     * Demonstrates the use of the blinking underline cursor shape by interacting with
     * the terminal to display a styled message, change the cursor style,
     * and manage delays for a sequential visual effect.
     * <p>
     * The method performs the following steps:
     * - Sets a formatted message indicating the demo step and cursor style.
     * - Clears the terminal screen and displays the message using a styled header.
     * - Changes the cursor style to a blinking underline using ANSI terminal capabilities.
     * - Displays the message character by character with a configured delay.
     * - Pauses the execution, allowing user interaction or further processing.
     *
     * @throws Exception If an error occurs during terminal operations or message display.
     */
    private void demo6() throws Exception {
        msg = "(6/9) - Blinking underline cursor shape";
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_UNDERLINE_SHAPE);
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, null);
    }
    /**
     * Demonstrates the use of a user-defined cursor shape by interacting with
     * the terminal to display a styled message, change the cursor style,
     * and manage delays for a sequential visual effect.
     * <p>
     * The method operates as follows:
     * - Sets a formatted message indicating the demo step and cursor style.
     * - Clears the terminal screen and displays the message using a styled header.
     * - Changes the cursor style to a user-defined shape using ANSI terminal capabilities.
     * - Displays the message character by character with a configured delay.
     * - Pauses the execution for user interaction or further processing.
     *
     * @throws Exception If an error occurs during terminal operations or message display.
     */
    private void demo7() throws Exception {
        msg = "(7/9) - User-defined cursor shape";
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_USER_SHAPE);
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, null);
    }
    /**
     * Demonstrates hiding the terminal cursor by interacting with the terminal
     * to display a styled message, modify the cursor visibility, and manage
     * delays for a gradual visual effect.
     * <p>
     * The method performs the following steps:
     * - Sets a formatted message indicating the demo step and action being performed.
     * - Clears the terminal screen and displays the message using a styled header.
     * - Hides the cursor using ANSI terminal capabilities.
     * - Displays the message character by character with a configured delay.
     * - Pauses the execution for user interaction or further processing.
     *
     * @throws Exception If an error occurs during terminal operations or message display.
     */
    private void demo8() throws Exception {
        msg = "(8/9) - Hide the cursor";
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        term.cursorHide();
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, null);
    }
    /**
     * Demonstrates showing the terminal cursor by interacting with the terminal
     * to display a styled message, modify the cursor visibility, and introduce
     * delays for a sequential visual effect.
     * <p>
     * The method performs the following steps:
     * - Sets a formatted message indicating the demo step and the action being performed.
     * - Clears the terminal screen and displays the message with a styled header.
     * - Reveals the terminal cursor using ANSI terminal capabilities.
     * - Displays the message character by character with a configured delay.
     * - Pauses the execution, awaiting user input to proceed.
     *
     * @throws Exception If an error occurs during terminal operations, message display,
     *                   or user input handling.
     */
    private void demo9() throws Exception {
        msg = "(9/9) - Show cursor";
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        term.cursorShow();
        printWithDelay(msg, 7, 1, term, DELAY);
        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }
}
