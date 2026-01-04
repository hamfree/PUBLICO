package es.nom.juanfranciscoruiz.ansiterm;

import static es.nom.juanfranciscoruiz.ansiterm.ANSITerm.logger;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * It provides access to the raw and cooked modes of the Linux terminal, as well
 * as theas obtaining the terminal size using ANSI escape sequences and setting
 * the raw mode so that ANSI code does not appear on the screen.
 * 
 * @author juanf
 */
public class LinuxTerminal implements ITerminal {

    private static final String ESC = "\033";
    private static final String REC_POS_CUR = ESC + "[6n";

    /**
     * Instantiate a LinuxTerminal object
     */
    public LinuxTerminal(){}
    
    /**
     * Enable the console's 'raw' mode. Use the 'stty' command, which is usually
     * available in all UNIX environments. In 'raw' mode, the characters typed
     * by the user are passed directly to the application without the user
     * having to press Enter. The '-echo' parameter is passed to prevent the
     * typed characters from being displayed on the screen.
     */
    @Override
    public void enableRawMode() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty raw -echo < /dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disable the terminal's 'raw' mode and enable 'cooked' mode, which
     * is the one usually used (the typed characters are displayed and to
     * receive our command the shell must press ENTER).
     */
    @Override
    public void disableRawMode() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty cooked echo < /dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the terminal size.
     * @return a TerminalSize object with the lines and columns of the terminal.
     */
    @Override
    public TerminalSize getTerminalSize() {
        Posicion inicialPosicion = readCurrentPosition();
        gotoXY(10000, 10000);
        Posicion result = readCurrentPosition();
        gotoXY(inicialPosicion.getCol(), inicialPosicion.getLin());
        return new TerminalSize(result.getCol(), result.getLin());
    }

    
    // Utility methods for 'getTerminalSize()'

    /**
     *
     * @return
     */
    private Posicion readCurrentPosition() {
        try {
            this.enableRawMode();
            System.out.print(REC_POS_CUR);

            String result = "";
            int character;

            do {
                character = System.in.read();
                if (character == 27) {
                    result += "^";
                } else {
                    result += (char) character;
                }
            } while (character != 82); // 'R'

            Pattern pattern = Pattern.compile("\\^\\[(\\d+);(\\d+)R");
            Matcher matcher = pattern.matcher(result);
            if (matcher.matches()) {
                return new Posicion(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)));
            } else {
                return new Posicion(1, 1);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return new Posicion(1, 1);
        } finally {
            this.disableRawMode();
        }
    }

    /**
     *
     * @param x
     * @param y
     */
    private void gotoXY(int x, int y) {
        System.out.print(String.format("\u001B[%d;%dH", y, x)); // CSI n ; m H
    }

    /**
     *
     * @param screenPosition
     */
    private void gotoXY(Posicion screenPosition) {
        System.out.print(String.format("\u001B[%d;%dH", screenPosition.getLin(), screenPosition.getCol())); // CSI n ; m H
    }
}
