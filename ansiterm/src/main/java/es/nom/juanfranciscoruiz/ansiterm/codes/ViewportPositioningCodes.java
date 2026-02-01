package es.nom.juanfranciscoruiz.ansiterm.codes;

import org.slf4j.Logger;

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
 * The default value for <n> is 1, and the value can be optionally omitted.
 *
 * @author Juan F. Ruiz
 */
public class ViewportPositioningCodes {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ViewportPositioningCodes.class);

    /**
     * Singleton instance of the {@code ViewportPositioningCodes} class.
     */
    private static ViewportPositioningCodes instance;

    static {
        instance = new ViewportPositioningCodes();
        if (LOGGER.isDebugEnabled()) LOGGER.debug(instance.toString());
    }

    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private ViewportPositioningCodes() {
    }

    /**
     * Provides access to the singleton instance of the {@code ViewportPositioningCodes} class.
     * This ensures that only one instance of the class exists throughout the application.
     *
     * @return the singleton instance of the {@code ViewportPositioningCodes} class
     */
    public static ViewportPositioningCodes getInstance() {
        return instance;
    }

    /**
     * Sequence......: ESC [ <n> S]
     * Code..........: SU
     * Description...: Scroll Up
     * Behavior......: Scroll text up by <n>. Also known as pan down, new lines
     * fill in from the bottom of the screen
     *
     * @param lines the number of lines to scroll
     */
    public void moveTextUp(int lines) {
        String sec_ansi = ESC + "[" + lines + "S";
        System.out.print(sec_ansi);
    }

    /**
     * Sequence......: ESC [ <n> T]
     * Code..........: SD
     * Description...: Scroll Down
     * Behavior......: Scroll down by <n>. Also known as pan up, new lines fill
     * in from the top of the screen
     *
     * @param lines the number of lines to scroll
     */
    public void moveTextDown(int lines) {
        String sec_ansi = ESC + "[" + lines + "T";
        System.out.print(sec_ansi);
    }

    @Override
    public String toString() {
        return "ViewportPositioningCodes{'ANSI escape codes for viewport positioning'}";
    }
}
