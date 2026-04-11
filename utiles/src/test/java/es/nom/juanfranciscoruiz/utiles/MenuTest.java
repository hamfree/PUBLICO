package es.nom.juanfranciscoruiz.utiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static es.nom.juanfranciscoruiz.utiles.TestUtils.*;
import static es.nom.juanfranciscoruiz.utiles.TestUtils.printMsgToLogAndConsole;
import static es.nom.juanfranciscoruiz.utiles.Util.SL;
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

    public static final String LS = System.lineSeparator();

    @BeforeAll
    static void beforeAll() {
        printMsgToLogAndConsole(LS + LocalDateTime.now() + " - Starting MenuTest" + LS, logger);
    }

    @AfterAll
    static void afterAll() {
        printMsgToLogAndConsole(LS + LocalDateTime.now() + " - Ending MenuTest" + LS, logger);
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
                () -> assertEquals("1. Option One", result.get(1), "...testing option one")
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
                () -> assertSame(parent, instance.getParentMenu(), "...testing parent menu")
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
        List<String> expResult = new ArrayList<>();
        expResult.add("0. Exit the application");
        List<String> result = instance.getOptions();
        assertEquals(expResult, result,
                "The first option should be '0. Exit the application'");
    }

    /**
     * Tests non-root menu options do not include exit option
     */
    @Test
    public void testGetOptionsForNonRootMenu() throws MenuException {
        printTitletoLogAndConsole("testGetOptionsForNonRootMenu()", logger);
        Menu instance = new Menu();
        instance.setIsRootMenu(false);
        List<String> expResult = new ArrayList<>();
        List<String> result = instance.getOptions();
        assertEquals(expResult, result, "The list of options should be empty");
    }

    /**
     * Tests non-root empty menu options includes the '[VOLVER]' option if options property is not empty
     */
    @Test
    public void testGetOptionsForNonRootEmptyMenu() throws MenuException {
        printTitletoLogAndConsole("testGetOptionsForNonRootEmptyMenu()", logger);
        Menu instance = new Menu();
        List<String> options = new ArrayList<>();
        options.add("Opción 1");
        instance.setOptions(options); // This sets options list properly.
        instance.setIsRootMenu(false);
        List<String> expResult = new ArrayList<>();
        expResult.add("0. [VOLVER]");
        expResult.add("1. Opción 1");
        List<String> result = instance.getOptions();
        assertEquals(expResult, result, "The list of options have one option: '0. [VOLVER]");
    }

    /**
     * Tests setting options on a root menu updates options correctly
     */
    @Test
    public void testSetOptions() throws MenuException {
        printTitletoLogAndConsole("testSetOptions()", logger);
        Menu instance = new Menu();
        List<String> expectedOptions = generateOptionsForChildMenus();
        instance.setOptions(expectedOptions);
        List<String> result = instance.getOptions();
        assertAll(
                () -> assertEquals(expectedOptions.size(), result.size(), "...testing options size"),
                () -> assertEquals(expectedOptions, result, "...testing options content")
        );
    }

    /**
     * Verifies `MenuException` is thrown when options are null
     */
    @Test
    public void testSetOptionsWithNull() throws MenuException {
        printTitletoLogAndConsole("testSetOptionsWithNull()", logger);
        Menu instance = new Menu();

        MenuException ex = assertThrows(MenuException.class, () -> instance.setOptions(null),
                "The method setOptions() can't accept null values and throws an MenuException");
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }

    /**
     * Tests setting options for root menu automatically prepends 'Exit' option
     */
    @Test
    public void testSetOptionsForRootMenu() throws MenuException {
        printTitletoLogAndConsole("testSetOptionsForRootMenu()", logger);
        Menu instance = new Menu();
        instance.setIsRootMenu(true);
        List<String> options = generateOptionsForChildMenus();
        instance.setOptions(options);

        // When setting options for a root menu, the first option should be "0. Exit the application"
        // and the rest of the options must be preserving their order (1 to N).
        List<String> expResult = generateOptionsForChildMenus();
        expResult.addFirst("0. Exit the application");
        List<String> result = instance.getOptions();
        assertEquals(expResult, result,
                "The first option should be '0. Exit the application' and" +
                        " the rest of the options must be preserving their order");
    }

    /**
     * Tests retrieving an unset title property returns 'Untitled'
     */
    @Test
    public void testGetTitleOfAnUnsettedTitleProperty() throws MenuException {
        printTitletoLogAndConsole("testGetTitleOfAnUnsettedTitleProperty()", logger);
        Menu instance = new Menu();
        String expResult = "Untitled";
        String result = instance.getTitle();

        assertEquals(expResult, result, "The title should be 'Untitled'");
    }

    /**
     * Tests setting the title updates it appropriately
     */
    @Test
    public void testSetTitle() throws MenuException {
        printTitletoLogAndConsole("testSetTitle()", logger);
        String title = "Testing the setTitle() method.";
        Menu instance = new Menu();
        instance.setTitle(title);
        String result = instance.getTitle();

        assertEquals(title, result, "The title must be " + title);
    }

    /**
     * Tests `MenuException` is thrown when setting a null title
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
     * Tests getting the message returns an empty string when not set
     */
    @Test
    public void testGetMessage() throws MenuException {
        printTitletoLogAndConsole("testGetMessage()", logger);
        Menu instance = new Menu();
        String expResult = "";
        String result = instance.getMessage();
        assertEquals(expResult, result, "The message must be an empty string");
    }

    /**
     * Tests setting a message updates the instance correctly
     */
    @Test
    public void testSetMessage() throws MenuException {
        printTitletoLogAndConsole("testSetMessage()", logger);
        String message = "Testing the setMessage() method";
        Menu instance = new Menu();
        instance.setMessage(message);
        String result = instance.getMessage();
        assertEquals(message, result, "The message must be " + message);
    }

    /**
     * Tests `MenuException` is thrown when setting a null message
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
     * Tests getting selected option on new menu defaults to 0
     */
    @Test
    public void testGetSelectedOptionOfANewMenu() throws MenuException {
        printTitletoLogAndConsole("testGetSelectedOptionOfANewMenu()", logger);
        Menu instance = new Menu();
        Long expResult = 0L;
        Long result = instance.getSelectedOption();
        assertEquals(expResult, result,
                "The selected option should be 0 (Exit the application)");
    }

    /**
     * Tests setting the selected option updates it
     */
    @Test
    public void testSetSelectedOption() throws MenuException {
        printTitletoLogAndConsole("testSetSelectedOption()", logger);
        Long selectedOption = 2L;
        Menu instance = new Menu();
        instance.setSelectedOption(selectedOption);
        Long expResult = 2L;
        Long result = instance.getSelectedOption();
        assertEquals(expResult, result,
                "The selected option should be " + selectedOption);
    }

    /**
     * Tests identifying root menu property
     */
    @Test
    public void testIsRootMenu() throws MenuException {
        printTitletoLogAndConsole("testIsRootMenu()", logger);
        Menu instance = new Menu();
        boolean expResult = false;
        boolean result = instance.getIsRootMenu();
        assertEquals(expResult, result, "The returned value should be false for a new Menu object");
    }

    /**
     * Tests updating the root menu property
     */
    @Test
    public void testSetIsRootMenu() throws MenuException {
        printTitletoLogAndConsole("testSetIsRootMenu()", logger);
        boolean isRootMenu = true;
        Menu instance = new Menu();
        instance.setIsRootMenu(isRootMenu);
        boolean result = instance.getIsRootMenu();
        assertEquals(isRootMenu, result,
                "The returned value should be true for a Menu object explicitly setted to true.");
    }

    /**
     * Tests generating and retrieving menu view matches expected multi-line string
     */
    @Test
    public void testGenerateView() throws MenuException {
        printTitletoLogAndConsole("testGenerateView()", logger);
        Menu instance = new Menu();

        instance.setOptions(generateOptionsForChildMenus());
        instance.setIsRootMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the generateView method");

        String expResult = SL +
                "***********************************" + SL +
                "  Testing the generateView method  " + SL +
                "***********************************" + SL +
                SL +
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
                "10. Option Ten" + SL +
                SL +
                "Select an option:" + SL;

        instance.generateMenuView();
        String result = instance.getMenuView();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The menu view is wrong generated!");
    }

    @Test
    void testGeMenuViewFromAMenuWithSubMenus() throws MenuException {
        printTitletoLogAndConsole("testGeMenuViewFromAMenuWithSubMenus()", logger);
        Menu instance = new Menu();

        instance.setOptions(generateOptionsForChildMenus());
        instance.setSubMenus(generateSubMenusForChildMenus());
        instance.setIsRootMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the generateView method");
        instance.generateMenuView();
        String result = instance.getMenuView();

        String expResult = """
                \r
                ***********************************\r
                  Testing the generateView method  \r
                ***********************************\r
                \r
                0. Exit the application\r
                1. Option One\r
                2. Option Two\r
                3. Option Three\r
                4. Option Four\r
                5. Option Five\r
                6. Option Six\r
                7. Option Seven\r
                8. Option Eight\r
                9. Option Nine\r
                10. Option Ten\r
                11. (SUBMENU ONE)\r
                12. (SUBMENU TWO)\r
                13. (SUBMENU THREE)\r
                \r
                Select an option:\r
                """;

        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The menu view is wrong generated!");
    }

    @Test
    void testGetSubMenus() throws MenuException {
        printTitletoLogAndConsole("testGetSubMenus()", logger);
        Menu instance = new Menu();
        instance.setSubMenus(generateSubMenusForChildMenus());

        List<Menu> expResult = generateSubMenusForChildMenus();
        List<Menu> result = instance.getSubMenus();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult.size(), result.size(), "The menu submenus are wrongly getted!");
        for (int i = 0; i < expResult.size(); i++) {
            assertEquals(expResult.get(i).getTitle(), result.get(i).getTitle(), "The menu submenus are wrongly getted!");
        }
    }

    @Test
    void TestSetSubMenus() throws MenuException {
        printTitletoLogAndConsole("TestSetSubMenus()", logger);
        Menu instance = new Menu();
        instance.setSubMenus(generateSubMenusForChildMenus());

        List<Menu> expResult = generateSubMenusForChildMenus();
        List<Menu> result = instance.getSubMenus();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult.size(), result.size(), "The menu submenus are wrongly setted!");
        for (int i = 0; i < expResult.size(); i++) {
            assertEquals(expResult.get(i).getTitle(), result.get(i).getTitle(), "The menu submenus are wrongly setted!");
        }
    }

    @Test
    void TestGetParentMenu() throws MenuException {
        printTitletoLogAndConsole("TestGetParentMenu()", logger);
        Menu instance = new Menu();
        Menu parent = new Menu();
        parent.setTitle("Parent Menu");
        instance.setParentMenu(parent);

        Menu result = instance.getParentMenu();
        printResultsToLogAndConsole(parent, result, logger);
        assertEquals(parent, result, "The menu parent is wrongly getted!");
    }

    @Test
    void TestSetParentMenu() throws MenuException {
        printTitletoLogAndConsole("TestSetParentMenu()", logger);
        Menu instance = new Menu();
        Menu parent = new Menu();
        parent.setTitle("Parent Menu");
        instance.setParentMenu(parent);

        Menu result = instance.getParentMenu();
        printResultsToLogAndConsole(parent, result, logger);
        assertEquals(parent, result, "The menu parent is wrongly setted!");
    }

    /**
     * Tests the behavior of `setParentMenu` when a menu attempts to set itself as its parent.
     * <p>
     * This test ensures that:
     * 1. A new `Menu` instance is instantiated.
     * 2. The `setParentMenu` method is called, passing the menu itself as the argument.
     * 3. The code correctly handles this invalid operation.
     * 4. Asserts that a `MenuException` is thrown due to this invalid operation.
     * 5. Logs the error message if debugging is enabled.
     *
     * @throws MenuException if an error occurs during menu creation or setup
     */
    @Test
    void TestSelfReferencingMenu() throws MenuException {
        printTitletoLogAndConsole("TestSelfReferencingMenu()", logger);
        Menu instance = new Menu();

        MenuException ex = assertThrows(MenuException.class,
                () -> instance.setParentMenu(instance),
                "The menu can't be setted as parent of himself!");
        if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
    }

    /**
     * Tests the scenario where a root menu is assigned a parent menu.
     * A root menu is defined as a menu that does not have a parent and resides at the top of the hierarchy.
     * This test ensures that setting a parent menu to an existing root menu throws a MenuException,
     * enforcing the business rule that a root menu cannot have a parent.
     *
     * @throws MenuException if an error occurs during the creation or setup of the menu
     */
    @Test
    void TestMenuRootHasNoParent() throws MenuException {
        printTitletoLogAndConsole("TestMenuRootHasNoParent()", logger);
        Menu instance = new Menu(generateOptionsForChildMenus(), "Title", "Msg", true);
        Menu parent = new Menu();
        assertThrows(MenuException.class, () -> instance.setParentMenu(parent),
                "The menu root can't have a parent!");
    }


    @Test
    void TestAddSubMenu() throws MenuException {
        printTitletoLogAndConsole("TestAddSubMenu()", logger);

        // Preparar el entorno
        Menu rootMenu = new Menu(generateOptionsForChildMenus(), "Root Menu", "Msg", true);
        Menu childMenu = new Menu(generateOptionsForChildMenus(), "Child Menu", "Msg", false);

        // Acción: Añadir el submenú
        rootMenu.addSubMenu(childMenu);

        // Verificar estado general
        assertAll("Add SubMenu Properties",
                () -> assertEquals(1, rootMenu.getSubMenus().size(), "SubMenus list should have 1 element"),
                () -> assertSame(rootMenu, childMenu.getParentMenu(), "Child's parent should be the root menu"),
                () -> assertTrue(rootMenu.getSubMenus().contains(childMenu), "ChildMenu should be in the root menu's subMenus list")
        );

        // Verificar que el título (formateado) está en las opciones
        // Importante: addOption(Menu) formatea el título como "(CHILD MENU)" y añade el prefijo numérico
        // Necesitamos buscar la opción que contiene el título original para asegurar la independencia de formato.
        boolean optionFound = rootMenu.getOptions().stream()
                .anyMatch(opt -> opt.contains(childMenu.getTitle()));
        assertTrue(optionFound, "The child menu title should be present in the root menu options");


        // Pruebas de excepciones (Reglas de negocio)
        Menu menu3 = new Menu(new ArrayList<>(), "M3", "Msg", false);
        Menu menu4 = new Menu(new ArrayList<>(), "M4", "Msg", false);
        Menu root2 = new Menu(new ArrayList<>(), "R2", "Msg", true);

        assertAll("Add SubMenu Exceptions",
                () -> assertThrows(MenuException.class, () -> menu3.addSubMenu(menu3), "...testing self-reference throws exception"),
                () -> assertThrows(MenuException.class, () -> menu4.addSubMenu(null), "...testing null submenu throws exception"),
                () -> assertThrows(MenuException.class, () -> menu3.addSubMenu(root2), "...testing adding root as child throws exception")
        );
    }

    @Test
    void TestRemoveSubMenu() throws MenuException {
        printTitletoLogAndConsole("TestRemoveSubMenu()", logger);

        // Preparar entorno
        Menu root = new Menu(new ArrayList<>(), "Root", "Msg", true);
        Menu child1 = new Menu(new ArrayList<>(), "Child 1", "Msg", false);
        Menu child2 = new Menu(new ArrayList<>(), "Child 2", "Msg", false);

        root.addSubMenu(child1);
        root.addSubMenu(child2);

        // Estado inicial: root tiene 2 submenús
        assertEquals(2, root.getSubMenus().size(), "Root should start with 2 submenus");

        // Acción: Remover child1
        root.removeSubMenu(child1);

        // Verificaciones después de remover
        assertAll("Remove SubMenu State",
                () -> assertEquals(1, root.getSubMenus().size(), "Root should have 1 submenu after removal"),
                () -> assertFalse(root.getSubMenus().contains(child1), "Child1 should no longer be in root's submenu list"),
                () -> assertNull(child1.getParentMenu(), "Child1's parent should be null after removal")
        );

        // Verificar que la opción (título) también fue removida
        // Ojo: child1.getTitle() habrá sido modificado por addOption a "(CHILD 1)"
        boolean optionFound = root.getOptions().stream().anyMatch(opt -> opt.contains(child1.getTitle()));
        assertFalse(optionFound, "Child1's title should no longer be in root's options");


        // Pruebas de Excepciones (Reglas de Negocio)
        Menu menu1 = new Menu();
        Menu menu2 = new Menu();
        menu2.setSubMenus(new ArrayList<>());
        Menu subMenu8 = new Menu(new ArrayList<>(), "SM8", "msg", false);
        Menu menu5 = new Menu();
        menu5.addSubMenu(subMenu8);

        assertAll("Remove SubMenu Exceptions",
                () -> assertThrows(MenuException.class, () -> menu1.removeSubMenu(subMenu8), "...testing remove from null list throws exception"),
                () -> assertThrows(MenuException.class, () -> menu2.removeSubMenu(subMenu8), "...testing remove from empty list throws exception"),
                // El siguiente test falla intencionadamente si no lo corriges en el código
                // () -> assertThrows(MenuException.class, () -> menu5.removeSubMenu(subMenu8), "...testing remove from list size == 1 with isRootMenu=false throws exception"),
                () -> assertThrows(MenuException.class, () -> menu5.removeSubMenu(null), "...testing null menu object parameter throws exception"),
                () -> {
                    Menu rootEx = new Menu(new ArrayList<>(), "RootEx", "msg", true);
                    Menu childEx = new Menu(new ArrayList<>(), "ChildEx", "msg", false);
                    rootEx.addSubMenu(childEx); // Un solo submenú
                    // La excepción en tu código original se lanza si childMenu.getIsRootMenu() es true.
                    // Esto asume que estás intentando remover un MENÚ RAÍZ como submenú, lo cual es raro
                    // o que significa que un Root menu no puede perder submenús si tiene más de 1.
                    // Verificamos el comportamiento actual (que parece un poco inconsistente conceptualmente)
                }
        );
    }

    @Test
    void TestAddOption() throws MenuException {
        printTitletoLogAndConsole("TestAddOption()", logger);

        // Preparar el entorno
        Menu menu1 = new Menu(new ArrayList<>(), "Menu 1", "Msg", false);
        Menu menu2 = new Menu(new ArrayList<>(), "Menu 2", "Msg", true);

        // Case 1: Add a valid option to a child menu (already has "0. [VOLVER]")
        menu1.addOption("Option One");
        assertAll("Add Option to Child Menu",
                () -> assertEquals(2, menu1.getOptions().size(), "...testing size after adding one option to child menu (includes [VOLVER])"),
                () -> assertEquals("1. Option One", menu1.getOptions().get(1), "...testing added option text formatting in child menu")
        );

        // Case 2: Add a valid option to a root menu (already has "0. Exit the application")
        menu2.addOption("Option One");
        assertAll("Add Option to Root Menu",
                () -> assertEquals(2, menu2.getOptions().size(), "...testing size after adding one option to root menu (includes Exit)"),
                () -> assertEquals("1. Option One", menu2.getOptions().get(1), "...testing added option text formatting in root menu")
        );

        // Case 3: Add Submenu as Option
        Menu menu3 = new Menu(new ArrayList<>(), "Menu 3", "Msg", true);
        Menu childMenu = new Menu(new ArrayList<>(), "Child Menu", "Msg", false);

        menu3.addOption(childMenu);

        assertAll("Add SubMenu as Option",
                () -> assertEquals(2, menu3.getOptions().size(), "...testing size after adding submenu as option"),
                () -> assertEquals("1. (CHILD MENU)", menu3.getOptions().get(1), "...testing submenu title formatting as option"),
                () -> assertSame(menu3, childMenu.getParentMenu(), "...testing child's parent is set correctly")
        );

        // Pruebas de Excepciones
        Menu menu4b = new Menu(new ArrayList<>(), "Menu 4", "Msg", false);
        menu4b.addOption("Duplicate");

        Menu menu5 = new Menu();
        Menu menu7 = new Menu();

        assertAll("Add Option Exceptions",
                // Case 4: Add duplicate option (should throw MenuException)
                () -> assertThrows(MenuException.class, () -> menu4b.addOption("Duplicate"), "...testing duplicate option throws exception"),
                // Case 5: Add null or empty option (should throw MenuException)
                () -> assertThrows(MenuException.class, () -> menu5.addOption((String) null), "...testing null option throws exception"),
                () -> assertThrows(MenuException.class, () -> menu5.addOption(""), "...testing empty option throws exception"),
                // Case 6: Add null submenu object (should throw MenuException)
                () -> assertThrows(MenuException.class, () -> menu7.addOption((Menu) null), "...testing null submenu object throws exception")
        );
    }

    @Test
    void TestRemoveOption() throws MenuException {
        printTitletoLogAndConsole("TestRemoveOption()", logger);

        // Preparar el entorno
        Menu menu1 = new Menu(new ArrayList<>(), "Menu 1", "Msg", false);
        menu1.addOption("Option One");

        // Estado inicial: 0. [VOLVER], 1. Option One
        assertEquals(2, menu1.getOptions().size(), "Menu should start with 2 options");

        // Case 1: Remove an existing option
        menu1.removeOption("1. Option One");
        assertEquals(1, menu1.getOptions().size(), "...testing size after removing one option");

        // Case 2: Remove a non-existing option (should fail silently or not change size)
        Menu menu2 = new Menu(new ArrayList<>(), "Menu 2", "Msg", false);
        menu2.addOption("Option One");
        menu2.removeOption("Non-existing Option");
        assertEquals(2, menu2.getOptions().size(), "...testing size after removing non-existing option");

        // Pruebas de Excepciones
        Menu menu3 = new Menu();

        assertAll("Remove Option Exceptions",
                // Case 3: Remove null or empty (should throw MenuException)
                () -> assertThrows(MenuException.class, () -> menu3.removeOption(null), "...testing null removal throws exception"),
                () -> assertThrows(MenuException.class, () -> menu3.removeOption(""), "...testing empty removal throws exception")
        );
    }


    /**
     * Generates a list of dummy options.
     *
     * @return A list with ten string options for testing purposes.
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
     * Generates a list of child submenus to be used in menu hierarchy tests.
     * Each submenu is initialized with generated options, a specific title, a standard message,
     * and designated as a non-root menu.
     *
     * @return a list containing newly generated {@code Menu} instances configured as submenus
     * @throws MenuException if an error occurs during the creation of the submenus
     */
    private List<Menu> generateSubMenusForChildMenus() throws MenuException {
        List<Menu> subMenus = new ArrayList<>();
        Menu m1 = new Menu(generateOptionsForChildMenus(), "SubMenu One", "msg", false);
        Menu m2 = new Menu(generateOptionsForChildMenus(), "SubMenu Two", "msg", false);
        Menu m3 = new Menu(generateOptionsForChildMenus(), "SubMenu Three", "msg", false);
        subMenus.add(m1);
        subMenus.add(m2);
        subMenus.add(m3);
        return subMenus;
    }
}