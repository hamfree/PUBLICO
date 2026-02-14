package es.nom.juanfranciscoruiz.utiles.impl;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import es.nom.juanfranciscoruiz.utiles.TermCtl;
import es.nom.juanfranciscoruiz.utiles.model.Dimensions;

/**
 * Implementation of the TermCtl interface providing terminal control for clearing the screen,
 * retrieving terminal dimensions, and setting terminal dimensions. This class manages platform-specific
 * handling for Windows and Unix-based systems by using JNA (Java Native Access) to interact with
 * low-level system libraries.
 */
public class TermCtlImpl implements TermCtl {

    /**
     * Interface that provides access to native Kernel32 library functions for managing the console screen buffer on
     * Windows platforms. This interface leverages JNA (Java Native Access) to enable interaction with native system
     * calls and structures.
     */
    public interface Kernel32 extends StdCallLibrary {
        /**
         * An instance of the Kernel32 interface that provides access to functions in the Windows Kernel32 library.
         * This variable is initialized using JNA (Java Native Access) to load the "kernel32" library and
         * bind its native methods to the methods defined in the Kernel32 interface.
         * <p>
         * Kernel32 contains methods for interacting with low-level system resources, such as file I/O,
         * console management, and process handling in the Windows operating system.
         */
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

        // We reuse the necessary structures

        /**
         * Represents a coordinate on a console screen buffer with X and Y positions.
         * <p>
         * This class is a structure used primarily in interactions with the Windows
         * console API to specify or retrieve coordinate positions within a console
         * screen buffer. The X and Y fields represent the horizontal (column) and
         * vertical (row) positions, respectively.
         * <p>
         * The COORD structure is typically used in methods for manipulating console
         * screen buffers, such as setting buffer sizes or retrieving cursor positions.
         */
        @com.sun.jna.Structure.FieldOrder({"X", "Y"})
        class COORD extends com.sun.jna.Structure {
            /**
             * Represents a coordinate on a console screen buffer with X and Y positions.
             * <p>
             * This class is a structure used primarily in interactions with the Windows
             * console API to specify or retrieve coordinate positions within a console
             * screen buffer. The X and Y fields represent the horizontal (column) and
             * vertical (row) positions, respectively.
             * <p>
             * The COORD structure is typically used in methods for manipulating console
             * screen buffers, such as setting buffer sizes or retrieving cursor positions.
             */
            public short X, Y;

            /**
             * Constructs a new instance of the COORD structure.
             */
            public COORD() {
                this.X = (short) 0;
                this.Y = (short) 0;
            }

            /**
             * Constructs a new instance of the COORD structure with specified X and Y coordinates.
             *
             * @param x The X coordinate (column) of the coordinate.
             * @param y The Y coordinate (row) of the coordinate.
             */
            public COORD(int x, int y) {
                this.X = (short) x;
                this.Y = (short) y;
            }
        }

        /**
         * Represents a rectangular region in a console screen buffer.
         * <p>
         * This class is primarily used in conjunction with native methods for interacting with the Windows console.
         * The rectangular region is defined by its left, top, right, and bottom coordinates, which represent
         * character-cell positions relative to the console screen buffer's coordinate system.
         * <p>
         * The SMALL_RECT structure is often used when specifying the size or position of a console window or
         * a subsection of a console screen buffer. The fields within this structure are represented as short
         * integers to maintain compatibility with the Windows API.
         * <p>
         * Fields:
         * <ul>
         * <li>Left: The x-coordinate of the upper-left corner of the rectangle.</li>
         * <li>Top: The y-coordinate of the upper-left corner of the rectangle.</li>
         * <li>Right: The x-coordinate of the lower-right corner of the rectangle.</li>
         * <li>Bottom: The y-coordinate of the lower-right corner of the rectangle.</li>
         * </ul>
         */
        @com.sun.jna.Structure.FieldOrder({"Left", "Top", "Right", "Bottom"})
        class SMALL_RECT extends com.sun.jna.Structure {
            /**
             * Represents a rectangle defined by its top-left and bottom-right corners in a console screen buffer.
             * <p>
             * This class is a structure used primarily in interactions with the Windows console API to specify
             * or retrieve rectangular regions within a console screen buffer. The Left, Top, Right, and Bottom
             * fields represent the x and y coordinates of the rectangle's corners.
             * <p>
             * The SMALL_RECT structure is typically used in methods for manipulating console screen buffers,
             * such as setting window sizes or retrieving cursor positions.
             */
            public short Left, Top, Right, Bottom;

            /**
             * Constructs a new instance of the SMALL_RECT structure.
             */
            public SMALL_RECT() {
                this.Left = (short) 0;
                this.Top = (short) 0;
                this.Right = (short) 0;
                this.Bottom = (short) 0;
            }

            /**
             * Constructs a new instance of the SMALL_RECT structure with specified coordinates.
             *
             * @param left   The x-coordinate of the upper-left corner of the rectangle.
             * @param top    The y-coordinate of the upper-left corner of the rectangle.
             * @param right  The x-coordinate of the lower-right corner of the rectangle.
             * @param bottom The y-coordinate of the lower-right corner of the rectangle.
             */
            public SMALL_RECT(int left, int top, int right, int bottom) {
                this.Left = (short) left;
                this.Top = (short) top;
                this.Right = (short) right;
                this.Bottom = (short) bottom;
            }
        }

        /**
         * Represents the current state and characteristics of a console screen buffer.
         * <p>
         * This structure is utilized in conjunction with various Windows API functions to retrieve
         * or manage attributes of a console screen buffer, such as its dimensions, cursor position,
         * text attributes, and window size. The data within this structure provides detailed information
         * regarding the console's display and configuration at a specific point in time.
         * <p>
         * Fields:
         * <ul>
         * <li>dwSize: A COORD structure containing the width (X) and height (Y) of the console screen buffer, in character cells.</li>
         * <li>dwCursorPosition: A COORD structure containing the current position of the cursor within the console screen buffer.</li>
         * <li>wAttributes: The current attributes of the text (e.g., colors or styles) within the console screen buffer.</li>
         * <li>srWindow: A SMALL_RECT structure that defines the size and position of the current window within the console screen buffer.</li>
         * <li>dwMaximumWindowSize: A COORD structure representing the maximum dimensions (width and height) of the console window,
         * based on the screen size and the current screen font.</li>
         * </ul>
         * <p>
         * This structure is commonly used for querying or modifying console state through functions such as:
         * <ul>
         * <li>GetConsoleScreenBufferInfo</li>
         * <li>SetConsoleWindowInfo</li>
         * <li>SetConsoleScreenBufferSize</li>
         * </ul>
         */
        @Structure.FieldOrder({"dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize"})
        class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
            /**
             * Represents the dimensions of the console screen buffer, in character cells.
             * <p>
             * This field is a COORD structure containing two properties:
             * <ul>
             * <li>X: The width of the console screen buffer, measured in columns.</li>
             * <li>Y: The height of the console screen buffer, measured in rows.</li>
             * </ul>
             * <p>
             * Used in conjunction with Windows API functions for querying or modifying
             * console screen buffer size.
             */
            public COORD dwSize;
            /**
             * Represents the current cursor position within the console screen buffer.
             */
            public COORD dwCursorPosition;
            /**
             * Represents the current attributes of the text within the console screen buffer.
             */
            public short wAttributes;
            /**
             * Defines the size and position of the current window within the console screen buffer.
             * <p>
             * This field is a SMALL_RECT structure that represents the rectangular region in the console
             * screen buffer corresponding to the visible window. The coordinates within this structure
             * specify the boundaries of the window:
             * <ul>
             * <li>Left: The x-coordinate of the upper-left corner of the window.</li>
             * <li>Top: The y-coordinate of the upper-left corner of the window.</li>
             * <li>Right: The x-coordinate of the lower-right corner of the window.</li>
             * <li>Bottom: The y-coordinate of the lower-right corner of the window.</li>
             * </ul>
             * <p>
             * Used in conjunction with Windows API functions to retrieve or adjust the size and position
             * of the console window within the overall screen buffer.
             */
            public SMALL_RECT srWindow;
            /**
             * Represents the maximum size of the console window, measured in character columns (X)
             * and rows (Y), based on the current console screen buffer and screen resolution.
             * <p>
             * The `dwMaximumWindowSize` field is a COORD structure containing the maximum
             * dimensions that the console window can be resized to. This value is determined
             * by the dimensions of the console screen buffer and the current size of the display
             * window.
             * <p>
             * This field is typically used in conjunction with the Windows console API to determine
             * or enforce window and buffer size constraints.
             */
            public COORD dwMaximumWindowSize;

            /**
             * Constructs a new instance of the CONSOLE_SCREEN_BUFFER_INFO structure.
             */
            public CONSOLE_SCREEN_BUFFER_INFO() {
            }

        }

        /**
         * Retrieves a handle to the specified standard device (standard input, standard output, or standard error).
         * This handle can be used in subsequent calls to functions requiring a handle to one of these devices.
         *
         * @param nStdHandle The standard device identifier. Possible values are:
         *                   <ul>
         *                   <li>{@code STD_INPUT_HANDLE}: Standard input</li>
         *                   <li>{@code STD_OUTPUT_HANDLE}: Standard output</li>
         *                   <li>{@code STD_ERROR_HANDLE}: Standard error</li>
         *                   </ul>
         * @return A handle to the specified standard device if the function succeeds;
         * otherwise, returns {@code null}. To retrieve extended error information, call GetLastError.
         */
        WinNT.HANDLE GetStdHandle(int nStdHandle);

        /**
         * Retrieves information about the specified console screen buffer.
         *
         * @param hConsoleOutput            A handle to the console screen buffer. This handle must have the GENERIC_READ access right.
         * @param lpConsoleScreenBufferInfo A pointer to a CONSOLE_SCREEN_BUFFER_INFO structure that receives the console
         *                                  screen buffer information, including the size of the buffer, the cursor position,
         *                                  the text attributes, the size of the window, and the maximum size of the window.
         * @return {@code true} if the function succeeds; {@code false} otherwise. To get extended error information,
         * call GetLastError.
         */
        boolean GetConsoleScreenBufferInfo(WinNT.HANDLE hConsoleOutput, CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);

        /**
         * Sets the size of the specified console screen buffer.
         *
         * @param hConsoleOutput A handle to the console screen buffer. The handle must have the GENERIC_WRITE access right.
         * @param dwSize         A COORD structure that specifies the new size of the console screen buffer, in character
         *                       columns (X) and rows (Y).
         * @return {@code true} if the function succeeds; {@code false} otherwise. To get extended error information,
         * call GetLastError.
         */
        boolean SetConsoleScreenBufferSize(WinNT.HANDLE hConsoleOutput, Kernel32.COORD dwSize);

        /**
         * Sets the current size and position of a console screen buffer's window.
         *
         * @param hConsoleOutput  A handle to the console screen buffer. This handle must have the GENERIC_WRITE access right.
         * @param bAbsolute       Specifies whether the coordinates in the lpConsoleWindow parameter are absolute or relative
         *                        to the current window's position. If {@code true}, the coordinates are absolute. If {@code false},
         *                        the coordinates are relative to the current window.
         * @param lpConsoleWindow A SMALL_RECT structure that specifies the new position and size of the console window.
         *                        The coordinates of the structure are in character-cell columns and rows relative to the
         *                        console screen buffer's upper-left corner.
         * @return {@code true} if the function succeeds; {@code false} otherwise. To get extended error information, call GetLastError.
         */
        boolean SetConsoleWindowInfo(WinNT.HANDLE hConsoleOutput, boolean bAbsolute, Kernel32.SMALL_RECT lpConsoleWindow);
    }

    /**
     * Represents the standard output handle used in Windows API interactions.
     * This constant is mapped to the value assigned by the Windows operating
     * system for accessing the standard output (console output) buffer.
     * It is primarily used in platform-specific operations such as console
     * configuration, resizing, or writing directly to the standard output
     * buffer through native system calls.
     * <p>
     * Value: -11, which is the predefined identifier for the standard output
     * handle in the Windows API.
     */
    private static final int STD_OUTPUT_HANDLE = -11;

    /**
     * Constructs a new instance of the TermCtlImpl class.
     */
    public TermCtlImpl() {
    }

    /**
     * Sets the current console dimensions.
     *
     * @param cols Number of columns
     * @param rows Number of rows
     * @return true if successful
     */
    public static boolean setConsoleSize(int cols, int rows) {
        Kernel32 k32 = Kernel32.INSTANCE;
        WinNT.HANDLE hConsole = k32.GetStdHandle(STD_OUTPUT_HANDLE);
    
    /*
    1. Define the window rectangle (0-based, hence the subtraction of 1)
    It is vital that the size is less than or equal to the current buffer size.
    */
        Kernel32.SMALL_RECT rect = new Kernel32.SMALL_RECT(0, 0, cols - 1, rows - 1);

        // 2. Define the buffer size
        Kernel32.COORD size = new Kernel32.COORD(cols, rows);
    
    /*
    Safe execution order in Windows: We try to adjust the buffer first to fit the window,
    or the window first if the buffer is already large. The most robust approach is to
    try both.
    */
        k32.SetConsoleScreenBufferSize(hConsole, size);
        return k32.SetConsoleWindowInfo(hConsole, true, rect);
    }

    /**
     * Interface representing native calls to the standard C library (libc).
     * This interface provides methods for interacting with low-level system operations,
     * such as retrieving and manipulating console or terminal properties.
     * <p>
     * This wrapper relies on the JNA (Java Native Access) library to load the native library
     * and expose its functionality through Java code. The availability of certain features
     * may depend on the platform being used.
     */
    public interface LibC extends Library {
        /**
         * A static instance of the {@link LibC} interface, representing the native C library.
         * This instance is conditionally loaded based on the underlying platform:
         * - On non-Windows platforms, the standard C library (libc) is dynamically loaded using
         * the JNA (Java Native Access) library.
         * - On Windows platforms, this instance is set to {@code null}, as libc is not available.
         * <p>
         * This instance provides access to native system calls defined in the libc library,
         * enabling low-level operations such as terminal or console management.
         * <p>
         * Note that platform compatibility should be considered when using this instance,
         * as certain functionality may not be available or behave differently depending on
         * the operating system.
         */
        LibC INSTANCE = !Platform.isWindows() ? Native.load("c", LibC.class) : null;

        /**
         * Represents the dimensions and pixel resolution of a terminal or console window.
         * This structure is commonly used in conjunction with native system calls to retrieve
         * or manipulate terminal window size parameters.
         * <p>
         * Field details:
         * <ul>
         * <li>{@code ws_row}: The number of rows (character height) in the terminal window.</li>
         * <li>{@code ws_col}: The number of columns (character width) in the terminal window.</li>
         * <li>{@code ws_xpixel}: The width of the terminal window in pixels.</li>
         * <li>{@code ws_ypixel}: The height of the terminal window in pixels.</li>
         * </ul>
         * <p>
         * This class uses the JNA (Java Native Access) library and is annotated with
         * {@link Structure.FieldOrder} to define the order of its fields as expected by the
         * corresponding native structure.
         */
        @Structure.FieldOrder({"ws_row", "ws_col", "ws_xpixel", "ws_ypixel"})
        class WinSize extends Structure {
            /**
             * Represents the dimensions and pixel resolution of a terminal or console window.
             */
            public short ws_row, ws_col, ws_xpixel, ws_ypixel;

            /**
             * Constructs a new instance of the {@link WinSize} structure.
             */
            public WinSize() {
            }
        }

        /**
         * Executes the ioctl system call, which performs device-specific input/output operations
         * or manipulates the parameters of a file descriptor.
         *
         * @param fd      The file descriptor referencing an open file, device, or terminal.
         * @param request The request code specifying the operation to be performed. This value
         *                is specific to the platform and the type of file or device.
         * @param ws      A reference to a {@link WinSize} structure that is used as an input
         *                or output buffer, depending on the specified request. This structure
         *                may hold or receive terminal size and resolution information.
         * @return An integer result code indicating the outcome of the operation. A return value
         * of 0 typically indicates success, while a negative value indicates an error.
         */
        int ioctl(int fd, long request, WinSize ws);
    }

    /**
     * Retrieves the current size of the console or terminal window in terms of rows and columns.
     * The method determines the operating system and fetches the dimensions using the appropriate
     * platform-specific implementation.
     *
     * @return a {@code Dimensions} object representing the number of rows and columns of
     * the console window, or {@code null} if the dimensions cannot be determined.
     */
    @Override
    public Dimensions getConsoleSize() {
        if (Platform.isWindows()) {
            return getWinDimensions();
        } else {
            return getUnixDimensions();
        }
    }

    /**
     * Clears the console or terminal screen by using either ANSI escape codes or
     * by printing a sufficient amount of blank lines to simulate a cleared screen.
     *
     * @param useANSI a boolean value indicating whether to use ANSI escape codes
     *                for clearing the screen. If {@code true}, ANSI codes will
     *                attempt to clear the screen. If {@code false}, the screen
     *                will be cleared by printing multiple blank lines.
     */
    @Override
    public void clearScreen(boolean useANSI) {
        if (useANSI) {
            System.out.print("\033[H\033[2J");
        } else {
            // We don't know the console size, so we'll just print enough newlines to clear it.
            for (int i = 0; i < 150; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Adjusts the size of the console or terminal window to the specified dimensions.
     * The method determines the operating system and uses platform-specific mechanisms
     * to attempt to resize the console. For Windows, it uses native Kernel32 functions
     * and ANSI escape sequences as a fallback. For non-Windows systems, it sends an
     * ANSI resize command directly.
     *
     * @param dimensions the desired dimensions for the console, represented by a
     *                   {@code Dimensions} object containing the number of rows
     *                   and columns
     * @return {@code true} if the resizing operation is executed (regardless of its
     * outcome), or {@code false} if the provided dimensions are invalid
     * (e.g., non-positive values)
     */
    @Override
    public boolean setConsoleSize(Dimensions dimensions) {
        String os = System.getProperty("os.name").toLowerCase();
        int cols = dimensions.columns();
        int rows = dimensions.rows();

        if (cols <= 0 || rows <= 0) {
            return false;
        }

        if (os.contains("win")) {
            Kernel32 k32 = Kernel32.INSTANCE;
            WinNT.HANDLE hConsole = k32.GetStdHandle(STD_OUTPUT_HANDLE);
    
      /*
    1. Define the window rectangle (0-based, hence the subtraction of 1)
    It is vital that the size is less than or equal to the current buffer size.
    */
            Kernel32.SMALL_RECT rect = new Kernel32.SMALL_RECT(0, 0, cols - 1, rows - 1);

            // 2. Define the buffer size
            Kernel32.COORD size = new Kernel32.COORD(cols, rows);
    
      /*
    Safe execution order in Windows: We try to adjust the buffer first to fit the window,
    or the window first if the buffer is already large. The most robust approach is to
    try both.
    */
            boolean success = k32.SetConsoleScreenBufferSize(hConsole, size);
            k32.SetConsoleWindowInfo(hConsole, true, rect);

      /*
      If it fails or we don't have JNA, we try the ANSI sequence (works in
      Windows Terminal)
       */
            if (!success) {
                sendAnsiResize(cols, rows);
            }
        } else {
            sendAnsiResize(cols, rows);
        }
        return true;
    }

    /**
     * Retrieves the dimensions of the console window on a Windows operating system.
     * This method uses the Kernel32 native library to fetch details about the current
     * console's screen buffer info and calculates the number of rows and columns
     * visible in the console window.
     *
     * @return a {@code Dimensions} object representing the number of rows and columns
     * of the console window, or {@code null} if the dimensions cannot be determined.
     */
    private static Dimensions getWinDimensions() {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        var hConsole = Kernel32.INSTANCE.GetStdHandle(-11); // STD_OUTPUT_HANDLE
        if (Kernel32.INSTANCE.GetConsoleScreenBufferInfo(hConsole, info)) {
            return new Dimensions(
                    info.srWindow.Bottom - info.srWindow.Top + 1,
                    info.srWindow.Right - info.srWindow.Left + 1
            );
        }
        return null;
    }

    /**
     * Retrieves the current dimensions of a Unix-based console or terminal window in terms of rows and columns.
     * This method uses the TIOCGWINSZ ioctl system call to get the console size, which is supported
     * on Linux and macOS/BSD platforms.
     *
     * @return a {@code Dimensions} object representing the number of rows and columns of the console window,
     * or {@code null} if the dimensions cannot be determined.
     */
    private static Dimensions getUnixDimensions() {
        LibC.WinSize ws = new LibC.WinSize();
        // TIOCGWINSZ: 0x5413 (Linux), 0x40087468 (macOS/BSD)
        long TIOCGWINSZ = Platform.isMac() ? 0x40087468L : 0x5413L;

        // 1 is the file descriptor for stdout
        if (LibC.INSTANCE.ioctl(1, TIOCGWINSZ, ws) == 0) {
            return new Dimensions(ws.ws_row, ws.ws_col);
        }
        return null;
    }

    /**
     * Send the CSI (Control Sequence Introducer) sequence to resize.
     * \033[8;rows;columns;t
     */
    private void sendAnsiResize(int cols, int rows) {
        // \033 is the octal escape, [8 is the size command, ;t is the closing.
        System.out.print(String.format("\033[8;%d;%dt", rows, cols));
        System.out.flush();
    }
}
