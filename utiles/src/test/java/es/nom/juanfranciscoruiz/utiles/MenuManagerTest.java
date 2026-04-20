package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.exceptions.MenuErrors;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuManagerErrors;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuManagerException;
import es.nom.juanfranciscoruiz.utiles.model.MenuConstants;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuManagerTest {
    private static final ResourceBundle MESSAGES = loadMessages();
    private static final String EXIT_OPTION = getMessage("msg.menu.exit.opt", MenuConstants.EXITOPT);

    @Test
    void constructorPorDefectoCreaUnMenuValido() throws MenuException {
        MenuManager manager = new MenuManager();

        assertNotNull(manager.getMenu());
        assertTrue(manager.getMenu().getIsRootMenu());
        assertEquals(1, manager.getMenu().getOptions().size());
    }

    @Test
    void constructorConMenuNuloLanzaExcepcion() {
        MenuManagerException ex = assertThrows(MenuManagerException.class, () -> new MenuManager(null));
        assertEquals(getMessage("err.menu.null", MenuErrors.ERR_MENU_OBJECT_CANNOT_BE_NULL), ex.getMessage());
    }

    @Test
    void setMenuConMenuValidoActualizaLaReferencia() throws MenuException, MenuManagerException {
        MenuManager manager = new MenuManager();
        Menu otherMenu = Menu.getInstance();

        manager.setMenu(otherMenu);

        assertSame(otherMenu, manager.getMenu());
    }

    @Test
    void showMenuSinAnsiImprimeLaVistaDelMenu() throws Exception {
        Menu menu = Menu.getInstance();
        menu.setTitle("Menu de prueba");
        menu.setMessage("Seleccione una opcion");
        MenuManager manager = new MenuManager(menu);

        String output = tapSystemOutNormalized(() -> manager.showMenu(false));

        assertEquals(normalize(menu.getMenuView()), normalize(output));
    }

    @Test
    void showMenuConAnsiLanzaExcepcion() throws MenuException, MenuManagerException {
        MenuManager manager = new MenuManager(Menu.getInstance());

        MenuManagerException ex = assertThrows(MenuManagerException.class, () -> manager.showMenu(true));

        assertEquals(getMessage("err.menu.manager.ansi", MenuManagerErrors.ERR_CANNOT_SHOW_MENU_BECAUSE_ANSI_IS_NOT_SUPPORTED), ex.getMessage());
    }

    @Test
    void awaitResponseDevuelveLaOpcionSeleccionadaYLaGuardaEnElMenu() throws Exception {
        Menu menu = Menu.getInstance();
        MenuManager manager = new MenuManager(menu);
        manager.addOptionToMenu("Primera opcion");
        manager.addOptionToMenu("Segunda opcion");

        Long result = ejecutarConEntrada("1", () -> manager.awaitResponse("Seleccione: "));

        assertAll(
                () -> assertEquals(1L, result),
                () -> assertEquals(1L, manager.getMenu().getSelectedOption())
        );
    }

    @Test
    void awaitResponseConEntradaVaciaLanzaExcepcionYGuardaMensaje() throws Exception {
        Menu menu = Menu.getInstance();
        MenuManager manager = new MenuManager(menu);
        manager.addOptionToMenu("Primera opcion");

        MenuException ex = assertThrows(MenuException.class, () -> ejecutarConEntrada("", () -> manager.awaitResponse("Seleccione: ")));

        assertAll(
                () -> assertEquals(getMessage("err.menu.blank", MenuErrors.ERR_BLANK_NULL), ex.getMessage()),
                () -> assertEquals(getMessage("err.menu.blank", MenuErrors.ERR_BLANK_NULL), menu.getMessage())
        );
    }

    @Test
    void awaitResponseConTextoNoNumericoLanzaExcepcionYGuardaMensaje() throws Exception {
        Menu menu = Menu.getInstance();
        MenuManager manager = new MenuManager(menu);
        manager.addOptionToMenu("Primera opcion");

        MenuException ex = assertThrows(MenuException.class, () -> ejecutarConEntrada("abc", () -> manager.awaitResponse("Seleccione: ")));

        assertAll(
                () -> assertEquals(getMessage("err.menu.no.number", MenuErrors.ERR_NO_NUMBER), ex.getMessage()),
                () -> assertEquals(getMessage("err.menu.no.number", MenuErrors.ERR_NO_NUMBER), menu.getMessage())
        );
    }

    @Test
    void awaitResponseConOpcionFueraDeRangoLanzaExcepcionYGuardaMensaje() throws Exception {
        Menu menu = Menu.getInstance();
        MenuManager manager = new MenuManager(menu);
        manager.addOptionToMenu("Primera opcion");

        MenuException ex = assertThrows(MenuException.class, () -> ejecutarConEntrada("9", () -> manager.awaitResponse("Seleccione: ")));

        assertAll(
                () -> assertEquals(getMessage("err.menu.out.of.range", MenuErrors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE), ex.getMessage()),
                () -> assertEquals(getMessage("err.menu.out.of.range", MenuErrors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE), menu.getMessage())
        );
    }

    @Test
    void addOptionToMenuNumeraYOcultaDuplicados() throws MenuException, MenuManagerException {
        MenuManager manager = new MenuManager(Menu.getInstance());

        manager.addOptionToMenu("Abrir");

        MenuException ex = assertThrows(MenuException.class, () -> manager.addOptionToMenu("Abrir"));

        assertAll(
                () -> assertEquals("1. Abrir", manager.getMenu().getOptions().get(1)),
                () -> assertEquals(getMessage("err.menu.option.exists", MenuErrors.ERR_OPTION_ALREADY_EXISTS), ex.getMessage())
        );
    }

    @Test
    void removeOptionFromMenuEliminaLaOpcionIndicada() throws MenuException, MenuManagerException {
        MenuManager manager = new MenuManager(Menu.getInstance());
        manager.addOptionToMenu("Abrir");

        manager.removeOptionFromMenu("1. Abrir");

        assertEquals(List.of(EXIT_OPTION), manager.getMenu().getOptions());
    }

    @Test
    void addSubMenuToMenuRelacionaMenusYAnadeOpcion() throws MenuException, MenuManagerException {
        Menu parent = Menu.getInstance();
        Menu child = Menu.getInstance(new ArrayList<>(), "Submenu", "Msg", false);
        MenuManager manager = new MenuManager(parent);

        manager.addSubMenuToMenu(child);

        assertAll(
                () -> assertEquals(1, parent.getSubMenus().size()),
                () -> assertSame(parent, child.getParentMenu()),
                () -> assertTrue(parent.getOptions().contains("1. Submenu"))
        );
    }

    @Test
    void removeSubMenuFromMenuConSubmenuNuloLanzaExcepcion() throws MenuException, MenuManagerException {
        Menu parent = Menu.getInstance();
        MenuManager manager = new MenuManager(parent);

        MenuException ex = assertThrows(MenuException.class, () -> manager.removeSubMenuFromMenu(null));

        assertEquals(getMessage("err.menu.null", MenuErrors.ERR_MENU_OBJECT_CANNOT_BE_NULL), ex.getMessage());
    }

    private Long ejecutarConEntrada(String input, ThrowingSupplier<Long> supplier) throws Exception {
        InputStream originalSystemIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream((input + System.lineSeparator()).getBytes(StandardCharsets.UTF_8)));
            return supplier.get();
        } finally {
            System.setIn(originalSystemIn);
        }
    }

    private static ResourceBundle loadMessages() {
        try {
            return ResourceBundle.getBundle("messages");
        } catch (MissingResourceException ex) {
            return null;
        }
    }

    private static String getMessage(String key, String defaultMessage) {
        if (MESSAGES != null && MESSAGES.containsKey(key)) {
            return MESSAGES.getString(key);
        }
        return defaultMessage;
    }

    private static String normalize(String value) {
        return value.replace("\r\n", "\n");
    }

    @FunctionalInterface
    private interface ThrowingSupplier<T> {
        T get() throws Exception;
    }
}
