package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

public class ColorsAndStylesCodes {
  // Control sequences for colors and styles
  /**
   * Resets all modes (styles and colors)
   */
  public static final String REINICIA_ESTILOS = ESC + "[0m";
  /**
   * Starts bold mode.
   */
  public static final String INI_NEG = ESC + "[1m";
  /**
   * Ends bold mode.
   */
  public static final String FIN_NEG = ESC + "[22m";
  /**
   * Starts faint/dim mode.
   */
  public static final String INI_OSC = ESC + "[2m";
  /**
   * Ends faint/dim mode.
   */
  public static final String FIN_OSC = ESC + "[22m";
  /**
   * Starts italic mode.
   */
  public static final String INI_CUR = ESC + "[3m";
  /**
   * Ends italic mode.
   */
  public static final String FIN_CUR = ESC + "[23m";
  /**
   * Starts underline mode.
   */
  public static final String INI_SUB = ESC + "[4m";
  /**
   * Ends underline mode.
   */
  public static final String FIN_SUB = ESC + "[24m";
  /**
   * Starts blink mode.
   */
  public static final String INI_INT = ESC + "[5m";
  /**
   * Ends blink mode.
   */
  public static final String FIN_INT = ESC + "[25m";
  /**
   * Starts reverse mode (swapped colors).
   */
  public static final String INI_REV = ESC + "[7m";
  /**
   * Ends reverse mode (swapped colors).
   */
  public static final String FIN_REV = ESC + "[27m";
  /**
   * Starts invisible mode.
   */
  public static final String INI_INV = ESC + "[8m";
  /**
   * Ends invisible mode.
   */
  public static final String FIN_INV = ESC + "[28m";
  /**
   * Starts strikethrough mode.
   */
  public static final String INI_TAC = ESC + "[9m";
  /**
   * Ends strikethrough mode.
   */
  public static final String FIN_TAC = ESC + "[29m";
  /**
   * The reset color is the reset code that restores all colors and text
   * effects. Use the default color of the <code>Color</code> and
   * <code>Bgcolor</code> Enums to reset only colors.
   * <p>
   * {@code @See} es.nom.juanfranciscoruiz.ansiterm.Color
   * {@code @See} es.nom.juanfranciscoruiz.ansiterm.BGcolor
   *
   */
  public static final String REINICIA = "0";
  /**
   * Foreground color (up to 256). Uses ESC[38;5;{0-255}m
   */
  public static final String COL256 = ESC + "[38;5;";
  /**
   * Background color (up to 256). Uses ESC[48;5;{0-255}m
   */
  public static final String COL256_F = ESC + "[48;5;";
  
  private ColorsAndStylesCodes() {}
}
