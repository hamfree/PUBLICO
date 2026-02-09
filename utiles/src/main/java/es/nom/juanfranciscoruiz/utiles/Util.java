package es.nom.juanfranciscoruiz.utiles;

import org.slf4j.Logger;

import java.io.BufferedInputStream;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.*;

import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.prtln;

/**
 * A utility class containing various static methods for system information retrieval,
 * collection processing, logging, and program flow control. This class is designed to
 * prevent instantiation.
 *
 * Fields:
 * - `FOREVER`: A constant used to specify an indefinite pause.
 *
 * Methods:
 * - `getFeaturesAsMap()`: Returns system resource details including CPU cores,
 * free memory, maximum usable memory, and total memory.
 * - `getSystemPropertiesAsMap()`: Returns a map of system properties.
 * - `getAllCharsets()`: Retrieves all charsets supported by the current JVM.
 * - `CollectionToString(Object obj, boolean showValues, int maxElements)`: Processes and returns
 * string representations of lists or maps in a specified format or detail level.
 * - `pause(long milliseconds, String msg)`: Pauses program execution with a custom message or default prompt.
 * - `pauseWithoutMessage(long milliseconds)`: Pauses program execution without displaying any message.
 * - `warn(Logger logger, String msg)`: Logs a sanitized warning message using the specified logger.
 * - `info(Logger logger, String msg)`: Logs a sanitized informational message using the specified logger.
 * - `error(Logger logger, String msg)`: Logs a sanitized error message using the specified logger.
 * - `dbg(Logger logger, String msg)`: Logs a sanitized debug-level message using the specified logger.
 * - `dbg(Logger logger, String msg, Object... params)`: Logs a sanitized formatted debug-level message with parameters.
 */
public class Util {
    /**
     * A constant representing an indefinite duration of time. This value can be
     * used in methods or logic that require an infinite or undefined wait period.
     * It is often employed as a parameter for methods to indicate that the
     * operation should wait indefinitely or until explicitly interrupted by the
     * user or another event.
     */
    public static final Long FOREVER = 0L;
    /**
     * We prevent it from being instantiated (Utility class)
     */
    private Util() {}

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

        hm.put("Processor Cores", String.valueOf(rt.availableProcessors()));
        hm.put("Free Memory", freeMemory);
        hm.put("Available Memory", maxMemory);
        hm.put("Total Memory", totalMemmory);
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
            sb.append("null");
        } else {
            Class<?> clazz = obj.getClass();
            if (clazz.isAssignableFrom(ArrayList.class)) {
                List<?> l = (List<?>) obj;
                if (showValues) {
                    sb.append("[");
                    if (l.size() < maxElements) {
                        for (Object o : l.toArray()) {
                            sb.append("'").append(o).append("'").append(" ");
                        }
                    } else {
                        for (int i = 0; i < maxElements; i++) {
                            sb.append("'").append(l.get(i)).append("'").append(" ");
                        }
                        sb.append(" ...");
                    }
                    sb.append("]");
                } else {
                    sb.append(obj.getClass().getCanonicalName())
                            .append(" ")
                            .append(l.size())
                            .append(" items.");
                }

            } else if (clazz.isAssignableFrom(java.util.HashMap.class)) {
                Map<?, ?> m = (Map<?, ?>) obj;
                if (showValues) {
                    sb.append("[");
                    if (m.size() < maxElements) {
                        for (Map.Entry<?, ?> entry : m.entrySet()) {
                            sb.append("{'").append(entry.getKey()).append("'->'").append(entry.getValue()).append("'}").append(" ");
                        }
                    } else {
                        int i = 0;
                        for (Iterator<?> it = m.entrySet().iterator(); it.hasNext();) {
                            if (i < maxElements) {
                                Map.Entry<?, ?> e = (Map.Entry<?, ?>) it.next();
                                sb.append("{'").append(e.getKey()).append("'->'").append(e.getValue()).append("'}").append(" ");
                                i++;
                            } else {
                                sb.append(" ...");
                                break;
                            }
                        }
                    }
                    sb.append("]");
                } else {
                    sb.append(obj.getClass().getCanonicalName())
                            .append(" ")
                            .append(m.size())
                            .append(" items.");
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
            msg = "\nPress <ENTER> to continue...";
        }
        if (milliseconds == FOREVER) {
            prtln(2,msg);
            Scanner sc = new Scanner(new BufferedInputStream(System.in));
            sc.nextLine();
            return;
        }
        prtln(1,"The program will continue in " + milliseconds + " milliseconds...");
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
        if (logger != null && logger.isDebugEnabled()) {
            if (msg != null && !msg.isEmpty()) {
                msg = msg.trim();
                msg = msg.replaceAll("\n", " ");
                msg = msg.replaceAll("\r", " ");
                msg = msg.replaceAll("\t", " ");
                msg = msg.replaceAll("  +", " ");
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
        if (logger != null && logger.isDebugEnabled()) {
            if (msg != null && !msg.isEmpty()) {
                msg = msg.trim();
                msg = msg.replaceAll("\n", " ");
                msg = msg.replaceAll("\r", " ");
                msg = msg.replaceAll("\t", " ");
                msg = msg.replaceAll("  +", " ");
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
        if (logger != null && logger.isDebugEnabled()) {
            if (msg != null && !msg.isEmpty()) {
                msg = msg.trim();
                msg = msg.replaceAll("\n", " ");
                msg = msg.replaceAll("\r", " ");
                msg = msg.replaceAll("\t", " ");
                msg = msg.replaceAll("  +", " ");
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
                msg = msg.trim();
                msg = msg.replaceAll("\n", " ");
                msg = msg.replaceAll("\r", " ");
                msg = msg.replaceAll("\t", " ");
                msg = msg.replaceAll("  +", " ");
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
        if (logger != null) {
            logger.debug(msg, params);
        } else {
            System.out.println(String.format(msg, params));
        }
    }
}