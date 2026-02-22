package es.nom.juanfranciscoruiz.ansiterm.app.options;

import com.sun.jna.LastErrorException;
import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.clearScreenAndPrintHeader;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * Demonstrates the use of raw terminal mode for keyboard input.
 *
 * @author Juan F. Ruiz
 */
public class RawMode {

    /**
     * Constructs a new RawMode.
     */
    public RawMode() {
    }

    /**
     * Logger used for tracing and debugging.
     */
    public static final Logger logger = LoggerFactory.getLogger(RawMode.class);

    /**
     * Performs the raw mode demonstration.
     *
     * @param term The ANSITerm object to use.
     * @throws Exception If an error occurs during execution.
     */
    public void perform(ANSITerm term) throws Exception {
        String title = "Keyboard RAW mode";
        String msg = "Sets the keyboard of console to RAW mode. Each keystroke generates a keyboard response in the form of code.";
        int columns = term.getTerminalSize().getColumns();
        clearScreenAndPrintHeader(term,title,msg,columns);

        try {
            pauseWithMessage(2000L, null);
            term.getOsCall().enableRawMode();

            term.printAt("Press keys. Press 'q' to exit.", 2, 1);
            term.printAt(3, 1);

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
