package es.nom.juanfranciscoruiz.ansiterm.codes;

/**
 * Contains general ASCII control codes.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 * @author Juan F. Ruiz
 */
public class GeneralAsciiCodes {
  /**
   * Rings the terminal bell
   * CODE: BELL
   */
  public static final char BELL = 7;
  /**
   * Backspaces one character
   * CODE: BS
   */
  public static final char BS = 8;
  /**
   * Horizontal tab
   * CODE: TAB
   */
  public static final char TAB = 9;
  /**
   * Line feed
   * CODE: LF
   */
  public static final char LF = 10;
  /**
   * Vertical tab
   * CODE: VT
   */
  public static final char VT = 11;
  /**
   * Form feed or new page
   * CODE: FF
   */
  public static final char FF = 12;
  /**
   * Carriage return
   * CODE: CR
   */
  public static final char CR = 13;
  
  /**
   * Private constructor. Class can't be instantiated by the user
   */
  private GeneralAsciiCodes() {}
}
