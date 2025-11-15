package es.nom.juanfranciscoruiz.utiles;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility to print to standard output and receive characters from the user 
 * through standard input.
 * 
 * TODO: Hacerla estática, y convertirla en un Singleton.
 * Incluir más métodos que permita hacer más cosas, o mejor, generar una 
 * interfaz IO y generar después una jerarquía de clases que la implementen: 
 * una sencilla, como esta clase, otra que se apoye en la librería 'ansiterm' y
 * que permita realizar operaciones más complejas en la pantalla, etc.
 *
 * @author hamfree
 */
public class IO {

    /**
     * Traces
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IO.class);

    /**
     * Operating system line break character
     */
    private static final String LS = System.lineSeparator();
    /**
     * Operating system path separator character
     */
    private static final String FS = FileSystems.getDefault().getSeparator();
    /**
     * Character indicating the beginning of the content of an object in its 
     * textual representation when passed through a converter function.
     */
    private static final String CHAR_INI = "[";
    /**
     * Character indicating the end of an object's contents in its textual 
     * representation when passed through a converter function.
     */
    private static final String CHAR_END = "]";
    /**
     * Item separator character of values of an collection type object when 
     * passed through a converter function.
     */
    private static final String SEP = ", ";
    /**
     * Constant representing the textual value of NULL when an object is 
     * converted into its textual representation.
     */
    private static final String NULL = "null";

    /**
     * Constants for error messages
     */
    private static final String ERR_PARAM = "Null or incorrect parameters!";
    private static final String ERR_NULL = "Null parameters!";
    private static final String ERR_SOME_NULL = "One of the parameters is null!";
    private static final String ERR_LONG = "The line length is less than the message length";

    /**
     * It is the same as an InputStream, which it extends and using the 
     * Decorator pattern we make its close() method do nothing. With this we 
     * avoid the problem of closing the standard input and not being able to 
     * reopen it from the current process.
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
     * Prints the given arguments to standard output. It will attempt to use the 
     * Console object, otherwise it will use the standard output channel.
     *
     * @param args a list of objects to print
     * @throws IllegalArgumentException in case of passing null or incorrect 
     * parameters.
     */
    public static void prt(Object... args) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (args == null) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(ERR_NULL);
            }
            throw new IllegalArgumentException(ERR_NULL);
        }
        if (args.length > 0) {
            for (Object arg : args) {
                if (arg == null) {
                    if (LOGGER.isErrorEnabled()) {
                        LOGGER.error(ERR_NULL);
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
     * Prints the specified arguments to standard output. It will try to use the 
     * Console object, otherwise it will use the standard output channel. After 
     * printing the argument list it will make as many newlines as indicated in 
     * the sl parameter.
     *
     * @param ls integer with the number of line breaks to perform
     * @param args a list of objects to print to standar output
     * @throws IllegalArgumentException in case of passing null or incorrect 
     * parameters.
     */
    public static void prtln(int ls, Object... args) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (args == null) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(ERR_NULL);
            }
            throw new IllegalArgumentException(ERR_NULL);
        }
        if (args.length > 0) {
            for (Object arg : args) {
                if (arg == null) {
                    if (LOGGER.isErrorEnabled()) {
                        LOGGER.error(ERR_NULL);
                    }
                    throw new IllegalArgumentException(ERR_SOME_NULL);
                }
            }
        }
        
        for (Object arg : args) {
            sb.append(arg.toString());
        }
        if (ls > 0) {
            for (int i = 0; i < ls; i++) {
                sb.append(LS);
            }
        }

        print(sb);
    }

    /**
     * Prints the contents of the sb argument to standard output. It will try to
     * use the Console object first. If it cannot use Console, it will use the 
     * standard output channel.
     *
     * @param sb StringBuilder object containing the text to print.
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
     * Retrieves a string of the user's input from standard input until the user 
     * presses ENTER. It will first try to use the Console object, if it cannot 
     * use that, it will use the standard input System.in.
     *
     * @return a string containing what the user has entered until the ENTER 
     * key is pressed.
     * @throws java.lang.Exception In case any error occurs.
     */
    public static String read() throws Exception {
        String data;

        Console con = System.console();
        if (con == null) {
            try (Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in))) {
                data = sc.nextLine();
            }
        } else {
            data = con.readLine();
        }
        return data;
    }

    /**
     * Generates a string containing a centered message (msg) as a title between 
     * two lines of the length indicated by 'length' composed of the character 
     * 'character'.
     *
     * @param msg the message of the title
     * @param character the character that makes up each line
     * @param length the length of the line (must be greater than the length of 
     * the message)
     * @return a string with the message centered as a title between two lines
     * @throws IllegalArgumentException in case of passing null or incorrect 
     * parameters.
     */
    public static String title(String msg, Character character, int length) {
        StringBuilder sb = new StringBuilder();
        if (Types.isNullOrEmpty(msg) || Types.isNullOrEmpty(character)) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(ERR_PARAM);
            }
            throw new IllegalArgumentException(ERR_PARAM);
        } else {
            int longMsg = msg.length();
            if (length > longMsg) {
                sb.append(getLS())
                        .append(line(character, length))
                        .append(getLS());
                int padding = (length - longMsg) / 2;
                sb.append(repeatCharacter(' ', padding))
                        .append(msg)
                        .append(repeatCharacter(' ', padding));
                sb.append(getLS()).
                        append(line(character, length));
            } else {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(ERR_LONG);
                }
                throw new IllegalArgumentException(ERR_LONG);
            }
        }
        return sb.toString();
    }

    /**
     * Generates a line composed of the character 'car' that will be repeated as 
     * many times as 'length' indicates
     *
     * @param character character to repeat
     * @param length number of times to repeat the character 'character'
     * @return String with the line composed of the characters 'car' repeated as 
     * many times as indicated by the 'length' parameter.
     * @throws IllegalArgumentException in case of passing null or incorrect 
     * parameters.
     */
    public static String line(Character character, int length) {
        String line;
        if (character != null && length > 0) {
            line = repeatCharacter(character, length);
        } else {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(ERR_PARAM);
            }
            throw new IllegalArgumentException(ERR_PARAM);
        }
        return line;
    }

    /**
     * Generates a string consisting of the character <code>character</code> as 
     * many times as indicated by <code>times</code>.
     *
     * @param character the character with which the string will be composed
     * @param times the number of times the character is repeated in the string
     * @return a string with the character <code>character</code> repeated as 
     * many times as indicated by <code>times</code>
     * @throws IllegalArgumentException in case of passing null or incorrect 
     * parameters.
     */
    public static String repeatCharacter(Character character, int times) {
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        if (character != null && times > 0) {
            for (int i = 0; i < times; i++) {
                sb.append(character);
            }
        } else {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(ERR_PARAM);
            }
            throw new IllegalArgumentException(ERR_PARAM);
        }
        return sb.toString();
    }

    //Getters y Setters
    /**
     * Returns LS, a constant containing the line feed control character of the 
     * current operating system.
     *
     * @return a constant chain
     */
    public static String getLS() {
        return LS;
    }

    /**
     * Returns FS, a constant containing the path separator character of the 
     * current operating system.
     *
     * @return a constant chain
     */
    public static String getFS() {
        return FS;
    }

    /**
     * Returns CHAR_INI, a constant containing the character that indicates the 
     * beginning of the contents of an object in its textual representation when 
     * passed through a converter function.
     *
     * @return a constant chain
     */
    public static String getCHAR_INI() {
        return CHAR_INI;
    }

    /**
     * Returns CHAR_END, a constant containing the character that indicates the 
     * end of the contents of an object in its textual representation when 
     * passed through a converter function.
     *
     * @return a constant chain
     */
    public static String getCHAR_END() {
        return CHAR_END;
    }

    /**
     * Returns SEP, the item separator of values of an collection type object when 
     * passed through a converter function.
     *
     * @return a constant chain
     */
    public static String getSEP() {
        return SEP;
    }

    /**
     * Returns NULL, a constant representing the null value when an object is 
     * converted into its textual representation.
     *
     * @return a constant chain
     */
    public static String getNULL() {
        return NULL;
    }
}
