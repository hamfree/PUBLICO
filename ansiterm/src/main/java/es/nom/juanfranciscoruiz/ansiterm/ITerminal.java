package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Interface for low-level terminal methods.
 * @author Juan F. Ruiz
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

    /**
     * Thread launched in background that monitors the user console resizes to reflect changes in the terminal's size.
     * This method should be invoked whenever the terminal dimensions are altered,
     * ensuring that the console monitor dynamically adjusts to the new width
     * and height of the terminal.
     */
    void resizeConsoleMonitor();
}
