package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains ANSI escape codes for erasing screen and line content.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 *
 * @author Juan F. Ruiz
 */
public class EraseSequencesCodes {
    /**
     * Escape sequence that erases from the cursor to the end of the screen
     */
    public static final String ERASES_FROM_CURSOR_TO_END_OF_SCREEN = ESC + "[0J";
    /**
     * Escape sequence that Erases from the cursor to the beginning of the screen
     */
    public static final String ERASES_FROM_CURSOR_TO_BEGINNING_OF_SCREEN = ESC + "[1J";
    /**
     * Escape sequence that erases the entire screen
     */
    public static final String CLEAR_SCREEN = ESC + "[2J";
    /**
     * Escape sequence that erases from the cursor to the end of the current line
     */
    public static final String ERASES_FROM_CURSOR_TO_END_OF_CURRENT_LINE = ESC + "[0K";
    /**
     * Escape sequence that erases from the cursor to the beginning of the current line
     */
    public static final String ERASES_FROM_CURSOR_TO_BEGINNING_OF_CURRENT_LINE = ESC + "[1K";
    /**
     * Escape sequence that erases the entire current line (Erasing a line DOES NOT move the cursor)
     */
    public static final String ERASES_CURRENT_LINE = ESC + "[2K";
    /**
     * Escape sequence that erases a character from the current position
     */
    public static final String ERASE_CHARACTER = ESC + "[X";

    /**
     * Private constructor to prevent instantiation.
     */
    private EraseSequencesCodes() {
    }

    /**
     * Returns an escape sequence that deletes everything from the cursor position
     * to the end of the screen
     * @return a string with the escape sequence to delete everything from the cursor position to the end of the screen
     */
    public static String deleteFromCursorToEndScreen() {
        return ERASES_FROM_CURSOR_TO_END_OF_SCREEN;
    }

    /**
     * Returns an escape sequence that deletes everything from the cursor position
     * to the beginning of the screen
     * @return a string with the escape sequence to delete everything from the cursor position to the beginning of the screen
     */
    public static String deleteFromCursorToBeginScreen() {
        return ERASES_FROM_CURSOR_TO_BEGINNING_OF_SCREEN;
    }

    /**
     * Returns an escape sequence that erases the entire screen
     * @return a string with the escape sequence to erase the entire screen
     */
    public static String clearScreen() {
        return EraseSequencesCodes.CLEAR_SCREEN;
    }

    /**
     * Returns an escape sequence that deletes everything from the cursor position
     * to the end of the line where it is located.
     * @return a string with the escape sequence to delete everything from the cursor position to the end of the line
     */
    public static String deleteFromCursorToEndLine() {
        return ERASES_FROM_CURSOR_TO_END_OF_CURRENT_LINE;
    }

    /**
     * Returns an escape sequence that deletes everything from the cursor position
     * to the beginning of the line where it is located.
     * @return a string with the escape sequence to delete everything from the cursor position to the beginning of the line
     */
    public static String deleteFromCursorToBeginLine() {
        return ERASES_FROM_CURSOR_TO_BEGINNING_OF_CURRENT_LINE;
    }

    /**
     * Returns an escape sequence that erases possible characters from the current
     * line where the cursor is located.
     * @return a string with the escape sequence to erase possible characters from the current line
     */
    public static String deleteLine() {
        return ERASES_CURRENT_LINE;
    }

    /**
     * Returns an escape sequence that deletes a character from the current position
     *
     * @return a string with the escape sequence to delete a character
     */
    public static String deleteChar() {
        return ERASE_CHARACTER;
    }

    /**
     * Generates an ANSI escape sequence to delete a specified number of characters
     * from the current position of the cursor in the terminal.
     *
     * @param cars the number of characters to delete. Must be a positive integer.
     * @return a string containing the escape sequence to delete the specified number of characters.
     * @throws IllegalArgumentException if the provided number of characters is not a positive integer.
     */
    public static String deleteChars(int cars) {
        if (cars > 0){
            return ESC + "[" + cars + "X";
        } else {
            throw new IllegalArgumentException("The number of characters to delete must be a positive integer");
        }

    }

    @Override
    public String toString() {
        return "EraseSecuencesCodes {'ANSI escape codes for erasing screen and line content'}";
    }

}
