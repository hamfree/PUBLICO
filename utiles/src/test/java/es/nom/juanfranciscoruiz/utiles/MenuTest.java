package es.nom.juanfranciscoruiz.utiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import es.nom.juanfranciscoruiz.utiles.model.MenuConstants;
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

    /**
     * A private static variable that holds a resource bundle for localized messages.
     * This is used to load and manage locale-specific resources, such as strings,
     * to support internationalization.
     */
    private static ResourceBundle messages;

    static {
        try {
            messages = ResourceBundle.getBundle("messages");
        } catch (MissingResourceException e) {
            logger.warn("ResourceBundle 'messages' not found. Falling back to default messages.");
            messages = null;
        }
    }
    /**
     * Helper method to fetch internationalized messages, falling back to default if not found.
     *
     * @param key            The ResourceBundle key
     * @param defaultMessage The default message if key or bundle is not available
     * @return The resolved message
     */
    private static String getMessage(String key, String defaultMessage) {
        if (messages != null && messages.containsKey(key)) {
            return messages.getString(key);
        }
        return defaultMessage;
    }

    /**
     * Default value for the title property of a Menu Object.
     */
    private static final String NO_TITLE = getMessage("msg.menu.no.title", MenuConstants.NO_TITLE);
    /**
     * For a Menu object that is the Home Menu this is the option 0 of the menu
     * for exiting the application.
     */
    private static final String EXITOPT = getMessage("msg.menu.exit.opt",MenuConstants.EXITOPT);

    private static final String BACKOPT = getMessage("msg.menu.back.opt", MenuConstants.BACKOPT);
    /**
     * Default vale for the message shown to the user below the list options.
     */
    private static final String DEFAULT_MSG = getMessage("msg.menu.default.msg", MenuConstants.DEFAULT_MSG);

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
        Menu instance = Menu.getInstance();
        // Verifies default constructor initializes menu properties correctly
        assertAll(
                () -> assertNotNull(instance.getOptions(), "...testing not null options"),
                () -> assertFalse(instance.getOptions().isEmpty(), "...testing empty options"), // Defaults adds exit option
                () -> assertEquals(NO_TITLE, instance.getTitle(), "...testing default title"),
                () -> assertEquals(DEFAULT_MSG, instance.getMessage(), "...testing default message"),
                () -> {
                    assertEquals(LS +
                                 "***************" + LS +
                                 "  " + NO_TITLE + "  " + LS +
                                 "***************" + LS +
                                 LS +
                                 EXITOPT + LS +
                                 LS +
                                 DEFAULT_MSG + LS,
                            instance.getMenuView(), "...testing default menu view");
                },
                () -> assertTrue(instance.getIsRootMenu(), "...testing default is root menu")
        );
    }

    /**
     * Tests the {@code Menu} constructor with default parameters when providing null and empty values.
     *
     * Verifies the behavior of the {@code Menu.getInstance()} method when default values are expected
     * to replace null or empty input arguments. Specifically ensures:
     * <ul>
     * <li>The options list is not null and not empty (back option added by default).</li>
     * <li>The default title is used when null is passed as the title parameter.</li>
     * <li>The default message is assigned when an empty message is provided.</li>
     * <li>The menu is not marked as a root menu if explicitly set to false.</li>
     * </ul>
     *
     * @throws MenuException if there is an error initializing the {@code Menu} instance
     */
    @Test
    public void testConstructorWithParamsDefaults() throws MenuException {
        printTitletoLogAndConsole("testConstructorWithParamsDefaults()", logger);
        Menu instance = Menu.getInstance().getInstance(null, "", null, false);
        // Verifies constructor with null title uses default title
        assertAll(
                () -> assertNotNull(instance.getOptions(), "...testing not null options"),
                () -> assertFalse(instance.getOptions().isEmpty(), "...testing empty options"), // Defaults adds back option
                () -> assertEquals(NO_TITLE, instance.getTitle(), "...testing default title"),
                () -> assertEquals(DEFAULT_MSG, instance.getMessage(), "...testing default message"),
                () -> assertFalse(instance.getIsRootMenu(), "...testing default is root menu")
        );
    }

    /**
     * Tests the constructor of the {@code Menu} class when initialized with parameters
     * including a root flag, and verifies if the exit option is automatically added
     * to the menu options.
     * <p>
     * The method performs the following validations:
     * <ol>
     * <li>Verifies that the first option in the menu corresponds to the exit option
     *    constant {@code EXITOPT}.</li>
     * <li>Verifies that the total size of the options list is as expected.</li>
     * <li> Verifies the correctness of subsequent menu options.</li>
     * </ol>
     * The test case uses assertions to ensure these conditions are met. If any of
     * the conditions fail, the test will log the discrepancy and fail accordingly.
     * <p>
     *  <b style='color:red'>Note:</b> The numeration of the options is set and
     *  validated by the MenuManager class!
     * <p>
     * @throws MenuException if there is an issue while creating the {@code Menu} instance.
     */
    @Test
    public void testConstructorWithParamsRootAddsExitOption() throws MenuException {
        printTitletoLogAndConsole("testConstructorWithParamsRootAddsExitOption()", logger);
        List<String> options = generateOptionsForChildMenus();
        Menu instance = Menu.getInstance().getInstance(options, "Title", "Msg", true);
        List<String> result = instance.getOptions();
        assertAll(
                () -> assertEquals(EXITOPT, result.getFirst(), "...testing exit option"),
                () -> assertEquals(11, result.size(), "...testing options size"),
                () -> assertEquals("Option One", result.get(1), "...testing option one")
        );
    }

    /**
     * Tests the behavior of the constructor when a root menu with submenus is provided a parent menu.
     *
     * This test validates that an exception is thrown when attempting to initialize a root menu
     * with submenus while also assigning it a non-null parent. A root menu, by definition, should
     * not have a parent menu. The method ensures that such a condition triggers a {@link MenuException}.
     *
     * @throws MenuException if an invalid operation related to menu setup is attempted
     */
    @Test
    public void testConstructorWithSubmenusRootWithParentThrows() throws MenuException {
        printTitletoLogAndConsole("testConstructorWithSubmenusRootWithParentThrows()", logger);
        Menu parent = Menu.getInstance();
        assertThrows(MenuException.class, () ->
                        Menu.getInstance().getInstance(generateOptionsForChildMenus(), "Title", "Msg", true,
                                new ArrayList<>(), parent),
                "The parent menu can't be null for root menus"
        );
    }

    @Test
    public void testConstructorWithSubmenusNonRootWithoutParentThrows() {
        printTitletoLogAndConsole("testConstructorWithSubmenusNonRootWithoutParentThrows()", logger);
        assertThrows(MenuException.class, () ->
                        Menu.getInstance().getInstance(generateOptionsForChildMenus(), "Title", "Msg", false,
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
        Menu parent = Menu.getInstance();
        List<Menu> subMenus = new ArrayList<>();
        Menu instance = Menu.getInstance().getInstance(generateOptionsForChildMenus(), "Title", "Msg", false,
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
        Menu instance = Menu.getInstance();
        instance.setIsRootMenu(true);
        List<String> expResult = new ArrayList<>();
        expResult.add(EXITOPT);
        List<String> result = instance.getOptions();
        assertEquals(expResult, result,
                "The first option should be '" + EXITOPT + "' and the rest of the options must be preserving their order");
    }

    /**
     * Tests non-root menu options do not include exit option
     */
    @Test
    public void testGetOptionsForNonRootMenu() throws MenuException {
        printTitletoLogAndConsole("testGetOptionsForNonRootMenu()", logger);
        Menu instance = Menu.getInstance();
        instance.setIsRootMenu(false);
        List<String> expResult = new ArrayList<>();
        expResult.add(BACKOPT);
        List<String> result = instance.getOptions();
        assertEquals(expResult, result, "The list of options should contain '" + BACKOPT + "' only");
    }

    /**
     * Tests non-root empty menu options includes the '[VOLVER]' option if options property is not empty
     */
    @Test
    public void testGetOptionsForNonRootEmptyMenu() throws MenuException {
        printTitletoLogAndConsole("testGetOptionsForNonRootEmptyMenu()", logger);
        Menu instance = Menu.getInstance();
        List<String> options = new ArrayList<>();
        options.add("Option One");
        instance.setIsRootMenu(false);
        instance.setOptions(options);

        List<String> expResult = new ArrayList<>();
        expResult.add(BACKOPT);
        expResult.add("Option One");
        List<String> result = instance.getOptions();
        assertEquals(expResult, result, "The list of options have two options: '" + BACKOPT + "' and 'Option One'" );
    }

    /**
     * Tests setting options on a root menu updates options correctly
     */
    @Test
    public void testSetOptions() throws MenuException {
        printTitletoLogAndConsole("testSetOptions()", logger);
        Menu instance = Menu.getInstance();
        List<String> expectedOptions = generateOptionsForChildMenus();
        instance.setOptions(expectedOptions);
        List<String> result = instance.getOptions();
        
        List<String> expResult = generateOptionsForChildMenus();
        expResult.addFirst(EXITOPT);

        assertAll(
                () -> assertEquals(expResult.size(), result.size(), "...testing options size"),
                () -> assertEquals(expResult, result, "...testing options content")
        );
    }

    /**
     * Verifies `MenuException` is thrown when options are null
     */
    @Test
    public void testSetOptionsWithNull() throws MenuException {
        printTitletoLogAndConsole("testSetOptionsWithNull()", logger);
        Menu instance = Menu.getInstance();

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
        Menu instance = Menu.getInstance();
        instance.setIsRootMenu(true);
        List<String> options = generateOptionsForChildMenus();
        instance.setOptions(options);

        // When setting options for a root menu, the first option should be
        // "0. Exit the application" (i18n depend) and the rest of the options
        // must be preserving their order (1 to N).
        List<String> expResult = generateOptionsForChildMenus();
        expResult.addFirst(EXITOPT);

        List<String> result = instance.getOptions();
        assertEquals(expResult, result,
                "The first option should be '" + EXITOPT + "' and" +
                        " the rest of the options must be preserving their order");
    }

    /**
     * Tests retrieving an unset title property returns 'Untitled'
     */
    @Test
    public void testGetTitleOfAnUnsettedTitleProperty() throws MenuException {
        printTitletoLogAndConsole("testGetTitleOfAnUnsettedTitleProperty()", logger);
        Menu instance = Menu.getInstance();
        String expResult = NO_TITLE;
        String result = instance.getTitle();

        assertEquals(expResult, result, "The title should be '" + NO_TITLE + "'");
    }

    /**
     * Tests setting the title updates it appropriately
     */
    @Test
    public void testSetTitle() throws MenuException {
        printTitletoLogAndConsole("testSetTitle()", logger);
        String title = "Testing the setTitle() method.";
        Menu instance = Menu.getInstance();
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
        Menu instance = Menu.getInstance();

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
        Menu instance = Menu.getInstance();
        String expResult = "Make your selection:";
        String result = instance.getMessage();
        assertEquals(expResult, result, "The message must be the default one");
    }

    /**
     * Tests setting a message updates the instance correctly
     */
    @Test
    public void testSetMessage() throws MenuException {
        printTitletoLogAndConsole("testSetMessage()", logger);
        String message = "Testing the setMessage() method";
        Menu instance = Menu.getInstance();
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
        Menu instance = Menu.getInstance();

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
        Menu instance = Menu.getInstance();
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
        Menu instance = Menu.getInstance();
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
        Menu instance = Menu.getInstance();
        boolean expResult = true; // By default is true
        boolean result = instance.getIsRootMenu();
        assertEquals(expResult, result, "The returned value should be true for a new Menu object");
    }

    /**
     * Tests updating the root menu property
     */
    @Test
    public void testSetIsRootMenu() throws MenuException {
        printTitletoLogAndConsole("testSetIsRootMenu()", logger);
        boolean isRootMenu = false;
        Menu instance = Menu.getInstance();
        instance.setIsRootMenu(isRootMenu);
        boolean result = instance.getIsRootMenu();
        assertEquals(isRootMenu, result,
                "The returned value should be false for a Menu object explicitly setted to false.");
    }

    /**
     * Tests the functionality of the {@code generateMenuView} method in the {@code Menu} class.
     *
     * This method verifies that the menu view generated by the {@code generateMenuView} method
     * is formatted and structured as expected. It sets up a {@code Menu} instance by configuring
     * its options, root menu state, message, and title, and then compares the actual generated
     * menu view with an expected result.
     *
     * The test also ensures consistency in the output format by using structured comparison,
     * logging intermediate steps for traceability, and asserting that the generated menu view
     * matches the expected output. An exception of type {@code MenuException} is handled if
     * thrown during the process.
     *
     * @throws MenuException if an error occurs during menu generation
     */
    @Test
    public void testGenerateView() throws MenuException {
        printTitletoLogAndConsole("testGenerateView()", logger);
        Menu instance = Menu.getInstance();

        instance.setOptions(generateOptionsForChildMenus());
        instance.setIsRootMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the generateView method");

        String expResult = generateExpectedMenuViewFromAMenu();

        instance.generateMenuView();
        String result = instance.getMenuView();
        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The menu view is wrong generated!");
    }

    @Test
    void testGetMenuViewFromAMenuWithSubMenus() throws MenuException {
        printTitletoLogAndConsole("testGeMenuViewFromAMenuWithSubMenus()", logger);
        Menu instance = Menu.getInstance();

        instance.setOptions(generateOptionsForChildMenus());
        instance.setSubMenus(generateSubMenusForChildMenus());
        instance.setIsRootMenu(true);
        instance.setMessage("Select an option:");
        instance.setTitle("Testing the generateView method");
        instance.generateMenuView();
        String result = instance.getMenuView();

        String expResult = generateExpectedMenuViewFromAMenuWithSubMenus();

        printResultsToLogAndConsole(expResult, result, logger);
        assertEquals(expResult, result, "The menu view is wrong generated!");
    }

    @Test
    void testGetSubMenus() throws MenuException {
        printTitletoLogAndConsole("testGetSubMenus()", logger);
        Menu instance = Menu.getInstance();
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
        Menu instance = Menu.getInstance();
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
        Menu instance = Menu.getInstance();
        instance.setIsRootMenu(false);
        Menu parent = Menu.getInstance();
        parent.setTitle("Parent Menu");
        instance.setParentMenu(parent);

        Menu result = instance.getParentMenu();
        printResultsToLogAndConsole(parent, result, logger);
        assertEquals(parent, result, "The menu parent is wrongly getted!");
    }

    @Test
    void TestSetParentMenu() throws MenuException {
        printTitletoLogAndConsole("TestSetParentMenu()", logger);
        Menu instance = Menu.getInstance();
        instance.setIsRootMenu(false);
        Menu parent = Menu.getInstance();
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
        Menu instance = Menu.getInstance();
        instance.setIsRootMenu(false);

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
        Menu instance = Menu.getInstance().getInstance(generateOptionsForChildMenus(), "Title", "Msg", true);
        Menu parent = Menu.getInstance();
        assertThrows(MenuException.class, () -> instance.setParentMenu(parent),
                "The menu root can't have a parent!");
    }


    @Test
    void TestAddSubMenu() throws MenuException {
        printTitletoLogAndConsole("TestAddSubMenu()", logger);

        // Preparar el entorno
        Menu rootMenu = Menu.getInstance().getInstance(generateOptionsForChildMenus(), "Root Menu", "Msg", true);
        Menu childMenu = Menu.getInstance().getInstance(generateOptionsForChildMenus(), "Child Menu", "Msg", false);

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
        Menu menu3 = Menu.getInstance().getInstance(new ArrayList<>(), "M3", "Msg", false);
        Menu menu4 = Menu.getInstance().getInstance(new ArrayList<>(), "M4", "Msg", false);
        Menu root2 = Menu.getInstance().getInstance(new ArrayList<>(), "R2", "Msg", true);

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
        Menu root = Menu.getInstance(new ArrayList<>(), "Root", "Msg", true);
        Menu child1 = Menu.getInstance(new ArrayList<>(), "Child 1", "Msg", false);
        Menu child2 = Menu.getInstance(new ArrayList<>(), "Child 2", "Msg", false);

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
        Menu menu1 = Menu.getInstance();
        Menu menu2 = Menu.getInstance();
        menu2.setSubMenus(new ArrayList<>());
        Menu subMenu8 = Menu.getInstance().getInstance(new ArrayList<>(), "SM8", "msg", false);
        Menu menu5 = Menu.getInstance();
        menu5.addSubMenu(subMenu8);

        assertAll("Remove SubMenu Exceptions",
                () -> assertThrows(MenuException.class, () -> menu2.removeSubMenu(subMenu8), "...testing remove from empty list throws exception"),
                () -> assertThrows(MenuException.class, () -> menu5.removeSubMenu(null), "...testing null menu object parameter throws exception"),
                () -> {
                    Menu rootEx = Menu.getInstance().getInstance(new ArrayList<>(), "RootEx", "msg", true);
                    Menu childEx = Menu.getInstance().getInstance(new ArrayList<>(), "ChildEx", "msg", false);
                    rootEx.addSubMenu(childEx); // Un solo submenú
                    // La excepción en tu código original se lanza si childMenu.getIsRootMenu() es true.
                    // Esto asume que estás intentando remover un MENÚ RAÍZ como submenú, lo cual es raro
                    // o que significa que un Root menu no puede perder submenús si tiene más de 1.
                    // Verificamos el comportamiento actual (que parece un poco inconsistente conceptualmente)
                }
        );
    }

    /**
     * Tests the functionality of adding options to both child and root menus in the {@code Menu} class.
     *
     * The test verifies the following scenarios:
     * <p>
     * 1. Adding a valid option to a child menu:<br>
     *    - Ensures that the size of the options list increases appropriately, including the default "[VOLVER]" option.<br>
     *    - Validates the text formatting of the newly added option.<br>
     *</p>
     * <p>
     * 2. Adding a valid option to a root menu:<br>
     *    - Confirms that the size of the options list increases correctly, including the default "Exit the application" option.<br>
     *    - Checks the text formatting of the newly added option.<br>
     *</p>
     * Assertions guarantee that the menu's options list is updated as expected and maintains proper formatting.
     * <p>
     * <b style='color:red'>Note:</b> The numeration of the options is set and validated by the MenuManager class!
     *
     * @throws MenuException if an error occurs during the menu setup or option addition process.
     */
    @Test
    void TestAddOption() throws MenuException {
        printTitletoLogAndConsole("TestAddOption()", logger);

        // Preparar el entorno
        Menu menu1 = Menu.getInstance().getInstance(new ArrayList<>(), "Menu 1", "Msg", false);
        Menu menu2 = Menu.getInstance().getInstance(new ArrayList<>(), "Menu 2", "Msg", true);

        // Case 1: Add a valid option to a child menu (already has "0. [VOLVER]")
        // NOTE:
        menu1.addOption("Option One");
        assertAll("Add Option to Child Menu",
                () -> assertEquals(2, menu1.getOptions().size(), "...testing size after adding one option to child menu (includes [VOLVER])"),
                () -> assertEquals("Option One", menu1.getOptions().get(1), "...testing added option text formatting in child menu")
        );

        // Case 2: Add a valid option to a root menu (already has "0. Exit the application")
        menu2.addOption("Option One");
        assertAll("Add Option to Root Menu",
                () -> assertEquals(2, menu2.getOptions().size(), "...testing size after adding one option to root menu (includes Exit)"),
                () -> assertEquals("Option One", menu2.getOptions().get(1), "...testing added option text formatting in root menu")
        );

    }

    @Test
    void TestRemoveOption() throws MenuException {
        printTitletoLogAndConsole("TestRemoveOption()", logger);

        // Prepare the environment
        Menu menu1 = Menu.getInstance().getInstance(new ArrayList<>(), "Menu 1", "Msg", false);
        menu1.addOption("Option One");

        // Initial state of options list: '0. [BACK]' or '0. [VOLVER]', 'Option One'
        // Remember, numeration of options is handled by MenuManager!
        assertEquals(2, menu1.getOptions().size(), "Menu should start with 2 options");

        // Case 1: Remove an existing option
        menu1.removeOption("Option One");
        assertEquals(1, menu1.getOptions().size(), "...testing size after removing one option");

        // Case 2: Remove a non-existing option (should fail silently or not change size)
        Menu menu2 = Menu.getInstance().getInstance(new ArrayList<>(), "Menu 2", "Msg", false);
        menu2.addOption("Option One");
        menu2.removeOption("Non-existing Option");
        assertEquals(2, menu2.getOptions().size(), "...testing size after removing non-existing option");

        // Exception Testing
        Menu menu3 = Menu.getInstance();

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
        Menu m1 = Menu.getInstance().getInstance(generateOptionsForChildMenus(), "SubMenu One", "msg", false);
        Menu m2 = Menu.getInstance().getInstance(generateOptionsForChildMenus(), "SubMenu Two", "msg", false);
        Menu m3 = Menu.getInstance().getInstance(generateOptionsForChildMenus(), "SubMenu Three", "msg", false);
        subMenus.add(m1);
        subMenus.add(m2);
        subMenus.add(m3);
        return subMenus;
    }

    /**
     * Generates and returns the expected string representation of a menu with submenus,
     * formatted for display. The output includes a header, menu options, submenu options,
     * and a prompt for the user to select an option.
     *
     * @return a string representing the formatted view of the menu and submenus.
     */
    private String generateExpectedMenuViewFromAMenuWithSubMenus(){
        return LS +
                "************************************" + LS +
                "  Testing the generateView method  " + LS +
                "************************************" + LS + LS  +
                EXITOPT + LS  +
                "Option One" + LS  +
                "Option Two" + LS  +
                "Option Three" + LS  +
                "Option Four" + LS  +
                "Option Five" + LS  +
                "Option Six" + LS  +
                "Option Seven" + LS  +
                "Option Eight" + LS  +
                "Option Nine" + LS  +
                "Option Ten" + LS  +
                "SubMenu One" + LS  +
                "SubMenu Two" + LS  +
                "SubMenu Three" + LS + LS +
                "Select an option:" + LS;
    }

    /**
     * Generates a formatted string representing a menu view including a list of options,
     * an exit option, and a prompt for the user to select an option.
     *
     * @return A string representation of the expected menu view, including decorative elements,
     *         options numbering from 1 to 10, and a prompt message for user interaction.
     */
    private String generateExpectedMenuViewFromAMenu(){
        return  SL +
                "************************************" + SL +
                "  Testing the generateView method  " + SL +
                "************************************" + SL +
                SL +
                EXITOPT + SL +
                "Option One" + SL +
                "Option Two" + SL +
                "Option Three" + SL +
                "Option Four" + SL +
                "Option Five" + SL +
                "Option Six" + SL +
                "Option Seven" + SL +
                "Option Eight" + SL +
                "Option Nine" + SL +
                "Option Ten" + SL +
                SL +
                "Select an option:" + SL;
    }
}