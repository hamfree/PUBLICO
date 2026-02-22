package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * Demonstrates the use of 256 colors.
 *
 * @author Juan F. Ruiz
 */
public class ShowTextColors256 {
    /**
     * Constructs a new ShowTextColors256.
     */
    public ShowTextColors256() {
    }

    /**
     * Performs the 256 colors demonstration.
     *
     * @param term The ANSITerm object to use.
     * @throws Exception If an error occurs during execution.
     */
    public void perform(ANSITerm term) throws Exception {
        term.clearTerminal();
        term.moveCursorToBegin();
        term.printAt("------------ Displays the palette of 256 colors ------------", 1, 1);

        int j = 0;
        int line = 2;
        int col = 0;
        term.printAt(line, col);
        for (int i = 0; i < 256; i++) {
            String msg = term.setColor256(i, String.valueOf(i));
            if (j > 15) {
                term.printAt(msg, line, col);
                term.LF();
                j = 0;
                col = 0;
                line = line + 1;
            } else {
                term.printAt(msg.concat(" "), line, col);
                col = col + 5;
                j++;
            }
        }

        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }
}
