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
  /**
   * Represents an instance of the {@link TermCtl} interface used for terminal control operations.
   * This variable holds a reference to the terminal control implementation, which provides
   * methods for interacting with the terminal, such as retrieving or setting its dimensions
   * and clearing the screen.
   * <p>
   * The {@code tc} instance is initialized with a specific implementation, typically
   * {@code TermCtlImpl}, to enable platform-specific terminal operations.
   */
  private TermCtl tc;

  /**
   * Constructs a new instance of the TerminalControl class and initializes the terminal control
   * implementation.
   * <p>
   * This constructor creates an internal instance of {@link TermCtlImpl}, which is assigned to the
   * {@code tc} field. This instance provides the functionality required for interacting with the terminal,
   * such as retrieving terminal dimensions, clearing the screen, and other terminal-specific operations.
   * <p>
   * The constructor ensures that the {@code tc} field is ready for terminal-related actions immediately
   * after the TerminalControl object is created.
   */
  public TerminalControl() {
    tc = new TermCtlImpl();
  }

  /**
   * Retrieves the {@link TermCtl} instance associated with this class.
   * The {@code TermCtl} interface provides methods for performing terminal control
   * operations such as retrieving terminal dimensions, clearing the screen, and setting dimensions.
   *
   * @return the {@code TermCtl} instance used for terminal control operations.
   */
  public TermCtl getTc() {
    return tc;
  }

  /**
   * Sets the {@link TermCtl} instance associated with this class.
   * This method allows changing the terminal control implementation
   * used for performing terminal-related operations such as retrieving
   * terminal size, clearing the screen, and setting terminal dimensions.
   *
   * @param tc the {@link TermCtl} implementation to be associated with this class
   */
  public void setTc(TermCtl tc) {
    this.tc = tc;
  }
  
  /**
   * Displays the current dimensions of the terminal or provides feedback if no real console
   * is detected. The method performs the following steps:
   * <ul>
   * <li>- Clears the terminal screen.</li>
   * <li>- Displays a custom title message formatted with a specific character and length.</li>
   * <li>- Retrieves the terminal's dimensions using the TermCtlImpl implementation.</li>
   * <li>- Prints the terminal dimensions (number of rows and columns) if a real terminal is detected.</li>
   * <li>- Outputs a message if no real console is found (e.g., when executed within an IDE or under redirection).</li>
   * </ul>
   * This method relies on external dependencies such as TermCtlImpl to access platform-specific
   * terminal properties, as well as utility methods like clearScreen and title for screen manipulation
   * and output formatting.
   * @throws Exception In case of errors during terminal control operations.
   */
  public void run() throws Exception {
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
