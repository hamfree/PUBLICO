package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

public class ColorsAndStylesCodes {
  // Control sequences for colors and styles
  /**
   * Resets all modes (styles and colors)
   */
  public static final String RESET_STYLES = ESC + "[0m";
  /**
   * Starts bold mode.
   */
  public static final String BOLD_START = ESC + "[1m";
  /**
   * Ends bold mode.
   */
  public static final String BOLD_END = ESC + "[22m";
  /**
   * Starts faint/dim mode.
   */
  public static final String DIM_START = ESC + "[2m";
  /**
   * Ends faint/dim mode.
   */
  public static final String DIM_END = ESC + "[22m";
  /**
   * Starts italic mode.
   */
  public static final String ITALIC_START = ESC + "[3m";
  /**
   * Ends italic mode.
   */
  public static final String ITALIC_END = ESC + "[23m";
  /**
   * Starts underline mode.
   */
  public static final String UNDERLINE_START = ESC + "[4m";
  /**
   * Ends underline mode.
   */
  public static final String UNDERLINE_STOP = ESC + "[24m";
  /**
   * Starts blink mode.
   */
  public static final String BLINK_START = ESC + "[5m";
  /**
   * Ends blink mode.
   */
  public static final String BLINK_END = ESC + "[25m";
  /**
   * Starts reverse mode (swapped colors).
   */
  public static final String REVERSE_START = ESC + "[7m";
  /**
   * Ends reverse mode (swapped colors).
   */
  public static final String REVERSE_END = ESC + "[27m";
  /**
   * Starts invisible mode.
   */
  public static final String INVISIBLE_START = ESC + "[8m";
  /**
   * Ends invisible mode.
   */
  public static final String INVISIBLE_END = ESC + "[28m";
  /**
   * Starts strikethrough mode.
   */
  public static final String STRIKETHROUGH_START = ESC + "[9m";
  /**
   * Ends strikethrough mode.
   */
  public static final String STRIKETHROUGH_END = ESC + "[29m";
  /**
   * The reset color is the reset code that restores all colors and text
   * effects. Use the default color of the <code>Color</code> and
   * <code>Bgcolor</code> Enums to reset only colors.
   * <p>
   * {@code @See} es.nom.juanfranciscoruiz.ansiterm.Color
   * {@code @See} es.nom.juanfranciscoruiz.ansiterm.BGcolor
   *
   */
  public static final String RESET_COLOR_AND_STYLES = "0";
  /**
   * Foreground color (up to 256). Uses ESC[38;5;{0-255}m
   */
  public static final String FOREGROUND_COLOR256 = ESC + "[38;5;";
  /**
   * Background color (up to 256). Uses ESC[48;5;{0-255}m
   */
  public static final String BACKGROUND_COLOR256 = ESC + "[48;5;";
  
  private ColorsAndStylesCodes() {}
}
