package es.nom.juanfranciscoruiz.utiles;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.win32.StdCallLibrary;
// Usando la librería JNA para acceder a la API de Windows directamente sí funciona...

/**
 * The NativeTerminalInspector class is responsible for inspecting the native terminal
 * (console) dimensions by using the Windows Kernel32 API through the Java Native Access (JNA) library.
 * It retrieves the current console's dimensions, including the width (columns) and height (rows).
 */
public class NativeTerminalInspector {
  
  // --- Definición de la API de Windows mediante JNA ---
  public interface Kernel32 extends StdCallLibrary {
    Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);
    
    // Representa la estructura COORD de C++
    @Structure.FieldOrder({"X", "Y"})
    class COORD extends Structure {
      public short X;
      public short Y;
    }
    
    // Representa la estructura SMALL_RECT de C++
    @Structure.FieldOrder({"Left", "Top", "Right", "Bottom"})
    class SMALL_RECT extends Structure {
      public short Left;
      public short Top;
      public short Right;
      public short Bottom;
    }
    
    // Estructura que rellena la función GetConsoleScreenBufferInfo
    @Structure.FieldOrder({"dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize"})
    class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
      public COORD dwSize;
      public COORD dwCursorPosition;
      public short wAttributes;
      public SMALL_RECT srWindow;
      public COORD dwMaximumWindowSize;
    }
    
    // Obtiene el manejador (handle) de la consola
    HANDLE GetStdHandle(int nStdHandle);
    
    // La función clave que nos da las dimensiones
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
