package es.nom.juanfranciscoruiz.utiles.demo.options;

import com.sun.jna.platform.win32.WinPerf;
import es.nom.juanfranciscoruiz.utiles.TermCtl;
import es.nom.juanfranciscoruiz.utiles.impl.TermCtlImpl;

import static es.nom.juanfranciscoruiz.utiles.Util.pause;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.*;

/**
 * This class provides a collection of miscellaneous utilities for demonstration purposes.
 * It includes methods for printing titles, messages, and pauses with formatted output.
 */
public class MiscellaneousUtilities {
    private TermCtl tc;
    private MiscellaneousUtilities() {
        tc = new TermCtlImpl();
    }
    
    private static final MiscellaneousUtilities INSTANCE = new MiscellaneousUtilities();
    
    public static MiscellaneousUtilities getInstance() {
        return INSTANCE;
    }
    
    public TermCtl getTc() {
        return tc;
    }
    
    public void setTc(TermCtl tc) {
        this.tc = tc;
    }
    
    public void miscellaneousUtilities() throws Exception {
        this.getTc().clearScreen(true);
        final long PAUSE_DURATION = 3000L;
        String msg = "Miscellaneous utilities...";
        prtln(2, title(msg, '*', 80));
        prtln(3, "Miscellaneous utilities not implemented yet.");
        pause(PAUSE_DURATION, "");
    }
}
