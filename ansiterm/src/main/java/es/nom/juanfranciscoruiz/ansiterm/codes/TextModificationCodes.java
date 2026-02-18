package es.nom.juanfranciscoruiz.ansiterm.codes;

/**
 * Provides ANSI escape codes for text modification operations.
 * <p>
 * This class includes methods to generate codes for inserting and deleting
 * characters and lines within the terminal.
 * </p>
 */
public class TextModificationCodes {
    /**
     * Private constructor to enforce Singleton pattern.
     */
    private TextModificationCodes() {
    }

    /**
     * ICH - Insert Character.
     * <p>
     * Inserts the specified number of spaces at the cursor position, 
     * shifting all existing text to the right.
     * </p>
     * 
     * @param n The number of characters to insert.
     * @return The ANSI escape sequence.
     */
    public static String insertSpaces(int n) {
        return CSI.ESC + "[" + n + "@";
    }

    /**
     * DCH - Delete Character.
     * <p>
     * Deletes the specified number of characters at the cursor position, 
     * shifting remaining text to the left.
     * </p>
     * 
     * @param n The number of characters to delete.
     * @return The ANSI escape sequence.
     */
    public static String eraseCharacters(int n) {
        return CSI.ESC + "[" + n + "P";
    }

    /**
     * ECH - Erase Character.
     * <p>
     * Erases the specified number of characters from the cursor position 
     * by overwriting them with spaces.
     * </p>
     * 
     * @param n The number of characters to erase.
     * @return The ANSI escape sequence.
     */
    public static String eraseCharactersWithSpaces(int n) {
        return CSI.ESC + "[" + n + "X";
    }

    /**
     * IL - Insert Line.
     * <p>
     * Inserts the specified number of lines at the cursor position.
     * </p>
     * 
     * @param n The number of lines to insert.
     * @return The ANSI escape sequence.
     */
    public static String insertLines(int n) {
        return CSI.ESC + "[" + n + "L";
    }

    /**
     * DL - Delete Line.
     * <p>
     * Deletes the specified number of lines starting from the cursor position.
     * </p>
     * 
     * @param n The number of lines to delete.
     * @return The ANSI escape sequence.
     */
    public static String deleteLines(int n) {
        return CSI.ESC + "[" + n + "M";
    }

}
