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
  private TestUtils() {
  }
  
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
  
  public static void printTitletoLogAndConsole(String methodName, Logger logger) {
    String test = "TEST " + (methodName != null ? methodName : "(not provided)");
    if (logger != null) {
      logger.debug(test);
    }
    System.out.println(test);
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
    System.out.println(actVal);
    System.out.println(expVal);
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
