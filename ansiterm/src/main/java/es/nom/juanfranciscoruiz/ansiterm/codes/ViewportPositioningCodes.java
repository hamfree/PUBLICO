package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Class for handling viewport positioning codes in ANSI terminal
 * The text is moved starting with the line the cursor is on. If the cursor is
 * on the middle row of the viewport, then scroll up would move the bottom half
 * of the viewport, and insert blank lines at the bottom. Scroll down would move
 * the top half of the viewport’s rows and insert new lines at the top.
 * <p>
 * Also, important to note is scroll up and down are also affected by the
 * scrolling margins. Scroll up and down won’t affect any lines outside the
 * scrolling margins.
 * <p>
 * The default value for 'n' is 1, and the value can be optionally omitted.
 *
 * @author Juan F. Ruiz
 */
public class ViewportPositioningCodes {
    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private ViewportPositioningCodes() {
    }

    /**
     * Sequence......: ESC [ n S]
     * Code..........: SU
     * Description...: Scroll Up
     * Behavior......: Scroll text up by 'n' lines. Also known as pan down, new lines
     * fill in from the bottom of the screen
     *
     * @param lines the number of lines to scroll
     * @return the ANSI escape sequence
     */
    public static String scrollLinesUp(int lines) {
        return (ESC + "[" + lines + "S");
    }

    /**
     * Sequence......: ESC [ n T]
     * Code..........: SD
     * Description...: Scroll Down
     * Behavior......: Scroll down by n lines. Also known as pan up, new lines fill
     * in from the top of the screen
     *
     * @param lines the number of lines to scroll down
     * @return the ANSI escape sequence
     */
    public static String scrollLinesDown(int lines) {
        return (ESC + "[" + lines + "T");
    }

    @Override
    public String toString() {
        return "ViewportPositioningCodes{'ANSI escape codes for viewport positioning'}";
    }
}
