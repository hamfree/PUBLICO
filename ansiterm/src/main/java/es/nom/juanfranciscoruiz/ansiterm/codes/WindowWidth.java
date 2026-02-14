package es.nom.juanfranciscoruiz.ansiterm.codes;

import org.slf4j.Logger;

/**
 * This class provides ANSI escape sequences to set terminal window widths to
 * specific column sizes. It includes constants and utility methods for
 * configuring terminal widths.
 * <p>
 * The ANSI escape sequences defined in this class utilize the Control Sequence
 * Introducer (CSI), allowing the terminal to adjust its display width to
 * either 80 or 132 columns.
 * <p>
 * This class cannot be instantiated, as its sole purpose is to provide
 * pre-defined constants and utility methods for terminal window management.
 */
public class WindowWidth {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WindowWidth.class);

    /**
     * Sequence.......: ESC [ ? 3 h
     * Code...........: DECCOLM
     * Description....: Sets the terminal width to 132 columns.
     */
    public static final String WIDTH_132_COLUMNS = CSI.ESC + "[?3h";

    /**
     * Sequence.......: ESC [ ? 3 l
     * Code...........: DECCOLM
     * Description....: Sets the terminal width to 80 columns.
     */
    public static final String WIDTH_80_COLUMNS = CSI.ESC + "[?3l";

    /**
     * Private constructor to prevent instantiation.
     */
    private WindowWidth() {
    }

    /**
     * Retrieves the ANSI escape sequence to set the terminal width to 132 columns.
     *
     * @return a {@code String} representing the ANSI escape code.
     */
    public static String getEsForWidth132Columns() {
        return WIDTH_132_COLUMNS;
    }

    /**
     * Retrieves the ANSI escape sequence to set the terminal width to 80 columns.
     *
     * @return a {@code String} representing the ANSI escape code.
     */
    public static String getEsForWidth80Columns() {
        return WIDTH_80_COLUMNS;
    }

}
