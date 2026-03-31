package es.nom.juanfranciscoruiz.ansiterm.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the mapping between keyboard scan codes (or ANSI sequences) and
 * their descriptive names.
 *
 * @author Juan F. Ruiz
 */
public class KeyCodes {
    /**
     * A map that associates key sequences with their corresponding key names.
     * The key is represented by a {@link KeySequence} object, which encapsulates
     * a sequence of integer scan codes or ANSI escape sequences. The value is
     * a string that describes or names the associated key.
     * <p>
     * This mapping can be used to standardize key input handling in terminal-based
     * applications, providing a convenient way to interpret and process key sequences.
     * <p>
     * The map is immutable and initialized through the constructors or methods of
     * the {@code KeyCodes} class, and it is populated with either default or custom
     * key mappings.
     */
    private final Map<KeySequence, String> keycodes;
    /**
     * Represents an extended ASCII character set helper, which includes a range
     * of characters from 0-255. This variable is used to facilitate operations
     * and mappings involving the extended ASCII table.
     * <p>
     * The extended ASCII set includes both standard ASCII characters (0-127)
     * and additional characters (128-255), which may vary based on the encoding
     * or code page. This helper is designed to provide metadata and functionalities
     * such as character descriptions and code mappings for the extended ASCII range.
     */
    private final ASCIICharacterSetExtended asciiSet;
    /**
     * A textual description that provides metadata or contextual information
     * about the current instance of the KeyCodes class. This value is immutable
     * and set during object initialization.
     */
    private final String description;

    /**
     * Default constructor for the KeyCodes class.
     * This constructor initializes the key mappings and ASCII character set helper
     * with default values and setups.
     * <p>
     * The default configuration includes:<br>
     * - An initial description set to "Unknown description".<br>
     * - An empty mapping of key codes.<br>
     * - An extended ASCII character set helper instance.<br>
     * <p>
     * Additionally, it invokes the `setupDefaultMappings` method to populate the
     * `keycodes` map with standard key mappings for typical keyboard setups.
     */
    public KeyCodes() {
        this.description = "Unknown description";
        this.keycodes = new HashMap<>();
        this.asciiSet = new ASCIICharacterSetExtended();
        setupDefaultMappings();
    }

    /**
     * Constructor that initializes the KeyCodes with a provided description.
     * The description is used to provide additional metadata or details about
     * the specific instance of KeyCodes. Additionally, this constructor
     * initializes an empty map for key codes, creates an instance of
     * {@link ASCIICharacterSetExtended}, and calls the method to set up
     * default key mappings.
     *
     * @param description A string providing metadata or details about this
     *                    instance of KeyCodes.
     */
    public KeyCodes(String description) {
        this.description = description;
        this.keycodes = new HashMap<>();
        this.asciiSet = new ASCIICharacterSetExtended();
        setupDefaultMappings();
    }

    /**
     * Constructor that allows providing a custom map of key codes.
     *
     * @param keycodes A map where the key is a KeySequence representing
     *                 the scan codes and the value is the key name.
     * @param description A string providing metadata or details about this
     */
    public KeyCodes(Map<KeySequence, String> keycodes, String description) {
        this.keycodes = keycodes;
        this.description = (description != null) ? description : "Unknown description";
        this.asciiSet = new ASCIICharacterSetExtended();
    }

    /**
     * Initializes default key mappings for various keys and key sequences with
     * the values returned by the _getch() function from API calls in Windows
     * and the read() function from Libc API in Linux/Mac. This method populates
     * the `keycodes` map with key names corresponding to specific scan code
     * sequences.
     * <p>
     * The method defines mappings for:<br>
     * - ESC key and function keys.<br>
     * - Keys in each row of the principal keyboard, including letters, numbers,<br>
     *   and special characters.<br>
     * - Modifier key combinations (Shift, Control, Alt) for various keys.<br>
     * - Navigation keys such as Insert, Home, Page Up, Delete, End, and Page Down.<br>
     * - Arrow keys.<br>
     * - Numeric keypad keys, including Num Lock states.<br>
     * - The space bar.<br>
     * <p>
     * This configuration is intended to standardize input interpretation and
     * provide consistent key names for terminal-driven applications.
     * <p>
     * <b style="color:red">¡ IMPORTANT !</b> Some keystrokes are <i>captured</i>
     * by the Windows Terminal application OR other system apps utilities.
     * <p>
     * This mapping of keys is designed for a <b>ISO Spanish 105 keys Keyboard</b>!
     * You must <b style="color:red">adapt</b> this method to fit your current keyboard!
     */
    public void setupDefaultMappings() {
        // ESC keey and Function keys (common sequences)
        keycodes.put(new KeySequence(27), "ESC");
        keycodes.put(new KeySequence(0, 59), "F1");
        keycodes.put(new KeySequence(0, 60), "F2");
        keycodes.put(new KeySequence(0, 61), "F3");
        keycodes.put(new KeySequence(0, 62), "F4");
        keycodes.put(new KeySequence(0, 63), "F5");
        keycodes.put(new KeySequence(0, 64), "F6");
        keycodes.put(new KeySequence(0, 65), "F7");
        keycodes.put(new KeySequence(0, 66), "F8");
        keycodes.put(new KeySequence(0, 67), "F9");
        keycodes.put(new KeySequence(0, 68), "F10");
        keycodes.put(new KeySequence(0, 69), "F11"); //Captured by Terminal Windows
        keycodes.put(new KeySequence(224, 134), "F12");
        // With Shift key modifier pressed
        keycodes.put(new KeySequence(0, 84), "Shift + F1");
        keycodes.put(new KeySequence(0, 85), "Shift + F2");
        keycodes.put(new KeySequence(0, 86), "Shift + F3");
        keycodes.put(new KeySequence(0, 87), "Shift + F4");
        keycodes.put(new KeySequence(0, 88), "Shift + F5");
        keycodes.put(new KeySequence(0, 89), "Shift + F6");
        keycodes.put(new KeySequence(0, 90), "Shift + F7");
        keycodes.put(new KeySequence(0, 91), "Shift + F8");
        keycodes.put(new KeySequence(0, 92), "Shift + F9");
        keycodes.put(new KeySequence(0, 93), "Shift + F10");
        keycodes.put(new KeySequence(224, 135), "Shift + F11");
        keycodes.put(new KeySequence(224, 136), "Shift + F12");
        // With Control key modifier pressed
        keycodes.put(new KeySequence(0, 94), "Ctrl + F1");
        keycodes.put(new KeySequence(0, 95), "Ctrl + F2");
        keycodes.put(new KeySequence(0, 96), "Ctrl + F3");
        keycodes.put(new KeySequence(0, 97), "Ctrl + F4");
        keycodes.put(new KeySequence(0, 98), "Ctrl + F5");
        keycodes.put(new KeySequence(0, 99), "Ctrl + F6");
        keycodes.put(new KeySequence(0, 100), "Ctrl + F7");
        keycodes.put(new KeySequence(0, 101), "Ctrl + F8");
        keycodes.put(new KeySequence(0, 102), "Ctrl + F9");
        keycodes.put(new KeySequence(0, 103), "Ctrl + F10");
        keycodes.put(new KeySequence(224, 137), "Ctrl + F11");
        keycodes.put(new KeySequence(224, 138), "Ctrl + F12");
        // With Alt  key modifier pressed
        //  The Alt + F4 is captured by the hotkey standard Windows function 'Close Application'.
        // The combination Alt + F9, again, captured by the Nvidia Application.
        // The combination Alt + F10 is ignored (!)
        keycodes.put(new KeySequence(0, 104), "Alt + F1");
        keycodes.put(new KeySequence(0, 105), "Alt + F2");
        keycodes.put(new KeySequence(0, 106), "Alt + F3");
        keycodes.put(new KeySequence(0, 107), "Alt + F4");
        keycodes.put(new KeySequence(0, 108), "Alt + F5");
        keycodes.put(new KeySequence(0, 109), "Alt + F6");
        keycodes.put(new KeySequence(0, 110), "Alt + F7");
        keycodes.put(new KeySequence(0, 111), "Alt + F8");
        keycodes.put(new KeySequence(0, 112), "Alt + F9");
        keycodes.put(new KeySequence(0, 113), "Alt + F10");
        keycodes.put(new KeySequence(224, 139), "Alt + F11");
        keycodes.put(new KeySequence(224, 140), "Alt + F12");

        // First row of principal keyboard
        keycodes.put(new KeySequence(194, 186), "º");
        keycodes.put(new KeySequence(49), "1");
        keycodes.put(new KeySequence(50), "2");
        keycodes.put(new KeySequence(51), "3");
        keycodes.put(new KeySequence(52), "4");
        keycodes.put(new KeySequence(53), "5");
        keycodes.put(new KeySequence(54), "6");
        keycodes.put(new KeySequence(55), "7");
        keycodes.put(new KeySequence(56), "8");
        keycodes.put(new KeySequence(57), "9");
        keycodes.put(new KeySequence(48), "0");
        keycodes.put(new KeySequence(39), "'");
        keycodes.put(new KeySequence(194, 161), "¡");
        keycodes.put(new KeySequence(8), "(Backspace)");
        // With Shift key modifier pressed
        keycodes.put(new KeySequence(194, 170), "Shift + º ('ª')");
        keycodes.put(new KeySequence(194,183), "Shift + 3 ('·')");
        keycodes.put(new KeySequence(194, 191), "Shift + ! ('¿')");
        // With Control key modifier pressed
        keycodes.put(new KeySequence(0,3), "Ctrl + 2");
        // With Alt + Control key modifier pressed
        keycodes.put(new KeySequence(0,129), "Alt + Ctrl + 0");
        keycodes.put(new KeySequence(0,130), "Alt + Ctrl + '");
        keycodes.put(new KeySequence(0,131), "Alt + Ctrl + ¡");

        // Second row of principal keyboard
        keycodes.put(new KeySequence(9), "(Horizontal Tab)");
        keycodes.put(new KeySequence(113), "q");
        keycodes.put(new KeySequence(119), "w");
        keycodes.put(new KeySequence(101), "e");
        keycodes.put(new KeySequence(114), "r");
        keycodes.put(new KeySequence(116), "t");
        keycodes.put(new KeySequence(121), "y");
        keycodes.put(new KeySequence(117), "u");
        keycodes.put(new KeySequence(105), "i");
        keycodes.put(new KeySequence(111), "o");
        keycodes.put(new KeySequence(112), "p");
        keycodes.put(new KeySequence(96), "`"); //Dead key
        keycodes.put(new KeySequence(43), "+");
        keycodes.put(new KeySequence(13), "(Carriage Return)");
        // With Alt  key modifier pressed
        keycodes.put(new KeySequence(0, 26), "Alt + `");
        // With Alt + Control key modifier pressed
        keycodes.put(new KeySequence(0, 16), "Alt + Ctrl + q");
        keycodes.put(new KeySequence(0, 17), "Alt + Ctrl + w");
        keycodes.put(new KeySequence(226, 130, 172), "Alt + Ctrl + e ('¬')");
        keycodes.put(new KeySequence(0, 19), "Alt + Ctrl + r");
        keycodes.put(new KeySequence(0, 20), "Alt + Ctrl + t");
        keycodes.put(new KeySequence(0, 21), "Alt + Ctrl + y");
        keycodes.put(new KeySequence(0, 22), "Alt + Ctrl + u");
        keycodes.put(new KeySequence(0, 23), "Alt + Ctrl + i");
        keycodes.put(new KeySequence(0, 24), "Alt + Ctrl + 0");
        keycodes.put(new KeySequence(0, 25), "Alt + Ctrl + p");

        // Third row of principal keyboard
        keycodes.put(new KeySequence(97), "a");
        keycodes.put(new KeySequence(115), "s");
        keycodes.put(new KeySequence(100), "d");
        keycodes.put(new KeySequence(102), "f");
        keycodes.put(new KeySequence(103), "g");
        keycodes.put(new KeySequence(104), "h");
        keycodes.put(new KeySequence(106), "j");
        keycodes.put(new KeySequence(107), "k");
        keycodes.put(new KeySequence(108), "l");
        keycodes.put(new KeySequence(195, 177), "ñ");
        keycodes.put(new KeySequence(194, 180), "´");
        keycodes.put(new KeySequence(195, 167), "ç");
        // With Alt  key modifier pressed
        keycodes.put(new KeySequence(0, 40), "Alt + ´");
        // With Alt + Control key modifier pressed
        keycodes.put(new KeySequence(0, 30), "Alt + Ctrl + a");
        keycodes.put(new KeySequence(0, 31), "Alt + Ctrl + s");
        keycodes.put(new KeySequence(0, 32), "Alt + Ctrl + d");
        keycodes.put(new KeySequence(0, 33), "Alt + Ctrl + f");
        keycodes.put(new KeySequence(0, 34), "Alt + Ctrl + g");
        keycodes.put(new KeySequence(0, 35), "Alt + Ctrl + h");
        keycodes.put(new KeySequence(0, 36), "Alt + Ctrl + j");
        keycodes.put(new KeySequence(0, 37), "Alt + Ctrl + k"); // In my computer stars the Kindle application (!)
        keycodes.put(new KeySequence(0, 38), "Alt + Ctrl + l");
        keycodes.put(new KeySequence(0, 39), "Alt + Ctrl + ñ");

        // Fourth row of principal keyboard
        keycodes.put(new KeySequence(60), "<");
        keycodes.put(new KeySequence(122), "z");
        keycodes.put(new KeySequence(120), "x");
        keycodes.put(new KeySequence(99), "c");
        keycodes.put(new KeySequence(118), "v");
        keycodes.put(new KeySequence(98), "b");
        keycodes.put(new KeySequence(110), "n");
        keycodes.put(new KeySequence(109), "m");
        keycodes.put(new KeySequence(44), ",");
        keycodes.put(new KeySequence(46), ".");
        keycodes.put(new KeySequence(45), "-");
        // With Shift key modifier pressed
        keycodes.put(new KeySequence(195, 145), "Shift + ñ ('Ñ')");
        keycodes.put(new KeySequence(194, 168), "Shift + ´ ('¨')"); //Dead key
        keycodes.put(new KeySequence(195, 135), "Shift + ç ('Ç')");
        // With Alt + Control key modifier pressed
        keycodes.put(new KeySequence(0, 44), "Alt + Ctrl + z");
        keycodes.put(new KeySequence(0, 45), "Alt + Ctrl + x");
        keycodes.put(new KeySequence(0, 46), "Alt + Ctrl + c");
        keycodes.put(new KeySequence(0, 47), "Alt + Ctrl + v");
        keycodes.put(new KeySequence(0, 48), "Alt + Ctrl + b");
        keycodes.put(new KeySequence(0, 49), "Alt + Ctrl + n");
        keycodes.put(new KeySequence(0, 50), "Alt + Ctrl + m");
        keycodes.put(new KeySequence(0, 51), "Alt + Ctrl + ,"); // Captured by Windows Terminal
        keycodes.put(new KeySequence(0, 52), "Alt + Ctrl + .");
        keycodes.put(new KeySequence(0, 53), "Alt + Ctrl + -");

        // The space bar
        keycodes.put(new KeySequence(32), "(Space)");

        // Navigation keys
        keycodes.put(new KeySequence(224, 82), "Insert");
        keycodes.put(new KeySequence(224, 71), "Home");
        keycodes.put(new KeySequence(224, 73), "Pg Up");
        keycodes.put(new KeySequence(224, 83), "Delete");
        keycodes.put(new KeySequence(224, 79), "End");
        keycodes.put(new KeySequence(224, 81), "Pg Down");
        // With Control key modifier pressed
        keycodes.put(new KeySequence(224, 146), "Ctrl + Insert");
        keycodes.put(new KeySequence(224, 119), "Ctrl + Home");
        keycodes.put(new KeySequence(224, 147), "Ctrl + Delete");
        keycodes.put(new KeySequence(224, 117), "Ctrl + End");
        keycodes.put(new KeySequence(224, 118), "Ctrl + Pg Down");
        // With Alt  key modifier pressed
        keycodes.put(new KeySequence(0, 162), "Alt + Insert");
        keycodes.put(new KeySequence(0, 151), "Alt + Home");
        keycodes.put(new KeySequence(0, 153), "Alt + Pg Up");
        keycodes.put(new KeySequence(0, 163), "Alt + Delete");
        keycodes.put(new KeySequence(0, 159), "Alt + End");
        keycodes.put(new KeySequence(0, 161), "Alt + Pg Down");
        // The Alt + Ctrl key modifiers produce the seven keys above
        // Ctrl + Alt + Del captured by Windows

        // Arrow keys
        keycodes.put(new KeySequence(224, 75), "Left arrow");
        keycodes.put(new KeySequence(224, 72), "Up arrow");
        keycodes.put(new KeySequence(224, 77), "Right arrow");
        keycodes.put(new KeySequence(224, 80), "Down arrow");
        // With Control key modifier pressed
        keycodes.put(new KeySequence(224, 115), "Ctrl + Left arrow");
        keycodes.put(new KeySequence(224, 141), "Ctrl + Up arrow");
        keycodes.put(new KeySequence(224, 116), "Ctrl + Right arrow");
        keycodes.put(new KeySequence(224, 145), "Ctrl + Down arrow");
        // With Alt  key modifier pressed
        keycodes.put(new KeySequence(0, 155), "Alt + Left arrow");
        keycodes.put(new KeySequence(0, 152), "Alt + Up arrow");
        keycodes.put(new KeySequence(0, 157), "Alt + Right arrow");
        keycodes.put(new KeySequence(0, 160), "Alt + Down arrow");
        // With Alt + Control key modifier pressed the keys are the same as four above


        // Numeric keyboard (Num clear enabled)
        keycodes.put(new KeySequence(47), "(num clear enabled) /");
        keycodes.put(new KeySequence(42), "(num clear enabled) *");
        keycodes.put(new KeySequence(45), "(num clear enabled) -");
        keycodes.put(new KeySequence(55), "(num clear enabled) 7");
        keycodes.put(new KeySequence(56), "(num clear enabled) 8");
        keycodes.put(new KeySequence(57), "(num clear enabled) 9");
        keycodes.put(new KeySequence(43), "(num clear enabled) +");
        keycodes.put(new KeySequence(52), "(num clear enabled) 4");
        keycodes.put(new KeySequence(53), "(num clear enabled) 5");
        keycodes.put(new KeySequence(54), "(num clear enabled) 6");
        keycodes.put(new KeySequence(49), "(num clear enabled) 1");
        keycodes.put(new KeySequence(50), "(num clear enabled) 2");
        keycodes.put(new KeySequence(51), "(num clear enabled) 3");
        keycodes.put(new KeySequence(48), "(num clear enabled) 0");
        keycodes.put(new KeySequence(46), "(num clear enabled) .");
        // Numeric keyboard (Num clear disabled)
        keycodes.put(new KeySequence(0, 71), "(num clear disabled) 7");
        keycodes.put(new KeySequence(0, 72), "(num clear disabled) 8");
        keycodes.put(new KeySequence(0, 73), "(num clear disabled) 9");
        keycodes.put(new KeySequence(0, 75), "(num clear disabled) 4");
        keycodes.put(new KeySequence(0, 77), "(num clear disabled) 6");
        keycodes.put(new KeySequence(0, 79), "(num clear disabled) 1");
        keycodes.put(new KeySequence(0, 80), "(num clear disabled) 2");
        keycodes.put(new KeySequence(0, 81), "(num clear disabled) 3");
        keycodes.put(new KeySequence(0, 82), "(num clear disabled) 0");
        keycodes.put(new KeySequence(0, 83), "(num clear disabled) .");
        // With Control key modifier pressed
        // Note: Control with '+' and Control with '-' are captured by Windows Terminal
        // Note 2:
        // This combinations of keystrokes don't generate a scan key sequence:
        // - Control + '*'
        // - Control + '5'
        // - Control + '0'
        keycodes.put(new KeySequence(0, 149), "(num clear enabled) Ctrl + /");
        keycodes.put(new KeySequence(0, 119), "(num clear enabled) Ctrl + 7");
        keycodes.put(new KeySequence(0, 141), "(num clear enabled) Ctrl + 8");
        keycodes.put(new KeySequence(0, 132), "(num clear enabled) Ctrl + 9");
        keycodes.put(new KeySequence(0, 115), "(num clear enabled) Ctrl + 4");
        keycodes.put(new KeySequence(0, 116), "(num clear enabled) Ctrl + 6");
        keycodes.put(new KeySequence(0, 117), "(num clear enabled) Ctrl + 1");
        keycodes.put(new KeySequence(0, 145), "(num clear enabled) Ctrl + 2");
        keycodes.put(new KeySequence(0, 118), "(num clear enabled) Ctrl + 3");
        keycodes.put(new KeySequence(0, 147), "(num clear enabled) Ctrl + .");

    }

    /**
     * Finds the name of a key based on an array of input codes.
     *
     * @param codes The sequence of integers received from the terminal.
     * @return The name of the key if found, or a descriptive string for
     * individual characters or unknown sequences.
     */
    public String getKeyName(int[] codes) {
        if (codes == null || codes.length == 0) {
            return "UNKNOWN";
        }

        KeySequence sequence = new KeySequence(codes);
        String keyName = keycodes.get(sequence);

        if (keyName != null) {
            return keyName;
        }

        // Fallback to ASCII character description for single codes
        if (codes.length == 1) {
            return asciiSet.getChar(codes[0]).getDescription();
        }

        return "UNKNOWN_SEQUENCE: " + Arrays.toString(codes);
    }

    /**
     * Returns the ASCII set helper used by this class.
     *
     * @return The extended ASCII character set.
     */
    public ASCIICharacterSetExtended getAsciiSet() {
        return asciiSet;
    }

    /**
     * Retrieves the current key mappings for specific key sequences.
     *
     * @return A map where the key is a {@link KeySequence} representing
     *         scan codes or ANSI escape sequences, and the value is the
     *         name or description of the corresponding key.
     */
    public Map<KeySequence, String> getKeycodes() {
        return keycodes;
    }

    /**
     * Retrieves the description associated with the current instance of KeyCodes.
     *
     * @return The description that provides details or metadata about this instance.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Generates a string representation of the current key mappings and their descriptions.
     * The representation includes the description of the instance, followed by the key sequences
     * and their associated values.
     *
     * @return A string containing a formatted view of the key mappings,
     *         including the description and key-value pairs.
     */
    public String getMapping(){
        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append("KeyCodes{");
        sb.append(ls).append("description=").append(this.getDescription()).append(ls);
        for (Map.Entry<KeySequence, String> entry : keycodes.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ").append(ls);
        }
        if (!keycodes.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
            sb.append("}");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "KeyCodes{" +
                "keycodes=" + keycodes +
                '}';
    }
}
