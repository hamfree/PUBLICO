package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.utiles.UnclosableInputStreamDecorator;

import java.util.Scanner;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pausa;

/**
 * Demonstrates how to obtain the current terminal screen size.
 *
 * @author Juan F. Ruiz
 */
public class ScreenSize {
  
  /**
   * Constructs a new ScreenSize.
   */
  ScreenSize() {}
  
  /**
   * Performs the screen size demonstration.
   * 
   * @param term The ANSITerm object to use.
   * @throws Exception If an error occurs during execution.
   */
  void perform(ANSITerm term) throws Exception {
    String resp = "";
    while (!resp.equals("q")) {
      term.clearScreen();
      term.moveCursorToBegin();
      term.printAt("------------ Screen size ------------", 1, 1);
      term.printAt("Try resizing the terminal window.", 2, 1);
      term.printAt("Then press ENTER. The terminal size will be displayed..", 3, 1);
      term.printAt("Press q and then ENTER to return to the menu", 4, 1);
      term.printAt("> : ", 5, 1);
      Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in));
      resp = sc.nextLine();
      TerminalSize ts = term.getOsCall().getTerminalSize();
      term.printAt("The screen size is:"
          + ts.getLineas()
          + "lines and "
          + ts.getColumnas()
          + " columns.", ts.getLineas() - 2, 1);
      pausa(0, null);
    }
  }
}
