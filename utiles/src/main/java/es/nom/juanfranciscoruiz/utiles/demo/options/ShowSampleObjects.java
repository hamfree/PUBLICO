package es.nom.juanfranciscoruiz.utiles.demo.options;

import es.nom.juanfranciscoruiz.utiles.TermCtl;
import es.nom.juanfranciscoruiz.utiles.helper.ObjectsGenerator;
import es.nom.juanfranciscoruiz.utiles.impl.TermCtlImpl;

import static es.nom.juanfranciscoruiz.utiles.Util.FOREVER;
import static es.nom.juanfranciscoruiz.utiles.Util.pause;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.prtln;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.title;

/**
 * A utility class that provides functionality to display sample objects in a formatted manner
 * on the console. This includes generating and showcasing collections like maps and lists.
 * <p>
 * This class follows a singleton-like approach for instance creation, providing a static
 * factory method for getting a new instance.
 * <p>
 * Usage involves invoking the {@code showObjects()} method which handles the generation
 * and display of objects in a predefined format. The method also clears the console
 * before displaying the objects.
 * <p>
 * Note: This class is designed for specific internal display requirements and initializes
 * its terminal control functionality using an implementation of {@code TermCtl}.
 */
public class ShowSampleObjects {
  /**
   * A private instance of the {@link TermCtl} interface used for controlling terminal-related
   * operations such as clearing the screen, retrieving console dimensions, or setting the
   * terminal size. This variable is initialized with an implementation of {@code TermCtl},
   * specifically {@code TermCtlImpl}, providing platform-specific functionality for terminal
   * management.
   * <p>
   * The {@code tc} variable serves as the primary terminal control mechanism within this class,
   * allowing for operations such as formatting console displays and managing terminal behavior.
   * <p>
   * It is an essential part for handling console interactions and is used across various
   * methods in the class to ensure proper terminal management and output formatting.
   */
  private final TermCtl tc;
  
  /**
   * Constructs an instance of {@code ShowSampleObjects} and initializes its terminal
   * control instance. This constructor is private to enforce controlled instance
   * creation through a static factory method.
   * <p>
   * The constructor initializes the {@code tc} field with a new instance of
   * {@link TermCtlImpl}. This setup ensures that terminal-related functionalities
   * such as screen clearing and terminal formatting are available throughout the
   * lifecycle of the {@code ShowSampleObjects} instance.
   * <p>
   * Note:
   * - This private constructor follows the singleton-like design pattern.
   * - Direct instantiation is restricted to maintain consistency in the initialization
   *   process and encapsulate the terminal control logic.
   */
  private ShowSampleObjects() {
    tc = new TermCtlImpl();
  }
  
  /**
   * Static factory method for getting a new instance of {@code ShowSampleObjects}.
   * @return a new instance of {@code ShowSampleObjects}.
   */
  public static ShowSampleObjects getInstance() {
    return new ShowSampleObjects();
  }
  
  /**
   * Displays a series of sample objects on the terminal, including maps, lists,
   * and a complex map of lists. The method performs the following tasks:
   * <p>
   * - Clears the terminal screen.
   * - Prints a formatted title.
   * - Displays generated objects such as a map, lists in both ascending and
   *   descending order, and a map of lists.
   * - Waits indefinitely after displaying the objects.
   * <p>
   * Throws exceptions in case of errors during terminal operations or object
   * generation.
   *
   * @throws Exception if an error occurs during execution.
   */
  public void run() throws Exception {
    tc.clearScreen(true);
    prtln(1, title("Sample objects", '*', 80));
    prtln(1, "Sample objects:");
    prtln(1, "  - Map: " + ObjectsGenerator.generateMap());
    prtln(1, "  - List: " + ObjectsGenerator.generateList(true));
    prtln(1, "  - List: " + ObjectsGenerator.generateList(false));
    prtln(1, "  - Map: " + ObjectsGenerator.generateMapOfLists(
        ObjectsGenerator.generateList(true),
        ObjectsGenerator.generateList(false)
      )
    );
    pause(FOREVER, "");
  }
}
