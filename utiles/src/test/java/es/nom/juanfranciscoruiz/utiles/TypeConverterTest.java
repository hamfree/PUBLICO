package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.exception.TypeConverterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeConverterTest {

    public final static Logger logger = LoggerFactory.getLogger(TypeConverterTest.class);

    /*
    We import constants from the IO class to make writing tests easier.
     */
    public final static String CAR_INI = IO.getCHAR_INI();
    public final static String CAR_FIN = IO.getCHAR_END();
    public final static String NULL = IO.getNULL();
    public final static String SEP = IO.getSEP();
    public final static String SF = IO.getFS();
    public final static String SL = IO.getLS();
    public final static String MESSAGE_EX1 = "";

    @Test
    public void testCollection2List() {
        printTitle("testCollection2List()");

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

        actualValue = TypeConverter.collection2List(clazz, vector);

        printResults(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "Should convert the vector to a list");

    }

    @Test
    public void testMap2List() {
        printTitle("testMap2List()");

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

        actualValue = TypeConverter.map2List(clazz, mapToConvert);

        printResults(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "The expected result is to convert the values ​​of the map into a list with those values");
    }

    @Test
    public void testExtractLongFromString() {
        printTitle("testExtractLongFromString()");

        assertAll(
                () -> {
                    String theString = "0asfklkdkr";
                    Long actualValue = TypeConverter.extractLongFromString(theString);
                    Long expectedValue = 0L;

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is " + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    String theString = "-987654321012me";
                    Long actualValue = TypeConverter.extractLongFromString(theString);
                    Long expectedValue = -987654321012L;

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is " + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    TypeConverterException ex = assertThrows(TypeConverterException.class, () -> {
                        String theString = "ItsNotALongValue";
                        TypeConverter.extractLongFromString(theString);
                    });
                    if (logger.isDebugEnabled()) {
                        logger.debug("TypeConverterException : " + ex.getMessage());
                    }
                },
                () -> {
                    TypeConverterException ex = assertThrows(TypeConverterException.class, () -> {
                        String theString = "9223372036854775808";
                        TypeConverter.extractLongFromString(theString);
                    });
                    if (logger.isDebugEnabled()) {
                        logger.debug("TypeConverterException : " + ex.getMessage());
                    }
                }
        );

    }

    @Test
    public void testExtractDoubleFromString() {
        printTitle("testExtractDoubleFromString()");

        //TODO: Do the test for exception and strings with negative double and no double inside
        assertAll(
                () -> {
                    String theString = "asfk3.07lkdkr";
                    Double actualValue = TypeConverter.extractDoubleFromString(theString);
                    Double expectedValue = 3.07;

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    String theString = "asfk-3.07lkdkr";
                    Double actualValue = TypeConverter.extractDoubleFromString(theString);
                    Double expectedValue = -3.07;

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    TypeConverterException ex = assertThrows(TypeConverterException.class, () -> {
                        String theString = null;
                        Double actualValue = TypeConverter.extractDoubleFromString(theString);
                    });
                    if (logger.isDebugEnabled()) {
                        logger.debug("TypeConverterException : " + ex.getMessage());
                    }
                },
                () -> {
                    TypeConverterException ex = assertThrows(TypeConverterException.class, () -> {
                        String theString = "   ";
                        Double actualValue = TypeConverter.extractDoubleFromString(theString);
                    });
                    if (logger.isDebugEnabled()) {
                        logger.debug("TypeConverterException : " + ex.getMessage());
                    }
                }
        );

    }

    @Test
    public void testArray2String() {
        printTitle("testArray2String()");

        Integer[] integerArray = {0, 1, 2};
        String expectedValue = CAR_INI
                .concat(String.valueOf(0))
                .concat(SEP)
                .concat(String.valueOf(1))
                .concat(SEP)
                .concat(String.valueOf(2))
                .concat(CAR_FIN);
        String actualValue = TypeConverter.array2String(integerArray);

        printResults(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));

    }

    @Test
    public void testArra2StringRecursive() {
        printTitle("testArra2StringRecursive()");

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
        String actualValue = TypeConverter.array2String(objectsArray);

        printResults(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
    }

    @Test
    public void testArray2StringNoArrayParameter() {
        printTitle("testArray2StringNoArrayParameter()");

        List<String> stringsList = new ArrayList<>();
        stringsList.add("a");
        stringsList.add("b");

        String expectedValue = NULL;
        String actualValue = TypeConverter.array2String(stringsList);

        printResults(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
    }

    @Test
    public void testArray2StringNullArray() {
        printTitle("testArray2StringNullArray()");

        String[] stringsArray = null;

        String expectedValue = NULL;
        String actualValue = TypeConverter.array2String(stringsArray);

        printResults(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
    }

    @Test
    public void testArrayByte2String() {
        printTitle("testArrayByte2String()");

        assertAll(
                () -> {
                    byte[] bytesArray = {65, 66, 67, 68};

                    String expectedValue = "(4 bytes), [0]=65,[1]=66,[2]=67,[3]=68";
                    String actualValue = TypeConverter.arrayByte2String(bytesArray, true, true);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    byte[] bytesArray = {65, 66, 67, 68};

                    String expectedValue = "[0]=65,[1]=66,[2]=67,[3]=68";
                    String actualValue = TypeConverter.arrayByte2String(bytesArray, false, true);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    byte[] bytesArray = {65, 66, 67, 68};

                    String expectedValue = "(4 bytes), 65,66,67,68";
                    String actualValue = TypeConverter.arrayByte2String(bytesArray, true, false);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    byte[] bytesArray = {65, 66, 67, 68};

                    String expectedValue = "65,66,67,68";
                    String actualValue = TypeConverter.arrayByte2String(bytesArray, false, false);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    byte[] bytesArray = null;

                    String expectedValue = "";
                    String actualValue = TypeConverter.arrayByte2String(bytesArray, false, false);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                }
        );

    }

    @Test
    public void testMap2String() {
        printTitle("testMap2String()");

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
                    String actualValue = TypeConverter.map2String(theMap);
                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    Map<Integer, String> theMap = null;

                    String expectedValue = "";
                    String actualValue = TypeConverter.map2String(theMap);
                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                }
        );
    }

    @Test
    public void testByteToHex() {
        printTitle("testByteToHex()");

        byte theByte = 127;

        String expectedValue = "7f";
        String actualValue = TypeConverter.byteToHex(theByte);

        printResults(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
    }

    @Test
    public void testCharToHex() {
        printTitle("testCharToHex()");

        char theCharacter = '\uffff';

        String expectedValue = "ffff";
        String actualValue = TypeConverter.charToHex(theCharacter);

        printResults(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
    }

    @Test
    public void testExtractDigits() {
        printTitle("testExtractDigits()");

        assertAll(
                () -> {
                    String theString = "ds[*_gjd1234ds,cf$vm";

                    String expectedValue = "1234";
                    String actualValue = TypeConverter.extractDigits(theString);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                },
                () -> {
                    String theString = null;

                    String expectedValue = "";
                    String actualValue = TypeConverter.extractDigits(theString);

                    printResults(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "The value returned is" + String.valueOf(actualValue) + "and it should be" + String.valueOf(expectedValue));
                }
        );

    }

    // Utility methods
    private void printTitle(String methodName) {
        String test = "TEST " + methodName;
        logger.debug(test);
    }

    private void printResults(Object expectedValue, Object actualValue) {
        String actVal = "Return value -> " + String.valueOf(actualValue);
        String expVal = "Expected value -> " + String.valueOf(expectedValue);
        logger.debug(actVal);
        logger.debug(expVal);
    }
}
