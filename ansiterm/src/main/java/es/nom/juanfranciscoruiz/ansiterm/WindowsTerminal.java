package es.nom.juanfranciscoruiz.ansiterm;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The WindowsTerminal class provides management and control methods for interacting with a Windows console.
 * It includes functionality for enabling raw mode, disabling raw mode, retrieving console dimensions,
 * and monitoring console resize events. This class relies on low-level Windows libraries like Kernel32
 * to manipulate the console's behavior and configuration.
 * <p>
 * Note:
 * - This class is designed specifically for Windows platforms due to its reliance on native libraries,
 * such as Kernel32 and MSVCRT, for its functionality.
 */
public class WindowsTerminal implements ITerminal {
    // --- CONSTANTS ---
    private static WindowsTerminal INSTANCE;

    /**
     * Represents the standard output handle used by the Windows console.
     * <p>
     * This constant is a predefined value uniquely identifying the standard
     * output stream in Windows systems. It is often utilized in low-level
     * interactions with the Windows API, particularly for console input/output
     * operations via the Kernel32 library or similar system libraries.
     */
    public static final int STD_OUTPUT_HANDLE = -11;
    /**
     * Constant representing the handle for the standard input (stdin) in the Windows console.
     * <p>
     * This value is used to reference the standard input stream and is passed to native
     * Windows API functions to perform operations such as reading input from the console.
     */
    public static final int STD_INPUT_HANDLE = -10;
    /**
     * Represents the standard error output handle for the Windows console.
     * <p>
     * This constant is used to identify the handle associated with the standard error stream
     * in the Windows operating system. It corresponds to a value defined by the Windows API.
     * <p>
     * Related handles:
     * - {@code STD_OUTPUT_HANDLE}: Standard output handle.
     * - {@code STD_INPUT_HANDLE}: Standard input handle.
     */
    public static final int STD_ERROR_HANDLE = -12;

    // --- NATIVE INTERFACES ---

    /**
     * Interface for interacting with the Microsoft C Runtime Library (MSVCRT).
     * <p>
     * This interface provides access to low-level input handling methods available in
     * the MSVCRT library. The library is dynamically loaded at runtime, enabling
     * native method calls to interact directly with the system's console input.
     * <p>
     * Methods in this interface are particularly useful for implementing features
     * like real-time keyboard input and keypress detection without buffering.
     * <p>
     * Platform-specific behavior:
     * - These methods are specific to Windows operating systems, as they depend
     * on the MSVCRT library.
     */
    public interface MSVCRT extends Library {
        /**
         * Singleton instance of the MSVCRT interface, dynamically loaded to provide
         * access to the native Microsoft C Runtime Library (MSVCRT) functions.
         * <p>
         * This variable serves as an entry point for invoking methods in the MSVCRT
         * library, such as low-level console input operations. It leverages the JNA
         * library to enable interaction with native code.
         * <p>
         * Note: The MSVCRT library is specific to Windows operating systems. Attempting
         * to use this instance on non-Windows platforms may result in undefined behavior.
         */
        MSVCRT INSTANCE = Native.load("msvcrt", MSVCRT.class);

        /**
         * Reads a single character from the console without echoing it to the screen.
         * This method corresponds to the C runtime library function `_getch`
         * in the Microsoft C Runtime Library (MSVCRT). It enables non-buffered
         * input for detecting keypresses in real time.
         *
         * @return The ASCII code of the character read from the console.
         * If the key corresponds to a special or function key, additional
         * calls to this method may be required to retrieve the additional keycode.
         */
        int _getch();

        /**
         * Determines if a keyboard key has been pressed.
         * This method checks the input buffer to verify if a keypress is available
         * for reading without blocking the execution of the program.
         *
         * @return A non-zero value if a keypress is available in the input buffer;
         * otherwise, returns 0 indicating no keypress is waiting.
         */
        int _kbhit();
    }

    /**
     * Provides access to native methods from the Windows Kernel32 DLL for console operations.
     * This interface relies on the JNA (Java Native Access) library to load the native library and
     * interact with low-level Windows API functions. It is specifically intended for advanced console
     * handling, including managing console input/output modes and retrieving console information.
     */
    public interface Kernel32 extends Library {
        /**
         * An instance of the {@link Kernel32} interface, which provides access to native methods
         * from the Windows Kernel32 DLL. This instance is created using the JNA (Java Native Access)
         * library to enable interaction with low-level Windows API functions for console management.
         * <p>
         * The {@code INSTANCE} variable serves as the primary access point to invoke methods
         * defined in the {@link Kernel32} interface. It allows Java applications to manage
         * console input/output modes, retrieve console information, and perform other advanced
         * console-related operations.
         */
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

        /**
         * Retrieves a handle to the specified standard device (standard input, standard output, or standard error).
         * This method is a native call to the Windows Kernel32 DLL.
         *
         * @param nStdHandle An integer value representing the standard device.
         *                   Use the following predefined constants:
         *                   <ul>
         *                   <li>STD_INPUT_HANDLE (-10): Standard input.</li>
         *                   <li>STD_OUTPUT_HANDLE (-11): Standard output.</li>
         *                   <li>STD_ERROR_HANDLE (-12): Standard error.</li>
         *                   </ul>
         * @return A `long` value representing the handle to the specified standard device.
         * If the function fails, the return value is `0` or `INVALID_HANDLE_VALUE`.
         */
        long GetStdHandle(int nStdHandle);

        /**
         * Retrieves the current input mode or output mode of a console associated with the specified handle.
         * This method is a native call to the Windows Kernel32 DLL.
         *
         * @param h      A handle to the console input buffer or the console screen buffer.
         *               The handle must have the GENERIC_READ access right.
         * @param lpMode A reference to an integer that receives the current mode of the console.
         *               For input handles, the mode can include values such as ENABLE_ECHO_INPUT or
         *               ENABLE_LINE_INPUT. For output handles, it can include values like
         *               ENABLE_PROCESSED_OUTPUT or ENABLE_WRAP_AT_EOL_OUTPUT.
         * @return A boolean value indicating the success or failure of the operation.
         * Returns {@code true} if the operation succeeds, or {@code false} if it fails.
         */
        boolean GetConsoleMode(long h, IntByReference lpMode);

        /**
         * Sets the input or output mode of a console associated with the specified handle.
         * This method is a native call to the Windows Kernel32 DLL.
         *
         * @param h      A handle to the console input buffer or the console screen buffer.
         *               For input handles, the handle must have the GENERIC_READ access right,
         *               and for output handles, the handle must have the GENERIC_WRITE access right.
         * @param dwMode The mode to be set for the console. For input handles, this can include flags
         *               such as ENABLE_ECHO_INPUT or ENABLE_LINE_INPUT. For output handles, it can
         *               include flags such as ENABLE_PROCESSED_OUTPUT or ENABLE_WRAP_AT_EOL_OUTPUT.
         * @return A boolean value indicating the success or failure of the operation.
         * Returns {@code true} if the operation succeeds, or {@code false} if it fails.
         */
        boolean SetConsoleMode(long h, int dwMode);

        /**
         * A structure representing a coordinate in the console screen buffer.
         * <p>
         * This structure is used by various Windows API functions to specify
         * the X and Y coordinates of a point in the console screen buffer.
         * <p>
         * This structure is typically utilized when interacting with the Windows
         * Kernel32 library to perform operations like setting the cursor position
         * or querying the buffer's geometry.
         */
        @Structure.FieldOrder({"X", "Y"})
        class COORD extends Structure {
            /**
             * The horizontal position of a point in the console screen buffer.
             * <p>
             * This field represents the X-coordinate, specified as a short value.
             * It is used in conjunction with the Y field to define a coordinate
             * within the console buffer when interacting with Windows API functions.
             */
            public short X, Y;
        }

        /**
         * Represents a rectangular region on the console screen buffer, typically used
         * in native Windows API operations for console manipulation. The SMALL_RECT
         * structure defines the coordinates of the rectangle with its top-left corner
         * and bottom-right corner.
         * <p>
         * The rectangle is specified by four fields:<br>
         * - Left: The X-coordinate (column) of the top-left corner.<br>
         * - Top: The Y-coordinate (row) of the top-left corner.<br>
         * - Right: The X-coordinate (column) of the bottom-right corner.<br>
         * - Bottom: The Y-coordinate (row) of the bottom-right corner.<br>
         *
         * <p>
         * This class is used in conjunction with native Windows console APIs to set
         * or retrieve rectangular regions.
         * <p>
         * It extends the {@code com.sun.jna.Structure} class, which enables interaction
         * with native code via the JNA (Java Native Access) library.
         */
        @Structure.FieldOrder({"Left", "Top", "Right", "Bottom"})
        class SMALL_RECT extends Structure {
            /**
             * The X-coordinate (column) of the top-left corner of the rectangular region.
             * Represents the horizontal starting position of the rectangle within the console screen buffer.
             */
            public short Left, Top, Right, Bottom;
        }

        /**
         * Represents the state of the console screen buffer, including its size,
         * cursor position, attributes, and window dimensions.
         * <p>
         * This structure is primarily utilized to store information retrieved from
         * the console screen buffer via native Windows API calls, such as
         * {@code GetConsoleScreenBufferInfo}. It provides the necessary details
         * for managing and interacting with the console's screen buffer.
         * <p>
         * Fields:<br>
         * - {@code dwSize}: A {@link COORD} object specifying the size of the
         * console screen buffer, with the number of rows (Y) and columns (X).<br>
         * - {@code dwCursorPosition}: A {@link COORD} object specifying the
         * current position of the cursor within the console screen buffer.<br>
         * - {@code wAttributes}: A short value representing the attributes of
         * the console screen buffer, such as text color and background color.<br>
         * - {@code srWindow}: A {@link SMALL_RECT} object defining the dimensions
         * of the window (viewport) within the console screen buffer.<br>
         * - {@code dwMaximumWindowSize}: A {@link COORD} object specifying the
         * maximum size of the console window, in terms of rows and columns.<br>
         * <p>
         * This class is part of the Windows Kernel32 console manipulation functionality
         * and extends {@code com.sun.jna.Structure}, allowing interaction with native
         * Windows API calls using the JNA (Java Native Access) library.
         */
        @Structure.FieldOrder({"dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize"})
       class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
            /**
             * Represents the structure CONSOLE_SCREEN_BUFFER_INFO, which is used to describe
             * the console screen buffer's state, including information about the size of the
             * buffer, the cursor position, text attributes, and window dimensions.
             * <p>
             * This is typically a part of native interaction with console APIs, enabling access
             * to low-level console state details.
             * <p>
             * The constructor initializes an instance of the CONSOLE_SCREEN_BUFFER_INFO structure.
             */
            public CONSOLE_SCREEN_BUFFER_INFO() {

            }

            /**
             * Represents the size of the console screen buffer in terms of its width and height.
             * <p>
             * This variable holds a {@link COORD} structure, where:<br>
             * - The {@code X} field specifies the width of the console screen buffer (number of columns).<br>
             * - The {@code Y} field specifies the height of the console screen buffer (number of rows).<br>
             * <p>
             * Used mainly in conjunction with Windows API functions to define or retrieve the
             * dimensions of the console screen buffer.
             */
            public COORD dwSize;
            /**
             * Represents the current position of the cursor within the console screen buffer.
             * <p>
             * This variable is a {@link COORD} structure that specifies the horizontal (X) and vertical (Y)
             * coordinates of the cursor in the console's screen buffer. The coordinates are measured in
             * character cells, with the origin (0, 0) located at the top-left corner of the console buffer.
             * <p>
             * Commonly used alongside Windows API functions, this field provides essential information
             * for operations involving cursor management or display updates within the console.
             */
            public COORD dwCursorPosition;
            /**
             * Represents the attribute information for characters written to a console screen buffer.
             * The value includes foreground and background color information, as well as other console
             * output attribute flags.
             * <p>
             * This field is part of the CONSOLE_SCREEN_BUFFER_INFO structure, which describes the
             * console screen buffer's state and properties.
             */
            public short wAttributes;
            /**
             * Represents the window dimensions of the console screen buffer.
             * <p>
             * The `srWindow` field defines the current viewport or visible rectangle of the
             * console screen buffer. It is a {@code SMALL_RECT} structure that specifies the
             * coordinates of the top-left and bottom-right corners of the window.
             * <p>
             * This field is part of the {@code CONSOLE_SCREEN_BUFFER_INFO} structure, which
             * is used in conjunction with Windows native API functions to manage and retrieve
             * information about the console screen buffer.
             * <p>
             * The dimensions represented by `srWindow` determine the portion of the console screen
             * buffer that is visible to the user in the console window. Modifying this value with the
             * appropriate API can adjust which part of the console screen buffer is displayed.
             */
            public SMALL_RECT srWindow;
            /**
             * Represents the maximum size of the console window based on the current screen buffer size,
             * measured in character rows and columns.
             * <p>
             * This field is defined as a {@link COORD} structure that specifies the largest possible
             * dimensions of the console window that can fit within the current console screen buffer.
             * It takes into account the visibility area constraints of the console window.
             * <p>
             * The `dwMaximumWindowSize` field is typically used in operations where determining or
             * modifying the console window's displayable size is necessary. This value can vary based
             * on the screen resolution and the size of the console screen buffer.
             */
            public COORD dwMaximumWindowSize;
        }

        /**
         * Retrieves information about the specified console screen buffer.
         * This method is a native call to the Windows Kernel32 DLL.
         *
         * @param h    A handle to the console screen buffer.
         *             The handle must have the GENERIC_READ access right.
         * @param info A reference to a {@code CONSOLE_SCREEN_BUFFER_INFO} structure
         *             that receives information about the console screen buffer,
         *             such as the size of the console screen buffer, the cursor position,
         *             and the current attributes of the console screen buffer.
         * @return A boolean value indicating the success or failure of the operation.
         * Returns {@code true} if the operation succeeds, or {@code false} if it fails.
         */
        boolean GetConsoleScreenBufferInfo(long h, CONSOLE_SCREEN_BUFFER_INFO info);
    }

    /**
     * Stores the original configuration of the Windows console input mode.
     * <p>
     * This variable holds the state of the console's input mode before it is modified
     * for "raw" mode operations. It is primarily utilized to restore the console back to
     * its original configuration after modifications are made, ensuring proper behavior
     * and system compatibility.
     * <p>
     * Key characteristics:
     * <ul>
     * <li>Used in conjunction with native Windows console APIs.</li>
     * <li>Null when the console mode has not been modified or stored.</li>
     * <li>Acts as a safeguard to revert any changes to the console's input mode state.</li>
     * </ul>
     * <p>
     * Usage context:
     * <ul>
     * <li>Set and referenced by methods such as `setRawMode` and `restoreMode`.</li>
     * <li>Intended for internal use within the WindowsTerminal implementation to manage
     *   platform-specific console behavior.</li>
     * </ul>
     * <p>
     * Platform-specific behavior:
     * <ul>
     * <li>Solely applicable on Windows operating systems due to dependency on the Kernel32 library.</li>
     * <li>Has no operational relevance on non-Windows platforms.</li>
     * </ul>
     */
    private Object originalConfig;

    /**
     * Represents the active state of the terminal operations and controls
     * the execution of background processes, such as monitoring tasks.
     * <p>
     * This flag is used to determine whether certain operations, such as
     * resizing the console window or monitoring events, should continue
     * running. It is managed as an atomic boolean to ensure thread-safe
     * operations in a multithreaded environment.
     * <p>
     * Behavior:<br>
     * - Initialized to `true` at the creation of the containing class.<br>
     * - Can be toggled externally to signal the termination of associated
     * processes or threads that rely on this flag.<br>
     * - Acts as a control mechanism for background threads to gracefully
     * stop their execution when set to `false`.<br>
     * <p>
     * Thread Safety:<br>
     * - The `AtomicBoolean` ensures atomic updates to the value, making
     * it safe for usage across multiple threads without explicit
     * synchronization.<br>
     */
        private final AtomicBoolean running = new AtomicBoolean(true);

    /**
     * Represents a mapping of key-value pairs used within the WindowsTerminal class.
     * This map serves as a storage mechanism for various string-based configuration
     * or state-related data relevant to the terminal's operation.
     * <p>
     * Characteristics:<br>
     * - The keys and values are both of type {@code String}.<br>
     * - The map is instantiated as a {@code HashMap}, ensuring no duplicate keys
     * and providing average constant-time performance for basic operations such
     * as get and put.<br>
     * <p>
     * Immutability:<br>
     * - The map is declared {@code final}, indicating that its reference cannot
     * be reassigned after initialization. However, modifications to the
     * contents of the map are still allowed unless further safeguarded.<br>
     * <p>
     * Usage Considerations:<br>
     * - This map is used internally by the {@code WindowsTerminal} class and
     * should not be directly manipulated outside of its enclosing scope.<br>
     * - The specific purposes and content of the map depend on the internal
     * logic and requirements of the class.<br>
     */
    private final Map<String, String> keyMap = new HashMap<>();

    /**
     * Stores the last known number of columns in the Windows console.
     * <p>
     * This field is used for tracking changes in the console's width during
     * runtime. It is updated by monitoring console resize events and helps
     * in detecting when the number of columns has been modified. This can
     * be useful for operations that depend on the dimensions of the terminal,
     * such as redrawing or layout adjustments.
     *
     * <p>
     * Field Characteristics:<br>
     * - Default value: -1 (indicates no size has been recorded yet).<br>
     * - Updated dynamically during runtime when console dimensions change.<br>
     * <p>
     * Thread-safety: This field may be accessed and modified by concurrent threads,
     * so appropriate synchronization should be ensured if accessed in a multi-threaded
     * environment.
     */
    private int lastCols = -1, lastRows = -1;

    /**
     * Private constructor for the WindowsTerminal class.
     * <p>
     * This constructor is intentionally defined as private to enforce
     * a singleton pattern or to restrict instantiation of the class
     * directly from outside. It ensures that instances of this class
     * can only be managed internally within the class or through a
     * controlled access method.
     */
    private  WindowsTerminal() {
    }

    /**
     * Retrieves the singleton instance of the {@code WindowsTerminal} class.
     * <p>
     * This method ensures that only one instance of the {@code WindowsTerminal} class
     * is created and returned. If an instance already exists, it is returned;
     * otherwise, a new instance is created.
     *
     * @return the singleton instance of the {@code WindowsTerminal} class
     */
    public static WindowsTerminal getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new WindowsTerminal();
        }
        return INSTANCE;
    }

    /**
     * Enables the 'raw' mode of the Windows console
     */
    @Override
    public void enableRawMode() {
        setRawMode();
    }

    /**
     * Disables the 'raw' mode and enables the normal mode of the Windows console
     */
    @Override
    public void disableRawMode() {
        restoreMode();
    }

    /**
     * Gets the console size
     *
     * @return A TerminalSize object with the current lines and columns of the
     * console
     * @see es.nom.juanfranciscoruiz.ansiterm.TerminalSize
     */
    @Override
    public TerminalSize getTerminalSize() {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = queryConsoleScreenBufferInfo();
        int columns = info.srWindow.Right - info.srWindow.Left + 1;
        int lines = info.srWindow.Bottom - info.srWindow.Top + 1;
        return new TerminalSize(columns, lines);
    }

    /**
     * Initiates the monitoring of console resize events.
     * <p>
     * This method serves as an entry point to start observing changes in the console
     * window size. Internally, it invokes the {@code startResizeMonitor} method,
     * which runs a background thread to periodically check and log changes in the
     * console's dimensions, such as the number of columns and rows.
     * <p>
     * Behavior:<br>
     * - Delegates the operation to the {@code startResizeMonitor} method, which takes
     * care of continuously monitoring the console size in a separate thread.<br>
     * <p>
     * Preconditions:<br>
     * - The console environment and required handles must be properly initialized
     * before invoking this method.<br>
     * <p>
     * Postconditions:<br>
     * - Resizing of the console window is detected and logged in real-time as long
     * as the monitoring mechanism is running.<br>
     */
    @Override
    public void resizeConsoleMonitor() {
        startResizeMonitor();
    }

    // Private methods to implement the public methods of the interface

    /**
     * Configures the Windows console to operate in "raw" mode by disabling specific input processing features.
     * <p>
     * This method disables the following console input modes:<br>
     * - PROCESSED_INPUT (0x0001): Disables the processing of special input characters (e.g., Ctrl+C signals).<br>
     * - LINE_INPUT (0x0002): Disables input line buffering, making input immediately available.<br>
     * - ECHO_INPUT (0x0004): Prevents keystrokes from being displayed in the console.<br>
     * <p>
     * The original console mode is stored in the `originalConfig` field for restoration later.
     * This is specifically implemented for Windows platforms and relies on low-level interaction with
     * the Kernel32 library.
     */
    private void setRawMode() {
        if (Platform.isWindows()) {
            long h = Kernel32.INSTANCE.GetStdHandle(STD_INPUT_HANDLE);
            IntByReference mode = new IntByReference();
            Kernel32.INSTANCE.GetConsoleMode(h, mode);
            originalConfig = mode.getValue();
            // Deshabilitar: PROCESSED_INPUT(1), LINE_INPUT(2), ECHO_INPUT(4)
            Kernel32.INSTANCE.SetConsoleMode(h, mode.getValue() & ~0x0007);
        }
    }

    /**
     * Restores the Windows console to its original input mode configuration.
     * <p>
     * This method is designed to revert the console mode to its initial state
     * using the configuration stored in the `originalConfig` field. It ensures
     * that any changes made to the console input mode (e.g., enabling "raw" mode)
     * are undone, and the console operates in its default configuration.
     * <p>
     * Behavior:<br>
     * - If the `originalConfig` field is null, the method performs no action.<br>
     * - On Windows platforms, the console mode is restored using the value in
     * `originalConfig` through the Kernel32 library.<br>
     * - This method directly interacts with native system libraries and is
     * intended only for use on Windows operating systems.<br>
     * - On non-Windows platforms, the method has no effect.<br>
     * <p>
     * Preconditions:<br>
     * - The console input mode must have been modified previously, and the
     * original configuration must be stored in the `originalConfig` field.<br>
     * <p>
     * Postconditions:<br>
     * - The console input mode is restored to the state prior to modification,
     * if applicable.<br>
     */
    private void restoreMode() {
        if (originalConfig == null) return;
        if (Platform.isWindows()) {
            Kernel32.INSTANCE.SetConsoleMode(Kernel32.INSTANCE.GetStdHandle(STD_INPUT_HANDLE), (int) originalConfig);
        }
    }

    /**
     * Starts a background thread to monitor console window resizing events.
     * <p>
     * This method runs a daemon thread that periodically checks the console window dimensions
     * (columns and rows). If a resize event is detected, it logs the new dimensions to the console.
     * <p>
     * Behavior:<br>
     * - On Windows, it retrieves console dimensions using the Kernel32 library.<br>
     * - If the window dimensions change, an event message is printed to indicate the new size.<br>
     * - The thread sleeps for a fixed interval (300 milliseconds) between each size check.<br>
     * - The monitoring continues as long as the `running` flag is set to `true`.<br>
     * - The thread terminates gracefully if interrupted.<br>
     * <p>
     * Preconditions:<br>
     * - The `Kernel32.INSTANCE.GetConsoleScreenBufferInfo` method relies on valid and accessible
     * Windows console output handles.<br>
     * - The `running` flag must be properly managed externally to signal the thread when to stop.<br>
     * <p>
     * Postconditions:<br>
     * - The dimensions of the console window are monitored, and changes are logged.<br>
     * - The monitoring thread is set as a daemon and automatically terminates when the main application ends.<br>
     */
    private void startResizeMonitor() {
        Thread monitor = new Thread(() -> {
            while (running.get()) {
                int cols = 0, rows = 0;
                if (Platform.isWindows()) {
                    Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = queryConsoleScreenBufferInfo();
                    cols = info.srWindow.Right - info.srWindow.Left + 1;
                    rows = info.srWindow.Bottom - info.srWindow.Top + 1;
                }
                if (cols != lastCols || rows != lastRows) {
                    if (lastCols != -1) {
                        System.out.printf("\r\n[EVENT] Resized window: %d x %d\r\n", cols, rows);
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

    private Kernel32.CONSOLE_SCREEN_BUFFER_INFO queryConsoleScreenBufferInfo() {
        long stdoutHandle = Kernel32.INSTANCE.GetStdHandle(STD_OUTPUT_HANDLE);
        if (stdoutHandle == 0L || stdoutHandle == -1L) {
            throw new IllegalStateException("Could not obtain the standard output handle");
        }

        Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        if (!Kernel32.INSTANCE.GetConsoleScreenBufferInfo(stdoutHandle, info)) {
            throw new IllegalStateException("Could not read console screen buffer info");
        }

        return info;
    }

    @Override
    public String toString() {
        return "WindowsTerminal{'Access to the raw and cooked modes of the Windows console with JNI API and ANSI escape sequences'}";
    }
}
