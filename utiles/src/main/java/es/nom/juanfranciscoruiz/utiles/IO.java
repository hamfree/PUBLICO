package es.nom.juanfranciscoruiz.utiles;

/**
 * The IO interface provides utility methods for performing input and output operations,
 * including printing formatted messages, reading input, and generating formatted strings
 * for various purposes.
 * @author Juan F. Ruiz
 */
public interface IO {
    /**
     * Prints the provided arguments to the output. This method acts as a utility
     * for formatting and printing objects. If invalid arguments are provided,
     * an IllegalArgumentException is thrown.
     *
     * @param args a variable number of arguments to be printed. Each argument
     *             will be converted to its string representation and included
     *             in the output.
     * @throws IllegalArgumentException if the provided arguments are invalid
     *                                   or if an error occurs during processing.
     */
    static void prt(Object... args) throws IllegalArgumentException {}

    /**
     * Prints the provided arguments to the output with a specified line separator.
     * This method acts as a utility for formatting and printing objects. If invalid
     * arguments are provided, an IllegalArgumentException is thrown.
     *
     * @param ls   the line separator to use for printing
     * @param args a variable number of arguments to be printed. Each argument
     *             will be converted to its string representation and included
     *             in the output.
     * @throws IllegalArgumentException if the provided arguments are invalid
     *                                   or if an error occurs during processing.
     */
    static void prtln(int ls, Object... args) throws IllegalArgumentException {}

    /**
     * Reads a line of input from the user.
     *
     * @return the input line as a string
     * @throws Exception if an error occurs during input reading
     */
    static String read() throws Exception {return "";}

    /**
     * Creates a title string with the specified message, character, and length.
     *
     * @param msg     the message to be included in the title
     * @param character the character to be used for the title
     * @param length  the length of the title
     * @return the formatted title string
     * @throws IllegalArgumentException if the provided arguments are invalid
     */
    static String title(String msg, Character character, int length) throws IllegalArgumentException {return msg;}

    /**
     * Creates a line string with the specified character and length.
     *
     * @param character the character to be used for the line
     * @param length  the length of the line
     * @return the formatted line string
     * @throws IllegalArgumentException if the provided arguments are invalid
     */
    static String line(Character character, int length) throws IllegalArgumentException {return null;}

    /**
     * Creates a string by repeating the specified character a given number of times.
     *
     * @param character the character to be repeated
     * @param times   the number of times to repeat the character
     * @return the formatted string with repeated characters
     * @throws IllegalArgumentException if the provided arguments are invalid
     */
    static String repeatCharacter(Character character, int times) throws IllegalArgumentException {return null;}
}
