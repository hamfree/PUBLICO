package es.nom.juanfranciscoruiz.ansiterm.codes;


import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

public class CursorMovementCodes {
  // Cursor movement controls
  /**
   * Moves the cursor to position 0,0
   */
  public static final String CURSOR_MOVE_TO_00 = ESC + "[H";
  /**
   * Response as ESC[#;#R of the cursor position
   */
  public static final String CURSOR_GET_POSITION = ESC + "[6n";
  /**
   * Moves the cursor one line up, scrolling if necessary (RI)
   */
  public static final String CURSOR_MOVE_ONE_LINE_UP = ESC + "M";
  /**
   * Saves the cursor position (DECSC)
   */
  public static final String CURSOR_SAVE_CURRENT_POSITION = ESC + "7";
  /**
   * Restores the cursor to its last saved position (DECSR)
   */
  public static final String CURSOR_RESTORE_CURRENT_POSITION = ESC + "8";
  
  /**
   * Private constructor. Class can't be instantiated by the user
   */
  private CursorMovementCodes() {}
}
