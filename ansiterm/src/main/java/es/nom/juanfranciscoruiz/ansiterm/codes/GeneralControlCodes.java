package es.nom.juanfranciscoruiz.ansiterm.codes;

import org.slf4j.Logger;

/**
 * The {@code GeneralControlCodes} class provides a set of ASCII control
 * characters as constants and methods to access these characters. This class
 * uses the Singleton design pattern to ensure only one instance of it exists
 * throughout the application.
 * <p>
 * The ASCII codes exposed by this class represent non-printable control
 * characters that are commonly used in terminal or text-based environments for
 * actions like producing a bell sound, moving the cursor, inserting horizontal
 * or vertical tabs, performing a line feed, form feed, or carriage return.
 * <p>
 * This class cannot be instantiated externally and provides a static method
 * {@code getInstance()} to access its single instance.
 */
public class GeneralControlCodes {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GeneralControlCodes.class);

    /**
     * Singleton instance of the {@code GeneralControlCodes} class.
     */
    private static final GeneralControlCodes instance;

    static {
        instance = new GeneralControlCodes();
        if (LOGGER.isDebugEnabled()) LOGGER.debug(instance.toString());
    }

    /**
     * Rings the terminal bell
     * CODE: BELL
     */
    public static final char BELL = 7;
    /**
     * Backspaces one character
     * CODE: BS
     */
    public static final char BS = 8;
    /**
     * Horizontal tab
     * CODE: TAB
     */
    public static final char TAB = 9;
    /**
     * Line feed
     * CODE: LF
     */
    public static final char LF = 10;
    /**
     * Vertical tab
     * CODE: VT
     */
    public static final char VT = 11;
    /**
     * Form feed or new page
     * CODE: FF
     */
    public static final char FF = 12;
    /**
     * Carriage return
     * CODE: CR
     */
    public static final char CR = 13;


    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private GeneralControlCodes() {
    }

    /**
     * Provides access to the singleton instance of the {@code GeneralControlCodes} class.
     * This ensures that only one instance of the class exists throughout the application.
     *
     * @return the singleton instance of the {@code GeneralControlCodes} class
     */
    public static GeneralControlCodes getInstance() {
        return instance;
    }

  /*
  TODO: The methods that moves the cursor has to update the 'cursorPosition' property of this
   class.
   */

    /**
     * Returns the ASCII control code BackSpace (BS)
     *
     * @return the ASCII control code BackSpace (BS)
     */
    public static char backSpace() {
        return BS;
    }


    /**
     * Returns the ASCII control code Tab (TAB)
     *
     * @return the ASCII control code Tab (TAB)
     */
    public static char bell() {
        return BELL;
    }

    /**
     * Returns the ASCII control code Tab (TAB)
     *
     * @return the ASCII control code Tab (TAB)
     */
    public static char tab() {
        return TAB;
    }

    /**
     * Returns the ASCII control code Line Feed (LF)
     *
     * @return the ASCII control code Line Feed (LF)
     */
    public static char linefeed() {
        return LF;
    }

    /**
     * Returns the ASCII control code Vertical Tab (VT)
     *
     * @return the ASCII control code Vertical Tab (VT)
     */
    public static char verticalTab() {
        return VT;
    }

    /**
     * Returns the ASCII control code Form Feed (FF)
     *
     * @return the ASCII control code Form Feed (FF)
     */
    public static char formfeed() {
        return FF;
    }

    /**
     * Returns the ASCII control code Carriage Return (CR)
     * @return the ASCII control code Carriage Return (CR)
     */
    public static char carriagereturn() {
        // In the Windows terminal it moves the cursor to the beginning of the line
        // To do a line break you have to do a linefeed() or use the Java \n escape code
        return CR;
    }

    @Override
    public String toString() {
        return "GeneralControlCodes{'General ASCII control codes'}";
    }
}
