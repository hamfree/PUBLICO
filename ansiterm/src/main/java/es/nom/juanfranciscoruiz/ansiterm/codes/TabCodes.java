package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.model.CSI.ESC;

/**
 * Contains ANSI escape codes for tab control.
 * <p>
 * The following constants and functions  allow an application to set the tab
 * stop locations within the console window, remove them, and navigate between them.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 *
 * @author Juan F. Ruiz
 */
public class TabCodes {
    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private TabCodes() {
    }

    /**
     * Sequence.......: ESC H
     * Code...........: HTS
     * Description....: Horizontal Tab Set
     * Behavior.......: Sets a tab stop in the current column the cursor is in.
     */
    public static final String TAB_HORIZONTAL_TAB_SET = ESC + "H";

    /**
     * Clear the tab stop in the current column
     * Sequence.......: ESC [0g
     * Code...........: TBC
     * Description....: Tab Clear (current column)
     * Behavior.......: Clears the tab stop in the current column, if there is
     * one. Otherwise does nothing.
     */
    public static final String TAB_CLEAR_CURRENT_COLUMN = ESC + "[0g";

    /**
     * ANSI escape sequence for clearing all tab stops in the terminal.
     * <p>
     * Sequence.......: ESC [3g
     * Description....: Removes all configured tab stops, effectively resetting
     * the tab stop settings.
     * Behavior.......: Clears all column-based tab stops.
     */
    public static final String TAB_CLEAR_ALL_COLUMNS = ESC + "[3g";

    /**
     * Sequence.......: ESC [nI
     * Code...........: CHT
     * Description....: Cursor Horizontal (Forward) Tab
     * Behavior.......: Advance the cursor to the next column (in the same row)
     * with a tab stop
     * Notes..........: For both CHT and CBT, 'n' is an optional parameter that
     * (default=1) indicating how many times to advance the cursor in
     * the specified direction. If there are no tab stops set via HTS,
     * CHT and CBT will treat the first and last columns of the window
     * as the only two tab stops. Using HTS to set a tab stop will also
     * cause the console to navigate to the next tab stop on the output
     * of a TAB (0x09, ‘\t’) character, in the same manner as CHT.
     *
     * @param n number of times to advance the cursor in the specified direction
     * @return the ANSI escape sequence
     *
     */
    public static String setTabCursorHorizontalTab(int n) {
        return ESC + "[" + n + "I";
    }

    /**
     * Move the cursor to the previous column (in the same row) with a tab stop
     * Sequence.......: ESC [nZ
     * Code...........: CBT
     * Description....: Cursor Backwards Tab
     * Behavior.......: Move the cursor to the previous column (in the same row)
     * with a tab stop
     *
     * @param n number of times to move the cursor in the specified direction
     * @return the ANSI escape sequence
     *
     */
    public static String setTabCursorBackwardsTab(int n) {
        return ESC + "[" + n + "Z";
    }

    @Override
    public String toString() {
        return "TabCodes{'ANSI escape codes for tab control'}";
    }
}
