package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.ansiterm.utiles.Util;

public class DrawsRectangle {
  DrawsRectangle() {}
  
  void perform(ANSITerm term) throws Exception {
    term.clearScreen();
    term.moveCursorToBegin();
    TerminalSize ts = term.getTerminalSize();
    
    term.printAt("------------ Printing text at specific coordinates ------------", 1, 1);
    term.printAt("A rectangle will be drawn on the screen:", 2, 1);
    for (int col = 2; col < ts.getColumnas(); col++) {
      term.printAt("*", 3, col);
    }
    for (int linea = 3; linea < ts.getLineas() - 2; linea++) {
      term.printAt("*", linea, ts.getColumnas() - 1);
    }
    for (int col = ts.getColumnas() - 1; col > 1; col--) {
      term.printAt("*", ts.getLineas() - 2, col);
    }
    for (int linea = ts.getLineas() - 2; linea > 2; linea--) {
      term.printAt("*", linea, 2);
    }
    
    Util.pausa(0, "Press <ENTER> to return to menu");
  }
}
