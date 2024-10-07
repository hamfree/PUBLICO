package es.nom.juanfranciscoruiz.utiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junitpioneer.jupiter.StdIn;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

public class IOTest {
    
    private static final String ERR_PARAM = "¡Parámetros nulos o incorrectos!";
    private static final String ERR_NULL = "¡Parámetros nulos!";
    private static final String ERR_SOME_NULL  = "¡Alguno de los parámetros es nulo!";
    private static final String ERR_LONG = "La longitud de la linea es menor que la longitud del mensaje";

    @Test
    @StdIo()
    public void prt(StdOut out) {
        IO.prt("uno", "dos", "tres");
        String actualValue = out.capturedString();
        String expectedValue = "unodostres";
        assertEquals(expectedValue, actualValue, "Los valores deberían ser los mismos");
    }

    @Test
    public void prtNulo() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prt(null);
        });
        assertEquals(ERR_NULL, ex.getMessage(),"Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_NULL);
    }
    
    @Test
    public void prtNuloEnPrimerElementoArgumentosVariables() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prt((Object) null);
        });
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_SOME_NULL);
    }
    
    
    @Test
    public void prtAlgunNuloEnArgumentosVariables() {
        Object[] args = {new Object(),null,new Object(), new Object()};
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prt(args);
        });
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_SOME_NULL);
    }

    @Test
    @StdIo()
    public void prtln(StdOut out) {
        String sl = System.lineSeparator();
        IO.prtln(2, "uno", "dos", "tres");
        String actualValue = out.capturedString();
        String expectedValue = "unodostres".concat(sl).concat(sl);
        assertEquals(expectedValue, actualValue, "Los valores deberían ser los mismos");
    }
    
    @Test
    public void prtlnNulo(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prtln(1,null);
        });
        assertEquals(ERR_NULL, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_NULL);
    }
    
    public void prtlnNuloEnPrimerElementoArgumentosVariables(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prtln(1,(Object) null);
        });
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_SOME_NULL);
    }
    
    public void prtlnAlgunNuloEnArgumentosVariables(){
        Object[] args = {new Object(),null,new Object(), new Object()};
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prtln(3,args);
        });
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_SOME_NULL);
    }
    

    @Test
    @StdIo({"uno"})
    public void read(StdIn in, StdOut out) throws Exception {
        String actualValue = IO.read();
        String expectedValue = "uno";
        assertEquals(expectedValue, actualValue, "Los valores deberían ser los mismos");
    }

    @Test
    public void titulo() {
        String expectedValue = System.lineSeparator()
                .concat("********************")
                .concat(System.lineSeparator())
                .concat("      MENSAJE      ")
                .concat(System.lineSeparator())
                .concat("********************");
        String actualValue = IO.titulo("MENSAJE", '*', 20);
        assertEquals(expectedValue, actualValue);
    }
    
    @Test
    public void tituloMensajeNulo(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.titulo(null, '*', 20);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_PARAM);
    }

    @Test
    public void tituloCaracterNulo(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.titulo("MENSAJE", null, 20);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_PARAM);
    }
    
    @Test
    public void tituloLongitudMenorQueLongitudMensaje(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.titulo("MENSAJE", '*', 3);
        });
        assertEquals(ERR_LONG, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_LONG);
    }
    
    @Test
    public void linea() {
        String expectedValue = "**********";
        String actualValue = IO.linea('*', 10);
        assertEquals(expectedValue, actualValue,"El método linea() debería generar una cadena con 10 asteriscos (*)");
    }
    
    @Test
    public void lineaCaracterNulo(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.linea(null, 10);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_PARAM);
    }
    
    @Test
    public void lineaLongitudErronea(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.linea('#', 0);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_PARAM);
    }
    

    @Test
    public void repiteCaracter() {
        String expectedValue = "---------";
        String actualValue = IO.repiteCaracter('-', 9);
        assertEquals(expectedValue, actualValue,"El método repiteCaracter() debería generar una cadena con 9 guiones (-)");
    }
    
    @Test
    public void repiteCaracterParametrosMalCaracter(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.repiteCaracter(null, 3);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_PARAM);
    }
    
    @Test
    public void repiteCaracterParametrosMalVeces(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.repiteCaracter('a', -4);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "Debería generarse una excepcion IllegalArgumentException con el mensaje " + ERR_PARAM);
    }
}
