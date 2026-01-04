package es.nom.juanfranciscoruiz.ansiterm;

import com.sun.jna.LastErrorException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Controla la salida del terminal usando códigos de escape ANSI. La terminal
 * debe soportar este estándar (Windows Terminal, xterm y otros emuladores de
 * terminal lo soportan). El terminal de consola de Windows (cmd.exe) no soporta
 * ANSI.
 * <p>
 * Más información sobre el uso de secuencias de escape ANSI y su uso en:<br><br>
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">Invisible-Island</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia</a>
 *
 * @author juanf
 */
public class ANSITerm {

    /**
     * Para trazas
     */
    public static final Logger logger = LoggerFactory.getLogger(ANSITerm.class);

    /**
     * Objeto de tipo ITerminal para poder hacer las llamadas a las funciones 
     * del SO donde se  ejecuta el programa y habilitar/deshabilitar capacidades 
     * de la consolano disponibles desde Java o las secuencias de control ANSI.
     */
    private ITerminal osCall;

    /**
     * Secuencia de escape ANSI
     */
    private static final String ESC = "\033";

    // Controles generales ASCII
    /**
     * Hace sonar la campana del terminal
     */
    private static final char BELL = 7;
    /**
     * Retrocede un caracter
     */
    private static final char BS = 8;
    /**
     * Tabulador horizontal
     */
    private static final char TAB = 9;
    /**
     * Salto de línea
     */
    private static final char LF = 10;

    /**
     * Tabulador vertical
     */
    private static final char VT = 11;
    /**
     * Formfeed o nueva página
     */
    private static final char FF = 12;
    /**
     * Retorno de carro
     */
    private static final char CR = 13;

    // Controles de movimiento del cursor
    /**
     * Mueve el cursor a la posición 0,0
     */
    private static final String MUE_CUR_00 = ESC + "[H";
    /**
     * Respuesta como ESC[#;#R de la posición del cursor
     */
    private static final String REC_POS_CUR = ESC + "[6n";
    /**
     * Mueve el cursor una línea hacia arriba, desplazándose si es necesario
     */
    private static final String MUE_CUR_1LIN_ARR = ESC + "M";
    /**
     * Salva la posición del cursor (Secuencia DEC)
     */
    private static final String SAL_POS_CUR = ESC + "7";
    /**
     * Restaura el cursor a su última posición salvada (Secuencia DEC)
     */
    private static final String RES_POS_CUR = ESC + "8";

    // Secuencias de borrado 
    /**
     * Borra desde el cursor hasta el final de la pantalla
     */
    private static final String BOR_CUR_FIN = ESC + "[0J";
    /**
     * Borra desde el cursor hasta el principio de la pantalla
     */
    private static final String BOR_CUR_PRI = ESC + "[1J";
    /**
     * Borra toda la pantalla
     */
    private static final String BOR_PAN = ESC + "[2J";
    /**
     * Borra desde el cursor hasta el final de la línea actual
     */
    private static final String BOR_CUR_FIN_LIN = ESC + "[0K";
    /**
     * Borra desde el cursor hasta el principio de la línea actual
     */
    private static final String BOR_CUR_PRI_LIN = ESC + "[1K";
    /**
     * Borra la linea actual entera (El borrado de una línea NO mueve el cursor)
     */
    private static final String BOR_CUR_LIN = ESC + "[2K";

    // Secuencias de control para colores y estilos
    /**
     * Reinicia todos los modos (estilos y colores)
     */
    private static final String REINICIA_ESTILOS = ESC + "[0m";
    /**
     * Inicia negritas.
     */
    private static final String INI_NEG = ESC + "[1m";
    /**
     * Finaliza negritas
     */
    private static final String FIN_NEG = ESC + "[22m";
    /**
     * Inicia modo oscuro/tenue.
     */
    private static final String INI_OSC = ESC + "[2m";
    /**
     * Finaliza modo oscuro/tenue.
     */
    private static final String FIN_OSC = ESC + "[22m";
    /**
     * Inicia el modo cursiva.
     */
    private static final String INI_CUR = ESC + "[3m";
    /**
     * Finaliza el modo cursiva.
     */
    private static final String FIN_CUR = ESC + "[23m";
    /**
     * Inicia el modo subrayado.
     */
    private static final String INI_SUB = ESC + "[4m";
    /**
     * Finaliza el modo subrayado.
     */
    private static final String FIN_SUB = ESC + "[24m";
    /**
     * Inicia modo intermitente
     */
    private static final String INI_INT = ESC + "[5m";
    /**
     * Finaliza modo intermitente
     */
    private static final String FIN_INT = ESC + "[25m";
    /**
     * Inicia modo inverso/reverso (colores intercambiados)
     */
    private static final String INI_REV = ESC + "[7m";
    /**
     * Finaliza modo inverso/reverso (colores intercambiados)
     */
    private static final String FIN_REV = ESC + "[27m";
    /**
     * Inicia modo invisible
     */
    private static final String INI_INV = ESC + "[8m";
    /**
     * Finaliza modo invisible
     */
    private static final String FIN_INV = ESC + "[28m";
    /**
     * Inicia el modo tachado.
     */
    private static final String INI_TAC = ESC + "[9m";
    /**
     * Finaliza el modo tachado.
     */
    private static final String FIN_TAC = ESC + "[29m";

    /**
     * El color de reinicio es el código de reinicio que restablece todos los
     * colores y efectos de texto. Use el color predeterminado de las
     * Enumeraciones Color y Bgcolor para restablecer solo los colores.
     * <p>
     * {@code @See} es.nom.juanfranciscoruiz.ansiterm.Color
     * {@code @See} es.nom.juanfranciscoruiz.ansiterm.BGcolor
     *
     */
    private static final String REINICIA = "0";

    /**
     * Color (hasta 256) del primer plano. Se usa ESC[38;5;{0-255}m
     */
    private static final String COL256 = ESC + "[38;5;";
    /**
     * Color (hasta 256) del fondo. Se usa ESC[48;5;{0-255}m
     */
    private static final String COL256_F = ESC + "[48;5;";

    // Códigos de control para el cursor
    /**
     * Oculta el cursor (DECTCEM)
     */
    private static final String CUR_INV = ESC + "[?25l";
    /**
     * Muestra el cursor (DECTCEM)
     */
    private static final String CUR_VIS = ESC + "[?25h";

    /**
     * Permite iniciar el parpadeo del cursor (ATT160)
     */
    private static final String CUR_PAR = ESC + "[?12h";
    /**
     * Permite detener el parpadeo del cursor. (ATT160)
     */
    private static final String CUR_NOPAR = ESC + "[?12l";

    // Códigos de posición de la ventanilla
    /**
     * Restaura la pantalla
     */
    private static final String RES_PAN = ESC + "c";
    /**
     * Salva la pantalla
     */
    private static final String SAV_PAN = ESC + "[?47h";
    /**
     * Habilita el buffer alternativo
     */
    private static final String HAB_ALT_BUF = ESC + "[?1049h";

    // Constantes para los mensajes de excepción
    private static final String EX_NO_MSG = "No hay mensaje, está vacío o solo contiene blancos";
    private static final String EX_NO_COL = "Color no válido";
    private static final String EX_NO_BACKCOL = "Color de fondo no válido";

    /**
     * Instancia un nuevo objeto Terminal.
     * Dependiendo del sistema operativo donde se ejecute la clase instanciará 
     * un objeto de WindowsTerminal o LinuxTerminal para cuando tenga que 
     * llamar a funciones de bajo nivel del sistema.
     * 
     * @throws java.lang.Exception En caso de que el sistema operativo no sea 
     * Windows o Linux
     */
    public ANSITerm() throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            osCall = new WindowsTerminal();
        } else if (os.contains("linux")) {
            osCall = new LinuxTerminal();
        }  else {
            throw new Exception("Sistema operativo no soportado");
        }
    }

    /**
     * Devuelve un objeto ITerminal con el que podremos acceder a los métodos 
     * de bajo nivel del terminal que se ejecuta en el sistema operativo actual.
     * @return un objeto ITerminal con los métodos adecuados de bajo nivel del
     * sistema operativo donde se ejecuta nuestro programa.
     */
    public ITerminal getOsCall(){
        return osCall;
    }
    
    
    /**
     * Habilita el modo 'raw' en la consola actual para
     * que puedan ser interpretadas las secuencias ANSI. 
     *
     * @throws LastErrorException En caso de que se produzca algún error en la
     * llamada o no se ejecute en Microsoft Windows.
     */
    public void enableRawMode() throws LastErrorException {
        osCall.enableRawMode();
    }

    /**
     * Deshabilita el modo 'raw' (y habilita el modo 'cooked' en la consola 
     * actual.
     *
     * @throws LastErrorException En caso de que se produzca algún error en la
     * llamada o no se ejecute en Microsoft Windows.
     */
    public void disableRawMode() throws LastErrorException {
        osCall.disableRawMode();
    }

    
    /**
     * Hace sonar la campana del terminal
     */
    public void bell() {
        System.out.print(BELL);
    }

    /**
     * Provoca un retroceso del cursor
     */
    public void backSpace() {
        System.out.print(BS);
    }

    /**
     * Genera una tabulación
     */
    public void tab() {
        System.out.print(TAB);
    }

    /**
     * Genera un salto de línea
     */
    public void linefeed() {
        System.out.print(LF);
    }

    /**
     * Genera una tabulación vertical
     */
    public void verticalTab() {
        System.out.print(VT);
    }

    /**
     * Genera un avance de formulario
     */
    public void formfeed() {
        System.out.print(FF);
    }

    /**
     * Genera un retorno de carro
     */
    public void carriagereturn() {
        // En el terminal de windows mueve el cursor al principio de la línea
        // Para hacer un salto de línea hay que hacer un linefeed() o usar 
        // el codigo de escape \n de Java
        System.out.print(CR);
    }

    /**
     * Borra el caracter anterior a la posición del cursor.
     */
    public void deleteCharacter() {
        // En Windows no funciona.
        char c = 127; //DEL (Delete character)
        System.out.print(c);
    }

    // Funciones del cursor
    /**
     * Mueve el cursor al principio del terminal (0,0)
     */
    public void moveCursorToBegin() {
        System.out.print(MUE_CUR_00);
    }

    /**
     * Mueve el cursor a la posición line, column del terminal
     *
     * @param line entero con la línea a la que mover el cursor
     * @param column entero con la columna a la que mover el cursor
     */
    public void moveCursorToXY(int line, int column) {
        String sec_ansi = ESC + "[" + line + ";" + column + "H";
        System.out.print(sec_ansi);
    }

    /**
     * Mueve el cursor a la posición del terminal indicada por p
     *
     * @param p Objeto Posicion que contiene la posició donde se moverá el
     * cursor
     */
    public void moveCursorToXY(Posicion p) {
        String sec_ansi = ESC + "[" + p.getCol() + ";" + p.getLin() + "H";
        System.out.print(sec_ansi);
    }

    /**
     * Devuelve la posición del cursor en pantalla
     *
     * @return un objeto Posicion con la posición actual del cursor en pantalla
     */
    public Posicion getCursorPosition() throws LastErrorException {
        try {
            // Hacemos que la consola no muestre los caracteres que se escriban
            // y que las pulsaciones de las teclas del usuario sean obtenidas
            // sin esperar a que pulse INTRO (modo raw)
            this.osCall.enableRawMode();

            System.out.print(REC_POS_CUR);

            StringBuilder result = new StringBuilder();
            int character;

            do {
                character = System.in.read();
                if (character == 27) {
                    result.append("^");
                } else {
                    result.append((char) character);
                }
            } while (character != 82); // 'R'

            Pattern pattern = Pattern.compile("\\^\\[(\\d+);(\\d+)R");
            Matcher matcher = pattern.matcher(result.toString());
            if (matcher.matches()) {
                return new Posicion(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)));
            } else {
                return new Posicion(1, 1);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return new Posicion(1, 1);
        } finally {
            // Hay que dejar la consola en el modo normal.
            this.osCall.disableRawMode();
        }
    }

    /**
     * Mueve el cursor una línea hacia arriba
     */
    public void moveCursorUp() {
        System.out.println(MUE_CUR_1LIN_ARR);
    }

    /**
     * Mueve el cursor hacia arriba tantas líneas como indiquemos en el
     * parámetro 'lines'<br>
     *
     * NOTA para los métodos:<br>
     * <ul>
     * <li>moveCursorUp(int lines)</li>
     * <li>moveCursorDown(int lines)</li>
     * <li>moveCursorRight(int cars)</li>
     * <li>moveCursorLeft(int cars)</li>
     *
     * </ul>
     * <ul>
     * <li>'lines' o 'cars' representa la distancia de traslado y es un
     * parámetro que es opcional para la secuencia de control (pero obligatorio
     * en la función, sise quiere omitir indique 0.</li>
     * <li>Si 'lines' o 'cars' se omite o es igual a 0, se tratará como 1.</li>
     * <li>'lines' o 'cars' no puede ser mayor que 32.767 (valor corto
     * máximo).</li>
     * <li>'lines' o 'cars' no puede ser negativo.</li>
     * </ul>
     *
     *
     * @param lines un entero con las líneas hacia arriba donde se moverá el
     * cursor
     */
    public void moveCursorUp(int lines) {
        // ESC[#A
        String sec_ansi = ESC + "[" + lines + "A";
        System.out.print(sec_ansi);
    }

    /**
     * Mueve el cursor hacia abajo tantas líneas como indiquemos en el parámetro
     * lines
     *
     * @see moveCursorUp
     * @param lines un entero con las líneas hacia abajo donde se moverá el
     * cursor
     */
    public void moveCursorDown(int lines) {
        // ESC[#B
        String sec_ansi = ESC + "[" + lines + "B";
        System.out.print(sec_ansi);
    }

    /**
     * Mueve el cursor hacia la derecha tantos caracteres como indiquemos en el
     * parámetro cars
     *
     * @see moveCursorUp
     * @param cars un entero con los caracteres hacia la derecha donde se moverá
     * el cursor
     */
    public void moveCursorRight(int cars) {
        // ESC[#C
        String sec_ansi = ESC + "[" + cars + "C";
        System.out.print(sec_ansi);
    }

    /**
     * Mueve el cursor hacia la izquierda tantos caracteres como indiquemos en
     * el parámetro cars
     *
     * @see moveCursorUp
     * @param cars un entero con los caracteres hacia la izquierda donde se
     * moverá el cursor
     */
    public void moveCursorLeft(int cars) {
        // ESC[#D
        String sec_ansi = ESC + "[" + cars + "D";
        System.out.print(sec_ansi);
    }

    /**
     * Oculta el cursor
     */
    public void cursorHide() {
        String sec_ansi = ESC + CUR_INV;
        System.out.print(sec_ansi);
    }

    /**
     * Muestra el cursor
     */
    public void cursorShow() {
        String sec_ansi = ESC + CUR_VIS;
        System.out.print(sec_ansi);
    }
    
    
    /**
     * Activa el parpadeo del cursor
     */
    public void cursorBlink() {
        String sec_ansi = ESC + CUR_PAR;
        System.out.print(sec_ansi);
    }
    
    /**
     * Desactiva el parpadeo del cursor
     */
    public void cursorNoBlink() {
        String sec_ansi = ESC + CUR_NOPAR;
        System.out.print(sec_ansi);
    }

    /**
     * Establece el estilo del cursor.
     *
     * @param style Uno de los estilos de cursor disponibles (Clase con
     * constantes estáticas)
     */
    public void cursorChangeStyle(String style) {
        this.cursorShow();
        if (style == null || style.isEmpty()) {
            throw new IllegalArgumentException("Estilo no válido");
        }

        if (!style.equals(CursorStyles.CUR_BAR_ES)
                && !style.equals(CursorStyles.CUR_BAR_PAR)
                && !style.equals(CursorStyles.CUR_BLO_EST)
                && !style.equals(CursorStyles.CUR_BLO_PAR)
                && !style.equals(CursorStyles.CUR_SUB_EST)
                && !style.equals(CursorStyles.CUR_SUB_PAR)
                && !style.equals(CursorStyles.CUR_USU)) {
            throw new IllegalArgumentException("Estilo no reconocido");
        }
        System.out.print(style);
    }

    /**
     * Desplaza el texto hacia arriba tantas líneas como se indique en 'lines'.
     * Opción también conocida como "Movimiento panorámico hacia abajo", las
     * nuevas líneas se rellenan desde la parte inferior de la pantalla.
     *
     * @param lines el número de líneas a desplazar
     */
    public void moveTextUp(int lines) {
        String sec_ansi = ESC + "[" + lines + "S";
        System.out.print(sec_ansi);
    }

    /**
     * Desplazarse hacia abajo tantas líneas como se indique en 'lines'. Opción
     * también conocida como "Movimiento panorámico hacia arriba", las nuevas
     * líneas se rellenan desde la parte superior de la pantalla.
     *
     * @param lines el número de líneas a desplazar
     */
    public void moveTextDown(int lines) {
        String sec_ansi = ESC + "[" + lines + "T";
        System.out.print(sec_ansi);
    }

    /**
     * Inserta 'cars' espacios en la posición actual del cursor, desplazando
     * todo el texto existente a la derecha. Asimismo, se quita el texto que
     * sale de la pantalla hacia la derecha.
     *
     * @param cars el número de espacios a insertar.
     */
    public void insertSpaces(int cars) {
        String sec_ansi = ESC + "[" + cars + "@";
        System.out.print(sec_ansi);
    }

    /**
     * Elimine 'cars' caracteres en la posición actual del cursor, desplazando
     * los caracteres de espacio desde el borde derecho de la pantalla.
     *
     * @param cars el número de caracteres a borrar.
     */
    public void delChars(int cars) {
        String sec_ansi = ESC + "[" + cars + "P";
        System.out.print(sec_ansi);
    }

    /**
     * Borre 'cars' caracteres de la posición actual del cursor
     * sobrescribiéndolos con un carácter de espacio.
     *
     * @param cars el número de caracteres a borrar.
     */
    public void delCharsWithSpaces(int cars) {
        String sec_ansi = ESC + "[" + cars + "X";
        System.out.print(sec_ansi);
    }

    /**
     * Inserta las líneas indicadas por 'lines' en el búfer de pantalla en la
     * posición del cursor. La línea en la que se encuentra el cursor y las
     * líneas que tiene debajo se desplazarán hacia abajo.
     *
     * @param lines el número de líneas a insertar.
     */
    public void insertLines(int lines) {
        String sec_ansi = ESC + "[" + lines + "L";
        System.out.print(sec_ansi);
    }

    /**
     * Elimina las líneas indicadas por 'lines' del búfer, comenzando por la
     * fila en la que se encuentra el cursor.
     *
     * @param lines el número de líneas a eliminar.
     */
    public void deleteLines(int lines) {
        String sec_ansi = ESC + "[" + lines + "M";
        System.out.print(sec_ansi);
    }

    /**
     * Salva la posición del cursor
     */
    public void saveCursorPos() {
        System.out.print(SAL_POS_CUR);

    }

    /**
     * Restaura la posición del cursor
     */
    public void restoreCursorPos() {
        System.out.print(RES_POS_CUR);
    }

    // Funciones de borrado
    /**
     * Elimina todo desde la posición del cursor hasta el final de la pantalla
     */
    public void deleteFromCursorToEndScreen() {
        System.out.print(BOR_CUR_FIN);
    }

    /**
     * Elimina todo desde la posición del cursor hasta el principio de la
     * pantalla
     */
    public void deleteFromCursorToBeginScreen() {
        System.out.print(BOR_CUR_PRI);
    }

    /**
     * Borra la pantalla
     */
    public void clearScreen() {
        System.out.print(BOR_PAN);
    }

    /**
     * Elimina todo desde la posición del cursor hasta el final de la línea
     * donde se encuentra.
     */
    public void deleteFromCursorToEndLine() {
        System.out.print(BOR_CUR_FIN_LIN);
    }

    /**
     * Elimina todo desde la posición del cursor hasta el principio de la línea
     * donde se encuentra.
     */
    public void deleteFromCursorToBeginLine() {
        System.out.print(BOR_CUR_PRI_LIN);
    }

    /**
     * Borra los posibles caracteres de la línea actual donde se encuentra el
     * cursor.
     */
    public void deleteLine() {
        System.out.print(BOR_CUR_LIN);
    }

    // Colores y estilos
    /**
     * Establece el modo negrita para la cadena que se le pase
     *
     * @param msg la cadena a poner en negrita
     * @return un String con la secuencia ANSI que, si se muestra en el
     * terminal, lo hará en negrita.
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setBold(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(INI_NEG)
                    .append(msg)
                    .append(ESC).append(FIN_NEG);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }
        return sb.toString();
    }

    /**
     * Establece el modo atenuado para la cadena que se le pase
     *
     * @param msg la cadena que se pondrá en modo atenuado
     * @return un String con la secuencia ANSI que, si se muestra en el
     * terminal, lo hará de forma atenuada.
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setDim(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(INI_OSC)
                    .append(msg)
                    .append(ESC).append(FIN_OSC);
        }

        return sb.toString();
    }

    /**
     * Establece el modo cursiva para la cadena que se le pase
     *
     * @param msg la cadena que se pondrá en modo cursiva
     * @return un String con la secuencia ANSI que, si se muestra en el
     * terminal, lo hará de forma cursiva.
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setItalic(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(INI_CUR)
                    .append(msg)
                    .append(ESC).append(FIN_CUR);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Establece el modo subrayado para la cadena que se le pase
     *
     * @param msg la cadena que se pondrá en modo subrayado
     * @return un String con la secuencia ANSI que, si se muestra en el
     * terminal, lo hará de forma subrayada.
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setUnderline(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(INI_SUB)
                    .append(msg)
                    .append(ESC).append(FIN_SUB);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Establece el modo intermitente/parpadeante para la cadena que se le pase
     *
     * @param msg la cadena que se pondrá en modo intermitente/parpadeante
     * @return un String con la secuencia ANSI que, si se muestra en el
     * terminal, lo hará de forma intermitente/parpadeante.
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setBlink(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(INI_INT)
                    .append(msg)
                    .append(ESC).append(FIN_INT);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Establece el modo inverso para la cadena que se le pase
     *
     * @param msg la cadena que se pondrá en modo inverso
     * @return un String con la secuencia ANSI que, si se muestra en el
     * terminal, lo hará de forma inversa (intercambio de colores del primer
     * plano/fondo).
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setInverse(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(INI_REV)
                    .append(msg)
                    .append(ESC).append(FIN_REV);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Establece el modo oculto para la cadena que se le pase
     *
     * @param msg la cadena que se pondrá en modo inverso
     * @return un String con la secuencia ANSI que, si se muestra en el
     * terminal, lo hará de forma oculta (no aparece nada en la pantalla, pero
     * se ocupa el espacio que tenga la cadena).
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setHidden(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(INI_INV)
                    .append(msg)
                    .append(ESC).append(FIN_INV);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Establece el modo tachado para la cadena que se le pase
     *
     * @param msg la cadena que se pondrá en modo tachado
     * @return un String con la secuencia ANSI que, si se muestra en el
     * terminal, aparecerá tachada.
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setStrikeThrough(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(INI_TAC)
                    .append(msg)
                    .append(ESC).append(FIN_TAC);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Combina varios estilos a la cadena que se le pase. <br>
     * <b>NOTA: No todas las combinaciones de estilos</b> producirán los
     * resultados previstos en <i>algunos terminales</i>.
     *
     * @param isBold Si es true pondrá la cadena en negritas
     * @param isDim Si es true pondrá la cadena de forma atenuada
     * @param isItalic Si es true pondrá la cadena en cursiva
     * @param isUnderline Si es true pondrá la cadena subrayada
     * @param isBlink Si es true pondrá la cadena intermitente/parpadeante
     * @param isInverse Si es true pondrá la cadena en modo inverso
     * @param isStrikeThrough Si es true pondrá la cadena con un 'tachado' por
     * encima.
     * @param msg la cadena a la que se aplicarán los estilos
     * @return un String con las secuencias ANSI necesarias para que, si luego
     * se imprime en el terminal, aparezca con los estilos solicitados.
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setStyles(boolean isBold, boolean isDim,
            boolean isItalic, boolean isUnderline,
            boolean isBlink, boolean isInverse,
            boolean isStrikeThrough, String msg) throws IllegalArgumentException {

        StringBuilder sb = new StringBuilder();
        boolean isMessageAdded = false;
        if (msg != null && !msg.isEmpty()) {
            if (isBold) {
                sb.append(setBold(msg));
                isMessageAdded = true;
            }
            if (isDim) {
                if (isMessageAdded) {
                    sb.insert(0, ESC + INI_OSC).append(ESC + FIN_OSC);
                } else {
                    sb.append(setDim(msg));
                    isMessageAdded = true;
                }
            }
            if (isItalic) {
                if (isMessageAdded) {
                    sb.insert(0, ESC + INI_CUR).append(ESC + FIN_CUR);
                } else {
                    sb.append(setItalic(msg));
                    isMessageAdded = true;
                }
            }
            if (isUnderline) {
                if (isMessageAdded) {
                    sb.insert(0, ESC + INI_SUB).append(ESC + FIN_SUB);
                } else {
                    sb.append(setUnderline(msg));
                    isMessageAdded = true;
                }
            }
            if (isBlink) {
                if (isMessageAdded) {
                    sb.insert(0, ESC + INI_INT).append(ESC + FIN_INT);
                } else {
                    sb.append(setBlink(msg));
                    isMessageAdded = true;
                }
            }
            if (isInverse) {
                if (isMessageAdded) {
                    sb.insert(0, ESC + INI_REV).append(ESC + FIN_REV);
                } else {
                    sb.append(setInverse(msg));
                    isMessageAdded = true;
                }
            }
            if (isStrikeThrough) {
                if (isMessageAdded) {
                    sb.insert(0, ESC + INI_TAC).append(ESC + FIN_TAC);
                } else {
                    sb.append(setStrikeThrough(msg));
                }
            }
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Establece el color del texto
     * @param color Una constante de la enumeración Color con el color
     * @param msg La cadena que se quiere colorear
     * @return una cadena con la secuencia ANSI apropiada para mostrar en la consola
     * el texto con el color indicado.
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setColor(Color color, String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        int iColor = Integer.parseInt(color.getAsString());
        if ((msg != null && !msg.isEmpty())) {
            sb.append(ESC).append("[")
                    .append(iColor)
                    .append("m")
                    .append(msg)
                    .append(ESC)
                    .append("[")
                    .append(Color.DEFECTO.getAsString())
                    .append("m");
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Establece un código de color de una paleta de 256 colores a la cadena
     * que se le pasa como parámetro.
     * 
     * @param color un entero entre 0 y 255 que contiene el código de color.
     * @param msg una cadena que recibirá la secuencia ANSI para darle el color 
     * indicado.
     * @return una cadena con la secuencia ANSI adecuada para mostrarse en el color 
     * indicado en el terminal.
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setColor256(int color, String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();

        if (color < 0 || color > 255) {
            throw new IllegalArgumentException(EX_NO_COL);
        }

        if (msg == null || msg.isEmpty()) {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        sb.append(ESC).append(COL256)
                .append(color)
                .append("m")
                .append(msg)
                .append(ESC)
                .append("[")
                .append(Color.DEFECTO.getAsString())
                .append("m");

        return sb.toString();
    }

    /**
     * Establece un código de color de una paleta de 256 colores para el fondo 
     * de la cadena que se le pasa como parámetro.
     * @param color un entero entre 0 y 255 que contiene el código de color.
     * @param msg una cadena que recibirá la secuencia ANSI para darle el color 
     * indicado a su fondo.
     * @return una cadena con la secuencia ANSI adecuada que mostrará el color 
     * indicado en su fondo en el terminal.
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setBackgroundColor256(int color, String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();

        if (color <= 0 || color > 255) {
            throw new IllegalArgumentException(EX_NO_COL);
        }

        if (msg == null || msg.isEmpty()) {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        sb.append(ESC).append(COL256_F)
                .append(color)
                .append("m")
                .append(msg)
                .append(ESC)
                .append("[")
                .append(Color.DEFECTO.getAsString())
                .append("m");

        return sb.toString();
    }

    /**
     * Establece el color de fondo de la cadena que se le pasa
     *
     * @param color Enum con el color de fondo
     * @param msg String con la cadena a colorear
     * @return un String con las secuencias de escape ANSI que colorean la
     * cadena segun lo solicitado
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setBackgroundColor(BGColor color, String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        int iColor = Integer.parseInt(color.getAsString());
        if (iColor >= 40 && iColor <= 47) {
            if ((msg != null && !msg.isEmpty())) {
                sb.append(ESC).append("[")
                        .append(iColor)
                        .append("m")
                        .append(msg)
                        .append(ESC)
                        .append("[")
                        .append(BGColor.DEFECTO.getAsString())
                        .append("m");
            } else {
                throw new IllegalArgumentException(EX_NO_MSG);
            }
        } else {
            throw new IllegalArgumentException(EX_NO_BACKCOL);
        }

        return sb.toString();
    }

    /**
     * Establece los colores de primer plano y de fondo de la cadena que se le
     * pasa
     *
     * @param color Enum con el color de primer plano
     * @param backgroundColor Enum con el color de fondo
     * @param msg String con la cadena a colorear
     * @return un String con las secuencias de escape ANSI que colorean la
     * cadena segun lo solicitado
     * @throws IllegalArgumentException En caso de que algún argumento no sea
     * válido.
     */
    public String setColors(Color color, BGColor backgroundColor, String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        int iColor = Integer.parseInt(color.getAsString());
        int bColor = Integer.parseInt(backgroundColor.getAsString());
        if ((iColor < 30 || iColor > 37)) {
            throw new IllegalArgumentException(EX_NO_COL);
        }
        if (bColor < 40) {
            throw new IllegalArgumentException(EX_NO_BACKCOL);
        }
        if ((msg == null || msg.isEmpty())) {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

        sb.append(ESC).append("[")
                .append(color)
                .append(";")
                .append(backgroundColor)
                .append("m")
                .append(msg)
                .append(ESC)
                .append("[")
                .append(REINICIA)
                .append("m");
        return sb.toString();
    }

    /**
     * Resetea el terminal a sus valores por defecto. Se recomienda llamar a
     * este método cuando se termine de usar Terminal en su aplicación.
     */
    public void resetScreen() {
        System.out.print(RES_PAN);
    }


    /**
     * Devuelve el tamaño de la terminal
     * @return un objeto TerminalSize con las líneas y columnas actuales de la 
     * terminal.
     * 
     * @see TerminalSize
     */
    public TerminalSize getTerminalSize(){
        return osCall.getTerminalSize();
    }

    /**
     * Imprime la cadena msg en la posición del terminal indicada por los
     * enteros line (la línea) y col (la columna)
     *
     * @param msg la cadena a imprimir
     * @param line la línea donde se imprimirá
     * @param col la columna a partir de la cual se imprimirá la cadena
     * @throws IllegalArgumentException En caso de argumentros no válidos.
     */
    public void printAt(String msg, int line, int col) throws IllegalArgumentException {
        if ((msg != null && !msg.isEmpty())) {
            moveCursorToXY(line, col);
            System.out.print(msg);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }

    }

    /**
     * Imprime la cadena msg en la posición p en el terminal
     *
     * @param msg la cadena a imprimir
     * @param p la posición en el terminal donde comenzará a imprimirse la
     * cadena
     */
    public void printAt(String msg, Posicion p) {
        printAt(msg, p.getCol(), p.getLin());
    }
}
