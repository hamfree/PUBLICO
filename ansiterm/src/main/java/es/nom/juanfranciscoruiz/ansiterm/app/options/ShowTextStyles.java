package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.clearScreenAndPrintHeader;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * Demonstrates various text styles like bold, italic, etc.
 *
 * @author Juan F. Ruiz
 */
public class ShowTextStyles {
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
     * Represents the title text for the MultipleStylesText object.
     * This field is used to store a string that acts as the title,
     * often styled and displayed prominently in terminal-based
     * demonstrations or other text-based UI elements of the class.
     */
    private final String title;
    /**
     * Represents the primary text message managed by the MultipleStylesText class.
     * This message is used as a content reference for various operations, including
     * styling demonstrations and terminal-based displays.
     */
    private final String message;
    /**
     * Represents the number of columns used to format and display text or other content
     * within the terminal. This variable determines the width of the area allocated
     * for various visual styles and text formatting operations.
     */
    private final int columns;
    /**
     * Represents the current line number or position within the context of the
     * {@code MultipleStylesText} class. This variable is typically used to track
     * or indicate which line or portion of the text is being operated on during
     * styling or display processes.
     */
    private int line;
    /**
     * Constructs a new TextStyles.
     */
    public ShowTextStyles() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Text styles";
        this.message = "The text can be set to bold, italic, underline, inverted, blinking, crossed, etc.";
        this.columns = term.getTerminalSize().getColumns();
    }

    /**
     * Performs the text styles demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        clearScreenAndPrintHeader(term, title, message, columns);
        term.printAt(term.setBold("Bold phrase"), 6, 10);
        term.printAt(term.setDim("Attenuated phrase"), 7, 10);
        term.printAt(term.setItalic("Phrase in italics"), 8, 10);
        term.printAt(term.setBlink("Intermittent phrase"), 9, 10);
        term.printAt(term.setInverse("Phrase with inverted colors"), 10, 10);
        term.printAt(term.setHidden("Hidden message"), 11, 10);
        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }
}
