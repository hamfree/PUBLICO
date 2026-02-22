package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.utiles.UnclosableInputStreamDecorator;

import java.util.Scanner;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * Demonstrates how to get the current terminal screen size.
 *
 * @author Juan F. Ruiz
 */
public class ScreenSize {

    /**
     * Constructs a new ScreenSize.
     */
    public ScreenSize() {
    }

    /**
     * Performs the screen size demonstration.
     *
     * @param term The ANSITerm object to use.
     * @throws Exception If an error occurs during execution.
     */
    public void perform(ANSITerm term) throws Exception {
        String msg;
        String resp = "";
        while (!resp.equals("q")) {
            term.clearTerminal();
            term.moveCursorToBegin();
            term.printAt("------------ Screen size ------------", 1, 1);
            term.printAt("Try resizing the terminal window.", 2, 1);
            term.printAt("Then press ENTER. The terminal size will be displayed..", 3, 1);
            term.printAt("Press q and then ENTER to return to the menu", 4, 1);
            term.printAt("> : ", 5, 1);
            Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in));
            resp = sc.nextLine();
            TerminalSize ts = term.getOsCall().getTerminalSize();
            msg = "The screen size is:%d lines and %d columns.".formatted(ts.getLines(), ts.getColumns());
            term.printAt(msg, ts.getLines() - 2, 1);
            pauseWithMessage(0, null);
        }
    }
}
