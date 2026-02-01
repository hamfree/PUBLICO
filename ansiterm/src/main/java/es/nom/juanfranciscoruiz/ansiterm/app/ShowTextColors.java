package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.codes.BGColor;
import es.nom.juanfranciscoruiz.ansiterm.codes.Color;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pauseWithMessage;

/**
 * Demonstrates various text and background colors.
 *
 * @author Juan F. Ruiz
 */
public class ShowTextColors {
  /**
   * Constructs a new ShowTextColors.
   */
  ShowTextColors() {}
  
  /**
   * Performs the text colors demonstration.
   * 
   * @param term The ANSITerm object to use.
   * @throws Exception If an error occurs during execution.
   */
  void perform(ANSITerm term) throws Exception {
    term.clearScreen();
    term.moveCursorToBegin();
    
    term.printAt("------------ Colors applicable to the text and background ------------", 1, 1);
    term.printAt("Colors in the foreground", 2, 1);
    
    term.linefeed();
    term.printAt(term.setColor(Color.YELLOW, "Phrase in yellow"), 4, 1);
    term.printAt(term.setColor(Color.BLUE, "Phrase in blue"), 5, 1);
    term.printAt(term.setColor(Color.WHITE, "Blank phrase"), 6, 1);
    term.printAt(term.setColor(Color.CYAN, "Phrase in cyan"), 7, 1);
    term.printAt(term.setColor(Color.MAGENTA, "Phrase in magenta"), 8, 1);
    term.printAt(term.setColor(Color.BLACK, "Phrase in black"), 9, 1);
    term.printAt(term.setColor(Color.RED, "Phrase in red"), 10, 1);
    term.printAt(term.setColor(Color.GREEN, "Phrase in green"), 11, 1);
    term.linefeed();
    term.printAt("Background colors", 13, 1);
    term.linefeed();
    term.printAt(term.setBackgroundColor(BGColor.YELLOW, "Phrase with a yellow background"), 15, 1);
    term.printAt(term.setBackgroundColor(BGColor.BLUE, "Phrase with a blue background"), 16, 1);
    term.printAt(term.setBackgroundColor(BGColor.WHITE, "Phrase with a white background"), 17, 1);
    term.printAt(term.setBackgroundColor(BGColor.CYAN, "Phrase with cyan background"), 18, 1);
    term.printAt(term.setBackgroundColor(BGColor.MAGENTA, "Phrase with a magenta background"), 19, 1);
    term.printAt(term.setBackgroundColor(BGColor.BLACK, "Phrase with a black background"), 20, 1);
    term.printAt(term.setBackgroundColor(BGColor.RED, "Phrase with a red background"), 21, 1);
    
    pauseWithMessage(0, null);
    
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Colors applicable to the text and background ------------", 1, 1);
    term.printAt("Bright colors in the foreground", 2, 1);
    term.linefeed();
    term.printAt(term.setColor(Color.GLOSSY_YELLOW, "Phrase in glossy yellow"), 4, 1);
    term.printAt(term.setColor(Color.GLOSSY_BLUE, "Phrase in glossy blue"), 5, 1);
    term.printAt(term.setColor(Color.GLOSSY_WHITE, "Glossy Blank phrase"), 6, 1);
    term.printAt(term.setColor(Color.GLOSSY_CYAN, "Phrase in glossy cyan"), 7, 1);
    term.printAt(term.setColor(Color.GLOSSY_MAGENTA, "Phrase in glossy magenta"), 8, 1);
    term.printAt(term.setColor(Color.GLOSSY_BLACK, "Phrase in glossy black"), 9, 1);
    term.printAt(term.setColor(Color.GLOSSY_RED, "Phrase in glossy red"), 10, 1);
    term.printAt(term.setColor(Color.GLOSSY_GREEN, "Phrase in glossy green"), 11, 1);
    term.linefeed();
    term.printAt("Bright colors for the background", 13, 1);
    term.linefeed();
    term.printAt(term.setBackgroundColor(BGColor.GLOSSY_YELLOW, "Phrase in glossy yellow " +
        "background"), 15, 1);
    term.printAt(term.setBackgroundColor(BGColor.GLOSSY_BLUE, "Phrase in glossy blue" +
        "background"), 16, 1);
    term.printAt(term.setBackgroundColor(BGColor.GLOSSY_WHITE, "Glossy Blank phrase" +
        "background"), 17, 1);
    term.printAt(term.setBackgroundColor(BGColor.GLOSSY_CYAN, "Phrase in glossy cyan" +
        "background"), 18, 1);
    term.printAt(term.setBackgroundColor(BGColor.GLOSSY_MAGENTA, "Phrase in glossy magenta" +
        "background"), 19, 1);
    term.printAt(term.setBackgroundColor(BGColor.GLOSSY_BLACK, "Phrase in glossy black" +
        "background"), 20, 1);
    term.printAt(term.setBackgroundColor(BGColor.GLOSSY_RED, "Phrase in glossy red" +
        "background"), 21, 1);
    term.printAt(term.setBackgroundColor(BGColor.GLOSSY_GREEN, "Phrase in glossy green" +
        "background"), 22, 1);
    
    pauseWithMessage(0, "Press <ENTER> to return to menu");
  }
}
