package es.nom.juanfranciscoruiz.ansiterm.app.options;

import com.sun.jna.LastErrorException;
import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.Position;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.ansiterm.codes.CursorStylesCodes;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;

/**
 * Demonstrates how to recover the current cursor position from the terminal.
 *
 * @author Juan F. Ruiz
 */
public class ShowRecoverCursorPosition {
    /**
     * Logger used for tracing and debugging.
     */
    public static final Logger logger = LoggerFactory.getLogger(ShowRecoverCursorPosition.class);
    /**
     * Represents an instance of the {@code ANSITerm} class used for managing
     * terminal interactions, such as cursor blinking or printing text at
     * specific locations on the terminal screen.
     * <p>
     * This object is final and assigned during class construction, ensuring
     * consistent and reliable access throughout the lifecycle of the containing
     * class. It provides methods and utilities for terminal display management.
     */
    private final ANSITerm term;
    /**
     * Stores the title to be displayed as part of the terminal output header.
     * <p>
     * This value represents a concise description or theme that is shown
     * during terminal operations, primarily used in demonstrations such as
     * enabling or disabling cursor blinking. It serves to provide context for
     * the displayed content within the terminal.
     */
    private final String title;
    /**
     * The message to be displayed during cursor blinking demonstrations.
     * <p>
     * This variable is used in methods that showcase cursor blinking functionality
     * and is displayed alongside other terminal outputs. The value of this field
     * represents a textual message to guide users or complement the demonstration.
     */
    private String msg;
    /**
     * Defines the delay duration in milliseconds used for controlling the timing
     * of cursor position recovery operations and other related processes.
     */
    public static final Long DELAY = 0L;

    /**
     * Constructs a new RecoverCursorPosition.
     */
    public ShowRecoverCursorPosition() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Cursor position recovery";
        this.msg = "The cursor will move through all positions in the screen buffer. For each position, its row and column will be retrieved. Delay: " + DELAY + " ms";
    }

    /**
     * Performs the cursor position recovery demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        TerminalSize screenSize = term.getTerminalSize();
        clearScreenAndPrintHeader(term, title, msg, screenSize.getColumns());

        term.cursorShow();
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_STEADY_BLOCK_SHAPE);

        // We start on line 6 so as not to delete the header...
        for (int lin = 6; lin < screenSize.getLines() - 3; lin++) {
            for (int col = 1; col <= screenSize.getColumns(); col++) {
                Position p = new Position(1, 1);
                term.printAt("X", lin, col);
                try {
                    p = term.getCursorPosition();
                } catch (LastErrorException e) {
                    logger.error(String.valueOf(e.getErrorCode()));
                    System.out.println(e.getErrorCode());
                    System.out.println(e.getMessage());
                }
                long retardo = DELAY;
                if (retardo > 0)
                    pauseForMilliseconds(retardo);
                term.printAt("Cursor position: column : ", screenSize.getLines() - 2, 1);
                term.eraseFromCursorToEndLine();
                term.printAt(p.getCol() + ", row: " + p.getLin(), screenSize.getLines() - 2, 32);
            }
        }
        pauseWithMessage(0, "Press <ENTER> to return to the menu");
    }
}
