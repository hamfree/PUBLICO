package es.nom.juanfranciscoruiz.ansiterm.app.options;

import java.util.concurrent.ThreadLocalRandom;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import es.nom.juanfranciscoruiz.ansiterm.model.BGColor;
import es.nom.juanfranciscoruiz.ansiterm.model.Color;
import org.slf4j.Logger;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;

/**
 * The {@code ShowScrollingMargins} class demonstrates functionality related to
 * scrolling margins in terminal-based environments. Its primary focus is to
 * serve as a structure for showcasing terminal interactions using the
 * {@code ANSITerm} utility class.
 * <p>
 * This class contains methods and placeholders for demonstrating the use of terminal
 * operations, such as clearing the terminal, outputting styled messages,
 * and pausing for user interaction.
 */
public class ShowScrollingMargins {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ShowScrollingMargins.class);

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
     * Represents the primary text message managed by the ShowScrollingMargins class.
     * This message is used as a content reference for various operations, including
     * styling demonstrations and terminal-based displays.
     */
    private String message;

    /**
     * The string with the title of this demo class
     */
    private String title;

    /**
     * Represents the delay time in milliseconds used for pausing operations or
     * introducing timed intervals within the application.
     * This value can be utilized for implementing pauses in terminal interactions
     * or other functionalities requiring a noticeable delay.
     */
    public static final long DELAY = 1000L;
    /**
     * Represents a short delay interval defined in milliseconds.
     * This constant is used to introduce brief pauses or delays
     * during program execution, often for user interface feedback
     * or timing control in terminal-based applications.
     */
    public static final long SHORTDELAY = 100L;


    /**
     * Constructs a new instance of the {@code ShowScrollingMargins} class.
     * This constructor initializes the fields {@code term}, {@code title}, and {@code message},
     * setting them to predefined default values. The {@code term} field is initialized
     * with an instance of {@code ANSITerm} for managing terminal interactions.
     *
     * @throws ANSITermException if an error occurs during the initialization of the {@code ANSITerm} instance.
     */
    public ShowScrollingMargins() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Scrolling margins";
        this.message = "Shows the use of scrolling margins";
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
        int heightScreen = term.getTerminalSize().getLines();
        int widthScreen = term.getTerminalSize().getColumns();
        this.message = "Shows the use of scrolling margins in a terminal of " + widthScreen + " columns and " + heightScreen + " lines. Delay: " + SHORTDELAY + " ms";
        clearScreenAndPrintHeader(term, title, message, widthScreen);

        // 1. Draw background text to demonstrate that it cannot be erased
        for (int line = 6; line < heightScreen - 1; line++) {
            for (int col = 1; col < widthScreen - 1; col++) {
                char character = generateRandomCharacter();
                term.printAt(String.valueOf(character), line, col);
            }
        }

        // 2. Draw the contents of our "rectangle" centered in the screen
        int widthRectangle = widthScreen / 3;
        int heightRectangle = heightScreen / 4;
        int topRectangle = (heightScreen - heightRectangle) / 2;
        int leftRectangle = (widthScreen - widthRectangle) / 2;

        String coordinates = "top=" + topRectangle + ", left=" + leftRectangle + ", " + "width=" + widthRectangle + ", height=" + heightRectangle;
        pauseWithMessage(0, "Press <ENTER> to draw the 'rectangle' at " + coordinates + "...");

        drawRectangleWithNumberInside(topRectangle,leftRectangle,widthRectangle,heightRectangle);
        pauseWithMessage(0, "Press <ENTER> to scroll UP...");

        // We scroll up inside the rectangle (height lines up with a delay)
        for (int i = 0; i < heightRectangle; i++){
            term.scrollUp(topRectangle, leftRectangle, widthRectangle, heightRectangle, 1);
            pauseForMilliseconds(SHORTDELAY);
        }
        pauseWithMessage(DELAY, "In one second the rectangle redraws!");
        drawRectangleWithNumberInside(topRectangle,leftRectangle,widthRectangle,heightRectangle);
        pauseWithMessage(0, "Press <ENTER> to scroll DOWN...");

        // We scroll down (height lines down with a delay of 250 milliseconds)
        for (int i = 0; i < heightRectangle; i++){
            term.scrollDown(topRectangle, leftRectangle, widthRectangle, heightRectangle, 1);
            pauseForMilliseconds(SHORTDELAY);
        }
        pauseWithMessage(DELAY, "In one second the rectangle redraws!");
        drawRectangleWithNumberInside(topRectangle,leftRectangle,widthRectangle,heightRectangle);
        pauseWithMessage(0, "Press <ENTER> to scroll LEFT...");

        // We scroll to the left (5 characters move)
        for (int i = 0; i < widthRectangle; i++) {
            term.scrollLeft(topRectangle, leftRectangle, widthRectangle, heightRectangle, 1);
            pauseForMilliseconds(SHORTDELAY);
        }
        pauseWithMessage(DELAY, "In one second the rectangle redraws!");
        drawRectangleWithNumberInside(topRectangle,leftRectangle,widthRectangle,heightRectangle);
        pauseWithMessage(0, "Press <ENTER> to scroll RIGHT...");

        // We scroll to the right (8 characters move)
        for (int i = 0; i < widthRectangle; i++) {
            term.scrollRight(topRectangle, leftRectangle, widthRectangle, heightRectangle, 1);
            pauseForMilliseconds(SHORTDELAY);
        }
        pauseWithMessage(0, "Demonstration completed. The background remained intact. Press <ENTER> to return to menu");
    }


    /**
     * Generates a random character within the specified ASCII range.
     * The character is selected from the range of ASCII values [32, 256),
     * which includes printable characters and extended ASCII values.
     *
     * @return A randomly generated character within the ASCII range [32, 256).
     */
    private char generateRandomCharacter() {
        return (char) ThreadLocalRandom.current().nextInt(32, 88);
    }

    /**
     * Draws a rectangle on the terminal, filling it with sequential numbers from 0 to 9,
     * restarting at 0 when 9 is exceeded. The rectangle is drawn with the top-left corner
     * at the specified position and with the specified width and height. Numbers within
     * the rectangle are styled with white foreground color and blue background color.
     *
     * @param topRectangle    The row position of the top edge of the rectangle.
     * @param leftRectangle   The column position of the left edge of the rectangle.
     * @param widthRectangle  The width of the rectangle in number of columns.
     * @param heightRectangle The height of the rectangle in number of rows.
     * @throws ANSITermException If an error occurs during terminal operations, such as
     *                           invalid coloring parameters or output issues.
     */
    private void drawRectangleWithNumberInside(int topRectangle, int leftRectangle, int widthRectangle, int heightRectangle) throws ANSITermException {
        int contador = 0;
        for (int line = topRectangle; line < topRectangle + heightRectangle; line++) {
            for (int col = leftRectangle; col < leftRectangle + widthRectangle; col++) {
                String character = String.valueOf(contador);
                character = term.setColors(Color.WHITE, BGColor.BLUE, character);
                term.printAt(character, line, col);
                contador++;
                if (contador > 9) contador = 0;
            }
        }
    }
}
