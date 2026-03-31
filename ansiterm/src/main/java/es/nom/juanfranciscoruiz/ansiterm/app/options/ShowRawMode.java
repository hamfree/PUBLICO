package es.nom.juanfranciscoruiz.ansiterm.app.options;

import com.sun.jna.Platform;
import es.nom.juanfranciscoruiz.ansiterm.*;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import es.nom.juanfranciscoruiz.ansiterm.model.*;
import es.nom.juanfranciscoruiz.ansiterm.model.ansisequences.TextColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static es.nom.juanfranciscoruiz.ansiterm.LinuxTerminal.*;
import static es.nom.juanfranciscoruiz.ansiterm.WindowsTerminal.*;
import static es.nom.juanfranciscoruiz.ansiterm.model.DrawChars.*;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;
import static java.lang.String.format;

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
     * Defines a Rectangle object to represent the dimensions, position, and drawing
     * character for terminal-based visual elements.
     * <p>
     * This field is used to draw and manage a rectangular area on the terminal,
     * typically for creating shapes, UI elements, or visual indicators during
     * terminal interactions.
     */
    private final Rectangle rectangle;
    /**
     * Represents the text color configuration used to define foreground
     * and background color properties for terminal-based text output.
     * <p>
     * The variable is an instance of {@link TextColor}, which encapsulates
     * both the ANSI foreground and background color settings.
     * <p>
     * This field is typically utilized for styling terminal elements such as
     * text or graphic components with specific color combinations.
     */
    private final TextColor tc;
    /**
     * Represents an instance of the extended ASCII character set, which includes
     * both the standard ASCII characters (codes 0-127) and additional extended
     * characters (codes 128-255). The `ascii` field provides access to metadata
     * about each character, such as its code, symbol, and description.
     * <p>
     * This field is utilized to support operations that require extended ASCII
     * character set functionalities, such as terminal rendering, graphical displays,
     * or interpreting encoded data. It acts as the primary reference for all extended
     * ASCII-related data within the `ShowRawMode` class.
     */
    private final ASCIICharacterSetExtended ascii;

    /**
     * Represents the key codes mapping configuration used for interpreting
     * and handling input events in raw mode terminal operations.
     * <p>
     * This field contains mappings of specific key codes to their respective
     * functionalities or interpretations, enabling the system to detect and
     * respond to both single and multi-code keystrokes, such as arrow keys,
     * function keys, or control sequences.
     * <p>
     * The `keyCodes` variable is an integral part of the raw mode implementation
     * and is utilized for processing user input in a terminal session across
     * different platforms such as Windows, Linux, and macOS.
     */
    private final KeyCodes keyCodes;

    /**
     * Constructs a new RawMode.
     *
     * @throws Exception If there is an error initializing the ANSITerm object.
     */
    public ShowRawMode() throws Exception {
        this(new ANSITerm(), new KeyCodes(), new ASCIICharacterSetExtended());
        info(logger, "Initializing " + ShowRawMode.class.getSimpleName());
    }

    /**
     * Constructs a new RawMode with provided dependencies.
     * This applies the Dependency Inversion Principle (SOLID).
     *
     * @param term     The terminal instance to be used.
     * @param keyCodes The key codes mapping configuration.
     * @param ascii    The extended ASCII character set configuration.
     */
    public ShowRawMode(ANSITerm term, KeyCodes keyCodes, ASCIICharacterSetExtended ascii) {
        info(logger, "Initializing " + ShowRawMode.class.getSimpleName());
        try {
            this.term = term;
            this.title = "Raw console mode test";
            this.message = """
                    Sets the keyboard of console to RAW mode.
                    Each keystroke generates a keyboard response (scan codes). Press 'q' to exit
                    """;
            this.columns = term.getTerminalSize().getColumns();
            this.tc = new TextColor(Color.RED, BGColor.YELLOW);
            int heightScreen = this.term.getTerminalSize().getLines();
            int widthScreen = this.term.getTerminalSize().getColumns();
            this.rectangle = new Rectangle(0, 0, 0, 0, " ");
            this.rectangle.setWidth(widthScreen - 10);
            this.rectangle.setHeight(heightScreen - 16);
            this.rectangle.setX((widthScreen - rectangle.getWidth()) / 2);
            this.rectangle.setY((heightScreen - rectangle.getHeight()) / 2);

            this.ascii = ascii;
            this.keyCodes = keyCodes;
            info(logger, format("KeyCodes configuration loaded: %s", this.keyCodes));
        } catch (RuntimeException e) {
            error(logger, format("Unable to initialize %s, %s", ShowRawMode.class.getSimpleName(), e.getMessage()));
            throw e;
        }
    }

    /**
     * Executes the main functionality of the raw mode demonstration based on the
     * underlying platform. This method determines the operating system type
     * (Windows, Linux, or macOS) and delegates the execution to the corresponding
     * platform-specific handler. If the platform is unsupported, it logs an error
     * and throws an exception.
     * <p>
     * For supported platforms:<br>
     * - Windows: Executes logic specific to terminal behavior in Windows environments.<br>
     * - Linux/macOS: Executes logic specific to terminal behavior in Unix-based environments.<br>
     * <p>
     * The terminal control instance (`ITerminal`) is initialized based on the
     * platform type and passed to the respective handler for processing.
     * <p>
     * After execution, the method pauses, prompting the user to press &lt;ENTER&gt;
     * to return to the menu.
     *
     * @throws Exception If the platform is not supported, or if an error occurs
     *                   during terminal handling or execution.
     */
    public void perform() throws Exception {
        ITerminal termctl;
        info(logger, format("Starting raw mode demo on platform %s", Platform.RESOURCE_PREFIX));
        if (Platform.isWindows()) {
            termctl = WindowsTerminal.getInstance();
            performInWindows(termctl);
        } else if (Platform.isLinux() || Platform.isMac()) {
            termctl = LinuxTerminal.getInstance();
            performInLinuxOrMac(termctl);
        } else {
            error(logger, "Platform not supported");
            throw new Exception("Platform not supported");
        }
        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }

    /**
     * Executes functionality in a Windows environment using raw mode terminal controls.
     * This method handles user input in real-time, processes special keys or ANSI sequences,
     * and updates the terminal display accordingly. It depends on Windows-specific
     * terminal behavior and leverages the MSVCRT library for capturing key presses.
     *
     * @param termctl The ITerminal instance responsible for managing terminal
     *                control operations, such as enabling or disabling raw mode.
     * @throws Exception If an error occurs during terminal operations or key handling.
     */
    public void performInWindows(ITerminal termctl) throws Exception {
        String msg;
        String key;
        int x = rectangle.getX();
        int y = rectangle.getY() + rectangle.getHeight() - 1;
        int c;
        int extra = 0;

        info(logger, keyCodes.getMapping());


        clearScreenAndPrintHeader(term, title, message, columns);
        Rectangle br = new Rectangle(rectangle.getX() - 1, rectangle.getY() - 1, rectangle.getWidth() + 2, rectangle.getHeight() + 2, " ");
        drawBorderRectangle(br, new TextColor(Color.GLOSSY_GREEN, BGColor.GLOSSY_WHITE));
        drawRectangle(rectangle, tc);

        try {
            while (true) {
                c = MSVCRT.INSTANCE._getch();
                if (c == 'q') {
                    term.clearTerminal();
                    cleanLineAndShowMessage(c, extra, y, x, rectangle, tc);
                    break;
                }
                if (c == 0 || c == 194 || c == 195 || c == 224) {
                    extra = MSVCRT.INSTANCE._getch();
                    key = "\u001B[" + ascii.getChar(extra).getCharacter();
                } else {
                    if (c < 32) {
                        cleanLineAndShowMessage(c, extra, y, x, rectangle, tc);
                        continue;
                    }
                    cleanLineAndShowMessage(c, extra, y, x, rectangle, tc);
                    key = String.valueOf((char) c);
                }
                if (key.startsWith("\u001B")) {
                    cleanLineAndShowMessage(c, extra, y, x, rectangle, tc);
                }
            }
        } finally {
            termctl.disableRawMode();
        }
    }

    /**
     * Executes functionality in a Linux or macOS environment using raw mode terminal controls.
     * This method captures and processes user input in real-time, identifies control keys or
     * regular keystrokes, and updates the terminal display accordingly based on the captured input.
     * If the user presses the 'q' key, the method terminates execution.
     *
     * @param termctl The ITerminal instance responsible for managing terminal control operations,
     *                such as enabling or disabling raw mode.
     * @throws Exception If an error occurs during terminal operations or input handling.
     */
    public void performInLinuxOrMac(ITerminal termctl) throws Exception {
        String msg;
        String key;
        int x = rectangle.getX() + 1;
        int y = rectangle.getY() + rectangle.getHeight() - 1;

        clearScreenAndPrintHeader(term, title, message, columns);
        drawRectangle(rectangle, tc);

        try {
            while (true) {
                byte[] buf = new byte[16];
                int n = LibC.INSTANCE.read(0, buf, buf.length);
                if (n <= 0) continue;
                key = new String(buf, 0, n);
                char c = key.charAt(0);
                if (c == 'q') {
                    break;
                }
                if (c < 32) {
                    cleanLineAndShowMessage(c, 0, y, x, rectangle, tc);
                } else {
                    int car = key.charAt(0);
                    if (car != 0xE0) {
                        cleanLineAndShowMessage(c, 0, y, x, rectangle, tc);
                    }
                }
            }
        } finally {
            termctl.disableRawMode();
        }
    }

    /**
     * Clears a specific line in the terminal, displays a message, and adjusts the terminal scroll.
     * The method handles single and two-code keystrokes, formats a message accordingly,
     * and updates the terminal with the new content and styling at the specified position.
     *
     * @param key   The primary key code representing a keystroke.
     * @param extra An additional key code for multi-code keystrokes (e.g., complex inputs like arrow keys).
     * @param y     The row position (vertical) in the terminal where the line should be cleared and the message displayed.
     * @param x     The column position (horizontal) in the terminal where the line and message operations start.
     * @param rec   A Rectangle object defining the dimensions of the region to scroll and clear.
     * @param tc    A TextColor object specifying the foreground and background colors for the message and cleared line.
     * @throws ANSITermException If an error occurs while interacting with the terminal, such as setting colors or cursor positions.
     */
    private void cleanLineAndShowMessage(int key, int extra, int y, int x, Rectangle rec, TextColor tc) throws ANSITermException {
        String msg;
        int[] codes = new int[2];
        int[] code = new int[1];

        if (key == 0 || key == 194 || key == 195 || key == 224) {
            codes[0] = key;
            codes[1] = extra;
            msg = format("Two code keystroke: (%d, %d), keycode: %s\n", key, extra, this.keyCodes.getKeyName(codes));
        } else {
            code[0] = key;
            msg = format("One code keystroke: (%d), keycode: %s\n", key, this.keyCodes.getKeyName(code));
        }

        msg = term.setColor(tc.getColor(), msg);
        msg = term.setBackgroundColor(tc.getBgColor(), msg);
        String emptyline = " ".repeat(rec.getWidth());
        emptyline = term.setColors(tc.getColor(), tc.getBgColor(), emptyline);

        term.setCursorPosition(new Position(y, x));
        term.printAt(emptyline, y, x);
        term.printAt(msg, y, x);
        term.scrollUp(rec.getY(), rec.getX(), rec.getWidth(), rec.getHeight(), 1);
    }

    /**
     * Draws a rectangle on the terminal using the specified dimensions, position, and colors.
     *
     * @param rectangle The Rectangle object containing the position (x, y),
     *                  dimensions (width, height), and character properties
     *                  to define the appearance and location of the rectangle.
     * @param tc        The TextColor object containing the foreground and
     *                  background color information to be applied while drawing the rectangle.
     * @throws ANSITermException If an error occurs while performing terminal operations.
     */
    private void drawRectangle(Rectangle rectangle, TextColor tc) throws ANSITermException {
        int contador = 0;
        String character = term.setColors(tc.getColor(), tc.getBgColor(), rectangle.getCharacter());
        int x = rectangle.getX();
        int y = rectangle.getY();
        int width = rectangle.getWidth();
        int height = rectangle.getHeight();

        for (int line = y; line < y + height; line++) {
            for (int col = x; col < x + width; col++) {
                term.printAt(character, line, col);
            }
        }
    }

    /**
     * Draws a border rectangle on the terminal with the specified dimensions, position, and colors.
     * The method creates a rectangular border consisting of custom characters
     * and applies the provided foreground and background colors to the border.
     *
     * @param rec The Rectangle object containing the position (x, y), dimensions (width, height),
     *            and character properties that define the appearance and location of the rectangle border.
     * @param tc  The TextColor object containing the foreground and background color information
     *            to be applied to the border.
     * @throws ANSITermException If an error occurs during terminal operations,
     *                           such as setting character sets or printing lines.
     */
    private void drawBorderRectangle(Rectangle rec, TextColor tc) throws ANSITermException {
        String line, upperLine, intermediateLine, lowerLine;

        term.setDECCharacterSet();
        line = String.valueOf(LU_CORNER);
        String repeat = String.valueOf(HL).repeat(rec.getWidth() - 2);
        line = line + repeat + RU_CORNER;
        upperLine = term.setColors(tc.getColor(), tc.getBgColor(), line);
        term.printAt(upperLine, rec.getY(), rec.getX());
        for (int currentY = rec.getY() + 1; currentY < rec.getY() + rec.getHeight() - 1; currentY++) {
            line = VL + " ".repeat(rec.getWidth() - 2) + VL;
            intermediateLine = term.setColors(tc.getColor(), tc.getBgColor(), line);
            term.printAt(intermediateLine, currentY, rec.getX());
        }
        line = LD_CORNER + repeat + RD_CORNER;
        lowerLine = term.setColors(tc.getColor(), tc.getBgColor(), line);
        term.printAt(lowerLine, rec.getY() + rec.getHeight() - 1, rec.getX());
        term.setASCIICharacterSet();
    }
}
