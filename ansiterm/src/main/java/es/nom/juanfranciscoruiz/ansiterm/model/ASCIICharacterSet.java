package es.nom.juanfranciscoruiz.ansiterm.model;

/**
 * Represents the standard 7-bit ASCII character set (0-127).
 * This class initializes and provides access to ASCIIChar objects for each code.
 */
public class ASCIICharacterSet {
    /**
     * An array of ASCIIChar objects representing all the characters in the
     * ASCII character set along with their associated metadata.
     * This array is initialized and managed internally by the ASCIICharacterSet
     * class to provide access to individual ASCIIChar instances or a copy of
     * the entire set of characters.
     */
    private final ASCIIChar[] characters;

    /**
     * Constructor for the ASCIICharacterSet class.
     * Initializes the ASCIICharacterSet instance by creating an array of ASCIIChar objects
     * for all 128 standard ASCII characters (0-127), and populates the array with their corresponding
     * information such as ASCII code, character representation, and description.
     * The initialization process ensures that the characters array is fully populated and ready
     * to be used for retrieving ASCII character metadata.
     */
    public ASCIICharacterSet() {
        this.characters = new ASCIIChar[128];
        initialize();
    }

    /**
     * Populates the characters array with ASCIIChar objects for all standard ASCII characters (0-127).
     * Each ASCIIChar object contains the ASCII code, corresponding character, and a description.
     * <p>
     * The method iterates from 0 to 127 and for each value:<br>
     * - Creates a new ASCIIChar object.<br>
     * - Sets the ASCII code for the character.<br>
     * - Sets the Unicode character that the code represents.<br>
     * - Sets a description, which is determined by invoking the getASCIIDescription method.<br>
     * <p>
     * This ensures that the characters array is fully initialized and ready for use.
     */
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

    /**
     * Generates a human-readable description of an ASCII character or extended ASCII character.
     *
     * @param code the integer code representing the ASCII or extended ASCII character (0-255).
     * @return a descriptive string corresponding to the ASCII character or extended ASCII character.
     */
    private String getASCIIDescription(int code) {
        return switch (code) {
            case 0 -> "Null";
            case 1 -> "(SOH) Start of Heading";
            case 2 -> "(STX) Start of Text";
            case 3 -> "(ETX) End of Text";
            case 4 -> "(EOT) End of Transmission";
            case 5 -> "(ENQ) Enquiry";
            case 6 -> "(ACK) Acknowledgment";
            case 7 -> "(BEL) Bell";
            case 8 -> "(BS) Backspace";
            case 9 -> "(HT) Horizontal Tab";
            case 10 -> "(LF) Line Feed";
            case 11 -> "(VT) Vertical Tab";
            case 12 -> "(FF) Form Feed";
            case 13 -> "(CR) Carriage Return";
            case 14 -> "(SO) Shift Out";
            case 15 -> "(SI) Shift In";
            case 16 -> "(DLE) Data Link Escape";
            case 17 -> "(DC1) Device Control 1";
            case 18 -> "(DC2) Device Control 2";
            case 19 -> "(DC3) Device Control 3";
            case 20 -> "(DC4) Device Control 4";
            case 21 -> "(NAK) Negative Acknowledgment";
            case 22 -> "(SYN) Synchronous Idle";
            case 23 -> "(ETB) End of Transmission Block";
            case 24 -> "(CAN) Cancel";
            case 25 -> "(EM) End of Medium";
            case 26 -> "(SUB) Substitute";
            case 27 -> "(ESC) Escape";
            case 28 -> "(FS) File Separator";
            case 29 -> "(GS) Group Separator";
            case 30 -> "(RS) Record Separator";
            case 31 -> "(US) Unit Separator";
            case 32 -> "Space";
            case 33 -> "(!) Exclamation";
            case 34 -> "(&) Double Quote";
            case 35 -> "(#) Hash";
            case 36 -> "($) Dollar";
            case 37 -> "(%) Percent";
            case 38 -> "(&) Ampersand";
            case 39 -> "(') Single Quote";
            case 40 -> "(*) Left Parenthesis";
            case 41 -> "(+) Right Parenthesis";
            case 42 -> "(*) Asterisk";
            case 43 -> "(+) Plus";
            case 44 -> "(,) Comma";
            case 45 -> "(-) Minus";
            case 46 -> "(.) Period";
            case 47 -> "(/) Slash";
            case 48 -> "(0) Zero";
            case 49 -> "(1) One";
            case 50 -> "(2) Two";
            case 51 -> "(3) Three";
            case 52 -> "(4) Four";
            case 53 -> "(5) Five";
            case 54 -> "(6) Six";
            case 55 -> "(7) Seven";
            case 56 -> "(8) Eight";
            case 57 -> "(9) Nine";
            case 58 -> "(: Colon";
            case 59 -> "(; Semicolon";
            case 60 -> "(<) Less Than";
            case 61 -> "(=) Equals";
            case 62 -> "(>) Greater Than";
            case 63 -> "(?) Question";
            case 64 -> "(@) At";
            case 65 -> "(A) Capital A";
            case 66 -> "(B) Capital B";
            case 67 -> "(C) Capital C";
            case 68 -> "(D) Capital D";
            case 69 -> "(E) Capital E";
            case 70 -> "(F) Capital F";
            case 71 -> "(G) Capital G";
            case 72 -> "(H) Capital H";
            case 73 -> "(I) Capital I";
            case 74 -> "(J) Capital J";
            case 75 -> "(K) Capital K";
            case 76 -> "(L) Capital L";
            case 77 -> "(M) Capital M";
            case 78 -> "(N) Capital N";
            case 79 -> "(O) Capital O";
            case 80 -> "(P) Capital P";
            case 81 -> "(Q) Capital Q";
            case 82 -> "(R) Capital R";
            case 83 -> "(S) Capital S";
            case 84 -> "(T) Capital T";
            case 85 -> "(U) Capital U";
            case 86 -> "(V) Capital V";
            case 87 -> "(W) Capital W";
            case 88 -> "(X) Capital X";
            case 89 -> "(Y) Capital Y";
            case 90 -> "(Z) Capital Z";
            case 91 -> "([) Left Square Bracket";
            case 92 -> "(/) Backslash";
            case 93 -> "]) Right Square Bracket";
            case 94 -> "(^) Caret";
            case 95 -> "(_) Underscore";
            case 96 -> "` (Backtick)";
            case 97 -> "(a) Small a";
            case 98 -> "(b) Small b";
            case 99 -> "(c) Small c";
            case 100 -> "(d) Small d";
            case 101 -> "(e) Small e";
            case 102 -> "(f) Small f";
            case 103 -> "(g) Small g";
            case 104 -> "(h) Small h";
            case 105 -> "(i) Small i";
            case 106 -> "(j) Small j";
            case 107 -> "(k) Small k";
            case 108 -> "(l) Small l";
            case 109 -> "(m) Small m";
            case 110 -> "(n) Small n";
            case 111 -> "(o) Small o";
            case 112 -> "(p) Small p";
            case 113 -> "(q) Small q";
            case 114 -> "(r) Small r";
            case 115 -> "(s) Small s";
            case 116 -> "(t) Small t";
            case 117 -> "(u) Small u";
            case 118 -> "(v) Small v";
            case 119 -> "(w) Small w";
            case 120 -> "(x) Small x";
            case 121 -> "(y) Small y";
            case 122 -> "(z) Small z";
            case 123 -> "({) Left Curly Bracket";
            case 124 -> "(|) Vertical Bar";
            case 125 -> "(}) Right Curly Bracket";
            case 126 -> "(~) Tilde";
            case 127 -> "(DEL) Delete";
            case 128 -> "(Ç) Capital letter C with cedilla\n";
            case 129 -> "(ü) Lowercase letter u with diaeresis\n";
            case 130 -> "(é) Lowercase letter e with acute accent\n\n";
            case 131 -> "(â) Lowercase letter a with circumflex accent\n";
            case 132 -> "(ä) Lowercase letter a with diaeresis\n";
            case 133 -> "(à) Lowercase letter a with grave accent\n";
            case 134 -> "(å) Lowercase letter a with ring accent\n";
            case 135 -> "(ç) Lowercase letter c with cedilla\n";
            case 136 -> "(ê) Lowercase letter e with circumflex accent\n";
            case 137 -> "(ë) Lowercase letter e with diaeresis\n";
            case 138 -> "(è) Lowercase letter e with grave accent\n";
            case 139 -> "(ï) Lowercase letter i with diaeresis\n";
            case 140 -> "(î) Lowercase letter i with circumflex accent\n";
            case 141 -> "(ì) Lowercase letter i with grave accent\n";
            case 142 -> "(Ä) Capital letter A with diaeresis\n";
            case 143 -> "(Å) Capital letter A with ring accent\n";
            case 144 -> "(É) Capital letter E with acute accent\n";
            case 145 -> "(æ) Lowercase Latin diphthong ae\n";
            case 146 -> "(Æ) Capital Latin diphthong AE\n";
            case 147 -> "(ô) Lowercase letter o with circumflex accent\n";
            case 148 -> "(ö) Lowercase letter o with diaeresis\n";
            case 149 -> "(ò) Lowercase letter o with grave accent\n";
            case 150 -> "(û) Lowercase letter u with circumflex accent\n";
            case 151 -> "(ù) Lowercase letter u with accent Grave (stressed)\n";
            case 152 -> "(ÿ) Lowercase y with umlaut\n";
            case 153 -> "(Ö) Uppercase O with umlaut\n";
            case 154 -> "(Ü) Uppercase U with umlaut\n";
            case 155 -> "(ø) Lowercase o with slash\n";
            case 156 -> "(£) Pound Sterling symbol\n";
            case 157 -> "(Ø) Uppercase O with slash\n";
            case 158 -> "(×) Multiplication sign\n\n";
            case 159 -> "(ƒ) Function symbol, Dutch guilder\n";
            case 160 -> "(á) Lowercase a with acute accent\n";
            case 161 -> "(í) Lowercase i with acute accent\n";
            case 162 -> "(ó) Lowercase o with acute accent\n";
            case 163 -> "(ú) Lowercase u with acute accent\n";
            case 164 -> "(ñ) Lowercase ñ - n with a tilde - enie\n";
            case 165 -> "(Ñ) Uppercase Ñ - N with a tilde - ENIE\n";
            case 166 -> "(ª) Feminine ordinal, indicator of feminine gender\n";
            case 167 -> "(º) Masculine ordinal, indicator of masculine gender\n";
            case 168 -> "(¿) Opens question mark\n";
            case 169 -> "(®) Registered Trademark symbol\n";
            case 170 -> "(¬) Negation sign\n";
            case 171 -> "(½) One half, half, fraction\n";
            case 172 -> "(¼) One quarter, quarter, fraction\n";
            case 173 -> "(¡) Opens exclamation mark, exclamation point\n";
            case 174 -> "(«) Opens quotation marks, angle quotes, guillemets, or Spanish quotation marks\n";
            case 175 -> "(») Closes quotation marks Low-density, angular, Latin, or Spanish letters\n";
            case 176 -> "(░) Block of color with low-density halftone, graphic character\n";
            case 177 -> "(▒) Block of color with medium-density halftone, graphic character\n";
            case 178 -> "(▓) Block of color with high-density halftone, graphic character\n";
            case 179 -> "(│) Single vertical line of graphic box\n";
            case 180 -> "(┤) Vertical line with fill of graphic box\n";
            case 181 -> "(Á) Capital letter A with acute accent\n";
            case 182 -> "(Â) Capital letter A with circumflex accent\n";
            case 183 -> "(À) Capital letter A with grave accent\n";
            case 184 -> "(©) Copyright symbol\n";
            case 185 -> "(╣) Double vertical line with left fill, graphic character\n";
            case 186 -> "(║) Double vertical lines of graphic box, vertical\n";
            case 187 -> "(╗) Double line in upper right corner of box\n";
            case 188 -> "(╝) Double line in lower right corner of box\n";
            case 189 -> "(¢) Cent sign, cent, or hundredth\n";
            case 190 -> "(¥) Currency sign: Japanese YEN, Chinese YUAN\n";
            case 191 -> "(┐) Single line in corner of graphic box\n";
            case 192 -> "(└) Single line in corner of graphic box\n";
            case 193 -> "(┴) Horizontal line with fill of graphic box\n";
            case 194 -> "(┬) Horizontal line with fill of graphic box\n";
            case 195 -> "(├) Vertical line with fill of box Graphic\n";
            case 196 -> "(─) Single horizontal line of graphic box\n";
            case 197 -> "(┼) Single lines joining graphic box\n";
            case 198 -> "(ã) Lowercase letter a with accent\n";
            case 199 -> "(Ã) Uppercase letter A with accent\n";
            case 200 -> "(╚) Double line lower left corner of box\n";
            case 201 -> "(╔) Double line upper left corner of box\n";
            case 202 -> "(╩) Double horizontal line joining top of box\n";
            case 203 -> "(╦) Double horizontal line joining bottom of box\n";
            case 204 -> "(╠) Double vertical line joining right of box\n";
            case 205 -> "(═) Double horizontal lines of graphic box\n";
            case 206 -> "(╬) Double lines crossing lines of graphic box\n";
            case 207 -> "(¤) Monetary symbol - general currency\n";
            case 208 -> "(ð  Lowercase Latin letter eth\n";
            case 209 -> "(Ð) Uppercase Latin letter eth\n";
            case 210 -> "(Ê) Uppercase letter E with circumflex accent\n";
            case 211 -> "(Ë) Uppercase letter E with diaeresis\n";
            case 212 -> "(È) Uppercase letter E with grave accent\n";
            case 213 -> "(ı) Lowercase letter i without a period\n";
            case 214 -> "(Í) Uppercase letter i with acute accent\n";
            case 215 -> "(Î) Uppercase letter I with circumflex accent\n";
            case 216 -> "(Ï) Uppercase letter i with diaeresis\n";
            case 217 -> "(┘) Single line, corner of graphic box\n";
            case 218 -> "(┌) Single line, corner of graphic box\n";
            case 219 -> "(█) Solid color block, graphic character\n";
            case 220 -> "(▄) Black half-block, lower half, graphic character\n";
            case 221 -> "(¦) Broken vertical bar\n";
            case 222 -> "(Ì) Capital letter I with grave accent\n";
            case 223 -> "(▀) Black half-block, upper half, graphic character\n";
            case 224 -> "(Ó) Capital letter o with acute accent\n";
            case 225 -> "(ß) German letter eszett or ese-zeta\n";
            case 226 -> "(Ô) Capital letter O with circumflex accent\n";
            case 227 -> "(Ò) Capital letter O with grave accent\n";
            case 228 -> "(õ) Lowercase letter o with tilde\n";
            case 229 -> "(Õ) Capital letter O with tilde\n";
            case 230 -> "(µ) Micro symbol\n";
            case 231 -> "(þ) Lowercase Latin letter thorn\n";
            case 232 -> "(Þ) Uppercase Latin letter thorn\n";
            case 233 -> "(Ú) U capital letter U with acute accent\n";
            case 234 -> "(Û) Capital letter U with circumflex accent\n";
            case 235 -> "(Ù) Capital letter U with grave accent\n";
            case 236 -> "(ý) Lowercase letter y with acute accent\n\n";
            case 237 -> "(Ý) Capital letter Y with acute acent\n";
            case 238 -> "(¯) Macron (long mark), superscript macron, hyphen\n";
            case 239 -> "(´) Acute accent\n";
            case 240 -> "(≡) Mathematical symbol for congruence, equivalence\n";
            case 241 -> "(±) Plus/Minus sign\n";
            case 242 -> "(‗) ASCII 242\n";
            case 243 -> "(¾) Three-quarters, fraction\n";
            case 244 -> "(¶) End of paragraph - pilcrow sign\n";
            case 245 -> "(§) Section sign\n";
            case 246 -> "(÷) Sign of division\n";
            case 247 -> "(¸) Cedilla, tilde\n";
            case 248 -> "(°) Degree sign, ring\n";
            case 249 -> "(¨) Diaeresis\n";
            case 250 -> "(·) Centered dot, midpoint, Georgian comma\n";
            case 251 -> "(¹) Superscript one\n";
            case 252 -> "(³) Superscript three, power three, cubed\n";
            case 253 -> "(²) Superscript two, squared\n\n";
            case 254 -> "(■) Black square, graphic character\n\n";
            case 255 -> "(nbsp) Non-breaking space\n";
            default -> "Unknown";
        };
    }
}
