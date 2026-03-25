package es.nom.juanfranciscoruiz.ansiterm.app.options;

import com.sun.jna.Platform;
import es.nom.juanfranciscoruiz.ansiterm.*;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import es.nom.juanfranciscoruiz.ansiterm.model.*;
import es.nom.juanfranciscoruiz.ansiterm.model.ansisequences.TextColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static es.nom.juanfranciscoruiz.ansiterm.LinuxTerminal.*;
import static es.nom.juanfranciscoruiz.ansiterm.WindowsTerminal.*;
import static es.nom.juanfranciscoruiz.ansiterm.model.DrawChars.*;
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
     * Defines a Rectangle object to represent the dimensions, position, and drawing
     * character for terminal-based visual elements.
     * <p>
     * This field is used to draw and manage a rectangular area on the terminal,
     * typically for creating shapes, UI elements, or visual indicators during
     * terminal interactions.
     */
    private Rectangle rectangle;
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
    private TextColor tc;
    /**
     * Represents the height of the screen in a terminal-based application.
     * This field typically stores the number of rows or lines available
     * on the terminal screen and is used for managing layout, drawing
     * operations, and ensuring proper content positioning.
     */
    private int heightScreen;
    /**
     * Represents the width of the terminal screen in columns.
     * This variable stores the number of columns available in the
     * terminal display and is typically used in operations that
     * involve layout calculations or rendering of elements
     * relative to the screen size.
     * <p>
     * It is initialized and/or updated based on the terminal's
     * current configuration or dimensions and may be used
     * throughout the class to ensure correct alignment and
     * positioning of content in a terminal-based application.
     */
    private int widthScreen;
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
    private ASCIICharacterSetExtended ascii;

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
                "keyboard response (scan codes). Press 'q' to exit";
        this.columns = term.getTerminalSize().getColumns();
        this.tc = new TextColor(Color.RED, BGColor.YELLOW);
        this.heightScreen = this.term.getTerminalSize().getLines();
        this.widthScreen = this.term.getTerminalSize().getColumns();
        this.rectangle = new Rectangle(0, 0, 0, 0, " ");
        this.rectangle.setWidth(widthScreen - 10);
        this.rectangle.setHeight(heightScreen - 16);
        this.rectangle.setX((widthScreen - rectangle.getWidth()) / 2);
        this.rectangle.setY((heightScreen - rectangle.getHeight()) / 2);

        this.ascii = new ASCIICharacterSetExtended();

        setupKeys();
    }

    /**
     * Performs the raw mode demonstration.
     *
     * @throws Exception If an error occurs during execution.
     */
    public void perform() throws Exception {
        ITerminal termctl;
        if (Platform.isWindows()) {
            termctl = WindowsTerminal.getInstance();
            performInWindows(termctl);
        } else if (Platform.isLinux() || Platform.isMac()) {
            termctl = LinuxTerminal.getInstance();
            performInLinuxOrMac(termctl);
        } else {
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

        clearScreenAndPrintHeader(term, title, message, columns);
        Rectangle br = new Rectangle(rectangle.getX() - 1, rectangle.getY() - 1, rectangle.getWidth() + 2, rectangle.getHeight() + 2, " ");
        drawBorderRectangle(br, new TextColor(Color.GLOSSY_GREEN, BGColor.GLOSSY_WHITE));
        drawRectangle(rectangle, tc);

        try {
            while (true) {
                c = MSVCRT.INSTANCE._getch();
                if (c == 'q') {
                    term.clearTerminal();
                    msg = String.format("Pressed the 'q' key! (%d)\n", c);
                    cleanLineAndPrintMessage(msg, y, x, rectangle, tc);
                    break;
                }
                if (c == 0 || c == 194 || c == 224) {
                    extra = MSVCRT.INSTANCE._getch();
                    msg = String.format("Value was '0', '194' or '224', getting the next scan code: %d\n", extra);
                    cleanLineAndPrintMessage(msg, y, x, rectangle, tc);
                    key = "\u001B[" + ascii.getChar(extra).getCharacter();
                } else {
                    if (c < 32){
                        msg = String.format("Control character: + %s\n", ascii.getChar(c).getDescription());
                        cleanLineAndPrintMessage(msg, y, x, rectangle, tc);
                        continue;
                    }
                    msg = String.format("key integer value: %d, key char value: '%c', description: %s\n",
                            c, ascii.getChar(c).getCharacter(), ascii.getChar(c).getDescription());
                    cleanLineAndPrintMessage(msg, y, x, rectangle, tc);
                    key = String.valueOf((char) c);
                }
                if (key.startsWith("\u001B")) {
                    String keyDetected = keyMap.getOrDefault(key, "ANSI sequence: " + key.replace("\u001B", "ESC"));
                    msg = String.format("Detected: %s, key integer value: %d\n", keyDetected, extra);
                    cleanLineAndPrintMessage(msg, y, x, rectangle, tc);
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
                    msg = String.format("Control: CTRL + %c\n", (char) (c + 64));
                    cleanLineAndPrintMessage(msg, y, x, rectangle, tc);
                } else {
                    int car = key.charAt(0);
                    if (car != 0xE0) {
                        msg = String.format("Keystroke: %s\n", key);
                        cleanLineAndPrintMessage(msg, y, x, rectangle, tc);
                    }
                }
            }
        } finally {
            termctl.disableRawMode();
        }
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
        // Change the values because they do not correspond to your keystrokes.
        // The ANSI codes and the keys that generate them when pressed must be
        // noted in a file to then redefine the keyMap variable map.
        keyMap.put("\u001B[A", "UP_ARROW");
        keyMap.put("\u001B[B", "DOWN ARROW");
        keyMap.put("\u001B[C", "RIGHT_ARROW");
        keyMap.put("\u001B[D", "LEFT_ARROW");
        keyMap.put("\u001B[H", "HOME");
        keyMap.put("\u001B[F", "END");
        keyMap.put("\u001BOP", "F1");
        keyMap.put("\u001BOQ", "F2");
        keyMap.put("\u001BOR", "F3");
        keyMap.put("\u001BOS", "F4");
        keyMap.put("\u001B[15~", "F5");
        keyMap.put("\u001B[17~", "F6");
        keyMap.put("\u001B[24~", "F12");
    }

    /**
     * Clears the specified line on the terminal and prints a message at the specified position.
     *
     * @param msg The message to be displayed on the terminal.
     * @param y   The line number where the message will be printed.
     * @param x   The column number where the message will start from.
     * @param rec The color and background colors to be applied to the message.
     * @throws ANSITermException If an error occurs during terminal operations.
     */
    private void cleanLineAndPrintMessage(String msg, int y, int x, Rectangle rec, TextColor tc) throws ANSITermException {
        msg = term.setColor(tc.getColor(), msg);
        msg = term.setBackgroundColor(tc.getBgColor(), msg);
        String lineaVacia = " ".repeat(rec.getWidth() - 1);
        lineaVacia = term.setColors(tc.getColor(), tc.getBgColor(), lineaVacia);

        term.setCursorPosition(new Position(y, x));
        term.printAt(lineaVacia, y, x);
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
        upperLine = term.setColors(tc.getColor(),tc.getBgColor(), line);
        term.printAt(upperLine, rec.getY(), rec.getX());
        for (int currentY = rec.getY() + 1; currentY < rec.getY() + rec.getHeight() - 1; currentY++){
            line = VL + " ".repeat(rec.getWidth() - 2) + VL;
            intermediateLine = term.setColors(tc.getColor(),tc.getBgColor(), line);
            term.printAt(intermediateLine, currentY, rec.getX());
        }
        line = LD_CORNER + repeat + RD_CORNER;
        lowerLine = term.setColors(tc.getColor(),tc.getBgColor(), line);
        term.printAt(lowerLine, rec.getY() + rec.getHeight() - 1, rec.getX());
        term.setASCIICharacterSet();
    }
}
