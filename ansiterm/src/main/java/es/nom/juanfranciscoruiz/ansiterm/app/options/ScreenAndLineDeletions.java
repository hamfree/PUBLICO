package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.codes.CursorStylesCodes;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;

/**
 * Demonstrates screen and line deletion and movement commands.
 *
 * @author Juan F. Ruiz
 */
public class ScreenAndLineDeletions {
    /**
     * A constant representing a delay of four seconds, used for timing or scheduling operations
     * within the ScreenAndLineDeletions class.
     */
    public final static Long FOUR_SECONDS_DELAY = 4000L;
    /**
     * Represents a constant delay interval of three seconds, defined in milliseconds.
     * This can be used to pause or delay operations for a short period.
     */
    public static final Long THREE_SECONDS_DELAY = 3000L;
    /**
     * Represents a short delay duration typically used for brief waiting periods
     * or time intervals in execution. This constant holds the value of 100 milliseconds.
     */
    public final static Long SHORT_DELAY = 100L;
    /**
     * Represents a delay duration of two seconds, often used for short pauses or timeouts.
     */
    public final static Long TWO_SECONDS_DELAY = 2000L;
    /**
     * Represents a message prompting the user to press Enter to return to the menu.
     */
    public static final String PRESS_ENTER_TO_RETURN_TO_MENU = "Press <ENTER> to return to menu";

    private final ANSITerm term;
    /**
     * Represents a message displayed to inform the user that the process will continue automatically every 3 seconds.
     */
    private final String msg = "The process will continue automatically every " +
            THREE_SECONDS_DELAY + " seconds. Do not press any keys!";
    /**
     * Represents a title for the screen and line deletions demonstration.
     */
    private final String title = "Text deletion capabilities";
    /**
     * Represents the number of lines in the terminal.
     * <p>
     * This variable is used to manage or reference the vertical
     * dimension of the terminal during operations such as screen
     * and line deletions.
     */
    private final int lines;
    /**
     * Represents the number of columns in the terminal or screen.
     * <p>
     * This variable is primarily used in various demonstrations to dynamically
     * adapt the output or layout based on the terminal's current width. It may
     * influence how text, messages, or other output is presented on the screen,
     * ensuring proper alignment and readability.
     */
    private final int columns;
    /**
     * Constructs a new ScreenAndLineDeletions.
     */
    public ScreenAndLineDeletions() throws ANSITermException {
        this.term = new ANSITerm();
        this.lines = term.getTerminalSize().getLines();
        this.columns = term.getTerminalSize().getColumns();
    }
    /**
     * Performs the screen and line deletions demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        String stylizedMessage = "This demonstration shows various deletion functions. Please do not press any keys.";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        pauseWithMessage(0,"Press <ENTER> to continue");
        demo_1();
        demo_2();
        demo_3();
        demo_4();
        demo_5();
        demo_6();
        demo_7();
        demo_8();
        demo_9();
        demo_10();
        demo_11();
        demo_12();
    }
    /**
     * Demonstrates terminal cursor and screen manipulation by performing a series
     * of actions, including screen clearing, cursor styling, printing messages at
     * specific positions, and partially erasing the screen.
     *<p>
     * This method specifically:
     * <ul>
     * <li>Displays a stylized message as a header.</li>
     * <li>Makes the cursor visible and changes its style to a block shape.</li>
     * <li>Shows a warning message and prints a block of random text.</li>
     * <li>Positions the cursor at a specific location.</li>
     * <li>Waits for a short delay before erasing content from the cursor's position
     *   to the beginning of the screen.</li>
     * <li>Introduces additional pauses to allow the user to view the intermediate
     *   steps in the demonstration.</li>
     *</ul>
     * @throws Exception If an error occurs during the terminal interaction or
     *                   delays.
     */
    private void demo_1() throws Exception {
        String stylizedMessage = "(1/12) Delete from the cursor to the beginning of the screen";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        term.cursorShow();
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_BLOCK_SHAPE);
        printRandomTextBlock(term, 6, lines - 6, columns);
        term.printAt(12, 60);
        Thread.sleep(THREE_SECONDS_DELAY);
        term.eraseFromCursorToBeginScreen();
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates terminal manipulation by displaying a header, a warning message,
     * and a block of random text. It then positions the cursor, waits for a delay,
     * and erases content from the cursor's position to the end of the screen.
     *
     * @throws Exception If an error occurs during execution, such as terminal interaction failures
     *                   or interruptions during delays.
     */
    private void demo_2() throws Exception {
        String stylizedMessage = "(2/12) Delete from the cursor to the end of the screen";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        printRandomTextBlock(term, 6, lines - 6, columns);
        term.printAt(12, 60);
        Thread.sleep(THREE_SECONDS_DELAY);
        term.eraseFromCursorToEndScreen();
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates terminal manipulation by displaying a stylized header message,
     * clearing the screen, showing a warning message, and printing a block of random text.
     * It then positions the cursor and introduces delays to allow the user to view the
     * intermediate steps. Finally, it erases content from the cursor's position to the
     * beginning of the current line.
     *
     * @throws Exception If any error occurs during terminal interaction or execution delays.
     */
    private void demo_3() throws Exception {
        String stylizedMessage = "(3/12) Delete from the cursor to the beginning of the line";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        printRandomTextBlock(term, 6, 1, columns);
        term.printAt(6, 60);
        Thread.sleep(THREE_SECONDS_DELAY);
        term.eraseFromCursorToBeginLine();
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates terminal manipulation by displaying a stylized header message,
     * clearing the screen, showing a warning message, and printing a block of random text.
     * It then positions the cursor and introduces delays to allow the user to view the
     * intermediate steps. Finally, it erases content from the cursor's position to the
     * end of the current line.
     *
     * @throws Exception If an error occurs during terminal interaction or execution delays.
     */
    private void demo_4() throws Exception {
        String stylizedMessage = "(4/12) Delete from the cursor to the end of the line";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        printRandomTextBlock(term, 6, 1, columns);
        term.printAt(6, 60);
        Thread.sleep(THREE_SECONDS_DELAY);
        term.eraseFromCursorToEndLine();
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates terminal manipulation by displaying a stylized header message,
     * clearing the screen, showing a warning message, and printing a block of random text.
     * It then positions the cursor, introduces delays to allow the user to follow the steps,
     * and finally deletes the line where the cursor is located.
     *
     * @throws Exception If an error occurs during terminal interaction or execution delays.
     */
    private void demo_5() throws Exception {
        String stylizedMessage = "(5/12) Delete the line where the cursor is located";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        printRandomTextBlock(term, 6, 3, columns);
        term.printAt(6, 60);
        Thread.sleep(THREE_SECONDS_DELAY);
        term.deleteCursorLine();
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates terminal manipulation by displaying a stylized header message,
     * clearing the screen, showing a warning message, and printing a block of random text.
     * The method positions the cursor, introduces delays for visibility of intermediate
     * steps, and finally deletes five lines, starting with the one containing the cursor's position.
     *
     * @throws Exception If an error occurs during terminal interaction or execution delays.
     */
    private void demo_6() throws Exception {
        String stylizedMessage = "(6/12) Delete 5 lines including the one containing the cursor";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        printRandomTextBlock(term, 6, 15, columns);
        term.printAt(6, 60);
        Thread.sleep(THREE_SECONDS_DELAY);
        term.eraseLines(5);
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates terminal manipulation by displaying a stylized header message,
     * clearing the screen, showing a warning message, and printing a block of random text.
     * The method positions the cursor, introduces delays to allow the user to follow the steps,
     * and finally deletes 20 characters starting at the cursor position.
     *
     * @throws Exception If an error occurs during terminal interaction or execution delays.
     */
    private void demo_7() throws Exception {
        String stylizedMessage = "(7/12) Delete the 20 characters following the cursor position";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        printRandomTextBlock(term, 6, 1, columns);
        term.printAt(6, 60);
        Thread.sleep(THREE_SECONDS_DELAY);
        term.deleteCharacters(20);
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates terminal manipulation by displaying a stylized header message,
     * clearing the screen, showing a warning message, and printing a block of random text.
     * The method positions the cursor, introduces delays for the user to observe the steps,
     * and deletes 20 characters starting at the cursor's current position by overwriting them
     * with space characters.
     *
     * @throws Exception If an error occurs during terminal interaction or execution delays.
     */
    private void demo_8() throws Exception {
        String stylizedMessage = "(8/12) Deletes 20 characters from the current cursor position by overwriting them with a space character.";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        printRandomTextBlock(term, 6, 1, columns);
        term.printAt(6, 60);
        Thread.sleep(THREE_SECONDS_DELAY);
        term.deleteCharsWithSpaces(20);
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates terminal manipulation by displaying a stylized header message,
     * clearing the screen, showing a warning message, and printing a random text block.
     * The method positions the cursor, introduces delays for clarity, and inserts
     * ten spaces at the cursor's location, shifting all existing text to the right.
     *
     * @throws Exception If an error occurs during terminal interaction or execution delays.
     */
    private void demo_9() throws Exception {
        String stylizedMessage = "(9/12) Insert 10 spaces at the current cursor position. Shift all existing text to the right.";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        printRandomTextBlock(term, 6, 1, columns);
        term.printAt(6, 60);
        Thread.sleep(THREE_SECONDS_DELAY);
        term.insertNSpaces(10);
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates terminal manipulation by displaying a stylized header message,
     * clearing the screen, showing a warning message, and printing a block of random text.
     * The method positions the cursor, introduces delays to allow users to follow the steps,
     * and inserts 10 lines at the current cursor position. The cursor line and the lines
     * below it will shift down.
     *
     * @throws Exception If an error occurs during terminal interaction or execution delays.
     */
    private void demo_10() throws Exception {
        String stylizedMessage = "(10/12) Insert 10 lines at the current cursor position. The cursor line and the lines below it will shift down";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        printRandomTextBlock(term, 6, 5, columns);
        term.printAt(7, 60);
        Thread.sleep(THREE_SECONDS_DELAY);
        term.insertNLines(10);
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates terminal manipulation by displaying a stylized header message,
     * clearing the screen, showing a warning message, and printing a block of random text.
     * The method introduces a delay and scrolls the terminal text down line by line,
     * allowing users to observe the scrolling effect in the terminal.
     *
     * @throws Exception If an error occurs during terminal interaction or execution delays.
     */
    private void demo_11() throws Exception {
        String stylizedMessage = String.format("(11/12) Scroll down %s the text", lines);
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        Thread.sleep(TWO_SECONDS_DELAY);
        printRandomTextBlock(term, 3, lines - 3, columns);
        for (int i = 1; i <= lines; i++) {
            Thread.sleep(SHORT_DELAY);
            term.scrollTextDown(1);
        }
        Thread.sleep(THREE_SECONDS_DELAY);
    }
    /**
     * Demonstrates the ability to scroll text upward in the terminal while displaying
     * a header and a warning message. The method simulates text scrolling line by line
     * with customizable delays and a pre-defined text block.
     *
     * @throws Exception if an interruption occurs during thread sleep or terminal operations.
     */
    private void demo_12() throws Exception {
        String stylizedMessage = String.format("(12/12) Scroll up %s lines the text", lines);
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        Thread.sleep(TWO_SECONDS_DELAY);
        printRandomTextBlock(term, 3, lines - 3, columns);
        for (int i = 1; i <= lines; i++) {
            Thread.sleep(SHORT_DELAY);
            term.scrollTextUp(1);
        }
        Thread.sleep(THREE_SECONDS_DELAY);
    }
}
