package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains ANSI escape codes for erasing screen and line content.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 * @author Juan F. Ruiz
 */
public class EraseSecuencesCodes {
  /**
   * Erases from the cursor to the end of the screen
   */
  public static final String ERASES_FROM_CURSOR_TO_END_OF_SCREEN = ESC + "[0J";
  /**
   * Erases from the cursor to the beginning of the screen
   */
  public static final String ERASES_FROM_CURSOR_TO_BEGINNING_OF_SCREEN = ESC + "[1J";
  /**
   * Erases the entire screen
   */
  public static final String CLEAR_SCREEN = ESC + "[2J";
  /**
   * Erases from the cursor to the end of the current line
   */
  public static final String ERASES_FROM_CURSOR_TO_END_OF_CURRENT_LINE = ESC + "[0K";
  /**
   * Erases from the cursor to the beginning of the current line
   */
  public static final String ERASES_FROM_CURSOR_TO_BEGINNING_OF_CURRENT_LINE = ESC + "[1K";
  /**
   * Erases the entire current line (Erasing a line DOES NOT move the cursor)
   */
  public static final String ERASES_CURRENT_LINE = ESC + "[2K";
  
  private EraseSecuencesCodes() {}
}
