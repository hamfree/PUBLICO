package es.nom.juanfranciscoruiz.ansiterm.app.options;

import com.sun.jna.Platform;
import es.nom.juanfranciscoruiz.ansiterm.*;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import java.util.concurrent.atomic.AtomicBoolean;

import static es.nom.juanfranciscoruiz.ansiterm.LinuxTerminal.*;
import static es.nom.juanfranciscoruiz.ansiterm.WindowsTerminal.*;
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
     * Represents a flag indicating whether the application is currently running.
     * <p>
     * The value is stored in an {@code AtomicBoolean} to support thread-safe
     * operations, ensuring consistent behavior in multi-threaded environments.
     * This variable can be dynamically updated and is used as a control mechanism
     * to manage the lifecycle of terminal-based demonstrations or other tasks
     * where execution needs to be paused or stopped gracefully.
     */
    private final AtomicBoolean running = new AtomicBoolean(true);

    /**
     * Constructs a new ScreenSize.
     *
     * @throws ANSITermException If there is an error initializing the ANSITerm object.
     */
    public ShowScreenSize() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Screen size";
        this.msg = "Try resizing the terminal window, then press ENTER. The terminal size will be" +
            " displayed. Enter 'q' to exit to menu.";
    }
    /**
     * Performs the screen size demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        ITerminal termctl;
        String screenSizeStatus;

        if (Platform.isWindows()) {
            termctl = WindowsTerminal.getInstance();
        } else if (Platform.isLinux() || Platform.isMac()) {
            termctl = LinuxTerminal.getInstance();
        } else {
            throw new Exception("Platform not supported");
        }

        clearScreenAndPrintHeader(term, title, msg, term.getTerminalSize().getColumns());
        pauseWithMessage(0, "Press any key to start the demo. Resize the terminal to see the size in lines and cols printed. Press 'q' to end the demo");

        // Enabling the raw mode of console (no need to press <INTRO> to pass the key to the program)
        termctl.enableRawMode();

        // Start the thread monitor of resize events of console
        termctl.resizeConsoleMonitor();
        try {
            while (running.get()) {
                String key;

                // Read the key press of the user (if any)
                if (Platform.isWindows()) {
                    // The OS is Windows, we use the Microsoft C Runtime API function getch() (via JNA)
                    int c = MSVCRT.INSTANCE._getch();
                    if (c == 'q') break;
                    if (c == 0 || c == 0xE0) {
                        int extra = MSVCRT.INSTANCE._getch();
                        key = "\u001B[" + (char)extra;
                    } else {
                        key = String.valueOf((char)c);
                    }
                } else {
                    // The OS is Windows, we use the GNU LibC Runtime API function read() (via JNA)
                    byte[] buf = new byte[16];
                    int n = LibC.INSTANCE.read(0, buf, buf.length);
                    if (n <= 0) continue;
                    key = new String(buf, 0, n);
                    if (key.equals("q")) break;
                }
            }
        } finally {
            // Stops the thread monitor
            running.set(false);

            // Enable the canonical mode of console (need to press <INTRO> to pass the key to the program)
            termctl.disableRawMode();
        }
        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }
}
