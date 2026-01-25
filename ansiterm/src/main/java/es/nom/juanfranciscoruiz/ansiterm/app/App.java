package es.nom.juanfranciscoruiz.ansiterm.app;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.utiles.IO;
import es.nom.juanfranciscoruiz.utiles.Menu;
import es.nom.juanfranciscoruiz.utiles.impl.IOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application class to demonstrate the functionality of the ANSITerm library.
 * It provides a menu-driven interface to test various ANSI escape sequence features
 * like cursor movement, text styles, colors, and more.
 * @author Juan F. Ruiz
 */
public class App {

    /**
     * Instantiates a new App object.
     */
    public App(){}
    
    /**
     * Logger used for tracing and debugging.
     */
    public static final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * Application entry point.
     * 
     * @param args Command line arguments received from the operating system.
     */
    public static void main(String[] args) {
        logger.info("Inicio de la aplicación");
        
        ANSITerm term = null;
        try {
            term = new ANSITerm();
            App app = new App();
            Menu menu = new Menu();
            List<String> opciones = new ArrayList<>();

            opciones.add("1. Raw console mode test");
            opciones.add("2. Cursor position recovery test");
            opciones.add("3. Screen size test");
            opciones.add("4. Rectangle drawing test");
            opciones.add("5. Text deletions from the cursor");
            opciones.add("6. Text styles");
            opciones.add("7. Multiple text styles");
            opciones.add("8. Text colors");
            opciones.add("9. Text palette of 256 colors");
            opciones.add("10. Cursor styles");
            opciones.add("11. Cursor blinking");
            opciones.add("12. Shows cursor movement");
            menu.setIsHomeMenu(true);
            menu.setTitle("Testing the ANSITerm library");
            menu.setOptions(opciones);
            menu.generateMenuView();

            do {
                term.clearScreen();
                term.moveCursorToBegin();
                if (menu.getMessage().isEmpty()) {
                    menu.setMessage("Running on".concat(System.getProperty("os.name")));
                }
                IO.prt(menu.getMenuView());
                menu.awaitResponse("Please choose an option: ");
                switch (menu.getSelectedOption().intValue()) {
                    case 1 -> {
                        menu.setMessage("");
                        app.habilitaModoRawTeclado(term);
                    }
                    case 2 -> {
                        menu.setMessage("");
                        app.recuperaPosicionCursor(term, 1L);
                    }
                    case 3 -> {
                        menu.setMessage("");
                        app.lineasYColumnasPantalla(term);
                    }
                    case 4 -> {
                        menu.setMessage("");
                        app.dibujaRectangulo(term);
                    }
                    case 5 -> {
                        menu.setMessage("");
                        app.borradosDesdeCursor(term);
                    }
                    case 6 -> {
                        menu.setMessage("");
                        app.estilos(term);
                    }
                    case 7 -> {
                        menu.setMessage("");
                        app.estilosMultiples(term);
                    }
                    case 8 -> {
                        menu.setMessage("");
                        app.colores(term);
                    }
                    case 9 -> {
                        menu.setMessage("");
                        app.colores256(term);
                    }
                    case 10 -> {
                        menu.setMessage("");
                        app.estilosDelCursor(term);
                    }
                    case 11 -> {
                        menu.setMessage("");
                        app.parpadeoDelCursor(term);
                    }
                    case 12 -> {
                        menu.setMessage("");
                        app.muestraMovimientoCursor(term, 200L);
                    }
                    default -> logger.warn("The user has entered {}", menu.getSelectedOption());
                }
            } while (menu.getSelectedOption() != 0L);

        } catch (Exception ex) {
            if (term != null){
                term.resetScreen();
            }
            logger.error(ex.getMessage());
            logger.info("The application ended unexpectedly.");
            System.out.println(ex.getMessage());
            System.exit(-1);
        } finally {
            // Just in case something goes wrong... and to avoid rendering the terminal useless.
            if (term != null) {
                term.clearScreen();
                term.moveCursorToBegin();
                term.resetScreen();
            }
            logger.info("End of application");
            System.exit(0);
        }
    }

    // Useful methods for displaying functionalities.
    /**
     * Perform a keystroke capture test once the terminal's 'raw' mode is
     * enabled. Display the keys pressed in a loop until the "q" key is pressed.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void habilitaModoRawTeclado(ANSITerm term) throws Exception {
       RawMode rawMode = new RawMode();
       rawMode.perform(term);
    }

    /**
     * Displays the cursor movement in the terminal with the delay in
     * milliseconds between each movement. The 'path' will be a rectangle,
     * taking into account the current dimensions of the terminal.
     * 
     * @param retardo The delay in milliseconds
     * @throws Exception In case of any error
     */
    private void muestraMovimientoCursor(ANSITerm term, long retardo) throws Exception {
        ShowCursorMovement showCursorMovement = new ShowCursorMovement();
        showCursorMovement.perform(term);
    }

    /**
     * It prints an 'X' character in each position of the terminal, and for each movement
     * of the cursor it recovers its position by printing it on the last line of the
     * terminal.
     * 
     * @param retardo The delay in milliseconds
     * @throws Exception In case of any error
     */
    private void recuperaPosicionCursor(ANSITerm term, long retardo) throws Exception {
        RecoverCursorPosition recoverCursorPosition = new RecoverCursorPosition();
        recoverCursorPosition.perform(term);
    }

    /**
     * Draw a rectangle with asterisks that borders the screen
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void dibujaRectangulo(ANSITerm term) throws Exception {
       DrawsRectangle dr = new DrawsRectangle();
       dr.perform(term);
    }

    /**
     * Tests and displays the screen size (lines and columns).
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void lineasYColumnasPantalla(ANSITerm term) throws Exception {
        ScreenSize  screenSize = new ScreenSize();
        screenSize.perform(term);
    }

    /**
     * Displays various text styles (bold, dim, italic, etc.) applicable to text.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void estilos(ANSITerm term) throws Exception {
       TextStyles textStyles = new TextStyles();
       textStyles.perform(term);
    }

    /**
     * Displays combinations of multiple text styles.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void estilosMultiples(ANSITerm term) throws Exception {
       MultipleStylesText multipleStylesText = new MultipleStylesText();
       multipleStylesText.perform(term);
    }

    /**
     * Displays various foreground and background text colors.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void colores(ANSITerm term) throws Exception {
        ShowTextColors showTextColors = new ShowTextColors();
        showTextColors.perform(term);
    }

    /**
     * Displays the 256-color palette.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void colores256(ANSITerm term) throws Exception {
       ShowTextColors256 showTextColors256 = new ShowTextColors256();
       showTextColors256.perform(term);
    }
    
    /**
     * Displays various cursor styles (block, bar, underline, blinking, etc.).
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void estilosDelCursor(ANSITerm term) throws Exception {
       ShowCursorStyles showCursorStyles = new ShowCursorStyles();
       showCursorStyles.perform(term);
    }

    /**
     * Demonstrates screen and line deletion commands from the current cursor position.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void borradosDesdeCursor(ANSITerm term) throws Exception {
       ScreenAndLineDeletions screenAndLineDeletions = new ScreenAndLineDeletions();
       screenAndLineDeletions.perform(term);
    }

    /**
     * Demonstrates enabling and disabling cursor blinking.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void parpadeoDelCursor(ANSITerm term) throws Exception {
        ShowCursorBlinking showCursorBlinking = new ShowCursorBlinking();
        showCursorBlinking.perform(term);
    }
}
