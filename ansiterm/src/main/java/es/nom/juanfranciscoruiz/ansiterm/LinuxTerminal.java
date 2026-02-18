package es.nom.juanfranciscoruiz.ansiterm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides access to the raw and cooked modes of the Linux terminal as well as
 * obtaining the terminal size using ANSI escape sequences and setting the
 * raw mode so that the ANSI code does not appear on the screen.
 *
 * @author Juan F. Ruiz
 */
public class LinuxTerminal implements ITerminal {
    
    /**
     * A logger instance for the {@code LinuxTerminal} class.
     * This is used to output logging information, such as error messages,
     * debug details, or general operational logs, to a configured logging system.
     * The logger uses the SLF4J API for flexible and configurable logging.
     */
    public static final Logger logger = LoggerFactory.getLogger(LinuxTerminal.class);
    
    /**
     * Represents the ANSI escape character sequence prefix, commonly used
     * to initiate control sequences for terminal text formatting, cursor movement,
     * and other terminal operations. This value is specific to UNIX-like environments
     * and allows manipulation of terminal behavior programmatically.
     */
    private static final String ESC = "\033";
    /**
     * Represents the ANSI escape sequence for retrieving the current cursor position.
     * This sequence is used to query the terminal for the current cursor position
     * and is part of the ANSI escape sequence standard.
     */
    private static final String REC_POS_CUR = ESC + "[6n";

    
    /**
     * Instantiates a LinuxTerminal object
     */
    public LinuxTerminal(){}
    
    /**
     * Enables the 'raw' mode of the console. It uses the 'stty' command which
     * is usually available in all UNIX environments. In 'raw' mode, the 
     * characters typed by the user are passed directly to the 
     * application without the user having to press ENTER. The '-echo' 
     * parameter is passed so that the characters typed do not appear 
     * on the screen.
     * Note: The method does not verify if the 'stty' command is available.
     * It's up to the user to ensure that the command is available in the system.
     */
    @Override
    public void enableRawMode() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty raw -echo < /dev/tty"};
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Disables the 'raw' mode of the terminal and enables the 'cooked' mode, 
     * which is the one normally used (typed characters are shown and for 
     * the shell to receive our command we have to press ENTER).
     * Note: The method does not verify if the 'stty' command is available.
     * It's up to the user to ensure that the command is available in the system.
     */
    @Override
    public void disableRawMode() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty cooked echo < /dev/tty"};
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    
    /**
     * Obtains the terminal size.
     * @return a TerminalSize object with the lines and columns of the terminal.
     */
    @Override
    public TerminalSize getTerminalSize() {
        Position inicialPosition = readCurrentPosition();
        gotoXY(10000, 10000); // Trick for getting the terminal size
        Position result = readCurrentPosition();
        gotoXY(inicialPosition.getCol(), inicialPosition.getLin());

        return new TerminalSize(result.getCol(), result.getLin());
    }


    /**
     * Reads the current cursor position in the terminal. The method switches the terminal
     * to 'raw' mode to process the position request, sends the appropriate ANSI escape
     * sequence to query the cursor position, captures the terminal's response, and parses
     * the result to create and return a {@link Position} object. If the position cannot
     * be determined due to an error, a default {@link Position} object with values (1, 1)
     * is returned. The terminal mode is restored to 'cooked' mode before the method exits.
     *
     * @return a {@link Position} object representing the current terminal cursor position,
     *         or a default {@link Position} with values (1, 1) if the position cannot be determined
     */
    private Position readCurrentPosition() {
        // Executes position request; extracts or defaults on failure
        try {
            this.enableRawMode();
            System.out.print(REC_POS_CUR);

            return getPosition();

        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return new Position(1, 1);
        } finally {
            this.disableRawMode();
        }
    }

    /**
     * Reads and parses the cursor position from the terminal input stream. The method listens for
     * a specific ANSI escape sequence response, which represents the cursor's current position.
     * If the sequence matches the expected format, the coordinates are extracted and used to
     * create a {@link Position} object. If parsing fails, a default {@link Position} with values
     * (1, 1) is returned.
     *
     * @return a {@link Position} object representing the current cursor position in the
     *         terminal, or a default {@link Position} with values (1, 1) in case of a failure
     *         to correctly parse the terminal response.
     * @throws IOException if an error occurs while reading input from the terminal.
     */
    static Position getPosition() throws IOException {
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
    }

    /**
     * Moves the cursor to the specified position in the terminal based on the provided
     * x (column) and y (row) coordinates. The method uses an ANSI escape sequence to
     * position the cursor within the terminal.
     *
     * @param x the target column number to move the cursor to. Columns are numbered starting
     *          from 1, where 1 represents the leftmost column.
     * @param y the target row number to move the cursor to. Rows are numbered starting
     *          from 1, where 1 represents the topmost row.
     */
    private void gotoXY(int x, int y) {
        System.out.printf("\u001B[%d;%dH", y, x); // CSI n ; m H
    }

    /**
     * Moves the cursor to the specified position in the terminal.
     * The position is determined by the provided {@link Position} object,
     * where the line and column values are used to set the cursor position.
     *
     * @param screenPosition a {@link Position} object containing the desired
     *                       line and column numbers to move the cursor to.
     */
    private void gotoXY(Position screenPosition) {
        System.out.printf("\u001B[%d;%dH", screenPosition.getLin(), screenPosition.getCol()); // CSI n ; m H
    }

    @Override
    public String toString() {
        return "LinuxTerminal{'Access to the raw and cooked modes of the Linux terminal'}";
    }
}
