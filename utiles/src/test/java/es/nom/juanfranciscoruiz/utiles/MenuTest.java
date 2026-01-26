package es.nom.juanfranciscoruiz.utiles;

import java.lang.reflect.Field;
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
    public void testGetOptionsForRootMenu() throws MenuException {
        printTitle("testGetOptionsForRootMenu()");
        Menu instance = new Menu();
        instance.setIsRootMenu(true);
        instance.setOptions(generateOptionsForChildMenus());
        List<String> expResult = generateOptionsForChildMenus();
        expResult.addFirst("0. Exit the application");
        List<String> result = instance.getOptions();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetOptionsForNonRootMenu() throws MenuException {
        printTitle("testGetOptionsForNonRootMenu()");
        Menu instance = new Menu();
        instance.setIsRootMenu(false);
        instance.setOptions(generateOptionsForChildMenus());
        List<String> expResult = generateOptionsForChildMenus();
        List<String> result = instance.getOptions();
        printResults(expResult,result);
    }
    
    @Test
    public void testGetOptionsForNonRootEmptyMenu() throws MenuException {
        printTitle("testGetOptionsForNonRootEmptyMenu()");
        Menu instance = new Menu();
        instance.setIsRootMenu(false);
        List<String> expResult = new ArrayList<>();
        List<String> result = instance.getOptions();
    }
    

    /**
     * Test of setOptions method, of class Menu.
     */
    @Test
    public void testSetOptions() throws MenuException {
        printTitle("testSetOptions()");
        List<String> opciones = generateOptionsForChildMenus();
        Menu instance = new Menu();
        instance.setIsRootMenu(false);
        instance.setOptions(opciones);
        List<String> expResult = generateOptionsForChildMenus();
        List<String> result = instance.getOptions();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }
    
    
    @Test
    public void testSetOptionsForRootMenu() throws MenuException {
        printTitle("testSetOptionsForRootMenu()");
        Menu instance = new Menu();
        // At this point the method setIsRootMenu(true) would add the first option "0. Exit the
        // application"
        instance.setIsRootMenu(true);
        instance.setOptions(generateOptionsForChildMenus());
        
        // When setting options for a root menu, the first option should be "0. Exit the application"
        List<String> expResult = generateOptionsForRootMenu();
        List<String> result = instance.getOptions();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTitle method, of class Menu.
     */
    @Test
    public void testGetTitleOfAnUnsettedTitleProperty() throws MenuException {
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
    public void testSetTitle() throws MenuException {
        printTitle("testSetTitle()");
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
    public void testGetMessage() throws MenuException {
        printTitle("testGetMessage()");
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
    public void testSetMessage() throws MenuException {
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
    public void testGetSelectedOption() throws MenuException {
        printTitle("testGetSelectedOption()");
        Menu instance = new Menu();
        Long result = instance.getSelectedOption();
        printResults(null,result);
        assertNull(result);
    }

    /**
     * Test of setSelectedOption method, of class Menu.
     */
    @Test
    public void testSetSelectedOption() throws MenuException {
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
     * Test of getIsRootMenu method, of class Menu.
     */
    @Test
    public void testIsRootMenu() throws MenuException {
        printTitle("testIsRootMenu()");
        Menu instance = new Menu();
        boolean expResult = false;
        boolean result = instance.getIsRootMenu();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of setIsRootMenu method, of class Menu.
     */
    @Test
    public void testSetIsRootMenu() throws MenuException {
        printTitle("testSetEsMenuInicio()");
        boolean esMenuInicio = true;
        Menu instance = new Menu();
        instance.setIsRootMenu(esMenuInicio);
        boolean expResult = true;
        boolean result = instance.getIsRootMenu();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }

    /**
     * Test of mostrar method, of class Menu.
     */
    @Test
    public void testGenerateView() throws MenuException {
        printTitle("testGenerateView()");
        final String SL = System.lineSeparator();
        Menu instance = new Menu();
        instance.setTitle("Title");
        instance.setMessage("this is the message");
        instance.setIsRootMenu(true);
        instance.setOptions(generateOptionsForChildMenus());
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
        instance.setOptions(generateOptionsForChildMenus());
        instance.setIsRootMenu(true);
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
    public void testAwaitResponseForInvalidResponse(StdIn in, StdOut out) throws MenuException {
        printTitle("testAwaitResponseForInvalidResponse()");
        String msg = "";
        Menu instance = new Menu();
        instance.setOptions(generateOptionsForChildMenus());
        instance.setIsRootMenu(true);
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
    public void testAwaitResponseForResponseOutOfRange(StdIn in, StdOut out) throws MenuException {
        printTitle("testAwaitResponseForResponseOutOfRange()");
        String msg = "";
        Menu instance = new Menu();
        instance.setOptions(generateOptionsForChildMenus());
        instance.setIsRootMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");

        MenuException ex = assertThrows(MenuException.class, () -> instance.awaitResponse(msg));
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }


    /**
     * Tests exception on invalid menu object
     */
    @Test
    public void testAwaitResponseForNonValidMenuObject() throws MenuException {
        printTitle("testAwaitResponseForNonValidMenuObject()");
        String msg = "";
        Menu instance = new Menu();
        instance.setIsRootMenu(false); //Not include the exit option
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");
      
        // We must use reflexion to bypass the private field validation
        // In normal cases, the options property should be set by the client class
        // and validated by the constructor and the setter methods. This is an
        // exceptional case for testing purposes.
        if (logger.isDebugEnabled()) logger.debug("Setting options to null with api reflection!");
        try {
            Class<?> clazz = instance.getClass();
            Field fields[] = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("options")) {
                    field.setAccessible(true);
                    field.set(instance,null); // set the List<String> of field 'options' to null
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        
        if (logger.isDebugEnabled()) logger.debug("Value of 'options' field: {}",
            instance.getOptions() != null ? "not null" :"null");
        
        
        MenuException ex = assertThrows(MenuException.class, () -> instance.awaitResponse(msg));
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());

    }

    /**
     * Test of toString method, of class Menu.
     */
    @Test
    public void testToString() throws MenuException {
        printTitle("testToString()");
        Menu instance = new Menu();
        StringBuilder sb = new StringBuilder();
        sb.append("Menu{");
        sb.append("options=").append(Util.CollectionToString(instance.getOptions(), true, 10));
        sb.append(", title=").append(instance.getTitle());
        sb.append(", message=").append(instance.getMessage());
        sb.append(", selectedOption=").append(instance.getSelectedOption());
        sb.append(", isRootMenu=").append(instance.getIsRootMenu());
        sb.append(", parentMenu=").append(instance.getParentMenu() != null ?
            instance.getParentMenu().getTitle() : "null");
        sb.append(", menuView='").append(instance.getMenuView()).append('\'');
        sb.append(", subMenus=").append(Util.CollectionToString(instance.getSubMenus(), true, 10));
        sb.append('}');
        String expResult = sb.toString();
        String result = instance.toString();
        printResults(expResult,result);
        assertEquals(expResult, result);
    }
    
 
   
    @Test
    void testGetMenuView() {
    }
    
    @Test
    void testGetSubMenus() {
    }
    
    @Test
    void TestSetSubMenus() {
    }
    
    @Test
    void TestGetParentMenu() {
    }
    
    @Test
    void TestSetParentMenu() {
    }
    
    @Test
    void TestAddSubMenu() {
    }
    
    @Test
    void TestRemoveSubMenu() {
    }
    
    @Test
    void TestAddOption() {
    }
    
    @Test
    void TestRemoveOption() {
    }
    
    
    // Utility methods for tests
    /**
     * Generates a list of sample menu options for testing purposes.
     * @return a list of menu options
     */
    private List<String> generateOptionsForChildMenus() {
        List<String> opciones = new ArrayList<>();
        opciones.add("1. Option One");
        opciones.add("2. Option Two");
        opciones.add("3. Option Three");
        return opciones;
    }
    
    private List<String> generateOptionsForRootMenu() {
        List<String> opciones = new ArrayList<>();
        opciones.add("0. Exit the application");
        opciones.addAll(generateOptionsForChildMenus());
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
