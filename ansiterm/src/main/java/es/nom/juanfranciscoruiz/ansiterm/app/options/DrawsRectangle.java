package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.ansiterm.utiles.Util;

/**
 * Draws a rectangle with asterisks around the screen borders.
 *
 * @author Juan F. Ruiz
 */
public class DrawsRectangle {
  /**
   * Constructs a new DrawsRectangle.
   */
  public DrawsRectangle() {}
  
  /**
   * Performs the rectangle drawing demonstration.
   * 
   * @param term The ANSITerm object to use for drawing.
   * @throws Exception If an error occurs during execution.
   */
  public void perform(ANSITerm term) throws Exception {
    term.clearScreen();
    term.moveCursorToBegin();
    TerminalSize ts = term.getTerminalSize();
    
    term.printAt("------------ Printing text at specific coordinates ------------", 1, 1);
    term.printAt("A rectangle will be drawn on the screen:", 2, 1);
    for (int col = 2; col < ts.getColumns(); col++) {
      term.printAt("*", 3, col);
    }
    for (int linea = 3; linea < ts.getLines() - 2; linea++) {
      term.printAt("*", linea, ts.getColumns() - 1);
    }
    for (int col = ts.getColumns() - 1; col > 1; col--) {
      term.printAt("*", ts.getLines() - 2, col);
    }
    for (int linea = ts.getLines() - 2; linea > 2; linea--) {
      term.printAt("*", linea, 2);
    }
    
    Util.pauseWithMessage(0, "Press <ENTER> to return to menu");
  }
}
