package es.nom.juanfranciscoruiz.ansiterm.app.options;

import com.sun.jna.Platform;
import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.ITerminal;
import es.nom.juanfranciscoruiz.ansiterm.LinuxTerminal;
import es.nom.juanfranciscoruiz.ansiterm.WindowsTerminal;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static es.nom.juanfranciscoruiz.ansiterm.LinuxTerminal.*;
import static es.nom.juanfranciscoruiz.ansiterm.WindowsTerminal.*;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.clearScreenAndPrintHeader;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * Demonstrates the use of raw terminal mode for keyboard input.
 *
 * @author Juan F. Ruiz
 */
public class ShowRawMode {
    /**
     * Logger used for tracing and debugging.
     */
    public static final Logger logger = LoggerFactory.getLogger(ShowRawMode.class);
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
     * Represents a mapping of key-value pairs where both keys and values are strings.
     * This map is utilized to store and retrieve specific string associations that
     * are relevant to the functionality of the containing class.
     * <p>
     * The content of this map is managed internally by the class, and it is not
     * intended to be modified or accessed directly by external consumers. It is
     * used to support operations such as mapping key inputs to corresponding
     * functionality or messages within the application.
     * <p>
     * This field is final, ensuring that the map instance cannot be reassigned after
     * initialization. However, the contents of the map can be dynamically modified
     * during the execution of the program.
     */
    private final Map<String, String> keyMap = new HashMap<>();

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
     * Constructs a new RawMode.
     *
     * @throws ANSITermException If there is an error initializing the ANSITerm object.
     */
    public ShowRawMode() throws ANSITermException {
        this.term = new ANSITerm();
        this.title = "Raw console mode test";
        this.message = "Sets the keyboard of console to RAW mode. " +
            "Each keystroke generates a " +
            "keyboard response in the form of code. Press 'q' to exit.";
        this.columns = term.getTerminalSize().getColumns();
        setupKeys();
    }


    /**
     * Performs the raw mode demonstration.
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

        clearScreenAndPrintHeader(term,title,message,columns);

        try {
            while (running.get()) {
                String key;
                if (Platform.isWindows()) {
                    int c = MSVCRT.INSTANCE._getch();
                    if (c == 'q') break;
                    if (c == 0 || c == 0xE0) {
                        int extra = MSVCRT.INSTANCE._getch();
                        key = "\u001B[" + (char)extra;
                    } else {
                        key = String.valueOf((char)c);
                    }
                } else {
                    byte[] buf = new byte[16];
                    int n = LibC.INSTANCE.read(0, buf, buf.length);
                    if (n <= 0) continue;
                    key = new String(buf, 0, n);
                    if (key.equals("q")) break;
                }

                processInput(key);
            }
        } finally {
            running.set(false);
            termctl.disableRawMode();
        }
        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }

    /**
     * Configures mappings between ANSI escape sequences and their corresponding
     * key representations. This method sets up a predefined mapping of terminal
     * escape sequences to readable names for various keys such as arrow keys,
     * function keys, and control keys. The mappings are stored in the {@code keyMap}
     * field for use in identifying keypress inputs.
     * <p>
     * Mappings include:
     * - Arrow keys (e.g., UP_ARROW, DOWN_ARROW, LEFT_ARROW, RIGHT_ARROW)
     * - Control keys (e.g., HOME, END)
     * - Function keys (e.g., F1, F2, F12)
     * <p>
     * This method is typically invoked to initialize the key mapping once
     * during the lifecycle of the class, ensuring that input received from
     * the terminal can be correctly identified and processed.
     */
    private void setupKeys() {
        keyMap.put("\u001B[A", "UP_ARROW");
        keyMap.put("\u001B[B", "DOWN ARROW");
        keyMap.put("\u001B[C", "RIGHT_ARROW");
        keyMap.put("\u001B[D", "LEFT_ARROW");
        keyMap.put("\u001B[H", "HOME");
        keyMap.put("\u001B[F", "END");
        keyMap.put("\u001BOP", "F1"); keyMap.put("\u001BOQ", "F2");
        keyMap.put("\u001BOR", "F3"); keyMap.put("\u001BOS", "F4");
        keyMap.put("\u001B[15~", "F5"); keyMap.put("\u001B[17~", "F6");
        keyMap.put("\u001B[24~", "F12");
    }

    /**
     * Processes the input received from the terminal and determines its type.
     * Differentiates between ANSI escape sequences, control characters,
     * and regular keystrokes, then outputs the processed result to the console.
     *
     * @param input The string input received, typically representing a keypress
     *              or control sequence. It may start with an ANSI escape sequence,
     *              a control character, or a standard visible character.
     */
    private void processInput(String input) {
        if (input.startsWith("\u001B")) {
            System.out.println("Detected: " + keyMap.getOrDefault(input, "ANSI sequence: " + input.replace("\u001B", "ESC")));
        } else {
            char c = input.charAt(0);
            if (c < 32) {
                System.out.printf("Control: CTRL + %c\n", (char)(c + 64));
            } else {
                System.out.printf("Keystroke: %s\n", input);
            }
        }
    }
}
