package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains ANSI escape codes for cursor control.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 * @author Juan F. Ruiz
 */
public class CursorControlCodes {
  /**
   * Hides the cursor (DECTCEM)
   */
  public static final String HIDES_CURSOR = ESC + "[?25l";
  /**
   * Shows the cursor (DECTCEM)
   */
  public static final String SHOWS_CURSOR = ESC + "[?25h";
  
  /**
   * Enables cursor blinking (ATT160)
   */
  public static final String ENABLE_BLINK_CURSOR = ESC + "[?12h";
  /**
   * Disables cursor blinking (ATT160)
   */
  public static final String DISABLE_BLINK_CURSOR = ESC + "[?12l";
  
  /**
   * Private constructor. Class can't be instantiated by the user
   */
  private CursorControlCodes() {}
}
