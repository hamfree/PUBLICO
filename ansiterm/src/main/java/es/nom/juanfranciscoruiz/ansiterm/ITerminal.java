package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Interface for low-level terminal methods.
 * 
 * @author juanf
 */
public interface ITerminal {

    /**
     * Enables the 'raw' mode of the terminal
     */
    void enableRawMode();

    /**
     * Disables the 'raw' mode of the terminal
     */
    void disableRawMode();

    /**
     * Returns the current size of the terminal
     * @return a TerminalSize object with the lines and columns of the 
     * current terminal
     * 
     * @see es.nom.juanfranciscoruiz.ansiterm.TerminalSize
     */
    TerminalSize getTerminalSize();
}
