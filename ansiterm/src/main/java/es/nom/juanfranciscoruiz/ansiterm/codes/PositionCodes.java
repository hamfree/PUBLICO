package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

public class PositionCodes {
  // Viewport position codes
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
