package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pauseWithMessage;

/**
 * Demonstrates cursor movement commands.
 *
 * @author Juan F. Ruiz
 */
public class ShowCursorMovement {
  
  /**
   * Constructs a new ShowCursorMovement.
   */
  public ShowCursorMovement() {}
  
  /**
   * Performs the cursor movement demonstration.
   * 
   * @param term The ANSITerm object to use.
   * @throws Exception If an error occurs during execution.
   */
  public void perform(ANSITerm term) throws Exception {
    long retardo = 50L;
    
    term.clearScreen();
    term.moveCursorToBegin();
    TerminalSize screenSize = term.getTerminalSize();
    term.printAt("------------ Moving the cursor ------------", 1, 1);
    
    term.moveCursorToBegin();
    for (int i = 0; i < screenSize.getLines(); i++) {
      Thread.sleep(retardo);
      term.moveCursorDown(1);
    }
    for (int i = 0; i < screenSize.getColumns(); i++) {
      Thread.sleep(retardo);
      term.moveCursorRight(1);
    }
    for (int i = 0; i < screenSize.getLines(); i++) {
      Thread.sleep(retardo);
      term.moveCursorUp(1);
    }
    for (int i = 0; i < screenSize.getColumns(); i++) {
      Thread.sleep(retardo);
      term.moveCursorLeft(1);
    }
    pauseWithMessage(2000L, null);
  }
}
