package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pausa;

public class ShowTextColors256 {
  
  ShowTextColors256(){}
  
  void perform(ANSITerm term) throws Exception {
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Displays the palette of 256 colors ------------", 1, 1);
    
    int j = 0;
    int line = 2;
    int col = 0;
    term.moveCursorToXY(line, col);
    for (int i = 0; i < 256; i++) {
      String msg = term.setColor256(i, String.valueOf(i));
      if (j > 15) {
        term.printAt(msg, line, col);
        term.linefeed();
        j = 0;
        col = 0;
        line = line + 1;
      } else {
        term.printAt(msg.concat(" "), line, col);
        col = col + 5;
        j++;
      }
    }
    
    pausa(0, "Press <ENTER> to return to menu");
  }
}
