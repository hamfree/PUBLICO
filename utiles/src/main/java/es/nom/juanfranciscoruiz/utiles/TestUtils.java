package es.nom.juanfranciscoruiz.utiles;

import org.slf4j.Logger;

/**
 * Utility class for test-related operations
 *
 * @author Juan F. Ruiz
 */
public class TestUtils {

    /**
     * We prevent it from being instantiated (Utility class)
     */
    private TestUtils(){ }
    /**
     * Prints title to output or to log
     */
    public static void printTitle(String methodName, Logger logger) {
        String test = "TEST " + methodName;
        if (logger != null) {
            logger.debug(test);
        } else {
            System.out.println(test);
        }
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
            System.out.println(actVal);
            System.out.println(expVal);
        }
    }
}
