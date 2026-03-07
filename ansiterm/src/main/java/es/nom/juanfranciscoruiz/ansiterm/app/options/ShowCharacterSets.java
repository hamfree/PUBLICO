package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import es.nom.juanfranciscoruiz.ansiterm.model.DrawChars;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;
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
        String message = "Shows the different character sets available in the terminal.";
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
        StringBuilder line1 =
                new StringBuilder(String.valueOf(DrawChars.LU_CORNER));
        String repeat = String.valueOf(DrawChars.HL).repeat(Math.max(0, col - 1));
        line1.append(repeat);
        line1.append(DrawChars.RU_CORNER);
        StringBuilder line2 =
                new StringBuilder(String.valueOf(DrawChars.VL));
        line2.append(" ".repeat(Math.max(0, col - 1)));
        line2.append(DrawChars.VL);
        StringBuilder line3 =
                new StringBuilder(String.valueOf(DrawChars.LD_CORNER));
        line3.append(repeat);
        line3.append(DrawChars.RD_CORNER);
        term.printAt(line1.toString(), 10, 1);
        term.printAt(line2.toString(), 11, 1);
        term.printAt(line3.toString(), 12, 1);

        // Now we print the chars 32 to 255 for the DEC character set.
        showChars(14);

        // We need to switch to the ASCII character set to display a readable message :-D
        term.setASCIICharacterSet();
        msg = "(2/2) - Changing the character set to ASCII Characters.";
        pauseWithMessage(0, null);
        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        line1 = new StringBuilder("This is the normal");
        line2 = new StringBuilder("ASCII characters");
        line3 = new StringBuilder("set.");
        term.printAt(line1.toString(), 10, (term.getTerminalSize().getColumns() - line1.length())/2);
        term.printAt(line2.toString(), 11, (term.getTerminalSize().getColumns() - line2.length())/2);
        term.printAt(line3.toString(), 12, (term.getTerminalSize().getColumns() - line3.length())/2);

        // Now we print the chars 32 to 255 for the ASCII character set.
        showChars(14);
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

    private void showChars(int line) throws ANSITermException {
        int col = 0;
        for (int i = 32; i < 256; i++){
            if (col < term.getTerminalSize().getColumns() - 1) {
                col += 2;
            } else {
                line++;
                col = 0;
            }
            term.printAt(" ", line, col);
            term.printAt(String.valueOf((char)i), line, col);
        }
    }
}
