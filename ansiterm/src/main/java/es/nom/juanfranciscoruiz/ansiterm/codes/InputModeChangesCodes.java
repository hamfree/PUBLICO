package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains ANSI escape codes for input mode changes.<br><br>
 * <p>
 * These are sequences that control the input modes. There are two different
 * sets of input modes, the 'Cursor Keys Mode'  and the 'Keypad Keys Mode'. The
 * 'Cursor Keys Mode' controls the sequences that are emitted by the arrow keys
 * as well as the Home and End, while the 'Keypad Keys Mode' controls the
 * sequences emitted by the keys on the numpad primarily, as well as the
 * function keys.<br><br>
 * <p>
 * Each of these modes are simple boolean settings – the 'Cursor Keys Mode' is
 * either 'Normal' (default) or 'Application', and the 'Keypad Keys Mode' is
 * either 'Numeric' (default) or 'Application'.<br><br>
 * <p>
 * See the Cursor Keys and Numpad &amp; Function Keys sections for the sequences
 * that are emitted in these modes.
 *
 * @author Juan F. Ruiz
 */

 /*
TODO: It will probably be necessary to define the constants of the codes that
 return the key presses in the different input modes
 */
public class InputModeChangesCodes {
    /**
     * Sequence.......: ESC =
     * Code...........: DECKPAM
     * Description....: Enables Keypad Application Mode.
     * Behavior.......: Keypad keys will emit their Application Mode sequences.
     */
    public static final String IM_ENABLE_KEYPAD_APP_MODE = ESC + "=";

    /**
     * Sequence.......: ESC >
     * Code...........: DECKPNM
     * Description....: Enables Keypad Numeric Mode.
     * Behavior.......: Keypad keys will emit their Numeric Mode sequences.
     */
    public static final String IM_ENABLE_KEYPAD_NUM_MODE = ESC + ">";

    /**
     * Sequence.......: ESC [ ? 1 h
     * Code...........: DECCKM
     * Description....: Enables Cursor Keys Application Mode.
     * Behavior.......: Keypad keys will emit their Application Mode sequences.
     */
    public static final String IM_ENABLE_CURSOR_KEYS_APP_MODE = ESC + "[?1h";

    /**
     * Sequence.......: ESC [ ? 1 l
     * Code...........: DECCKM
     * Description....: Disables Cursor Keys Application Mode (use Normal Mode).
     * Behavior.......: Keypad keys will emit their Numeric Mode sequences.
     */
    public static final String IM_DISABLE_CURSOR_KEYS_APP_MODE = ESC + "[?1l";

    /**
     * Private constructor to prevent instantiation.
     */
    private InputModeChangesCodes() {
    }

    /**
     * Retrieves the ANSI escape code to enable Keypad Application Mode.
     * <p>
     * In Keypad Application Mode, the keys on the numeric keypad will emit their
     * Application Mode sequences instead of their default Numeric Mode sequences.
     *
     * @return a {@code String} representing the ANSI escape code for enabling
     * Keypad Application Mode.
     */
    public static String enableKeypadApplicationMode() {
        return IM_ENABLE_KEYPAD_APP_MODE;
    }

    /**
     * Retrieves the ANSI escape code to enable Keypad Numeric Mode.
     * <p>
     * In Keypad Numeric Mode, the keys on the numeric keypad will emit their
     * Numeric Mode sequences instead of their default Application Mode sequences.
     *
     * @return a {@code String} representing the ANSI escape code for enabling
     * Keypad Numeric Mode.
     */
    public static String enableKeypadNumericMode() {
        return IM_ENABLE_KEYPAD_NUM_MODE;
    }

    /**
     * Retrieves the ANSI escape code to enable Cursor Keys Application Mode.
     * <p>
     * In Cursor Keys Application Mode, the cursor keys will emit their
     * Application Mode sequences instead of their default Normal Mode sequences.
     *
     * @return a {@code String} representing the ANSI escape code for enabling
     * Cursor Keys Application Mode.
     */
    public static String enableCursorKeysApplicationMode() {
        return IM_ENABLE_CURSOR_KEYS_APP_MODE;
    }

    /**
     * Retrieves the ANSI escape code to disable Cursor Keys Application Mode.
     * <p>
     * In Cursor Keys Normal Mode, the cursor keys will emit their
     * Normal Mode sequences instead of their default Application Mode sequences.
     *
     * @return a {@code String} representing the ANSI escape code for disabling
     * Cursor Keys Application Mode.
     */
    public static String disableCursorKeysAppMode() {
        return IM_DISABLE_CURSOR_KEYS_APP_MODE;
    }

    @Override
    public String toString() {
        return "InputModeChangesCodes{'ANSI escape codes for input mode changes'}";
    }
}
