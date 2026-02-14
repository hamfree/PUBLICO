package es.nom.juanfranciscoruiz.utiles.demo.options;

import es.nom.juanfranciscoruiz.utiles.TermCtl;
import es.nom.juanfranciscoruiz.utiles.impl.TermCtlImpl;

import static es.nom.juanfranciscoruiz.utiles.Util.pause;
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
    private TermCtl tc;
    private ConvertTypes() {
        tc = new TermCtlImpl();
    }
    
    private static final ConvertTypes INSTANCE = new ConvertTypes();
    
     public static ConvertTypes getInstance() {
        return INSTANCE;
    }
    
    public TermCtl getTc() {
        return tc;
    }
    
    public void setTc(TermCtl tc) {
        this.tc = tc;
    }
    
    public void convertTypes() throws Exception {
        getInstance().getTc().clearScreen(false);
        final long PAUSE_DURATION = 3000L;
        String msg = "Converting types";
        prtln(2, title(msg, '*', 80));
        // Implementation for type conversion demo
        // ...
        prtln(3, "Converting types not implemented yet.");
        pause(PAUSE_DURATION, null);
    }

}
