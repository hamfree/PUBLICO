package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.impl.IOimpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static es.nom.juanfranciscoruiz.utiles.TestUtils.*;
import static es.nom.juanfranciscoruiz.utiles.Util.dbg;
import static org.junit.jupiter.api.Assertions.*;

import org.junitpioneer.jupiter.StdIn;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Test class for the IO utility class.
 * <p>
 * This class contains unit tests to verify the functionality of IO operations
 * including printing, reading, title generation, and character repetition.
 * Tests cover both normal operation and error handling scenarios.
 * </p>
 *
 * @author Juan F. Ruiz
 */
public class IOimplTest {
  public final static Logger logger = LoggerFactory.getLogger(IOimplTest.class);
  
  private static final String ERR_PARAM = "Null or incorrect parameters!";
  private static final String ERR_NULL = "Null parameters!";
  private static final String ERR_SOME_NULL = "One of the parameters is null!";
  private static final String ERR_LONG = "The line length is less than the message length";

  @BeforeAll
  static void beforeAll() {
    printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Starting IOimplTest" + System.lineSeparator(), logger);
  }

  @AfterAll
  static void afterAll() {
    printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Ending IOimplTest" + System.lineSeparator(),logger);
  }

  /**
   * Tests the prt method with multiple string arguments.
   * Verifies that arguments are concatenated without separators.
   */
  @Test
  @StdIo()
  public void testPrt(StdOut out) {
    IOimpl.prt("one", "two", "three");
    String actualValue = out.capturedString();
    String expectedValue = "onetwothree";
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The values should be the same");
  }
  
  /**
   * Tests that prt method throws IllegalArgumentException when passed a null argument.
   */
  @Test
  public void testPrtNull() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.prt((Object) null));
    assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_NULL);
  }
  
  /**
   * Tests that prt method throws IllegalArgumentException when the first varargs item is null.
   */
  @Test
  public void testPrtNullInFirstItemVarArgs() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.prt((Object) null));
    assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_SOME_NULL);
  }
  
  
  /**
   * Tests that prt method throws IllegalArgumentException when any varargs item is null.
   */
  @Test
  public void testPrtSomeNullInVarArgs() {
    Object[] args = {new Object(), null, new Object(), new Object()};
    
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.prt(args));
    assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message" + ERR_SOME_NULL);
  }
  
  /**
   * Tests the prtln method with multiple arguments and line separators.
   * Verifies that arguments are concatenated with the specified number of line separators appended.
   */
  @Test
  @StdIo()
  public void testPrtln(StdOut out) {
    //Don't print the title of the test method because breaks the test
    String sl = System.lineSeparator();
    IOimpl.prtln(2, "one", "two", "three");
    String actualValue = out.capturedString();
    String expectedValue = "onetwothree".concat(sl).concat(sl);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The values should be the same");
  }
  
  /**
   * Tests that prtln method throws IllegalArgumentException when passed a null argument.
   */
  @Test
  public void testPrtlnNull() {
    printTitletoLogAndConsole("testPrtlnNull()", logger);
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.prtln(1, (Object) null));
    assertEquals(ERR_SOME_NULL, ex.getMessage(),
        "An IllegalArgumentException should be thrown with the message" + ERR_NULL);
  }
  
  /**
   * Tests that prtln method throws IllegalArgumentException when the first varargs item is null.
   */
  @Test
  public void testPrtlnNullInFirtsArgInVarArgs() {
    printTitletoLogAndConsole("testPrtlnNullInFirtsArgInVarArgs()", logger);
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.prtln(1, (Object) null));
    assertEquals(ERR_SOME_NULL, ex.getMessage(),
        "An IllegalArgumentException should be thrown with the message " + ERR_SOME_NULL);
  }
  
  /**
   * Tests that prtln method throws IllegalArgumentException when any varargs item is null.
   */
  @Test
  public void testPrtlnSomeNullInVarArgs() {
    printTitletoLogAndConsole("testPrtlnSomeNullInVarArgs()", logger);
    Object[] args = {new Object(), null, new Object(), new Object()};
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.prtln(3, args));
    assertEquals(ERR_SOME_NULL, ex.getMessage(),
        "An IllegalArgumentException should be thrown with the message " + ERR_SOME_NULL);
  }
  
  /**
   * Tests the read method to verify it correctly reads input from standard input.
   */
  @Test
  @StdIo({"one"})
  public void testRead(StdIn in, StdOut out) {
    printTitletoLogAndConsole("testRead()", logger);
    String actualValue = IOimpl.read();
    String expectedValue = "one";
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The values should be the same");
  }
  
  /**
   * Tests the title method to verify it generates a properly formatted title
   * with the message centered and surrounded by specified characters.
   */
  @Test
  public void testTitle() {
    printTitletoLogAndConsole("testTitle()", logger);
    String expectedValue = System.lineSeparator()
        .concat("********************")
        .concat(System.lineSeparator())
        .concat("      MESSAGE      ")
        .concat(System.lineSeparator())
        .concat("********************");
    String actualValue = IOimpl.title("MESSAGE", '*', 20);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The values should be the same");
  }
  
  /**
   * Tests that title method throws IllegalArgumentException when message parameter is null.
   */
  @Test
  public void testTitleMessageNull() {
    printTitletoLogAndConsole("testTitleMessageNull()", logger);
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.title(null, '*', 20));
    assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
  }
  
  /**
   * Tests that title method throws IllegalArgumentException when character parameter is null.
   */
  @Test
  public void testTitleNullCharacter() {
    printTitletoLogAndConsole("testTitleNullCharacter()", logger);
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.title("MESSAGE", null, 20));
    assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
  }
  
  /**
   * Tests that title method throws IllegalArgumentException when line length is less than message length.
   */
  @Test
  public void testTitleLengthLessThanLengthMessage() {
    printTitletoLogAndConsole("testTitleLengthLessThanLengthMessage()", logger);
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.title("MESSAGE", '*', 3));
    assertEquals(ERR_LONG, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_LONG);
  }
  
  /**
   * Tests the line method to verify it generates a line of specified length using the given character.
   */
  @Test
  public void testLine() {
    printTitletoLogAndConsole("testLine()", logger);
    String expectedValue = "**********";
    String actualValue = IOimpl.line('*', 10);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The line() method should generate a string with 10 asterisks (*)");
  }
  
  /**
   * Tests that line method throws IllegalArgumentException when character parameter is null.
   */
  @Test
  public void testLineNullCharacter() {
    printTitletoLogAndConsole("testLineNullCharacter()", logger);
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.line(null, 10));
    assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
  }
  
  /**
   * Tests that line method throws IllegalArgumentException when length parameter is invalid (zero or negative).
   */
  @Test
  public void testLineWrongLength() {
    printTitletoLogAndConsole("testLineWrongLength()", logger);
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.line('#', 0));
    assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
  }
  
  /**
   * Tests the repeatCharacter method to verify it repeats a character the specified number of times.
   */
  @Test
  public void testRepeatCharacter() {
    printTitletoLogAndConsole("testRepeatCharacter()", logger);
    String expectedValue = "---------";
    String actualValue = IOimpl.repeatCharacter('-', 9);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The repeatCharacter() method should generate a string with 9 dashes (-)");
  }
  
  /**
   * Tests that repeatCharacter method throws IllegalArgumentException when character parameter is null.
   */
  @Test
  public void testRepeatCharacterNullCharacterParameter() {
    printTitletoLogAndConsole("testRepeatCharacterNullCharacterParameter()", logger);
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.repeatCharacter(null, 3));
    assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
  }
  
  /**
   * Tests that repeatCharacter method throws IllegalArgumentException when times parameter is negative.
   */
  @Test
  public void testRepeatCharacterWrongTimesParameter() {
    printTitletoLogAndConsole("testRepeatCharacterWrongTimesParameter()", logger);
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOimpl.repeatCharacter('a', -4));
    assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
  }
}
