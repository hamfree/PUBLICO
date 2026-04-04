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
    private final Map<KeySequence, String> mapKeycodes;
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
     * A boolean flag indicating whether the current instance of the KeyCodes class
     * has been initialized. This variable determines if the necessary setup, such
     * as key mappings, has been completed.
     * <p>
     * When `true`, the instance is considered initialized and ready for use.
     * When `false`, operations relying on proper initialization of the instance
     * may not function correctly.
     */
    private boolean isInitialized;

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
        this.mapKeycodes = new HashMap<>();
        this.asciiSet = new ASCIICharacterSetExtended();
        this.isInitialized = false;
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
        this.mapKeycodes = new HashMap<>();
        this.asciiSet = new ASCIICharacterSetExtended();
        this.isInitialized = false;
    }

    /**
     * Constructor that allows providing a custom map of key codes.
     *
     * @param keycodes A map where the key is a KeySequence representing
     *                 the scan codes and the value is the key name.
     * @param description A string providing metadata or details about this
     */
    public KeyCodes(Map<KeySequence, String> keycodes, String description) {
        this.mapKeycodes = keycodes;
        this.description = (description != null) ? description : "Unknown description";
        this.asciiSet = new ASCIICharacterSetExtended();
        this.isInitialized = false;
    }


    /**
     * Initializes the key mappings if they are currently null or empty.
     * This method ensures that default key mappings are established
     * by invoking the `setupDefaultMappings` method, which populates
     * the key mappings with standard configurations.
     *
     * @return {@code true} if the key mappings were successfully initialized
     *         (i.e., were null or empty before initialization), {@code false} otherwise.
     */
    public boolean initialize(){
        if (this.mapKeycodes != null && this.mapKeycodes.isEmpty()){
            setupDefaultMappings();
            return true;
        }
        return false;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Updates the internal mapping of key sequences to their corresponding key names.
     * The provided map is validated to ensure it is neither null nor empty, and its
     * entries are added to the internal key mapping. This operation marks the instance
     * as initialized upon successful execution.
     *
     * @param mapKeycodes A map where the keys are {@link KeySequence} objects representing
     *                    sequences of key codes, and the values are strings denoting
     *                    the corresponding key names.
     * @throws IllegalArgumentException If the provided map is null or empty.
     */
    public void setMapKeycodes(Map<KeySequence, String> mapKeycodes) throws IllegalArgumentException {
        if (mapKeycodes == null || mapKeycodes.isEmpty()) {
            throw new IllegalArgumentException("The map of key codes cannot be null or empty.");
        }
        this.mapKeycodes.putAll(mapKeycodes);
        this.isInitialized  = true;
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
        // ESC key and Function keys (common sequences)
        mapKeycodes.put(new KeySequence(27), "ESC");
        mapKeycodes.put(new KeySequence(0, 59), "F1");
        mapKeycodes.put(new KeySequence(0, 60), "F2");
        mapKeycodes.put(new KeySequence(0, 61), "F3");
        mapKeycodes.put(new KeySequence(0, 62), "F4");
        mapKeycodes.put(new KeySequence(0, 63), "F5");
        mapKeycodes.put(new KeySequence(0, 64), "F6");
        mapKeycodes.put(new KeySequence(0, 65), "F7");
        mapKeycodes.put(new KeySequence(0, 66), "F8");
        mapKeycodes.put(new KeySequence(0, 67), "F9");
        mapKeycodes.put(new KeySequence(0, 68), "F10");
        mapKeycodes.put(new KeySequence(0, 69), "F11"); //Captured by Terminal Windows
        mapKeycodes.put(new KeySequence(224, 134), "F12");
        // With Shift key modifier pressed
        mapKeycodes.put(new KeySequence(0, 84), "Shift + F1");
        mapKeycodes.put(new KeySequence(0, 85), "Shift + F2");
        mapKeycodes.put(new KeySequence(0, 86), "Shift + F3");
        mapKeycodes.put(new KeySequence(0, 87), "Shift + F4");
        mapKeycodes.put(new KeySequence(0, 88), "Shift + F5");
        mapKeycodes.put(new KeySequence(0, 89), "Shift + F6");
        mapKeycodes.put(new KeySequence(0, 90), "Shift + F7");
        mapKeycodes.put(new KeySequence(0, 91), "Shift + F8");
        mapKeycodes.put(new KeySequence(0, 92), "Shift + F9");
        mapKeycodes.put(new KeySequence(0, 93), "Shift + F10");
        mapKeycodes.put(new KeySequence(224, 135), "Shift + F11");
        mapKeycodes.put(new KeySequence(224, 136), "Shift + F12");
        // With Control key modifier pressed
        mapKeycodes.put(new KeySequence(0, 94), "Ctrl + F1");
        mapKeycodes.put(new KeySequence(0, 95), "Ctrl + F2");
        mapKeycodes.put(new KeySequence(0, 96), "Ctrl + F3");
        mapKeycodes.put(new KeySequence(0, 97), "Ctrl + F4");
        mapKeycodes.put(new KeySequence(0, 98), "Ctrl + F5");
        mapKeycodes.put(new KeySequence(0, 99), "Ctrl + F6");
        mapKeycodes.put(new KeySequence(0, 100), "Ctrl + F7");
        mapKeycodes.put(new KeySequence(0, 101), "Ctrl + F8");
        mapKeycodes.put(new KeySequence(0, 102), "Ctrl + F9");
        mapKeycodes.put(new KeySequence(0, 103), "Ctrl + F10");
        mapKeycodes.put(new KeySequence(224, 137), "Ctrl + F11");
        mapKeycodes.put(new KeySequence(224, 138), "Ctrl + F12");
        // With Alt  key modifier pressed
        //  The Alt + F4 is captured by the hotkey standard Windows function 'Close Application'.
        // The combination Alt + F9, again, captured by the Nvidia Application.
        // The combination Alt + F10 is ignored (!)
        mapKeycodes.put(new KeySequence(0, 104), "Alt + F1");
        mapKeycodes.put(new KeySequence(0, 105), "Alt + F2");
        mapKeycodes.put(new KeySequence(0, 106), "Alt + F3");
        mapKeycodes.put(new KeySequence(0, 107), "Alt + F4");
        mapKeycodes.put(new KeySequence(0, 108), "Alt + F5");
        mapKeycodes.put(new KeySequence(0, 109), "Alt + F6");
        mapKeycodes.put(new KeySequence(0, 110), "Alt + F7");
        mapKeycodes.put(new KeySequence(0, 111), "Alt + F8");
        mapKeycodes.put(new KeySequence(0, 112), "Alt + F9");
        mapKeycodes.put(new KeySequence(0, 113), "Alt + F10");
        mapKeycodes.put(new KeySequence(224, 139), "Alt + F11");
        mapKeycodes.put(new KeySequence(224, 140), "Alt + F12");

        // First row of principal keyboard
        mapKeycodes.put(new KeySequence(194, 186), "º");
        mapKeycodes.put(new KeySequence(49), "1");
        mapKeycodes.put(new KeySequence(50), "2");
        mapKeycodes.put(new KeySequence(51), "3");
        mapKeycodes.put(new KeySequence(52), "4");
        mapKeycodes.put(new KeySequence(53), "5");
        mapKeycodes.put(new KeySequence(54), "6");
        mapKeycodes.put(new KeySequence(55), "7");
        mapKeycodes.put(new KeySequence(56), "8");
        mapKeycodes.put(new KeySequence(57), "9");
        mapKeycodes.put(new KeySequence(48), "0");
        mapKeycodes.put(new KeySequence(39), "'");
        mapKeycodes.put(new KeySequence(194, 161), "¡");
        mapKeycodes.put(new KeySequence(8), "(Backspace)");
        // With Shift key modifier pressed
        mapKeycodes.put(new KeySequence(194, 170), "Shift + º ('ª')");
        mapKeycodes.put(new KeySequence(194,183), "Shift + 3 ('·')");
        mapKeycodes.put(new KeySequence(194, 191), "Shift + ! ('¿')");
        // With Control key modifier pressed
        mapKeycodes.put(new KeySequence(0,3), "Ctrl + 2");
        // With Alt + Control key modifier pressed
        mapKeycodes.put(new KeySequence(0,129), "Alt + Ctrl + 0");
        mapKeycodes.put(new KeySequence(0,130), "Alt + Ctrl + '");
        mapKeycodes.put(new KeySequence(0,131), "Alt + Ctrl + ¡");

        // Second row of principal keyboard
        mapKeycodes.put(new KeySequence(9), "(Horizontal Tab)");
        mapKeycodes.put(new KeySequence(113), "q");
        mapKeycodes.put(new KeySequence(119), "w");
        mapKeycodes.put(new KeySequence(101), "e");
        mapKeycodes.put(new KeySequence(114), "r");
        mapKeycodes.put(new KeySequence(116), "t");
        mapKeycodes.put(new KeySequence(121), "y");
        mapKeycodes.put(new KeySequence(117), "u");
        mapKeycodes.put(new KeySequence(105), "i");
        mapKeycodes.put(new KeySequence(111), "o");
        mapKeycodes.put(new KeySequence(112), "p");
        mapKeycodes.put(new KeySequence(96), "`"); //Dead key
        mapKeycodes.put(new KeySequence(43), "+");
        mapKeycodes.put(new KeySequence(13), "(Carriage Return)");
        // With Alt  key modifier pressed
        mapKeycodes.put(new KeySequence(0, 26), "Alt + `");
        // With Alt + Control key modifier pressed
        mapKeycodes.put(new KeySequence(0, 16), "Alt + Ctrl + q");
        mapKeycodes.put(new KeySequence(0, 17), "Alt + Ctrl + w");
        mapKeycodes.put(new KeySequence(226, 130, 172), "Alt + Ctrl + e ('¬')");
        mapKeycodes.put(new KeySequence(0, 19), "Alt + Ctrl + r");
        mapKeycodes.put(new KeySequence(0, 20), "Alt + Ctrl + t");
        mapKeycodes.put(new KeySequence(0, 21), "Alt + Ctrl + y");
        mapKeycodes.put(new KeySequence(0, 22), "Alt + Ctrl + u");
        mapKeycodes.put(new KeySequence(0, 23), "Alt + Ctrl + i");
        mapKeycodes.put(new KeySequence(0, 24), "Alt + Ctrl + 0");
        mapKeycodes.put(new KeySequence(0, 25), "Alt + Ctrl + p");

        // Third row of principal keyboard
        mapKeycodes.put(new KeySequence(97), "a");
        mapKeycodes.put(new KeySequence(115), "s");
        mapKeycodes.put(new KeySequence(100), "d");
        mapKeycodes.put(new KeySequence(102), "f");
        mapKeycodes.put(new KeySequence(103), "g");
        mapKeycodes.put(new KeySequence(104), "h");
        mapKeycodes.put(new KeySequence(106), "j");
        mapKeycodes.put(new KeySequence(107), "k");
        mapKeycodes.put(new KeySequence(108), "l");
        mapKeycodes.put(new KeySequence(195, 177), "ñ");
        mapKeycodes.put(new KeySequence(194, 180), "´");
        mapKeycodes.put(new KeySequence(195, 167), "ç");
        // With Alt  key modifier pressed
        mapKeycodes.put(new KeySequence(0, 40), "Alt + ´");
        // With Alt + Control key modifier pressed
        mapKeycodes.put(new KeySequence(0, 30), "Alt + Ctrl + a");
        mapKeycodes.put(new KeySequence(0, 31), "Alt + Ctrl + s");
        mapKeycodes.put(new KeySequence(0, 32), "Alt + Ctrl + d");
        mapKeycodes.put(new KeySequence(0, 33), "Alt + Ctrl + f");
        mapKeycodes.put(new KeySequence(0, 34), "Alt + Ctrl + g");
        mapKeycodes.put(new KeySequence(0, 35), "Alt + Ctrl + h");
        mapKeycodes.put(new KeySequence(0, 36), "Alt + Ctrl + j");
        mapKeycodes.put(new KeySequence(0, 37), "Alt + Ctrl + k"); // In my computer stars the Kindle application (!)
        mapKeycodes.put(new KeySequence(0, 38), "Alt + Ctrl + l");
        mapKeycodes.put(new KeySequence(0, 39), "Alt + Ctrl + ñ");

        // Fourth row of principal keyboard
        mapKeycodes.put(new KeySequence(60), "<");
        mapKeycodes.put(new KeySequence(122), "z");
        mapKeycodes.put(new KeySequence(120), "x");
        mapKeycodes.put(new KeySequence(99), "c");
        mapKeycodes.put(new KeySequence(118), "v");
        mapKeycodes.put(new KeySequence(98), "b");
        mapKeycodes.put(new KeySequence(110), "n");
        mapKeycodes.put(new KeySequence(109), "m");
        mapKeycodes.put(new KeySequence(44), ",");
        mapKeycodes.put(new KeySequence(46), ".");
        mapKeycodes.put(new KeySequence(45), "-");
        // With Shift key modifier pressed
        mapKeycodes.put(new KeySequence(195, 145), "Shift + ñ ('Ñ')");
        mapKeycodes.put(new KeySequence(194, 168), "Shift + ´ ('¨')"); //Dead key
        mapKeycodes.put(new KeySequence(195, 135), "Shift + ç ('Ç')");
        // With Alt + Control key modifier pressed
        mapKeycodes.put(new KeySequence(0, 44), "Alt + Ctrl + z");
        mapKeycodes.put(new KeySequence(0, 45), "Alt + Ctrl + x");
        mapKeycodes.put(new KeySequence(0, 46), "Alt + Ctrl + c");
        mapKeycodes.put(new KeySequence(0, 47), "Alt + Ctrl + v");
        mapKeycodes.put(new KeySequence(0, 48), "Alt + Ctrl + b");
        mapKeycodes.put(new KeySequence(0, 49), "Alt + Ctrl + n");
        mapKeycodes.put(new KeySequence(0, 50), "Alt + Ctrl + m");
        mapKeycodes.put(new KeySequence(0, 51), "Alt + Ctrl + ,"); // Captured by Windows Terminal
        mapKeycodes.put(new KeySequence(0, 52), "Alt + Ctrl + .");
        mapKeycodes.put(new KeySequence(0, 53), "Alt + Ctrl + -");

        // The space bar
        mapKeycodes.put(new KeySequence(32), "(Space)");

        // Navigation keys
        mapKeycodes.put(new KeySequence(224, 82), "Insert");
        mapKeycodes.put(new KeySequence(224, 71), "Home");
        mapKeycodes.put(new KeySequence(224, 73), "Pg Up");
        mapKeycodes.put(new KeySequence(224, 83), "Delete");
        mapKeycodes.put(new KeySequence(224, 79), "End");
        mapKeycodes.put(new KeySequence(224, 81), "Pg Down");
        // With Control key modifier pressed
        mapKeycodes.put(new KeySequence(224, 146), "Ctrl + Insert");
        mapKeycodes.put(new KeySequence(224, 119), "Ctrl + Home");
        mapKeycodes.put(new KeySequence(224, 147), "Ctrl + Delete");
        mapKeycodes.put(new KeySequence(224, 117), "Ctrl + End");
        mapKeycodes.put(new KeySequence(224, 118), "Ctrl + Pg Down");
        // With Alt  key modifier pressed
        mapKeycodes.put(new KeySequence(0, 162), "Alt + Insert");
        mapKeycodes.put(new KeySequence(0, 151), "Alt + Home");
        mapKeycodes.put(new KeySequence(0, 153), "Alt + Pg Up");
        mapKeycodes.put(new KeySequence(0, 163), "Alt + Delete");
        mapKeycodes.put(new KeySequence(0, 159), "Alt + End");
        mapKeycodes.put(new KeySequence(0, 161), "Alt + Pg Down");
        // The Alt + Ctrl key modifiers produce the seven keys above
        // Ctrl + Alt + Del captured by Windows

        // Arrow keys
        mapKeycodes.put(new KeySequence(224, 75), "Left arrow");
        mapKeycodes.put(new KeySequence(224, 72), "Up arrow");
        mapKeycodes.put(new KeySequence(224, 77), "Right arrow");
        mapKeycodes.put(new KeySequence(224, 80), "Down arrow");
        // With Control key modifier pressed
        mapKeycodes.put(new KeySequence(224, 115), "Ctrl + Left arrow");
        mapKeycodes.put(new KeySequence(224, 141), "Ctrl + Up arrow");
        mapKeycodes.put(new KeySequence(224, 116), "Ctrl + Right arrow");
        mapKeycodes.put(new KeySequence(224, 145), "Ctrl + Down arrow");
        // With Alt  key modifier pressed
        mapKeycodes.put(new KeySequence(0, 155), "Alt + Left arrow");
        mapKeycodes.put(new KeySequence(0, 152), "Alt + Up arrow");
        mapKeycodes.put(new KeySequence(0, 157), "Alt + Right arrow");
        mapKeycodes.put(new KeySequence(0, 160), "Alt + Down arrow");
        // With Alt + Control key modifier pressed the keys are the same as four above


        // Numeric keyboard (Num clear enabled)
        mapKeycodes.put(new KeySequence(47), "(num clear enabled) /");
        mapKeycodes.put(new KeySequence(42), "(num clear enabled) *");
        mapKeycodes.put(new KeySequence(45), "(num clear enabled) -");
        mapKeycodes.put(new KeySequence(55), "(num clear enabled) 7");
        mapKeycodes.put(new KeySequence(56), "(num clear enabled) 8");
        mapKeycodes.put(new KeySequence(57), "(num clear enabled) 9");
        mapKeycodes.put(new KeySequence(43), "(num clear enabled) +");
        mapKeycodes.put(new KeySequence(52), "(num clear enabled) 4");
        mapKeycodes.put(new KeySequence(53), "(num clear enabled) 5");
        mapKeycodes.put(new KeySequence(54), "(num clear enabled) 6");
        mapKeycodes.put(new KeySequence(49), "(num clear enabled) 1");
        mapKeycodes.put(new KeySequence(50), "(num clear enabled) 2");
        mapKeycodes.put(new KeySequence(51), "(num clear enabled) 3");
        mapKeycodes.put(new KeySequence(48), "(num clear enabled) 0");
        mapKeycodes.put(new KeySequence(46), "(num clear enabled) .");
        // Numeric keyboard (Num clear disabled)
        mapKeycodes.put(new KeySequence(0, 71), "(num clear disabled) 7");
        mapKeycodes.put(new KeySequence(0, 72), "(num clear disabled) 8");
        mapKeycodes.put(new KeySequence(0, 73), "(num clear disabled) 9");
        mapKeycodes.put(new KeySequence(0, 75), "(num clear disabled) 4");
        mapKeycodes.put(new KeySequence(0, 77), "(num clear disabled) 6");
        mapKeycodes.put(new KeySequence(0, 79), "(num clear disabled) 1");
        mapKeycodes.put(new KeySequence(0, 80), "(num clear disabled) 2");
        mapKeycodes.put(new KeySequence(0, 81), "(num clear disabled) 3");
        mapKeycodes.put(new KeySequence(0, 82), "(num clear disabled) 0");
        mapKeycodes.put(new KeySequence(0, 83), "(num clear disabled) .");
        // With Control key modifier pressed
        // Note: Control with '+' and Control with '-' are captured by Windows Terminal
        // Note 2:
        // This combinations of keystrokes don't generate a scan key sequence:
        // - Control + '*'
        // - Control + '5'
        // - Control + '0'
        mapKeycodes.put(new KeySequence(0, 149), "(num clear enabled) Ctrl + /");
        mapKeycodes.put(new KeySequence(0, 119), "(num clear enabled) Ctrl + 7");
        mapKeycodes.put(new KeySequence(0, 141), "(num clear enabled) Ctrl + 8");
        mapKeycodes.put(new KeySequence(0, 132), "(num clear enabled) Ctrl + 9");
        mapKeycodes.put(new KeySequence(0, 115), "(num clear enabled) Ctrl + 4");
        mapKeycodes.put(new KeySequence(0, 116), "(num clear enabled) Ctrl + 6");
        mapKeycodes.put(new KeySequence(0, 117), "(num clear enabled) Ctrl + 1");
        mapKeycodes.put(new KeySequence(0, 145), "(num clear enabled) Ctrl + 2");
        mapKeycodes.put(new KeySequence(0, 118), "(num clear enabled) Ctrl + 3");
        mapKeycodes.put(new KeySequence(0, 147), "(num clear enabled) Ctrl + .");
        this.isInitialized = true;
    }

    /**
     * Finds the name of a key based on an array of input codes.
     *
     * @param codes The sequence of integers received from the terminal.
     * @return The name of the key if found, or a descriptive string for
     * individual characters or unknown sequences.
     * @throws Exception If the instance is not initialized.
     */
    public String getKeyName(int[] codes) throws Exception {
        if (!isInitialized) {
            throw new Exception("KeyCodes is not initialized");
        }

        if (codes == null || codes.length == 0) {
            return "UNKNOWN";
        }

        KeySequence sequence = new KeySequence(codes);
        String keyName = mapKeycodes.get(sequence);

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
     * Retrieves a mapping of key sequences to their associated key names.
     * <p>
     * This method returns the internal map containing key sequences as keys
     * (represented by {@link KeySequence}) and their corresponding string
     * representations as values. If the class has not been initialized, this
     * method will return {@code null}.
     *
     * @return A map where keys are {@link KeySequence} objects representing
     *         scan code sequences, and values are strings representing the
     *         names of the corresponding keys, or {@code null} if the instance
     *         is not initialized.
     *
     * @throws Exception If the instance is not initialized.
     */
    public Map<KeySequence, String> getMapKeycodes() throws Exception {
        if (!isInitialized) {
            throw new Exception("KeyCodes is not initialized");
        }
        return mapKeycodes;
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
    public String getMapping() {
        if (!isInitialized){
            return "KeyCodes is not initialized";
        }
        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append("KeyCodes{");
        sb.append(ls).append("description=").append(this.getDescription()).append(ls);
        for (Map.Entry<KeySequence, String> entry : mapKeycodes.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ").append(ls);
        }
        if (!mapKeycodes.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
            sb.append("}");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "KeyCodes{" +
                "keycodes=" + mapKeycodes +
                '}';
    }
}
