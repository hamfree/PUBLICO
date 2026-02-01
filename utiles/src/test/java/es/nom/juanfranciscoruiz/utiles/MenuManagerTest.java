package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuManagerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIn;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static es.nom.juanfranciscoruiz.utiles.TestUtils.printResults;
import static es.nom.juanfranciscoruiz.utiles.TestUtils.printTitle;
import static org.junit.jupiter.api.Assertions.*;

class MenuManagerTest {

    public final static Logger logger = LoggerFactory.getLogger(MenuManagerTest.class);
    List<String> options = new ArrayList<>();

    @BeforeEach
    void setUp() {
        options.add("1. Option One");
        options.add("2. Option Two");
        options.add("3. Option Three");
    }

    @Test
    void getMenu() throws MenuException {
        printTitle("testGetMenu()", logger);
        MenuManager instance = new MenuManager();
        Menu expectedValue = instance.getMenu();
        printResults(expectedValue,instance.getMenu(),logger);
        assertNotNull(expectedValue, "Menu should not be null after initialization");
    }

    @Test
    void setMenu() throws MenuException, MenuManagerException {
        printTitle("testSetMenu()", logger);
        MenuManager instance = new MenuManager();
        Menu expectedValue = new Menu();
        instance.setMenu(expectedValue);
        printResults(expectedValue,instance.getMenu(),logger);
        assertEquals(expectedValue, instance.getMenu(), "Menu should be the same after setting it");
    }
    
    @Test
    void setMenuWithNull() throws MenuException, MenuManagerException {
        printTitle("testSetMenuWithNull()", logger);
        MenuManager instance = new MenuManager();
        assertThrows(MenuManagerException.class, () -> instance.setMenu(null),
            "MenuManagerException" +
            " " +
            "should be thrown");
    }

    /**
     * Tests menu rendering against expected output
     */
    @Test
    void showMenu() throws Exception {
        printTitle("testShowMenu()", logger);
        MenuManager instance = new MenuManager();
        Menu menu = new Menu();
        menu.setOptions(options);
        menu.setIsRootMenu(true);
        menu.setMessage("Select an option:");
        menu.setTitle("Testing the showMenu method");
        instance.setMenu(menu);
        String expectedValue = menu.getMenuView();
        String actualValue = tapSystemOut(() -> {
            instance.showMenu(false);
        });
        printResults(expectedValue,actualValue,logger);
        assertEquals(expectedValue, actualValue);
    }

    /**
     * Tests exception on empty menu options
     */
    @Test
    void showInvalidMenu() throws MenuManagerException, MenuException {
        printTitle("testShowInvalidMenu()", logger);
        MenuManager instance = new MenuManager();
        Menu menu = new Menu();
        menu.setOptions(options);
        menu.setIsRootMenu(true);
        menu.setMessage("Select an option:");
        menu.setTitle("Testing the showMenu method");

        // Clear options to simulate an invalid menu
        menu.getOptions().clear();
        try {
            instance.showMenu(false);
            fail("Expected MenuException not thrown");
        } catch (MenuManagerException e) {
            assertEquals("There are no defined options in this menu", e.getMessage());
        }
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
        printTitle("testAwaitResponse()", logger);
        String msg = "";
        Menu instance = new Menu();
        instance.setOptions(options);
        instance.setIsRootMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");
        Long expResult = 2L;
        instance.awaitResponse(msg);
        Long result = instance.getSelectedOption();
        printResults(expResult,result,logger);
        assertEquals(expResult, result, "The selected option should be 2");
    }

    private List<String> generateOptionsForChildMenus() {
        List<String> opciones = new ArrayList<>();
        opciones.add("1. Option One");
        opciones.add("2. Option Two");
        opciones.add("3. Option Three");
        return opciones;
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
        printTitle("testAwaitResponseForInvalidResponse()",logger);
        String msg = "";
        Menu instance = new Menu();
        instance.setOptions(generateOptionsForChildMenus());
        instance.setIsRootMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");

        MenuException ex = assertThrows(MenuException.class, () -> instance.awaitResponse(msg),
                "The user answer with an invalid response (not a number) and throws an MenuException");
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
        printTitle("testAwaitResponseForResponseOutOfRange()",logger);
        String msg = "";
        Menu instance = new Menu();
        instance.setOptions(generateOptionsForChildMenus());
        instance.setIsRootMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");

        MenuException ex = assertThrows(MenuException.class, () -> instance.awaitResponse(msg),
                "The user answer with an invalid response (out of range) and throws an MenuException");
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }


    /**
     * Tests exception on invalid menu object
     */
    @Test
    public void testAwaitResponseForNonValidMenuObject() throws MenuException {
        printTitle("testAwaitResponseForNonValidMenuObject()", logger);
        String msg = "";
        Menu instance = new Menu();
        instance.setIsRootMenu(false); //Not include the exit option
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the awaitResponse() method");

        /*
         We must use reflexion to bypass the private field validation. In normal cases, the
         options property should be set by the client class and validated by the constructor
         and the setter methods. This is an exceptional case for testing purposes.
         */
        if (logger.isDebugEnabled()) logger.debug("Setting options to null with api reflection!");
        try {
            Class<?> clazz = instance.getClass();
            Field[] fields = clazz.getDeclaredFields();
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
        MenuException ex = assertThrows(MenuException.class, () -> instance.awaitResponse(msg),
                "The user answer with an invalid response (not a number) and throws an MenuException");
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }
}