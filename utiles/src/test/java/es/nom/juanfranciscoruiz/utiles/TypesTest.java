package es.nom.juanfranciscoruiz.utiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the Types utility class.
 *
 * @author juanf
 */
public class TypesTest {

    /**
     * Logger for this class
     */
    public final static Logger logger = LoggerFactory.getLogger(TypesTest.class);

    /**
     * Constructor for TypesTest.
     */
    public TypesTest() {
    }

    /**
     * Test method for {@link es.nom.juanfranciscoruiz.utiles.Types#isNullOrEmpty(java.lang.Object)}.
     */
    @Test
    public void testIsNullOrEmpty() {
        printTitle("testIsNullOrEmpty()");

        assertAll(
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("List with two elements. Must return false");
                    List<String> stringList = new ArrayList<>();
                    stringList.add("one");
                    stringList.add("two");
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(stringList);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("Map without elements. Must return true");
                    Map<String, String> theMap = new HashMap<>();
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(theMap);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("A null value. Must return true");
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(null);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("An instance of Object that points to null. Must evaluate to true");
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(null);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("An instance of Object. Must return false");
                    Object theObject = new Object();
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(theObject);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("An array with 5 null value objects. Must return false");
                    Object[] objectsArray = new Object[5];
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(objectsArray);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("An array with no elements. Must return true");
                    Object[] objectsArray = new Object[0];
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(objectsArray);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("An array with two Strings, one of them points to null. It must return false");
                    String[] stringsArray = {"un valor", null};
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(stringsArray);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("A String with a value assigned. Must return false");
                    String theString = "algun valor";
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(theString);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("An empty array of objects. Must return true");
                    Object[] objectsArray = {};
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(objectsArray);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("A primitive type value. Must return false");
                    byte theByte = 0x55;
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(theByte);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                }
        );
    }


    /**
     * Test method for {@link es.nom.juanfranciscoruiz.utiles.Types#isInteger(java.lang.Object)}.
     */
    @Test
    public void testIsInteger() {
        printTitle("testIsInteger()");
        assertAll(
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("An object that points to null. Must return false");
                    boolean expectedValue = false;
                    boolean actualValue = Types.isInteger(null);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("An instance of Object. Must return false");
                    Object obj = new Object();
                    boolean expectedValue = false;
                    boolean actualValue = Types.isInteger(obj);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("A String instance with content convertible to Integer. Must evaluate to true");
                    String obj = "45566";
                    boolean expectedValue = true;
                    boolean actualValue = Types.isInteger(obj);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                },
                () -> {
                    if (logger.isDebugEnabled()) logger.debug("A String instance with content NOT convertible to Integer. Must return false");
                    String obj = "is not an integer";
                    boolean expectedValue = false;
                    boolean actualValue = Types.isInteger(obj);
                    printResults(expectedValue, actualValue);
                    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
                }
        );
    }

    /**
     * Test method for {@link es.nom.juanfranciscoruiz.utiles.Types#isLong(java.lang.Object)}.
     */
    @Test
    public void testIsLong() {
    }

    /**
     * Test method for {@link es.nom.juanfranciscoruiz.utiles.Types#isFloat(java.lang.Object)}.
     */
    @Test
    public void testIsFloat() {
    }

    /**
     * Test method for {@link es.nom.juanfranciscoruiz.utiles.Types#isDouble(java.lang.Object)}.
     */
    @Test
    public void testIsDouble() {
    }

    /**
     * Test method for {@link es.nom.juanfranciscoruiz.utiles.Types#isArray(java.lang.Object)}.
     */
    @Test
    public void testIsArray() {
    }

    /**
     * Test method for {@link es.nom.juanfranciscoruiz.utiles.Types#isFromType(java.lang.Object, java.lang.Class)}.
     */
    @Test
    public void testIsFromType() {
    }

    // Utility methods

    /**
     * Logs a debug message including the provided method name.
     *
     * @param nombreMetodo the name of the method to include in the debug message
     */
    private void printTitle(String nombreMetodo) {
        String test = "TEST " + nombreMetodo;
        if (logger.isDebugEnabled()) logger.debug(test);
    }

    /**
     * Logs the actual and expected values for debugging purposes.
     *
     * @param expectedValue the expected value to be compared
     * @param actualValue   the actual value obtained
     */
    private void printResults(Object expectedValue, Object actualValue) {
        String actVal = "Return value -> " + actualValue;
        String expVal = "Expected value -> " + expectedValue;
        if (logger.isDebugEnabled()) logger.debug(actVal);
        if (logger.isDebugEnabled()) logger.debug(expVal);
    }
}
