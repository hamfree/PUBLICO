package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.model.Dimensions;

public interface TermCtl {
  Dimensions getConsoleSize();
 
  boolean setConsoleSize(Dimensions dimensions);
  
  void clearScreen(boolean useANSI);
}
