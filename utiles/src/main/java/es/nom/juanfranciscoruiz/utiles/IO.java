package es.nom.juanfranciscoruiz.utiles;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilidad para imprimir a la salida estándar y recibir caracteres del usuario
 * por la entrada estándar.
 *
 * @author hamfree
 */
public class IO {

    /**
     * Trazas
     */
    private static final Logger logger = LoggerFactory.getLogger(IO.class);

    /**
     * Caracter de salto de linea del sistema operativo
     */
    private static final String SL = System.lineSeparator();
    /**
     * Caracter separador de rutas del sistema operativo
     */
    private static final String SF = FileSystems.getDefault().getSeparator();
    /**
     * Caracter que indica el inicio del contenido de un objeto
     */
    private static final String CAR_INI = "[";
    /**
     * Caracter que indica el final del contenido de un objeto
     */
    private static final String CAR_FIN = "]";
    /**
     * Caracter separador de items
     */
    private static final String SEP = ", ";
    /**
     * Constante que representa el valor textual de NULL
     */
    private static final String NULL = "null";

    /**
     * Constantes para los mensajes de error
     */
    private static final String ERR_PARAM = "¡Parámetros nulos o incorrectos!";
    private static final String ERR_NULL = "¡Parámetros nulos!";
    private static final String ERR_SOME_NULL = "¡Alguno de los parámetros es nulo!";
    private static final String ERR_LONG = "La longitud de la linea es menor que la longitud del mensaje";

    /**
     * Es igual que un InputStream, del que extiende y usando el patrón
     * Decorator hacemos que su método close() no haga nada.
     *
     */
    static class UnclosableInputStreamDecorator extends InputStream {

        private final InputStream inputStream;

        public UnclosableInputStreamDecorator(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public int read(byte[] b) throws IOException {
            return inputStream.read(b);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return inputStream.read(b, off, len);
        }

        @Override
        public long skip(long n) throws IOException {
            return inputStream.skip(n);
        }

        @Override
        public int available() throws IOException {
            return inputStream.available();
        }

        @Override
        public synchronized void mark(int readlimit) {
            inputStream.mark(readlimit);
        }

        @Override
        public synchronized void reset() throws IOException {
            inputStream.reset();
        }

        @Override
        public boolean markSupported() {
            return inputStream.markSupported();
        }

        @Override
        public void close() throws IOException {
            // Do nothing
        }
    }

    /**
     * Imprime los argumentos indicados en la salida estándar. Intentará usar el
     * objeto Console, en caso contrario usará el canal estandar de salida.
     *
     * @param args una lista de objetos a imprimir
     * @throws IllegalArgumentException en caso de pasar parámetros nulos.
     */
    public static void prt(Object... args) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (args == null) {
            if (logger.isErrorEnabled()) {
                logger.error(ERR_NULL);
            }
            throw new IllegalArgumentException(ERR_NULL);
        }
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    if (logger.isErrorEnabled()) {
                        logger.error(ERR_NULL);
                    }
                    throw new IllegalArgumentException(ERR_SOME_NULL);
                }
            }
        }
        for (Object arg : args) {
            sb.append(arg.toString());
        }

        print(sb);
    }

    /**
     * Imprime los argumentos indicados en la salida estándar. Intentará usar el
     * objeto Console, si no puede utilizará el canal estandar de salida.
     * Después de imprimir la lista de argumentos hará tantos saltos de línea
     * como indiquemos en el parámetro sl.
     *
     * @param sl entero con la cantidad de saltos de línea a realizar
     * @param args una lista de objetos a imprimir
     * @throws IllegalArgumentException en caso de pasar parámetros nulos.
     */
    public static void prtln(int sl, Object... args) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (args == null) {
            if (logger.isErrorEnabled()) {
                logger.error(ERR_NULL);
            }
            throw new IllegalArgumentException(ERR_NULL);
        }
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    if (logger.isErrorEnabled()) {
                        logger.error(ERR_NULL);
                    }
                    throw new IllegalArgumentException(ERR_SOME_NULL);
                }
            }
        }
        
        for (Object arg : args) {
            sb.append(arg.toString());
        }
        if (sl > 0) {
            for (int i = 0; i < sl; i++) {
                sb.append(SL);
            }
        }

        print(sb);
    }

    /**
     * Imprime el contenido del argumento sb en la salida estándar. Intentará
     * usar primero el objeto Console. En caso de que no pueda usar Console
     * utilizará el canal estandar de salida.
     *
     * @param sb StringBuilder que contiene el texto a imprimir.
     */
    private static void print(StringBuilder sb) {
        Console con = System.console();
        if (con == null) {
            System.out.format("%s", sb.toString());
        } else {
            con.printf("%s", sb.toString());
        }
    }

    /**
     * Recupera en una cadena lo introducido por el usuario en la entrada
     * estándar hasta que pulse INTRO. Intentará primero usar el objeto Console,
     * si no puede utilizarlo, usará la entrada estándar System.in.
     *
     * @return una cadena con lo introducido por el usuario hasta que pulse la
     * tecla INTRO.
     * @throws java.lang.Exception En caso de producirse algún error.
     */
    public static String read() throws Exception {
        String dato;

        Console con = System.console();
        if (con == null) {
            try (Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in))) {
                dato = sc.nextLine();
            }
        } else {
            dato = con.readLine();
        }
        return dato;
    }

    /**
     * Genera una cadena que contendrá un mensaje centrado (msg) como título
     * entre dos líneas de la longitud indicada por 'longitud' compuestas por el
     * carácter 'car'.
     *
     * @param msg el mensaje del título
     * @param car el caracter que compone cada línea
     * @param longitud la longitud de la línea (debe ser mayor que la longitud
     * del mensaje)
     * @return una cadena con el mensaje centrado como título entre dos líneas
     * @throws IllegalArgumentException en caso de pasarle parámetros nulos o
     * incorrectos.
     */
    public static String titulo(String msg, Character car, int longitud) {
        StringBuilder sb = new StringBuilder();
        if (Types.isNullOrEmpty(msg) || Types.isNullOrEmpty(car)) {
            if (logger.isErrorEnabled()) {
                logger.error(ERR_PARAM);
            }
            throw new IllegalArgumentException(ERR_PARAM);
        } else {
            int longMsg = msg.length();
            if (longitud > longMsg) {
                sb.append(getSL())
                        .append(linea(car, longitud))
                        .append(getSL());
                int relleno = (longitud - longMsg) / 2;
                sb.append(repiteCaracter(' ', relleno))
                        .append(msg)
                        .append(repiteCaracter(' ', relleno));
                sb.append(getSL()).
                        append(linea(car, longitud));
            } else {
                if (logger.isErrorEnabled()) {
                    logger.error(ERR_LONG);
                }
                throw new IllegalArgumentException(ERR_LONG);
            }
        }
        return sb.toString();
    }

    /**
     * Genera una línea compuesta por el carácter 'car' que se repetirá tantas
     * veces como indique 'longitud'
     *
     * @param car carácter a repetir
     * @param longitud número de veces que se repetirá el carácter 'car'
     * @return String con la línea compuesta de caracteres 'car' repetidos
     * tantas veces como indica el parámetro 'longitud'.
     * @throws IllegalArgumentException en caso de pasarle parámetros nulos o
     * incorrectos.
     */
    public static String linea(Character car, int longitud) {
        String linea;
        if (car != null && longitud > 0) {
            linea = repiteCaracter(car, longitud);
        } else {
            if (logger.isErrorEnabled()) {
                logger.error(ERR_PARAM);
            }
            throw new IllegalArgumentException(ERR_PARAM);
        }
        return linea;
    }

    /**
     * Genera una cadena compuesta por el caracter <code>car</code> tantas veces
     * como indica <code>veces</code>.
     *
     * @param car el caracter con el que va a estar compuesta la cadena
     * @param veces el número de veces que se repite el caracter en la cadena
     * @return una cadena con el caracter <code>car</code> repetida tantas veces
     * como indica <code>veces</code>
     * @throws IllegalArgumentException en caso de pasarle parámetros nulos o
     * incorrectos.
     */
    public static String repiteCaracter(Character car, int veces) {
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        if (car != null && veces > 0) {
            for (int i = 0; i < veces; i++) {
                sb.append(car);
            }
        } else {
            if (logger.isErrorEnabled()) {
                logger.error(ERR_PARAM);
            }
            throw new IllegalArgumentException(ERR_PARAM);
        }
        return sb.toString();
    }

    //Getters y Setters
    /**
     * Devuelve SL, constante con el caracter de control del salto de línea del
     * sistema operativo actual.
     *
     * @return una cadena constante
     */
    public static String getSL() {
        return SL;
    }

    /**
     * Devuelve SF, constante con el caracter separador de rutas del sistema
     * operativo actual.
     *
     * @return una cadena constante
     */
    public static String getSF() {
        return SF;
    }

    /**
     * Devuelve CAR_INI, cosntante con el caracter que indica el inicio del
     * contenido de un objeto
     *
     * @return una cadena constante
     */
    public static String getCAR_INI() {
        return CAR_INI;
    }

    /**
     * Devuelve CAR_FIN, cosntante con el caracter que indica el final del
     * contenido de un objeto
     *
     * @return una cadena constante
     */
    public static String getCAR_FIN() {
        return CAR_FIN;
    }

    /**
     * Devuelve SEP, el separador de items
     *
     * @return una cadena constante
     */
    public static String getSEP() {
        return SEP;
    }

    /**
     * Devuelve NULL, una constante que representa el valor nulo.
     *
     * @return una cadena constante
     */
    public static String getNULL() {
        return NULL;
    }
}
