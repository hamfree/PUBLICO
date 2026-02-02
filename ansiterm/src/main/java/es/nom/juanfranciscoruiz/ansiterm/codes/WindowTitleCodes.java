package es.nom.juanfranciscoruiz.ansiterm.codes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowTitleCodes {
    /**
     * For logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowTitleCodes.class);

    /**
     * Singleton instance of the {@code WindowTitleCodes} class.
     */
    private static final WindowTitleCodes instance;

    static {
        instance = new WindowTitleCodes();
        if (LOGGER.isDebugEnabled()) LOGGER.debug(instance.toString());
    }

    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private WindowTitleCodes() {}

    /**
     * Sequence......: ESC ] 0 ; <string> BEL | ST
     * Code..........: OSC 0
     * Description...: Set Window Title and Icon Name
     * Behavior......: Sets the terminal window title and the icon name to the specified string.
     *
     * @param title the title to set
     * @return the ANSI escape sequence
     */
    public static String getEsForSetWindowTitle(String title) {
        return OSC.OSC + title + OSC.ST;
    }

    /**
     * Sequence......: ESC ] 2 ; <string> BEL | ST
     * Code..........: OSC 2
     * Description...: Set Window Title
     * Behavior......: Sets only the terminal window title to the specified string.
     *
     * @param title the title to set
     * @return the ANSI escape sequence
     */
    public static String getEsForSetOnlyWindowTitle(String title) {
        return OSC.OSC + "2;" + title + OSC.ST;
    }

}
