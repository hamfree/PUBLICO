package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains the ANSI constants for changing cursor styles
 *  ANSI escape sequence with which all CSI sequences begin
 *  More information at:
 *  <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">...</a>
 * @author juanf
 */
public class CursorStylesCodes {
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

    private CursorStylesCodes() {}
}
