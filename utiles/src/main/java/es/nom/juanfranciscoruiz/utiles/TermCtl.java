package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.model.Dimensions;
import es.nom.juanfranciscoruiz.utiles.model.Using;

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
   * Clears the terminal screen based on the specified usage mode.
   * <p>
   * This method provides an abstraction for clearing the terminal screen,
   * allowing different implementations or approaches depending on the specified
   * usage mode.
   *
   * @param use the usage mode specifying how the screen should be cleared.
   *            It is represented by a {@link Using} enum which can include
   *            options like SO, ANSI, API, or JAVA.
   */
  void clearScreen(Using use);
}
