package es.nom.juanfranciscoruiz.ansiterm.codes;


import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains ANSI escape codes for cursor movement.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 * @author Juan F. Ruiz
 */
public class CursorMovementCodes {
  /**
   * Moves the cursor to position 0,0
   */
  public static final String CURSOR_MOVE_TO_00 = ESC + "[H";
  /**
   * Sequence......: ESC [6 n
   * Code..........: DECXCPR
   * Description...: Specify the cursor position
   * Behavior......: Outputs the cursor position as ESC[<r>;<c>R,
   * where <r> is equal to the cursor row and <c> is equal to the cursor column
   */
  public static final String CURSOR_GET_POSITION = ESC + "[6n";
  /**
   * Moves the cursor one line up, scrolling if necessary (RI)
   */
  public static final String CURSOR_MOVE_ONE_LINE_UP = ESC + "M";
  /**
   * Sequence......: ESC 7
   * Code..........: DECSC
   * Description...: Saves the cursor position in memory.
   * Notes.........: No value will be stored in memory until the `save` command
   * is used for the first time. The only way to access the saved value is with the `restore` command.
   */
  public static final String CURSOR_SAVE_CURRENT_POSITION = ESC + "7";
  /**
   * Sequence......: ESC 8
   * Code..........: DECSR
   * Description...: Restores the cursor to its last saved position from memory
   * Notes.........: No value will be stored in memory until the `save` command
   * is used for the first time. The only way to access the saved value is with the `restore` command.
   *
   */
  public static final String CURSOR_RESTORE_CURRENT_POSITION = ESC + "8";
  
  /**
   * Private constructor. Class can't be instantiated by the user
   */
  private CursorMovementCodes() {}
}
