package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/**
 * Contains ANSI escape codes for viewport and buffer management.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 * @author Juan F. Ruiz
 */
public class PositionCodes {
  /**
   * Restores the screen
   */
  public static final String RESTORES_SCREEN = ESC + "c";
  /**
   * Saves the screen
   */
  public static final String SAVES_SCREEN = ESC + "[?47h";
  /**
   * Enables the alternate buffer
   */
  public static final String ENABLES_ALTERNATE_BUFFER = ESC + "[?1049h";
  
  /**
   * Private constructor. Class can't be instantiated by the user
   */
  private PositionCodes() {}
}
