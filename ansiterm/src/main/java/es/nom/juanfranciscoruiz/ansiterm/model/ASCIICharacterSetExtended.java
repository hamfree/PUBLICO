package es.nom.juanfranciscoruiz.ansiterm.model;

import java.util.Objects;

/**
 * Represents the extended ASCII character set (0-255), which includes the standard
 * ASCII characters (0-127) and additional characters (128-255). The extended range
 * may vary depending on the encoding or code page.
 * <p>
 * This class provides functionality for initializing and retrieving metadata
 * about each character in the extended ASCII set, including its code, character
 * representation, and description.
 */
public class ASCIICharacterSetExtended {
    private final ASCIIChar[] characters;

    /**
     * Default constructor for the ASCIICharacterSetExtended class.
     * Initializes the extended ASCII character set (0-255) by populating
     * an array of ASCIIChar objects. Each ASCIIChar object represents a
     * single character and includes its ASCII code, character representation,
     * and a corresponding description.
     * <p>
     * The initialization process is handled by invoking the private initialize()
     * method, which iterates through the ASCII character range and assigns
     * metadata for each character.
     */
    public ASCIICharacterSetExtended() {
        this.characters = new ASCIIChar[256];
        initialize();
    }

    /**
     * Initializes the extended ASCII character set by populating an internal array
     * with metadata for each character in the ASCII range (0-255).
     * <p>
     * This method creates an instance of {@code ASCIIChar} for each possible
     * ASCII value, assigns the corresponding code, character, and description,
     * and stores it in the {@code characters} array. The description for each character
     * is obtained using the {@code getExtendedASCIIDescription} method.
     * <p>
     * Each {@code ASCIIChar} object represents the code, character, and
     * a textual description for the respective ASCII value.
     */
    private void initialize() {
        for (int i = 0; i < 256; i++) {
            ASCIIChar asciiChar = new ASCIIChar();
            asciiChar.setCode((short) i);
            asciiChar.setCharacter((char) i);
            asciiChar.setDescription(getExtendedASCIIDescription(i));
            characters[i] = asciiChar;
        }
    }

    /**
     * Returns the ASCIIChar associated with the given code.
     *
     * @param code The extended ASCII code (0-255).
     * @return The ASCIIChar object, or null if the code is out of range.
     */
    public ASCIIChar getChar(int code) {
        if (code < 0 || code > 255) {
            return null;
        }
        return characters[code];
    }

    /**
     * Returns a copy of all characters in the extended set.
     *
     * @return An array of ASCIIChar objects.
     */
    public ASCIIChar[] getAllChars() {
        return characters.clone();
    }

    private String getExtendedASCIIDescription(int code) {
        if (code < 128) {
            return Objects.requireNonNull(new ASCIICharacterSet().getChar(code)).getDescription();
        }
        // Basic description for extended range (128-255)
        // These vary depending on the code page (e.g., CP437, ISO-8859-1)
        return "Extended ASCII Character: " + (char) code;
    }

}
