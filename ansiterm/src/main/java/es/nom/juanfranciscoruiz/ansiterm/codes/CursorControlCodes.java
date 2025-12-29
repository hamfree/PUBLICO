package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

public class CursorControlCodes {
  // Cursor control codes
  /**
   * Hides the cursor (DECTCEM)
   */
  public static final String CUR_INV = ESC + "[?25l";
  /**
   * Shows the cursor (DECTCEM)
   */
  public static final String CUR_VIS = ESC + "[?25h";
  
  /**
   * Enables cursor blinking (ATT160)
   */
  public static final String CUR_PAR = ESC + "[?12h";
  /**
   * Disables cursor blinking (ATT160)
   */
  public static final String CUR_NOPAR = ESC + "[?12l";
  
  /**
   * Private constructor. Class can't be instantiated by the user
   */
  private CursorControlCodes() {}
}
