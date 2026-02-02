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

    public final static Long FOUR_SECONDS_DELAY = 4000L;
    public final static Long SHORT_DELAY = 100L;
    public final static Long TWO_SECONDS_DELAY = 2000L;
    public final static String MSG = "The process will continue automatically every 4 seconds. Do not press any keys!";
    public final static String TITLE = "------------ Deleted from the cursor and scroll ------------";
    public static final String PRESS_ENTER_TO_RETURN_TO_MENU = "Press <ENTER> to return to menu";
    String[] messages;


    /**
     * Constructs a new ScreenAndLineDeletions.
     */
    ScreenAndLineDeletions() {
        messages = new String[]{
                "(1/12) Delete from the cursor to the beginning of the screen",
                "(2/12) Delete from the cursor to the end of the screen",
                "(3/12) Delete from the cursor to the beginning of the line",
                "(4/12) Delete from the cursor to the end of the line",
                "(5/12) Delete the line where the cursor is located",
                "(6/12) Delete 5 lines including the one containing the cursor",
                "(7/12) Delete the 20 characters following the cursor position",
                "(8/12) Deletes 20 characters from the current cursor position by overwriting them with a space character.",
                "(9/12) Insert 10 spaces at the current cursor position. Shift all existing text to the right.",
                "(10/12) Insert 10 lines at the current cursor position. The cursor line and the lines below it will shift down",
                "(11/12) Move {} lines down the text",
                "(12/12) Move {} lines upwards the text"
        };
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

        clearScreen(term);
        term.cursorShow();
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_BLOCK_SHAPE);
        printTitle(term, messages[0]);
        showWarning(term, MSG);

        printRandomTextBlock(term, 5, filas - 5, columnas);
        term.printAt(12, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteFromCursorToBeginScreen();
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, messages[1]);
        showWarning(term, MSG);

        printRandomTextBlock(term, 5, filas - 5, columnas);
        term.printAt(12, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteFromCursorToEndScreen();
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, messages[3]);
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 1, columnas);
        term.printAt(5, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteFromCursorToBeginLine();
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, messages[4]);
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 1, columnas);
        term.printAt(5, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteFromCursorToEndLine();
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, messages[5]);
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 3, columnas);
        term.printAt(6, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteCursorLine();
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, messages[6]);
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 15, columnas);
        term.printAt(6, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.deleteLines(5);
        Thread.sleep(FOUR_SECONDS_DELAY);


        clearScreen(term);
        printTitle(term, messages[7]);
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 1, columnas);
        term.printAt(5, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.eraseCharacters(20);
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, messages[8]);
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 1, columnas);
        term.printAt(5, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.eraseCharsWithSpaces(20);
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, messages[9]);
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 1, columnas);
        term.printAt(5, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.insertSpaces(10);
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        printTitle(term, messages[10]);
        showWarning(term, MSG);
        printRandomTextBlock(term, 5, 5, columnas);
        term.printAt(7, 60);
        Thread.sleep(FOUR_SECONDS_DELAY);
        term.insertLines(10);
        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        String title = String.format(messages[11], filas);
        printTitle(term, title);
        showWarning(term, MSG);
        printRandomTextBlock(term, 3, filas - 3, columnas);

        for (int i = 1; i <= filas; i++) {
            Thread.sleep(SHORT_DELAY);
            term.scrollTextDown(1);
        }

        Thread.sleep(FOUR_SECONDS_DELAY);

        clearScreen(term);
        title = String.format(messages[12], filas);
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
