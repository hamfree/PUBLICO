package es.nom.juanfranciscoruiz.ansiterm.model;

import java.util.Arrays;

/**
 * Represents an immutable sequence of integer scan codes or ANSI escape codes.
 * This class serves as a reliable key for use in HashMaps, as it properly 
 * implements equals and hashCode based on the sequence contents.
 */
public final class KeySequence {

    private final int[] codes;

    /**
     * Constructs a new KeySequence with the provided integer codes.
     * 
     * @param codes A variable number of integer codes representing the sequence.
     */
    public KeySequence(int... codes) {
        this.codes = codes != null ? codes.clone() : new int[0];
    }

    /**
     * Retrieves a copy of the internal integer codes.
     * 
     * @return An array of integers representing the key codes.
     */
    public int[] getCodes() {
        return codes.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        KeySequence that = (KeySequence) obj;
        return Arrays.equals(this.codes, that.codes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.codes);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.codes);
    }
}