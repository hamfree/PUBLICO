package es.nom.juanfranciscoruiz.utiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeConverterTest {
    
    public final static Logger logger = LoggerFactory.getLogger(TypeConverterTest.class);

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
        
        imprimeResultados(expectedValue, actualValue)  ;
        
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
    }

    @Test
    public void arrayByte2String() {
    }

    @Test
    public void map2String() {
    }

    @Test
    public void byteToHex() {
    }

    @Test
    public void charToHex() {
    }

    @Test
    public void extractDigits() {
    }
    
    
    private void imprimeTitulo(String nombreMetodo){
        String test = "TEST " + nombreMetodo;
        logger.debug(test);
    }
    
    private void imprimeResultados(Object expectedValue, Object actualValue){
        String actVal = "Valor devuelto -> " + String.valueOf(actualValue);
        String expVal = "Valor esperado -> " + String.valueOf(expectedValue);
        logger.debug(actVal);
        logger.debug(expVal);
    }
}
