package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains the ANSI constants for changing cursor styles
 * @author juanf
 */
public class CursorStyles {
    /**
     * ANSI escape sequence with which all CSI sequences begin
     * More information at:
     * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">...</a>
     */
    /**
     * User-set cursor shape
     */
    public final static String CUR_USU = ESC + "[0 q";
    /**
     * Blinking block cursor shape
     */
    public final static String CUR_BLO_PAR = ESC + "[1 q";
    /**
     * Steady block cursor shape
     */
    public final static String CUR_BLO_EST = ESC + "[2 q";
    /**
     * Blinking underline cursor shape
     */
    public final static String CUR_SUB_PAR = ESC + "[3 q";
    /**
     * Steady underline cursor shape
     */
    public final static String CUR_SUB_EST  = ESC + "[4 q";
    /**
     * Blinking bar cursor shape
     */
    public final static String CUR_BAR_PAR  = ESC + "[5 q";
    /**
     * Steady bar cursor shape
     */
    public final static String CUR_BAR_ES  = ESC + "[6 q";

    private CursorStyles() {

    }
}
