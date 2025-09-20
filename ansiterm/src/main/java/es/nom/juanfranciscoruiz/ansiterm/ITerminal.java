package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Interfaz para  los métodos de bajo nivel de un terminal.
 * 
 * @author juanf
 */
public interface ITerminal {

    /**
     * Habilita el modo 'raw' del terminal
     */
    void enableRawMode();

    /**
     * Deshabilita el modo 'raw' del terminal
     */
    void disableRawMode();

    /**
     * Devuelve el tamaño actual del terminal
     * @return un objeto TerminalSize con las líneas y columnas del terminal 
     * actual
     * 
     * @see es.nom.juanfranciscoruiz.ansiterm.TerminalSize
     */
    TerminalSize getTerminalSize();
}
