package es.nom.juanfranciscoruiz.ansiterm;

import com.sun.jna.LastErrorException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.nom.juanfranciscoruiz.ansiterm.codes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

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
 * @author Juan F. Ruiz
 */
public class ANSITerm {

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
     * ANSI escape sequence generator
     */
    private static CSI CSI;

    /**
     * General ASCII controls
     */
    private static GeneralAsciiCodes gac;

    /**
     * Cursor movement controls
     */
    private static CursorMovementCodes cmc;

    /**
     * Erase sequences
     */
    private EraseSecuencesCodes esec;

    /**
     * Control sequences for colors and styles
     */
    private static ColorsAndStylesCodes csc;

    /**
     * Cursor control codes
     */
    private static CursorControlCodes ccc;

    /**
     * Viewport position codes
     */
    private static PositionCodes vpc;

    /**
     * Error message for empty or whitespace-only messages.
     */
    private static final String EX_NO_MSG = "No message, it is empty or only contains whitespace";

    /**
     * Error message for invalid color.
     */
    private static final String EX_NO_COL = "Invalid color";

    /**
     * Error message for invalid background color.
     */
    private static final String EX_NO_BACKCOL = "Invalid background color";

    /**
     * Instantiates a new Terminal object.
     * Depending on the operating system where the class is executed, it will
     * instantiate a WindowsTerminal or LinuxTerminal object for when it has to
     * call low-level system functions.
     * 
     * @throws java.lang.Exception In case the operating system is not
     * Windows or Linux
     */
    public ANSITerm() throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            osCall = new WindowsTerminal();
        } else if (os.contains("linux")) {
            osCall = new LinuxTerminal();
        }  else {
            throw new Exception("Unsupported operating system");
        }
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
        System.out.print(GeneralAsciiCodes.BELL);
    }

    /**
     * Causes a cursor backspace
     */
    public void backSpace() {
        System.out.print(GeneralAsciiCodes.BS);
    }

    /**
     * Generates a tab
     */
    public void tab() {
        System.out.print(GeneralAsciiCodes.TAB);
    }

    /**
     * Generates a line feed
     */
    public void linefeed() {
        System.out.print(GeneralAsciiCodes.LF);
    }

    /**
     * Generates a vertical tab
     */
    public void verticalTab() {
        System.out.print(GeneralAsciiCodes.VT);
    }

    /**
     * Generates a form feed
     */
    public void formfeed() {
        System.out.print(GeneralAsciiCodes.FF);
    }

    /**
     * Generates a carriage return
     */
    public void carriagereturn() {
        // In the Windows terminal it moves the cursor to the beginning of the line
        // To do a line break you have to do a linefeed() or use
        // the Java \n escape code
        System.out.print(GeneralAsciiCodes.CR);
    }

    /**
     * Deletes the character before the cursor position.
     */
    public void deleteCharacter() {
        // Does not work on Windows.
        char c = 127; //DEL (Delete character)
        System.out.print(c);
    }

    // Cursor functions
    /**
     * Moves the cursor to the beginning of the terminal (0,0)
     */
    public void moveCursorToBegin() {
        System.out.print(CursorMovementCodes.CURSOR_MOVE_TO_00);
    }

    /**
     * Moves the cursor to the line, column position of the terminal
     *
     * @param line integer with the line to move the cursor to
     * @param column integer with the column to move the cursor to
     */
    public void moveCursorToXY(int line, int column) {
        String sec_ansi = ESC + "[" + line + ";" + column + "H";
        System.out.print(sec_ansi);
    }

    /**
     * Moves the cursor to the terminal position indicated by p
     *
     * @param p Posicion object containing the position where the cursor
     * will be moved
     */
    public void moveCursorToXY(Position p) {
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

            System.out.print(CursorMovementCodes.CURSOR_GET_POSITION);

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
                return new Position(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)));
            } else {
                return new Position(1, 1);
            }

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
        System.out.println(CursorMovementCodes.CURSOR_MOVE_ONE_LINE_UP);
    }

    /**
     * Moves the cursor up as many lines as indicated in the 'lines' parameter<br>
     * CODE: CUU (Cursor Up)
     * NOTE for the methods:<br>
     * <ul>
     * <li>moveCursorUp(int lines)</li>
     * <li>moveCursorDown(int lines)</li>
     * <li>moveCursorRight(int cars)</li>
     * <li>moveCursorLeft(int cars)</li>
     *
     * </ul>
     * <ul>
     * <li>'lines' or 'cars' represents the distance of transfer and is a
     * parameter that is optional for the control sequence (but mandatory
     * in the function, if you want to omit it indicate 0).</li>
     * <li>If 'lines' or 'cars' is omitted or equals 0, it will be treated as 1.</li>
     * <li>'lines' or 'cars' cannot be greater than 32,767 (maximum short value).</li>
     * <li>'lines' or 'cars' cannot be negative.</li>
     * </ul>
     *
     *
     * @param lines an integer with the lines up where the cursor will be moved
     */
    public void moveCursorUp(int lines) {
        // ESC[#A
        String sec_ansi = ESC + "[" + lines + "A";
        System.out.print(sec_ansi);
    }

    /**
     * Moves the cursor down as many lines as indicated in the lines parameter
     * CODE: CUD (Cursor Down)
     * @see #moveCursorUp(int)
     * @param lines an integer with the lines down where the cursor will be moved
     */
    public void moveCursorDown(int lines) {
        // ESC[#B
        String sec_ansi = ESC + "[" + lines + "B";
        System.out.print(sec_ansi);
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
        // ESC[#C
        String sec_ansi = ESC + "[" + cars + "C";
        System.out.print(sec_ansi);
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
        // ESC[#D
        String sec_ansi = ESC + "[" + cars + "D";
        System.out.print(sec_ansi);
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
     */
    public void cursorChangeStyle(String style) {
        this.cursorShow();
        if (style == null || style.isEmpty()) {
            throw new IllegalArgumentException("Invalid style");
        }

        if (!style.equals(CursorStylesCodes.CURSOR_STEADY_BAR_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_BLINKING_BAR_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_STEADY_BLOCK_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_BLOCK_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_STEADY_UNDERLINE_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_UNDERLINE_SHAPE)
                && !style.equals(CursorStylesCodes.CURSOR_USER_SHAPE)) {
            throw new IllegalArgumentException("Unrecognized style");
        }
        System.out.print(style);
    }

    /**
     * Scrolls text up by as many lines as indicated in 'lines'.
     * Option also known as "Panning down", new lines are filled from the
     * bottom of the screen.
     *
     * @param lines the number of lines to scroll
     */
    public void moveTextUp(int lines) {
        String sec_ansi = ESC + "[" + lines + "S";
        System.out.print(sec_ansi);
    }

    /**
     * Scrolls down by as many lines as indicated in 'lines'. Option also
     * known as "Panning up", new lines are filled from the top of the screen.
     *
     * @param lines the number of lines to scroll
     */
    public void moveTextDown(int lines) {
        String sec_ansi = ESC + "[" + lines + "T";
        System.out.print(sec_ansi);
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
        System.out.print(EraseSecuencesCodes.ERASES_FROM_CURSOR_TO_END_OF_SCREEN);
    }

    /**
     * Deletes everything from the cursor position to the beginning of the screen
     */
    public void deleteFromCursorToBeginScreen() {
        System.out.print(EraseSecuencesCodes.ERASES_FROM_CURSOR_TO_BEGINNING_OF_SCREEN);
    }

    /**
     * Erases the screen
     */
    public void clearScreen() {
        System.out.print(EraseSecuencesCodes.CLEAR_SCREEN);
    }

    /**
     * Deletes everything from the cursor position to the end of the line
     * where it is located.
     */
    public void deleteFromCursorToEndLine() {
        System.out.print(EraseSecuencesCodes.ERASES_FROM_CURSOR_TO_END_OF_CURRENT_LINE);
    }

    /**
     * Deletes everything from the cursor position to the beginning of the line
     * where it is located.
     */
    public void deleteFromCursorToBeginLine() {
        System.out.print(EraseSecuencesCodes.ERASES_FROM_CURSOR_TO_BEGINNING_OF_CURRENT_LINE);
    }

    /**
     * Erases possible characters from the current line where the cursor is
     * located.
     */
    public void deleteLine() {
        System.out.print(EraseSecuencesCodes.ERASES_CURRENT_LINE);
    }

    // Colors and styles
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
            sb.append(ESC).append(ColorsAndStylesCodes.BOLD_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.BOLD_END);
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
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setItalic(String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !msg.isEmpty()) {
            sb.append(ESC).append(ColorsAndStylesCodes.ITALIC_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.ITALIC_END);
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
            sb.append(ESC).append(ColorsAndStylesCodes.UNDERLINE_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.UNDERLINE_STOP);
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
            sb.append(ESC).append(ColorsAndStylesCodes.BLINK_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.BLINK_END);
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
            sb.append(ESC).append(ColorsAndStylesCodes.REVERSE_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.REVERSE_END);
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
            sb.append(ESC).append(ColorsAndStylesCodes.INVISIBLE_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.INVISIBLE_END);
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
            sb.append(ESC).append(ColorsAndStylesCodes.STRIKETHROUGH_START)
                    .append(msg)
                    .append(ESC).append(ColorsAndStylesCodes.STRIKETHROUGH_END);
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
     * @throws IllegalArgumentException In case any argument is not valid.
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
            throw new IllegalArgumentException(EX_NO_MSG);
        }

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
                throw new IllegalArgumentException(EX_NO_MSG);
            }
        } else {
            throw new IllegalArgumentException(EX_NO_COL);
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
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setBackgroundColor(BGColor color, String msg) throws IllegalArgumentException {
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
                throw new IllegalArgumentException(EX_NO_MSG);
            }
        } else {
            throw new IllegalArgumentException(EX_NO_BACKCOL);
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
     * @throws IllegalArgumentException In case any argument is not valid.
     */
    public String setColors(Color color, BGColor backgroundColor, String msg) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        int iColor = Integer.parseInt(color.getAsString());
        int bColor = Integer.parseInt(backgroundColor.getAsString());
        if ((iColor < 30 || iColor > 37)) {
            throw new IllegalArgumentException(EX_NO_COL);
        }
        if ((bColor < 40 || iColor > 47)) {
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
     * @throws IllegalArgumentException In case of invalid arguments.
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
     * Prints the msg string at position p in the terminal
     *
     * @param msg the string to print
     * @param p the terminal position where the string will start to be printed
     */
    public void printAt(String msg, Position p) {
        printAt(msg, p.getCol(), p.getLin());
    }
}
