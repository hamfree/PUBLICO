package es.nom.juanfranciscoruiz.utiles;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junitpioneer.jupiter.StdIn;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

/**
 *
 * @author juanf
 */
public class MenuTest {

    public MenuTest() {
    }

    /**
     * Test of getOptions method, of class Menu.
     */
    @Test
    public void testGetOpciones() {
        Menu instance = new Menu();
        instance.setIsHomeMenu(true);
        instance.setOptions(generaOpciones());
        List<String> expResult = generaOpciones();
        expResult.add(0, "0. Exit the application");

        List<String> result = instance.getOptions();
        assertEquals(expResult, result);
    }

    /**
     * Test of setOptions method, of class Menu.
     */
    @Test
    public void testSetOptions() {
        List<String> opciones = generaOpciones();
        Menu instance = new Menu();
        instance.setIsHomeMenu(false);
        instance.setOptions(opciones);
        List<String> expResult = generaOpciones();
        List<String> result = instance.getOptions();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTitle method, of class Menu.
     */
    @Test
    public void testGetTitleOfAnUnsettedTitleProperty() {
        Menu instance = new Menu();
        String expResult = "Untitled";
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTitle method, of class Menu.
     */
    @Test
    public void testSetTitulo() {
        String titulo = "Application Test";
        Menu instance = new Menu();
        instance.setTitle(titulo);
        String expResult = "Application Test";
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMessage method, of class Menu.
     */
    @Test
    public void testGetMensaje() {
        Menu instance = new Menu();
        String expResult = "";
        String result = instance.getMessage();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMessage method, of class Menu.
     */
    @Test
    public void testSetMensaje() {
        String mensaje = "Un mensaje cualquiera";
        Menu instance = new Menu();
        instance.setMessage(mensaje);
        String result = instance.getMessage();
        String expResult = "Un mensaje cualquiera";
        assertEquals(expResult, result);
    }

    /**
     * Test of getSelectedOption method, of class Menu.
     */
    @Test
    public void testGetOpcionSeleccionada() {
        Menu instance = new Menu();
        Long expResult = null;
        Long result = instance.getSelectedOption();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSelectedOption method, of class Menu.
     */
    @Test
    public void testSetSelectedOption() {
        Long opcionSeleccionada = 5L;
        Menu instance = new Menu();
        instance.setSelectedOption(opcionSeleccionada);
        Long expResult = 5L;
        Long result = instance.getSelectedOption();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsHomeMenu method, of class Menu.
     */
    @Test
    public void testIsEsMenuInicio() {
        Menu instance = new Menu();
        boolean expResult = false;
        boolean result = instance.getIsHomeMenu();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIsHomeMenu method, of class Menu.
     */
    @Test
    public void testSetEsMenuInicio() {
        boolean esMenuInicio = true;
        Menu instance = new Menu();
        instance.setIsHomeMenu(esMenuInicio);
        boolean expResult = true;
        boolean result = instance.getIsHomeMenu();
        assertEquals(expResult, result);
    }

    /**
     * Test of mostrar method, of class Menu.
     */
    @Test
    public void testGenerateView() throws Exception {
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
        assertEquals(expResult, instance.getMenuView());
    }

    /**
     * Test of awaitResponse method, of class Menu.
     *
     * @param in
     * @param out
     * @throws java.lang.Exception
     */
    @Test
    @StdIo({"2"})
    public void testAwaitResponse(StdIn in, StdOut out) throws Exception {
        String msg = "";
        Menu instance = new Menu();
        instance.setOptions(generaOpciones());
        instance.setIsHomeMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");
        Long expResult = 2L;
        instance.awaitResponse(msg);
        assertEquals(expResult, instance.getSelectedOption());
    }

    /**
     * Test of toString method, of class Menu.
     */
    @Test
    public void testToString() {
        Menu instance = new Menu();
        StringBuilder sb = new StringBuilder();
        sb.append("Menu{");
        sb.append("opciones=").append(Util.CollectionAsString(instance.getOptions(), true, 10));
        sb.append(", titulo=").append(instance.getTitle());
        sb.append(", mensaje=").append(instance.getMessage());
        sb.append(", opcionSeleccionada=").append(instance.getSelectedOption());
        sb.append(", esMenuInicio=").append(instance.getIsHomeMenu());
        sb.append('}');
        String expResult = sb.toString();
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    private List<String> generaOpciones() {
        List<String> opciones = new ArrayList<>();
        opciones.add("1. Option One");
        opciones.add("2. Option Two");
        opciones.add("3. Option Three");
        return opciones;
    }
}
