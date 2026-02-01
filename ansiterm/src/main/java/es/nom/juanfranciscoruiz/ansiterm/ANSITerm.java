package es.nom.juanfranciscoruiz.ansiterm;

import com.sun.jna.LastErrorException;
import es.nom.juanfranciscoruiz.ansiterm.codes.*;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static es.nom.juanfranciscoruiz.ansiterm.LinuxTerminal.getPosition;
import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;
import static es.nom.juanfranciscoruiz.ansiterm.exceptions.Errors.*;

/**
 *
 * Controls terminal output using ANSI escape codes. The terminal must support
 * this standard (Windows Terminal, xterm, and other terminal emulators support it).
 * The Windows console terminal (cmd.exe) does not support ANSI.
 * <p>
 * More information about using ANSI escape sequences at:<br><br>
 * <a href="https://invisible-island.net/xterm/ctlseqs/ctlseqs.html">XTerm Control Sequences by Edward Moy</a><br>
 * <a href="https://learn.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences">Microsoft Learn - Console Virtual Terminal Sequences</a><br>
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">Wikipedia - ANSI escape code</a>
 *
 * @author Juan F. Ruiz
 */
public class ANSITerm {
    // Constants and attributes
    /**
     * For logging
     */
    public static final Logger logger = LoggerFactory.getLogger(ANSITerm.class);
    
    /**
     * ITerminal object to make calls to the OS functions where the program is
     * running and enable/disable console capabilities not available from Java
     * or ANSI control sequences.
     */
    private ITerminal osCall;
    /**
     * Terminal size
     */
    private TerminalSize terminalSize;
    /**
     * Cursor position
     */
    private Position cursorPosition;

    // Helper classes that represent ANSI control sequences and return them.
    /**
     * ANSI escape sequence generator
     */
    private static CSI CSI;
    /**
     * Cursor control codes
     */
    private static CursorControlCodes ccc;
    /**
     * Cursor movement controls
     */
    private static CursorMovementCodes cmc;
    /**
     * Singleton instance for colors and styles codes.
     * This class is stateless (constants), so sharing one instance is safe.
     */
    private static ColorsAndStylesCodes cosc = ColorsAndStylesCodes.getInstance();
    /**
     * Erase sequences
     */
    private EraseSecuencesCodes esec;
    /**
     * General ASCII controls
     */
    private static GeneralControlCodes gac;
    /**
     * Input mode changes codes
     */
    private static InputModeChangesCodes imcc;
    /**
     * Position codes
     */
    private static PositionCodes vpc;
    /**
     * Tab codes
     */
    private static TabCodes tc;
    /**
     * Viewport positioning codes
     */
    private static ViewportPositioningCodes vposc;
    /**
     * Character set mode codes
     */
    private static CharacterSetModeCodes csmc;

    // Constructors
    /**
     * Constructor for the ANSITerm class.
     * <p>
     * This constructor initializes the terminal based on the operating system. It determines
     * whether the operating system is Windows or Linux and creates an appropriate terminal instance.
     * If the operating system is unsupported, an exception will be thrown.
     *
     * @throws ANSITermException if the operating system is unsupported.
     */
    public ANSITerm() throws ANSITermException {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            osCall = new WindowsTerminal();
        } else if (os.contains("linux")) {
            osCall = new LinuxTerminal();
        }  else {
            throw new ANSITermException("Unsupported operating system");
        }
        this.terminalSize = osCall.getTerminalSize();
        this.cursorPosition = new Position(0, 0);

        esec = EraseSecuencesCodes.getInstance();
        CSI = es.nom.juanfranciscoruiz.ansiterm.codes.CSI.getInstance();
        ccc = CursorControlCodes.getInstance();
        cmc = CursorMovementCodes.getInstance();
        gac = GeneralControlCodes.getInstance();
        imcc = InputModeChangesCodes.getInstance();
        vpc = PositionCodes.getInstance();
        tc = TabCodes.getInstance();
        vposc = ViewportPositioningCodes.getInstance();
        csmc = CharacterSetModeCodes.getInstance();
        if (logger.isDebugEnabled()) logger.debug(this.toString());
    }

    // Getters and setters
    /**
     * Sets the size of the terminal.
     *
     * @param terminalSize the new size to set for the terminal
     */
    public void setTerminalSize(TerminalSize terminalSize) {
        this.terminalSize = terminalSize;
    }

    /**
     * Sets the cursor position.
     *
     * @param cursorPosition the new position to set for the cursor
     */
    public void setCursorPosition(Position cursorPosition) {
        this.cursorPosition = cursorPosition;
    }

    /**
     * Returns an ITerminal object with which we can access the low-level
     * methods of the terminal running on the current operating system.
     * @return an ITerminal object with the appropriate low-level methods of
     * the operating system where our program is running.
     */
    public ITerminal getOsCall(){
        return osCall;
    }

    /**
     * Retrieves the current value of the ColorsAndStylesCodes (COSC).
     *
     * @return the current ColorsAndStylesCodes instance.
     */
    public static ColorsAndStylesCodes getCosc() {
        return cosc;
    }

    /**
     * Sets the ColorsAndStylesCodes (COSC) instance.
     *
     * @param cosc the new ColorsAndStylesCodes instance to set
     */
    public static void setCosc(ColorsAndStylesCodes cosc) {
        ANSITerm.cosc = cosc;
    }

    /**
     * Retrieves the current value of the CSI (Control Sequence Introducer).
     *
     * @return the current CSI instance.
     */
    public static CSI getCSI() {
        return CSI;
    }

    /**
     * Sets the CSI (Control Sequence Introducer) instance.
     *
     * @param CSI the new CSI instance to set
     */
    public static void setCSI(CSI CSI) {
        ANSITerm.CSI = CSI;
    }

    /**
     * Retrieves the current value of the CursorControlCodes (CCC).
     *
     * @return the current CursorControlCodes instance.
     */
    public static CursorControlCodes getCcc() {
        return ccc;
    }

    /**
     * Sets the CursorControlCodes (CCC) instance.
     *
     * @param ccc the new CursorControlCodes instance to set
     */
    public static void setCcc(CursorControlCodes ccc) {
        ANSITerm.ccc = ccc;
    }

    /**
     * Retrieves the current value of the CursorMovementCodes (CMC).
     *
     * @return the current CursorMovementCodes instance.
     */
    public static CursorMovementCodes getCmc() {
        return cmc;
    }

    /**
     * Sets the CursorMovementCodes (CMC) instance.
     *
     * @param cmc the new CursorMovementCodes instance to set
     */
    public static void setCmc(CursorMovementCodes cmc) {
        ANSITerm.cmc = cmc;
    }

    /**
     * Retrieves the current value of the CursorStylesCodes (CSC).
     *
     * @return the current CursorStylesCodes instance.
     */
    public static ColorsAndStylesCodes getCsc() {
        return cosc;
    }

    /**
     * Sets the CursorStylesCodes (CSC) instance.
     *
     * @param cosc the new CursorStylesCodes instance to set
     */
    public static void setCsc(ColorsAndStylesCodes cosc) {
        ANSITerm.cosc = cosc;
    }

    /**
     * Retrieves the current value of the EraseSecuencesCodes (ES).
     *
     * @return the current EraseSecuencesCodes instance.
     */
    public EraseSecuencesCodes getEsec() {
        return EraseSecuencesCodes.getInstance();
    }

    /**
     * Sets the EraseSequencesCodes object.
     *
     * @param esec the EraseSequencesCodes object to be set
     */
    public void setEsec(EraseSecuencesCodes esec) {
        this.esec = esec;
    }

    /**
     * Retrieves the current value of the GeneralControlCodes (GAC).
     *
     * @return the current GeneralControlCodes instance.
     */
    public static GeneralControlCodes getGac() {
        return gac;
    }

    public static void setGac(GeneralControlCodes gac) {
        ANSITerm.gac = gac;
    }

    /**
     * Retrieves the current instance of InputModeChangesCodes (IMCC).
     *
     * @return the current InputModeChangesCodes instance
     */
    public static InputModeChangesCodes getImcc() {
        return imcc;
    }

    /**
     * Sets the InputModeChangesCodes (IMCC) instance.
     *
     * @param imcc the new InputModeChangesCodes instance to set
     */
    public static void setImcc(InputModeChangesCodes imcc) {
        ANSITerm.imcc = imcc;
    }

    /**
     * Retrieves the current instance of ViewportPositioningCodes (VPC).
     *
     * @return the current ViewportPositioningCodes instance
     */
    public static PositionCodes getVpc() {
        return vpc;
    }

    /**
     * Sets the ViewportPositioningCodes (VPC) instance.
     *
     * @param vpc the new ViewportPositioningCodes instance to set
     */
    public static void setVpc(PositionCodes vpc) {
        ANSITerm.vpc = vpc;
    }

    /**
     * Retrieves the current instance of TabCodes (TC).
     *
     * @return the current TabCodes instance
     */
    public static TabCodes getTc() {
        return tc;
    }

    /**
     * Sets the TabCodes (TC) instance.
     *
     * @param tc the new TabCodes instance to set
     */
    public static void setTc(TabCodes tc) {
        ANSITerm.tc = tc;
    }

    /**
     * Retrieves the current instance of ViewportPositioningCodes (VPOS).
     *
     * @return the current ViewportPositioningCodes instance
     */
    public static ViewportPositioningCodes getVposc() {
        return vposc;
    }

    /**
     * Sets the ViewportPositioningCodes (VPOS) instance.
     *
     * @param vposc the new ViewportPositioningCodes instance to set
     */
    public static void setVposc(ViewportPositioningCodes vposc) {
        ANSITerm.vposc = vposc;
    }

    /**
     * Retrieves the current value of the CharacterSetModeCodes (CSMC).
     *
     * @return the current CharacterSetModeCodes instance.
     */
    public static CharacterSetModeCodes getCsmc() {
        return csmc;
    }

    /**
     * Sets the CharacterSetModeCodes (CSMC) instance.
     *
     * @param csmc the new CharacterSetModeCodes instance to set
     */
    public static void setCsmc(CharacterSetModeCodes csmc) {
        ANSITerm.csmc = csmc;
    }

    // Methods
    /**
     * Enables 'raw' mode in the current console so that ANSI sequences can
     * be interpreted.
     *
     * @throws LastErrorException In case an error occurs in the call, or it
     * is not running on Microsoft Windows.
     */
    public void enableRawMode() throws LastErrorException {
        osCall.enableRawMode();
    }

    /**
     * Disables 'raw' mode (and enables 'cooked' mode) in the current console.
     *
     * @throws LastErrorException In case an error occurs in the call, or it
     * is not running on Microsoft Windows.
     */
    public void disableRawMode() throws LastErrorException {
        osCall.disableRawMode();
    }

    /**
     * Rings the terminal bell
     */
    public void bell() {
        System.out.print(getGac().bell());
    }

    /*
    TODO: The methods that moves the cursor has to update the 'cursorPosition' property of this
     class.
     */
    /**
     * Causes a cursor backspace
     */
    public void backSpace() {
        System.out.print(getGac().backSpace());
    }

    /**
     * Generates a tab
     */
    public void tab() {
        System.out.print(getGac().tab());
    }

    /**
     * Generates a line feed
     */
    public void linefeed() {
        System.out.print(getGac().linefeed());
    }

    /**
     * Generates a vertical tab
     */
    public void verticalTab() {
        System.out.print(getGac().verticalTab());
    }

    /**
     * Generates a form feed
     */
    public void formfeed() {
        System.out.print(getGac().formfeed());
    }

    /**
     * Generates a carriage return
     * In the Windows terminal it moves the cursor to the beginning of the line.
     * To do a line break you have to do a linefeed() or use  the Java \n escape code
     */
    public void carriagereturn() {System.out.print(getGac().carriagereturn()); }

    // Cursor functions
    /**
     * Moves the cursor to the beginning of the terminal (0,0)
     */
    public void moveCursorToBegin() {
        System.out.print(CursorMovementCodes.getSecforSetCursorToBegin());
    }

    /**
     * Moves the cursor to the line, column position of the terminal
     * <p>
     * Sequence......: <ESCESC[<y>;<x>H
     * Code..........: CUP
     * Description...: Position of cursor
     * The cursor moves to the coordinates <x>; <y> within the
     * window, where <x> is the column of the row <y>
     *
     * @param line integer with the line to move the cursor to
     * @param column integer with the column to move the cursor to
     */
    public void printAt(int line, int column) {
        String sec_ansi = ESC + "[" + line + ";" + column + "H";
        System.out.print(CursorMovementCodes.getSecforSetCursorAtPosition(line, column));
    }

    /**
     * Moves the cursor to the terminal position indicated by p
     *
     * @param p Posicion object containing the position where the cursor
     * will be moved
     */
    public void printAt(Position p) {
        String sec_ansi = ESC + "[" + p.getCol() + ";" + p.getLin() + "H";
        System.out.print(sec_ansi);
    }

    /**
     * Returns the cursor position on the screen
     *
     * @return a Posicion object with the current cursor position on the screen
     * @throws LastErrorException if an error occurs while getting the position
     */
    public Position getCursorPosition() throws LastErrorException {
        try {
            // We make the console not show the characters that are written
            // and that the user's keystrokes are obtained without waiting
            // for them to press ENTER (raw mode)
            this.osCall.enableRawMode();
            System.out.print(CursorMovementCodes.getSecForGetCursorPosition());
            return getPosition();
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return new Position(1, 1);
        } finally {
            // The console must be left in normal mode.
            this.osCall.disableRawMode();
        }
    }

    /**
     * Moves the cursor one line up
     */
    public void moveCursorUp() {
        System.out.println(CursorMovementCodes.getSecForMoveCursorNLinesUp(1));
    }

    /**
     * Moves the cursor up as many lines as indicated in the 'lines' parameter<br>
     * CODE: CUU (Cursor Up)
     *
     * @param lines an integer with the lines up where the cursor will be moved
     */
    public void moveCursorUp(int lines) {
        System.out.print(CursorMovementCodes.getSecForMoveCursorNLinesUp(lines));
    }

    /**
     * Moves the cursor down as many lines as indicated in the lines parameter
     * CODE: CUD (Cursor Down)
     * @see #moveCursorUp(int)
     * @param lines an integer with the lines down where the cursor will be moved
     */
    public void moveCursorDown(int lines) {
        System.out.print(CursorMovementCodes.getSecForMoveCursorNLinesDown(lines));
    }

    /**
     * Moves the cursor to the right as many characters as indicated in the
     * cars parameter
     * CODE: CUF (Cursor Forward)
     * @see #moveCursorUp(int)
     * @param cars an integer with the characters to the right where the
     * cursor will be moved
     */
    public void moveCursorRight(int cars) {
        System.out.print(CursorMovementCodes.getSecForMoveCursorNCharsToRight(cars));
    }

    /**
     * Moves the cursor to the left as many characters as indicated in the
     * cars parameter
     * CODE: CUB (Cursor Backward)
     * @see #moveCursorUp(int)
     * @param cars an integer with the characters to the left where the
     * cursor will be moved
     */
    public void moveCursorLeft(int cars) {
        System.out.print(CursorMovementCodes.getSecForMoveCursorNCharsToLeft(cars));
    }

    /**
     * Hides the cursor
     * CODE: DECTCEM (Text Cursor Enable Mode Hide)
     */
    public void cursorHide() {
        String sec_ansi = ESC + CursorControlCodes.HIDES_CURSOR;
        System.out.print(sec_ansi);
    }

    /**
     * Shows the cursor
     * CODE: DECTCEM (Text Cursor Enable Mode Show)
     */
    public void cursorShow() {
        String sec_ansi = ESC + CursorControlCodes.SHOWS_CURSOR;
        System.out.print(sec_ansi);
    }
    
    
    /**
     * Enables cursor blinking
     * CODE: ATT160 (Text Cursor Enable Blinking)
     */
    public void cursorBlink() {
        String sec_ansi = ESC + CursorControlCodes.ENABLE_BLINK_CURSOR;
        System.out.print(sec_ansi);
    }
    
    /**
     * Disables cursor blinking
     * CODE: ATT160 (Text Cursor Disable Blinking)
     */
    public void cursorNoBlink() {
        String sec_ansi = ESC + CursorControlCodes.DISABLE_BLINK_CURSOR;
        System.out.print(sec_ansi);
    }

    /**
     * Sets the cursor style.
     * Implements the following ANSI sequences:
     * <ul>
     * <li>ESC [ 0 SP q, DECSCUSR, User Shape</li>
     * <li>ESC [ 1 SP q, DECSCUSR, Blinking Block</li>
     * <li>ESC [ 2 SP q, DECSCUSR, Steady Block</li>
     * <li>ESC [ 3 SP q, DECSCUSR, Blinking Underline</li>
     * <li>ESC [ 4 SP q, DECSCUSR, Steady Underline</li>
     * <li>ESC [ 5 SP q, DECSCUSR, Blinking Bar</li>
     * <li>ESC [ 6 SP q, DECSCUSR, Steady Bar</li>
     * </ul>
     * @param style One of the available cursor styles (Class with static
     * constants)
     * @throws ANSITermException In case any argument is not valid.
     */
    public void cursorChangeStyle(String style) throws ANSITermException {
        this.cursorShow();
        if (style == null || style.isEmpty()) {
            throw new ANSITermException("Invalid style");
        }

        if (!style.equals(CursorStylesCodes.CURSOR_STEADY_BAR_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_BLINKING_BAR_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_STEADY_BLOCK_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_BLOCK_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_STEADY_UNDERLINE_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_UNDERLINE_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_USER_SHAPE)) {
            throw new ANSITermException("Unrecognized style");
        }
        System.out.print(style);
    }

    /**
     * Inserts 'cars' spaces at the current cursor position, shifting all
     * existing text to the right. Also, text that goes off the screen to
     * the right is removed.
     *
     * @param cars the number of spaces to insert.
     */
    public void insertSpaces(int cars) {
        String sec_ansi = ESC + "[" + cars + "@";
        System.out.print(sec_ansi);
    }

    /**
     * Deletes 'cars' characters at the current cursor position, shifting
     * space characters from the right edge of the screen.
     *
     * @param cars the number of characters to delete.
     */
    public void delChars(int cars) {
        String sec_ansi = ESC + "[" + cars + "P";
        System.out.print(sec_ansi);
    }

    /**
     * Deletes 'cars' characters from the current cursor position by
     * overwriting them with a space character.
     *
     * @param cars the number of characters to delete.
     */
    public void delCharsWithSpaces(int cars) {
        String sec_ansi = ESC + "[" + cars + "X";
        System.out.print(sec_ansi);
    }

    /**
     * Inserts the lines indicated by 'lines' into the screen buffer at the
     * cursor position. The line where the cursor is and the lines below it
     * will shift down.
     *
     * @param lines the number of lines to insert.
     */
    public void insertLines(int lines) {
        String sec_ansi = ESC + "[" + lines + "L";
        System.out.print(sec_ansi);
    }

    /**
     * Deletes the lines indicated by 'lines' from the buffer, starting from
     * the row where the cursor is located.
     *
     * @param lines the number of lines to delete.
     */
    public void deleteLines(int lines) {
        String sec_ansi = ESC + "[" + lines + "M";
        System.out.print(sec_ansi);
    }

    /**
     * Saves the cursor position
     */
    public void saveCursorPos() {
        System.out.print(CursorMovementCodes.CURSOR_SAVE_CURRENT_POSITION);

    }

    /**
     * Restores the cursor position
     */
    public void restoreCursorPos() {
        System.out.print(CursorMovementCodes.CURSOR_RESTORE_CURRENT_POSITION);
    }

    // Erase functions
    /**
     * Deletes everything from the cursor position to the end of the screen
     */
    public void deleteFromCursorToEndScreen() {
        System.out.print(esec.deleteFromCursorToEndScreen());
    }

    /**
     * Deletes everything from the cursor position to the beginning of the screen
     */
    public void deleteFromCursorToBeginScreen() {
        System.out.print(esec.deleteFromCursorToBeginScreen());
    }

    /**
     * Erases the screen
     */
    public void clearScreen() {
        System.out.print(getEsec().clearScreen());
    }

    /**
     * Deletes everything from the cursor position to the end of the line
     * where it is located.
     */
    public void deleteFromCursorToEndLine() {
        System.out.print(esec.deleteFromCursorToEndLine());
    }

    /**
     * Deletes everything from the cursor position to the beginning of the line
     * where it is located.
     */
    public void deleteFromCursorToBeginLine() {
        System.out.print(esec.deleteFromCursorToBeginLine());
    }

    /**
     * Erases possible characters from the current line where the cursor is
     * located.
     */
    public void deleteLine() {
        System.out.print(esec.deleteLine());
    }

    // Colors and styles
    /**
     * Sets bold mode for the passed string
     *
     * @param msg the string to put in bold
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be in bold.
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setBold(String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(ColorsAndStylesCodes.BOLD_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.BOLD_END);
        } else {
            throw new ANSITermException(EX_NO_MSG);
        }
        return sb.toString();
    }

    /**
     * Sets dimmed mode for the passed string
     *
     * @param msg the string that will be put in dimmed mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be dimmed.
     */
    public String setDim(String msg) {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(ColorsAndStylesCodes.DIM_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.DIM_END);
        }

        return sb.toString();
    }

    /**
     * Sets italic mode for the passed string
     *
     * @param msg the string that will be put in italic mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be in italics.
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setItalic(String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(ColorsAndStylesCodes.ITALIC_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.ITALIC_END);
        } else {
            throw new ANSITermException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Sets underline mode for the passed string
     *
     * @param msg the string that will be put in underline mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be underlined.
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setUnderline(String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(ColorsAndStylesCodes.UNDERLINE_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.UNDERLINE_STOP);
        } else {
            throw new ANSITermException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Sets blink mode for the passed string
     *
     * @param msg the string that will be put in blink mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be blinking.
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setBlink(String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(ColorsAndStylesCodes.BLINK_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.BLINK_END);
        } else {
            throw new ANSITermException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Sets inverse mode for the passed string
     *
     * @param msg the string that will be put in inverse mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be inverted (foreground/background color swap).
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setInverse(String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(ColorsAndStylesCodes.REVERSE_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.REVERSE_END);
        } else {
            throw new ANSITermException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Sets hidden mode for the passed string
     *
     * @param msg the string that will be put in hidden mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be hidden (nothing appears on the screen, but the space
     * occupied by the string is taken).
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setHidden(String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(ColorsAndStylesCodes.INVISIBLE_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.INVISIBLE_END);
        } else {
            throw new ANSITermException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Sets strikethrough mode for the passed string
     *
     * @param msg the string that will be put in strikethrough mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will appear struck through.
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setStrikeThrough(String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(ColorsAndStylesCodes.STRIKETHROUGH_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.STRIKETHROUGH_END);
        } else {
            throw new ANSITermException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Combines several styles to the passed string. <br>
     * <b>NOTE: Not all style combinations</b> will produce the intended
     * results in <i>some terminals</i>.
     *
     * @param isBold If true, puts the string in bold
     * @param isDim If true, puts the string in dimmed mode
     * @param isItalic If true, puts the string in italics
     * @param isUnderline If true, puts the string underlined
     * @param isBlink If true, puts the string blinking
     * @param isInverse If true, puts the string in inverse mode
     * @param isStrikeThrough If true, puts the string with a strikethrough.
     * @param msg the string to which styles will be applied
     * @return a String with the ANSI sequences necessary so that, if later
     * printed in the terminal, it appears with the requested styles.
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setStyles(boolean isBold, boolean isDim,
            boolean isItalic, boolean isUnderline,
            boolean isBlink, boolean isInverse,
            boolean isStrikeThrough, String msg) throws ANSITermException {

        StringBuilder sb = new StringBuilder();
        boolean isMessageAdded = false;
        if (msg != null && !msg.isEmpty()) {
            if (isBold) {
                sb.append(setBold(msg));
                isMessageAdded = true;
            }
            if (isDim) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                        ColorsAndStylesCodes.DIM_START).append(ESC +
                        ColorsAndStylesCodes.DIM_END);
                } else {
                    sb.append(setDim(msg));
                    isMessageAdded = true;
                }
            }
            if (isItalic) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                        ColorsAndStylesCodes.ITALIC_START).append(ESC +
                        ColorsAndStylesCodes.ITALIC_END);
                } else {
                    sb.append(setItalic(msg));
                    isMessageAdded = true;
                }
            }
            if (isUnderline) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                        ColorsAndStylesCodes.UNDERLINE_START).append(ESC +
                        ColorsAndStylesCodes.UNDERLINE_STOP);
                } else {
                    sb.append(setUnderline(msg));
                    isMessageAdded = true;
                }
            }
            if (isBlink) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                        ColorsAndStylesCodes.BLINK_START).append(ESC +
                        ColorsAndStylesCodes.BLINK_END);
                } else {
                    sb.append(setBlink(msg));
                    isMessageAdded = true;
                }
            }
            if (isInverse) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                        ColorsAndStylesCodes.REVERSE_START).append(ESC +
                        ColorsAndStylesCodes.REVERSE_END);
                } else {
                    sb.append(setInverse(msg));
                    isMessageAdded = true;
                }
            }
            if (isStrikeThrough) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                        ColorsAndStylesCodes.STRIKETHROUGH_START).append(ESC +
                        ColorsAndStylesCodes.STRIKETHROUGH_END);
                } else {
                    sb.append(setStrikeThrough(msg));
                    isMessageAdded = true;
                }
            }
        } else {
            throw new ANSITermException(EX_NO_MSG);
        }

        return sb.toString();
    }

    /**
     * Sets text color
     * @param color A Color enum constant with the color
     * @param msg The string to be colored
     * @return a string with the appropriate ANSI sequence to show the text
     * with the indicated color in the console.
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setColor(Color color, String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();
        int iColor = Integer.parseInt(color.getAsString());
        if ((iColor >= 30 || iColor <= 37)) {
            if ((msg != null && !msg.isEmpty())) {
                sb.append(ESC).append("[")
                        .append(iColor)
                        .append("m")
                        .append(msg)
                        .append(ESC)
                        .append("[")
                        .append(Color.DEFAULT.getAsString())
                        .append("m");
            } else {
                throw new ANSITermException(EX_NO_MSG);
            }
        } else {
            throw new ANSITermException(EX_NO_COL);
        }

        return sb.toString();
    }

    /**
     * Sets a color code from a 256-color palette to the string passed as
     * a parameter.
     * 
     * @param color an integer between 0 and 255 containing the color code.
     * @param msg a string that will receive the ANSI sequence to give it the
     * indicated color.
     * @return a string with the appropriate ANSI sequence to be displayed in
     * the indicated color in the terminal.
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setColor256(int color, String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();

        if (color < 0 || color > 255) {
            throw new ANSITermException(EX_NO_COL);
        }

        if (msg == null || msg.isEmpty()) {
            throw new ANSITermException(EX_NO_MSG);
        }

        sb.append(ESC).append(ColorsAndStylesCodes.FOREGROUND_COLOR256)
                .append(color)
                .append("m")
                .append(msg)
                .append(ESC)
                .append("[")
                .append(Color.DEFAULT.getAsString())
                .append("m");

        return sb.toString();
    }

    /**
     * Sets a color code from a 256-color palette for the background of the
     * string passed as a parameter.
     * @param color an integer between 0 and 255 containing the color code.
     * @param msg a string that will receive the ANSI sequence to give its
     * background the indicated color.
     * @return a string with the appropriate ANSI sequence that will show the
     * indicated color in its background in the terminal.
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setBackgroundColor256(int color, String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();

        if (color <= 0 || color > 255) {
            throw new ANSITermException(EX_NO_COL);
        }

        if (msg == null || msg.isEmpty()) {
            throw new ANSITermException(EX_NO_MSG);
        }

        sb.append(ESC).append(ColorsAndStylesCodes.BACKGROUND_COLOR256)
                .append(color)
                .append("m")
                .append(msg)
                .append(ESC)
                .append("[")
                .append(Color.DEFAULT.getAsString())
                .append("m");

        return sb.toString();
    }

    /**
     * Sets the background color of the passed string
     *
     * @param color Enum with the background color
     * @param msg String with the string to color
     * @return a String with the ANSI escape sequences that color the string
     * as requested
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setBackgroundColor(BGColor color, String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();
        int iColor = Integer.parseInt(color.getAsString());
        if ((iColor >= 40 || iColor <= 47)) {
            if ((msg != null && !msg.isEmpty())) {
                sb.append(ESC).append("[")
                        .append(iColor)
                        .append("m")
                        .append(msg)
                        .append(ESC)
                        .append("[")
                        .append(BGColor.DEFAULT.getAsString())
                        .append("m");
            } else {
                throw new ANSITermException(EX_NO_MSG);
            }
        } else {
            throw new ANSITermException(EX_NO_BACKCOL);
        }

        return sb.toString();
    }

    /**
     * Sets the foreground and background colors of the passed string
     *
     * @param color Enum with the foreground color
     * @param backgroundColor Enum with the background color
     * @param msg String with the string to color
     * @return a String with the ANSI escape sequences that color the string
     * as requested
     * @throws ANSITermException In case any argument is not valid.
     */
    public String setColors(Color color, BGColor backgroundColor, String msg) throws ANSITermException {
        StringBuilder sb = new StringBuilder();
        int iColor = Integer.parseInt(color.getAsString());
        int bColor = Integer.parseInt(backgroundColor.getAsString());
        if ((iColor < 30 || iColor > 37)) {
            throw new ANSITermException(EX_NO_COL);
        }
        if ((bColor < 40 || iColor > 47)) {
            throw new ANSITermException(EX_NO_BACKCOL);
        }
        if ((msg == null || msg.isEmpty())) {
            throw new ANSITermException(EX_NO_MSG);
        }

        sb.append(ESC).append("[")
                .append(color)
                .append(";")
                .append(backgroundColor)
                .append("m")
                .append(msg)
                .append(ESC)
                .append("[")
                .append(ColorsAndStylesCodes.RESET_COLOR_AND_STYLES)
                .append("m");
        return sb.toString();
    }

    /**
     * Resets the terminal to its default values. It is recommended to call
     * this method when you finish using Terminal in your application.
     */
    public void resetScreen() {
        System.out.print(PositionCodes.RESTORES_SCREEN);
    }

    /**
     * Returns terminal size
     * @return a TerminalSize object with the current lines and columns of
     * the terminal.
     * 
     * @see TerminalSize
     */
    public TerminalSize getTerminalSize(){
        return osCall.getTerminalSize();
    }

    /**
     * Prints the msg string at the terminal position indicated by the
     * integers line and col (the column)
     *
     * @param msg the string to print
     * @param line the line where it will be printed
     * @param col the column from which the string will be printed
     * @throws ANSITermException In case of invalid arguments.
     */
    public void printAt(String msg, int line, int col) throws ANSITermException {
        if ((msg != null && !msg.isEmpty())) {
            printAt(line, col);
            System.out.print(msg);
        } else {
            throw new ANSITermException(EX_NO_MSG);
        }
    }

    /**
     * Prints the msg string at position p in the terminal
     *
     * @param msg the string to print
     * @param p the terminal position where the string will start to be printed
     */
    public void printAt(String msg, Position p) throws ANSITermException {
        try {
            printAt(msg, p.getCol(), p.getLin());
        } catch (ANSITermException e) {
            throw new ANSITermException(e);
        }
    }

    /**
     * Moves the text up in the terminal
     * @param lines the number of lines to move up
     */
    public void  moveTextUp(int lines) {
        vposc.moveTextUp(lines);
    }

    /**
     * Moves the text down in the terminal
     * @param lines the number of lines to move down
     */
    public void  moveTextDown(int lines) {
        vposc.moveTextDown(lines);
    }

    @Override
    public String toString() {
        return "ANSITerm{" +
                "osCall=" + osCall +
                ", terminalSize=" + terminalSize +
                ", cursorPosition=" + cursorPosition +
                '}';
    }
}
