package es.nom.juanfranciscoruiz.utiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.*;

import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.prtln;

/**
 * A utility class containing various static methods for system information retrieval,
 * collection processing, logging, and program flow control. This class is designed to
 * prevent instantiation.
 * <p>
 * Fields:
 * - `FOREVER`: A constant used to specify an indefinite pause.
 * <p>
 * Methods:
 * <ul>
 * <li>`getFeaturesAsMap()`: Returns system resource details including CPU cores,
 * free memory, maximum usable memory, and total memory.</li>
 * <li>`getSystemPropertiesAsMap()`: Returns a map of system properties.</li>
 * <li>`getAllCharsets()`: Retrieves all charsets supported by the current JVM.</li>
 * <li>`CollectionToString(Object obj, boolean showValues, int maxElements)`: Processes and returns
 * string representations of lists or maps in a specified format or detail level.</li>
 * <li>`pause(long milliseconds, String msg)`: Pauses program execution with a custom message or default prompt.</li>
 * <li>`pauseWithoutMessage(long milliseconds)`: Pauses program execution without displaying any message.</li>
 * <li>`warn(Logger logger, String msg)`: Logs a sanitized warning message using the specified logger.</li>
 * <li>`info(Logger logger, String msg)`: Logs a sanitized informational message using the specified logger.</li>
 * <li>`error(Logger logger, String msg)`: Logs a sanitized error message using the specified logger.</li>
 * <li>`dbg(Logger logger, String msg)`: Logs a sanitized debug-level message using the specified logger.</li>
 * <li>`dbg(Logger logger, String msg, Object... params)`: Logs a sanitized formatted debug-level message with parameters.</li>
 * </ul>
 */
public class Stuff {
    /**
     * A static, final logger instance used for logging messages throughout
     * the utility class. It is initialized with the logging configuration
     * for the {@code Util} class.
     */
    public static final Logger logger = LoggerFactory.getLogger(Stuff.class);
    /**
     * A constant representing an indefinite duration of time. This value can be
     * used in methods or logic that require an infinite or undefined wait period.
     * It is often employed as a parameter for methods to indicate that the
     * operation should wait indefinitely or until explicitly interrupted by the
     * user or another event.
     */
    public static final Long FOREVER = 0L;
    /**
     * Represents the system's line separator.
     * <p>
     * This constant retrieves the platform-specific line separator using
     * {@link System#lineSeparator()}. It ensures consistent newline behavior
     * across different operating systems.
     */
    public static final String SL = System.lineSeparator();

    /** Left square bracket used for formatting collections. */
    public static final String OPEN_BRACKET = "[";

    /** Single quote used to wrap elements in string representations. */
    public static final String SINGLE_QUOTE = "'";

    /** A single space string used for separation. */
    public static final String SPACE = " ";

    /** Ellipsis prefixed with a space, indicating truncated output. */
    public static final String ELLIPSIS = " ...";

    /** Right square bracket used for formatting collections. */
    public static final String CLOSE_BRACKET = "]";

    /** Opening curly brace and a single quote, used for map entry formatting. */
    public static final String OPEN_MAP_ENTRY = "{'";

    /** Arrow symbol surrounded by single quotes, used to separate map keys and values. */
    public static final String MAP_ARROW = "'->'";

    /** Single quote followed by a closing curly brace, used for map entry formatting. */
    public static final String CLOSE_MAP_ENTRY = "'}";
    
    /**
     * A static instance of {@link ResourceBundle} used to fetch internationalized messages
     * from a properties file. This allows the application to support multiple languages
     * and serves as a centralized resource management utility for localization.
     * <p>
     * The ResourceBundle is typically initialized with a specific base name for the
     * properties file containing localized strings and is used across the
     * application to resolve message keys to their localized values.
     */
    private static ResourceBundle messages;

    static {
        try {
            messages = ResourceBundle.getBundle("messages");
        } catch (MissingResourceException e) {
            logger.warn("ResourceBundle 'messages' not found. Falling back to default messages.");
            messages = null;
        }
    }

    /**
     * Helper method to fetch internationalized messages, falling back to default if not found.
     *
     * @param key            The ResourceBundle key
     * @param defaultMessage The default message if key or bundle is not available
     * @return The resolved message
     */
    private static String getMessage(String key, String defaultMessage) {
        if (messages != null && messages.containsKey(key)) {
            return messages.getString(key);
        }
        return defaultMessage;
    }
    /**
     * We prevent it from being instantiated (Utility class)
     */
    private Stuff() {}

    /**
     * Returns in a map the existing CPUs on the PC, the free memory available
     * to the virtual machine (VM), the maximum amount of memory the VM will
     * attempt to use, and the total amount of memory currently available for
     * current and future objects, measured in bytes.
     *
     * @return a map with four data pairs:
     *
     * <ol>
     * <li> Key: "Processor Cores". Value: The number of CPU cores available to
     * the VM.</li>
     * <li> Key: "Free Memory". Value: the free memory available for the VM</li>
     * <li> Key: "Available Memory" Value: The maximum amount of memory that the
     * VM will attempt to use</li>
     * <li> Key: "Total Memory" Value: The total amount of memory currently
     * available in the VM for current and future objects.</li>
     * </ol>
     */
    public static Map<String, String> getFeaturesAsMap() {
        HashMap<String, String> hm = new HashMap<>();

        Runtime rt = Runtime.getRuntime();
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault());

        System.gc();
        String freeMemory = numberFormatter.format(rt.freeMemory());
        String maxMemory = numberFormatter.format(rt.maxMemory());
        String totalMemmory = numberFormatter.format(rt.totalMemory());

        hm.put(getMessage("stuff.cores", "Processor Cores"), String.valueOf(rt.availableProcessors()));
        hm.put(getMessage("stuff.memory.free", "Free Memory"), freeMemory);
        hm.put(getMessage("stuff.available","Available Memory") , maxMemory);
        hm.put(getMessage("stuff.memory.total","Total Memory"), totalMemmory);
        return hm;
    }

    /**
     * Returns the system properties in a map
     *
     * @return a map with the properties of the system.
     */
    public static Map<String, String> getSystemPropertiesAsMap() {
        HashMap<String, String> hm = new HashMap<>();

        Properties p = System.getProperties();

        p.forEach((key, value) -> hm.put(String.valueOf(key), String.valueOf(value)));
        return hm;
    }

    /**
     * Gets all charsets supported by the current JVM.
     *
     * @return una lista con todos los charsets soportados por la MVJ actual.
     */
    public static List<Charset> getAllCharsets() {
        SortedMap<String, Charset> sm;
        ArrayList<Charset> al;
        sm = Charset.availableCharsets();
        Iterator<Charset> it = sm.values().iterator();
        al = new ArrayList<>();
        while (it.hasNext()) {
            Charset ch = it.next();
            al.add(ch);
        }
        return al;
    }

    /**
     * Processes lists and maps to improve the output of the application
     * objects' 'toString()' method.
     *
     * @param obj the map or list whose elements you want to display or indicate
     * its type and size.
     * @param showValues boolean, if true, a string with the elements of the 
     * list or map will be returned. If false, a string with the type of object 
     * and the number of elements it contains will be returned.
     * @param maxElements If values are to be returned, sets the maximum number
     * of items whose textual representation will be returned in the string.
     * @return a string with the textual representation of the elements 
     * contained in the list or map or the type of the object and the number of 
     * elements it contains. If the object passed is neither a map nor a list 
     * the string returned is generated by the static method String.valueOf(). 
     * If the object is null it returns the string "null".
     */
    public static String CollectionToString(Object obj, boolean showValues, int maxElements) {

        StringBuilder sb = new StringBuilder();

        if (obj == null) {
            sb.append(getMessage("stuff.null", "null"));
        } else {
            Class<?> clazz = obj.getClass();
            if (clazz.isAssignableFrom(ArrayList.class)) {
                List<?> l = (List<?>) obj;
                if (showValues) {
                    sb.append(OPEN_BRACKET);
                    if (l.size() < maxElements) {
                        for (Object o : l.toArray()) {
                            sb.append(SINGLE_QUOTE).append(o).append(SINGLE_QUOTE).append(SPACE);
                        }
                    } else {
                        for (int i = 0; i < maxElements; i++) {
                            sb.append(SINGLE_QUOTE).append(l.get(i)).append(SINGLE_QUOTE).append(SPACE);
                        }
                        sb.append(ELLIPSIS);
                    }
                    sb.append(CLOSE_BRACKET);
                } else {
                    sb.append(obj.getClass().getCanonicalName())
                            .append(" ")
                            .append(l.size())
                            .append(getMessage("stuff.items", " items."));
                }

            } else if (clazz.isAssignableFrom(java.util.HashMap.class)) {
                Map<?, ?> m = (Map<?, ?>) obj;
                if (showValues) {
                    sb.append(OPEN_BRACKET);
                    if (m.size() < maxElements) {
                        for (Map.Entry<?, ?> entry : m.entrySet()) {
                            sb.append(OPEN_MAP_ENTRY).append(entry.getKey()).append(MAP_ARROW).append(entry.getValue()).append(CLOSE_MAP_ENTRY).append(SPACE);
                        }
                    } else {
                        int i = 0;
                        for (Iterator<?> it = m.entrySet().iterator(); it.hasNext();) {
                            if (i < maxElements) {
                                Map.Entry<?, ?> e = (Map.Entry<?, ?>) it.next();
                                sb.append(OPEN_MAP_ENTRY).append(e.getKey()).append(MAP_ARROW).append(e.getValue()).append(CLOSE_MAP_ENTRY).append(SPACE);
                                i++;
                            } else {
                                sb.append(ELLIPSIS);
                                break;
                            }
                        }
                    }
                    sb.append(CLOSE_BRACKET);
                } else {
                    sb.append(obj.getClass().getCanonicalName())
                            .append(" ")
                            .append(m.size())
                            .append(getMessage("stuff.items", " items."));
                }
            } else {
                sb.append(obj);
            }
        }
        return sb.toString();
    }
    
    /**
     * Pauses the program for a specified number of milliseconds and optionally
     * displays a message. If no message is provided, it defaults to
     * "Press &lt;ENTER&gt; to continue..." on the terminal.
     * <p>
     * If 0 milliseconds are specified, the function waits for the user to
     * press ENTER. Otherwise, it informs the user that the program will
     * continue after the specified delay and then pauses.
     *
     * @param milliseconds The duration of the pause in milliseconds. If 0,
     * the function waits for the user to press ENTER.
     * @param msg The message to display to the user.
     * @throws Exception In case of any error.
     */
    public static void pause(long milliseconds, String msg) throws Exception {
        if (msg == null || msg.isEmpty()) {
            msg = getMessage("msg.util.pause.default", "\nPress <ENTER> to continue...");
        }
        if (milliseconds == FOREVER) {
            prtln(2,msg);
            Scanner sc = new Scanner(new BufferedInputStream(System.in));
            sc.nextLine();
            return;
        }
        String continueMsg = getMessage("msg.util.pause.continue", "The program will continue in %d milliseconds...");
        prtln(1, String.format(continueMsg, milliseconds));
        Thread.sleep(milliseconds);
    }
    
    /**
     * Pauses the program for a specified number of milliseconds. If FOREVER is
     * specified, it waits for the user to press ENTER. No message is
     * displayed.
     *
     * @param milliseconds The duration of the pause in milliseconds. If FOREVER,
     * the function waits for the user to press ENTER.
     * @throws Exception In case of any error.
     */
    public static void pauseWithoutMessage(long milliseconds) throws Exception {
        if (milliseconds == FOREVER) {
            Scanner sc = new Scanner(new BufferedInputStream(System.in));
            sc.nextLine();
            return;
        }
        Thread.sleep(milliseconds);
    }

    /**
     * Logs a warning message if the provided logger and message are valid. The method ensures
     * the message is properly sanitized by trimming whitespace, removing newlines, carriage
     * returns, and tabs, and condensing multiple spaces into a single space before logging.
     *
     * @param logger the logger instance to be used for logging the warning message. If null,
     *               the method will do nothing.
     * @param msg the warning message to log. If null or empty, the method will do nothing.
     */
    public static void warn(Logger logger, String msg) {
        if (logger != null && logger.isWarnEnabled()) {
            if (msg != null && !msg.isEmpty()) {
                msg = sanitize(msg);
                logger.warn(msg);
            }
        }
    }

    /**
     * Logs an informational message if the provided logger and message are valid. The method
     * ensures the message is properly sanitized by trimming whitespace, removing newlines,
     * carriage returns, and tabs, and condensing multiple spaces into a single space before logging.
     *
     * @param logger the logger instance to be used for logging the informational message. If null,
     *               the method will do nothing.
     * @param msg the informational message to log. If null or empty, the method will do nothing.
     */
    public static void info(Logger logger, String msg) {
        if (logger != null && logger.isInfoEnabled()) {
            if (msg != null && !msg.isEmpty()) {
                msg = sanitize(msg);
                logger.info(msg);
            }
        }
    }

    /**
     * Logs an error message if the provided logger and message are valid. The method ensures
     * the message is properly sanitized by trimming whitespace, removing newlines, carriage
     * returns, and tabs, and condensing multiple spaces into a single space before logging.
     *
     * @param logger the logger instance to be used for logging the error message. If null,
     *               the method will do nothing.
     * @param msg the error message to log. If null or empty, the method will do nothing.
     */
    public static void error(Logger logger, String msg){
        if (logger != null && logger.isErrorEnabled()) {
            if (msg != null && !msg.isEmpty()) {
                msg = sanitize(msg);
                logger.error(msg);
            }
        }
    }

    /**
     * Logs a debug-level message after performing basic transformations to ensure readability.
     * The method trims the input message, replaces newlines, carriage returns, tabs,
     * and multiple spaces with a single space, and then logs the processed message using
     * the provided logger if debug logging is enabled.
     *
     * @param logger the logger used to log the message; if null, logging is bypassed
     * @param msg    the message to be logged; if null or empty, the method does nothing
     */
    public static void dbg(Logger logger, String msg) {
        if (logger != null && logger.isDebugEnabled()) {
            if (msg != null && !msg.isEmpty()) {
                msg = sanitize(msg);
                logger.debug(msg);
            }
        }
    }

    /**
     * Logs a debug-level message after performing basic transformations to ensure readability.
     * The method trims the input message, replaces newlines, carriage returns, tabs,
     * and multiple spaces with a single space, and then logs the processed message using
     * the provided logger if debug logging is enabled.
     *
     * @param logger the logger used to log the message; if null, logging is bypassed
     * @param msg    the message to be logged; if null or empty, the method does nothing
     * @param params the parameters to be formatted into the message
     */
    public static void dbg(Logger logger, String msg, Object... params) {
        if (logger != null && logger.isDebugEnabled()) {
            if (msg != null && !msg.isEmpty()){
                msg = sanitize(msg);
                logger.debug(msg, params);
            }
        }
    }

    /**
     * Cleans up a given string by removing leading and trailing whitespace, replacing 
     * newlines, carriage returns, tabs with a single space, and condensing multiple 
     * consecutive spaces into a single space.
     *
     * @param msg the input string to be sanitized; if null, behavior is undefined
     * @return the sanitized version of the input string
     */
    private static String sanitize(String msg){
        msg = msg.trim();
        msg = msg.replace("\n", " ");
        msg = msg.replace("\r", " ");
        msg = msg.replace("\t", " ");
        msg = msg.replaceAll("  +", " ");
        return msg;
    }
}