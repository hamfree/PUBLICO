package es.nom.juanfranciscoruiz.utiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOTest {
    public final static Logger logger = LoggerFactory.getLogger(IOTest.class);

    @Test
    public void prt() {
    }

    @Test
    public void prtln() {
    }

    @Test
    public void read() {
    }

    @Test
    public void titulo() {
    }

    @Test
    public void linea() {
    }

    @Test
    public void repiteCaracter() {
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