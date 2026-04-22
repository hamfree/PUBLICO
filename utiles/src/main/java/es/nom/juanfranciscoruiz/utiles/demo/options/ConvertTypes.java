package es.nom.juanfranciscoruiz.utiles.demo.options;

import es.nom.juanfranciscoruiz.utiles.TermCtl;
import es.nom.juanfranciscoruiz.utiles.TypeConverter;
import es.nom.juanfranciscoruiz.utiles.exceptions.TypeConverterException;
import es.nom.juanfranciscoruiz.utiles.impl.TermCtlImpl;
import es.nom.juanfranciscoruiz.utiles.model.Using;

import static es.nom.juanfranciscoruiz.utiles.Util.*;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.*;

/**
 * The ConvertTypes class provides a placeholder for demonstrating type conversion
 * functionalities. The class includes a static method to simulate the conversion
 * of types with formatted messages and pauses for demonstration purposes.
 * <p>
 * This class does not currently implement any actual type conversion logic but
 * serves as a framework for future development of type conversion utilities.
 * <p>
 * Utility methods such as formatted printing, title generation, and pausing
 * are used to enhance the demonstration output.
 */
public class ConvertTypes {
    /**
     * Private constructor to make this class non-instantiable
     */
    private TermCtl tc;

    /**
     * Private constructor to make this class non-instantiable
     */
    private ConvertTypes() {
        tc = new TermCtlImpl();
    }

    /**
     * Singleton instance of the ConvertTypes class.
     */
    private static final ConvertTypes INSTANCE = new ConvertTypes();

    /**
     * Returns the singleton instance of the ConvertTypes class.
     * This ensures that only one instance of the class exists throughout
     * the application, following the Singleton design pattern.
     *
     * @return the singleton instance of the ConvertTypes class.
     */
    public static ConvertTypes getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves the TermCtl implementation associated with this class.
     * The TermCtl interface provides methods for interacting with the terminal,
     * such as getting or setting console dimensions and clearing the screen.
     *
     * @return the TermCtl instance used by this class.
     */
    public TermCtl getTc() {
        return tc;
    }

    /**
     * Sets the TermCtl implementation for this class.
     *
     * @param tc the TermCtl instance to be associated with this class.
     */
    public void setTc(TermCtl tc) {
        this.tc = tc;
    }

    /**
     * Simulates the process of type conversion by displaying placeholder messages
     * and pausing for demonstration purposes.
     * <p>
     * This method clears the screen, displays a formatted title message indicating
     * that types are being converted, logs that the conversion logic is not yet
     * implemented, and pauses for a predefined duration.
     * <p>
     * The method uses the following utility functionalities:
     * - Clears the terminal screen using `getInstance().getTc().clearScreen(true)`.
     * - Prints formatted messages with utility methods such as `prtln` and `title`.
     * - Pauses execution using the `pause` method.
     * <p>
     * Note: The actual implementation for converting types has not been provided
     * and is indicated by a placeholder comment within the method.
     *
     * @throws Exception if an error occurs during screen clearing or pausing.
     */
    public void run() throws Exception {
        getInstance().getTc().clearScreen(Using.ANSI);
        String title = "Converting types";
        String message;
        String yourInput;
        String response;
        prtln(2, title(title, '*', 80));
        // Implementation for type conversion demo
        // Do a demo with all the methods of the class TypeConverter
        prtln(2, "This demo shows you several methods to convert types.");
        try {
            //extractLongFromString() demo
            message = "Please, type a string with digits and other chars. " + SL +
                    "Press <ENTER> to send your input. ";
            prtln(1, message);
            prt("-> ");
            yourInput = read();
            Long longFromString = TypeConverter.extractLongFromString(yourInput);
            prtln(2, "Long from String '" + yourInput + "': " + longFromString);
            // extractDigits() demo
             message = "Please enter some text containing digits. The digits can be jumbled however you like. " + SL +
                    "Press <ENTER> to send your input.";
            prtln(2, message);
            prt("-> ");
            yourInput = read();
            response = TypeConverter.extractDigits(yourInput);
            prtln(2, "You entered '" + yourInput + SL + "'. The digits are: '" + response + "'.");
        } catch (TypeConverterException e) {
            dbg(logger, e.getMessage());
            prtln(2, "Something went wrong: " + e.getMessage());
        }

        pause(FOREVER, null);
    }

}
