package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;

import static es.nom.juanfranciscoruiz.ansiterm.codes.WindowWidthCodes.setWidthTo132;
import static es.nom.juanfranciscoruiz.ansiterm.codes.WindowWidthCodes.setWidthTo80;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.*;

public class ChangeTerminalWidth {
  
  public ChangeTerminalWidth() {
  }
  
  public void perform(ANSITerm term) throws Exception {
    final long DELAY = 5000L;
    String title = "------------ Changing terminal width ------------";
    String msg = "The console width will be changed to 80 characters in five seconds.";
    term.clearTerminal();
    term.moveCursorToBegin();
    term.printAt(title, 1, 1);
    term.printAt(msg, 2, 1);
    pauseForMilliseconds(DELAY);
    System.out.print(setWidthTo80());
    msg = "The console width will be changed to 132 characters in five seconds.";
    term.printAt(msg, 3, 1);
    pauseForMilliseconds(DELAY);
    System.out.print(setWidthTo132());
    
    printWithDelay(title, 5, 1, term, 100L);
    pauseWithMessage(0, "Press <ENTER> to return to menu");
  }
}
