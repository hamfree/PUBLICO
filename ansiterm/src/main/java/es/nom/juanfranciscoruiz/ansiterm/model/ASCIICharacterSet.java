package es.nom.juanfranciscoruiz.ansiterm.model;

/**
 * Represents the standard 7-bit ASCII character set (0-127).
 * This class initializes and provides access to ASCIIChar objects for each code.
 */
public class ASCIICharacterSet {
    private final ASCIIChar[] characters;

    public ASCIICharacterSet() {
        this.characters = new ASCIIChar[128];
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < 128; i++) {
            ASCIIChar asciiChar = new ASCIIChar();
            asciiChar.setCode((short) i);
            asciiChar.setCharacter((char) i);
            asciiChar.setDescription(getASCIIDescription(i));
            characters[i] = asciiChar;
        }
    }

    /**
     * Returns the ASCIIChar associated with the given code.
     *
     * @param code The ASCII code (0-127).
     * @return The ASCIIChar object, or null if the code is out of range.
     */
    public ASCIIChar getChar(int code) {
        if (code < 0 || code > 127) {
            return null;
        }
        return characters[code];
    }

    /**
     * Returns a copy of all characters in the set.
     *
     * @return An array of ASCIIChar objects.
     */
    public ASCIIChar[] getAllChars() {
        return characters.clone();
    }

    private String getASCIIDescription(int code) {
        switch (code) {
            case 0: return "Null";
            case 1: return "(SOH) Start of Heading";
            case 2: return "(STX) Start of Text";
            case 3: return "(ETX) End of Text";
            case 4: return "(EOT) End of Transmission";
            case 5: return "(ENQ) Enquiry";
            case 6: return "(ACK) Acknowledgment";
            case 7: return "(BEL) Bell";
            case 8: return "(BS) Backspace";
            case 9: return "(HT) Horizontal Tab";
            case 10: return "(LF) Line Feed";
            case 11: return "(VT) Vertical Tab";
            case 12: return "(FF) Form Feed";
            case 13: return "(CR) Carriage Return";
            case 14: return "(SO) Shift Out";
            case 15: return "(SI) Shift In";
            case 16: return "(DLE) Data Link Escape";
            case 17: return "(DC1) Device Control 1";
            case 18: return "(DC2) Device Control 2";
            case 19: return "(DC3) Device Control 3";
            case 20: return "(DC4) Device Control 4";
            case 21: return "(NAK) Negative Acknowledgment";
            case 22: return "(SYN) Synchronous Idle";
            case 23: return "(ETB) End of Transmission Block";
            case 24: return "(CAN) Cancel";
            case 25: return "(EM) End of Medium";
            case 26: return "(SUB) Substitute";
            case 27: return "(ESC) Escape";
            case 28: return "(FS) File Separator";
            case 29: return "(GS) Group Separator";
            case 30: return "(RS) Record Separator";
            case 31: return "(US) Unit Separator";
            case 32: return "Space";
            case 127: return "(DEL) Delete";
            default:
                if (code > 32 && code < 127) {
                    return "Printable Character: " + (char) code;
                }
                return "Unknown";
        }
    }
}
