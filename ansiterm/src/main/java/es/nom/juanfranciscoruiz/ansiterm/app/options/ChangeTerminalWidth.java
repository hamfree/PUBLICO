package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;

import static es.nom.juanfranciscoruiz.ansiterm.codes.WindowWidthCodes.setWidthTo132;
import static es.nom.juanfranciscoruiz.ansiterm.codes.WindowWidthCodes.setWidthTo80;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.*;

/**
 * The ChangeTerminalWidth class provides functionality to adjust
 * the terminal's width dynamically with specified delays and user prompts.
 * It is intended to demonstrate changes in terminal dimensions
 * and allow the user to interact with the process.
 * <p>
 * Note: These ANSI escape sequences do not work on either Windows or Linux.
 * Further research is needed.
 * @author Juan F. Ruiz
 */
public class ChangeTerminalWidth {

  /**
   * Constructs a new ChangeTerminalWidth object.
   */
  public ChangeTerminalWidth() { }

  /**
   * Adjusts the terminal width in a stepwise manner with delay intervals and prompts
   * the user before returning to the main menu.
   *
   * @param term the terminal object on which the width configuration is performed
   * @throws Exception if any error occurs during terminal operations or thread sleep
   */
  public void perform(ANSITerm term) throws Exception {
    final long DELAY = 5000L;
    String title = "Changing terminal width";
    String msg = "The console width will be changed to 80 characters in " + DELAY + " ms.";
    int columns = term.getTerminalSize().getColumns();
    clearScreenAndPrintHeader(term,title,msg,columns);
    System.out.print(setWidthTo80());
    msg = "The console width will be changed to 132 characters in  " + DELAY + " ms.";
    term.printAt(msg, 6, 1);
    pauseForMilliseconds(DELAY);
    System.out.print(setWidthTo132());

    Thread.sleep(DELAY);
    pauseWithMessage(0, "Press <ENTER> to return to menu");
  }
}
