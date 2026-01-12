package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pausa;

public class ShowCursorMovement {
  
  public ShowCursorMovement() {}
  
  public void perform(ANSITerm term) throws Exception {
    Long retardo = 50L;
    
    term.clearScreen();
    term.moveCursorToBegin();
    TerminalSize screenSize = term.getTerminalSize();
    term.printAt("------------ Moving the cursor ------------", 1, 1);
    
    term.moveCursorToBegin();
    for (int i = 0; i < screenSize.getLineas(); i++) {
      Thread.sleep(retardo);
      term.moveCursorDown(1);
    }
    for (int i = 0; i < screenSize.getColumnas(); i++) {
      Thread.sleep(retardo);
      term.moveCursorRight(1);
    }
    for (int i = 0; i < screenSize.getLineas(); i++) {
      Thread.sleep(retardo);
      term.moveCursorUp(1);
    }
    for (int i = 0; i < screenSize.getColumnas(); i++) {
      Thread.sleep(retardo);
      term.moveCursorLeft(1);
    }
    pausa(2000L, null);
  }
}
