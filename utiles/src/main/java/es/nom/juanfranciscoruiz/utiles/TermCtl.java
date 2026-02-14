package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.model.Dimensions;

/**
 * Interface for terminal control operations.
 */
public interface TermCtl {
  /**
   * Retrieves the current dimensions of the terminal console.
   *
   * @return the dimensions of the terminal console
   */
  Dimensions getConsoleSize();

  /**
   * Sets the dimensions of the terminal console.
   *
   * @param dimensions the new dimensions to set for the terminal console. It is represented
   *                   by a {@link Dimensions} object containing the number of rows and columns.
   * @return {@code true} if the console size was successfully set; {@code false} otherwise.
   */
  boolean setConsoleSize(Dimensions dimensions);

  /**
   * Clears the terminal screen.
   *
   * @param useANSI whether to use ANSI escape codes for clearing the screen
   */
  void clearScreen(boolean useANSI);
}
