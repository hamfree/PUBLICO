package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.exceptions.TypeConverterException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import es.nom.juanfranciscoruiz.utiles.impl.IOimpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static es.nom.juanfranciscoruiz.utiles.TestUtils.*;
import static es.nom.juanfranciscoruiz.utiles.TypeConverter.*;
import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests for TypeConverter class
 *
 * @author Juan F. Ruiz
 */
public class TypeConverterTest {
  
  /**
   * For debugging.
   */
  public final static Logger logger = LoggerFactory.getLogger(TypeConverterTest.class);
  
  /*
  We import constants from the IO class to make writing tests easier.
   */
  public final static String CAR_INI = IOimpl.getCHAR_INI();
  public final static String CAR_FIN = IOimpl.getCHAR_END();
  public final static String NULL = IOimpl.getNULL();
  public final static String SEP = IOimpl.getSEP();
  public final static String SL = IOimpl.getLS();


  @BeforeAll
  static void beforeAll() {
    printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Starting TypeConverterTest"  + System.lineSeparator(), logger);
  }

  @AfterAll
  static void afterAll() {
    printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Ending TypeConverterTest"  + System.lineSeparator(), logger);
  }

  /**
   * Tests collection conversion to list
   */
  @Test
  public void testCollection2List() {
    printTitletoLogAndConsole("testCollection2List()", logger);
    
    Collection<Integer> vector = new Vector<>();
    List<Integer> expectedValue = new ArrayList<>();
    List<Integer> actualValue;
    Class<Integer> clazz;
    
    vector.add(1);
    vector.add(2);
    vector.add(3);
    
    expectedValue.add(1);
    expectedValue.add(2);
    expectedValue.add(3);
    
    clazz = Integer.class;
    actualValue = collection2List(clazz, vector);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "Should convert the vector to a list");
  }
  
  /**
   * Tests vector‑to‑list and map‑to‑list conversions
   */
  @Test
  public void testMap2List() {
    printTitletoLogAndConsole("testMap2List()", logger);
    
    Map<String, Object> mapToConvert = new HashMap<>();
    List<Integer> expectedValue = new ArrayList<>();
    List<Integer> actualValue;
    Class<Integer> clazz;
    
    mapToConvert.put("1", 1);
    mapToConvert.put("2", 2);
    mapToConvert.put("3", 3);
    
    expectedValue.add(1);
    expectedValue.add(2);
    expectedValue.add(3);
    
    clazz = Integer.class;
    actualValue = map2List(clazz, mapToConvert);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The expected result is to convert the values of the map into a list with those values");
  }
  
  /**
   * Tests string‑to‑long conversion, including edge cases and exceptions
   */
  @Test
  public void testExtractLongFromString() {
    printTitletoLogAndConsole("testExtractLongFromString()", logger);
    
    assertAll(
        () -> {
          String theString = "0asfklkdkr";
          Long actualValue = extractLongFromString(theString);
          Long expectedValue = 0L;
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is %dand it should be%s".formatted(actualValue, expectedValue));
        },
        () -> {
          String theString = "-987654321012me";
          Long actualValue = extractLongFromString(theString);
          Long expectedValue = -987654321012L;
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is " + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          TypeConverterException ex = assertThrows(TypeConverterException.class, () -> {
            String theString = "ItsNotALongValue";
            extractLongFromString(theString);
          });
          if (logger.isDebugEnabled()) logger.debug("TypeConverterException : {}", ex.getMessage());
        },
        () -> {
          TypeConverterException ex = assertThrows(TypeConverterException.class, () -> {
            String theString = "9223372036854775808";
            extractLongFromString(theString);
          });
          if (logger.isDebugEnabled()) logger.debug("TypeConverterException : {}", ex.getMessage());
        }
    );
  }
  
  /**
   * Tests string‑to‑double conversion, including edge cases and exceptions
   */
  @Test
  public void testExtractDoubleFromString() {
    printTitletoLogAndConsole("testExtractDoubleFromString()", logger);
    
    //TODO: Do the test for exception and strings with negative double and no double inside
    assertAll(
        () -> {
          String theString = "asfk3.07lkdkr";
          Double actualValue = extractDoubleFromString(theString);
          Double expectedValue = 3.07;
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          String theString = "asfk-3.07lkdkr";
          Double actualValue = extractDoubleFromString(theString);
          Double expectedValue = -3.07;
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          TypeConverterException ex = assertThrows(TypeConverterException.class, () -> extractDoubleFromString(null));
          if (logger.isDebugEnabled()) logger.debug("TypeConverterException : {}", ex.getMessage());
        },
        () -> {
          TypeConverterException ex = assertThrows(TypeConverterException.class, () -> {
            String theString = "   ";
            extractDoubleFromString(theString);
          });
          if (logger.isDebugEnabled()) logger.debug("TypeConverterException : {}", ex.getMessage());
        }
    );
  }
  
  /**
   * Tests array to string conversion
   */
  @Test
  public void testArray2String() {
    printTitletoLogAndConsole("testArray2String()", logger);
    
    Integer[] integerArray = {0, 1, 2};
    String expectedValue = CAR_INI
        .concat(String.valueOf(0))
        .concat(SEP)
        .concat(String.valueOf(1))
        .concat(SEP)
        .concat(String.valueOf(2))
        .concat(CAR_FIN);
    String actualValue = array2String(integerArray);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
  }
  
  /**
   * Tests recursive array to string conversion
   */
  @Test
  public void testArra2StringRecursive() {
    printTitletoLogAndConsole("testArra2StringRecursive()", logger);
    
    Object[] objectsArray = {0, new Object[]{5, 6}, 2, new Object[]{"a", "b"}, 4};
    String expectedValue = CAR_INI
        .concat(String.valueOf(0))
        .concat(SEP)
        .concat(CAR_INI)
        .concat(String.valueOf(5))
        .concat(SEP)
        .concat(String.valueOf(6))
        .concat(CAR_FIN)
        .concat(SEP)
        .concat(String.valueOf(2))
        .concat(SEP)
        .concat(CAR_INI)
        .concat("a")
        .concat(SEP)
        .concat("b")
        .concat(CAR_FIN)
        .concat(SEP)
        .concat(String.valueOf(4))
        .concat(CAR_FIN);
    String actualValue = array2String(objectsArray);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
  }
  
  /**
   * Tests string conversion with non‑array input
   */
  @Test
  public void testArray2StringNoArrayParameter() {
    printTitletoLogAndConsole("testArray2StringNoArrayParameter()", logger);
    
    List<String> stringsList = new ArrayList<>();
    stringsList.add("a");
    stringsList.add("b");
    String expectedValue = NULL;
    String actualValue = array2String(stringsList);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
  }
  
  /**
   * Tests string conversion with null array
   */
  @Test
  public void testArray2StringNullArray() {
    printTitletoLogAndConsole("testArray2StringNullArray()", logger);
    
    String expectedValue = NULL;
    String actualValue = array2String(null);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
  }
  
  /**
   * Tests byte array to string conversion
   */
  @Test
  public void testArrayByte2String() {
    printTitletoLogAndConsole("testArrayByte2String()", logger);
    
    assertAll(
        () -> {
          byte[] bytesArray = {65, 66, 67, 68};
          
          String expectedValue = "(4 bytes), [0]=65,[1]=66,[2]=67,[3]=68";
          String actualValue = arrayByte2String(bytesArray, true, true);
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          byte[] bytesArray = {65, 66, 67, 68};
          
          String expectedValue = "[0]=65,[1]=66,[2]=67,[3]=68";
          String actualValue = arrayByte2String(bytesArray, false, true);
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          byte[] bytesArray = {65, 66, 67, 68};
          
          String expectedValue = "(4 bytes), 65,66,67,68";
          String actualValue = arrayByte2String(bytesArray, true, false);
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          byte[] bytesArray = {65, 66, 67, 68};
          
          String expectedValue = "65,66,67,68";
          String actualValue = arrayByte2String(bytesArray, false, false);
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          String expectedValue = "";
          String actualValue = arrayByte2String(null, false, false);
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        }
    );
    
  }
  
  @Test
  public void testMap2String() {
    printTitletoLogAndConsole("testMap2String()", logger);
    
    assertAll(
        () -> {
          Map<Integer, String> theMap = new TreeMap<>();
          theMap.put(1, "ONE");
          theMap.put(2, "TWO");
          theMap.put(3, "THREE");
          theMap.put(4, "FOUR");
          String expectedValue = String.valueOf(1).concat(SEP).concat("ONE").concat(SL)
              .concat(String.valueOf(2)).concat(SEP).concat("TWO").concat(SL)
              .concat(String.valueOf(3)).concat(SEP).concat("THREE").concat(SL)
              .concat(String.valueOf(4)).concat(SEP).concat("FOUR").concat(SL);
          String actualValue = map2String(theMap);
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          String expectedValue = "";
          String actualValue = map2String(null);
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        }
    );
  }
  
  @Test
  public void testByteToHex() {
    printTitletoLogAndConsole("testByteToHex()", logger);
    
    byte theByte = 127;
    String expectedValue = "7f";
    String actualValue = byteToHex(theByte);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
  }
  
  @Test
  public void testCharToHex() {
    printTitletoLogAndConsole("testCharToHex()", logger);
    
    char theCharacter = '\uffff';
    String expectedValue = "ffff";
    String actualValue = charToHex(theCharacter);
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
  }
  
  @Test
  public void testExtractDigits() {
    printTitletoLogAndConsole("testExtractDigits()", logger);
    
    assertAll(
        () -> {
          String theString = "ds[*_gjd1234ds,cf$vm";
          String expectedValue = "1234";
          String actualValue = extractDigits(theString);
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        },
        () -> {
          String expectedValue = "";
          String actualValue = extractDigits(null);
          printResultsToLogAndConsole(expectedValue, actualValue, logger);
          assertEquals(expectedValue, actualValue, "The value returned is" + actualValue + "and it should be" + expectedValue);
        }
    );
    
  }
}
