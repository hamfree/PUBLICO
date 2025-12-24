package es.nom.juanfranciscoruiz.ansiterm;

import com.sun.jna.LastErrorException;
import es.nom.juanfranciscoruiz.utiles.Menu;
import es.nom.juanfranciscoruiz.ansiterm.util.UnclosableInputStreamDecorator;
import es.nom.juanfranciscoruiz.utiles.impl.IO;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application class to demonstrate the functionality of the ANSITerm library.
 * It provides a menu-driven interface to test various ANSI escape sequence features
 * like cursor movement, text styles, colors, and more.
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
        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Keyboard RAW mode ------------", 1, 1);

        try {
            pausa(2000L, null);
            term.getOsCall().enableRawMode();

            term.printAt("Press keys. Press 'q' to exit.", 2, 1);
            term.moveCursorToXY(3, 1);
            
            while (true) {
                int resp = System.in.read();
                char ch = (char) resp;
                System.out.println(resp + ", char = " + ch);
                if (ch == 'q') {
                    break;
                }
            }
        } catch (LastErrorException e) {
            logger.error(String.valueOf(e.getErrorCode()));
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
            System.exit(-1);
        } finally {
            term.getOsCall().disableRawMode();
        }
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
        term.clearScreen();
        term.moveCursorToBegin();
        TerminalSize screenSize = term.getTerminalSize();
        term.printAt("------------ Moving the cursor ------------", 1, 1);

        term.moveCursorToBegin();
        for (int i = 0; i < screenSize.getLineas(); i++) {
            Thread.sleep(retardo);
            term.moveCursorDown(1);
        }
        for (int i = 0; i < screenSize.getColumnas(); i++) {
            Thread.sleep(retardo);
            term.moveCursorRight(1);
        }
        for (int i = 0; i < screenSize.getLineas(); i++) {
            Thread.sleep(retardo);
            term.moveCursorUp(1);
        }
        for (int i = 0; i < screenSize.getColumnas(); i++) {
            Thread.sleep(retardo);
            term.moveCursorLeft(1);
        }
        pausa(2000L, null);
    }

    /**
     * It prints an 'X' character in each position of the terminal and for each movement
     * of the cursor it recovers its position by printing it on the last line of the
     * terminal.
     * 
     * @param retardo The delay in milliseconds
     * @throws Exception In case of any error
     */
    private void recuperaPosicionCursor(ANSITerm term, long retardo) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------  Recovering the cursor position ------------ ", 1, 1);
        TerminalSize screenSize = term.getTerminalSize();

        term.cursorShow();
        term.cursorChangeStyle(CursorStyles.CUR_BLO_EST);

        for (int lin = 2; lin < screenSize.getLineas() - 3; lin++) {
            for (int col = 1; col <= screenSize.getColumnas(); col++) {
                Position p = new Position(1, 1);
                term.printAt("X", lin, col);
                try {
                    p = term.getCursorPosition();
                } catch (LastErrorException e) {
                    logger.error(String.valueOf(e.getErrorCode()));
                    System.out.println(e.getErrorCode());
                    System.out.println(e.getMessage());
                }
                pausaSinMensaje(retardo);
                term.printAt("Cursor position: column : ", screenSize.getLineas() - 2, 1);
                term.deleteFromCursorToEndLine();
                term.printAt(p.getCol() + ", row: " + p.getLin(), screenSize.getLineas() - 2, 31);
                pausaSinMensaje(retardo);
            }
        }
        pausa(0, "Press <ENTER> to return to the menu");
    }

    /**
     * Draw a rectangle with asterisks that borders the screen
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void dibujaRectangulo(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        TerminalSize ts = term.getTerminalSize();

        term.printAt("------------ Printing text at specific coordinates ------------", 1, 1);
        term.printAt("A rectangle will be drawn on the screen:", 2, 1);
        for (int col = 2; col < ts.getColumnas(); col++) {
            term.printAt("*", 3, col);
        }
        for (int linea = 3; linea < ts.getLineas() - 2; linea++) {
            term.printAt("*", linea, ts.getColumnas() - 1);
        }
        for (int col = ts.getColumnas() - 1; col > 1; col--) {
            term.printAt("*", ts.getLineas() - 2, col);
        }
        for (int linea = ts.getLineas() - 2; linea > 2; linea--) {
            term.printAt("*", linea, 2);
        }

        pausa(0, "Press <ENTER> to return to menu");
    }

    /**
     * Tests and displays the screen size (lines and columns).
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void lineasYColumnasPantalla(ANSITerm term) throws Exception {

        String resp = "";
        while (!resp.equals("q")) {
            term.clearScreen();
            term.moveCursorToBegin();
            term.printAt("------------ Screen size ------------", 1, 1);
            term.printAt("Try resizing the terminal window.", 2, 1);
            term.printAt("Then press ENTER. The terminal size will be displayed..", 3, 1);
            term.printAt("Press q and then ENTER to return to the menu", 4, 1);
            term.printAt("> : ", 5, 1);
            Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in));
            resp = sc.nextLine();
            TerminalSize ts = term.getOsCall().getTerminalSize();
            term.printAt("The screen size is:"
                    + ts.getLineas()
                    + "lines and "
                    + ts.getColumnas()
                    + " columns.", ts.getLineas() - 2, 1);
            pausa(0, null);
        }

    }

    /**
     * Displays various text styles (bold, dim, italic, etc.) applicable to text.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void estilos(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Styles applicable to the text ------------", 1, 1);
        term.printAt(term.setBold("Bold phrase"), 2, 10);
        term.printAt(term.setDim("Attenuated phrase"), 3, 10);
        term.printAt(term.setItalic("Frase en cursiva"), 4, 10);
        term.printAt(term.setBlink("Intermittent phrase"), 5, 10);
        term.printAt(term.setInverse("Phrase with inverted colors"), 6, 10);
        term.printAt(term.setHidden("Hidden message"), 7, 10);
        pausa(0, "Press <ENTER> to return to menu");
    }

    /**
     * Displays combinations of multiple text styles.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void estilosMultiples(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Multiple styles applicable to the text ------------", 1, 1);

        // Bold with other styles
        String mensaje = "Bold phrase with various styles";
        term.printAt("The following sentence will be in bold and dimmed", 2, 5);
        term.printAt(term.setStyles(true, true, false, false, false, false, false, mensaje), 3, 10);
        term.printAt("The following sentence will be in bold and italics", 4, 5);
        term.printAt(term.setStyles(true, false, true, false, false, false, false, mensaje), 5, 10);
        term.printAt("The following sentence will be in bold and underlined", 6, 5);
        term.printAt(term.setStyles(true, false, false, true, false, false, false, mensaje), 7, 10);
        term.printAt("The following sentence will be in bold and flashing", 8, 5);
        term.printAt(term.setStyles(true, false, false, false, true, false, false, mensaje), 9, 10);
        term.printAt("The following sentence will be in bold and with inverted colors.", 10, 5);
        term.printAt(term.setStyles(true, false, false, false, false, true, false, mensaje), 11, 10);
        term.printAt("The following sentence will be in bold and struck through", 12, 5);
        term.printAt(term.setStyles(true, false, false, false, false, false, true, mensaje), 13, 10);

        // Dim with other styles
        mensaje = "ATTENUATED phrase with various styles";
        term.printAt("The following sentence will be in bold and attenuated.", 14, 5);
        term.printAt(term.setStyles(true, true, false, false, false, false, false, mensaje), 15, 10);
        term.printAt("The following sentence will be attenuated and in italics", 16, 5);
        term.printAt(term.setStyles(false, true, true, false, false, false, false, mensaje), 17, 10);
        term.printAt("The following sentence will be highlighted and underlined", 18, 5);
        term.printAt(term.setStyles(false, true, false, true, false, false, false, mensaje), 19, 10);
        term.printAt("The following sentence will be dimmed and intermittent.", 20, 5);
        term.printAt(term.setStyles(false, true, false, false, true, false, false, mensaje), 21, 10);
        term.printAt("The following sentence will be dimmed and with inverted colors.", 22, 5);
        term.printAt(term.setStyles(false, true, false, false, false, true, false, mensaje), 23, 10);
        term.printAt("The following sentence will be grayed out and crossed out", 24, 5);
        term.printAt(term.setStyles(false, true, false, false, false, false, true, mensaje), 25, 10);

        pausa(0, null);
        term.clearScreen();
        term.printAt("------------ Multiple styles applicable to the text ------------", 1, 1);
        
        // Italic with other styles
        mensaje = "Phrase in CURSIVE with various styles";
        term.printAt("The following sentence will be in italics and attenuated.", 2, 5);
        term.printAt(term.setStyles(false, true, true, false, false, false, false, mensaje), 3, 10);
        term.printAt("The following sentence will be in italics and bold.", 4, 5);
        term.printAt(term.setStyles(true, false, true, false, false, false, false, mensaje), 5, 10);
        term.printAt("The following sentence will be in italics and underlined", 6, 5);
        term.printAt(term.setStyles(false, false, true, true, false, false, false, mensaje), 7, 10);
        term.printAt("The following sentence will be in italics and blinking.", 8, 5);
        term.printAt(term.setStyles(false, false, true, false, true, false, false, mensaje), 9, 10);
        term.printAt("The following sentence will be in italics and with inverted colors.", 10, 5);
        term.printAt(term.setStyles(false, false, true, false, false, true, false, mensaje), 11, 10);
        term.printAt("The following sentence will be in italics and crossed out", 12, 5);
        term.printAt(term.setStyles(false, false, true, false, false, false, true, mensaje), 13, 10);

        // Underline with other styles
        mensaje = "UNDERLINED phrase with various styles";
        term.printAt("The following sentence will be underlined and in bold.", 14, 5);
        term.printAt(term.setStyles(true, false, false, true, false, false, false, mensaje), 15, 10);
        term.printAt("The following sentence will be underlined and in italics", 16, 5);
        term.printAt(term.setStyles(false, false, true, true, false, false, false, mensaje), 17, 10);
        term.printAt("The following sentence will be underlined and highlighted.", 18, 5);
        term.printAt(term.setStyles(false, true, false, true, false, false, false, mensaje), 19, 10);
        term.printAt("The following sentence will be underlined and flashing.", 20, 5);
        term.printAt(term.setStyles(false, false, false, true, true, false, false, mensaje), 21, 10);
        term.printAt("The following sentence will be underlined and with inverted colors.", 22, 5);
        term.printAt(term.setStyles(false, false, false, true, false, true, false, mensaje), 23, 10);
        term.printAt("The following sentence will be underlined and crossed out.", 24, 5);
        term.printAt(term.setStyles(false, false, false, true, false, false, true, mensaje), 25, 10);

        pausa(0, null);

        term.clearScreen();
        term.printAt("------------ Multiple styles applicable to the text ------------", 1, 1);
        
        // Blinking with other styles
        mensaje = "INTERMITTENT phrase with various styles";
        term.printAt("The following sentence will be intermittent and dimmed.", 2, 5);
        term.printAt(term.setStyles(false, true, false, false, true, false, false, mensaje), 3, 10);
        term.printAt("The following sentence will be flashing and bold", 4, 5);
        term.printAt(term.setStyles(true, false, false, false, true, false, false, mensaje), 5, 10);
        term.printAt("The following sentence will be intermittent and underlined", 6, 5);
        term.printAt(term.setStyles(false, false, false, true, true, false, false, mensaje), 7, 10);
        term.printAt("The following phrase will be intermittent and intermittent", 8, 5);
        term.printAt(term.setStyles(false, false, false, false, true, false, false, mensaje), 9, 10);
        term.printAt("The following phrase will be flashing and with inverted colors.", 10, 5);
        term.printAt(term.setStyles(false, false, false, false, true, true, false, mensaje), 11, 10);
        term.printAt("The following sentence will be intermittent and crossed out", 12, 5);
        term.printAt(term.setStyles(false, false, false, false, true, false, true, mensaje), 13, 10);

        // Inverse with other styles
        mensaje = "INVERTED phrase with various styles";
        term.printAt("The following sentence will be reversed and in bold.", 14, 5);
        term.printAt(term.setStyles(true, false, false, false, false, true, false, mensaje), 15, 10);
        term.printAt("The following sentence will be inverted and in italics", 16, 5);
        term.printAt(term.setStyles(false, false, true, false, false, true, false, mensaje), 17, 10);
        term.printAt("The following sentence will be inverted and attenuated", 18, 5);
        term.printAt(term.setStyles(false, true, false, false, false, true, false, mensaje), 19, 10);
        term.printAt("The following sentence will be inverted and intermittent", 20, 5);
        term.printAt(term.setStyles(false, false, false, false, true, true, false, mensaje), 21, 10);
        term.printAt("The following sentence will be reversed", 22, 5);
        term.printAt(term.setStyles(false, false, false, false, false, true, false, mensaje), 23, 10);
        term.printAt("The following sentence will be reversed and crossed out", 24, 5);
        term.printAt(term.setStyles(false, false, false, false, false, true, true, mensaje), 25, 10);

        pausa(0, null);

        term.clearScreen();
        term.printAt("------------ Multiple styles applicable to the text ------------", 1, 1);
        
        // Strikethrough with other styles
        mensaje = "Phrase CROSSED out in various styles";
        term.printAt("The following sentence will be crossed out and dimmed.", 2, 5);
        term.printAt(term.setStyles(false, true, false, false, false, false, true, mensaje), 3, 10);
        term.printAt("The following sentence will be crossed out and in bold.", 4, 5);
        term.printAt(term.setStyles(true, false, false, false, false, false, true, mensaje), 5, 10);
        term.printAt("The following sentence will be crossed out and underlined.", 6, 5);
        term.printAt(term.setStyles(false, false, false, true, false, false, true, mensaje), 7, 10);
        term.printAt("The following sentence will be crossed out and intermittent.", 8, 5);
        term.printAt(term.setStyles(false, false, false, false, true, false, true, mensaje), 9, 10);
        term.printAt("The following sentence will be crossed out and with the colors inverted.", 10, 5);
        term.printAt(term.setStyles(false, false, false, false, false, true, true, mensaje), 11, 10);
        term.printAt("The following sentence will be crossed out", 12, 5);
        term.printAt(term.setStyles(false, false, false, false, false, false, true, mensaje), 13, 10);

        pausa(0, "Press <ENTER> to return to menu");
    }

    /**
     * Displays various foreground and background text colors.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void colores(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();

        term.printAt("------------ Colors applicable to the text and background ------------", 1, 1);
        term.printAt("Colors in the foreground", 2, 1);

        term.linefeed();
        term.printAt(term.setColor(Color.AMARILLO, "Phrase in yellow"), 4, 1);
        term.printAt(term.setColor(Color.AZUL, "Phrase in blue"), 5, 1);
        term.printAt(term.setColor(Color.BLANCO, "Blank phrase"), 6, 1);
        term.printAt(term.setColor(Color.CIAN, "Phrase in cyan"), 7, 1);
        term.printAt(term.setColor(Color.MAGENTA, "Phrase in magenta"), 8, 1);
        term.printAt(term.setColor(Color.NEGRO, "Phrase in black"), 9, 1);
        term.printAt(term.setColor(Color.ROJO, "Phrase in red"), 10, 1);
        term.printAt(term.setColor(Color.VERDE, "Phrase in green"), 11, 1);
        term.linefeed();
        term.printAt("Background colors", 13, 1);
        term.linefeed();
        term.printAt(term.setBackgroundColor(BGColor.AMARILLO, "Phrase with a yellow background"), 15, 1);
        term.printAt(term.setBackgroundColor(BGColor.AZUL, "Phrase with a blue background"), 16, 1);
        term.printAt(term.setBackgroundColor(BGColor.BLANCO, "Phrase with a white background"), 17, 1);
        term.printAt(term.setBackgroundColor(BGColor.CIAN, "Phrase with cyan background"), 18, 1);
        term.printAt(term.setBackgroundColor(BGColor.MAGENTA, "Phrase with a magenta background"), 19, 1);
        term.printAt(term.setBackgroundColor(BGColor.NEGRO, "Phrase with a black background"), 20, 1);
        term.printAt(term.setBackgroundColor(BGColor.ROJO, "Phrase with a red background"), 21, 1);

        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Colors applicable to the text and background ------------", 1, 1);
        term.printAt("Bright colors in the foreground", 2, 1);
        term.linefeed();
        term.printAt(term.setColor(Color.AMARILLO_BRILLANTE, "Phrase in yellow"), 4, 1);
        term.printAt(term.setColor(Color.AZUL_BRILLANTE, "Phrase in blue"), 5, 1);
        term.printAt(term.setColor(Color.BLANCO_BRILLANTE, "Blank phrase"), 6, 1);
        term.printAt(term.setColor(Color.CIAN_BRILLANTE, "Phrase in cyan"), 7, 1);
        term.printAt(term.setColor(Color.MAGENTA_BRILLANTE, "Phrase in magenta"), 8, 1);
        term.printAt(term.setColor(Color.NEGRO_BRILLANTE, "Phrase in black"), 9, 1);
        term.printAt(term.setColor(Color.ROJO_BRILLANTE, "Phrase in red"), 10, 1);
        term.printAt(term.setColor(Color.VERDE_BRILLANTE, "Phrase in green"), 11, 1);
        term.linefeed();
        term.printAt("Bright colors for the background", 13, 1);
        term.linefeed();
        term.printAt(term.setBackgroundColor(BGColor.AMARILLO_BRILLANTE, "Phrase in yellow"), 15, 1);
        term.printAt(term.setBackgroundColor(BGColor.AZUL_BRILLANTE, "Phrase in blue"), 16, 1);
        term.printAt(term.setBackgroundColor(BGColor.BLANCO_BRILLANTE, "Blank phrase"), 17, 1);
        term.printAt(term.setBackgroundColor(BGColor.CIAN_BRILLANTE, "Phrase in cyan"), 18, 1);
        term.printAt(term.setBackgroundColor(BGColor.MAGENTA_BRILLANTE, "Phrase in magenta"), 19, 1);
        term.printAt(term.setBackgroundColor(BGColor.NEGRO_BRILLANTE, "Phrase in black"), 20, 1);
        term.printAt(term.setBackgroundColor(BGColor.ROJO_BRILLANTE, "Phrase in red"), 21, 1);
        term.printAt(term.setBackgroundColor(BGColor.VERDE_BRILLANTE, "Phrase in green"), 22, 1);

        pausa(0, "Press <ENTER> to return to menu");

    }

    /**
     * Displays the 256-color palette.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void colores256(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Displays the palette of 256 colors ------------", 1, 1);

        int j = 0;
        int line = 2;
        int col = 0;
        term.moveCursorToXY(line, col);
        for (int i = 0; i < 256; i++) {
            String msg = term.setColor256(i, String.valueOf(i));
            if (j > 15) {
                term.printAt(msg, line, col);
                term.linefeed();
                j = 0;
                col = 0;
                line = line + 1;
            } else {
                term.printAt(msg.concat(" "), line, col);
                col = col + 5;
                j++;
            }
        }

        pausa(0, "Press <ENTER> to return to menu");
    }

    
    /**
     * Displays various cursor styles (block, bar, underline, blinking, etc.).
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void estilosDelCursor(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.cursorShow();
        term.printAt("------------ Cursor styles ------------", 1, 1);
        String msg = "(1/9) - Stable bar cursor shape";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_BAR_ES);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Cursor styles ------------", 1, 1);
        msg = "(2/9) - Blinking bar cursor shape";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_BAR_PAR);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Cursor styles ------------", 1, 1);
        msg = "(3/9) - Stable block cursor shape";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_BLO_EST);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Cursor styles ------------", 1, 1);
        msg = "(4/9) - Blinking block cursor shape";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_BLO_PAR);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Cursor styles ------------", 1, 1);
        msg = "(5/9) - Stable underline cursor shape";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_SUB_EST);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Cursor styles ------------", 1, 1);
        msg = "(6/9) - Blinking underline cursor shape";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_SUB_PAR);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Cursor styles ------------", 1, 1);
        msg = "(7/9) - User-defined cursor shape";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_USU);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Cursor styles ------------", 1, 1);
        msg = "(8/9) - Hide the cursor";
        term.printAt(msg, 2, 1);
        term.cursorHide();
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Cursor styles ------------", 1, 1);
        msg = "(9/9) - Show cursor";
        term.printAt(msg, 2, 1);
        term.cursorShow();
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, "Press <ENTER> to return to menu");
    }

    /**
     * Demonstrates screen and line deletion commands from the current cursor position.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void borradosDesdeCursor(ANSITerm term) throws Exception {
        String msg = "The process will continue automatically every 4 seconds. Do not press any keys!";
        int columnas = term.getTerminalSize().getColumnas();
        int filas = term.getTerminalSize().getLineas();
        final long RETARDO = 4000L;

        term.clearScreen();
        term.moveCursorToBegin();
        term.cursorShow();
        term.cursorChangeStyle(CursorStyles.CUR_BLO_PAR);
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(1/13) Delete from the cursor to the beginning of the screen", 2, 1);
        term.printAt(msg, 3, 1);

        imprimeBloqueTexto(term, 5, filas - 5, columnas);
        term.moveCursorToXY(12, 60);
        Thread.sleep(RETARDO);
        term.deleteFromCursorToBeginScreen();
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(2/13) Deleted from the cursor to the end of the screen", 2, 1);
        term.printAt(msg, 3, 1);

        imprimeBloqueTexto(term, 5, filas - 5, columnas);
        term.moveCursorToXY(12, 60);
        Thread.sleep(RETARDO);
        term.deleteFromCursorToEndScreen();
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(3/13) Deleted from the cursor to the beginning of the line", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        term.deleteFromCursorToBeginLine();
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(4/13) Deleted from the cursor to the end of the line", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        term.deleteFromCursorToEndLine();
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(5/13) Deleting the line where the cursor is located", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 3, columnas);
        term.moveCursorToXY(6, 60);
        Thread.sleep(RETARDO);
        term.deleteLine();
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(6/13) Delete 5 lines including the one containing the cursor", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 15, columnas);
        term.moveCursorToXY(6, 60);
        Thread.sleep(RETARDO);
        term.deleteLines(5);
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(7/13) Delete the 10 characters before the cursor position", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        for (int i = 0; i < 10; i++) {
            term.deleteCharacter();
        }
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(8/13) Delete the 20 characters following the cursor position", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        term.delChars(20);
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(9/13) Deletes 20 characters from the current cursor position by overwriting them with a space character.", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        term.delCharsWithSpaces(20);
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(10/13) Insert 10 spaces at the current cursor position. Shift all existing text to the right.", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        term.insertSpaces(10);
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(11/13) Insert 10 lines at the current cursor position. The cursor line and the lines below it will shift down", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 5, columnas);
        term.moveCursorToXY(7, 60);
        Thread.sleep(RETARDO);
        term.insertLines(10);
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(12/13) Move " + filas + " lines down the text", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 3, filas - 3, columnas);

        for (int i = 1; i <= filas; i++) {
            Thread.sleep(100L);
            term.moveTextDown(1);
        }

        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Deleted from the cursor and scroll ------------", 1, 1);
        term.printAt("(13/13) Move " + filas + " lines upwards the text", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 3, filas - 3, columnas);

        Thread.sleep(2000L);

        for (int i = 1; i <= filas; i++) {
            Thread.sleep(100L);
            term.moveTextUp(1);
        }

        Thread.sleep(RETARDO);
        pausa(0, "Press <ENTER> to return to menu");
    }

    /**
     * Demonstrates enabling and disabling cursor blinking.
     * 
     * @param term An ANSITerm object
     * @throws Exception In case of any error
     */
    private void parpadeoDelCursor(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.cursorShow();
        term.cursorChangeStyle(CursorStyles.CUR_BLO_EST);
        term.printAt("------------ Cursor blinking ------------", 1, 1);
        String msg = "(1/2) - The cursor starts blinking.";
        term.printAt(msg, 2, 1);
        term.cursorBlink();
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        msg = "(2/2) - Turn off cursor blinking";
        term.printAt(msg, 2, 1);
        term.cursorNoBlink();
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, "Press <ENTER> to return to menu");
    }

    /*
    Utility methods.
     */
    /**
     * Pauses the program for a specified number of milliseconds and optionally 
     * displays a message. If no message is provided, it defaults to 
     * "Press &lt;ENTER&gt; to continue..." on the last line of the terminal.
     * 
     * If 0 milliseconds are specified, the function waits for the user to 
     * press ENTER. Otherwise, it informs the user that the program will 
     * continue after the specified delay and then pauses.
     * 
     * @param milisegundos The duration of the pause in milliseconds. If 0, 
     * the function waits for the user to press ENTER.
     * @param msg The message to display to the user.
     * @throws Exception In case of any error.
     */
    private void pausa(long milisegundos, String msg) throws Exception {
        ANSITerm t = new ANSITerm();
        if (msg == null || msg.isEmpty()) {
            msg = "\nPress <ENTER> to continue...";
        }

        TerminalSize ts = t.getTerminalSize();
        int ultimaLinea = ts.getLineas();

        if (milisegundos == 0) {
            t.printAt(msg, ultimaLinea - 1, 1);
            Scanner sc = new Scanner(new BufferedInputStream(System.in));
            sc.nextLine();
            return;
        }
        t.printAt("The program will continue in " + milisegundos + " milliseconds...",
            ultimaLinea - 1, 1);
        Thread.sleep(milisegundos);
    }

    /**
     * Pauses the program for a specified number of milliseconds. If 0 is 
     * specified, it waits for the user to press ENTER. No message is 
     * displayed.
     * 
     * @param milisegundos The duration of the pause in milliseconds. If 0, 
     * the function waits for the user to press ENTER.
     * @throws Exception In case of any error.
     */
    private void pausaSinMensaje(long milisegundos) throws Exception {
        ANSITerm t = new ANSITerm();
        t.moveCursorToBegin();
        if (milisegundos == 0) {
            Scanner sc = new Scanner(new BufferedInputStream(System.in));
            sc.nextLine();
            return;
        }
        Thread.sleep(milisegundos);
    }

    /**
     * Displays a block of random text starting at 'lineaInicial', with 
     * 'lineas' number of rows and 'cols' number of columns per row.
     * 
     * @param term An ANSITerm object
     * @param lineaInicial The starting line in the terminal.
     * @param lineas The number of lines in the text block.
     * @param cols The number of columns (characters) in each line.
     */
    private void imprimeBloqueTexto(ANSITerm term, int lineaInicial, int lineas, int cols) {
        int car;
        char ascii;
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();

        for (int line = lineaInicial; line < (lineaInicial + lineas); line++) {
            for (int col = 1; col < cols; col++) {
                car = rnd.nextInt(94) + 32;
                ascii = (char) car;
                sb.append(ascii);
            }
            term.printAt(sb.toString(), line, 1);
            sb.setLength(0);
        }
    }

    /**
     * Prints a string 'msg' starting at 'linea, columna' character by 
     * character with the specified delay in 'milisegundos'.
     * 
     * @param msg The string to print.
     * @param linea The terminal line where the string will be printed.
     * @param columna The terminal column where the string will be printed.
     * @param term An ANSITerm object.
     * @param milisegundos The delay between printing each character in milliseconds.
     */
    private void imprimeConLapso(String msg,
            int linea,
            int columna,
            ANSITerm term,
            long milisegundos) {
        for (int i = 0; i < msg.length(); i++) {
            term.printAt(String.valueOf(msg.charAt(i)), linea, columna + i);
            try {
                Thread.sleep(milisegundos);
            } catch (InterruptedException ex) {
            }
        }
    }
}
