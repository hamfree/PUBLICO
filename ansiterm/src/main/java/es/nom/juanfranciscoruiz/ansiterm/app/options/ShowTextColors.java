package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.model.BGColor;
import es.nom.juanfranciscoruiz.ansiterm.model.Color;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.clearScreenAndPrintHeader;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * Demonstrates various text and background colors.
 *
 * @author Juan F. Ruiz
 */
public class ShowTextColors {
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
     * Constructs a new ShowTextColors.
     */
    public ShowTextColors() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Colors applicable to the text and background";
        this.message = "Multiple styles will be applied to the text at once.";
        this.columns = term.getTerminalSize().getColumns();
    }

    /**
     * Performs the text colors demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        showColorsForForeground();
        showColorsForBackground();
    }

    /**
     * Displays a demonstration of various foreground and background color combinations
     * using ANSI color codes in the terminal. The method prints a list of text phrases
     * styled with different foreground and background colors.
     * <p>
     * The demonstration begins by clearing the screen and printing a header defined
     * by a title and a message. Phrases styled with specific foreground colors are printed
     * first, followed by phrases styled with specific background colors.
     * <p>
     * Foreground colors demonstrated include:
     * <ul>
     * <li>Yellow</li>
     * <li>Blue</li>
     * <li>White (default)</li>
     * <li>Cyan</li>
     * <li>Magenta</li>
     * <li>Black</li>
     * <li>Red</li>
     * <li>Green</li>
     * </ul>
     *
     * Background colors demonstrated include:
     * <ul>
     * <li>Yellow</li>
     * <li>Blue</li>
     * <li>White</li>
     * <li>Cyan</li>
     * <li>Magenta</li>
     * <li>Black</li>
     * <li>Red</li>
     *
     * This method integrates terminal interactions facilitated by the ANSITerm object
     * and manages the sequence and positioning of the color demonstrations.
     *
     * @throws Exception If an error occurs during execution, such as invalid terminal
     *                   arguments or issues with ANSI formatting.
     */
    void showColorsForForeground() throws Exception {
        clearScreenAndPrintHeader(term, title, message, columns);
        line = 7;
        term.printAt(term.setItalic("Colors in the foreground"), line, 1);
        term.printAt(term.setColor(Color.YELLOW, "Phrase in yellow"), ++line, 1);
        term.printAt(term.setColor(Color.BLUE, "Phrase in blue"), ++line, 1);
        //It's completely normal that nothing is displayed unless you select something with the cursor...
        term.printAt(term.setColor(Color.WHITE, "White phrase"), ++line, 1);
        term.printAt(term.setColor(Color.CYAN, "Phrase in cyan"), ++line, 1);
        term.printAt(term.setColor(Color.MAGENTA, "Phrase in magenta"), ++line, 1);
        term.printAt(term.setColor(Color.BLACK, "Phrase in black"), ++line, 1);
        term.printAt(term.setColor(Color.RED, "Phrase in red"), ++line, 1);
        term.printAt(term.setColor(Color.GREEN, "Phrase in green"), ++line, 1);
        line += 2;
        term.printAt(term.setItalic("Background colors"), line, 1);
        term.printAt(term.setBackgroundColor(BGColor.YELLOW, "Phrase with a yellow background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.BLUE, "Phrase with a blue background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.WHITE, "Phrase with a white background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.CYAN, "Phrase with cyan background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.MAGENTA, "Phrase with a magenta background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.BLACK, "Phrase with a black background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.RED, "Phrase with a red background"), ++line, 1);
        pauseWithMessage(0, null);
    }

    /**
     * Displays a demonstration of various foreground and background color combinations
     * using ANSI color codes in the terminal. The method showcases a list of styled
     * text phrases with distinct foreground and background color combinations.
     * <p>
     * The demonstration sequence includes the following steps:
     * - The screen is cleared, and a header with a title and a message is displayed.
     * - Bright foreground colors are demonstrated first by printing phrases styled
     *   in the following colors:
     *   <ul>
     *   <li>Glossy Yellow</li>
     *   <li>Glossy Blue</li>
     *   <li>Glossy White (default)</li>
     *   <li>Glossy Cyan</li>
     *   <li>Glossy Magenta</li>
     *   <li>Glossy Black</li>
     *   <li>Glossy Red</li>
     *   <li>Glossy Green</li>
     *   </ul>
     * - Color displays are printed systematically across different terminal lines for
     *   clear visibility.
     * - The user is prompted to press ENTER to return to the menu, allowing time to
     *   view the results before proceeding.
     * <p>
     * This method utilizes terminal interactions through an ANSITerm instance to set
     * color styles, print formatted text, and manage cursor positioning. The method
     * ensures proper sequencing and formatting for consistent and visually appealing
     * demonstrations.
     *
     * @throws Exception If an error occurs during execution or terminal interactions,
     *                   such as invalid arguments or issues with ANSI color formatting.
     */
    void showColorsForBackground() throws Exception {
        clearScreenAndPrintHeader(term, title, message, columns);
        line = 7;
        term.printAt(term.setItalic("Bright colors in the foreground"), line, 1);
        term.printAt(term.setColor(Color.GLOSSY_YELLOW, "Phrase in glossy yellow"), ++line, 1);
        term.printAt(term.setColor(Color.GLOSSY_BLUE, "Phrase in glossy blue"), ++line, 1);
        term.printAt(term.setColor(Color.GLOSSY_WHITE, "Glossy Blank phrase"), ++line, 1);
        term.printAt(term.setColor(Color.GLOSSY_CYAN, "Phrase in glossy cyan"), ++line, 1);
        term.printAt(term.setColor(Color.GLOSSY_MAGENTA, "Phrase in glossy magenta"), ++line, 1);
        term.printAt(term.setColor(Color.GLOSSY_BLACK, "Phrase in glossy black"), ++line, 1);
        term.printAt(term.setColor(Color.GLOSSY_RED, "Phrase in glossy red"), ++line, 1);
        term.printAt(term.setColor(Color.GLOSSY_GREEN, "Phrase in glossy green"), ++line, 1);
        line += 2;
        term.printAt(term.setItalic("Bright colors for the background"), line, 1);
        term.printAt(term.setBackgroundColor(BGColor.GLOSSY_YELLOW, "Phrase in glossy yellow background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.GLOSSY_BLUE, "Phrase in glossy blue background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.GLOSSY_WHITE, "Glossy Blank phrase background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.GLOSSY_CYAN, "Phrase in glossy cyan background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.GLOSSY_MAGENTA, "Phrase in glossy magenta background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.GLOSSY_BLACK, "Phrase in glossy black background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.GLOSSY_RED, "Phrase in glossy red background"), ++line, 1);
        term.printAt(term.setBackgroundColor(BGColor.GLOSSY_GREEN, "Phrase in glossy green background"), ++line, 1);

        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }
}
