package es.nom.juanfranciscoruiz.utiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeConverterTest {

    @Test
    void collection2List() {
    }

    @Test
    void map2List() {
    }

    @Test
    void extractLongFromString() {
        String cadena = "0asfklkdkr";
        Long actualValue = TypeConverter.extractLongFromString(cadena);
        Long expectedValue = 0L;
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void extractDoubleFromString() {
    }

    @Test
    void array2String() {
    }

    @Test
    void arrayByte2String() {
    }

    @Test
    void map2String() {
    }

    @Test
    void byteToHex() {
    }

    @Test
    void charToHex() {
    }

    @Test
    void extractDigits() {
    }
}