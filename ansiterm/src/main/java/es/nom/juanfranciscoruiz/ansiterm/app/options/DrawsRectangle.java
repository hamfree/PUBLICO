package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.ansiterm.app.options.model.Rectangle;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import es.nom.juanfranciscoruiz.ansiterm.utiles.Util;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pauseForMilliseconds;

/**
 * Draws a rectangle with asterisks around the screen borders.
 *
 * @author Juan F. Ruiz
 */
public class DrawsRectangle {
  Rectangle rectangle = new Rectangle(1, 1, 1, 1, "*");
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
    long DELAY = 10L;
    term.clearTerminal();
    term.moveCursorToBegin();
    TerminalSize ts = term.getTerminalSize();
    int widthConsole = ts.getColumns();
    int heightConsole = ts.getLines();
    
    term.printAt("------------ Printing text at specific coordinates ------------", 1, 1);
    term.printAt("Concentric rectangles will be drawn on the screen:", 2, 1);

    int height = ts.getLines() - 4;
    int width = ts.getColumns() - 4;
    int x = 2;
    int y = 3;
    int digito = 0;

    do {
      this.rectangle.setX(x);
      this.rectangle.setY(y);
      this.rectangle.setHeight(height);
      this.rectangle.setWidth(width);
      this.rectangle.setCharacter(String.valueOf(digito));
      this.drawRectangle(term, this.rectangle);
      height--;
      width--;
      x++;
      y++;
      digito++;
      if (digito == 10) digito = 0;
      pauseForMilliseconds(DELAY);
    } while ((height > 0 && height >= heightConsole/2) && (width > 0 && width >= widthConsole/2));


    Util.pauseWithMessage(0, "Press <ENTER> to return to menu");
  }

  private void drawRectangle(ANSITerm term, Rectangle rectangle) throws ANSITermException {
    int columns = rectangle.getWidth();
    int lines = rectangle.getHeight();
    String ch = rectangle.getCharacter();

    for (int col = rectangle.getX(); col < columns; col++) {
      term.printAt(ch, rectangle.getY(), col);
    }
    for (int line = rectangle.getY(); line < lines; line++) {
      term.printAt(ch, line, columns);
    }
    for (int col = columns; col > rectangle.getX(); col--) {
      term.printAt(ch, lines, col);
    }
    for (int line = lines; line > rectangle.getY(); line--) {
      term.printAt(ch, line, rectangle.getX());
    }
  }
}
