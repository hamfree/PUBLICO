package es.nom.juanfranciscoruiz.utiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypesTest {

    public final static Logger logger = LoggerFactory.getLogger(TypesTest.class);
    
    public TypesTest() {
    }

    @Test
    public void testIsNullOrEmpty() {
        printTitle("testIsNullOrEmpty()");

        assertAll(
                () -> {
                    logger.debug("List with two elements. Must return false");
                    List<String> stringList = new ArrayList<>();
                    stringList.add("one");
                    stringList.add("two");

                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(stringList);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Map without elements. Must return true");
                    Map<String, String> theMap = new HashMap<>();

                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(theMap);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("A null value. Must return true");
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(null);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("An instance of Object that points to null. Must evaluate to true");
                    Object theObject=  null;
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(theObject);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("An instance of Object. Must return false");
                    Object theObject = new Object();
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(theObject);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("An array with 5 null value objects. Must return false");
                    Object[] objectsArray = new Object[5];
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(objectsArray);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("An array with no elements. Must return true");
                    Object[] objectsArray = new Object[0];
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(objectsArray);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("An array with two Strings, one of them points to null. It must return false");
                    String[] stringsArray = {"un valor", null};
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(stringsArray);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("A String with a value assigned. Must return false");
                    String theString = "algun valor";
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(theString);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("An empty array of objects. Must return true");
                    Object[] objectsArray = {};
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(objectsArray);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("A primitive type value. Must return false");
                    byte theByte = 0x55;
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(theByte);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                }
        );

    }



    @Test
    public void testIsInteger() {
        printTitle("testIsInteger()");

        assertAll(
                () -> {
                    System.out.println("An object that points to null. Must return false");
                    Object obj = null;

                    boolean expectedValue = false;
                    boolean actualValue = Types.isInteger(obj);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    System.out.println("An instance of Object. Must return false");
                    Object obj = new Object();

                    boolean expectedValue = false;
                    boolean actualValue = Types.isInteger(obj);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    System.out.println("A String instance with content convertible to Integer. Must evaluate to true");
                    String obj = "45566";

                    boolean expectedValue = true;
                    boolean actualValue = Types.isInteger(obj);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    System.out.println("A String instance with content NOT convertible to Integer. Must return false");
                    String obj = "is not an integer";

                    boolean expectedValue = false;
                    boolean actualValue = Types.isInteger(obj);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
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

    // Utility methods
    private void printTitle(String nombreMetodo) {
        String test = "TEST " + nombreMetodo;
        logger.debug(test);
    }

    private void printResults(Object expectedValue, Object actualValue) {
        String actVal = "Return value -> " + String.valueOf(actualValue);
        String expVal = "Expected value -> " + String.valueOf(expectedValue);
        logger.debug(actVal);
        logger.debug(expVal);
    }
}
