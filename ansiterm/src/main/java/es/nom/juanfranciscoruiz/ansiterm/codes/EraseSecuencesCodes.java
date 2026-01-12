package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

public class EraseSecuencesCodes {
  // Erase sequences
  /**
   * Erases from the cursor to the end of the screen
   */
  public static final String ERASES_FROM_CURSOR_TO_END_OF_SCREEN = ESC + "[0J";
  /**
   * Erases from the cursor to the beginning of the screen
   */
  public static final String ERASES_FROM_CURSOR_TO_BEGINNING_OF_SCREEN = ESC + "[1J";
  /**
   * Erases the entire screen
   */
  public static final String CLEAR_SCREEN = ESC + "[2J";
  /**
   * Erases from the cursor to the end of the current line
   */
  public static final String ERASES_FROM_CURSOR_TO_END_OF_CURRENT_LINE = ESC + "[0K";
  /**
   * Erases from the cursor to the beginning of the current line
   */
  public static final String ERASES_FROM_CURSOR_TO_BEGINNING_OF_CURRENT_LINE = ESC + "[1K";
  /**
   * Erases the entire current line (Erasing a line DOES NOT move the cursor)
   */
  public static final String ERASES_CURRENT_LINE = ESC + "[2K";
  
  private EraseSecuencesCodes() {}
}
