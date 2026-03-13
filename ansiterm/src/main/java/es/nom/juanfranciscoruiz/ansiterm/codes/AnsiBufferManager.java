package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.model.ansisequences.CSISequences.ESC;

/**
 * Contains ANSI escape codes for viewport and buffer management.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 *
 * @author Juan F. Ruiz
 */
public class AnsiBufferManager {
    /**
     * Enables the alternate buffer
     */
    public static final String ENABLES_ALTERNATE_BUFFER = ESC + "[?1049h";
    /**
     * Disables the alternate buffer
     */
    public static final String DISABLES_ALTERNATE_BUFFER = ESC + "[?1049l";
    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private AnsiBufferManager() {}
    /**
     * Retrieves the ANSI escape sequence that disables the alternate buffer in
     * the terminal. This is typically used to restore the primary buffer after
     * switching to the alternate buffer for temporary display.
     *
     * @return the ANSI escape sequence for disabling the alternate buffer
     */
    public static String disableAlternateBuffer() {
        return DISABLES_ALTERNATE_BUFFER;
    }

    /**
     * Retrieves the ANSI escape sequence that enables the alternate buffer in
     * the terminal. This is typically used for temporary display or for
     * creating a separate buffer for specific content.
     *
     * @return the ANSI escape sequence for enabling the alternate buffer
     */
    public static String enableAlternateBuffer() {
        return ENABLES_ALTERNATE_BUFFER;
    }

    @Override
    public String toString() {
        return "PositionCodes{'ANSI escape codes for terminal screen positioning'}";
    }
}
