package es.nom.juanfranciscoruiz.ansiterm.codes;


import es.nom.juanfranciscoruiz.ansiterm.Position;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains ANSI escape codes for cursor movement.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 *
 * @author Juan F. Ruiz
 */
public class CursorMovementCodes {

    /**
     * Sequence......: ESC[H
     * Code..........: DECXCPR
     * Description...: Moves the cursor to position 0,0
     * Behavior......: Moves the cursor to position 0,0 of the visible screen
     */
    public static final String CURSOR_MOVE_TO_BEGIN = ESC + "[H";
    /**
     * Sequence......: ESC[H
     * Code..........: DECXCPR
     * Description...: Specify the cursor position
     * Behavior......: Outputs the cursor position as ESC[&lt;r&gt;&lt;c&gt;R,
     * where &lt;r&gt; is equal to the cursor row and &lt;c&gt; is equal to the cursor column
     */
    public static final String CURSOR_GET_POSITION = ESC + "[6n";
    /**
     * Sequence......: ESC[M
     * Code..........: DECUP
     * Description...: Moves the cursor one line up, scrolling if necessary (RI)
     * Behavior......: Moves the cursor one line up, scrolling if necessary (RI)
     */
    public static final String CURSOR_MOVE_ONE_LINE_UP = ESC + "M";
    /**
     * Sequence......: ESC 7
     * Code..........: DECSC
     * Description...: Saves the cursor position in memory.
     * Notes.........: No value will be stored in memory until the `save` command
     * is used for the first time. The only way to access the saved value is with the `restore` command.
     */
    public static final String CURSOR_SAVE_CURRENT_POSITION = ESC + "7";
    /**
     * Sequence......: ESC 8
     * Code..........: DECSR
     * Description...: Restores the cursor to its last saved position from memory
     * Notes.........: No value will be stored in memory until the `save` command
     * is used for the first time. The only way to access the saved value is with the `restore` command.
     *
     */
    public static final String CURSOR_RESTORE_CURRENT_POSITION = ESC + "8";

    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private CursorMovementCodes() {
    }


    /**
     * Returns the ANSI escape sequence to move the cursor to the beginning of the line.
     * Sequence......: ESC [6 n
     * Code..........: DECXCPR
     * Description...: Specify the cursor position
     * Behavior......: Return the escape sequence to move the cursor position as ESC['r';'c'R,
     * where 'r' is equal to the cursor row and 'c' is equal to the cursor column
     *
     * @return a string representing the ANSI escape sequence for setting the cursor
     * to the beginning of the current line.
     */
    public static String setCursorToBegin() {
        return CURSOR_MOVE_TO_BEGIN;
    }

    /**
     * Returns the ANSI escape sequence to get the cursor position
     * Sequence......: ESC 6 n
     * Code..........: DECXCPR
     * Description...: Get cursor position
     * Behavior......: Return the escape sequence to get the cursor position as ESC['r';'c'R,
     * where 'r' is equal to the cursor row and 'c' is equal to the cursor column
     *
     * @return a string representing the ANSI escape sequence to get the cursor position
     */
    public static String getCursorPosition() {
        return CURSOR_GET_POSITION;
    }

    /**
     * Returns the ANSI escape sequence to move the cursor one line up
     * Sequence......: ESC[M
     * Code..........: DECUP
     * Description...: Moves the cursor one line up, scrolling if necessary (RI)
     * Behavior......: Moves the cursor one line up, scrolling if necessary (RI)
     *
     * @return a string representing the ANSI escape sequence to move the cursor one line up
     */
    public static String moveCursorOneLineUp() {
        return CURSOR_MOVE_ONE_LINE_UP;
    }

    /**
     * Returns the escape sequence to move the cursor to the line, column position
     * of the terminal
     * <p>
     * Sequence......: ESC[y;xH
     * Code..........: CUP
     * Description...: Returns the escape sequence for positioning the cursor at
     * the specified coordinates 'x'; 'y' within the  window, where 'x' is the
     * column of the row 'y'
     *
     * @param line   integer with the line to move the cursor to
     * @param column integer with the column to move the cursor to
     * @return a string representing the ANSI escape sequence to move the cursor to the position indicated by line and column
     */
    public static String setCursorPosition(int line, int column) {
        return (ESC + "[" + line + ";" + column + "H");
    }

    /**
     * Returns the escape sequence to move the cursor to the terminal position
     * indicated by p
     *
     * @param p Position object containing the position where the cursor
     *          will be moved
     * @return a string representing the ANSI escape sequence to move the cursor to the position indicated by p
     */
    public static String setCursorPosition(Position p) {
        if (p != null) {
            return (ESC + "[" + p.getCol() + ";" + p.getLin() + "H");
        } else {
            throw new IllegalArgumentException("Position cannot be null");
        }
    }

    /**
     * Sequence ......: ESC[nA
     * Code...........: CUU
     * Description....: Cursor Up
     * Behaviour......: Returns the escape sequence to move the cursor up as many 'n' lines as indicated in the 'lines' parameter
     *
     * @param lines an integer with the lines up where the cursor will be moved
     * @return a string representing the ANSI escape sequence to move the cursor up
     */
    public static String moveCursorNLinesUp(int lines) {
        return (ESC + "[" + lines + "A");
    }

    /**
     * Sequence ......: ESC[nB
     * Code...........: CUD
     * Description....: Cursor Down
     * Behaviour......: Returns the escape sequence to move the cursor down as many 'n'lines as indicated in the lines parameter
     *
     * @param lines an integer with the lines down where the cursor will be moved
     * @return a string representing the ANSI escape sequence to move the cursor down
     */
    public static String moveCursorNLinesDown(int lines) {
        return (ESC + "[" + lines + "B");
    }

    /**
     * Sequence ......: ESC[nC
     * Code...........: CUF
     * Description....: Cursor Forward
     * Behaviour......: Returns the escape sequence to move the cursor to the right as many 'n' characters as indicated in the cars parameter
     *
     * @param cars an integer with the characters to the right where the cursor will be moved
     * @return a string representing the ANSI escape sequence to move the cursor to the right
     */
    public static String moveCursorNCharsToRight(int cars) {
        return (ESC + "[" + cars + "C");
    }

    /**
     * Sequence ......: ESC[nD
     * Code...........: CUB
     * Description....: Cursor Backward
     * Behaviour......: Returns the escape sequence to move the cursor to the left as many 'n' characters as indicated in the cars parameter
     *
     * @param cars an integer specifying the number of characters to move the cursor to the left
     * @return a string representing the ANSI escape sequence to move the cursor to the left
     */
    public static String moveCursorNCharsToLeft(int cars) {
        // ESC[#D
        return (ESC + "[" + cars + "D");
    }

    /**
     * Returns the ANSI escape sequence to save the current cursor position.
     * <p>
     * Sequence......: ESC 7
     * Code..........: SCOSC
     * Description...: Saves the current cursor position to restore it later.
     * Behavior......: Stores the cursor's current position in memory, enabling it
     *                 to be restored to this position using the respective restore sequence.
     *
     * @return a string representing the ANSI escape sequence to save the current cursor position.
     */
    public static String saveCursorPosition(){
        return CURSOR_SAVE_CURRENT_POSITION;
    }

    /**
     * Returns the ANSI escape sequence to restore the current cursor position.
     * <p>
     * Sequence......: ESC 8
     * Code..........: RCSR
     * Description...: Restores the cursor position to the one saved with the save sequence.
     * Behavior......: Moves the cursor to the position saved with the save sequence.
     *
     * @return a string representing the ANSI escape sequence to restore the current cursor position.
     */
    public static String restoreCursorPosition(){
        return CURSOR_RESTORE_CURRENT_POSITION;
    }

    @Override
    public String toString() {
        return "CursorMovementCodes{'ANSI escape codes for cursor movement'}";
    }
}
