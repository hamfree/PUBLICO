package es.nom.juanfranciscoruiz.utiles;

import java.util.ArrayList;
import java.util.List;

import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junitpioneer.jupiter.StdIn;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the Menu class.
 * @author Juan F. Ruiz
 */
public class MenuTest {
    public final static Logger logger = LoggerFactory.getLogger(MenuTest.class);

    public MenuTest() {
    }

    /**
     * Test of getOptions method, of class Menu.
     */
    @Test
    public void testGetOpciones() {
        printTitle("testGetOpciones()");
        Menu instance = new Menu();
        instance.setIsHomeMenu(true);
        instance.setOptions(generaOpciones());
        List<String> expResult = generaOpciones();
        expResult.addFirst("0. Exit the application");
        List<String> result = instance.getOptions();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of setOptions method, of class Menu.
     */
    @Test
    public void testSetOptions() {
        printTitle("testSetOptions()");
        List<String> opciones = generaOpciones();
        Menu instance = new Menu();
        instance.setIsHomeMenu(false);
        instance.setOptions(opciones);
        List<String> expResult = generaOpciones();
        List<String> result = instance.getOptions();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTitle method, of class Menu.
     */
    @Test
    public void testGetTitleOfAnUnsettedTitleProperty() {
        printTitle("testGetTitleOfAnUnsettedTitleProperty()");
        Menu instance = new Menu();
        String expResult = "Untitled";
        String result = instance.getTitle();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of setTitle method, of class Menu.
     */
    @Test
    public void testSetTitulo() {
        printTitle("testSetTitulo()");
        String titulo = "Application Test";
        Menu instance = new Menu();
        instance.setTitle(titulo);
        String expResult = "Application Test";
        String result = instance.getTitle();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMessage method, of class Menu.
     */
    @Test
    public void testGetMensaje() {
        printTitle("testGetMensaje()");
        Menu instance = new Menu();
        String expResult = "";
        String result = instance.getMessage();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of setMessage method, of class Menu.
     */
    @Test
    public void testSetMensaje() {
        printTitle("testSetMensaje()");
        String mensaje = "Un mensaje cualquiera";
        Menu instance = new Menu();
        instance.setMessage(mensaje);
        String result = instance.getMessage();
        String expResult = "Un mensaje cualquiera";
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSelectedOption method, of class Menu.
     */
    @Test
    public void testGetOpcionSeleccionada() {
        printTitle("testGetOpcionSeleccionada()");
        Menu instance = new Menu();
        Long result = instance.getSelectedOption();
        printResults(null,result);
        assertNull(result);
    }

    /**
     * Test of setSelectedOption method, of class Menu.
     */
    @Test
    public void testSetSelectedOption() {
        printTitle("testSetSelectedOption()");
        Long opcionSeleccionada = 5L;
        Menu instance = new Menu();
        instance.setSelectedOption(opcionSeleccionada);
        Long expResult = 5L;
        Long result = instance.getSelectedOption();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsHomeMenu method, of class Menu.
     */
    @Test
    public void testIsEsMenuInicio() {
        printTitle("testIsEsMenuInicio()");
        Menu instance = new Menu();
        boolean expResult = false;
        boolean result = instance.getIsHomeMenu();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of setIsHomeMenu method, of class Menu.
     */
    @Test
    public void testSetEsMenuInicio() {
        printTitle("testSetEsMenuInicio()");
        boolean esMenuInicio = true;
        Menu instance = new Menu();
        instance.setIsHomeMenu(esMenuInicio);
        boolean expResult = true;
        boolean result = instance.getIsHomeMenu();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of mostrar method, of class Menu.
     */
    @Test
    public void testGenerateView() {
        printTitle("testGenerateView()");
        final String SL = System.lineSeparator();
        Menu instance = new Menu();
        instance.setTitle("Title");
        instance.setMessage("this is the message");
        instance.setIsHomeMenu(true);
        instance.setOptions(generaOpciones());
        instance.generateMenuView();

        String expResult = SL 
                + "**********" + SL
                + "  Title  " + SL
                + "**********" + SL
                +  SL
                + "0. Exit the application" + SL
                + "1. Option One" + SL
                + "2. Option Two" + SL
                + "3. Option Three" + SL
                +  SL
                + "this is the message" + SL;
        String result = instance.getMenuView();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of awaitResponse method, of class Menu.
     *
     * @param in a StdIn object for testing
     * @param out a StdOut object for testing
     * @throws java.lang.Exception in case of an error
     */
    @Test
    @StdIo({"2"})
    public void testAwaitResponse(StdIn in, StdOut out) throws Exception {
        printTitle("testAwaitResponse()");
        String msg = "";
        Menu instance = new Menu();
        instance.setOptions(generaOpciones());
        instance.setIsHomeMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");
        Long expResult = 2L;
        instance.awaitResponse(msg);
        Long result = instance.getSelectedOption();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of awaitResponse method, of class Menu when the user answer with
     * an invalid response (not a number).
     *
     * @param in a StdIn object for testing
     * @param out a StdOut object for testing
     */
    @Test
    @StdIo({"notValid"})
    public void testAwaitResponseForInvalidResponse(StdIn in, StdOut out) {
        printTitle("testAwaitResponseForInvalidResponse()");
        String msg = "";
        Menu instance = new Menu();
        instance.setOptions(generaOpciones());
        instance.setIsHomeMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");

        MenuException ex = assertThrows(MenuException.class, () -> instance.awaitResponse(msg));
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }

    /**
     * Test of awaitResponse method, of class Menu when the user answer with
     * an invalid response (not a number).
     *
     * @param in a StdIn object for testing
     * @param out a StdOut object for testing
     */
    @Test
    @StdIo({"6"})
    public void testAwaitResponseForResponseOutOfRange(StdIn in, StdOut out) {
        printTitle("testAwaitResponseForResponseOutOfRange()");
        String msg = "";
        Menu instance = new Menu();
        instance.setOptions(generaOpciones());
        instance.setIsHomeMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");

        MenuException ex = assertThrows(MenuException.class, () -> instance.awaitResponse(msg));
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }


    /**
     * Tests exception on invalid menu object
     */
    @Test
    public void testAwaitResponseForNonValidMenuObject() {
        printTitle("testAwaitResponseForNonValidMenuObject()");
        String msg = "";
        Menu instance = new Menu();
        instance.setOptions(null); //Invalid options list
        instance.setIsHomeMenu(false); //Not include the exit option
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");

        MenuException ex = assertThrows(MenuException.class, () -> instance.awaitResponse(msg));
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());

    }

    /**
     * Test of toString method, of class Menu.
     */
    @Test
    public void testToString() {
        printTitle("testToString()");
        Menu instance = new Menu();
        StringBuilder sb = new StringBuilder();
        sb.append("Menu{");
        sb.append("opciones=").append(Util.CollectionToString(instance.getOptions(), true, 10));
        sb.append(", titulo=").append(instance.getTitle());
        sb.append(", mensaje=").append(instance.getMessage());
        sb.append(", opcionSeleccionada=").append(instance.getSelectedOption());
        sb.append(", esMenuInicio=").append(instance.getIsHomeMenu());
        sb.append('}');
        String expResult = sb.toString();
        String result = instance.toString();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    // Utility methods for tests
    /**
     * Generates a list of sample menu options for testing purposes.
     * @return a list of menu options
     */
    private List<String> generaOpciones() {
        List<String> opciones = new ArrayList<>();
        opciones.add("1. Option One");
        opciones.add("2. Option Two");
        opciones.add("3. Option Three");
        return opciones;
    }

    /**
     * Logs a debug message with the formatted title for the given method name.
     *
     * @param methodName The name of the method whose title is to be printed.
     */
    private void printTitle(String methodName) {
        String test = "TEST " + methodName;
        logger.debug(test);
    }

    /**
     * Logs the actual and expected values for debugging purposes.
     *
     * @param expectedValue The expected value to be compared.
     * @param actualValue The actual value obtained.
     */
    private void printResults(Object expectedValue, Object actualValue) {
        String actVal = "Return value -> " + actualValue;
        String expVal = "Expected value -> " + expectedValue;
        logger.debug(actVal);
        logger.debug(expVal);
    }
}
