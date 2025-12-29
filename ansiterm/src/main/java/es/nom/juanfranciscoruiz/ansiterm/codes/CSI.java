package es.nom.juanfranciscoruiz.ansiterm.codes;

/**
 * ANSI Control Sequence Introducer (CSI) for CSI sequences.
 */
public class CSI {
  /**
   * ANSI Control Sequence Introducer (CSI) for CSI sequences.
   * All CSI sequences start with ESC (0x1b or \033) followed by the character [
   * (left bracket, 0x5b) and may contain parameters of variable length to
   * specify more information for each operation. This will be represented by
   * the shorthand <n>. For all parameters, the following rules apply unless
   * otherwise noted:
   * <ul>
   *   <li><n> represents the distance to move and is an optional parameter</li>
   *   <li>if <n> is omitted or equals 0, it will be treated as a 1</li>
   *   <li><n> cannot be larger than 32767 (maximum short value)</li>
   *   <li><n> cannot be negative</li>
   * </ul>
   */
  public static final String ESC = "\033";
  
  /**
   * Private constructor. Class can't be instantiated by the user
   */
  private CSI() {}
}
