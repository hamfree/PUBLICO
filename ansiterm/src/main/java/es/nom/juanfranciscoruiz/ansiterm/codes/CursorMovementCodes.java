package es.nom.juanfranciscoruiz.ansiterm.codes;


import static es.nom.juanfranciscoruiz.ansiterm.codes.CTS.ESC;

public class CursorMovementCodes {
  // Cursor movement controls
  /**
   * Moves the cursor to position 0,0
   */
  public static final String MUE_CUR_00 = ESC + "[H";
  /**
   * Response as ESC[#;#R of the cursor position
   */
  public static final String REC_POS_CUR = ESC + "[6n";
  /**
   * Moves the cursor one line up, scrolling if necessary
   */
  public static final String MUE_CUR_1LIN_ARR = ESC + "M";
  /**
   * Saves the cursor position (DEC sequence)
   */
  public static final String SAL_POS_CUR = ESC + "7";
  /**
   * Restores the cursor to its last saved position (DEC sequence)
   */
  public static final String RES_POS_CUR = ESC + "8";
  
  private CursorMovementCodes() {}
}
