package es.nom.juanfranciscoruiz.ansiterm.app.options;

import com.sun.jna.LastErrorException;
import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.clearScreenAndPrintHeader;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * Demonstrates the use of raw terminal mode for keyboard input.
 *
 * @author Juan F. Ruiz
 */
public class ShowRawMode {
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
     * Constructs a new RawMode.
     */
    public ShowRawMode() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Raw console mode test";
        this.message = "Sets the keyboard of console to RAW mode. Each keystroke generates a keyboard response in the form of code.";
        this.columns = term.getTerminalSize().getColumns();
    }

    /**
     * Logger used for tracing and debugging.
     */
    public static final Logger logger = LoggerFactory.getLogger(ShowRawMode.class);

    /**
     * Performs the raw mode demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        clearScreenAndPrintHeader(term,title,message,columns);

        try {
            pauseWithMessage(2000L, null);
            term.getOsCall().enableRawMode();

            term.printAt("Press keys. Press 'q' to exit.", 6, 1);
            term.printAt(7, 1);

            while (true) {
                int resp = System.in.read();
                char ch = (char) resp;
                System.out.println(resp + ", char = " + ch);
                if (ch == 'q') {
                    break;
                }
            }
        } catch (LastErrorException e) {
            logger.error(String.valueOf(e.getErrorCode()));
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
            System.exit(-1);
        } finally {
            // It is *HIGHLY* recommended to return to normal keyboard mode.
            term.getOsCall().disableRawMode();
        }
    }
}
