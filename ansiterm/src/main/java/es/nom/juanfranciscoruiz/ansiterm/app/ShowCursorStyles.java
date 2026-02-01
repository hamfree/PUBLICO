package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.codes.CursorStylesCodes;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.printWithDelay;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pauseWithMessage;

/**
 * Demonstrates various cursor styles and visibility.
 *
 * @author Juan F. Ruiz
 */
public class ShowCursorStyles {

  public static final long DELAY_MILLISECONDS = 100L;

  /**
   * Constructs a new ShowCursorStyles.
   */
  ShowCursorStyles() {}
  
  /**
   * Performs the cursor styles demonstration.
   * 
   * @param term The ANSITerm object to use.
   * @throws Exception If an error occurs during execution.
   */
  public void perform(ANSITerm term) throws Exception {
    term.clearScreen();
    term.moveCursorToBegin();
    term.cursorShow();
    term.printAt("------------ Cursor styles ------------", 1, 1);
    String msg = "(1/9) - Stable bar cursor shape";
    term.printAt(msg, 2, 1);
    term.cursorChangeStyle(CursorStylesCodes.CURSOR_STEADY_BAR_SHAPE);
    printWithDelay(msg, 5, 1, term, DELAY_MILLISECONDS);
    pauseWithMessage(0, null);
    
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Cursor styles ------------", 1, 1);
    msg = "(2/9) - Blinking bar cursor shape";
    term.printAt(msg, 2, 1);
    term.cursorChangeStyle(CursorStylesCodes.CURSOR_BLINKING_BAR_SHAPE);
    printWithDelay(msg, 5, 1, term, DELAY_MILLISECONDS);
    pauseWithMessage(0, null);
    
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Cursor styles ------------", 1, 1);
    msg = "(3/9) - Stable block cursor shape";
    term.printAt(msg, 2, 1);
    term.cursorChangeStyle(CursorStylesCodes.CURSOR_STEADY_BLOCK_SHAPE);
    printWithDelay(msg, 5, 1, term, DELAY_MILLISECONDS);
    pauseWithMessage(0, null);
    
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Cursor styles ------------", 1, 1);
    msg = "(4/9) - Blinking block cursor shape";
    term.printAt(msg, 2, 1);
    term.cursorChangeStyle(CursorStylesCodes.CURSOR_BLOCK_SHAPE);
    printWithDelay(msg, 5, 1, term, DELAY_MILLISECONDS);
    pauseWithMessage(0, null);
    
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Cursor styles ------------", 1, 1);
    msg = "(5/9) - Stable underline cursor shape";
    term.printAt(msg, 2, 1);
    term.cursorChangeStyle(CursorStylesCodes.CURSOR_STEADY_UNDERLINE_SHAPE);
    printWithDelay(msg, 5, 1, term, DELAY_MILLISECONDS);
    pauseWithMessage(0, null);
    
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Cursor styles ------------", 1, 1);
    msg = "(6/9) - Blinking underline cursor shape";
    term.printAt(msg, 2, 1);
    term.cursorChangeStyle(CursorStylesCodes.CURSOR_UNDERLINE_SHAPE);
    printWithDelay(msg, 5, 1, term, DELAY_MILLISECONDS);
    pauseWithMessage(0, null);
    
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Cursor styles ------------", 1, 1);
    msg = "(7/9) - User-defined cursor shape";
    term.printAt(msg, 2, 1);
    term.cursorChangeStyle(CursorStylesCodes.CURSOR_USER_SHAPE);
    printWithDelay(msg, 5, 1, term, DELAY_MILLISECONDS);
    pauseWithMessage(0, null);
    
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Cursor styles ------------", 1, 1);
    msg = "(8/9) - Hide the cursor";
    term.printAt(msg, 2, 1);
    term.cursorHide();
    printWithDelay(msg, 5, 1, term, DELAY_MILLISECONDS);
    pauseWithMessage(0, null);
    
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Cursor styles ------------", 1, 1);
    msg = "(9/9) - Show cursor";
    term.printAt(msg, 2, 1);
    term.cursorShow();
    printWithDelay(msg, 5, 1, term, DELAY_MILLISECONDS);
    pauseWithMessage(0, "Press <ENTER> to return to menu");
  }
}
