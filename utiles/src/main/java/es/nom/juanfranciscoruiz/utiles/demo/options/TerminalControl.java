package es.nom.juanfranciscoruiz.utiles.demo.options;

import es.nom.juanfranciscoruiz.utiles.TermCtl;
import es.nom.juanfranciscoruiz.utiles.impl.TermCtlImpl;
import es.nom.juanfranciscoruiz.utiles.model.Dimensions;
import es.nom.juanfranciscoruiz.utiles.model.Using;

import static es.nom.juanfranciscoruiz.utiles.Util.FOREVER;
import static es.nom.juanfranciscoruiz.utiles.Util.pause;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.*;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.title;


/**
 * The TerminalControl class provides a utility method for interacting with the terminal to
 * display its current dimensions. The dimensions are retrieved using platform-specific
 * implementations for Windows and Unix-based systems.
 * <p>
 * This class checks whether a terminal is available and retrieves its number of rows and
 * columns using the TermCtlImpl implementation. If no real console is detected, it provides
 * appropriate feedback to the user.
 */
public class TerminalControl {
    /**
     * Represents an instance of the {@link TermCtl} interface used for terminal control operations.
     * This variable holds a reference to the terminal control implementation, which provides
     * methods for interacting with the terminal, such as retrieving or setting its dimensions
     * and clearing the screen.
     * <p>
     * The {@code tc} instance is initialized with a specific implementation, typically
     * {@code TermCtlImpl}, to enable platform-specific terminal operations.
     */
    private TermCtl tc;

    /**
     * Constructs a new instance of the TerminalControl class and initializes the terminal control
     * implementation.
     * <p>
     * This constructor creates an internal instance of {@link TermCtlImpl}, which is assigned to the
     * {@code tc} field. This instance provides the functionality required for interacting with the terminal,
     * such as retrieving terminal dimensions, clearing the screen, and other terminal-specific operations.
     * <p>
     * The constructor ensures that the {@code tc} field is ready for terminal-related actions immediately
     * after the TerminalControl object is created.
     */
    public TerminalControl() {
        tc = new TermCtlImpl();
    }

    /**
     * Retrieves the {@link TermCtl} instance associated with this class.
     * The {@code TermCtl} interface provides methods for performing terminal control
     * operations such as retrieving terminal dimensions, clearing the screen, and setting dimensions.
     *
     * @return the {@code TermCtl} instance used for terminal control operations.
     */
    public TermCtl getTc() {
        return tc;
    }

    /**
     * Sets the {@link TermCtl} instance associated with this class.
     * This method allows changing the terminal control implementation
     * used for performing terminal-related operations such as retrieving
     * terminal size, clearing the screen, and setting terminal dimensions.
     *
     * @param tc the {@link TermCtl} implementation to be associated with this class
     */
    public void setTc(TermCtl tc) {
        this.tc = tc;
    }

    /**
     * Executes terminal control operations including clearing the terminal screen, retrieving and modifying the console size,
     * and restoring the console to its original dimensions.
     * <p>
     * The method performs the following tasks:
     * <ul>
     * <li>1. Clears the terminal screen.</li>
     * <li>2. Retrieves the current console dimensions and displays them.</li>
     * <li>3. Sets the console size to specific values (25 rows x 80 columns) if a valid console is detected.</li>
     * <li>4. Restores the console to its original size after the operations are completed.</li>
     * <li>5. Provides pauses between operations to allow the user to observe the changes.</li>
     * </ul>
     * Console size operations are skipped if no valid console is detected (e.g., when running in an IDE
     * or with output redirection).
     *
     * @throws Exception if an error occurs during terminal operations.
     */
    public void run() throws Exception {
        // Screen 1
        getTc().clearScreen(Using.ANSI);
        String msg = "Getting and setting console size";
        prtln(2, title(msg, '*', 80));
        System.out.println();
        TermCtlImpl termCtl = new TermCtlImpl();
        Dimensions original_dimensions = termCtl.getConsoleSize();
        Dimensions resized_dimensions;
        Dimensions dimensions;
        if (original_dimensions != null) {
            System.out.printf("Current terminal: %d of rows x %d columns %n",
                    original_dimensions.rows(), original_dimensions.columns());
        } else {
            prtln(1, "No real console was detected (possible IDE or redirection).");
        }
        pause(FOREVER, "");

        // Screen 2
        getTc().clearScreen(Using.ANSI);
        prtln(2, title(msg, '*', 80));
        if (original_dimensions != null) {
            original_dimensions = termCtl.getConsoleSize();
            System.out.printf("Current terminal: %d of rows x %d columns %n",
                    original_dimensions.rows(), original_dimensions.columns());
            prtln(1, "Now, when you press <INTRO>, the terminal size will be set to 25x80...");
            pause(FOREVER, "");
            if (TermCtlImpl.setConsoleSize(80, 25)) {
                prtln(1, "The terminal size is set to 25 rows x 80 columns.");
            } else {
                prtln(1, "The terminal size is *NOT* set to 25 rows x 80 columns.");
            }
            resized_dimensions = termCtl.getConsoleSize();
            System.out.printf( "Current terminal: %d of rows x %d columns %n",
                    resized_dimensions.rows(), resized_dimensions.columns());
        } else {
            prtln(1, "No real console was detected (possible IDE or redirection).");
        }
        pause(FOREVER, "");

        // Screen 3
        getTc().clearScreen(Using.ANSI);
        prtln(2, title(msg, '*', 80));
        dimensions = termCtl.getConsoleSize();
        //We are going to restore the terminal to its previous size, which we have saved.
        if (dimensions != null) {
            boolean isResized = TermCtlImpl.setConsoleSize(
                    original_dimensions != null ? original_dimensions.columns() : 0,
                    original_dimensions != null ? original_dimensions.rows() : 0
                    );
            if (isResized) {
                prtln(1, "The terminal size is restored to its initial values.");
                System.out.printf( "Current terminal: %d of rows x %d columns %n",
                        original_dimensions != null ? original_dimensions.rows() : 0,
                        original_dimensions != null ? original_dimensions.columns() : 0);
            } else {
                prtln(1, "The terminal size is *NOT* restored to its initial values.");
            }

        } else {
            prtln(1, "No real console was detected (possible IDE or redirection).");
        }
        pause(FOREVER, "");
    }
}
