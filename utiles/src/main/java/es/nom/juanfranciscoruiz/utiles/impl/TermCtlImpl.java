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
  private static final int STD_OUTPUT_HANDLE = -11;
  
  /**
   * Interface for interacting with Windows Kernel32 library functions. Provides methods for accessing
   * system-level operations, particularly related to console-based functionality. This interface is a
   * wrapper around native Kernel32 library functions using the Java Native Access (JNA) library.
   */
  public interface Kernel32 extends StdCallLibrary {
    Kernel32 INSTANCE = Platform.isWindows() ? Native.load("kernel32", Kernel32.class) : null;
    
    // We reuse the necessary structures
    @com.sun.jna.Structure.FieldOrder({"X", "Y"})
    class COORD extends com.sun.jna.Structure {
      public short X, Y;
      
      public COORD(int x, int y) {
        this.X = (short) x;
        this.Y = (short) y;
      }
    }
    
    @com.sun.jna.Structure.FieldOrder({"Left", "Top", "Right", "Bottom"})
    class SMALL_RECT extends com.sun.jna.Structure {
      public short Left, Top, Right, Bottom;
      
      public SMALL_RECT(int left, int top, int right, int bottom) {
        this.Left = (short) left;
        this.Top = (short) top;
        this.Right = (short) right;
        this.Bottom = (short) bottom;
      }
    }
    
    @Structure.FieldOrder({"dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize"})
    class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
      public COORD dwSize, dwCursorPosition;
      public short wAttributes;
      public SMALL_RECT srWindow;
      public COORD dwMaximumWindowSize;
      
    }
    
    // Change the size of the internal buffer
    boolean SetConsoleScreenBufferSize(WinNT.HANDLE hConsoleOutput, COORD dwSize);
    
    // Changes the size of the physical window that the user sees
    boolean SetConsoleWindowInfo(WinNT.HANDLE hConsoleOutput, boolean bAbsolute, SMALL_RECT lpConsoleWindow);
    
    com.sun.jna.platform.win32.WinNT.HANDLE GetStdHandle(int nStdHandle);
    
    boolean GetConsoleScreenBufferInfo(com.sun.jna.platform.win32.WinNT.HANDLE hConsoleOutput,
                                       CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);
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
    LibC INSTANCE = !Platform.isWindows() ? Native.load("c", LibC.class) : null;
    
    @Structure.FieldOrder({"ws_row", "ws_col", "ws_xpixel", "ws_ypixel"})
    class WinSize extends Structure {
      public short ws_row, ws_col, ws_xpixel, ws_ypixel;
    }
    
    /*
     * The TIOCGWINSZ operation number varies depending on the OS, but 0x5413 is common
     * in Linux x86
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
   *  outcome), or {@code false} if the provided dimensions are invalid
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
      k32.SetConsoleScreenBufferSize(hConsole, size);
      boolean success = k32.SetConsoleWindowInfo(hConsole, true, rect);
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
   *         of the console window, or {@code null} if the dimensions cannot be determined.
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
   *         or {@code null} if the dimensions cannot be determined.
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
