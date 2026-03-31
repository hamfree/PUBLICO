package es.nom.juanfranciscoruiz.ansiterm.model;

import java.util.Objects;

/**
 * Represents a character in the ASCII table along with its associated metadata.
 * This class provides methods for accessing and modifying the ASCII code,
 * character value, and its description.
 */
public class ASCIIChar {
    /**
     * Represents the ASCII code associated with the character.
     * This field stores the numeric value of the character in the ASCII table.
     * It can range from 0 to 255, where each value corresponds to a unique character.
     */
    private short code;
    /**
     * Represents a single character associated with this object.
     * This field holds the character value as defined in the Unicode
     * or ASCII table. It can be used to identify or manipulate the
     * specific character tied to the object's context.
     */
    private char character;
    /**
     * Represents a description associated with the ASCII character.
     * This field provides additional context or metadata, serving as a textual
     * explanation or annotation related to the character.
     */
    private String description;

    /**
     * Default constructor for the ASCIIChar class.
     * Initializes an ASCII character instance with default values for
     * the ASCII code, character, and description fields.
     */
    public ASCIIChar() {

    }

    /**
     * Retrieves the ASCII code associated with this character.
     *
     * @return the ASCII code as a short value.
     */
    public short getCode() {
        return code;
    }

    /**
     * Sets the ASCII code for this character.
     * The code must be a valid short value representing the numeric
     * value of the character in the ASCII table.
     *
     * @param code the ASCII code to assign to this object
     */
    public void setCode(short code) {
        this.code = code;
    }

    /**
     * Retrieves the character associated with this instance.
     *
     * @return the character as a char value.
     */
    public char getCharacter() {
        return character;
    }

    /**
     * Sets the character associated with this instance.
     * The character should correspond to a valid ASCII character.
     *
     * @param character the character to assign to this instance
     */
    public void setCharacter(char character) {
        this.character = character;
    }

    /**
     * Retrieves the description associated with this ASCII character instance.
     *
     * @return the description as a string value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for this ASCII character instance.
     * The description provides additional information about the character.
     *
     * @param description the description to assign to this instance
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ASCIIChar asciiChar = (ASCIIChar) o;
        return code == asciiChar.code && character == asciiChar.character && Objects.equals(description, asciiChar.description);
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + character;
        result = 31 * result + Objects.hashCode(description);
        return result;
    }

    @Override
    public String toString() {
        return "ASCIIChar{" +
                "code=" + code +
                ", character=" + character +
                ", description='" + description + '\'' +
                '}';
    }
}
