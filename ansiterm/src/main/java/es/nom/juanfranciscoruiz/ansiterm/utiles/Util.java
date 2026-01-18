package es.nom.juanfranciscoruiz.ansiterm.utiles;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;

import java.io.BufferedInputStream;
import java.util.Random;
import java.util.Scanner;

/**
 * Utility class providing helper methods for terminal operations, such as
 * pausing execution or printing formatted text blocks.
 * @author Juan F. Ruiz
 */
public class Util {
  /**
   * Private constructor to prevent instantiation.
   */
  private Util() {}
  
  /**
   * Pauses the program for a specified number of milliseconds and optionally
   * displays a message. If no message is provided, it defaults to
   * "Press &lt;ENTER&gt; to continue..." on the last line of the terminal.
   *
   * If 0 milliseconds are specified, the function waits for the user to
   * press ENTER. Otherwise, it informs the user that the program will
   * continue after the specified delay and then pauses.
   *
   * @param milisegundos The duration of the pause in milliseconds. If 0,
   * the function waits for the user to press ENTER.
   * @param msg The message to display to the user.
   * @throws Exception In case of any error.
   */
  public static void pausa(long milisegundos, String msg) throws Exception {
    ANSITerm t = new ANSITerm();
    if (msg == null || msg.isEmpty()) {
      msg = "\nPress <ENTER> to continue...";
    }
    
    TerminalSize ts = t.getTerminalSize();
    int ultimaLinea = ts.getLineas();
    
    if (milisegundos == 0) {
      t.printAt(msg, ultimaLinea - 1, 1);
      Scanner sc = new Scanner(new BufferedInputStream(System.in));
      sc.nextLine();
      return;
    }
    t.printAt("The program will continue in " + milisegundos + " milliseconds...",
        ultimaLinea - 1, 1);
    Thread.sleep(milisegundos);
  }
  
  /**
   * Pauses the program for a specified number of milliseconds. If 0 is
   * specified, it waits for the user to press ENTER. No message is
   * displayed.
   *
   * @param milisegundos The duration of the pause in milliseconds. If 0,
   * the function waits for the user to press ENTER.
   * @throws Exception In case of any error.
   */
  public static void pausaSinMensaje(long milisegundos) throws Exception {
    ANSITerm t = new ANSITerm();
    t.moveCursorToBegin();
    if (milisegundos == 0) {
      Scanner sc = new Scanner(new BufferedInputStream(System.in));
      sc.nextLine();
      return;
    }
    Thread.sleep(milisegundos);
  }
  
  /**
   * Displays a block of random text starting at 'lineaInicial', with
   * 'lineas' number of rows and 'cols' number of columns per row.
   *
   * @param term An ANSITerm object
   * @param lineaInicial The starting line in the terminal.
   * @param lineas The number of lines in the text block.
   * @param cols The number of columns (characters) in each line.
   */
  public static void imprimeBloqueTexto(ANSITerm term, int lineaInicial, int lineas, int cols) {
    int car;
    char ascii;
    Random rnd = new Random();
    StringBuilder sb = new StringBuilder();
    
    for (int line = lineaInicial; line < (lineaInicial + lineas); line++) {
      for (int col = 1; col < cols; col++) {
        car = rnd.nextInt(94) + 32;
        ascii = (char) car;
        sb.append(ascii);
      }
      term.printAt(sb.toString(), line, 1);
      sb.setLength(0);
    }
  }
  
  /**
   * Prints a string 'msg' starting at 'linea, columna' character by
   * character with the specified delay in 'milisegundos'.
   *
   * @param msg The string to print.
   * @param linea The terminal line where the string will be printed.
   * @param columna The terminal column where the string will be printed.
   * @param term An ANSITerm object.
   * @param milisegundos The delay between printing each character in milliseconds.
   */
  public static void imprimeConLapso(String msg,
                               int linea,
                               int columna,
                               ANSITerm term,
                               long milisegundos) {
    for (int i = 0; i < msg.length(); i++) {
      term.printAt(String.valueOf(msg.charAt(i)), linea, columna + i);
      try {
        Thread.sleep(milisegundos);
      } catch (InterruptedException ex) {
      }
    }
  }
}
