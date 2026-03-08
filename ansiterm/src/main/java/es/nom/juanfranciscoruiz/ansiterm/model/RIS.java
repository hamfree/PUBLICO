package es.nom.juanfranciscoruiz.ansiterm.model;

/**
 * The RIS (Reset to Initial State) class provides a constant for the RIS ANSI escape sequence.
 * RIS is used to reset a terminal to its initial state, clearing the screen, resetting the cursor,
 * and restoring all settings to their defaults.
 * <p>
 * This class is designed to interact with ANSI escape codes for terminal control and adheres to
 * the definitions provided by the ANSI standard for terminal sequences.
 * <p>
 * See related resources for additional context on ANSI escape codes:
 * - CSI (Control Sequence Introducer)
 * - ANSI terminal sequences
 */
public class RIS {
    /**
     * Represents the ANSI escape sequence for RIS (Reset to Initial State).
     * Using this sequence in a terminal resets the terminal to its default
     * initial state. This includes clearing the screen, resetting the cursor
     * position to the top-left corner, and restoring default settings like
     * colors and modes.
     * <p>
     * In the context of control sequences, RIS is constructed using the ESC
     * character followed by 'c', as per the ANSI specification.
     */
    public static final String RESETTOINITIALSTATE =   CSI.ESC + 'c';

    /**
     * Private constructor. The user can't instantiate class
     */
    private RIS() {}
}
