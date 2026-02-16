package es.nom.juanfranciscoruiz.ansiterm.app.options;

import com.sun.jna.LastErrorException;
import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.Position;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.ansiterm.codes.CursorStylesCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pauseWithMessage;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pauseForMilliseconds;

/**
 * Demonstrates how to recover the current cursor position from the terminal.
 *
 * @author Juan F. Ruiz
 */
public class RecoverCursorPosition {
  
  /**
   * Logger used for tracing and debugging.
   */
  public static final Logger logger = LoggerFactory.getLogger(RecoverCursorPosition.class);

  /**
   * Defines the delay duration in milliseconds used for controlling the timing
   * of cursor position recovery operations and other related processes.
   */
  public static final Long DELAY = 10L;
  
  /**
   * Constructs a new RecoverCursorPosition.
   */
  public RecoverCursorPosition() {}
  
  /**
   * Performs the cursor position recovery demonstration.
   * 
   * @param term The ANSITerm object to use.
   * @throws Exception If an error occurs during execution.
   */
  public void perform(ANSITerm term) throws Exception {
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------  Recovering the cursor position ------------ ", 1, 1);
    TerminalSize screenSize = term.getTerminalSize();
    
    term.cursorShow();
    term.cursorChangeStyle(CursorStylesCodes.CURSOR_STEADY_BLOCK_SHAPE);
    
    for (int lin = 2; lin < screenSize.getLines() - 3; lin++) {
      for (int col = 1; col <= screenSize.getColumns(); col++) {
        Position p = new Position(1, 1);
        term.printAt("X", lin, col);
        try {
          p = term.getCursorPosition();
        } catch (LastErrorException e) {
          logger.error(String.valueOf(e.getErrorCode()));
          System.out.println(e.getErrorCode());
          System.out.println(e.getMessage());
        }
        long retardo = DELAY;
        pauseForMilliseconds(retardo);
        term.printAt("Cursor position: column : ", screenSize.getLines() - 2, 1);
        term.deleteFromCursorToEndLine();
        term.printAt(p.getCol() + ", row: " + p.getLin(), screenSize.getLines() - 2, 32);
        pauseForMilliseconds(retardo);
      }
    }
    pauseWithMessage(0, "Press <ENTER> to return to the menu");
  }
}
