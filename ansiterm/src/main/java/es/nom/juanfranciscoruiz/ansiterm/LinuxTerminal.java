package es.nom.juanfranciscoruiz.ansiterm;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that provides functionality to interact with a Linux terminal. It supports
 * manipulation of terminal modes, capturing input, obtaining cursor position, and
 * monitoring terminal resizing events. The class makes use of ANSI escape sequences
 * and interacts with the terminal using POSIX-compatible system calls.
 */
public class LinuxTerminal implements ITerminal {
    /**
     * A logger instance for the {@code LinuxTerminal} class.
     * This is used to output logging information, such as error messages,
     * debug details, or general operational logs, to a configured logging system.
     * The logger uses the SLF4J API for flexible and configurable logging.
     */
    public static final Logger logger = LoggerFactory.getLogger(LinuxTerminal.class);
    /**
     * A singleton instance of the LinuxTerminal class.
     * This variable is used to ensure that only one instance of the LinuxTerminal
     * exists within the application, adhering to the Singleton design pattern.
     * It is declared as private to restrict direct access from outside the class.
     */
    private static LinuxTerminal INSTANCE;

    /**
     * Represents the ANSI escape character sequence prefix, commonly used
     * to initiate control sequences for terminal text formatting, cursor movement,
     * and other terminal operations. This value is specific to UNIX-like environments
     * and allows manipulation of terminal behavior programmatically.
     */
    private static final String ESC = "\033";
    /**
     * Represents the ANSI escape sequence for retrieving the current cursor position.
     * This sequence is used to query the terminal for the current cursor position
     * and is part of the ANSI escape sequence standard.
     */
    private static final String REC_POS_CUR = ESC + "[6n";
    /**
     * Represents the original terminal configuration prior to any modifications made
     * by the LinuxTerminal class. This field is used to store and restore the
     * terminal's state, ensuring that changes such as enabling raw mode can be
     * reverted when necessary.
     */
    private Object originalConfig;
    /**
     * A flag used to control the state of terminal-related operations, such as the resize monitoring thread.
     * When set to {@code true}, operations that depend on this flag (e.g., terminal monitoring) are allowed
     * to continue executing. Changing this value to {@code false} signals dependent operations to stop gracefully.
     * <p>
     * This variable is particularly useful for managing lifecycle behaviors, such as terminating background
     * threads or processes related to the terminal when the application shuts down or transitions states.
     * <p>
     * As an {@link AtomicBoolean}, this ensures thread-safe updates and visibility across threads, making it
     * suitable for use in concurrent programming environments.
     */
    private final AtomicBoolean running = new AtomicBoolean(true);
    /**
     * A mapping of key-value pairs where both keys and values are strings.
     * The keyMap is used internally within the LinuxTerminal class for managing
     * specific terminal-related configurations or states that may be needed
     * during the terminal's operation.
     * <p>
     * This map is immutable once initialized, as it is declared final.
     * It employs a HashMap for storing the entries, ensuring constant-time
     * performance for basic operations such as get and put, assuming a
     * well-distributed hash.
     */
    private final Map<String, String> keyMap = new HashMap<>();
    /**
     * Represents the last known number of columns in the terminal.
     * This field is used internally to track changes in the terminal's width.
     * Its value is initially set to -1 to indicate that no measurement or
     * initialization has been performed. This value is dynamically updated
     * during terminal size monitoring to reflect the current number of columns.
     */
    private int lastCols = -1;
    /**
     * Represents the number of rows processed or tracked most recently.
     * <p>
     * This variable holds the count of rows, typically used to monitor
     * or limit the number of rows involved in a particular operation.
     * A default value of -1 indicates that no specific count has been set
     * or that the count is uninitialized.
     */
    private int lastRows = -1;

    /**
     * Represents the terminal attributes and settings structure used in Unix-based systems.
     * This class provides fields that map to the `termios` structure used for configuring
     * terminal I/O settings such as input modes, output modes, control modes, and local modes.
     * <p>
     * The `Termios` structure is a low-level construct used to manipulate terminal behavior
     * and is commonly accessed via system calls like `tcgetattr` and `tcsetattr`. These settings
     * define how the terminal handles input and output data.
     */
    @Structure.FieldOrder({"c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_line", "c_cc", "c_ispeed", "c_ospeed"})
    public static class Termios extends Structure {
        /**
         * Represents the input mode flags in the terminal attributes structure.
         * <p>
         * This variable corresponds to the `c_iflag` field in the `termios` structure
         * in Unix-based systems. It determines various input mode settings that control
         * how input data is processed by the terminal. These settings may include
         * behaviors such as enabling or disabling input character processing,
         * character mapping, input line editing, and special character handling.
         * <p>
         * The `c_iflag` value is typically a bitmask, where each bit represents a
         * specific input mode setting.
         * <p>
         * Common input mode settings controlled by `c_iflag` include:<br>
         * - Enabling or disabling character parity checks.<br>
         * - Converting carriage return to newline during input.<br>
         * - Ignoring specific input characters or errors.
         */
        public int c_iflag;
        /**
         * Represents the output mode flags in the terminal attributes structure.
         * <p>
         * This variable corresponds to the `c_oflag` field in the `termios` structure
         * in Unix-based systems. It determines various output mode settings that control
         * how output data is processed by the terminal. These settings may include
         * behaviors such as character translation, output flow control, and newline handling.
         * <p>
         * The `c_oflag` value is typically a bitmask, where each bit represents a specific
         * output mode setting.
         * <p>
         * Common output mode settings controlled by `c_oflag` include:<br>
         * - Enabling or disabling translation of newline characters to carriage return and newline.<br>
         * - Controlling the handling of tab characters.<br>
         * - Defining output post-processing behaviors.
         */
        public int c_oflag;
        /**
         * Represents the control mode flags in the terminal attributes structure.
         * <p>
         * This variable corresponds to the `c_cflag` field in the `termios` structure
         * in Unix-based systems. It determines various control mode settings that define
         * hardware-related aspects of terminal behavior such as baud rate, character size,
         * parity, and hardware flow control.
         * <p>
         * The `c_cflag` value is typically a bitmask, where each bit represents a
         * specific control mode setting. These settings affect how data transmission
         * and reception are handled at a low level by the terminal hardware.
         * <p>
         * Common control mode settings controlled by `c_cflag` include:<br>
         * - Setting the baud rate for data transmission.<br>
         * - Defining the number of data bits per character.<br>
         * - Enabling or disabling hardware flow control.<br>
         * - Configuring parity checking and generation.
         */
        public int c_cflag;
        /**
         * Represents the local modes flags within the termios structure. The c_lflag field is used
         * to specify or modify local terminal behaviour settings, such as canonical mode (line-based
         * input processing), echo settings, and other terminal control configurations.
         * <p>
         * This field corresponds to the local mode bitmask in POSIX termios structures typically found in Unix-like systems.
         */
        public int c_lflag;
        /**
         * Represents the line discipline of the terminal settings in the structure.
         * It is used to specify the terminal control line configuration.
         */
        public byte c_line;
        /**
         * Control character array used to configure special character handling
         * in terminal I/O settings. This array typically holds predefined
         * indexes corresponding to specific terminal control functions
         * such as end-of-file, interrupt, or erase. The specific interpretation
         * of the array values depends on the underlying terminal configuration
         * and the associated system's termios structure.
         */
        public byte[] c_cc = new byte[32];
        /**
         * Represents the input baud rate for terminal I/O settings.
         * This variable is typically used in low-level terminal control configurations
         * to define the speed for incoming data in bits per second.
         * It is part of the termios structure, which is used for configuring
         * terminal I/O characteristics.
         */
        public int c_ispeed;
        /**
         * Represents the output baud rate in the terminal control settings.
         * This field is part of the {@code Termios} structure and specifies
         * the speed at which data is transmitted from the terminal.
         */
        public int c_ospeed;
    }

    /**
     * Represents the terminal window size, including the current number of rows,
     * columns, and the pixel dimensions (width and height) of a character cell.
     * This class is primarily used for interacting with terminal properties
     * at a lower level, often through native operating system calls.
     * <p>
     * Note:<br>
     * - The actual dimensions of the window may vary based on the terminal emulator or environment.<br>
     * - Pixel dimensions may not always be available and may remain as default or undefined values.
     */
    @Structure.FieldOrder({"ws_row", "ws_col", "ws_xpixel", "ws_ypixel"})
    public static class WinSize extends Structure {
        /**
         * Represents the number of rows (lines) in the terminal window.
         * This value is often used to determine the vertical size of the terminal
         * when interacting with low-level system APIs or terminal properties.
         */
        public short ws_row;
        /**
         * Represents the number of columns (characters wide) in the terminal window.
         * This value specifies the horizontal size of the terminal when interacting
         * with low-level system APIs or terminal properties.
         */
        public short ws_col;
        /**
         * Represents the horizontal pixel dimension of a single character cell in the terminal window.
         * This value indicates the width, in pixels, of each character cell, allowing for
         * fine-grained detailing of the terminal's graphical representation.
         */
        public short ws_xpixel;
        /**
         * Represents the vertical pixel dimension of a single character cell in the terminal window.
         * This value indicates the height, in pixels, of each character cell, enabling detailed
         * graphical representation of the terminal's appearance.
         */
        public short ws_ypixel;
    }

    /**
     * This interface defines JNI bindings to standard C library functions for terminal I/O operations.
     * It provides methods for interacting with the terminal, controlling terminal attributes,
     * and performing low-level I/O operations.
     */
    public interface LibC extends Library {
        /**
         * An instance of the {@code LibC} interface, which provides bindings to standard C library
         * functions for terminal I/O operations. This is initialized using the {@code Native.load} method
         * to bind the native "c" library and provide access to its underlying functionality.
         */
        LibC INSTANCE = Native.load("c", LibC.class);

        /**
         * Retrieves the terminal attributes for the file descriptor provided and stores them in the specified
         * {@code Termios} structure. This function is a wrapper for the `tcgetattr` system call and is typically
         * used in Unix-based systems to access and modify terminal I/O settings.
         *
         * @param fd      The file descriptor of the terminal. This is a valid descriptor for which the attributes need to be retrieved.
         * @param termios A reference to the {@code Termios} structure where the current terminal attributes will be stored.
         *                The structure contains fields that represent input flags, output flags, control flags,
         *                local flags, and other terminal configuration parameters.
         * @return Returns 0 on success, or -1 if an error occurs. If an error occurs, the global {@code errno} variable is set
         * to indicate the error type.
         */
        int tcgetattr(int fd, Termios termios);

        /**
         * Updates the terminal attributes for the specified file descriptor.
         * This function is a wrapper for the `tcsetattr` system call and is
         * typically used in Unix-based systems to modify terminal I/O settings.
         *
         * @param fd      The file descriptor of the terminal. This should be a valid
         *                terminal file descriptor for which the attributes need to be set.
         * @param opt     The option that specifies when the changes to the terminal attributes
         *                will take effect. Common values include:<br>
         *                - `TCSANOW`: Apply the changes immediately.<br>
         *                - `TCSADRAIN`: Apply the changes after all output has been transmitted.<br>
         *                - `TCSAFLUSH`: Apply the changes after all output has been transmitted,
         *                and discard all unread input.
         * @param termios A reference to the {@code Termios} structure that contains
         *                the new terminal attributes to be applied. These attributes
         *                include input flags, output flags, control flags, local flags,
         *                and other terminal configuration parameters.
         * @return Returns 0 on success, or -1 if an error occurs. If an error occurs,
         * the global {@code errno} variable is set to indicate the error type.
         */
        int tcsetattr(int fd, int opt, Termios termios);

        /**
         * Reads data from a file descriptor into the provided buffer. This method
         * wraps the `read` system call, which is typically used in Unix-based
         * systems to read data from files, pipes, or other I/O streams.
         *
         * @param fd    The file descriptor to read from. This must be a valid descriptor
         *              that is open and readable.
         * @param buf   The buffer into which the data will be read. The buffer must
         *              have sufficient space to store up to {@code count} bytes.
         * @param count The maximum number of bytes to read. The actual number of
         *              bytes read may be less than this value depending on the
         *              availability of data and other conditions.
         * @return Returns the number of bytes read on success. If the return value
         * is 0, it indicates that the end-of-file has been reached. If the
         * return value is -1, an error occurred, and the global {@code errno}
         * variable will be set to indicate the error type.
         */
        int read(int fd, byte[] buf, int count);

        /**
         * Issues a control command to a device associated with the given file descriptor.
         * This method is a wrapper for the `ioctl` system call, commonly used in Unix-based
         * systems to perform device-specific operations that are not achievable through
         * standard system calls.
         *
         * @param fd      The file descriptor of the device to operate on. This must be a valid
         *                descriptor that is open and associated with a device capable of handling
         *                the requested operation.
         * @param request The control command to execute. This is a device-specific request and
         *                must be an appropriate value for the device referred to by {@code fd}.
         * @param ws      A reference to a {@code WinSize} structure. This structure may be used to
         *                pass data to or retrieve data from the device (e.g., querying or setting
         *                terminal window size). This parameter's use depends on the {@code request}
         *                argument and is optional in some cases.
         * @return Returns 0 on success. If an error occurs, the return value is -1, and the global
         * {@code errno} variable is set to indicate the specific error type.
         */
        int ioctl(int fd, long request, WinSize ws);
    }

    /**
     * This constructor initializes a new instance of the LinuxTerminal class.
     * It is private to prevent instantiation of the class from outside.
     * <p>
     * The LinuxTerminal class is designed to represent functionality specific
     * to a Linux terminal environment. By restricting its instantiation,
     * it ensures that the class can only be utilized in the intended manner,
     * possibly via static methods or controlled access patterns.
     */
    private LinuxTerminal() {
    }

    /**
     * Provides a singleton instance of the LinuxTerminal class.
     *
     * @return the singleton instance of LinuxTerminal,
     * either an existing one or a newly created instance.
     */
    public static LinuxTerminal getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LinuxTerminal();
        }
        return INSTANCE;
    }

    /**
     * Enables the 'raw' mode of the terminal, which provides direct access to
     * terminal input and output without processing certain control characters
     * (e.g., ENTER, CTRL-C). In raw mode, input is made available immediately
     * (character by character) without waiting for a newline, and special
     * characters, such as backspace or interrupt signals, are no longer processed
     * by the terminal.
     * <p>
     * The method modifies terminal attributes (specifically, `c_lflag`) to
     * disable canonical mode, echoing of input, and signal generation. It also
     * adjusts control characters like `VMIN` to set the minimum input length to
     * 1 and `VTIME` to 0, ensuring non-blocking input behavior.
     * <p>
     * Key terminal modes and behaviors adjusted:<br>
     * - Canonical mode (ICANON): Disables line editing features, allowing raw
     * character input without requiring a newline.<br>
     * - Echo mode (ECHO): Prevents the terminal from displaying input characters.<br>
     * - Signal processing (ISIG): Disables signal generation, so input of certain
     * characters (e.g., CTRL-C) does not generate system signals.<br>
     * <p>
     * Notes:<br>
     * - This method captures the current terminal configuration (using `tcgetattr`)
     * and stores it in the `originalConfig` field before making changes. The
     * original configuration can later be restored using other methods (e.g.,
     * disabling raw mode).<br>
     * - The `LibC` interface is used to interact with native system calls,
     * making this functionality specific to environments that support such
     * system-level operations (e.g., POSIX-compliant systems).<br>
     * - This method assumes it is executed in a context where modifying terminal
     * settings is permitted and the calling thread/process has the necessary
     * privileges.<br>
     */
    @Override
    public void enableRawMode() {
        if (Platform.isLinux()) {
            Termios t = new Termios();
            LibC.INSTANCE.tcgetattr(0, t);
            originalConfig = t;
            Termios raw = new Termios();
            LibC.INSTANCE.tcgetattr(0, raw);
            // Disable: ICANON(2), ECHO(8), ISIG(1)
            raw.c_lflag &= ~(0x00000002 | 0x00000008 | 0x00000001);
            raw.c_cc[4] = 1; // VMIN
            raw.c_cc[5] = 0; // VTIME
            LibC.INSTANCE.tcsetattr(0, 0, raw);
        }
    }

    /**
     * Restores the terminal to its original configuration by disabling 'raw' mode.
     * <p>
     * This method is specifically designed for environments using a POSIX-compatible terminal,
     * such as Linux, and aims to revert any changes made to the terminal attributes during
     * the enabling of 'raw' mode (e.g., with the {@code enableRawMode} method).
     * <p>
     * The method checks if the original terminal configuration is available (stored in the
     * {@code originalConfig} field). If the configuration exists, it invokes the system call
     * `tcsetattr` via the {@code LibC} interface to restore the terminal’s attributes to their
     * original state.
     * <p>
     * Key Details:<br>
     * - If the terminal is a Linux platform, the {@code tcsetattr} function is invoked to reset
     * the terminal attributes. This re-enables canonical mode, echo, and other default terminal
     * behaviors that might have been disabled when raw mode was activated.<br>
     * - If the {@code originalConfig} field is null (indicating no previous configuration was
     * captured), the method does nothing, as there is no configuration to restore.<br>
     * <p>
     * Notes:<br>
     * - This method assumes that the terminal attribute changes made during raw mode activation
     * can be reversed without issue. If the terminal was modified unexpectedly outside
     * of the scope of this class, the behavior may be undefined.<br>
     * - The method relies on the {@code LibC} interface to invoke system-level operations, tying
     * this functionality to environments that support POSIX terminal control features.<br>
     * <p>
     * Platform-Specific Considerations:<br>
     * - The current implementation only applies to Linux systems. On non-Linux platforms,
     * invoking this method will have no effect unless a compatible mechanism is implemented.<br>
     * - The behavior may vary in non-standard terminal environments or embedded systems.<br>
     * <p>
     * Safeguards:<br>
     * - Checking if {@code originalConfig} is null ensures that this method avoids unintended
     * behavior when no prior configuration is available.<br>
     */
    @Override
    public void disableRawMode() {
        if (originalConfig == null) return;
        if (Platform.isLinux()) {
            LibC.INSTANCE.tcsetattr(0, 0, (Termios) originalConfig);
        }

    }

    /**
     * Obtains the terminal size.
     *
     * @return a TerminalSize object with the lines and columns of the terminal.
     */
    @Override
    public TerminalSize getTerminalSize() {
        Position inicialPosition = readCurrentPosition();
        gotoXY(10000, 10000); // Trick for getting the terminal size
        Position result = readCurrentPosition();
        gotoXY(inicialPosition.getCol(), inicialPosition.getLin());

        return new TerminalSize(result.getCol(), result.getLin());
    }

    /**
     * Resizes the console monitor by initiating a resize monitoring process.
     * This method invokes the {@code startResizeMonitor} function, which starts
     * a background daemon thread to continuously monitor changes in the terminal's dimensions.
     * Such changes could include updates to the number of rows or columns in the console
     * window.
     */
    @Override
    public void resizeConsoleMonitor() {
        startResizeMonitor();
    }

    /**
     * Reads the current cursor position in the terminal. The method switches the terminal
     * to 'raw' mode to process the position request, sends the appropriate ANSI escape
     * sequence to query the cursor position, captures the terminal's response, and parses
     * the result to create and return a {@link Position} object. If the position cannot
     * be determined due to an error, a default {@link Position} object with values (1, 1)
     * is returned. The terminal mode is restored to 'cooked' mode before the method exits.
     *
     * @return a {@link Position} object representing the current terminal cursor position,
     * or a default {@link Position} with values (1, 1) if the position cannot be determined
     */
    private Position readCurrentPosition() {
        // Executes position request; extracts or defaults on failure
        try {
            this.enableRawMode();
            System.out.print(REC_POS_CUR);

            return getPosition();

        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return new Position(1, 1);
        } finally {
            this.disableRawMode();
        }
    }

    /**
     * Reads and parses the cursor position from the terminal input stream. The method listens for
     * a specific ANSI escape sequence response, which represents the cursor's current position.
     * If the sequence matches the expected format, the coordinates are extracted and used to
     * create a {@link Position} object. If parsing fails, a default {@link Position} with values
     * (1, 1) is returned.
     *
     * @return a {@link Position} object representing the current cursor position in the
     * terminal, or a default {@link Position} with values (1, 1) in case of a failure
     * to correctly parse the terminal response.
     * @throws IOException if an error occurs while reading input from the terminal.
     */
    static Position getPosition() throws IOException {
        StringBuilder result = new StringBuilder();
        int character;

        do {
            character = System.in.read();
            if (character == 27) {
                result.append("^");
            } else {
                result.append((char) character);
            }
        } while (character != 82); // 'R'

        Pattern pattern = Pattern.compile("\\^\\[(\\d+);(\\d+)R");
        Matcher matcher = pattern.matcher(result.toString());
        if (matcher.matches()) {
            return new Position(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)));
        } else {
            return new Position(1, 1);
        }
    }

    /**
     * Moves the cursor to the specified position in the terminal based on the provided
     * x (column) and y (row) coordinates. The method uses an ANSI escape sequence to
     * position the cursor within the terminal.
     *
     * @param x the target column number to move the cursor to. Columns are numbered starting
     *          from 1, where 1 represents the leftmost column.
     * @param y the target row number to move the cursor to. Rows are numbered starting
     *          from 1, where 1 represents the topmost row.
     */
    private void gotoXY(int x, int y) {
        System.out.printf("\u001B[%d;%dH", y, x); // CSI n ; m H
    }

    /**
     * Moves the cursor to the specified position in the terminal.
     * The position is determined by the provided {@link Position} object,
     * where the line and column values are used to set the cursor position.
     *
     * @param screenPosition a {@link Position} object containing the desired
     *                       line and column numbers to move the cursor to.
     */
    private void gotoXY(Position screenPosition) {
        System.out.printf("\u001B[%d;%dH", screenPosition.getLin(), screenPosition.getCol()); // CSI n ; m H
    }

    // --- RESIZE MONITOR ---

    /**
     * Starts a monitoring thread that continuously checks for changes in the terminal's dimensions
     * (number of columns and rows). The method uses the `ioctl` system call to retrieve the terminal
     * size at regular intervals and compares it with the last known size. If a change is detected,
     * an event message is printed to indicate the new dimensions.
     * <p>
     * The monitoring process runs in the background as a daemon thread and checks for updates
     * approximately every 300 milliseconds. It stops when the `running` field is set to `false`.
     * <p>
     * Notes:<br>
     * - The method leverages platform-specific constants for the `ioctl` request, which differ
     * on macOS and other UNIX systems.<br>
     * - The starting size of the terminal is considered to be (-1, -1). The first change will not
     * trigger an event message if resizing starts at the default value.<br>
     * - This method is private and intended for internal use.<br>
     * <p>
     * Threads:<br>
     * - The thread is marked as a daemon to ensure it does not block application termination.<br>
     * <p>
     * Diagnosed Issues:<br>
     * - Any interruption to the thread will break the monitoring loop.<br>
     * - System calls such as `ioctl` may fail if the process does not have the appropriate
     * permissions or if the terminal does not support the required operations.<br>
     * <p>
     * Usage Context:<br>
     * This method is typically used to adapt terminal-based user interfaces or features
     * dynamically to changes in terminal size.
     */
    private void startResizeMonitor() {
        if (Platform.isLinux()) {
            Thread monitor = new Thread(() -> {
                while (running.get()) {
                    int cols, rows;
                    WinSize ws = new WinSize();
                    long request = Platform.isMac() ? 0x40087468L : 0x5413L;
                    LibC.INSTANCE.ioctl(0, request, ws);
                    cols = ws.ws_col;
                    rows = ws.ws_row;


                    if (cols != lastCols || rows != lastRows) {
                        if (lastCols != -1) {
                            System.out.printf("\r\n[EVENT] Ventana redimensionada: %d x %d\r\n", cols, rows);
                        }
                        lastCols = cols;
                        lastRows = rows;
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });
            monitor.setDaemon(true);
            monitor.start();
        }
    }

    @Override
    public String toString() {
        return "LinuxTerminal{'Access to the raw and cooked modes of the Linux terminal'}";
    }
}
