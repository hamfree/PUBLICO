package es.nom.juanfranciscoruiz.ansiterm.codes;

import es.nom.juanfranciscoruiz.ansiterm.model.CSI;

/**
 * Utility class that provides ANSI escape sequences for setting and resetting
 * scrolling margins in a terminal. These sequences define the top and bottom
 * margins of a scrolling region within the terminal's display area.
 * <p>
 * The following sequences allow a program to configure the “scrolling region”
 * of the screen that is affected by scrolling operations. This is a subset of
 * the rows that are adjusted when the screen would otherwise scroll, for
 * example, on a ‘\n’ or RI. These margins also affect the rows modified by
 * Insert Line (IL) and Delete Line (DL), Scroll Up (SU) and Scroll Down (SD).
 *<p>
 * The scrolling margins can be especially useful for having a portion of the
 * screen that doesn’t scroll when the rest of the screen is filled, such as
 * having a title bar at the top or a status bar at the bottom of your
 * application.
 * <p>
 * For DECSTBM, there are two optional parameters, 't' and 'b', which are used
 * to specify the rows that represent the top and bottom lines of the scroll
 * region, inclusive. If the parameters are omitted, 't' defaults to 1 and 'b'
 * defaults to the current viewport height.
 * <p>
 * Scrolling margins are per-buffer, so importantly, the Alternate Buffer and
 * Main Buffer maintain separate scrolling margins settings (so a full screen
 * application in the alternate buffer will not poison the main buffer’s
 * margins).
 */
public class ScrollingMarginsCodes {
    /**
     * Provides access to the singleton instance of the {@code ScrollingMarginsCodes} class.
     */
    private static final String ESC = CSI.ESC;

    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private ScrollingMarginsCodes() {}

    /**
     * Sequence......: ESC [ t ; b r
     * Code..........: DECSTBM
     * Description...: Set Scrolling Region
     * Behavior......: Sets the top and bottom margins which defines the scrolling
     * region. The default is the full height of the terminal.
     *
     * @param top    the top line of the scrolling region
     * @param bottom the bottom line of the scrolling region
     * @return the ANSI escape sequence
     */
    public static String setScrollingMargins(int top, int bottom) {
        return ESC + "[" + top + ";" + bottom + "r";
    }

    /**
     * Resets the scrolling margins to the full height of the terminal.
     *
     * @return the ANSI escape sequence
     */
    public static String resetScrollingMargins() {
        return ESC + "[r";
    }
}
