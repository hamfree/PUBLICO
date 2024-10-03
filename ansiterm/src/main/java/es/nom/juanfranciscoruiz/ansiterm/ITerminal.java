package es.nom.juanfranciscoruiz.ansiterm;

import com.sun.jna.platform.win32.Kernel32;

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
     */
    TerminalSize getTerminalSize();
}
