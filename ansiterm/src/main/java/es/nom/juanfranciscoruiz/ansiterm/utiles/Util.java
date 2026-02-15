package es.nom.juanfranciscoruiz.ansiterm.utiles;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;
import es.nom.juanfranciscoruiz.ansiterm.TerminalSize;
import es.nom.juanfranciscoruiz.ansiterm.codes.Color;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;

import java.io.BufferedInputStream;
import java.util.Random;
import java.util.Scanner;

/**
 * Utility class providing helper methods for terminal operations, such as
 * pausing execution or printing formatted text blocks.
 *
 * @author Juan F. Ruiz
 */
public class Util {
    /**
     * Private constructor to prevent instantiation.
     */
    private Util() {
    }

    /**
     * Pauses the program for a specified number of milliseconds and optionally
     * displays a message. If no message is provided, it defaults to
     * "Press &lt;ENTER&gt; to continue..." on the last line of the terminal.
     * <p>
     * If 0 milliseconds are specified, the function waits for the user to
     * press ENTER. Otherwise, it informs the user that the program will
     * continue after the specified delay and then pauses.
     *
     * @param milliseconds The duration of the pause in milliseconds. If 0,
     *                     the function waits for the user to press ENTER.
     * @param msg          The message to display to the user.
     * @throws Exception In case of any error.
     */
    public static void pauseWithMessage(long milliseconds, String msg) throws Exception {
        ANSITerm t = new ANSITerm();
        if (msg == null || msg.isEmpty()) {
            msg = "\nPress <ENTER> to continue...";
        }

        TerminalSize ts = t.getTerminalSize();
        int lastLine = ts.getLines();

        if (milliseconds == 0) {
            t.printAt(msg, lastLine - 1, 1);
            Scanner sc = new Scanner(new BufferedInputStream(System.in));
            sc.nextLine();
            return;
        }
        t.printAt("The program will continue in " + milliseconds + " milliseconds...",
                lastLine - 1, 1);
        Thread.sleep(milliseconds);
    }

    /**
     * Pauses the program for a specified number of milliseconds. If 0 is
     * specified, it waits for the user to press ENTER. No message is
     * displayed.
     *
     * @param milliseconds The duration of the pause in milliseconds. If 0,
     *                     the function waits for the user to press ENTER.
     * @throws Exception In case of any error.
     */
    public static void pauseForMilliseconds(long milliseconds) throws Exception {
        ANSITerm t = new ANSITerm();
        t.moveCursorToBegin();
        if (milliseconds == 0) {
            Scanner sc = new Scanner(new BufferedInputStream(System.in));
            sc.nextLine();
            return;
        }
        Thread.sleep(milliseconds);
    }

    /**
     * Displays a block of random text starting at 'lineaInicial', with
     * 'lineas' number of rows and 'cols' number of columns per row.
     *
     * @param term         An ANSITerm object
     * @param startLine    The starting line in the terminal.
     * @param linesToPrint The number of lines in the text block.
     * @param cols         The number of columns (characters) in each line.
     * @throws ANSITermException If there's an error printing the text block.
     */
    public static void printRandomTextBlock(ANSITerm term, int startLine, int linesToPrint, int cols) throws ANSITermException {
        int car;
        char ascii;
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();

        for (int line = startLine; line < (startLine + linesToPrint); line++) {
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
     * Prints a string 'msg' starting at 'line, column' character by
     * character with the specified delay in 'milliseconds'.
     *
     * @param msg          The string to print.
     * @param line         The terminal line where the string will be printed.
     * @param column       The terminal column where the string will be printed.
     * @param term         An ANSITerm object.
     * @param milliseconds The delay between printing each character in milliseconds.
     * @throws ANSITermException If there's an error printing the string.
     */
    public static void printWithDelay(String msg,
                                      int line,
                                      int column,
                                      ANSITerm term,
                                      long milliseconds) throws ANSITermException {
        for (int i = 0; i < msg.length(); i++) {
            term.printAt(String.valueOf(msg.charAt(i)), line, column + i);
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * Clears the entire screen and moves the cursor to the beginning position.
     *
     * @param term The ANSITerm object responsible for terminal operations.
     */
    public static void clearScreen(ANSITerm term) {
        term.clearScreen();
        term.moveCursorToBegin();
    }

    /**
     * Prints the provided title at the second row and column of the terminal with
     * specific styles applied.
     *
     * @param term  The ANSITerm object used to manage terminal operations.
     * @param title The title text to be printed on the terminal.
     * @throws ANSITermException If an error occurs while setting styles or
     *                           printing the title.
     */
    public static void printTitle(ANSITerm term, String title) throws ANSITermException {
        term.setStyles(true, false, false, false, false, false, false, title);
        term.printAt(title, 2, 1);
    }

    /**
     * Prints a warning message at the specified position. The text is displayed
     * in a glossy red color.
     *
     * @param term The ANSITerm object responsible for terminal operations.
     * @param msg  The warning message to be printed.
     * @throws ANSITermException If there's an error printing the warning message.
     */
    public static void showWarning(ANSITerm term, String msg) throws ANSITermException {
        term.setColor(Color.GLOSSY_RED, msg);
        term.printAt(msg, 3, 1);
    }

}
