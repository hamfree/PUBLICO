package es.nom.juanfranciscoruiz.ansiterm.codes;

import org.slf4j.Logger;

/**
 * Provides constants for Operating System Command (OSC) escape sequences.
 * OSC escape sequences are part of the ANSI escape codes and are used to control
 * terminal functions, such as setting window titles or clipboard management.
 */
public class OSC {
    /**
     * For logging
     */
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(OSC.class);
    
    /**
     * Singleton instance of the {@code OSC} class.
     */
    private static final OSC instance;

    static {
        instance = new OSC();
        if (LOGGER.isDebugEnabled()) LOGGER.debug(instance.toString());
    }
    
    /**
     * Private constructor. Class can't be instantiated by the user
     */
    private OSC() {}
    
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
