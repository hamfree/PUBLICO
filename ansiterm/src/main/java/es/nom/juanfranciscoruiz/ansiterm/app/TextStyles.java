package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pauseWithMessage;

/**
 * Demonstrates various text styles like bold, italic, etc.
 *
 * @author Juan F. Ruiz
 */
public class TextStyles {
  /**
   * Constructs a new TextStyles.
   */
  TextStyles() {}
  
  /**
   * Performs the text styles demonstration.
   * 
   * @param term The ANSITerm object to use.
   * @throws Exception If an error occurs during execution.
   */
  void perform(ANSITerm term) throws Exception {
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Styles applicable to the text ------------", 1, 1);
    term.printAt(term.setBold("Bold phrase"), 2, 10);
    term.printAt(term.setDim("Attenuated phrase"), 3, 10);
    term.printAt(term.setItalic("Frase en cursiva"), 4, 10);
    term.printAt(term.setBlink("Intermittent phrase"), 5, 10);
    term.printAt(term.setInverse("Phrase with inverted colors"), 6, 10);
    term.printAt(term.setHidden("Hidden message"), 7, 10);
    pauseWithMessage(0, "Press <ENTER> to return to menu");
  }
}
