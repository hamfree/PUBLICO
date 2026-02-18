package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * The following class allow a program to change the active character set mapping.
 * This allows a program to emit 7-bit ASCII characters but have them displayed as
 * other glyphs on the terminal screen itself. Currently, the only two supported
 * character sets are ASCII (default) and the DEC Special Graphics Character Set.
 * See <a href="http://vt100.net/docs/vt220-rm/table2-4.html">...</a> for a
 * listing of all the characters represented by the DEC Special Graphics
 * Character Set.
 *
 * @author Juan F. Ruiz
 */
public class CharacterSetModeCodes {
    /**
     * DEC Line Drawing Mode
     */
    public static final String DEC_LINE_DRAWING = ESC + "(0";
    /**
     * ASCII Mode
     */
    public static final String ASCII_MODE = ESC + "(B";

    /**
     * Private constructor. The user can't instantiate class
     */
    private CharacterSetModeCodes() {
    }


    /**
     * Sequence....: ESC(0
     * Description.: Designate Character Set
     * Behavior....: Enables DEC Line Drawing Mode.
     *
     * @return the CSI sequence to enable DEC Line Drawing Mode
     */
    public static String enableDECLineDrawing() {
        return DEC_LINE_DRAWING;
    }

    /**
     * Sequence....: ESC(B)
     * Description.: Designate Character Set
     * Behavior....: Enables ASCII Mode.
     *
     * @return the CSI sequence to enable ASCII Mode
     */
    public static String enableASCII() {
        return ASCII_MODE;
    }

    @Override
    public String toString() {
        return "CharacterSetModeCodes{'ANSI escape codes for character set mode'}";
    }
}
