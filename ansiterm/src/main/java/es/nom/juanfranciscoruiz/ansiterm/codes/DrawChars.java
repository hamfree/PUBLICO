package es.nom.juanfranciscoruiz.ansiterm.codes;

/**
 * This class provides predefined character constants for drawing characters in terminal applications.
 * It is used in conjunction with the CharacterSetModeCodes to create box layouts, tables, or borders.
 * Table for ASCII character maps to which line drawing character.
 * <table>
 * <tr><th>CONSTANT NAME</th><th>Hex</th><th>ASCII</th><th>DEC</th></tr>
 * <tr><td>RIGHT_DOWN_CORNER</td><td>0x6a</td><td>j</td><td>┘</td></tr>
 * <tr><td>RIGHT_UP_CORNER</td><td>0x6b</td><td>k</td><td>┐</td></tr>
 * <tr><td>LEFT_UP_CORNER</td>td>0x6c</td><td>l</td><td>┌</td></tr>
 * <tr><td>LEFT_DOWN_CORNER</td><td>0x6d</td><td>m</td><td>└</td></tr>
 * <tr><td>CROSS</td><td>0x6e</td><td>n</td><td>┼</td></tr>
 * <tr><td>HORIZONTAL_LINE</td><td>0x71</td><td>q</td><td>─</td></tr>
 * <tr><td>T_LEFT</td><td>0x74</td><td>t</td><td>├</td></tr>
 * <tr><td>T_RIGHT</td><td>0x75</td><td>u</td><td>┤</td></tr>
 * <tr><td>T_DOWN</td><td>0x76</td><td>v</td><td>┴</td></tr>
 * <tr><td>T_UP</td><td>0x77</td><td>w</td><td>┬</td></tr>
 * <tr><td>VERTICAL_LINE</td><td>0x78</td><td>x</td><td>│</td></tr>
 * </table>
 */
public class DrawChars {
  /**
   * The next constants are used in terminal applications that utilize the DEC Line Drawing Mode
   * for creating box layouts, tables, or borders.
   *
   */
  final static char RIGHT_DOWN_CORNER = 0x6a;
  final static char RIGHT_UP_CORNER = 0x6b;
  final static char LEFT_UP_CORNER = 0x6c;
  final static char LEFT_DOWN_CORNER = 0x6d;
  final static char CROSS = 0x6e;
  final static char HORIZONTAL_LINE = 0x71;
  final static char VERTICAL_LINE = 0x78;
  final static char T_LEFT = 0x74;
  final static char T_RIGHT = 0x75;
  final static char T_DOWN = 0x76;
  final static char T_UP = 0x77;
  
  //TODO: Add more characters
  
  /**
   * Private constructor to prevent instantiation.
   */
  private DrawChars() {
  }
  
  @Override
  public String toString() {
    return "DrawChars [RIGHT_DOWN_CORNER=" + RIGHT_DOWN_CORNER +
        ", RIGHT_UP_CORNER=" + RIGHT_UP_CORNER +
        ", LEFT_UP_CORNER=" + LEFT_UP_CORNER +
        ", LEFT_DOWN_CORNER=" + LEFT_DOWN_CORNER +
        ", CROSS=" + CROSS +
        ", HORIZONTAL_LINE=" + HORIZONTAL_LINE +
        ", VERTICAL_LINE=" + VERTICAL_LINE +
        ", T_LEFT=" + T_LEFT +
        ", T_RIGHT=" + T_RIGHT +
        ", T_DOWN=" + T_DOWN +
        ", T_UP=" + T_UP + "]";
  }
}
