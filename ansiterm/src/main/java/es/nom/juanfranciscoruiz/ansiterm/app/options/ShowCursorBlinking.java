package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.codes.CursorStylesCodes;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.printWithDelay;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pauseWithMessage;

/**
 * Demonstrates enabling and disabling cursor blinking.
 *
 * @author Juan F. Ruiz
 */
public class ShowCursorBlinking {
    /**
     * Constructs a new ShowCursorBlinking.
     */
    public ShowCursorBlinking() {
    }

    /**
     * Performs the cursor blinking demonstration.
     *
     * @param term The ANSITerm object to use.
     * @throws Exception If an error occurs during execution.
     */
    public void perform(ANSITerm term) throws Exception {
        term.clearTerminal();
        term.moveCursorToBegin();
        term.cursorShow();
        term.cursorChangeStyle(CursorStylesCodes.CURSOR_STEADY_BLOCK_SHAPE);
        term.printAt("------------ Cursor blinking ------------", 1, 1);
        String msg = "(1/2) - The cursor starts blinking.";
        term.printAt(msg, 2, 1);
        term.cursorBlink();
        printWithDelay(msg, 5, 1, term, 100L);
        pauseWithMessage(0, null);

        msg = "(2/2) - Turn off cursor blinking";
        term.printAt(msg, 2, 1);
        term.cursorNoBlink();
        printWithDelay(msg, 5, 1, term, 100L);
        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }
}
