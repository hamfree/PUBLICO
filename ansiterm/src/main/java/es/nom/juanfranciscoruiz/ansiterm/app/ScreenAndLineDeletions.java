package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.codes.CursorStylesCodes;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.*;

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
     * Represents a short delay duration typically used for brief waiting periods
     * or time intervals in execution. This constant holds the value of 100 milliseconds.
     */
    public final static Long SHORT_DELAY = 100L;
    /**
     * Represents a delay duration of two seconds, often used for short pauses or timeouts.
     */
    public final static Long TWO_SECONDS_DELAY = 2000L;
    /**
     * Represents a message displayed to inform the user that the process will continue automatically every 4 seconds.
     */
    public final static String MSG = "The process will continue automatically every 4 seconds. Do not press any keys!";
    /**
     * Represents a title for the screen and line deletions demonstration.
     */
    public final static String TITLE = "------------ Deleted from the cursor and scroll ------------";
    /**
     * Represents a message prompting the user to press Enter to return to the menu.
     */
    public static final String PRESS_ENTER_TO_RETURN_TO_MENU = "Press <ENTER> to return to menu";
    
    /**
     * Constructs a new ScreenAndLineDeletions.
     */
    ScreenAndLineDeletions() {
    
    }

    /**
     * Performs the screen and line deletions demonstration.
     *
     * @param term The ANSITerm object to use.
     * @throws Exception If an error occurs during execution.
     */
    void perform(ANSITerm term) throws Exception {
        int columnas = term.getTerminalSize().getColumns();
        int filas = term.getTerminalSize().getLines();
        term.cursorShow();
        
        clearScreen(term);
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_BLOCK_SHAPE);
        printTitle(term,  "(1/12) Delete from the cursor to the beginning of the screen");
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, filas - 5, columnas);
        term.printAt(12, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteFromCursorToBeginScreen();
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, "(2/12) Delete from the cursor to the end of the screen");
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, filas - 5, columnas);
        term.printAt(12, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteFromCursorToEndScreen();
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, "(3/12) Delete from the cursor to the beginning of the line");
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 1, columnas);
        term.printAt(5, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteFromCursorToBeginLine();
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, "(4/12) Delete from the cursor to the end of the line");
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 1, columnas);
        term.printAt(5, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteFromCursorToEndLine();
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, "(5/12) Delete the line where the cursor is located");
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 3, columnas);
        term.printAt(6, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteCursorLine();
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, "(6/12) Delete 5 lines including the one containing the cursor");
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 15, columnas);
        term.printAt(6, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteLines(5);
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, "(7/12) Delete the 20 characters following the cursor position");
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 1, columnas);
        term.printAt(5, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.eraseCharacters(20);
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, "(8/12) Deletes 20 characters from the current cursor position by overwriting them with a space character.");
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 1, columnas);
        term.printAt(5, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.eraseCharsWithSpaces(20);
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, "(9/12) Insert 10 spaces at the current cursor position. Shift all existing text to the right.");
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 1, columnas);
        term.printAt(5, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.insertSpaces(10);
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, "(10/12) Insert 10 lines at the current cursor position. The cursor line and the lines below it will shift down");
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 5, columnas);
        term.printAt(7, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.insertLines(10);
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        String title = String.format("(11/12) Scroll down %s the text", filas);
        printTitle(term, title);
        showWarning(term, MSG);
        printRandomTextBlock(term, 3, filas - 3, columnas);
        for (int i = 1; i <= filas; i++) {
            Thread.sleep(SHORT_DELAY);
            term.scrollTextDown(1);
        }
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        title = String.format("(12/12) Scroll up %s lines the text", filas);
        printTitle(term, title);
        showWarning(term, MSG);
        printRandomTextBlock(term, 3, filas - 3, columnas);
        Thread.sleep(TWO_SECONDS_DELAY);
        for (int i = 1; i <= filas; i++) {
            Thread.sleep(SHORT_DELAY);
            term.scrollTextUp(1);
        }
        Thread.sleep(FOUR_SECONDS_DELAY);
        pauseWithMessage(0, PRESS_ENTER_TO_RETURN_TO_MENU);
    }

}
