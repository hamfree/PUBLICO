package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.impl.IOimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static es.nom.juanfranciscoruiz.utiles.Util.dbg;

/**
 * Utility class for test-related operations
 *
 * @author Juan F. Ruiz
 */
public class TestUtils {

    public final static Logger logger = LoggerFactory.getLogger(TestUtils.class);


    /**
     * Prints title to output or to log
     */
    public static void printTitle(String methodName, Logger logger) {
        String test = "TEST " + methodName;
        if (logger != null) {
            logger.debug(test);
        } else {
            IOimpl.prtln(1, test);
        }
    }

    public static void printTitletoLogAndConsole(String methodName, Logger logger) {
        String test = "TEST " + (methodName != null ? methodName : "(not provided)");
        if (logger != null) {
            logger.debug(test);
        }
        IOimpl.prtln(1, test);
    }

    /**
     * Prints actual and expected values to output or to log
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

    public static void printMsg(String msg, Logger logger) {
        if (logger != null) {
            logger.debug(msg);
        }
    }

    public static void printMsgToLogAndConsole(String msg, Logger logger){
        if (logger != null) {
            logger.debug(msg);
        }
        IOimpl.prtln(1, msg);
    }

    }
