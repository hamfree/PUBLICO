package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.codes.CursorStylesCodes;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.imprimeBloqueTexto;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pausa;

/**
 * Demonstrates screen and line deletion and movement commands.
 *
 * @author Juan F. Ruiz
 */
public class ScreenAndLineDeletions {
  
  final static Long DELAY = 4000L;
  final static String MSG = "The process will continue automatically every 4 seconds. Do not press any keys!";
  final static String TITLE = "------------ Deleted from the cursor and scroll ------------";
  
  
  /**
   * Constructs a new ScreenAndLineDeletions.
   */
  ScreenAndLineDeletions() {}
  
  /**
   * Performs the screen and line deletions demonstration.
   * 
   * @param term The ANSITerm object to use.
   * @throws Exception If an error occurs during execution.
   */
  void perform(ANSITerm term) throws Exception {
    int columnas = term.getTerminalSize().getColumnas();
    int filas = term.getTerminalSize().getLineas();
    
    
    clearScreen(term);
    term.cursorShow();
    term.cursorChangeStyle(CursorStylesCodes.CURSOR_BLOCK_SHAPE);
    printTitle(term);
    term.printAt("(1/13) Delete from the cursor to the beginning of the screen", 2, 1);
    showWarning(term);
    
    imprimeBloqueTexto(term, 5, filas - 5, columnas);
    term.moveCursorToXY(12, 60);
    Thread.sleep(DELAY);
    term.deleteFromCursorToBeginScreen();
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(2/13) Deleted from the cursor to the end of the screen", 2, 1);
    showWarning(term);
    
    imprimeBloqueTexto(term, 5, filas - 5, columnas);
    term.moveCursorToXY(12, 60);
    Thread.sleep(DELAY);
    term.deleteFromCursorToEndScreen();
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(3/13) Deleted from the cursor to the beginning of the line", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 5, 1, columnas);
    term.moveCursorToXY(5, 60);
    Thread.sleep(DELAY);
    term.deleteFromCursorToBeginLine();
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(4/13) Deleted from the cursor to the end of the line", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 5, 1, columnas);
    term.moveCursorToXY(5, 60);
    Thread.sleep(DELAY);
    term.deleteFromCursorToEndLine();
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(5/13) Deleting the line where the cursor is located", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 5, 3, columnas);
    term.moveCursorToXY(6, 60);
    Thread.sleep(DELAY);
    term.deleteLine();
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(6/13) Delete 5 lines including the one containing the cursor", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 5, 15, columnas);
    term.moveCursorToXY(6, 60);
    Thread.sleep(DELAY);
    term.deleteLines(5);
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(7/13) Delete the 10 characters before the cursor position", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 5, 1, columnas);
    term.moveCursorToXY(5, 60);
    Thread.sleep(DELAY);
    for (int i = 0; i < 10; i++) {
      term.deleteCharacter();
    }
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(8/13) Delete the 20 characters following the cursor position", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 5, 1, columnas);
    term.moveCursorToXY(5, 60);
    Thread.sleep(DELAY);
    term.delChars(20);
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(9/13) Deletes 20 characters from the current cursor position by overwriting them with a space character.", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 5, 1, columnas);
    term.moveCursorToXY(5, 60);
    Thread.sleep(DELAY);
    term.delCharsWithSpaces(20);
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(10/13) Insert 10 spaces at the current cursor position. Shift all existing text to the right.", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 5, 1, columnas);
    term.moveCursorToXY(5, 60);
    Thread.sleep(DELAY);
    term.insertSpaces(10);
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(11/13) Insert 10 lines at the current cursor position. The cursor line and the lines below it will shift down", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 5, 5, columnas);
    term.moveCursorToXY(7, 60);
    Thread.sleep(DELAY);
    term.insertLines(10);
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(12/13) Move " + filas + " lines down the text", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 3, filas - 3, columnas);
    
    for (int i = 1; i <= filas; i++) {
      Thread.sleep(100L);
      term.moveTextDown(1);
    }
    
    Thread.sleep(DELAY);
    
    clearScreen(term);
    printTitle(term);
    term.printAt("(13/13) Move " + filas + " lines upwards the text", 2, 1);
    showWarning(term);
    imprimeBloqueTexto(term, 3, filas - 3, columnas);
    
    Thread.sleep(2000L);
    
    for (int i = 1; i <= filas; i++) {
      Thread.sleep(100L);
      term.moveTextUp(1);
    }
    
    Thread.sleep(DELAY);
    pausa(0, "Press <ENTER> to return to menu");
  }
  
  private static void clearScreen(ANSITerm term) {
    term.clearScreen();
    term.moveCursorToBegin();
  }
  
  private static void printTitle(ANSITerm term) throws ANSITermException {
    term.printAt(TITLE, 1, 1);
  }
  
  private static void showWarning(ANSITerm term) throws ANSITermException {
    term.printAt(MSG, 3, 1);
  }
}
