package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import es.nom.juanfranciscoruiz.ansiterm.model.DrawChars;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;
import static es.nom.juanfranciscoruiz.utiles.IO.line;
import static es.nom.juanfranciscoruiz.utiles.Util.pause;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.prtln;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.title;

/**
 * The ShowCharacterSets class is designed to manage terminal interactions
 * and demonstrates character sets or related functionality, although the core
 * functionality is not yet implemented. It utilizes the ANSITerm class to perform
 * terminal-based tasks, such as clearing the terminal, moving the cursor, and
 * displaying styled output.
 */
public class ShowCharacterSets {
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
     * Represents the title text for the ShowCharacterSets object.
     * This field is used to store a string that acts as the title,
     * often styled and displayed prominently in terminal-based
     * demonstrations or other text-based UI elements of the class.
     */
    private final String title;
    /**
     * Represents the primary text message managed by the ShowCharacterSets class.
     * This message is used as a content reference for various operations, including
     * styling demonstrations and terminal-based displays.
     */
    private String message;
    /**
     * Represents a fixed delay duration in milliseconds used for pausing or waiting
     */
    public static final long DELAY = 3000L;
    
    /**
     * Constructs a new instance of the {@code ShowCharacterSets} class.
     * This constructor initializes the fields {@code term}, {@code title}, and {@code message},
     * setting them to predefined default values. The {@code term} field is initialized
     * with an instance of {@code ANSITerm} for managing terminal interactions.
     *
     * @throws ANSITermException if an error occurs during the initialization of the {@code ANSITerm} instance.
     */
    public ShowCharacterSets() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Characters sets";
        this.message = "Shows the different character sets available in the terminal.";
    }

    /**
     * Executes the primary behavior associated with the {@code perform} method of the
     * {@code ShowCharacterSets} class. This method indicates that the requested
     * functionality has not yet been implemented.
     * <p>
     * The method utilizes the {@code notImplementedYet} utility function to display
     * an informational message on the terminal, providing the user with feedback
     * on the current status of the functionality. It clears the terminal, outputs the
     * title and message, and pauses briefly to ensure the user can see the notification.
     *
     * @throws Exception If an error occurs during the terminal operations performed
     *                   by the {@code notImplementedYet} method.
     */
    public void perform() throws Exception {
        String msg = "(1/2) - Changing the character set to DEC Drawing Characters.";
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        term.setDECCharacterSet();
        
        int col = term.getTerminalSize().getColumns() - 1;
        
        // We are going to draw a box with the DEC Drawing Characters...
        String line1 =
            String.valueOf(DrawChars.LU_CORNER);
        for (int i = 1; i < col; i++) {
            line1 = line1 + String.valueOf(DrawChars.HL);
        }
        line1 = line1 + String.valueOf(DrawChars.RU_CORNER);
        String line2 =
            String.valueOf(DrawChars.VL);
        for (int i = 1; i < col; i++) {
            line2 = line2 + String.valueOf(" ");
        }
        line2 = line2 + String.valueOf(DrawChars.VL);
        String line3 =
            String.valueOf(DrawChars.LD_CORNER);
        for (int i = 1; i < col; i++) {
            line3 = line3 + String.valueOf(DrawChars.HL);
        }
        line3 = line3 + String.valueOf(DrawChars.RD_CORNER);
        term.printAt(line1, 10, 1);
        term.printAt(line2, 11, 1);
        term.printAt(line3, 12, 1);
        
        // We need to switch to the ASCII character set to display a readable message :-D
        term.setASCIICharacterSet();
        msg = "(2/2) - Changing the character set to ASCII Characters.";
        pauseWithMessage(0, null);
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        line1 = "This is the normal";
        line2 = "ASCII characters";
        line3 = "set.";
        term.printAt(line1, 10, (term.getTerminalSize().getColumns() - line1.length())/2);
        term.printAt(line2, 11, (term.getTerminalSize().getColumns() - line2.length())/2);
        term.printAt(line3, 12, (term.getTerminalSize().getColumns() - line3.length())/2);
        pauseWithMessage(0, null);
    }

    /**
     * Placeholder method indicating functionality that has not been implemented yet.
     * The terminal is cleared, and an informational message is displayed.
     *
     * @param term An instance of ANSITerm used for terminal operations.
     * @param msg  A message to display, indicating the unimplemented status.
     * @throws Exception In case of any error during terminal operations.
     */
    public void notImplementedYet(ANSITerm term, String msg) throws Exception {
        term.clearTerminal();
        term.moveCursorToBegin();
        final long PAUSE_DURATION = 3000L;
        prtln(2, title(msg, '*', 80));
        prtln(3, "This function is not implemented yet.");
        pause(PAUSE_DURATION, null);
    }
}
