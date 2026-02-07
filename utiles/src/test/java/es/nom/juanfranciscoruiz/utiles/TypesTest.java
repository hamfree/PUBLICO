package es.nom.juanfranciscoruiz.utiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static es.nom.juanfranciscoruiz.utiles.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the Types class.
 *
 * @author Juan F. Ruiz
 */
public class TypesTest {
  
  public final static Logger logger = LoggerFactory.getLogger(TypesTest.class);
  
  public TypesTest() {
  }

  @BeforeAll
  static void beforeAll() {
    printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Starting TypesTest" + System.lineSeparator(), logger);
  }

  @AfterAll
  static void afterAll() {
    printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Ending TypesTest" + System.lineSeparator(), logger);
  }
  
  @Test
  public void testIsNullOrEmpty() {
    printTitletoLogAndConsole("testIsNullOrEmpty()", logger);
    
    assertAll(
        () -> {
          logger.debug("List with two elements. Must return false");
          List<String> stringList = new ArrayList<>();
          stringList.add("one");
          stringList.add("two");
          
          boolean expectedValue = false;
          boolean actualValue = Types.isNullOrEmpty(stringList);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          logger.debug("Map without elements. Must return true");
          Map<String, String> theMap = new HashMap<>();
          
          boolean expectedValue = true;
          boolean actualValue = Types.isNullOrEmpty(theMap);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          logger.debug("A null value. Must return true");
          boolean expectedValue = true;
          boolean actualValue = Types.isNullOrEmpty(null);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          logger.debug("An instance of Object that points to null. Must evaluate to true");
          Object theObject = null;
          boolean expectedValue = true;
          boolean actualValue = Types.isNullOrEmpty(theObject);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          logger.debug("An instance of Object. Must return false");
          Object theObject = new Object();
          boolean expectedValue = false;
          boolean actualValue = Types.isNullOrEmpty(theObject);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          logger.debug("An array with 5 null value objects. Must return false");
          Object[] objectsArray = new Object[5];
          boolean expectedValue = false;
          boolean actualValue = Types.isNullOrEmpty(objectsArray);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          logger.debug("An array with no elements. Must return true");
          Object[] objectsArray = new Object[0];
          boolean expectedValue = true;
          boolean actualValue = Types.isNullOrEmpty(objectsArray);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          logger.debug("An array with two Strings, one of them points to null. It must return false");
          String[] stringsArray = {"un valor", null};
          boolean expectedValue = false;
          boolean actualValue = Types.isNullOrEmpty(stringsArray);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          logger.debug("A String with a value assigned. Must return false");
          String theString = "algun valor";
          boolean expectedValue = false;
          boolean actualValue = Types.isNullOrEmpty(theString);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          logger.debug("An empty array of objects. Must return true");
          Object[] objectsArray = {};
          boolean expectedValue = true;
          boolean actualValue = Types.isNullOrEmpty(objectsArray);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          logger.debug("A primitive type value. Must return false");
          byte theByte = 0x55;
          boolean expectedValue = false;
          boolean actualValue = Types.isNullOrEmpty(theByte);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        }
    );
    
  }
  
  
  @Test
  public void testIsInteger() {
    printTitletoLogAndConsole("testIsInteger()", logger);
    
    assertAll(
        () -> {
          System.out.println("An object that points to null. Must return false");
          Object obj = null;
          
          boolean expectedValue = false;
          boolean actualValue = Types.isInteger(obj);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          System.out.println("An instance of Object. Must return false");
          Object obj = new Object();
          
          boolean expectedValue = false;
          boolean actualValue = Types.isInteger(obj);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          System.out.println("A String instance with content convertible to Integer. Must evaluate to true");
          String obj = "45566";
          
          boolean expectedValue = true;
          boolean actualValue = Types.isInteger(obj);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          System.out.println("A String instance with content NOT convertible to Integer. Must return false");
          String obj = "is not an integer";
          
          boolean expectedValue = false;
          boolean actualValue = Types.isInteger(obj);
          
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          
          assertEquals(expectedValue, actualValue,
              "The value returned is" + actualValue + "and it should be" + expectedValue);
        }
    );
    
  }
  
  @Test
  public void testIsLong() {
  }
  
  @Test
  public void testIsFloat() {
  }
  
  @Test
  public void testIsDouble() {
  }
  
  @Test
  public void testIsArray() {
  }
  
  @Test
  public void testIsFromType() {
  }
}
