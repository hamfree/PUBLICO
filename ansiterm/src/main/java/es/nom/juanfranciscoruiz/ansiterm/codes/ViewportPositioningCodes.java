package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.model.ansisequences.CSISequences.CSI;
import static es.nom.juanfranciscoruiz.ansiterm.model.ansisequences.CSISequences.ESC;

/**
 * Utility class for viewport (terminal screen) positioning and content manipulation.
 * This class provides methods to scroll terminal content vertically or horizontally
 * within specified regions, leveraging ANSI escape codes for terminal control.
 * <p>
 * The class is designed for advanced terminal-based applications where granular
 * control of the displayed content is required.
 * <p>
 * <b>Important</b>: some ANSI sequence escape codes belongs to DEC extensions for the
 * VT420 terminal and could not work in some terminal emulators than only emulates
 * estandar ANSI sequence escape codes or terminal emulators than supports only
 * ANSIVT100 terminals
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

    /**
     * Scrolls the content of a defined rectangular region within a terminal window upward
     * by a specified number of lines. New lines are filled from the bottom of the region.
     *
     * @param row    the starting row position of the scrollable region (1-indexed)
     * @param col    the starting column position of the scrollable region (1-indexed)
     * @param width  the width of the scrollable region in columns
     * @param height the height of the scrollable region in rows
     * @param lines  the number of lines to scroll up
     * @return the ANSI escape sequence representing the vertical scroll-up operation
     */
    public static String scrollUp(int row, int col, int width, int height, int lines) {
        return performScroll(row, col, width, height, lines, true);
    }

    /**
     * Scrolls the content of a defined rectangular region within a terminal window downward
     * by a specified number of lines. New lines are filled from the top of the region.
     *
     * @param row    the starting row position of the scrollable region (1-indexed)
     * @param col    the starting column position of the scrollable region (1-indexed)
     * @param width  the width of the scrollable region in columns
     * @param height the height of the scrollable region in rows
     * @param lines  the number of lines to scroll down
     * @return the ANSI escape sequence representing the vertical scroll-down operation
     */
    public static String scrollDown(int row, int col, int width, int height, int lines) {
        return performScroll(row, col, width, height, lines, false);
    }

    /**
     * Shifts the content of a specific rectangular region of the terminal window to the LEFT.
     * The text within the region moves to the left, and blank spaces appear on the right.
     *
     * @param row    Starting row (Y) of the rectangle (1-indexed).
     * @param col    Starting column (X) of the rectangle (1-indexed).
     * @param width  Width of the rectangle in columns.
     * @param height Height of the rectangle in rows.
     * @param chars  Number of characters to shift.
     * @return The ANSI escape sequence representing the horizontal scroll operation.
     */

    public static String scrollLeft(int row, int col, int width, int height, int chars) {
        return performHorizontalScroll(row, col, width, height, chars, true);
    }

    /**
     * Shifts the content of a specific rectangular region of the terminal window to the RIGHT.
     * The text within the region moves to the right, and blank spaces appear on the left.
     *
     * @param row    Starting row (Y) of the rectangle (1-indexed).
     * @param col    Starting column (X) of the rectangle (1-indexed).
     * @param width  Width of the rectangle in columns.
     * @param height Height of the rectangle in rows.
     * @param chars  Number of characters to shift.
     * @return The ANSI escape sequence representing the horizontal scroll operation.
     */

    public static String scrollRight(int row, int col, int width, int height, int chars) {
        return performHorizontalScroll(row, col, width, height, chars, false);
    }

    // ------------------------------ Utility methods ----------------------------------

    /**
     * Performs a scrolling operation within a defined region of a terminal window.
     * The method updates the visible content of the region by shifting lines up or down
     * while maintaining cursor and margin settings.
     *
     * @param row    the starting row position of the scrollable region
     * @param col    the starting column position of the scrollable region
     * @param width  the width of the scrollable region in columns
     * @param height the height of the scrollable region in rows
     * @param lines  the number of lines to scroll
     * @param isUp   true to scroll up (content moves up, new lines fill from the bottom),
     *               false to scroll down (content moves down, new lines fill from the top)
     */
    private static String performScroll(int row, int col, int width, int height, int lines, boolean isUp) {
        int bottom = row + height - 1;
        int right = col + width - 1;

        StringBuilder ansiBuilder = new StringBuilder();


        // 1. Saves the actual position of cursor (DEC estandar)
        ansiBuilder.append(ESC).append("7");

        // 2. Enables DECLRMM (Left/Right Margin Mode)
        ansiBuilder.append(CSI).append("?69h");


        // 3. Set superior and inferior margins (DECSTBM)
        ansiBuilder.append(CSI).append(row).append(";").append(bottom).append("r");

        // 4. Set Left and Right Margins (DECSLRM)
        ansiBuilder.append(CSI).append(col).append(";").append(right).append("s");

        // 5. Move the cursor INSIDE the restricted area (necessary on some terminals to limit scrolling)
        ansiBuilder.append(CSI).append(row).append(";").append(col).append("H");

        // 6. Run the Scroll
        if (isUp) {
            // 'S' = Scroll Up (the text goes up)
            ansiBuilder.append(CSI).append(lines).append("S");
        } else {
            // 'T' = Scroll Down (the text scrolls down)
            ansiBuilder.append(CSI).append(lines).append("T");
        }

        // 7. Disable DECLRMM (Restore Side Margins)
        ansiBuilder.append(CSI).append("?69l");

        // 8. Restore top and bottom margins to the entire screen
        ansiBuilder.append(CSI).append("r");

        // 9. Restore the original cursor position
        ansiBuilder.append(CSI).append("8");

        // Print and empty the buffer immediately so the terminal can process it
        return ansiBuilder.toString();
    }

    /**
     * Performs a horizontal scroll operation within a defined rectangular region of the terminal window.
     * The method iterates over each line within the region and moves characters either left or right
     * based on the specified direction, while maintaining cursor and margin settings.
     *
     * @param row    the starting row position of the rectangle
     * @param col    the starting column position of the rectangle
     * @param width  the width of the rectangle in columns
     * @param height the height of the rectangle in rows
     * @param chars  the number of characters to scroll horizontally
     * @param isLeft true to scroll left (content moves left, with blank spaces filling on the right),
     *               false to scroll right (content moves right, with blank spaces filling on the left)
     */
    private static String performHorizontalScroll(int row, int col, int width, int height, int chars, boolean isLeft) {
        int bottom = row + height - 1;
        int right = col + width - 1;

        StringBuilder ansiBuilder = new StringBuilder();

        ansiBuilder.append(ESC).append("7"); // Save the actual position of cursor
        ansiBuilder.append(CSI).append("?69h"); // Enable DECLRMM (Left/Right Margin Mode)
        ansiBuilder.append(CSI)
                .append(col)
                .append(";")
                .append(right)
                .append("s"); //Set Left and Right Margins

        // Unlike vertical scrolling, horizontal scrolling requires iterating through each line of the rectangle.
        for (int r = row; r <= bottom; r++) {
            // Move the cursor to the beginning of the line inside our rectangle
            ansiBuilder.append(CSI).append(r).append(";").append(col).append("H");

            if (isLeft) {
                // DCH (Delete Character): Deletes 'n' characters.
                // Since it's constrained by the right margin, it pulls the text to the left.
                ansiBuilder.append(CSI).append(chars).append("P");
            } else {
                // ICH (Insert Character): Inserts 'n' blank spaces.
                // Being restricted by the right margin, it pushes the text to the right.
                ansiBuilder.append(CSI).append(chars).append("@");
            }
        }

        ansiBuilder.append(CSI).append("?69l"); // Disable DECLRMM
        ansiBuilder.append(CSI).append("r"); // Restore top and bottom margins (for safety)
        ansiBuilder.append(ESC).append("8"); // Restore the original cursor position

        return ansiBuilder.toString();
    }

    @Override
    public String toString() {
        return "ViewportPositioningCodes{'ANSI escape codes for viewport positioning'}";
    }
}
