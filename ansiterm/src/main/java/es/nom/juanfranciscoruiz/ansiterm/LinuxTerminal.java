package es.nom.juanfranciscoruiz.ansiterm;

import static es.nom.juanfranciscoruiz.ansiterm.ANSITerm.logger;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Proporciona acceso a los modos raw y cooked del terminal de Linux así como
 * la obtención del tamaño del terminal mediante secuencias de escape ANSI y el
 * establecimiento del modo raw para que no aparezca el código ANSI en pantalla.
 * 
 * @author juanf
 */
public class LinuxTerminal implements ITerminal {

    private static final String ESC = "\033";
    private static final String REC_POS_CUR = ESC + "[6n";

    /**
     * Habilita el modo 'raw' de la consola. Utiliza el comando 'stty' que
     * suele estar disponible en todos los entornos UNIX. En el modo 'raw' los 
     * caracteres tecleados por el usuario se pasan directamente a la 
     * aplicación sin que el usuario tenga que pulsar INTRO. Se pasa el parámetro
     * '-echo' para que no aparezcan los caracteres tecleados en pantalla.
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
     * Deshabilita el modo 'raw' del terminal y habilita el modo 'cooked', que 
     * es el que habitualmente se usa (se muestran los caracteres tecleados y 
     * para que el shell reciba nuestra orden hay que pulsar INTRO)
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
     * Obtiene el tamaño del terminal.
     * @return un objeto TerminalSize con las líneas y columnas del terminal.
     */
    @Override
    public TerminalSize getTerminalSize() {
        Posicion inicialPosicion = readCurrentPosition();
        gotoXY(10000, 10000);
        Posicion result = readCurrentPosition();
        gotoXY(inicialPosicion.getCol(), inicialPosicion.getLin());

        return new TerminalSize(result.getCol(), result.getLin());
    }

    
    // Métodos de utilidad para el método 'getTerminalSize()'
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

    private void gotoXY(int x, int y) {
        System.out.print(String.format("\u001B[%d;%dH", y, x)); // CSI n ; m H
    }

    private void gotoXY(Posicion screenPosition) {
        System.out.print(String.format("\u001B[%d;%dH", screenPosition.getLin(), screenPosition.getCol())); // CSI n ; m H
    }

}
