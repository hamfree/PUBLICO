package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

public class CursorControlCodes {
  // Cursor control codes
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
