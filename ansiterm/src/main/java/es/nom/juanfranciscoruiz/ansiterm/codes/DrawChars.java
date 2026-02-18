package es.nom.juanfranciscoruiz.ansiterm.codes;

/**
 * This class provides predefined character constants for drawing characters in terminal applications.
 * It is used in conjunction with the CharacterSetModeCodes to create box layouts, tables, or borders.
 * Table for ASCII character maps to which line drawing character.
 * <table>
 *     <caption>ASCII Character Map to Line Drawing Characters</caption>
 * <tr><th>CONSTANT NAME</th><th>Hex</th><th>ASCII</th><th>DEC</th></tr>
 * <tr><td>RIGHT_DOWN_CORNER</td><td>0x6a</td><td>j</td><td>┘</td></tr>
 * <tr><td>RIGHT_UP_CORNER</td><td>0x6b</td><td>k</td><td>┐</td></tr>
 * <tr><td>LEFT_UP_CORNER</td><td>0x6c</td><td>l</td><td>┌</td></tr>
 * <tr><td>LEFT_DOWN_CORNER</td><td>0x6d</td><td>m</td><td>└</td></tr>
 * <tr><td>CROSS</td><td>0x6e</td><td>n</td><td>┼</td></tr>
 * <tr><td>HORIZONTAL_LINE</td><td>0x71</td><td>q</td><td>─</td></tr>
 * <tr><td>T_LEFT</td><td>0x74</td><td>t</td><td>├</td></tr>
 * <tr><td>T_RIGHT</td><td>0x75</td><td>u</td><td>┤</td></tr>
 * <tr><td>T_DOWN</td><td>0x76</td><td>v</td><td>┴</td></tr>
 * <tr><td>T_UP</td><td>0x77</td><td>w</td><td>┬</td></tr>
 * <tr><td>VERTICAL_LINE</td><td>0x78</td><td>x</td><td>│</td></tr>
 * </table>
 *
 * @author Juan F. Ruiz
 */
public class DrawChars {

    /**
     * Represents the right-down corner character (┘) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x6A in hexadecimal (ASCII 'j') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char RD_CORNER = 0x6a; // ┘ (right down corner)
    /**
     * Represents the right-up corner character (┐) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x6B in hexadecimal (ASCII 'k') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char RU_CORNER = 0x6b; // ┐ (right up corner)
    /**
     * Represents the left-up corner character (┌) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x6C in hexadecimal (ASCII 'l') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char LU_CORNER = 0x6c; // ┌ (left up corner)
    /**
     * Represents the left-down corner character (└) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x6D in hexadecimal (ASCII 'm') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char LD_CORNER = 0x6d; // └ (left down corner)
    /**
     * Represents the cross character (┼) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x6E in hexadecimal (ASCII 'n') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char CROSS = 0x6e;     // ┼ (cross)
    /**
     * Represents the horizontal line character (─) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x71 in hexadecimal (ASCII 'q') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char HL = 0x71;        // ─ (horizontal line)
    /**
     * Represents the vertical line character (│) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x78 in hexadecimal (ASCII 'x') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char VL = 0x78;        // │ (vertical line)
    /**
     * Represents the top-left corner character (├) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x74 in hexadecimal (ASCII 't') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char T_LEFT = 0x74;    // ├ (top left)
    /**
     * Represents the top-right corner character (┤) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x75 in hexadecimal (ASCII 'u') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char T_RIGHT = 0x75;   // ┤ (top right)
    /**
     * Represents the bottom-left corner character (└) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x76 in hexadecimal (ASCII 'v') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char T_DOWN = 0x76;    // ┴ (bottom left)
    /**
     * Represents the bottom-right corner character (┬) used for creating
     * box layouts, tables, or borders in terminal applications.
     * The character is mapped to 0x77 in hexadecimal (ASCII 'w') and is
     * commonly utilized in conjunction with the DEC Line Drawing Mode.
     */
    public final static char T_UP = 0x77;      // ┬ (bottom right)

    //TODO: Add more characters if any

    /**
     * Private constructor to prevent instantiation.
     */
    private DrawChars() {
    }

    @Override
    public String toString() {
        return "DrawChars{'ANSI escape codes for drawing characters'}";
    }
}
