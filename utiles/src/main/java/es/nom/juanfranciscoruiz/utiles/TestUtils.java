package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.impl.IOimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for test-related operations
 *
 * @author Juan F. Ruiz
 */
public class TestUtils {
    /**
     * Logger instance used for logging messages and debugging information in the
     * TestUtils class. This logger is configured to record log messages associated
     * with the TestUtils utility class.
     */
    public final static Logger logger = LoggerFactory.getLogger(TestUtils.class);
    private static final TestUtils INSTANCE = new TestUtils();

    /**
     * Private constructor for the TestUtils class.
     * This constructor is intentionally defined as private to prevent instantiation of the utility class.
     * The TestUtils class is designed to provide static utility methods and does not require
     * object instantiation.
     */
    private TestUtils() {
    }

    /**
     * Provides access to the singleton instance of the TestUtils class.
     * This method ensures that only one instance of TestUtils is created
     * and shared across the application, as TestUtils is designed to be a utility class.
     *
     * @return the singleton instance of the TestUtils class
     */
    public static TestUtils getInstance() {
        return INSTANCE;
    }

    /**
     * Logs or prints a formatted title based on the provided method name.
     * If a logger is provided, the title is logged using the logger. Otherwise, it is printed to the console.
     *
     * @param methodName the name of the method whose title is to be logged or printed
     * @param logger     the logger used to log the title; if null, the title is printed to the console
     */
    public static void printTitle(String methodName, Logger logger) {
        String test = "TEST " + methodName;
        if (logger != null) {
            logger.debug(test);
        } else {
            IOimpl.prtln(1, test);
        }
    }

    /**
     * Logs or prints a formatted title based on the provided method name.
     * If a logger is provided, the title is logged using the logger. Otherwise, it is printed to the console.
     *
     * @param methodName the name of the method whose title is to be logged or printed
     * @param logger     the logger used to log the title; if null, the title is printed to the console
     */
    public static void printTitletoLogAndConsole(String methodName, Logger logger) {
        String test = "TEST " + (methodName != null ? methodName : "(not provided)");
        printMsgToLogAndConsole(test, logger);
    }

    /**
     * Logs or prints the expected and actual values. Uses the provided logger if available;
     * otherwise, outputs the results to the console.
     *
     * @param expectedValue the expected value to be logged or printed
     * @param actualValue   the actual value to be logged or printed
     * @param logger        the logger used to log the values; if null, the values are printed to the console
     */
    public static void printResults(Object expectedValue, Object actualValue, Logger logger) {
        String actVal = "Return value -> " + actualValue;
        String expVal = "Expected value -> " + expectedValue;
        if (logger != null) {
            logger.debug(actVal);
            logger.debug(expVal);
        } else {
            IOimpl.prtln(1, actVal);
            IOimpl.prtln(1, expVal);
        }
    }

    /**
     * Logs and prints expected and actual values to both the console and a logger (if provided).
     *
     * @param expectedValue the expected value to be logged and printed
     * @param actualValue   the actual value to be logged and printed
     * @param logger        the logger used to log the values; if null, the values are only printed to the console
     */
    public static void printResultsToLogAndConsole(Object expectedValue, Object actualValue,
                                                   Logger logger) {
        String actVal = "Return value -> " + actualValue;
        String expVal = "Expected value -> " + expectedValue;
        if (logger != null) {
            logger.debug(actVal);
            logger.debug(expVal);
        }
        IOimpl.prtln(1, actVal);
        IOimpl.prtln(1, expVal);
    }

    /**
     * Logs and prints a message to both the console and a logger (if provided).
     *
     * @param msg    the message to be logged and printed
     * @param logger the logger used to log the message; if null, the message is only printed to the console
     */
    public static void printMsg(String msg, Logger logger) {
        if (logger != null) {
            logger.debug(msg);
        }
    }

    /**
     * Logs and prints a message to both the console and a logger, if a logger is provided.
     *
     * @param msg    the message to be logged and printed
     * @param logger the logger used to log the message; if null, the message is only printed to the console
     */
    public static void printMsgToLogAndConsole(String msg, Logger logger) {
        if (logger != null) {
            logger.debug(msg);
        }
        IOimpl.prtln(1, msg);
    }

}
