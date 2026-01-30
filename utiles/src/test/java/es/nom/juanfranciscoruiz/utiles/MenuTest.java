package es.nom.juanfranciscoruiz.utiles;

import java.util.ArrayList;
import java.util.List;

import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    public void testDefaultConstructor() throws MenuException {
        printTitle("testDefaultConstructor()");
        Menu instance = new Menu();
        // Verifies default constructor initializes menu properties correctly
        assertAll(
            () -> assertNotNull(instance.getOptions(),"...testing not null options"),
            () -> assertTrue(instance.getOptions().isEmpty(), "...testing empty options"),
            () -> assertEquals("Untitled", instance.getTitle(), "...testing default title"),
            () -> assertEquals("", instance.getMessage(), "...testing default message"),
            () -> assertEquals("", instance.getMenuView(), "...testing default menu view"),
            () -> assertFalse(instance.getIsRootMenu(), "...testing default is root menu")
        );
    }

    @Test
    public void testConstructorWithParamsDefaults() throws MenuException {
        printTitle("testConstructorWithParamsDefaults()");
        Menu instance = new Menu(null, "", "msg", false);
        // Verifies constructor with null title uses default title
        assertAll(
            () -> assertNotNull(instance.getOptions()),
            () -> assertTrue(instance.getOptions().isEmpty()),
            () -> assertEquals("Untitled", instance.getTitle()),
            () -> assertEquals("msg", instance.getMessage()),
            () -> assertFalse(instance.getIsRootMenu())
        );
    }

    /**
     * Verifies root menu constructor adds exit option
     */
    @Test
    public void testConstructorWithParamsRootAddsExitOption() throws MenuException {
        printTitle("testConstructorWithParamsRootAddsExitOption()");
        List<String> options = generateOptionsForChildMenus();
        Menu instance = new Menu(options, "Title", "Msg", true);
        List<String> result = instance.getOptions();
        assertAll(
            () -> assertEquals("0. Exit the application", result.getFirst()),
            () -> assertEquals(4, result.size()),
            () -> assertEquals("1. Option One", result.get(1))
        );
    }

    @Test
    public void testConstructorWithParamsNullMessageThrows() {
        printTitle("testConstructorWithParamsNullMessageThrows()");
        assertThrows(
            MenuException.class, () -> new Menu(new ArrayList<>(), "Title", null, false),
            "The message can't be null");
    }

    @Test
    public void testConstructorWithSubmenusRootWithParentThrows() throws MenuException {
        printTitle("testConstructorWithSubmenusRootWithParentThrows()");
        Menu parent = new Menu();
        assertThrows(MenuException.class, () ->
            new Menu(generateOptionsForChildMenus(), "Title", "Msg", true,
                new ArrayList<>(), parent),
            "The parent menu can't be null for root menus"
        );
    }

    @Test
    public void testConstructorWithSubmenusNonRootWithoutParentThrows() {
        printTitle("testConstructorWithSubmenusNonRootWithoutParentThrows()");
        assertThrows(MenuException.class, () ->
            new Menu(generateOptionsForChildMenus(), "Title", "Msg", false,
                new ArrayList<>(), null),
            "The parent menu can't be null for non-root menus"
        );
    }

    /**
     * Tests constructor sets parent and submenus correctly
     */
    @Test
    public void testConstructorWithSubmenusSetsParentAndSubmenus() throws MenuException {
        printTitle("testConstructorWithSubmenusSetsParentAndSubmenus()");
        Menu parent = new Menu();
        List<Menu> subMenus = new ArrayList<>();
        Menu instance = new Menu(generateOptionsForChildMenus(), "Title", "Msg", false,
            subMenus, parent);
        assertAll(
            () -> assertSame(parent, instance.getParentMenu(), "...testing parent menu"),
            () -> assertSame(subMenus, instance.getSubMenus(), "...testing submenus")
        );
    }

    /**
     * Tests root menu options include exit option
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
        assertEquals(expResult, result,
            "The first option should be '0. Exit the application'");
    }

    /**
     * Tests non‑root menu options are those set
     */
    @Test
    public void testGetOptionsForNonRootMenu() throws MenuException {
        printTitle("testGetOptionsForNonRootMenu()");
        Menu instance = new Menu();
        instance.setIsRootMenu(false);
        instance.setOptions(generateOptionsForChildMenus());
        List<String> expResult = generateOptionsForChildMenus();
        List<String> result = instance.getOptions();
        printResults(expResult,result);
        assertEquals(expResult, result,
            "The list of options should be the same as the one set with setOptions()");
    }

    @Test
    public void testGetOptionsForNonRootEmptyMenu() throws MenuException {
        printTitle("testGetOptionsForNonRootEmptyMenu()");
        Menu instance = new Menu();
        instance.setIsRootMenu(false);
        List<String> expResult = new ArrayList<>();
        List<String> result = instance.getOptions();
        assertEquals(expResult, result, "The list of options should be empty");
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
        assertEquals(expResult, result,
            "The list of options should be the same as the one set with setOptions()");
    }

    /**
     * Verifies `MenuException` is thrown when options are null
     */
    @Test
    public void testSetOptionsWithNullt() throws MenuException {
        printTitle("testSetOptionsWithEmptyList()");
        Menu instance = new Menu();
        instance.setIsRootMenu(false);
        MenuException ex = assertThrows(MenuException.class, () -> instance.setOptions(null),
            "The list of options can't be null");
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }

    /**
     * Tests root menu options include exit option
     */
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
        assertEquals(expResult, result,
            "The first option should be '0. Exit the application' and" +
            " the list of options should be the same as the one set with setOptions()");
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
        assertEquals(expResult, result, "The title should be 'Untitled'");
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
        assertEquals(expResult, result, "The title should be 'Application Test'");
    }

    /**
     * Confirms `setTitle` throws exception on null input
     */
    @Test
    public void testSetTitleWithNullValue() throws MenuException {
        printTitle("testSetTitleWithNullValue()");
        Menu instance = new Menu();
        MenuException ex = assertThrows(MenuException.class, () -> instance.setTitle(null),
            "The method setTitle() can't accept null values and throws an MenuException");
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
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
        assertEquals(expResult, result, "The message should be empty");
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
        assertEquals(expResult, result, "The message should be 'Un mensaje cualquiera'");
    }

    /**
     * Confirms `setMessage` throws exception on null input
     */
    @Test
    public void testSetMessageWithNullValue() throws MenuException {
        printTitle("testSetMessageWithNullValue()");
        Menu instance = new Menu();
        MenuException ex = assertThrows(MenuException.class, () -> instance.setMessage(null),
            "The method setMessage() can't accept null values and throws an MenuException");
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }

    /**
     * Test of getSelectedOption method, of class Menu.
     */
    @Test
    public void testGetSelectedOptionOfANewMenu() throws MenuException {
        printTitle("testGetSelectedOption()");
        Menu instance = new Menu();
        Long result = instance.getSelectedOption();
        Long expResult = 0L;
        printResults(expResult,result);
        assertEquals(expResult, result,
            "The selected option should be 0 (Exit the application)");
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
        assertEquals(expResult, result, "The selected option should be 5");
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
        assertEquals(expResult, result, "The menu should not be a root menu");
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
        assertEquals(expResult, result, "The menu should be a root menu");
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
        assertEquals(expResult, result, "The menu view should be generated correctly");
    }



    /**
     * Test of toString method, of class Menu.
     */
    @Test
    public void testToString() throws MenuException {
        printTitle("testToString()");
        Menu instance = new Menu();
        String expResult = "Menu{" +
                "options=" + Util.CollectionToString(instance.getOptions(), true, 10) +
                ", title=" + instance.getTitle() +
                ", message=" + instance.getMessage() +
                ", selectedOption=" + instance.getSelectedOption() +
                ", isRootMenu=" + instance.getIsRootMenu() +
                ", parentMenu=" + (instance.getParentMenu() != null ?
                instance.getParentMenu().getTitle() : "null") +
                ", menuView='" + instance.getMenuView() + '\'' +
                ", subMenus=" + Util.CollectionToString(instance.getSubMenus(), true, 10) +
                '}';
        String result = instance.toString();
        printResults(expResult,result);
        assertEquals(expResult, result,
            "The toString() method should return a string " +
            "representation of the object");
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

    /**
     * Generates a list of sample menu options for testing purposes.
     * @return a list of menu options
     */
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
