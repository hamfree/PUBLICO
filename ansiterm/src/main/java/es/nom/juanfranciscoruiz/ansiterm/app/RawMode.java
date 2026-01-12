package es.nom.juanfranciscoruiz.ansiterm.app;

import com.sun.jna.LastErrorException;
import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pausa;

public class RawMode {
  
  RawMode() {}
  
  /**
   * Logger used for tracing and debugging.
   */
  public static final Logger logger = LoggerFactory.getLogger(RawMode.class);
  
  void perform(ANSITerm term) throws Exception {
    term.clearScreen();
    term.moveCursorToBegin();
    term.printAt("------------ Keyboard RAW mode ------------", 1, 1);
    
    try {
      pausa(2000L, null);
      term.getOsCall().enableRawMode();
      
      term.printAt("Press keys. Press 'q' to exit.", 2, 1);
      term.moveCursorToXY(3, 1);
      
      while (true) {
        int resp = System.in.read();
        char ch = (char) resp;
        System.out.println(resp + ", char = " + ch);
        if (ch == 'q') {
          break;
        }
      }
    } catch (LastErrorException e) {
      logger.error(String.valueOf(e.getErrorCode()));
      logger.error(e.getMessage());
      System.out.println(e.getMessage());
      System.exit(-1);
    } catch (IOException ex) {
      logger.error(ex.getMessage());
      System.out.println(ex.getMessage());
      System.exit(-1);
    } finally {
      term.getOsCall().disableRawMode();
    }
  }
}
