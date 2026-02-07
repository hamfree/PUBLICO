package es.nom.juanfranciscoruiz.utiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static es.nom.juanfranciscoruiz.utiles.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the Menu class.
 *
 * @author Juan F. Ruiz
 */
public class MenuTest {
    public final static Logger logger = LoggerFactory.getLogger(MenuTest.class);

    @BeforeAll
    static void beforeAll() {
        printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Starting MenuTest" + System.lineSeparator(), logger);
    }

    @AfterAll
    static void afterAll() {
        printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Ending MenuTest" + System.lineSeparator(), logger);
    }

    public MenuTest() {
    }


    /**
     * Test of getOptions method, of class Menu.
     */
    @Test
    public void testDefaultConstructor() throws MenuException {
        printTitletoLogAndConsole("testDefaultConstructor()", logger);
        Menu instance = new Menu();
        // Verifies default constructor initializes menu properties correctly
        assertAll(
                () -> assertNotNull(instance.getOptions(), "...testing not null options"),
                () -> assertTrue(instance.getOptions().isEmpty(), "...testing empty options"),
                () -> assertEquals("Untitled", instance.getTitle(), "...testing default title"),
                () -> assertEquals("", instance.getMessage(), "...testing default message"),
                () -> {
                    assertEquals("""
                                    \r
                                    *************\r
                                      Untitled  \r
                                    *************\r
                                    \r
                                    \r
                                    \r
                                    """,
                            instance.getMenuView(), "...testing default menu view");
                },
                () -> assertFalse(instance.getIsRootMenu(), "...testing default is root menu")
        );
    }

    @Test
    public void testConstructorWithParamsDefaults() throws MenuException {
        printTitletoLogAndConsole("testConstructorWithParamsDefaults()", logger);
        Menu instance = new Menu(null, "", "msg", false);
        // Verifies constructor with null title uses default title
        assertAll(
                () -> assertNotNull(instance.getOptions(), "...testing not null options"),
                () -> assertTrue(instance.getOptions().isEmpty(), "...testing empty options"),
                () -> assertEquals("Untitled", instance.getTitle(), "...testing default title"),
                () -> assertEquals("msg", instance.getMessage(), "...testing default message"),
                () -> assertFalse(instance.getIsRootMenu(), "...testing default is root menu")
        );
    }

    /**
     * Verifies root menu constructor adds exit option
     */
    @Test
    public void testConstructorWithParamsRootAddsExitOption() throws MenuException {
        printTitletoLogAndConsole("testConstructorWithParamsRootAddsExitOption()", logger);
        List<String> options = generateOptionsForChildMenus();
        Menu instance = new Menu(options, "Title", "Msg", true);
        List<String> result = instance.getOptions();
        assertAll(
                () -> assertEquals("0. Exit the application", result.getFirst(), "...testing exit option"),
                () -> assertEquals(11, result.size(), "...testing options size"),
                () -> assertEquals("0. Option One", result.get(1), "...testing option one")
        );
    }

    @Test
    public void testConstructorWithParamsNullMessageThrows() {
        printTitletoLogAndConsole("testConstructorWithParamsNullMessageThrows()", logger);
        assertThrows(
                MenuException.class, () -> new Menu(new ArrayList<>(), "Title", null, false),
                "The message can't be null");
    }

    @Test
    public void testConstructorWithSubmenusRootWithParentThrows() throws MenuException {
        printTitletoLogAndConsole("testConstructorWithSubmenusRootWithParentThrows()", logger);
        Menu parent = new Menu();
        assertThrows(MenuException.class, () ->
                        new Menu(generateOptionsForChildMenus(), "Title", "Msg", true,
                                new ArrayList<>(), parent),
                "The parent menu can't be null for root menus"
        );
    }

    @Test
    public void testConstructorWithSubmenusNonRootWithoutParentThrows() {
        printTitletoLogAndConsole("testConstructorWithSubmenusNonRootWithoutParentThrows()", logger);
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
        printTitletoLogAndConsole("testConstructorWithSubmenusSetsParentAndSubmenus()", logger);
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
        printTitletoLogAndConsole("testGetOptionsForRootMenu()", logger);
        Menu instance = new Menu();
        instance.setIsRootMenu(true);
        instance.setOptions(generateOptionsForChildMenus());
        List<String> expResult = generateOptionsForExpectedResult();
        List<String> result = instance.getOptions();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result,
                "The first option should be '0. Exit the application'");
    }

    /**
     * Tests non‑root menu options are those set
     */
    @Test
    public void testGetOptionsForNonRootMenu() throws MenuException {
        printTitletoLogAndConsole("testGetOptionsForNonRootMenu()", logger);
        Menu instance = new Menu();
        instance.setIsRootMenu(false);
        instance.setOptions(generateOptionsForChildMenus());
        List<String> expResult = generateOptionsForChildMenusForExpectedResult();
        List<String> result = instance.getOptions();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result,
                "The list of options should be the same as the one set with setOptions()");
    }

    @Test
    public void testGetOptionsForNonRootEmptyMenu() throws MenuException {
        printTitletoLogAndConsole("testGetOptionsForNonRootEmptyMenu()", logger);
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
        printTitletoLogAndConsole("testSetOptions()", logger);
        List<String> opciones = generateOptionsForChildMenus();
        Menu instance = new Menu();
        instance.setIsRootMenu(false);
        instance.setOptions(opciones);
        List<String> expResult = generateOptionsForChildMenusForExpectedResult();
        List<String> result = instance.getOptions();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result,
                "The list of options should be the same as the one set with setOptions()");
    }

    /**
     * Verifies `MenuException` is thrown when options are null
     */
    @Test
    public void testSetOptionsWithNull() throws MenuException {
        printTitletoLogAndConsole("testSetOptionsWithEmptyList()", logger);
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
        printTitletoLogAndConsole("testSetOptionsForRootMenu()", logger);
        Menu instance = new Menu();
        // At this point the method setIsRootMenu(true) would add the first option "0. Exit the
        // application"
        instance.setIsRootMenu(true);
        instance.setOptions(generateOptionsForChildMenus());

        // When setting options for a root menu, the first option should be "0. Exit the application"
        List<String> expResult = generateOptionsForExpectedResult();
        List<String> result = instance.getOptions();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result,
                "The first option should be '0. Exit the application' and" +
                        " the list of options should be the same as the one set with setOptions()");
    }

    /**
     * Test of getTitle method, of class Menu.
     */
    @Test
    public void testGetTitleOfAnUnsettedTitleProperty() throws MenuException {
        printTitletoLogAndConsole("testGetTitleOfAnUnsettedTitleProperty()", logger);
        Menu instance = new Menu();
        String expResult = "Untitled";
        String result = instance.getTitle();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The title should be 'Untitled'");
    }

    /**
     * Test of setTitle method, of class Menu.
     */
    @Test
    public void testSetTitle() throws MenuException {
        printTitletoLogAndConsole("testSetTitle()", logger);
        String titulo = "Application Test";
        Menu instance = new Menu();
        instance.setTitle(titulo);
        String expResult = "Application Test";
        String result = instance.getTitle();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The title should be 'Application Test'");
    }

    /**
     * Confirms `setTitle` throws exception on null input
     */
    @Test
    public void testSetTitleWithNullValue() throws MenuException {
        printTitletoLogAndConsole("testSetTitleWithNullValue()", logger);
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
        printTitletoLogAndConsole("testGetMessage()", logger);
        Menu instance = new Menu();
        String expResult = "";
        String result = instance.getMessage();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The message should be empty");
    }

    /**
     * Test of setMessage method, of class Menu.
     */
    @Test
    public void testSetMessage() throws MenuException {
        printTitletoLogAndConsole("testSetMensaje()", logger);
        String mensaje = "Un mensaje cualquiera";
        Menu instance = new Menu();
        instance.setMessage(mensaje);
        String result = instance.getMessage();
        String expResult = "Un mensaje cualquiera";
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The message should be 'Un mensaje cualquiera'");
    }

    /**
     * Confirms `setMessage` throws exception on null input
     */
    @Test
    public void testSetMessageWithNullValue() throws MenuException {
        printTitletoLogAndConsole("testSetMessageWithNullValue()", logger);
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
        printTitletoLogAndConsole("testGetSelectedOption()", logger);
        Menu instance = new Menu();
        Long result = instance.getSelectedOption();
        Long expResult = 0L;
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result,
                "The selected option should be 0 (Exit the application)");
    }

    /**
     * Test of setSelectedOption method, of class Menu.
     */
    @Test
    public void testSetSelectedOption() throws MenuException {
        printTitletoLogAndConsole("testSetSelectedOption()", logger);
        Long opcionSeleccionada = 5L;
        Menu instance = new Menu();
        instance.setSelectedOption(opcionSeleccionada);
        Long expResult = 5L;
        Long result = instance.getSelectedOption();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The selected option should be 5");
    }

    /**
     * Test of getIsRootMenu method, of class Menu.
     */
    @Test
    public void testIsRootMenu() throws MenuException {
        printTitletoLogAndConsole("testIsRootMenu()", logger);
        Menu instance = new Menu();
        boolean expResult = false;
        boolean result = instance.getIsRootMenu();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The menu should not be a root menu");
    }

    /**
     * Test of setIsRootMenu method, of class Menu.
     */
    @Test
    public void testSetIsRootMenu() throws MenuException {
        printTitletoLogAndConsole("testSetEsMenuInicio()", logger);
        boolean esMenuInicio = true;
        Menu instance = new Menu();
        instance.setIsRootMenu(esMenuInicio);
        boolean expResult = true;
        boolean result = instance.getIsRootMenu();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The menu should be a root menu");
    }

    /**
     * Test of mostrar method, of class Menu.
     */
    @Test
    public void testGenerateView() throws MenuException {
        printTitletoLogAndConsole("testGenerateView()", logger);
        final String SL = System.lineSeparator();
        Menu instance = new Menu();
        instance.setTitle("Title");
        instance.setMessage("this is the message");
        instance.setIsRootMenu(true);
        instance.setOptions(generateOptionsForChildMenus());
        instance.generateMenuView();

        String expResult = SL +
                "**********" + SL +
                "  Title  " + SL +
                "**********" + SL + SL +
                "0. Exit the application" + SL +
                "1. Option One" + SL +
                "2. Option Two" + SL +
                "3. Option Three" + SL +
                "4. Option Four" + SL +
                "5. Option Five" + SL +
                "6. Option Six" + SL +
                "7. Option Seven" + SL +
                "8. Option Eight" + SL +
                "9. Option Nine" + SL +
                "10. Option Ten" + SL + SL +
                "this is the message" + SL;

        String result = instance.getMenuView();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The menu view should be generated correctly");
    }


    /**
     * Test of toString method, of class Menu.
     */
    @Test
    public void testToString() throws MenuException {
        printTitletoLogAndConsole("testToString()", logger);
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
        printResultsToLogAndConsole(expResult, result, logger);
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

    //--------------------------------------------------------------------------
    // Utility methods for tests

    /**
     * Generates a list of sample menu options for testing purposes and for child menus
     * for provide the options to set the menu options property.
     *
     * @return a list of menu options
     */
    private List<String> generateOptionsForChildMenus() {
        List<String> options = new ArrayList<>();
        options.add("Option One");
        options.add("Option Two");
        options.add("Option Three");
        options.add("Option Four");
        options.add("Option Five");
        options.add("Option Six");
        options.add("Option Seven");
        options.add("Option Eight");
        options.add("Option Nine");
        options.add("Option Ten");
        return options;
    }

    /**
     * Generates a list of sample menu options for testing purposes and for the root menus
     * for provide the options to set the menu options property.
     *
     * @return a list of menu options
     */
    private List<String> generateOptionsForRootMenu() {
        List<String> options = new ArrayList<>();
        //options.add("0. Exit the application");
        options.addAll(generateOptionsForChildMenus());
        return options;
    }

    /**
     * Generates a predefined list of menu options for testing purposes and for the
     * 'expectedResult' property of the Menu variable (because the options added
     * to the menu instance were modified by the addOption() method adding the
     * index of the option).
     * <p>
     * This is the version for the root menu.
     *
     * @return a list of strings representing menu options, including an exit
     * option and ten other sample options.
     */
    private List<String> generateOptionsForExpectedResult() {
        List<String> options = new ArrayList<>();
        options.add("0. Exit the application");
        options.add("1. Option One");
        options.add("2. Option Two");
        options.add("3. Option Three");
        options.add("4. Option Four");
        options.add("5. Option Five");
        options.add("6. Option Six");
        options.add("7. Option Seven");
        options.add("8. Option Eight");
        options.add("9. Option Nine");
        options.add("10. Option Ten");
        return options;
    }

    /**
     * Generates a predefined list of menu options for testing purposes and for the
     * 'expectedResult' property of the Menu variable (because the options added
     * to the menu instance were modified by the addOption() method adding the
     * index of the option).
     * <p>
     * This is the version for the child menus.
     *
     * @return a list of strings representing menu options, including an exit
     * option and ten other sample options.
     */
    private List<String> generateOptionsForChildMenusForExpectedResult() {
        List<String> options = new ArrayList<>();
        options.add("1. Option One");
        options.add("2. Option Two");
        options.add("3. Option Three");
        options.add("4. Option Four");
        options.add("5. Option Five");
        options.add("6. Option Six");
        options.add("7. Option Seven");
        options.add("8. Option Eight");
        options.add("9. Option Nine");
        options.add("10. Option Ten");
        return options;
    }
}
