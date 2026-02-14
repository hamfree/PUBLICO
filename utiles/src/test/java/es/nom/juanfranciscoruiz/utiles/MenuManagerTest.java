package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuManagerException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIn;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static es.nom.juanfranciscoruiz.utiles.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuManagerTest {

    public final static Logger logger = LoggerFactory.getLogger(MenuManagerTest.class);
    List<String> options = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Starting MenuManagerTest" + System.lineSeparator(), logger);
    }

    @AfterAll
    static void afterAll() {
        printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Ending MenuManagerTest" + System.lineSeparator(), logger);
    }


    public MenuManagerTest() {
    }

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @BeforeEach
    void setUp() {
        options.add("1. Option One");
        options.add("2. Option Two");
        options.add("3. Option Three");
    }

    @Test
    void getMenu() throws MenuException {
        printTitletoLogAndConsole("testGetMenu()", logger);
        MenuManager instance = new MenuManager();
        Menu expectedValue = instance.getMenu();
        printResultsToLogAndConsole(expectedValue,instance.getMenu(),logger);
        assertNotNull(expectedValue, "Menu should not be null after initialization");
    }

    @Test
    void setMenu() throws MenuException, MenuManagerException {
        printTitletoLogAndConsole("testSetMenu()", logger);
        MenuManager instance = new MenuManager();
        Menu expectedValue = new Menu();
        instance.setMenu(expectedValue);
        printResultsToLogAndConsole(expectedValue,instance.getMenu(),logger);
        assertEquals(expectedValue, instance.getMenu(), "Menu should be the same after setting it");
    }
    
    @Test
    void setMenuWithNull() throws MenuException {
        printTitletoLogAndConsole("testSetMenuWithNull()", logger);
        MenuManager instance = new MenuManager();
        assertThrows(MenuManagerException.class, () -> instance.setMenu(null),
            "MenuManagerException should be thrown");
    }

    /**
     * Tests menu rendering against expected output
     */
    @Test
    void showMenu() throws Exception {
        printTitletoLogAndConsole("testShowMenu()", logger);
        MenuManager instance = new MenuManager();
        Menu menu = new Menu();
        menu.setOptions(options);
        menu.setIsRootMenu(true);
        menu.setMessage("Select an option:");
        menu.setTitle("Testing the showMenu method");
        instance.setMenu(menu);
        menu.generateMenuView();
        String expectedValue = menu.getMenuView();
        String actualValue = tapSystemOut(() -> {
            instance.showMenu(false);
        });
        printResultsToLogAndConsole(expectedValue,actualValue,logger);
        assertEquals(expectedValue, actualValue);
    }

    /**
     * Tests exception on empty menu options
     */
    @Test
    void showInvalidMenu() throws MenuException {
        printTitletoLogAndConsole("testShowInvalidMenu()", logger);
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
        printTitletoLogAndConsole("testAwaitResponse()", logger);
        String msg = "";
        
        Menu menu = new Menu();
        menu.setOptions(options);
        menu.setIsRootMenu(true);
        menu.setMessage("Select an option:");
        menu.setTitle("Testing the awaitResponse() method");
        Long expResult = 2L;
        MenuManager instance = new MenuManager();
        instance.setMenu(menu);
        instance.awaitResponse(msg);
        Long result = menu.getSelectedOption();
        printResultsToLogAndConsole(expResult,result,logger);
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
    public void testAwaitResponseForInvalidResponse(StdIn in, StdOut out) throws MenuException, MenuManagerException {
        printTitletoLogAndConsole("testAwaitResponseForInvalidResponse()",logger);
        String msg = "";
        Menu menu = new Menu();
        menu.setOptions(generateOptionsForChildMenus());
        menu.setIsRootMenu(true);
        menu.setMessage("Select an option:");
        menu.setTitle("Testing the awaitResponse() method");
        MenuManager instance = new MenuManager();
        instance.setMenu(menu);

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
    public void testAwaitResponseForResponseOutOfRange(StdIn in, StdOut out) throws MenuException, MenuManagerException {
        printTitletoLogAndConsole("testAwaitResponseForResponseOutOfRange()",logger);
        String msg = "";
        Menu menu = new Menu();
        menu.setOptions(generateOptionsForChildMenus());
        menu.setIsRootMenu(true);
        menu.setMessage("Select an option:");
        menu.setTitle("Testing the awaitResponse() method");
        
        MenuManager instance = new MenuManager();
        instance.setMenu(menu);

        MenuException ex = assertThrows(MenuException.class, () -> instance.awaitResponse(msg),
                "The user answer with an invalid response (out of range) and throws an MenuException");
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }


    /**
     * Tests exception on invalid menu object
     */
    @Test
    public void testAwaitResponseForNonValidMenuObject() throws MenuException, MenuManagerException {
        printTitletoLogAndConsole("testAwaitResponseForNonValidMenuObject()", logger);
        String msg = "";
        Menu menu = new Menu();
        menu.setIsRootMenu(false); //Not include the exit option
        menu.setMessage("Select an option:");
        menu.setTitle("Testing the awaitResponse() method");

        MenuManager instance = new MenuManager();
        instance.setMenu(menu);
        
        /*
         We must use reflexion to bypass the private field validation. In normal cases, the
         options property should be set by the client class and validated by the constructor
         and the setter methods. This is an **exceptional case** for testing purposes.
         */
        if (logger.isDebugEnabled()) logger.debug("Setting options to null with api reflection!");
        try {
            Class<?> clazz = menu.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("options")) {
                    field.setAccessible(true);
                    field.set(menu,null); // set the List<String> of field 'options' to null
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (logger.isDebugEnabled()) logger.debug("Value of 'options' field: {}",
                menu.getOptions() != null ? "not null" :"null");
        MenuException ex = assertThrows(MenuException.class, () -> instance.awaitResponse(msg),
                "The user answer with an invalid response (not a number) and throws an MenuException");
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }
}