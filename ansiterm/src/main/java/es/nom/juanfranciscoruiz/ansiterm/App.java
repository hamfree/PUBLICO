package es.nom.juanfranciscoruiz.ansiterm;

import com.sun.jna.LastErrorException;
import es.nom.juanfranciscoruiz.ansiterm.util.Menu;
import es.nom.juanfranciscoruiz.ansiterm.util.UnclosableInputStreamDecorator;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que prueba las funcionalidades de la clase ANSITerm (Códigos ANSI)
 * Falta por probar la combinación de colores y estilos.
 *
 * @author juanf
 */
public class App {

    public static final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * Inicio de la aplicación
     * @param args 
     */
    public static void main(String[] args) {
        ANSITerm term = null;
        try {
            term = new ANSITerm();
            App app = new App();
            Menu menu = new Menu();
            List<String> opciones = new ArrayList<>();
            long resp;

            opciones.add("1. Test modo crudo consola");
            opciones.add("2. Test recuperación posicion del cursor");
            opciones.add("3. Test obtención tamaño de la pantalla");
            opciones.add("4. Test dibujo de rectángulo");
            opciones.add("5. Borrados de texto desde el cursor");
            opciones.add("6. Estilos del texto");
            opciones.add("7. Estilos múltiples del texto");
            opciones.add("8. Colores del texto");
            opciones.add("9. Paleta de 256 colores del texto");
            opciones.add("10. Estilos del cursor");
            opciones.add("11. Parpadeo del cursor");
            opciones.add("12. Muestra movimiento del cursor");
            menu.setEsMenuInicio(true);
            menu.setTitulo("Test de la libreria ANSITerm");
            
            menu.setOpciones(opciones);

            do {
                term.clearScreen();
                term.moveCursorToBegin();
                if (menu.getMensaje().isBlank()) {
                    menu.setMensaje("Ejecutándose en ".concat(System.getProperty("os.name")));
                }
                
                menu.mostrar();
                resp = menu.esperarRespuesta(null);
                if (resp == 1L) {
                    menu.setMensaje("");
                    app.habilitaModoRawTeclado(term);
                } else if (resp == 2L) {
                    menu.setMensaje("");
                    app.recuperaPosicionCursor(term, 1L);
                } else if (resp == 3L) {
                    menu.setMensaje("");
                    app.lineasYColumnasPantalla(term);
                } else if (resp == 4L) {
                    menu.setMensaje("");
                    app.dibujaRectangulo(term);
                } else if (resp == 5L) {
                    menu.setMensaje("");
                    app.borradosDesdeCursor(term);
                } else if (resp == 6L) {
                    menu.setMensaje("");
                    app.estilos(term);
                } else if (resp == 7L) {
                    menu.setMensaje("");
                    app.estilosMultiples(term);
                } else if (resp == 8L) {
                    menu.setMensaje("");
                    app.colores(term);
                } else if (resp == 9L) {
                    menu.setMensaje("");
                    app.colores256(term);
                } else if (resp == 10L) {
                    menu.setMensaje("");
                    app.estilosDelCursor(term);
                } else if (resp == 11L) {
                    menu.setMensaje("");
                    app.parpadeoDelCursor(term);
                } else if (resp == 12L) {
                    menu.setMensaje("");
                    app.muestraMovimientoCursor(term, 200L);
                } else {
                    logger.warn("El usuario ha introducido {}", resp);
                }
            } while (resp != 0L);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
            System.exit(-1);
        } finally {
            // Por si algo va mal ... y no dejar el terminal inútil.
            if (term != null) {
                term.clearScreen();
                term.moveCursorToBegin();
                term.resetScreen();
            }
            System.exit(0);
        }
    }

    // Metodos de utilidad para mostrar las funcionalidades.
    /**
     * Realiza una prueba de captación de teclas una vez habilitado el modo 
     * 'raw' de la terminal. Muestra las teclas que se pulsan en un bucle hasta
     * que se pulsa la tecla "q"
     * 
     * @param term Un objeto ANSITerm
     * @throws Exception En caso de que se produzca algún error
     */
    private void habilitaModoRawTeclado(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Modo RAW del teclado ------------", 1, 1);

        try {
            pausa(2000L, null);
            term.getOsCall().enableRawMode();

            term.printAt("Pulse teclas. Pulse 'q' para salir", 2, 1);
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
            e.printStackTrace();
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
     * Muestra en el terminal el movimiento del cursor con el retardo en milisegundos
     * entre cada movimiento. La 'ruta' será un rectángulo teniendo en cuenta las
     * dimensiones actuales del terminal.
     * 
     * @param term Un objeto ANSITerm
     * @param retardo  Un long con el retardo en milisegundos
     * @throws Exception En caso de que se produzca algún error
     */
    private void muestraMovimientoCursor(ANSITerm term, long retardo) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        TerminalSize screenSize = term.getTerminalSize();
        term.printAt("------------ Moviendo el cursor ------------", 1, 1);

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
     * Va imprimiendo un carácter 'X' en cada posición de la terminal y por cada
     * movimiento del cursor recupera su posición imprimiéndola en la última 
     * línea de la terminal.
     * 
     * @param term Un objeto ANSITerm
     * @param retardo Un long con el retardo en milisegundos
     * @throws Exception En caso de que se produzca algún error
     */
    private void recuperaPosicionCursor(ANSITerm term, long retardo) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------  Recuperando la posición del cursor ------------ ", 1, 1);
        TerminalSize screenSize = term.getTerminalSize();

        term.cursorShow();
        term.cursorChangeStyle(CursorStyles.CUR_BLO_EST);

        for (int lin = 2; lin < screenSize.getLineas() - 3; lin++) {
            for (int col = 1; col <= screenSize.getColumnas(); col++) {
                Posicion p = new Posicion(1, 1);
                term.printAt("X", lin, col);
                try {
                    p = term.getCursorPosition();
                } catch (LastErrorException e) {
                    logger.error(String.valueOf(e.getErrorCode()));
                    System.out.println(e.getErrorCode());
                    System.out.println(e.getMessage());
                }
                pausaSinMensaje(retardo);
                term.printAt("Posicion del cursor: columna : ", screenSize.getLineas() - 2, 1);
                term.deleteFromCursorToEndLine();
                term.printAt(p.getCol() + ", fila: " + p.getLin(), screenSize.getLineas() - 2, 31);
                pausaSinMensaje(retardo);
            }
        }
        pausa(0, "Pulse <INTRO> para volver al menú");
    }

    /**
     * 
     * @param term Un objeto ANSITerm
     * @throws Exception En caso de que se produzca algún error
     */
    private void dibujaRectangulo(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        TerminalSize ts = term.getTerminalSize();

        term.printAt("------------ Impresion de texto en coordenadas específicas ------------", 1, 1);
        term.printAt("Se va a dibujar un rectángulo en pantalla:", 2, 1);
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

        pausa(0, "Pulse <INTRO> para volver al menú");
    }

    /**
     * 
     * @param term Un objeto ANSITerm
     * @throws Exception Exception En caso de que se produzca algún error
     */
    private void lineasYColumnasPantalla(ANSITerm term) throws Exception {

        String resp = "";
        while (!resp.equals("q")) {
            term.clearScreen();
            term.moveCursorToBegin();
            term.printAt("------------ Tamaño de la pantalla ------------", 1, 1);
            term.printAt("Pruebe a redimensionar la ventana de la terminal.", 2, 1);
            term.printAt("Después pulse INTRO. Se mostrará el tamaño de la terminal.", 3, 1);
            term.printAt("Pulse q y después INTRO para volver al menú", 4, 1);
            term.printAt("> : ", 5, 1);
            Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in));
            resp = sc.nextLine();
            TerminalSize ts = term.getOsCall().getTerminalSize();
            term.printAt("El tamaño de la pantalla es :"
                    + ts.getLineas()
                    + " líneas y "
                    + ts.getColumnas()
                    + " columnas.", ts.getLineas() - 2, 1);
            pausa(0, null);
        }

    }

    /**
     * 
     * @param term Un objeto ANSITerm
     * @throws Exception Exception En caso de que se produzca algún error
     */
    private void estilos(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Estilos aplicables al texto ------------", 1, 1);

        term.printAt(term.setBold("Frase en negrita"), 2, 10);
        term.printAt(term.setDim("Frase atenuada"), 3, 10);
        term.printAt(term.setItalic("Frase en cursiva"), 4, 10);
        term.printAt(term.setBlink("Frase intermitente"), 5, 10);
        term.printAt(term.setInverse("Frase con los colores invertidos"), 6, 10);
        term.printAt(term.setHidden("Mensaje oculto"), 7, 10);
        pausa(0, "Pulse <INTRO> para volver al menú");
    }

    /**
     * 
     * @param term Un objeto ANSITerm
     * @throws Exception Exception En caso de que se produzca algún error
     */
    private void estilosMultiples(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Estilos múltiples  aplicables al texto ------------", 1, 1);

        // Negrita con otros estilos
        String mensaje = "Frase en NEGRITA con varios estilos";
        term.printAt("La frase siguiente estará en negrita y atenuada", 2, 5);
        term.printAt(term.setStyles(true, true, false, false, false, false, false, mensaje), 3, 10);
        term.printAt("La frase siguiente estará en negrita e itálica", 4, 5);
        term.printAt(term.setStyles(true, false, true, false, false, false, false, mensaje), 5, 10);
        term.printAt("La frase siguiente estará en negrita y subrayada", 6, 5);
        term.printAt(term.setStyles(true, false, false, true, false, false, false, mensaje), 7, 10);
        term.printAt("La frase siguiente estará en negrita e intermitente", 8, 5);
        term.printAt(term.setStyles(true, false, false, false, true, false, false, mensaje), 9, 10);
        term.printAt("La frase siguiente estará en negrita y con los colores invertidos", 10, 5);
        term.printAt(term.setStyles(true, false, false, false, false, true, false, mensaje), 11, 10);
        term.printAt("La frase siguiente estará en negrita y tachada", 12, 5);
        term.printAt(term.setStyles(true, false, false, false, false, false, true, mensaje), 13, 10);

        // Dim con otros estilos
        mensaje = "Frase ATENUADA con varios estilos";
        term.printAt("La frase siguiente estará atenuada y en negrita", 14, 5);
        term.printAt(term.setStyles(true, true, false, false, false, false, false, mensaje), 15, 10);
        term.printAt("La frase siguiente estará atenuada y en itálica", 16, 5);
        term.printAt(term.setStyles(false, true, true, false, false, false, false, mensaje), 17, 10);
        term.printAt("La frase siguiente estará atenuada y subrayada", 18, 5);
        term.printAt(term.setStyles(false, true, false, true, false, false, false, mensaje), 19, 10);
        term.printAt("La frase siguiente estará atenuada e intermitente", 20, 5);
        term.printAt(term.setStyles(false, true, false, false, true, false, false, mensaje), 21, 10);
        term.printAt("La frase siguiente estará atenuada y con los colores invertidos", 22, 5);
        term.printAt(term.setStyles(false, true, false, false, false, true, false, mensaje), 23, 10);
        term.printAt("La frase siguiente estará atenuada y tachada", 24, 5);
        term.printAt(term.setStyles(false, true, false, false, false, false, true, mensaje), 25, 10);

        pausa(0, null);
        term.clearScreen();
        term.printAt("------------ Estilos múltiples  aplicables al texto ------------", 1, 1);
        
        // Cursiva con otros estilos
        mensaje = "Frase en CURSIVA con varios estilos";
        term.printAt("La frase siguiente estará en italica y atenuada", 2, 5);
        term.printAt(term.setStyles(false, true, true, false, false, false, false, mensaje), 3, 10);
        term.printAt("La frase siguiente estará en italica y negrita", 4, 5);
        term.printAt(term.setStyles(true, false, true, false, false, false, false, mensaje), 5, 10);
        term.printAt("La frase siguiente estará en italica y subrayada", 6, 5);
        term.printAt(term.setStyles(false, false, true, true, false, false, false, mensaje), 7, 10);
        term.printAt("La frase siguiente estará en italica e intermitente", 8, 5);
        term.printAt(term.setStyles(false, false, true, false, true, false, false, mensaje), 9, 10);
        term.printAt("La frase siguiente estará en italica y con los colores invertidos", 10, 5);
        term.printAt(term.setStyles(false, false, true, false, false, true, false, mensaje), 11, 10);
        term.printAt("La frase siguiente estará en italica y tachada", 12, 5);
        term.printAt(term.setStyles(false, false, true, false, false, false, true, mensaje), 13, 10);

        // Subrayada con otros estilos
        mensaje = "Frase SUBRAYADA con varios estilos";
        term.printAt("La frase siguiente estará subrayada y en negrita", 14, 5);
        term.printAt(term.setStyles(true, false, false, true, false, false, false, mensaje), 15, 10);
        term.printAt("La frase siguiente estará subrayada y en itálica", 16, 5);
        term.printAt(term.setStyles(false, false, true, true, false, false, false, mensaje), 17, 10);
        term.printAt("La frase siguiente estará subrayada y atenuada", 18, 5);
        term.printAt(term.setStyles(false, true, false, true, false, false, false, mensaje), 19, 10);
        term.printAt("La frase siguiente estará subrayada e intermitente", 20, 5);
        term.printAt(term.setStyles(false, false, false, true, true, false, false, mensaje), 21, 10);
        term.printAt("La frase siguiente estará subrayada y con los colores invertidos", 22, 5);
        term.printAt(term.setStyles(false, false, false, true, false, true, false, mensaje), 23, 10);
        term.printAt("La frase siguiente estará subrayada y tachada", 24, 5);
        term.printAt(term.setStyles(false, false, false, true, false, false, true, mensaje), 25, 10);

        pausa(0, null);

        term.clearScreen();
        term.printAt("------------ Estilos múltiples  aplicables al texto ------------", 1, 1);
        
        // Intermitente con otros estilos
        mensaje = "Frase INTERMITENTE con varios estilos";
        term.printAt("La frase siguiente estará intermitente y atenuada", 2, 5);
        term.printAt(term.setStyles(false, true, false, false, true, false, false, mensaje), 3, 10);
        term.printAt("La frase siguiente estará intermitente y negrita", 4, 5);
        term.printAt(term.setStyles(true, false, false, false, true, false, false, mensaje), 5, 10);
        term.printAt("La frase siguiente estará intermitente y subrayada", 6, 5);
        term.printAt(term.setStyles(false, false, false, true, true, false, false, mensaje), 7, 10);
        term.printAt("La frase siguiente estará intermitente e intermitente", 8, 5);
        term.printAt(term.setStyles(false, false, false, false, true, false, false, mensaje), 9, 10);
        term.printAt("La frase siguiente estará intermitente y con los colores invertidos", 10, 5);
        term.printAt(term.setStyles(false, false, false, false, true, true, false, mensaje), 11, 10);
        term.printAt("La frase siguiente estará intermitente y tachada", 12, 5);
        term.printAt(term.setStyles(false, false, false, false, true, false, true, mensaje), 13, 10);

        // Invertida con otros estilos
        mensaje = "Frase INVERTIDA con varios estilos";
        term.printAt("La frase siguiente estará invertida y en negrita", 14, 5);
        term.printAt(term.setStyles(true, false, false, false, false, true, false, mensaje), 15, 10);
        term.printAt("La frase siguiente estará invertida y en itálica", 16, 5);
        term.printAt(term.setStyles(false, false, true, false, false, true, false, mensaje), 17, 10);
        term.printAt("La frase siguiente estará invertida y atenuada", 18, 5);
        term.printAt(term.setStyles(false, true, false, false, false, true, false, mensaje), 19, 10);
        term.printAt("La frase siguiente estará invertida e intermitente", 20, 5);
        term.printAt(term.setStyles(false, false, false, false, true, true, false, mensaje), 21, 10);
        term.printAt("La frase siguiente estará invertida", 22, 5);
        term.printAt(term.setStyles(false, false, false, false, false, true, false, mensaje), 23, 10);
        term.printAt("La frase siguiente estará invertida y tachada", 24, 5);
        term.printAt(term.setStyles(false, false, false, false, false, true, true, mensaje), 25, 10);

        pausa(0, null);

        term.clearScreen();
        term.printAt("------------ Estilos múltiples  aplicables al texto ------------", 1, 1);
        
        // Tachada con otros estilos
        mensaje = "Frase TACHADA con varios estilos";
        term.printAt("La frase siguiente estará tachada y atenuada", 2, 5);
        term.printAt(term.setStyles(false, true, false, false, false, false, true, mensaje), 3, 10);
        term.printAt("La frase siguiente estará tachada y negrita", 4, 5);
        term.printAt(term.setStyles(true, false, false, false, false, false, true, mensaje), 5, 10);
        term.printAt("La frase siguiente estará tachada y subrayada", 6, 5);
        term.printAt(term.setStyles(false, false, false, true, false, false, true, mensaje), 7, 10);
        term.printAt("La frase siguiente estará tachada e intermitente", 8, 5);
        term.printAt(term.setStyles(false, false, false, false, true, false, true, mensaje), 9, 10);
        term.printAt("La frase siguiente estará tachada y con los colores invertidos", 10, 5);
        term.printAt(term.setStyles(false, false, false, false, false, true, true, mensaje), 11, 10);
        term.printAt("La frase siguiente estará tachada", 12, 5);
        term.printAt(term.setStyles(false, false, false, false, false, false, true, mensaje), 13, 10);

        pausa(0, "Pulse <INTRO> para volver al menú");
    }

    /**
     * 
     * @param term Un objeto ANSITerm
     * @throws Exception Exception En caso de que se produzca algún error
     */
    private void colores(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();

        term.printAt("------------ Colores aplicables al texto y al fondo ------------", 1, 1);
        term.printAt("Colores en primer plano", 2, 1);

        term.linefeed();
        term.printAt(term.setColor(Color.AMARILLO, "Frase en amarillo"), 4, 1);
        term.printAt(term.setColor(Color.AZUL, "Frase en azul"), 5, 1);
        term.printAt(term.setColor(Color.BLANCO, "Frase en blanco"), 6, 1);
        term.printAt(term.setColor(Color.CIAN, "Frase en cian"), 7, 1);
        term.printAt(term.setColor(Color.MAGENTA, "Frase en magenta"), 8, 1);
        term.printAt(term.setColor(Color.NEGRO, "Frase en negro"), 9, 1);
        term.printAt(term.setColor(Color.ROJO, "Frase en rojo"), 10, 1);
        term.printAt(term.setColor(Color.VERDE, "Frase en verde"), 11, 1);
        term.linefeed();
        term.printAt("Colores de fondo", 13, 1);
        term.linefeed();
        term.printAt(term.setBackgroundColor(BGColor.AMARILLO, "Frase con el fondo en amarillo"), 15, 1);
        term.printAt(term.setBackgroundColor(BGColor.AZUL, "Frase con el fondo en azul"), 16, 1);
        term.printAt(term.setBackgroundColor(BGColor.BLANCO, "Frase con el fondo en blanco"), 17, 1);
        term.printAt(term.setBackgroundColor(BGColor.CIAN, "Frase con el fondo en cian"), 18, 1);
        term.printAt(term.setBackgroundColor(BGColor.MAGENTA, "Frase con el fondo en magenta"), 19, 1);
        term.printAt(term.setBackgroundColor(BGColor.NEGRO, "Frase con el fondo en negro"), 20, 1);
        term.printAt(term.setBackgroundColor(BGColor.ROJO, "Frase con el fondo en rojo"), 21, 1);

        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Colores aplicables al texto y al fondo ------------", 1, 1);
        term.printAt("Colores brillantes en primer plano", 2, 1);
        term.linefeed();
        term.printAt(term.setColor(Color.AMARILLO_BRILLANTE, "Frase en amarillo"), 4, 1);
        term.printAt(term.setColor(Color.AZUL_BRILLANTE, "Frase en azul"), 5, 1);
        term.printAt(term.setColor(Color.BLANCO_BRILLANTE, "Frase en blanco"), 6, 1);
        term.printAt(term.setColor(Color.CIAN_BRILLANTE, "Frase en cian"), 7, 1);
        term.printAt(term.setColor(Color.MAGENTA_BRILLANTE, "Frase en magenta"), 8, 1);
        term.printAt(term.setColor(Color.NEGRO_BRILLANTE, "Frase en negro"), 9, 1);
        term.printAt(term.setColor(Color.ROJO_BRILLANTE, "Frase en rojo"), 10, 1);
        term.printAt(term.setColor(Color.VERDE_BRILLANTE, "Frase en verde"), 11, 1);
        term.linefeed();
        term.printAt("Colores brillantes para el fondo", 13, 1);
        term.linefeed();
        term.printAt(term.setBackgroundColor(BGColor.AMARILLO_BRILLANTE, "Frase con el fondo en amarillo"), 15, 1);
        term.printAt(term.setBackgroundColor(BGColor.AZUL_BRILLANTE, "Frase con el fondo en azul"), 16, 1);
        term.printAt(term.setBackgroundColor(BGColor.BLANCO_BRILLANTE, "Frase con el fondo en blanco"), 17, 1);
        term.printAt(term.setBackgroundColor(BGColor.CIAN_BRILLANTE, "Frase con el fondo en cian"), 18, 1);
        term.printAt(term.setBackgroundColor(BGColor.MAGENTA_BRILLANTE, "Frase con el fondo en magenta"), 19, 1);
        term.printAt(term.setBackgroundColor(BGColor.NEGRO_BRILLANTE, "Frase con el fondo en negro"), 20, 1);
        term.printAt(term.setBackgroundColor(BGColor.ROJO_BRILLANTE, "Frase con el fondo en rojo"), 21, 1);
        term.printAt(term.setBackgroundColor(BGColor.VERDE_BRILLANTE, "Frase con el fondo en verde"), 22, 1);

        pausa(0, "Pulse <INTRO> para volver al menú");

    }

    /**
     * 
     * @param term Un objeto ANSITerm
     * @throws Exception Exception En caso de que se produzca algún error
     */
    private void colores256(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Muestra la paleta de 256 colores ------------", 1, 1);

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

        pausa(0, "Pulse <INTRO> para volver al menú");
    }

    
    /**
     * 
     * @param term Un objeto ANSITerm
     * @throws Exception Exception En caso de que se produzca algún error
     */
    private void estilosDelCursor(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.cursorShow();
        term.printAt("------------ Estilos del cursor ------------", 1, 1);
        String msg = "(1/9) - Forma de cursor de barra estable";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_BAR_ES);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Estilos del cursor ------------", 1, 1);
        msg = "(2/9) - Forma de cursor de barra parpadeante";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_BAR_PAR);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Estilos del cursor ------------", 1, 1);
        msg = "(3/9) - Forma de cursor de bloque estable";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_BLO_EST);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Estilos del cursor ------------", 1, 1);
        msg = "(4/9) - Forma de cursor de bloque parpadeante";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_BLO_PAR);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Estilos del cursor ------------", 1, 1);
        msg = "(5/9) - Forma de cursor de subrayado estable";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_SUB_EST);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Estilos del cursor ------------", 1, 1);
        msg = "(6/9) - Forma de cursor de subrayado parpadeante";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_SUB_PAR);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Estilos del cursor ------------", 1, 1);
        msg = "(7/9) - Forma de cursor establecida por el usuario";
        term.printAt(msg, 2, 1);
        term.cursorChangeStyle(CursorStyles.CUR_USU);
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Estilos del cursor ------------", 1, 1);
        msg = "(8/9) - Oculta el cursor";
        term.printAt(msg, 2, 1);
        term.cursorHide();
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Estilos del cursor ------------", 1, 1);
        msg = "(9/9) - Muestra el cursor";
        term.printAt(msg, 2, 1);
        term.cursorShow();
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, "Pulse <INTRO> para volver al menú");
    }

    /**
     * 
     * @param term Un objeto ANSITerm
     * @throws Exception Exception En caso de que se produzca algún error
     */
    private void borradosDesdeCursor(ANSITerm term) throws Exception {
        String msg = "El proceso continuará automáticamente cada 4 segundos. ¡No pulse ninguna tecla!";
        int columnas = term.getTerminalSize().getColumnas();
        int filas = term.getTerminalSize().getLineas();
        final long RETARDO = 4000L;

        term.clearScreen();
        term.moveCursorToBegin();
        term.cursorShow();
        term.cursorChangeStyle(CursorStyles.CUR_BLO_PAR);
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(1/13) Borrado desde el cursor hasta el principio de la pantalla", 2, 1);
        term.printAt(msg, 3, 1);

        imprimeBloqueTexto(term, 5, filas - 5, columnas);
        term.moveCursorToXY(12, 60);
        Thread.sleep(RETARDO);
        term.deleteFromCursorToBeginScreen();
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(2/13) Borrado desde el cursor hasta el final de la pantalla", 2, 1);
        term.printAt(msg, 3, 1);

        imprimeBloqueTexto(term, 5, filas - 5, columnas);
        term.moveCursorToXY(12, 60);
        Thread.sleep(RETARDO);
        term.deleteFromCursorToEndScreen();
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(3/13) Borrado desde el cursor hasta el principio de la línea", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        term.deleteFromCursorToBeginLine();
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(4/13) Borrado desde el cursor hasta el final de la línea", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        term.deleteFromCursorToEndLine();
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(5/13) Borrado de la linea donde se encuentra el cursor", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 3, columnas);
        term.moveCursorToXY(6, 60);
        Thread.sleep(RETARDO);
        term.deleteLine();
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(6/13) Borrado de 5 líneas incluyendo la que contiene el cursor", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 15, columnas);
        term.moveCursorToXY(6, 60);
        Thread.sleep(RETARDO);
        term.deleteLines(5);
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(7/13) Borrado de los 10 caracteres anteriores a la posición del cursor", 2, 1);
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
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(8/13) Borrado de los 20 caracteres siguientes a la posición del cursor", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        term.delChars(20);
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(9/13) Borra 20 caracteres desde la posición actual del cursor sobrescribiéndolos con un carácter de espacio.", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        term.delCharsWithSpaces(20);
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(10/13) Inserta 10 espacios en la posición actual del cursor. Desplaza todo el texto existente a la derecha", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 1, columnas);
        term.moveCursorToXY(5, 60);
        Thread.sleep(RETARDO);
        term.insertSpaces(10);
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(11/13) Inserta 10 lineas en la posición actual del cursor. La línea del cursor y las de debajo se desplazarán hacia abajo", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 5, 5, columnas);
        term.moveCursorToXY(7, 60);
        Thread.sleep(RETARDO);
        term.insertLines(10);
        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(12/13) Mueve " + filas + " lineas hacia abajo el texto", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 3, filas - 3, columnas);

        for (int i = 1; i <= filas; i++) {
            Thread.sleep(100L);
            term.moveTextDown(1);
        }

        Thread.sleep(RETARDO);

        term.clearScreen();
        term.moveCursorToBegin();
        term.printAt("------------ Borrados desde el cursor y scroll ------------", 1, 1);
        term.printAt("(13/13) Mueve " + filas + " lineas hacia arriba el texto", 2, 1);
        term.printAt(msg, 3, 1);
        imprimeBloqueTexto(term, 3, filas - 3, columnas);

        Thread.sleep(2000L);

        for (int i = 1; i <= filas; i++) {
            Thread.sleep(100L);
            term.moveTextUp(1);
        }

        Thread.sleep(RETARDO);
        pausa(0, "Pulse <INTRO> para volver al menú");
    }

    /**
     * 
     * @param term Un objeto ANSITerm
     * @throws Exception Exception En caso de que se produzca algún error
     */
    private void parpadeoDelCursor(ANSITerm term) throws Exception {
        term.clearScreen();
        term.moveCursorToBegin();
        term.cursorShow();
        term.cursorChangeStyle(CursorStyles.CUR_BLO_EST);
        term.printAt("------------ Parpadeo del cursor ------------", 1, 1);
        String msg = "(1/2) - Se activa el parpadeo del cursor";
        term.printAt(msg, 2, 1);
        term.cursorBlink();
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, null);

        msg = "(2/2) - Desactiva el parpadeo del cursor";
        term.printAt(msg, 2, 1);
        term.cursorNoBlink();
        imprimeConLapso(msg, 5, 1, term, 100L);
        pausa(0, "Pulse <INTRO> para volver al menú");
    }

    /*
    Métodos de utilidad.
     */
    /**
     * Realiza una pausa de tantos milisegundos como se pase por parámetro y 
     * muestra, opcionalmente un mensaje en pantalla. Si no se pasa el mensaje 
     * mostrará por defecto el mensaje "Pulse &lt;INTRO> para continuar..." en 
     * la última línea del terminal.
     * 
     * Si se indica 0 como parámetro 'milisegundos' la función esperará a que 
     * el usuario pulse INTRO para continuar. En caso contrario, se mostrará al
     * usuario que el programa continuará después de los milisegundos indicados
     * y hace la pausa.
     * 
     * @param milisegundos Long con los milisegundos que durará la pausa. Si 
     * indica 0 la función esperará que el usuario pulse INTRO para continuar.
     * @param msg Una cadena con el mensaje que se quiere mostrar al usuario.
     * @throws Exception En caso de que se produzca algún error.
     */
    private void pausa(long milisegundos, String msg) throws Exception {
        ANSITerm t = new ANSITerm();
        if (msg == null || msg.isEmpty()) {
            msg = "\nPulse <INTRO> para continuar...";
        }

        TerminalSize ts = t.getTerminalSize();
        int ultimaLinea = ts.getLineas();

        if (milisegundos == 0) {
            t.printAt(msg, ultimaLinea - 1, 1);
            Scanner sc = new Scanner(new BufferedInputStream(System.in));
            sc.nextLine();
            return;
        }
        t.printAt("El programa continuará en " + milisegundos + " milisegundos...", ultimaLinea - 1, 1);
        Thread.sleep(milisegundos);
    }

    /**
     * Realiza una pausa de tantos milisegundos como se pase por parámetro. Si 
     * se indica 0 esperará a que el usuario pulse INTRO para continuar. No 
     * muestra ningún mensaje por pantalla.
     * 
     * @param milisegundos Long con los milisegundos que durará la pausa. Si 
     * indica 0 la función esperará que el usuario pulse INTRO para continuar.
     * @throws InterruptedException En caso de que se interrumpa al método 
     * sleep()
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
     * Muestra por pantalla un bloque de texto aleatorio, que comienza en la 
     * línea 'lineaInicial', tendrá tantas líneas de tamaño como se indique en 
     * 'lineas' y cada línea del bloque tendrá tantas columnas como indique 
     * 'cols'.
     * @param term Un objeto ANSITerm
     * @param lineaInicial La línea inicial en el terminal donde comenzará el 
     * bloque de texto.
     * @param lineas Las líneas que tendrá el bloque de texto.
     * @param cols El número de columnas (caracteres) que tendrá cada línea del 
     * bloque de texto.
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
                sb.append(String.valueOf(ascii));
            }
            term.printAt(sb.toString(), line, 1);
            sb.setLength(0);
        }
    }

    /**
     * Imprime una cadena 'msg' a partir de la posición 'linea, columna' en el 
     * terminal carácter a carácter con el retardo indicado en 'milisegundos'.
     * indicado.
     * @param msg La cadena a imprimir
     * @param linea La línea del terminal donde se imprimirá la cadena
     * @param columna La columna del terminal donde se imprimirá la cadena
     * @param term Un objeto ANSIterm
     * @param milisegundos Retardo de impresión entre cada carácter en milisegundos
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
