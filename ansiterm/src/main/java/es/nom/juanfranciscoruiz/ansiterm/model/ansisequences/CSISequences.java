package es.nom.juanfranciscoruiz.ansiterm.model.ansisequences;

/**
 * ANSI Control Sequence Introducer (CSI) for CSI sequences.
 * More information about using ANSI escape sequences at:<br><br>
 *
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 *
 * @author Juan F. Ruiz
 */
public class CSISequences {
    /**
   * ANSI Control Sequence Introducer (CSI) for CSI sequences.
   * All CSI sequences start with ESC (0x1b or \033) followed by the character [
   * (left bracket, 0x5b) and may contain parameters of variable length to
   * specify more information for each operation. This will be represented by
   * the shorthand 'n'. For all parameters, the following rules apply unless
   * otherwise noted:
   * <ul>
   *   <li>'n'represents the distance to move and is an optional parameter</li>
   *   <li>if 'n' is omitted or equals 0, it will be treated as a 1</li>
   *   <li>'n' cannot be larger than 32767 (maximum short value)</li>
   *   <li>'n' cannot be negative</li>
   * </ul>
   */
  public static final String ESC = "\033";
    /**
     * ANSI Control Sequence Introducer (CSI) for Control Sequence Introducer (CSI) sequences.
     * CSI sequences are a subset of ANSI escape codes used to control text formatting,
     * cursor movement, and other terminal behaviors. The CSI sequence starts with the
     * ESC character followed by the left square bracket '['.
     */
  public static final String CSI = "\033[";
  
  /**
   * Private constructor. The user can't instantiate class
   */
  private CSISequences() {}
}
