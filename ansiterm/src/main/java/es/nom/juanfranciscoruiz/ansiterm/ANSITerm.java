package es.nom.juanfranciscoruiz.ansiterm;

import com.sun.jna.LastErrorException;
import es.nom.juanfranciscoruiz.ansiterm.codes.*;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static es.nom.juanfranciscoruiz.ansiterm.LinuxTerminal.getPosition;
import static es.nom.juanfranciscoruiz.ansiterm.codes.AnsiBufferManager.*;
import static es.nom.juanfranciscoruiz.ansiterm.codes.BGColor.DEFAULT;
import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;
import static es.nom.juanfranciscoruiz.ansiterm.codes.CursorControlCodes.*;
import static es.nom.juanfranciscoruiz.ansiterm.codes.CursorMovementCodes.*;
import static es.nom.juanfranciscoruiz.ansiterm.codes.EraseSecuencesCodes.*;
import static es.nom.juanfranciscoruiz.ansiterm.codes.GeneralControlCodes.*;
import static es.nom.juanfranciscoruiz.ansiterm.codes.ScrollingMarginsCodes.resetScrollingMargins;
import static es.nom.juanfranciscoruiz.ansiterm.codes.ScrollingMarginsCodes.setScrollingMargins;
import static es.nom.juanfranciscoruiz.ansiterm.codes.StylesCodes.*;
import static es.nom.juanfranciscoruiz.ansiterm.codes.CursorStylesCodes.*;
import static es.nom.juanfranciscoruiz.ansiterm.codes.TextModificationCodes.*;
import static es.nom.juanfranciscoruiz.ansiterm.codes.ViewportPositioningCodes.*;
import static es.nom.juanfranciscoruiz.ansiterm.codes.WindowTitleCodes.setWindowTitle;
import static es.nom.juanfranciscoruiz.ansiterm.codes.WindowTitleCodes.updateWindowTitle;
import static es.nom.juanfranciscoruiz.ansiterm.codes.WindowWidthCodes.setWidthTo132;
import static es.nom.juanfranciscoruiz.ansiterm.codes.WindowWidthCodes.setWidthTo80;
import static es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSIErrors.*;

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
    private final ITerminal osCall;
    /**
     * Terminal size
     */
    private TerminalSize terminalSize;
    /**
     * Cursor position
     */
    private Position cursorPosition;


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
    }

    // Getters and setters
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
     * Sets the size of the terminal.
     *
     * @param terminalSize the new size to set for the terminal
     */
    public void setTerminalSize(TerminalSize terminalSize) {this.terminalSize = terminalSize;}

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



    // Methods
    /* ------------------------------------------ Console capabilities methods ---------------------------------------*/
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

    /* ------------------------------------------ ANSI control sequences methods -------------------------------------*/
    /**
     * Rings the terminal bell
     */
    public void BELL() {
        System.out.print(bell());
    }

    /*
    TODO: The methods that moves the cursor has to update the 'cursorPosition' property of this
     class.
     */
    /**
     * Causes a cursor backspace
     */
    public void BS() {
        System.out.print(backSpace());
    }

    /**
     * Generates a tab
     */
    public void TAB() {
        System.out.print(tab());
    }

    /**
     * Generates a line feed
     */
    public void LF() {
        System.out.print(lineFeed());
    }

    /**
     * Generates a vertical tab
     */
    public void VT() {System.out.print(verticalTab()); }

    /**
     * Generates a form feed
     */
    public void FF() {
        System.out.print(formFeed());
    }

    /**
     * Generates a carriage return
     * In the Windows terminal it moves the cursor to the beginning of the line.
     * To do a line break you have to do a linefeed() or use  the Java \n escape code
     */
    public void CR() {System.out.print(carriageReturn()); }

    /* ----------------------------- Cursor movements, getting position and printing methods -------------------------*/
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
            System.out.print(CursorMovementCodes.getCursorPosition());
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
     * Moves the cursor to the beginning of the terminal (0,0)
     */
    public void moveCursorToBegin() {
        System.out.print(setCursorToBegin());
    }

    /**
     * Moves the cursor one line up
     */
    public void moveCursorUp() {
        System.out.println(moveCursorNLinesUp(1));
    }

    /**
     * Moves the cursor up as many lines as indicated in the 'lines' parameter<br>
     * CODE: CUU (Cursor Up)
     *
     * @param lines an integer with the lines up where the cursor will be moved
     */
    public void moveCursorUp(int lines) {
        System.out.print(moveCursorNLinesUp(lines));
    }

    /**
     * Moves the cursor down as many lines as indicated in the lines parameter
     * CODE: CUD (Cursor Down)
     * @see #moveCursorUp(int)
     * @param lines an integer with the lines down where the cursor will be moved
     */
    public void moveCursorDown(int lines) {
        System.out.print(moveCursorNLinesDown(lines));
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
        System.out.print(moveCursorNCharsToRight(cars));
    }

    /**
     * Moves the cursor to the left as many characters as indicated in the
     * cars parameter
     * CODE: CUB (Cursor Backward)
     * @see #moveCursorUp(int)
     * @param cars an integer with the characters to the left where the
     * cursor will be moved
     */
    public void moveCursorLeft(int cars) {System.out.print(moveCursorNCharsToLeft(cars));}

    /**
     * Moves the cursor to the line, column position of the terminal
     * <p>
     * Sequence......: &lt;[&lt;y&gt;&lt;x&gt;H
     * Code..........: CUP
     * Description...: Position of cursor
     * The cursor moves to the coordinates &lt;x&gt; &lt;y&gt; within the
     * window, where &lt;x&gt; is the column of the row &lt;y&gt;
     *
     * @param line integer with the line to move the cursor to
     * @param column integer with the column to move the cursor to
     */
    public void printAt(int line, int column) {
        //TODO: Validar que la posicion sea valida
        System.out.print(CursorMovementCodes.setCursorPosition(line, column));
    }

    /**
     * Moves the cursor to the terminal position indicated by p
     *
     * @param p Posicion object containing the position where the cursor
     * will be moved
     */
    public void printAt(Position p) {
        //TODO: Validar que la posicion sea valida
        System.out.print(CursorMovementCodes.setCursorPosition(p.getLin(), p.getCol()));
    }

    /**
     * Saves the cursor position
     */
    public void saveCursorPos() {
        System.out.print(saveCursorPosition());

    }

    /**
     * Restores the cursor position
     */
    public void restoreCursorPos() {
        System.out.print(restoreCursorPosition());
    }


    /* ----------------------------------------- Cursor control codes methods --------------------------------------- */
    /**
     * Hides the cursor
     * CODE: DECTCEM (Text Cursor Enable Mode Hide)
     */
    public void cursorHide() {System.out.print(hideCursor());}

    /**
     * Shows the cursor
     * CODE: DECTCEM (Text Cursor Enable Mode Show)
     */
    public void cursorShow() {System.out.print(showCursor());}
    
    
    /**
     * Enables cursor blinking
     * CODE: ATT160 (Text Cursor Enable Blinking)
     */
    public void cursorBlink() {System.out.print(enableCursorBlink());}
    
    /**
     * Disables cursor blinking
     * CODE: ATT160 (Text Cursor Disable Blinking)
     */
    public void cursorNoBlink() {System.out.print(disableCursorBlink());}


    /* ---------------------------------------------- Cursor Styles methods ----------------------------------------- */
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
        if (style == null || style.isEmpty()) throw new ANSITermException(EX_STYLE_INVALID);

        if (!style.equals(CURSOR_STEADY_BAR_SHAPE)
                && !style.equals(CURSOR_BLINKING_BAR_SHAPE)
                && !style.equals(CURSOR_STEADY_BLOCK_SHAPE)
                && !style.equals(CURSOR_BLOCK_SHAPE)
                && !style.equals(CURSOR_STEADY_UNDERLINE_SHAPE)
                && !style.equals(CURSOR_UNDERLINE_SHAPE)
                && !style.equals(CURSOR_USER_SHAPE)) {
            throw new ANSITermException(EX_STYLE_UNKNOWN);
        }
        System.out.print(style);
    }

    /* -------------------------------------------- Text modification ----------------------------------------------- */

    /**
     * Inserts the specified number of spaces into the terminal's output.
     *
     * @param cars the number of spaces to insert; must be a positive integer
     * @throws ANSITermException if the provided number of spaces is invalid (e.g., non-positive)
     */
    public void insertNSpaces(int cars) throws ANSITermException {
        if (cars > 0) {
            System.out.print(insertSpaces(cars));
        } else {
            throw new ANSITermException(EX_CARS_INVALID);
        }
    }

    /**
     * Deletes 'cars' characters at the current cursor position, shifting
     * space characters from the right edge of the screen.
     *
     * @param cars the number of characters to delete.
     * @throws ANSITermException if the provided number of characters is invalid (e.g., non-positive)
     */
    public void deleteCharacters(int cars) throws ANSITermException {
        if (cars > 0) {
            System.out.print(eraseCharacters(cars));
        } else {
            throw new ANSITermException(EX_CARS_INVALID);
        }
    }

    /**
     * Deletes 'cars' characters from the current cursor position by
     * overwriting them with a space character.
     *
     * @param cars the number of characters to delete.
     * @throws ANSITermException if the provided number of characters is invalid (e.g., non-positive)
     */
    public void deleteCharsWithSpaces(int cars)  throws ANSITermException {
        if (cars > 0) {
            System.out.print(eraseCharactersWithSpaces(cars));
        } else {
            throw new ANSITermException(EX_WHITE_SPACE_INVALID);
        }
    }

    /**
     * Inserts the lines indicated by 'lines' into the screen buffer at the
     * cursor position. The line where the cursor is and the lines below it
     * will shift down.
     *
     * @param lines the number of lines to insert.
     * @throws ANSITermException if the provided number of lines is invalid (e.g., non-positive)
     */
    public void insertNLines(int lines) throws ANSITermException {
        if (lines > 0) {
            System.out.print(insertLines(lines));
        } else {
            throw new ANSITermException(EX_LINES_INVALID);
        }
    }

    /**
     * Deletes the lines indicated by 'lines' from the buffer, starting from
     * the row where the cursor is located.
     *
     * @param lines the number of lines to delete.
     * @throws ANSITermException if the provided number of lines is invalid (e.g., non-positive)
     */
    public void eraseLines(int lines) throws ANSITermException {
        if (lines > 0) {
            System.out.print(deleteLines(lines));
        } else {
            throw new ANSITermException(EX_LINES_INVALID);
        }
    }

    /* ------------------------------------------ Erase functions --------------------------------------------------- */
    /**
     * Deletes everything from the cursor position to the end of the screen
     */
    public void eraseFromCursorToEndScreen() {
        System.out.print(deleteFromCursorToEndScreen());
    }

    /**
     * Deletes everything from the cursor position to the beginning of the screen
     */
    public void eraseFromCursorToBeginScreen() {System.out.print(deleteFromCursorToBeginScreen()); }

    /**
     * Erases the screen
     */
    public void clearTerminal() {
        System.out.print(clearScreen());
    }

    /**
     * Deletes everything from the cursor position to the end of the line
     * where it is located.
     */
    public void eraseFromCursorToEndLine() {
        System.out.print(deleteFromCursorToEndLine());
    }

    /**
     * Deletes everything from the cursor position to the beginning of the line
     * where it is located.
     */
    public void eraseFromCursorToBeginLine() {
        System.out.print(deleteFromCursorToBeginLine());
    }

    /**
     * Erases possible characters from the current line where the cursor is
     * located.
     */
    public void deleteCursorLine() {
        System.out.print(deleteLine());
    }

    /* ----------------------------------------------- Colors and styles -------------------------------------------- */
    /**
     * Sets bold mode for the passed string
     *
     * @param msg the string to put in bold
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be in bold.
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setBold(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(BOLD_START)
                .append(msg)
                .append(ESC).append(BOLD_END);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
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
            sb.append(ESC).append(DIM_START)
                    .append(msg)
                    .append(ESC).append(DIM_END);
        }
        return sb.toString();
    }

    /**
     * Sets italic mode for the passed string
     *
     * @param msg the string that will be put in italic mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be in italics.
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setItalic(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(ITALIC_START)
                .append(msg)
                .append(ESC).append(ITALIC_END);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }
        
        return sb.toString();
    }

    /**
     * Sets underline mode for the passed string
     *
     * @param msg the string that will be put in underline mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be underlined.
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setUnderline(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(UNDERLINE_START)
                .append(msg)
                .append(ESC).append(UNDERLINE_STOP);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }
        
        return sb.toString();
    }

    /**
     * Sets blink mode for the passed string
     *
     * @param msg the string that will be put in blink mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be blinking.
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setBlink(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(BLINK_START)
                .append(msg)
                .append(ESC).append(BLINK_END);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }
        
        return sb.toString();
    }

    /**
     * Sets inverse mode for the passed string
     *
     * @param msg the string that will be put in inverse mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will be inverted (foreground/background color swap).
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setInverse(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(REVERSE_START)
                .append(msg)
                .append(ESC).append(REVERSE_END);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
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
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setHidden(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(INVISIBLE_START)
                .append(msg)
                .append(ESC).append(INVISIBLE_END);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
        }
        
        return sb.toString();
    }

    /**
     * Sets strikethrough mode for the passed string
     *
     * @param msg the string that will be put in strikethrough mode
     * @return a String with the ANSI sequence that, if shown in the
     * terminal, will appear struck through.
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setStrikeThrough(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(STRIKETHROUGH_START)
                .append(msg)
                .append(ESC).append(STRIKETHROUGH_END);
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
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
                        DIM_START).append(ESC +
                        DIM_END);
                } else {
                    sb.append(setDim(msg));
                    isMessageAdded = true;
                }
            }
            if (isItalic) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                        ITALIC_START).append(ESC +
                        ITALIC_END);
                } else {
                    sb.append(setItalic(msg));
                    isMessageAdded = true;
                }
            }
            if (isUnderline) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                        UNDERLINE_START).append(ESC +
                        UNDERLINE_STOP);
                } else {
                    sb.append(setUnderline(msg));
                    isMessageAdded = true;
                }
            }
            if (isBlink) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                        BLINK_START).append(ESC +
                        BLINK_END);
                } else {
                    sb.append(setBlink(msg));
                    isMessageAdded = true;
                }
            }
            if (isInverse) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                        REVERSE_START).append(ESC +
                        REVERSE_END);
                } else {
                    sb.append(setInverse(msg));
                    isMessageAdded = true;
                }
            }
            if (isStrikeThrough) {
                if (isMessageAdded) {
                    sb.insert(0, ESC +
                            STRIKETHROUGH_START).append(ESC +
                            STRIKETHROUGH_END);
                } else sb.append(setStrikeThrough(msg));
            }
        } else throw new ANSITermException(EX_NO_MSG);
        return sb.toString();
    }

    /**
     * Sets text color
     * @param color A Color enum constant with the color
     * @param msg The string to be colored
     * @return a string with the appropriate ANSI sequence to show the text
     * with the indicated color in the console.
     * @throws IllegalArgumentException In case any argument is not valid.
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
                .append(Color.DEFAULT.getAsString())
                .append("m");
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
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
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setColor256(int color, String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        
        if (color < 0 || color > 255) {
            throw new IllegalArgumentException(EX_NO_COL);
        }
        
        if (msg == null || msg.isEmpty()) {
            throw new IllegalArgumentException(EX_NO_MSG);
        }
        
        sb.append(ESC).append(FOREGROUND_COLOR256)
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
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setBackgroundColor256(int color, String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        
        if (color <= 0 || color > 255) {
            throw new IllegalArgumentException(EX_NO_COL);
        }
        
        if (msg == null || msg.isEmpty()) {
            throw new IllegalArgumentException(EX_NO_MSG);
        }
        
        sb.append(ESC).append(BACKGROUND_COLOR256)
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
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setBackgroundColor(BGColor color, String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        int iColor = Integer.parseInt(color.getAsString());
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append("[")
                .append(iColor)
                .append("m")
                .append(msg)
                .append(ESC)
                .append("[")
                .append(DEFAULT.getAsString())
                .append("m");
        } else {
            throw new IllegalArgumentException(EX_NO_MSG);
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
        if (iColor < 30 || iColor > 37) throw new ANSITermException(EX_NO_COL);
        if (bColor < 40) throw new ANSITermException(EX_NO_BACKCOL);
        if ((msg == null || msg.isEmpty())) throw new ANSITermException(EX_NO_MSG);

        sb.append(ESC);
        sb.append("[");
        sb.append(color);
        sb.append(";");
        sb.append(backgroundColor);
        sb.append("m");
        sb.append(msg);
        sb.append(ESC);
        sb.append("[");
        sb.append(RESET_COLOR_AND_STYLES);
        sb.append("m");
        return sb.toString();
    }

    /**
     * Resets the terminal to its default values. It is recommended to call
     * this method when you finish using Terminal in your application.
     */
    public void resetScreen() {
        System.out.print(RESTORES_SCREEN);
    }



    /* ----------------------------------------------- Position functions -------------------------------------------- */
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
     * @throws ANSITermException In case of invalid arguments.
     */
    public void printAt(String msg, Position p) throws ANSITermException {
        try {
            printAt(msg, p.getCol(), p.getLin());
        } catch (ANSITermException e) {
            throw new ANSITermException(e);
        }
    }

    /**
     * Scrolls the text up by the specified number of lines.
     *
     * @param lines the number of lines by which the text should be scrolled upwards
     */
    public void scrollTextUp(int lines) {
        System.out.println(scrollLinesUp(lines));
    }

    /**
     * Scrolls the text down by the specified number of lines.
     *
     * @param lines the number of lines to scroll the text down
     */
    public void scrollTextDown(int lines) {
        System.out.println(scrollLinesDown(lines));
    }

    /**
     * Captures and saves the current state of the screen.
     */
    public void saveScreenBuffer(){
        System.out.print(saveScreen());
    }

    /**
     * Restores the screen to its previous state by outputting the appropriate
     * escape sequence for screen restoration. The escape sequence is retrieved
     * from the PositionCodes utility class using the getESforRestoreScreen method.
     */
    public void restoreScreenBuffer(){
        System.out.print(restoreScreen());
    }

    /**
     * Enables the alternative buffer in the terminal by sending the appropriate escape sequence.
     * <p>
     * This method activates an alternative screen buffer, which is often used for full-screen
     * terminal applications. When the alternative buffer is enabled, the terminal switches
     * to a separate buffer where screen content can be modified without affecting the main buffer.
     * Typically, the alternative buffer is cleared upon activation.
     * <p>
     * The method retrieves the escape sequence to enable the alternative buffer from
     * the {@code PositionCodes.getESforEnableAlternateBuffer()} method and outputs it to the
     * terminal standard output.
     */
    public void enableAlternativeBuffer(){
        System.out.print(enableAlternateBuffer());
    }

    /**
     * Disables the alternative buffer for the terminal.
     * <p>
     * This method typically switches the terminal back to the primary display
     * buffer, restoring the previous terminal contents and cursor position.
     * It utilizes the appropriate escape sequence provided by the
     * {@code PositionCodes.getESforDisableAlternateBuffer()} method
     * to accomplish this.
     */
    public void disableAlternativeBuffer(){
        System.out.print(disableAlternateBuffer());
    }

    /* --------------------------------------------- Window Title methods ------------------------------------------- */

    /**
     * Sets the window title
     * @param title the title to set for the window
     */
    public void setWinTitle(String title){
        System.out.print(setWindowTitle(title));
    }

    /**
     * Sets only the window title
     * @param title the title to set for the window
     */
    public void setOnlyWinTitle(String title){
        System.out.print(updateWindowTitle(title));
    }

    /* ---------------------------------------- Scrolling Margins methods ------------------------------------------- */

    /**
     * Sets the scrolling margins for the top and bottom of the display or viewport.
     *
     * @param top    the size of the margin at the top, typically measured in pixels or rows
     * @param bottom the size of the margin at the bottom, typically measured in pixels or rows
     */
    public void setScrollMargins(int top, int bottom){
        System.out.print(setScrollingMargins(top, bottom));
    }

    /**
     * Resets the scrolling margins
     */
    public void resetScrollMargins(){
        System.out.print(resetScrollingMargins());
    }

    /* ---------------------------------------- Windows Witdh methods ----------------------------------------------- */

    /**
     * Sets the window width to 132 columns
     */
    public void setWindowWidth132Columns(){
        System.out.print(setWidthTo132());
    }

    /**
     * Sets the window width to 80 columns
     */
    public void setWindowWidth80Columns(){
        System.out.print(setWidthTo80());
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
