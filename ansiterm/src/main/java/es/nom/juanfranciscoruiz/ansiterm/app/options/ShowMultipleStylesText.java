package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;

/**
 * Demonstrates the use of multiple text styles combined.
 *
 * @author Juan F. Ruiz
 */
public class ShowMultipleStylesText {
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
    private String title;
    /**
     * Represents the primary text message managed by the MultipleStylesText class.
     * This message is used as a content reference for various operations, including
     * styling demonstrations and terminal-based displays.
     */
    private String message;
    /**
     * Represents the number of columns used to format and display text or other content
     * within the terminal. This variable determines the width of the area allocated
     * for various visual styles and text formatting operations.
     */
    private int columns;
    /**
     * Represents the current line number or position within the context of the
     * {@code MultipleStylesText} class. This variable is typically used to track
     * or indicate which line or portion of the text is being operated on during
     * styling or display processes.
     */
    private int line;

    /**
     * Constructs a new MultipleStylesText.
     */
    public ShowMultipleStylesText() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Multiple styles applicable to the text";
        this.message = "Multiple styles will be applied to the text at once.";
    }

    /**
     * Retrieves the title associated with this instance.
     *
     * @return The title as a string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title for this instance.
     *
     * @param title The title to be set as a string.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the message associated with this instance.
     *
     * @return The message as a string.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message for this instance.
     *
     * @param message The message to be set as a string.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the number of columns configured for this instance.
     *
     * @return The number of columns as an integer.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Sets the number of columns for this instance.
     * @param columns The number of columns to be set as an integer.
     *
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * Retrieves the current line number.
     *
     * @return The current line number as an integer.
     */
    public int getLine() {
        return line;
    }

    /**
     * Sets the current line number.
     *
     * @param line The line number to be set as an integer.
     */
    public void setLine(int line) {
        this.line = line;
    }

    /**
     * Performs the multiple styles demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        this.setColumns(term.getTerminalSize().getColumns());
        showTextStylesBold();
        showTextStylesAttenuated();
        showTextStylesCursive();
        showTextStylesUnderline();
        showTextStylesIntermittent();
        showTextStylesInverted();
        showTextStylesCrossed();
    }

    /**
     * Displays various text styles in bold, combined with additional styles such as
     * dimmed, italic, underlined, flashing, inverted colors, and struck through.
     * The demonstration involves formatting specific messages and printing them
     * to the terminal using the provided ANSITerm object.
     *
     * @throws Exception If an error occurs during execution, such as issues with terminal
     *                   interaction or styling operations.
     */
    private void showTextStylesBold() throws Exception {
        setLine(6);
        String stylizedMessage = "BOLD phrase with various styles";
        clearScreenAndPrintHeader(term, title, message, columns);
        term.printAt("The following sentence will be in bold and dimmed", line++, 5);
        term.printAt(term.setStyles(true, true, false, false, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be in bold and italics", line++, 5);
        term.printAt(term.setStyles(true, false, true, false, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be in bold and underlined", line++, 5);
        term.printAt(term.setStyles(true, false, false, true, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be in bold and flashing", line++, 5);
        term.printAt(term.setStyles(true, false, false, false, true, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be in bold and with inverted colors.", line++, 5);
        term.printAt(term.setStyles(true, false, false, false, false, true, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be in bold and struck through", line++, 5);
        term.printAt(term.setStyles(true, false, false, false, false, false, true, stylizedMessage), line++, 10);
        pauseWithMessage(0, null);
    }

    /**
     * Displays various attenuated text styles in combination with additional styles
     * such as bold, italic, underlined, intermittent flashing, inverted colors, and
     * struck-through formatting. This method demonstrates how text can be styled and
     * displayed in an attenuated (dimmed) manner using the provided ANSITerm object.
     *
     * @throws Exception If an error occurs during the execution of terminal
     *                   interactions or styling methods.
     */
    private void showTextStylesAttenuated() throws Exception {
        setLine(6);
        String stylizedMessage = "ATTENUATED phrase with various styles";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        term.printAt("The following sentence will be in bold and attenuated.", line++, 5);
        term.printAt(term.setStyles(true, true, false, false, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be attenuated and in italics", line++, 5);
        term.printAt(term.setStyles(false, true, true, false, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be highlighted and underlined", line++, 5);
        term.printAt(term.setStyles(false, true, false, true, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be dimmed and intermittent.", line++, 5);
        term.printAt(term.setStyles(false, true, false, false, true, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be dimmed and with inverted colors.", line++, 5);
        term.printAt(term.setStyles(false, true, false, false, false, true, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be grayed out and crossed out", line++, 5);
        term.printAt(term.setStyles(false, true, false, false, false, false, true, stylizedMessage), line++, 10);
        pauseWithMessage(0, null);
    }

    /**
     * Demonstrates various text styles in cursive (italic) combined with additional styles,
     * such as bold, underlined, blinking, inverted colors, and struck-through text. The method
     * formats specific messages with these combined styles and prints them to the terminal
     * using the provided ANSITerm object.
     *
     * @throws Exception If an error occurs during execution, such as issues with terminal
     *                   interaction or styling operations.
     */
    private void showTextStylesCursive() throws Exception {
        setLine(6);
        String stylizedMessage = "Phrase in CURSIVE with various styles";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        term.printAt("The following sentence will be in italics and attenuated.", line++, 5);
        term.printAt(term.setStyles(false, true, true, false, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be in italics and bold.", line++, 5);
        term.printAt(term.setStyles(true, false, true, false, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be in italics and underlined", line++, 5);
        term.printAt(term.setStyles(false, false, true, true, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be in italics and blinking.", line++, 5);
        term.printAt(term.setStyles(false, false, true, false, true, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be in italics and with inverted colors.", line++, 5);
        term.printAt(term.setStyles(false, false, true, false, false, true, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be in italics and crossed out", line++, 5);
        term.printAt(term.setStyles(false, false, true, false, false, false, true, stylizedMessage), line++, 10);
        pauseWithMessage(0, null);
    }

    /**
     * Demonstrates various underlined text styles combined with additional styles such as bold,
     * italic, highlighted, flashing, inverted colors, and struck-through text. The method formats
     * specific messages with these combined styles and prints them to the terminal using the
     * provided ANSITerm object.
     *
     * @throws Exception If an error occurs during execution, such as issues with terminal
     *                   interaction or styling operations.
     */
    private void showTextStylesUnderline() throws Exception {
        // Underline with other styles
        setLine(6);
        String stylizedMessage = "UNDERLINED phrase with various styles";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        term.printAt("The following sentence will be underlined and in bold.", line++, 5);
        term.printAt(term.setStyles(true, false, false, true, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be underlined and in italics", line++, 5);
        term.printAt(term.setStyles(false, false, true, true, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be underlined and highlighted.", line++, 5);
        term.printAt(term.setStyles(false, true, false, true, false, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be underlined and flashing.", line++, 5);
        term.printAt(term.setStyles(false, false, false, true, true, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be underlined and with inverted colors.", line++, 5);
        term.printAt(term.setStyles(false, false, false, true, false, true, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be underlined and crossed out.", line++, 5);
        term.printAt(term.setStyles(false, false, false, true, false, false, true, stylizedMessage), line++, 10);
        pauseWithMessage(0, null);
    }

    /**
     * Displays various intermittent (blinking) text styles in combination with additional styles
     * such as bold, dimmed, underlined, inverted colors, and struck-through formatting. This method
     * formats specific messages with these combined styles and prints them to the terminal using the
     * provided ANSITerm object.
     *
     * @throws Exception If an error occurs during the execution of terminal interactions or
     *                   styling methods.
     */
    private void showTextStylesIntermittent() throws Exception {
        // Blinking with other styles
        setLine(6);
        String stylizedMessage = "INTERMITTENT phrase with various styles";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        term.printAt("The following sentence will be intermittent and dimmed.", line++, 10);
        term.printAt(term.setStyles(false, true, false, false, true, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be flashing and bold", line++, 5);
        term.printAt(term.setStyles(true, false, false, false, true, false, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be intermittent and underlined", line++, 5);
        term.printAt(term.setStyles(false, false, false, true, true, false, false, stylizedMessage), line++, 10);
        term.printAt("The following phrase will be intermittent and intermittent", line++, 5);
        term.printAt(term.setStyles(false, false, false, false, true, false, false, stylizedMessage), line++, 10);
        term.printAt("The following phrase will be flashing and with inverted colors.", line++, 5);
        term.printAt(term.setStyles(false, false, false, false, true, true, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be intermittent and crossed out", line++, 5);
        term.printAt(term.setStyles(false, false, false, false, true, false, true, stylizedMessage), line++, 10);
        pauseWithMessage(0, null);
    }

    /**
     * Displays various text styles with inverted colors combined with additional styles,
     * such as bold, italic, dimmed, intermittent flashing, and struck-through formatting.
     * The method demonstrates how text can be styled using inverted colors and prints
     * them to the terminal with the provided ANSITerm object.
     *
     * @throws Exception If an error occurs during execution, such as issues with terminal
     *                   interaction or styling operations.
     */
    private void showTextStylesInverted() throws Exception {
        // Inverse with other styles
        setLine(6);
        String stylizedMessage = "INVERTED phrase with various styles";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        term.printAt("The following sentence will be reversed and in bold.", line++, 5);
        term.printAt(term.setStyles(true, false, false, false, false, true, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be inverted and in italics", line++, 5);
        term.printAt(term.setStyles(false, false, true, false, false, true, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be inverted and attenuated", line++, 5);
        term.printAt(term.setStyles(false, true, false, false, false, true, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be inverted and intermittent", line++, 5);
        term.printAt(term.setStyles(false, false, false, false, true, true, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be reversed", line++, 5);
        term.printAt(term.setStyles(false, false, false, false, false, true, false, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be reversed and crossed out", line++, 5);
        term.printAt(term.setStyles(false, false, false, false, false, true, true, stylizedMessage), line++, 10);
        pauseWithMessage(0, null);
    }

    /**
     * Demonstrates various crossed-out (strikethrough) text styles in combination with
     * additional styles such as dimmed, bold, underlined, intermittent flashing, and inverted colors.
     * The method formats specific messages with these combined styles and prints them
     * to the terminal using the provided ANSITerm object.
     *
     * @throws Exception If an error occurs during execution, such as issues with terminal
     *                   interaction or styling operations.
     */
    private void showTextStylesCrossed() throws Exception {
        // Strikethrough with other styles
        setLine(6);
        String stylizedMessage = "Phrase CROSSED out in various styles";
        clearScreenAndPrintHeader(term, title, stylizedMessage, columns);
        term.printAt("The following sentence will be crossed out and dimmed.", line++, 5);
        term.printAt(term.setStyles(false, true, false, false, false, false, true, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be crossed out and in bold.", line++, 5);
        term.printAt(term.setStyles(true, false, false, false, false, false, true, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be crossed out and underlined.", line++, 5);
        term.printAt(term.setStyles(false, false, false, true, false, false, true, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be crossed out and intermittent.", line++, 5);
        term.printAt(term.setStyles(false, false, false, false, true, false, true, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be crossed out and with the colors inverted.", line++, 5);
        term.printAt(term.setStyles(false, false, false, false, false, true, true, stylizedMessage), line++, 10);
        term.printAt("The following sentence will be crossed out", line++, 5);
        term.printAt(term.setStyles(false, false, false, false, false, false, true, stylizedMessage), line++, 10);
        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }
}