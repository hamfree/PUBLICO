package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

public class EraseSecuences {
  // Erase sequences
  /**
   * Erases from the cursor to the end of the screen
   */
  public static final String BOR_CUR_FIN = ESC + "[0J";
  /**
   * Erases from the cursor to the beginning of the screen
   */
  public static final String BOR_CUR_PRI = ESC + "[1J";
  /**
   * Erases the entire screen
   */
  public static final String BOR_PAN = ESC + "[2J";
  /**
   * Erases from the cursor to the end of the current line
   */
  public static final String BOR_CUR_FIN_LIN = ESC + "[0K";
  /**
   * Erases from the cursor to the beginning of the current line
   */
  public static final String BOR_CUR_PRI_LIN = ESC + "[1K";
  /**
   * Erases the entire current line (Erasing a line DOES NOT move the cursor)
   */
  public static final String BOR_CUR_LIN = ESC + "[2K";
  
  private EraseSecuences() {}
}
