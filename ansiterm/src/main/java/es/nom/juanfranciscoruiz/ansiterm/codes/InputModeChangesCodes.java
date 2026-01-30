package es.nom.juanfranciscoruiz.ansiterm.codes;


import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains ANSI escape codes for input mode changes.<br><br>
 *
 * These are sequences that control the input modes. There are two different sets of input modes, the 'Cursor Keys Mode'
 * and the 'Keypad Keys Mode'. The 'Cursor Keys Mode' controls the sequences that are emitted by the arrow keys as well as
 * the Home and End, while the 'Keypad Keys Mode' controls the sequences emitted by the keys on the numpad primarily, as
 * well as the function keys.<br><br>
 *
 * Each of these modes are simple boolean settings – the 'Cursor Keys Mode' is either 'Normal' (default) or
 * 'Application', and the 'Keypad Keys Mode' is either 'Numeric' (default) or 'Application'.<br><br>
 *
 * See the Cursor Keys and Numpad & Function Keys sections for the sequences emitted in these modes. <-- Ojo con esto
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
    private InputModeChangesCodes() {}

}
