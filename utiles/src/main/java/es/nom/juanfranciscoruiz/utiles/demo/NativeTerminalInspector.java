package es.nom.juanfranciscoruiz.utiles.demo;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.win32.StdCallLibrary;

/**
 * The NativeTerminalInspector class is responsible for inspecting the native terminal
 * (console) dimensions by using the Windows Kernel32 API through the Java Native Access (JNA) library.
 * It retrieves the current console's dimensions, including the width (columns) and height (rows).
 */
public class NativeTerminalInspector {

  /**
   * Constructs a new instance of the NativeTerminalInspector class.
   */
  public NativeTerminalInspector() {
  }

  /**
   * The Kernel32 interface represents a binding to the native Windows Kernel32 library
   * using JNA (Java Native Access). It provides methods for interacting with Windows
   * console functions and retrieving console information.
   */
  public interface Kernel32 extends StdCallLibrary {
    /**
     * Singleton instance of the {@link Kernel32} interface, providing access to functions
     * from the Kernel32 library loaded via the JNA {@link Native#load} method. This instance
     * enables interaction with low-level Windows API functionalities, such as console
     * management and standard device handling.
     * <p>
     * The `INSTANCE` variable serves as the primary access point for invoking methods
     * defined in the {@link Kernel32} interface. The underlying library is dynamically
     * loaded at runtime, typically relying on the "kernel32" system library.
     */
    Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

    /**
     * Represents a coordinate structure with X and Y values. This structure is
     * commonly used in console-related operations to define positions or dimensions
     * in a two-dimensional space, such as cursor positions or window sizes.
     * <p>
     * The `X` field represents the horizontal coordinate, while the `Y` field
     * represents the vertical coordinate. Both values are of type `short`.
     * <p>
     * This structure is typically utilized as part of interactions with
     * native console APIs, particularly when working with the Windows Kernel32
     * library via JNA (Java Native Access).
     */
    @Structure.FieldOrder({"X", "Y"})
    class COORD extends Structure {
      /**
       * Default constructor for the COORD structure.
       * Initializes a new instance with default values for the X and Y fields.
       * This constructor is typically used to create a `COORD` object before setting
       * specific coordinate values.
       */
      public COORD() {}
      /**
       * Represents the horizontal coordinate in a two-dimensional space.
       * Used to define the X-axis position within the {@code COORD} structure,
       * typically in the context of console-related operations such as cursor positions
       * or window dimensions. The value is expressed as a {@code short}.
       */
      public short X;
      /**
       * Represents the vertical coordinate in a two-dimensional space.
       * Used to define the Y-axis position within the {@code COORD} structure,
       * typically in the context of console-related operations such as cursor positions
       * or window dimensions. The value is expressed as a {@code short}.
       */
      public short Y;
    }

    /**
     * Represents a rectangular area using four coordinates: left, top, right, and bottom.
     * This structure is commonly used in console-based operations to define dimensions
     * or boundaries for a rectangular region, such as a console window or a subsection
     * of the screen buffer.
     * <p>
     * The coordinates are represented as short integers:
     * - `Left` specifies the x-coordinate of the left edge of the rectangle.
     * - `Top` specifies the y-coordinate of the top edge of the rectangle.
     * - `Right` specifies the x-coordinate of the right edge of the rectangle.
     * - `Bottom` specifies the y-coordinate of the bottom edge of the rectangle.
     * <p>
     * This structure is typically utilized as part of native Windows API interactions
     * via JNA (Java Native Access), particularly in scenarios that involve manipulating
     * the console screen buffer or window layout.
     */
    @Structure.FieldOrder({"Left", "Top", "Right", "Bottom"})
    class SMALL_RECT extends Structure {
      /**
       * Constructs a new instance of the SMALL_RECT structure, which represents a rectangular
       * region defined by its left, top, right, and bottom coordinates. This constructor
       * initializes the object without setting any specific values to the fields, allowing
       * the caller to define the desired coordinates after the instance is created.
       */
      public SMALL_RECT() {}
      /**
       * Represents the x-coordinate of the left boundary of a rectangular region.
       * This value is a short integer and is used to define or manipulate the horizontal
       * position of the left edge of a rectangle, particularly in the context of
       * graphical or console-based operations.
       */
      public short Left;
      /**
       * Represents the y-coordinate of the top boundary of a rectangular region.
       * This value is a short integer and is used to define or manipulate the vertical
       * position of the top edge of a rectangle, particularly in the context of
       * graphical or console-based operations.
       */
      public short Top;
      /**
       * Represents the x-coordinate of the right boundary of a rectangular region.
       * This value is a short integer and is used to define or manipulate the horizontal
       * position of the right edge of a rectangle, particularly in the context of
       * graphical or console-based operations.
       */
      public short Right;
      /**
       * Represents the y-coordinate of the bottom boundary of a rectangular region.
       * This value is a short integer and is used to define or manipulate the vertical
       * position of the bottom edge of a rectangle, particularly in the context of
       * graphical or console-based operations.
       */
      public short Bottom;
    }

    /**
     * Represents information about the console screen buffer, including its dimensions,
     * cursor position, attributes, and window information. This structure is primarily used
     * in console-based operations to retrieve or manipulate the state of the console screen
     * buffer in a Windows system.
     * <p>
     * The structure is defined with the following fields:
     * <p>
     * - `dwSize`: Specifies the size of the console screen buffer, represented as a
     *   {@link COORD} structure containing the width and height in character cells.
     * <p>
     * - `dwCursorPosition`: Specifies the current position of the cursor within the
     *   console screen buffer, represented as a {@link COORD} structure.
     * <p>
     * - `wAttributes`: Specifies the current text and background color attributes
     *   of the characters in the console screen buffer. This field is represented as
     *   a `short` value.
     * <p>
     * - `srWindow`: Defines the console screen buffer's window, represented as a
     *   {@link SMALL_RECT} structure. This field specifies the window's boundaries
     *   within the console screen buffer.
     * <p>
     * - `dwMaximumWindowSize`: Specifies the maximum size of the console window
     *   in character cells, represented as a {@link COORD} structure. This value is
     *   dependent on the current screen buffer size and the display resolution.
     * <p>
     * This structure is commonly used in conjunction with native Windows API functions,
     * such as `GetConsoleScreenBufferInfo`, to query or modify console attributes
     * and layout. It is frequently used via JNA (Java Native Access) when interacting
     * with the Kernel32 library.
     */
    @Structure.FieldOrder({"dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize"})
    class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
      /**
       * Default constructor for the CONSOLE_SCREEN_BUFFER_INFO class.
       * This class is typically used to encapsulate information about the console screen buffer, including
       * properties such as the size of the buffer, cursor position, text attributes, and window dimensions.
       * It extends the com.sun.jna.Structure class, allowing interaction with native code.
       */
      public CONSOLE_SCREEN_BUFFER_INFO() {}
      /**
       * Specifies the size of the console screen buffer, represented as a {@link COORD} structure.
       * This variable defines the dimensions of the screen buffer in character cells, where the
       * horizontal dimension is indicated by the `X` field and the vertical dimension is indicated
       * by the `Y` field of the {@link COORD}.
       * <p>
       * The size of the screen buffer is measured in terms of character cells, not pixels. It is
       * used for configuring or querying the width and height of the console screen buffer in
       * console-based applications on Windows systems.
       */
      public COORD dwSize;
      /**
       * Specifies the current position of the cursor within the console screen buffer.
       * This variable is represented as a {@link COORD} structure, where the `X` field
       * indicates the horizontal position and the `Y` field indicates the vertical
       * position, both measured in character cells.
       * <p>
       * The position stored in this variable is relative to the top-left corner
       * of the console screen buffer, with (0,0) representing the origin.
       */
      public COORD dwCursorPosition;
      /**
       * The wAttributes field represents the attributes of a character cell in a console screen buffer.
       * These attributes define the foreground and background colors, as well as any text attributes
       * like intensity or underlining, for characters displayed on the console. The value of this
       * field is a bitmask, with individual bits corresponding to different attributes.
       */
      public short wAttributes;
      /**
       * Defines the displayable rectangular region of the console screen buffer.
       * <p>
       * This variable is an instance of the {@link SMALL_RECT} structure and represents
       * the boundaries and dimensions of the active or visible portion of the console
       * screen buffer. The coordinates specified in this rectangle determine
       * which part of the buffer is currently displayed in the console window.
       */
      public SMALL_RECT srWindow;
      /**
       * Defines the maximum window size for a console screen buffer, expressed as a
       * two-dimensional coordinate using the {@link COORD} structure.
       * <p>
       * The `X` field of the {@link COORD} structure represents the maximum width
       * (number of columns) of the window. The `Y` field represents the maximum height
       * (number of rows) of the window, taking into account the current screen buffer
       * size and the dimensions of the display screen.
       * <p>
       * This field is part of the {@link CONSOLE_SCREEN_BUFFER_INFO} structure, which
       * encapsulates information about a console screen buffer.
       * <p>
       * The value of `dwMaximumWindowSize` is used in console-related operations to
       * determine or constrain the maximum dimensions that a console window can be resized to.
       */
      public COORD dwMaximumWindowSize;
    }

    /**
     * Retrieves a handle to the specified standard device (standard input, standard output, or standard error).
     * This method is commonly used to obtain a handle for performing input/output operations on
     * the console or associated standard streams.
     *
     * @param nStdHandle an integer value representing the standard device. Valid values include:
     *                   - -10: Standard input device (STD_INPUT_HANDLE)
     *                   - -11: Standard output device (STD_OUTPUT_HANDLE)
     *                   - -12: Standard error device (STD_ERROR_HANDLE)
     * @return a HANDLE representing the standard device handle, or null if an error occurs.
     */
    HANDLE GetStdHandle(int nStdHandle);

    /**
     * Retrieves information about the specified console screen buffer, including its size,
     * attributes, cursor position, and window information.
     *
     * @param hConsoleOutput a HANDLE to the console screen buffer from which to retrieve information.
     *                       This handle is typically obtained by using functions such as {@code GetStdHandle}.
     * @param lpConsoleScreenBufferInfo a reference to a {@link CONSOLE_SCREEN_BUFFER_INFO} structure
     *                                  that will receive the console screen buffer information.
     * @return {@code true} if the function succeeds, or {@code false} if it fails. If the function fails,
     *         additional error information can be obtained by calling {@code GetLastError()}.
     */
    boolean GetConsoleScreenBufferInfo(HANDLE hConsoleOutput, CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);
  }
  
  /**
   * Represents the standard output handle identifier for the current process in the Windows
   * operating system. This constant is used in conjunction with the Windows Kernel32 API
   * to retrieve a handle to the standard output device, enabling operations like collecting
   * the current console's screen buffer information.
   * <p>
   * In the Windows API, the value -11 is reserved specifically for standard output.
   */
  private static final int STD_OUTPUT_HANDLE = -11;
  
  /**
   * Represents the dimensions of a two-dimensional structure such as a grid or table.
   * This immutable record encapsulates the number of rows and columns.
   *
   * @param rows    the number of rows in the structure
   * @param columns the number of columns in the structure
   */
  public record Dimensions(int rows, int columns) {}

  /**
   * The entry point of the application. This method initializes the process of retrieving
   * the console's dimensions and prints relevant information to the output.
   *
   * @param args command-line arguments passed to the program at runtime. These arguments
   *             are not used in this method.
   */
  public static void main(String[] args) {
    Dimensions dims = getDimensions();
    
    if (dims != null) {
      System.out.println("✅ Conexión nativa establecida.");
      System.out.println("Columnas: " + dims.columns());
      System.out.println("Líneas:   " + dims.rows());
    } else {
      System.err.println("❌ No se pudo obtener el Handle de la consola. ¿Estás en un IDE?");
    }
  }
  
  /**
   * Retrieves the dimensions of the current visible console window.
   * The dimensions are calculated as the number of rows and columns
   * visible in the console's window.
   * <p>
   * Native Windows API calls are used to collect the console's screen
   * buffer information and extract the necessary dimensions.
   *
   * @return an instance of {@code Dimensions} representing the number of
   *         rows and columns of the console's visible window, or {@code null}
   *         if the dimensions could not be determined.
   */
  public static Dimensions getDimensions() {
    Kernel32 k32 = Kernel32.INSTANCE;
    
    // 1. Obtenemos el Handle de la salida estándar del proceso actual
    HANDLE hConsole = k32.GetStdHandle(STD_OUTPUT_HANDLE);
    
    // 2. Preparamos la estructura donde Windows escribirá los datos
    Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
    
    // 3. Llamada nativa
    if (k32.GetConsoleScreenBufferInfo(hConsole, info)) {
      // El tamaño de la ventana visible se calcula restando las coordenadas del rectángulo
      int columns = info.srWindow.Right - info.srWindow.Left + 1;
      int rows = info.srWindow.Bottom - info.srWindow.Top + 1;
      
      return new Dimensions(rows, columns);
    }
    
    return null;
  }
}
