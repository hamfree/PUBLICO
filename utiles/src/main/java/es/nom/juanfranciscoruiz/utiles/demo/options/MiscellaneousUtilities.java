package es.nom.juanfranciscoruiz.utiles.demo.options;

import static es.nom.juanfranciscoruiz.utiles.Util.pauseWithoutMessage;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.prtln;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.title;

public class MiscellaneousUtilities {
    public static void miscellaneousUtilities() throws Exception {
        final long TWO_SECONDS = 2000L;
        String msg = "Miscellaneous utilities...";
        prtln(2, title(msg, '*', 80));
        prtln(3, "Miscellaneous utilities not implemented yet.");
        pauseWithoutMessage(TWO_SECONDS);
    }

}
