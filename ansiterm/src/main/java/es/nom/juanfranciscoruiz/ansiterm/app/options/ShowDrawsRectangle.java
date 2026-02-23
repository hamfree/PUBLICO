package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.ansiterm.model.Rectangle;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * Draws a rectangle with asterisks around the screen borders.
 *
 * @author Juan F. Ruiz
 */
public class ShowDrawsRectangle {
  public static final long DELAY = 10L;
  /**
   * Represents an instance of the {@code ANSITerm} class used for managing
   * terminal interactions, such as cursor blinking or printing text at
   * specific locations on the terminal screen.
   * <p>
   * This object is final and assigned during class construction, ensuring
   * consistent and reliable access throughout the lifecycle of the containing
   * class. It provides methods and utilities for terminal display management.
   */
  private final ANSITerm term;
  /**
   * Represents the title text for the MultipleStylesText object.
   * This field is used to store a string that acts as the title,
   * often styled and displayed prominently in terminal-based
   * demonstrations or other text-based UI elements of the class.
   */
  private final String title;
  /**
   * Represents the primary text message managed by the MultipleStylesText class.
   * This message is used as a content reference for various operations, including
   * styling demonstrations and terminal-based displays.
   */
  private final String message;
  /**
   * Represents a rectangle to be drawn on the terminal screen.
   * <p>
   * This rectangle is characterized by its position on the screen,
   * specified dimensions (width and height), and the character used
   * to render its edges or fill its area. It provides a visual element
   * for terminal-based applications.
   * <p>
   * The rectangle is initialized with default dimensions (1x1) and a
   * specific drawing character (*). Its position is determined by its
   * x and y coordinates, which refer to its starting column and line
   * respectively.
   */
  Rectangle rectangle = new Rectangle(1, 1, 1, 1, "*");
  /**
   * Constructs a new DrawsRectangle.
   */
  public ShowDrawsRectangle() throws ANSITermException {
    this.term = new ANSITerm();
    this.title = "Draws concentric rectangles with digits around at the screen";
    this.message = "Concentric rectangles will be drawn on the screen. Delay: " + DELAY + " ms";
  }
  
  /**
   * Performs the rectangle drawing demonstration.
   * 
   * @throws Exception If an error occurs during execution.
   */
  public void perform() throws Exception {
    long DELAY = 10L;
    TerminalSize ts = term.getTerminalSize();
    int widthConsole = ts.getColumns();
    int heightConsole = ts.getLines();

    clearScreenAndPrintHeader(term, title, message, widthConsole);

    // We start on line 6 so as not to delete the header...
    int height = ts.getLines() - 6;
    int width = ts.getColumns();
    int x = 1;
    int y = 6;
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


    pauseWithMessage(0, "Press <ENTER> to return to menu");
  }

  /**
   * Draws a rectangle on the given terminal using the specified rectangle properties.
   *
   * @param term The terminal on which the rectangle will be drawn.
   * @param rectangle The rectangle object containing dimensions, position, and character to use for drawing.
   * @throws ANSITermException If an error occurs while printing to the terminal.
   */
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
