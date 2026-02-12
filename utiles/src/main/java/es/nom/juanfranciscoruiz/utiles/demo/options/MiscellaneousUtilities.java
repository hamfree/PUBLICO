package es.nom.juanfranciscoruiz.utiles.demo.options;

import static es.nom.juanfranciscoruiz.utiles.Util.pause;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.*;

/**
 * This class provides a collection of miscellaneous utilities for demonstration purposes.
 * It includes methods for printing titles, messages, and pauses with formatted output.
 */
public class MiscellaneousUtilities {
    public static void miscellaneousUtilities() throws Exception {
        clearScreen(true);
        final long PAUSE_DURATION = 3000L;
        String msg = "Miscellaneous utilities...";
        prtln(2, title(msg, '*', 80));
        prtln(3, "Miscellaneous utilities not implemented yet.");
        pause(PAUSE_DURATION, "");
    }

}
