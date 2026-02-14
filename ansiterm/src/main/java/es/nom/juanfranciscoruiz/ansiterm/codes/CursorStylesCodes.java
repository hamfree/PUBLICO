package es.nom.juanfranciscoruiz.ansiterm.codes;

import org.slf4j.Logger;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains the ANSI constants for changing cursor styles
 * ANSI escape sequence with which all CSI sequences begin
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 *
 * @author Juan F. Ruiz
 */
public class CursorStylesCodes {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CursorStylesCodes.class);

    /**
     * Singleton instance of the {@code CursorStylesCodes} class.
     * This variable ensures that only one instance of the {@code CursorStylesCodes} class
     * exists throughout the application and provides global access to it.
     */
    private static CursorStylesCodes instance;

    static {
        instance = new CursorStylesCodes();
        if (LOGGER.isDebugEnabled()) LOGGER.debug(instance.toString());
    }

    /**
     * User-set cursor shape
     */
    public final static String CURSOR_USER_SHAPE = ESC + "[0 q";
    /**
     * Blinking block cursor shape
     */
    public final static String CURSOR_BLOCK_SHAPE = ESC + "[1 q";
    /**
     * Steady block cursor shape
     */
    public final static String CURSOR_STEADY_BLOCK_SHAPE = ESC + "[2 q";
    /**
     * Blinking underline cursor shape
     */
    public final static String CURSOR_UNDERLINE_SHAPE = ESC + "[3 q";
    /**
     * Steady underline cursor shape
     */
    public final static String CURSOR_STEADY_UNDERLINE_SHAPE = ESC + "[4 q";
    /**
     * Blinking bar cursor shape
     */
    public final static String CURSOR_BLINKING_BAR_SHAPE = ESC + "[5 q";
    /**
     * Steady bar cursor shape
     */
    public final static String CURSOR_STEADY_BAR_SHAPE = ESC + "[6 q";

    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private CursorStylesCodes() {
    }

    /**
     * Provides access to the singleton instance of the {@code CursorStylesCodes} class.
     * This ensures that only one instance of the class exists throughout the application.
     *
     * @return the singleton instance of the {@code CursorStylesCodes} class
     */
    public static CursorStylesCodes getInstance() {
        return instance;
    }

    /**
     * Retrieves the ANSI escape sequence for the user-set cursor shape.
     *
     * @return the ANSI escape sequence for setting the user-defined cursor shape.
     */
    public static String getCursorUserShape() {
        return CURSOR_USER_SHAPE;
    }

    /**
     * Retrieves the ANSI escape sequence for the block cursor shape.
     *
     * @return the ANSI escape sequence for setting the block cursor shape.
     */
    public static String getCursorBlockShape() {
        return CURSOR_BLOCK_SHAPE;
    }

    /**
     * Retrieves the ANSI escape sequence for the steady block cursor shape.
     *
     * @return the ANSI escape sequence for setting the steady block cursor shape.
     */
    public static String getCursorSteadyBlockShape() {
        return CURSOR_STEADY_BLOCK_SHAPE;
    }

    /**
     * Retrieves the ANSI escape sequence for the blinking underline cursor shape.
     *
     * @return the ANSI escape sequence for setting the blinking underline cursor shape.
     */
    public static String getCursorUnderlineShape() {
        return CURSOR_UNDERLINE_SHAPE;
    }

    /**
     * Retrieves the ANSI escape sequence for the steady underline cursor shape.
     *
     * @return the ANSI escape sequence for setting the steady underline cursor shape.
     */
    public static String getCursorSteadyUnderlineShape() {
        return CURSOR_STEADY_UNDERLINE_SHAPE;
    }

    /**
     * Retrieves the ANSI escape sequence for the blinking bar cursor shape.
     *
     * @return the ANSI escape sequence for setting the blinking bar cursor shape.
     */
    public static String getCursorBlinkingBarShape() {
        return CURSOR_BLINKING_BAR_SHAPE;
    }

    /**
     * Retrieves the ANSI escape sequence for the steady bar cursor shape.
     *
     * @return the ANSI escape sequence for setting the steady bar cursor shape.
     */
    public static String getCursorSteadyBarShape() {
        return CURSOR_STEADY_BAR_SHAPE;
    }


    @Override
    public String toString() {
        return "CursorStylesCodes{'ANSI escape codes for cursor styles'}";
    }
}
