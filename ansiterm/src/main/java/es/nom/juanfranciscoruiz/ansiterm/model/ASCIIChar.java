package es.nom.juanfranciscoruiz.ansiterm.model;

import java.util.Objects;

/**
 * Represents a character in the ASCII table along with its associated metadata.
 * This class provides methods for accessing and modifying the ASCII code,
 * character value, and its description.
 */
public class ASCIIChar {
    private short code;
    private char character;
    private String description;

    public ASCIIChar() {

    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public String getDescription() {
        return description;
    }

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
