package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.codes.CursorStylesCodes;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.imprimeConLapso;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pausa;

/**
 * Demonstrates enabling and disabling cursor blinking.
 *
 * @author Juan F. Ruiz
 */
public class ShowCursorBlinking {
  /**
   * Constructs a new ShowCursorBlinking.
   */
  ShowCursorBlinking() {}
  
  /**
   * Performs the cursor blinking demonstration.
   * 
   * @param term The ANSITerm object to use.
   * @throws Exception If an error occurs during execution.
   */
  void perform(ANSITerm term) throws Exception {
    term.clearScreen();
    term.moveCursorToBegin();
    term.cursorShow();
    term.cursorChangeStyle(CursorStylesCodes.CURSOR_STEADY_BLOCK_SHAPE);
    term.printAt("------------ Cursor blinking ------------", 1, 1);
    String msg = "(1/2) - The cursor starts blinking.";
    term.printAt(msg, 2, 1);
    term.cursorBlink();
    imprimeConLapso(msg, 5, 1, term, 100L);
    pausa(0, null);
    
    msg = "(2/2) - Turn off cursor blinking";
    term.printAt(msg, 2, 1);
    term.cursorNoBlink();
    imprimeConLapso(msg, 5, 1, term, 100L);
    pausa(0, "Press <ENTER> to return to menu");
  }
}
