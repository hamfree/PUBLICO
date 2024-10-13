package es.nom.juanfranciscoruiz.utiles;

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
    Importamos constantes de la clase IO para facilitar la escritura de los tests.
     */
    public final static String CAR_INI = IO.getCAR_INI();
    public final static String CAR_FIN = IO.getCAR_FIN();
    public final static String NULL = IO.getNULL();
    public final static String SEP = IO.getSEP();
    public final static String SF = IO.getSF();
    public final static String SL = IO.getSL();

    @Test
    public void collection2List() {
        imprimeTitulo("collection2List()");

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

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "Debería convertir el vector en una lista");

    }

    @Test
    public void map2List() {
        imprimeTitulo("collection2List()");

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

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "Debería convertir el mapa a una lista");
    }

    @Test
    public void extractLongFromString() {
        imprimeTitulo("extractLongFromString()");

        String cadena = "0asfklkdkr";
        Long actualValue = TypeConverter.extractLongFromString(cadena);
        Long expectedValue = 0L;

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
    }

    @Test
    public void extractDoubleFromString() {
        imprimeTitulo("extractDoubleFromString()");

        String cadena = "asfk3.07lkdkr";
        Double actualValue = TypeConverter.extractDoubleFromString(cadena);
        Double expectedValue = 3.07;

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
    }

    @Test
    public void array2String() {
        imprimeTitulo("array2String()");

        Integer[] matrizEnteros = {0, 1, 2};
        String expectedValue = CAR_INI
                .concat(String.valueOf(0))
                .concat(SEP)
                .concat(String.valueOf(1))
                .concat(SEP)
                .concat(String.valueOf(2))
                .concat(CAR_FIN);
        String actualValue = TypeConverter.array2String(matrizEnteros);

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));

    }

    @Test
    public void arra2StringRecursivo() {
        imprimeTitulo("arra2StringRecursivo()");

        Object[] matriz = {0, new Object[]{5, 6}, 2, new Object[]{"a", "b"}, 4};
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
        String actualValue = TypeConverter.array2String(matriz);

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
    }

    @Test
    public void array2StringParametroNoArray() {
        imprimeTitulo("array2StringParametroNoArray()");

        List<String> lista = new ArrayList<>();
        lista.add("a");
        lista.add("b");

        String expectedValue = NULL;
        String actualValue = TypeConverter.array2String(lista);

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
    }

    @Test
    public void array2StringArrayNulo() {
        imprimeTitulo("array2StringArrayNulo()");

        String[] matriz = null;

        String expectedValue = NULL;
        String actualValue = TypeConverter.array2String(matriz);

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
    }

    @Test
    public void arrayByte2String() {
        imprimeTitulo("arrayByte2String()");

        assertAll(
                () -> {
                    byte[] matrizBytes = {65, 66, 67, 68};

                    String expectedValue = "(4 bytes), [0]=65,[1]=66,[2]=67,[3]=68";
                    String actualValue = TypeConverter.arrayByte2String(matrizBytes, true, true);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    byte[] matrizBytes = {65, 66, 67, 68};

                    String expectedValue = "[0]=65,[1]=66,[2]=67,[3]=68";
                    String actualValue = TypeConverter.arrayByte2String(matrizBytes, false, true);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    byte[] matrizBytes = {65, 66, 67, 68};

                    String expectedValue = "(4 bytes), 65,66,67,68";
                    String actualValue = TypeConverter.arrayByte2String(matrizBytes, true, false);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    byte[] matrizBytes = {65, 66, 67, 68};

                    String expectedValue = "65,66,67,68";
                    String actualValue = TypeConverter.arrayByte2String(matrizBytes, false, false);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    byte[] matrizBytes = null;

                    String expectedValue = "";
                    String actualValue = TypeConverter.arrayByte2String(matrizBytes, false, false);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                }
        );

    }

    @Test
    public void map2String() {
        imprimeTitulo("arrayByte2String()");

        assertAll(
                () -> {
                    Map<Integer, String> mapa = new TreeMap<>();
                    mapa.put(1, "UNO");
                    mapa.put(2, "DOS");
                    mapa.put(3, "TRES");
                    mapa.put(4, "CUATRO");

                    String expectedValue = String.valueOf(1).concat(SEP).concat("UNO").concat(SL)
                            .concat(String.valueOf(2)).concat(SEP).concat("DOS").concat(SL)
                            .concat(String.valueOf(3)).concat(SEP).concat("TRES").concat(SL)
                            .concat(String.valueOf(4)).concat(SEP).concat("CUATRO").concat(SL);
                    String actualValue = TypeConverter.map2String(mapa);
                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    Map<Integer, String> mapa = null;

                    String expectedValue = "";
                    String actualValue = TypeConverter.map2String(mapa);
                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                }
        );
    }

    @Test
    public void byteToHex() {
        imprimeTitulo("byteToHex()");

        byte octeto = 127;

        String expectedValue = "7f";
        String actualValue = TypeConverter.byteToHex(octeto);

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
    }

    @Test
    public void charToHex() {
        imprimeTitulo("charToHex()");

        char caracter = '\uffff';

        String expectedValue = "ffff";
        String actualValue = TypeConverter.charToHex(caracter);

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
    }

    @Test
    public void extractDigits() {
        imprimeTitulo("extractDigits()");

        assertAll(
                () -> {
                    String cadena = "ds[*_gjd1234ds,cf$vm";

                    String expectedValue = "1234";
                    String actualValue = TypeConverter.extractDigits(cadena);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    String cadena = null;
                    
                    String expectedValue = "";
                    String actualValue = TypeConverter.extractDigits(cadena);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es" + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                }
        );

    }

    private void imprimeTitulo(String nombreMetodo) {
        String test = "TEST " + nombreMetodo;
        logger.debug(test);
    }

    private void imprimeResultados(Object expectedValue, Object actualValue) {
        String actVal = "Valor devuelto -> " + String.valueOf(actualValue);
        String expVal = "Valor esperado -> " + String.valueOf(expectedValue);
        logger.debug(actVal);
        logger.debug(expVal);
    }
}
