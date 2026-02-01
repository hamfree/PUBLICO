package es.nom.juanfranciscoruiz.ansiterm.codes;

import org.slf4j.Logger;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains ANSI escape codes for cursor control.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 *
 * @author Juan F. Ruiz
 */
public class CursorControlCodes {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CursorControlCodes.class);

    /**
     * Singleton instance of the {@code CursorControlCodes} class.
     * This instance provides access to ANSI escape codes for cursor control,
     * ensuring consistent use of these codes and preventing instantiation
     * of multiple objects of this class.
     */
    public static final CursorControlCodes INSTANCE;

    static {
        INSTANCE = new CursorControlCodes() {};
        if (LOGGER.isDebugEnabled()) LOGGER.debug(INSTANCE.toString());
    }

    /**
     * Hides the cursor (DECTCEM)
     */
    public static final String HIDES_CURSOR = ESC + "[?25l";
    /**
     * Shows the cursor (DECTCEM)
     */
    public static final String SHOWS_CURSOR = ESC + "[?25h";

    /**
     * Enables cursor blinking (ATT160)
     */
    public static final String ENABLE_BLINK_CURSOR = ESC + "[?12h";
    /**
     * Disables cursor blinking (ATT160)
     */
    public static final String DISABLE_BLINK_CURSOR = ESC + "[?12l";

    /**
     * Private constructor. The user can't instantiate class
     */
    private CursorControlCodes() {
    }

    /**
     * Provides access to the singleton instance of the {@code CursorControlCodes} class.
     * This ensures that only one instance of the class exists throughout the application.
     *
     * @return the singleton instance of the {@code CursorControlCodes} class
     */
    public static CursorControlCodes getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return  "CursorControlCodes{'ANSI escape codes for cursor control'}";
    }
}
