package es.nom.juanfranciscoruiz.ansiterm.codes;

/**
 * Provides constants for Operating System Command (OSC) escape sequences.
 * OSC escape sequences are part of the ANSI escape codes and are used to control
 * terminal functions, such as setting window titles or clipboard management.
 */
public class OSC {
    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private OSC() {
    }

    /**
     * Operating System Command (OSC) escape character.
     */
    public static final String OSC = "\033]";

    /**
     * String Terminator (ST) escape sequence.
     */
    public static final String ST = "\033\\";

    /**
     * Bell (BEL) character, often used as a terminator for OSC sequences.
     */
    public static final String BEL = "\007";
}
