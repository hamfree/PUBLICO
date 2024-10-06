package es.nom.juanfranciscoruiz.utiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypesTest {
    
    public final static Logger logger = LoggerFactory.getLogger(TypesTest.class);

    @Test
    public void isNullOrEmpty() {
    }

    @Test
    public void isInteger() {
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