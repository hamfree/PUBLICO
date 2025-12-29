package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

public class ViewportPositionCodes {
  // Viewport position codes
  /**
   * Restores the screen
   */
  public static final String RES_PAN = ESC + "c";
  /**
   * Saves the screen
   */
  public static final String SAV_PAN = ESC + "[?47h";
  /**
   * Enables the alternate buffer
   */
  public static final String HAB_ALT_BUF = ESC + "[?1049h";
  
  /**
   * Private constructor. Class can't be instantiated by the user
   */
  private ViewportPositionCodes() {}
}
