package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Interface for the low-level methods of a terminal.
 * 
 * @author juanf
 */
public interface ITerminal {

    /**
     * Enable the terminal's 'raw' mode
     */
    void enableRawMode();

    /**
     * Disable the terminal's 'raw' mode
     */
    void disableRawMode();

    /**
     * Returns the current size of the terminal
     * @return a TerminalSize object with the lines and columns of the current
     * terminal
     * 
     * @see es.nom.juanfranciscoruiz.ansiterm.TerminalSize
     */
    TerminalSize getTerminalSize();
}
