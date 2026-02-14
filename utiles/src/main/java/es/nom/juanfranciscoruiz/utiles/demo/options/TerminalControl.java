package es.nom.juanfranciscoruiz.utiles.demo.options;

import es.nom.juanfranciscoruiz.utiles.TermCtl;
import es.nom.juanfranciscoruiz.utiles.impl.IOimpl;
import es.nom.juanfranciscoruiz.utiles.impl.TermCtlImpl;
import es.nom.juanfranciscoruiz.utiles.model.Dimensions;

import static es.nom.juanfranciscoruiz.utiles.Util.FOREVER;
import static es.nom.juanfranciscoruiz.utiles.Util.pause;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.title;


/**
 * The TerminalControl class provides a utility method for interacting with the terminal to
 * display its current dimensions. The dimensions are retrieved using platform-specific
 * implementations for Windows and Unix-based systems.
 * <p>
 * This class checks whether a terminal is available and retrieves its number of rows and
 * columns using the TermCtlImpl implementation. If no real console is detected, it provides
 * appropriate feedback to the user.
 */
public class TerminalControl {
  private TermCtl tc;
  
  public TerminalControl() {
    tc = new TermCtlImpl();
  }
  
  public TermCtl getTc() {
    return tc;
  }
  
  public void setTc(TermCtl tc) {
    this.tc = tc;
  }
  
  /**
   * Displays the current dimensions of the terminal or provides feedback if no real console
   * is detected. The method performs the following steps:
   * <ul>
   * - Clears the terminal screen.
   * - Displays a custom title message formatted with a specific character and length.
   * - Retrieves the terminal's dimensions using the TermCtlImpl implementation.
   * - Prints the terminal dimensions (number of rows and columns) if a real terminal is detected.
   * - Outputs a message if no real console is found (e.g., when executed within an IDE or under redirection).
   * </ul>
   * This method relies on external dependencies such as TermCtlImpl to access platform-specific
   * terminal properties, as well as utility methods like clearScreen and title for screen manipulation
   * and output formatting.
   */
  public void terminalControl() throws Exception {
    getTc().clearScreen(true);
    String msg = "Getting and setting console size";
    IOimpl.prtln(2,title(msg,'*',80));
    System.out.println();
    TermCtlImpl termCtl = new TermCtlImpl();
    Dimensions d = termCtl.getConsoleSize();
    if (d != null) {
      System.out.printf("Current terminal: %d of rows x %d columns %n", d.rows(), d.columns());
    } else {
      System.out.println("No real console was detected (possible IDE or redirection).");
    }
    pause(FOREVER, "");
  }
}
