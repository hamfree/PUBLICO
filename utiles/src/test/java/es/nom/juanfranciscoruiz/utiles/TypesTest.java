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
    public void isNullOrEmpty() {
        imprimeTitulo("isNullOrEmpty()");

        assertAll(
                () -> {
                    logger.debug("Lista con dos elementos. Debe dar false");
                    List<String> coleccion = new ArrayList<>();
                    coleccion.add("uno");
                    coleccion.add("dos");

                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(coleccion);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Mapa sin elementos. Debe dar true");
                    Map<String, String> coleccion = new HashMap<>();

                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(coleccion);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Un valor nulo. Debe dar true");
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(null);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Una instancia de Object que apunta a null. Debe dar true");
                    Object valor = null;
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(valor);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Una instancia de Object. Debe dar false");
                    Object valor = new Object();
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(valor);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Una matriz con 5 objects de valor nulo. Debe dar false");
                    Object[] valor = new Object[5];
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(valor);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Una matriz sin elementos. Debe devolver true");
                    Object[] valor = new Object[0];
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(valor);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Una matriz con dos Strings, uno de ellos apunta a null. Debe dar false");
                    String[] valores = {"un valor", null};
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(valores);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Un String con un valor asignado. Debe dar false");
                    String valor = "algun valor";
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(valor);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Un array de objects vacío. Debe dar true");
                    Object[] objetos = {};
                    boolean expectedValue = true;
                    boolean actualValue = Types.isNullOrEmpty(objetos);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    logger.debug("Un valor de tipo primitivo. Debe dar false");
                    byte octeto = 0x55;
                    boolean expectedValue = false;
                    boolean actualValue = Types.isNullOrEmpty(octeto);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                }
        );

    }



    @Test
    public void isInteger() {
        imprimeTitulo("isInteger()");

        assertAll(
                () -> {
                    System.out.println("Un objeto que apunta a nulo. Debe dar false");
                    Object obj = null;

                    boolean expectedValue = false;
                    boolean actualValue = Types.isInteger(obj);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    System.out.println("Una instancia de Object. Debe dar false");
                    Object obj = new Object();

                    boolean expectedValue = false;
                    boolean actualValue = Types.isInteger(obj);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    System.out.println("Una instancia de String con un contenido convertible a Integer. Debe dar true");
                    String obj = "45566";

                    boolean expectedValue = true;
                    boolean actualValue = Types.isInteger(obj);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                },
                () -> {
                    System.out.println("Una instancia de String con un contenido NO convertible a Integer. Debe dar false");
                    String obj = "no es un entero";

                    boolean expectedValue = false;
                    boolean actualValue = Types.isInteger(obj);

                    imprimeResultados(expectedValue, actualValue);

                    assertEquals(expectedValue, actualValue, "El valor devuelto es " + String.valueOf(actualValue) + " y debía ser " + String.valueOf(expectedValue));
                }
        );

    }

    @Test
    public void isLong() {
    }

    @Test
    public void isFloat() {
    }

    @Test
    public void isDouble() {
    }

    @Test
    public void isArray() {
    }

    @Test
    public void isFromType() {
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
