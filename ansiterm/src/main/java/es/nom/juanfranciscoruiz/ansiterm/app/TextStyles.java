package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import org.w3c.dom.Text;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pausa;

public class TextStyles {
  TextStyles() {}
  
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
    pausa(0, "Press <ENTER> to return to menu");
  }
}
