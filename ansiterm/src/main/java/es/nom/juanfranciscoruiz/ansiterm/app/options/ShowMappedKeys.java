package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.model.KeyCodes;
import es.nom.juanfranciscoruiz.ansiterm.model.KeySequence;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.clearScreenAndPrintHeader;
import static es.nom.juanfranciscoruiz.ansiterm.utiles.Stuff.pauseWithMessage;

/**
 * The ShowMappedKeys class is responsible for displaying all the currently
 * mapped keyboard scan codes along with their corresponding names in a terminal.
 * It uses the ANSITerm library for terminal interactions and KeyCodes to retrieve
 * the mappings.
 */
public class ShowMappedKeys {
    /**
     * Represents an instance of the ANSITerm terminal interface used for managing
     * terminal-related operations such as outputting content or retrieving terminal
     * dimensions. This is utilized in the context of displaying mapped keyboard
     * scan codes and their details in the terminal.
     * <p>
     * It serves as the terminal management component for interacting with the
     * terminal in a user-friendly and formatted manner using the ANSITerm library.
     */
    private final es.nom.juanfranciscoruiz.ansiterm.ANSITerm term;
    /**
     * Stores an instance of the KeyCodes model, which represents specialized key mappings
     * and configurations used for terminal interactions.
     */
    private final KeyCodes keyCodes;
    /**
     * Represents the title of the current mapped keys display.
     * This is a descriptive identifier used to label or categorize the content
     * shown to the user in the terminal.
     * <p>
     * The value of this field is immutable once assigned.
     */
    private final String title;
    /**
     * A message that represents textual information to be used or displayed
     * by the ShowMappedKeys class. This field is immutable and can be set
     * only during the initialization phase of the class.
     */
    private final String message;

    /**
     * Constructs a new ShowMappedKeys instance.
     *
     * @throws Exception If there is an error initializing the terminal.
     */
    public ShowMappedKeys() throws Exception {
        this.term = new es.nom.juanfranciscoruiz.ansiterm.ANSITerm();
        this.keyCodes = new KeyCodes();
        this.title = "Mapped Keys Display";
        this.message = "Displays all the currently mapped keyboard scan codes and their names.";
    }

    /**
     * Performs the action of displaying all mapped keys in the terminal.
     *
     * @throws Exception If an error occurs during terminal output.
     */
    public void perform() throws Exception {
        int columns = term.getTerminalSize().getColumns();
        AtomicInteger line = new AtomicInteger(6);
        AtomicInteger index = new AtomicInteger(1);
        Map<KeySequence, String> mappings = keyCodes.getMapKeycodes();

        clearScreenAndPrintHeader(term, title, message, columns);

        mappings.forEach((sequence, name) -> {
            try {
                if (line.get() < term.getTerminalSize().getLines() - 1) {
                    term.printAt(String.format("%d - Sequence: %-15s -> Name: %s",
                                    index.getAndIncrement(),
                                    sequence.toString(), name),
                            line.getAndIncrement(), 1);
                } else {
                    pauseWithMessage(0, "Press <ENTER> to continue");
                    term.clearTerminal();
                    clearScreenAndPrintHeader(term, title, message, columns);
                    line.set(6);
                    term.printAt(String.format("%d - Sequence: %-15s -> Name: %s",
                                    index.getAndIncrement(),
                                    sequence.toString(), name),
                            line.getAndIncrement(), 1);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        term.printAt("-".repeat(columns), line.getAndIncrement(), 1);
        pauseWithMessage(0, "Press <ENTER> to return to menu");
    }

}
