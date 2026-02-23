package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import es.nom.juanfranciscoruiz.utiles.UnclosableInputStreamDecorator;

import java.util.Scanner;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.clearScreenAndPrintHeader;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * Demonstrates how to get the current terminal screen size.
 *
 * @author Juan F. Ruiz
 */
public class ShowScreenSize {
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
     * Constructs a new ScreenSize.
     */
    public ShowScreenSize() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Screen size";
        this.msg = "Try resizing the terminal window, then press ENTER. The terminal size will be displayed.";
    }
    /**
     * Performs the screen size demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        String resp = "";
        while (!resp.equals("q")) {
            TerminalSize screenSize = term.getTerminalSize();
            clearScreenAndPrintHeader(term, title, msg, screenSize.getColumns());
            term.printAt("> : ", 6, 1);
            Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in));
            resp = sc.nextLine();
            TerminalSize ts = term.getOsCall().getTerminalSize();
            msg = "The screen size is:%d lines and %d columns.".formatted(ts.getLines(), ts.getColumns());
            term.printAt(msg, ts.getLines() - 2, 1);
            pauseWithMessage(0, null);
        }
    }
}
