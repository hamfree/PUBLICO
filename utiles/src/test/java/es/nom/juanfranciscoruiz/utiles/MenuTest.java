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
    expResult.add("0. [VOLVER]");
    List<String> result = instance.getOptions();
    assertEquals(expResult, result, "The list of options have one option: '0. [VOLVER]");
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
    final String SL = LS;
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
  

  @Test
  void testGetMenuView() {
    
    
  }
  
  @Test
  void testGeMenuViewFromAMenuWithSubMenus() throws MenuException {
    printTitletoLogAndConsole("testGeMenuViewFromAMenuWithSubMenus()", logger);
    Menu instance = new Menu();
    List<String> options = generateOptionsForRootMenu();
    List<Menu> subMenus = generateSubMenusForChildMenus();
    
    instance.setRootMenu(true);
    instance.setTitle("Root Menu");
    instance.setOptions(options);
    instance.setSubMenus(subMenus);
    String expResult = """
            \r
            **************\r
              Root Menu  \r
            **************\r
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
            11. (SUBMENU 1)\r
            12. (SUBMENU 2)\r
            \r
            \r
            """;
    String result = instance.getMenuView();
    printResultsToLogAndConsole(expResult, result, logger);
    assertEquals(expResult, result, "The menu view should be generated correctly");
  }
  
  @Test
  void testGetSubMenus() throws MenuException {
    printTitletoLogAndConsole("testGetSubMenus()", logger);
    Menu instance = new Menu();
    List<Menu> subMenus = generateSubMenusForChildMenus();
    instance.setSubMenus(subMenus);
    List<Menu> result = instance.getSubMenus();
    printResultsToLogAndConsole(subMenus, result, logger);
    assertAll(
            () -> assertNotNull(result, "...testing subMenus is not null"),
            () -> assertEquals(2, result.size(), "...testing subMenus size"),
            () -> assertEquals("0. (SUBMENU 1)", result.getFirst().getTitle(), "...testing first submenu title"),
            () -> assertEquals("1. (SUBMENU 2)", result.get(1).getTitle(), "...testing second submenu title")
    );

  }
  
  @Test
  void TestSetSubMenus() throws MenuException {
    printTitletoLogAndConsole("TestSetSubMenus()", logger);
    Menu instance = new Menu();
    List<Menu> subMenus = generateSubMenusForChildMenus();
    instance.setSubMenus(subMenus);
    List<Menu> result = instance.getSubMenus();
    printResultsToLogAndConsole(subMenus, result, logger);
    assertAll(
            () -> assertNotNull(result, "...testing subMenus is not null"),
            () -> assertEquals(2, result.size(), "...testing subMenus size"),
            () -> assertEquals("0. (SUBMENU 1)", result.getFirst().getTitle(), "...testing first submenu title"),
            () -> assertEquals("1. (SUBMENU 2)", result.get(1).getTitle(), "...testing second submenu title")
    );
  }
  
  @Test
  void TestGetParentMenu() throws MenuException {
    printTitletoLogAndConsole("TestGetParentMenu()", logger);
    Menu parentMenu = new Menu();
    parentMenu.setTitle("Parent Menu");
    Menu instance = new Menu();
    instance.setParentMenu(parentMenu);
    Menu result = instance.getParentMenu();
    printResultsToLogAndConsole(parentMenu, result, logger);
    assertAll(
            () -> assertNotNull(result, "...testing parent menu is not null"),
            () -> assertEquals("Parent Menu", result.getTitle(), "...testing parent menu title"),
            () -> assertSame(parentMenu, result, "...testing parent menu reference is the same")
    );

  }
  
  @Test
  void TestSetParentMenu() throws MenuException {
    printTitletoLogAndConsole("TestSetParentMenu()", logger);
    Menu parentMenu = new Menu();
    parentMenu.setTitle("Parent Menu");
    Menu instance = new Menu();
    instance.setTitle("Child Menu");
    instance.setParentMenu(parentMenu);
    Menu result = instance.getParentMenu();
    printResultsToLogAndConsole(parentMenu, result, logger);
    assertAll(
            () -> assertNotNull(result, "...testing parent menu is not null"),
            () -> assertEquals("Parent Menu", result.getTitle(), "...testing parent menu title"),
            () -> assertSame(parentMenu, result, "...testing parent menu reference is the same")
    );
  }

  /**
   * Tests the behavior of the {@code setParentMenu} method by verifying that a menu cannot be set as its own parent.
   * This is an edge case designed to confirm the enforcement of circular reference restrictions within the `Menu` class.
   * <p>
   * The method performs the following steps:
   * 1. Initializes a `Menu` instance named `selfReferencingMenu`.
   * 2. Sets a title for the menu.
   * 3. Attempts to assign the menu itself as its parent using the {@code setParentMenu} method.
   * 4. Asserts that a `MenuException` is thrown due to this invalid operation.
   * <p>
   * The test also logs the exception message if logging is enabled at the debug level.
   *
   * @throws MenuException if an error occurs during menu creation or setup
   */
  @Test
  void TestSelfReferencingMenu() throws MenuException {
    printTitletoLogAndConsole("TestSelfReferencingMenu()", logger);
    Menu selfReferencingMenu = new Menu();
    selfReferencingMenu.setTitle("Self Referencing Menu");
    MenuException ex = assertThrows(MenuException.class,
            () -> selfReferencingMenu.setParentMenu(selfReferencingMenu),
            "A menu cannot be its own parent");
    if (logger.isDebugEnabled()) logger.debug("MenuException : {}", ex.getMessage());
  }

  /**
   * Verifies that the root menu does not have a parent menu assigned.
   * <p>
   * The test performs the following steps:
   * 1. Creates an instance of the `Menu` class.
   * 2. Sets the menu title to "Root Menu".
   * 3. Marks the menu as a root menu by setting the `isRootMenu` flag to `true`.
   * 4. Asserts that the `getParentMenu` method returns `null`, ensuring that a root menu cannot have a parent menu.
   * <p>
   * This test is designed to validate the structural constraints of the menu hierarchy by ensuring
   * that the root menu operates as expected with no parent menu reference.
   *
   * @throws MenuException if an error occurs during the creation or setup of the menu
   */
  @Test
  void TestMenuRootHasNoParent() throws MenuException {
    // Test case: root menu's parent must be null
    Menu rootMenu = new Menu();
    rootMenu.setTitle("Root Menu");
    rootMenu.setIsRootMenu(true);
    assertNull(rootMenu.getParentMenu(), "Root menu's parent should be null");
  }

  @Test
  void TestAddSubMenu() throws MenuException {
    printTitletoLogAndConsole("TestAddSubMenu()", logger);

    // Test case 1: Add submenu to a menu with null submenus list
    Menu menu1 = new Menu();
    menu1.setTitle("Menu 1");
    menu1.setSubMenus(null);
    Menu subMenu1 = new Menu();
    subMenu1.setTitle("SubMenu 1");
    menu1.addSubMenu(subMenu1);

    // Test case 2: Add submenu to a menu with empty submenus list
    Menu menu2 = new Menu();
    menu2.setTitle("Menu 2");
    menu2.setSubMenus(new ArrayList<>());
    Menu subMenu2 = new Menu();
    subMenu2.setTitle("SubMenu 2");
    menu2.addSubMenu(subMenu2);

    // Test case 3: Add submenu to a menu with existing submenus
    Menu menu3 = new Menu();
    menu3.setTitle("Menu 3");
    List<Menu> existingSubMenus = generateSubMenusForChildMenus();
    menu3.setSubMenus(existingSubMenus);
    Menu subMenu3 = new Menu();
    subMenu3.setTitle("SubMenu 3");
    int originalSize = menu3.getSubMenus().size();
    menu3.addSubMenu(subMenu3);

    // Test case 4: Attempt to add null submenu
    Menu menu4 = new Menu();
    menu4.setTitle("Menu 4");

    // Test case 5: Verify parent reference is set correctly
    Menu menu5 = new Menu();
    menu5.setTitle("Menu 5");
    Menu subMenu5 = new Menu();
    subMenu5.setTitle("SubMenu 5");
    menu5.addSubMenu(subMenu5);

    // Test case 6: Verify submenu appears in menu view
    Menu menu6 = new Menu();
    menu6.setTitle("Menu 6");
    menu6.setIsRootMenu(true);
    menu6.setOptions(generateOptionsForChildMenus());
    Menu subMenu6 = new Menu();
    subMenu6.setTitle("SubMenu 6");
    menu6.addSubMenu(subMenu6);
    String menuView = menu6.getMenuView();

    assertAll(
            () -> assertNotNull(menu1.getSubMenus(), "...testing submenus list is not null after adding to null list"),
            () -> assertEquals(1, menu1.getSubMenus().size(), "...testing one submenu was added to null list"),
            () -> assertEquals("0. (SUBMENU 1)", menu1.getSubMenus().getFirst().getTitle(), "...testing submenu title in menu1"),
            () -> assertEquals(1, menu2.getSubMenus().size(), "...testing one submenu was added to empty list"),
            () -> assertEquals("0. (SUBMENU 2)", menu2.getSubMenus().getFirst().getTitle(), "...testing submenu title in menu2"),
            () -> assertEquals(originalSize + 1, menu3.getSubMenus().size(), "...testing submenu was added to existing list"),
            () -> assertEquals("2. (SUBMENU 3)", menu3.getSubMenus().getLast().getTitle(), "...testing submenu was added to the end"),
            () -> assertThrows(MenuException.class, () -> menu4.addSubMenu(null), "...testing null submenu throws exception"),
            () -> assertSame(menu5, subMenu5.getParentMenu(), "...testing parent reference is set correctly"),
            () -> assertTrue(menuView.contains("(SUBMENU 6)"), "...testing submenu appears in menu view")
    );
  }

  @Test
  void TestRemoveSubMenu() throws MenuException {
    printTitletoLogAndConsole("TestRemoveSubMenu()", logger);

    // Test case 1: Remove submenu from a menu with null submenus list
    Menu menu1 = new Menu();
    menu1.setTitle("Menu 1");
    menu1.setSubMenus(null);

    // Test case 2: Remove submenu from a menu with empty submenus list
    Menu menu2 = new Menu();
    menu2.setTitle("Menu 2");
    menu2.setSubMenus(new ArrayList<>());

    // Test case 3: Remove submenu from a menu with existing submenus
    Menu menu3 = new Menu();
    menu3.setTitle("Menu 3");
    List<Menu> existingSubMenus = generateSubMenusForChildMenus();
    menu3.setSubMenus(existingSubMenus);
    Menu menuToRemove = existingSubMenus.getFirst();
    int originalSize = menu3.getSubMenus().size();

    // Test case 4: Attempt to remove non-existent submenu
    Menu menu4 = new Menu();
    menu4.setTitle("Menu 4");
    menu4.setSubMenus(generateSubMenusForChildMenus());
    String nonExistentTitle = "Non Existent SubMenu";

    // Test case 5: Attempt to remove null submenu
    Menu menu5 = new Menu();
    menu5.setTitle("Menu 5");
    menu5.setSubMenus(generateSubMenusForChildMenus());

    // Test case 6: Verify parent reference is cleared after removal
    Menu menu6 = new Menu();
    menu6.setTitle("Menu 6");
    Menu subMenu6 = new Menu();
    subMenu6.setTitle("SubMenu 6");
    menu6.addSubMenu(subMenu6);
    menu6.removeSubMenu(subMenu6);

    // Test case 7: Verify submenu is removed from menu view
    Menu menu7 = new Menu();
    menu7.setTitle("Menu 7");
    menu7.setIsRootMenu(true);
    menu7.setOptions(generateOptionsForChildMenus());
    Menu subMenu7 = new Menu();
    subMenu7.setTitle("SubMenu 7");
    menu7.addSubMenu(subMenu7);
    String menuViewBefore = menu7.getMenuView();
    menu7.removeSubMenu(subMenu7);
    String menuViewAfter = menu7.getMenuView();

    // Test case 8: Remove submenu by object reference
    Menu menu8 = new Menu();
    menu8.setTitle("Menu 8");
    Menu subMenu8 = new Menu();
    subMenu8.setTitle("SubMenu 8");
    menu8.addSubMenu(subMenu8);
    int sizeBefore = menu8.getSubMenus().size();
    menu8.removeSubMenu(subMenu8);

    assertAll(
            () -> assertThrows(MenuException.class, () -> menu1.removeSubMenu(subMenu8), "...testing remove from null list throws exception"),
            () -> assertThrows(MenuException.class, () -> menu2.removeSubMenu(subMenu8), "...testing remove from empty list throws exception"),
            () -> {
              menu3.removeSubMenu(menuToRemove);
              assertEquals(originalSize - 1, menu3.getSubMenus().size(), "...testing submenu was removed from existing list");
            },
            () -> {
              int sizeBefore4 = menu4.getSubMenus().size();
              Menu nonExistantSubMenu = new Menu();
              menu4.removeSubMenu(nonExistantSubMenu);
              assertEquals(sizeBefore4, menu4.getSubMenus().size(), "...testing non-existent submenu removal doesn't change size");
            },
            () -> assertThrows(MenuException.class, () -> menu5.removeSubMenu(null), "...testing null menu object parameter throws exception"),
            () -> assertNull(subMenu6.getParentMenu(), "...testing parent reference is cleared after removal"),
            () -> assertTrue(menuViewBefore.contains("(SUBMENU 7)"), "...testing submenu appears in menu view before removal"),
            () -> assertFalse(menuViewAfter.contains("(SUBMENU 7)"), "...testing submenu is removed from menu view after removal"),
            () -> assertEquals(sizeBefore - 1, menu8.getSubMenus().size(), "...testing remove by object reference works correctly"),
            () -> assertNull(subMenu8.getParentMenu(), "...testing parent reference cleared when removed by object")
    );
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
    return new ArrayList<>(generateOptionsForChildMenus());
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
    options.add("0. [VOLVER]");
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
   * Generates a predefined list of two options intended for use with child menus.
   * The options included are static strings representing sample menu entries.
   *
   * @return a list of two predefined strings representing menu options for child menus
   */
  private List<String> generateTwoOptionsForChildMenus() {
    List<String> options = new ArrayList<>();
    options.add("Option One Child Menu");
    options.add("Option Two Child Menu");
    return options;
  }
  
  /**
   * Generates a predefined list of three options intended for use with child menus.
   * The options included are static strings representing sample menu entries.
   *
   * @return a list of three predefined strings representing menu options for child menus
   */
  private List<String> generateThreeOptionsForChildMenus() {
    List<String> options = new ArrayList<>();
    options.add("Option One Child Menu");
    options.add("Option Two Child Menu");
    options.add("Option Three Child Menu");
    return options;
  }
  
  /**
   * Generates a list of two predefined submenus for child menus, each with its own unique
   * title, message, options, and other menu properties. These submenus are designed to
   * represent hierarchical menu structures for testing or other purposes. The generated
   * submenus are not root menus and do not contain nested submenus themselves.
   *
   * @return a list of {@code Menu} objects representing the generated submenus
   * @throws MenuException if an error occurs during the creation of the submenus
   */
  private List<Menu> generateSubMenusForChildMenus() throws MenuException {
    List<Menu> submenus = new ArrayList<>();

    Menu menuSub1 = new Menu();
    menuSub1.setTitle("SubMenu 1");
    menuSub1.setMessage("SubMenu 1 Message");
    menuSub1.setIsRootMenu(false);
    List<String> subOptions = generateTwoOptionsForChildMenus();
    menuSub1.setOptions(subOptions);
    menuSub1.setSelectedOption(0L);
    menuSub1.setSubMenus(null);

    Menu menuSub2 = new Menu();
    menuSub2.setTitle("SubMenu 2");
    menuSub2.setMessage("SubMenu 2 Message");
    menuSub2.setIsRootMenu(false);
    menuSub2.setOptions(generateThreeOptionsForChildMenus());
    menuSub2.setSelectedOption(0L);
    menuSub2.setSubMenus(null);

    // Arrays.asList() returns a FIXED size list, and it's not valid for the purpose of our tests...
    submenus.add(menuSub1);
    submenus.add(menuSub2);
    return submenus;
  }
}
