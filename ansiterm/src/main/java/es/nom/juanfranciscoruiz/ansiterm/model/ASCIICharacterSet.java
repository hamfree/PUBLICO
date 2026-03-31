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
            case 128 -> "(Ç) Letra C cedilla mayúscula\n";
            case 129 -> "(ü) Letra u minúscula con diéresis";
            case 130 -> "(é) Letra e minúscula con acento agudo\n";
            case 131 -> "(â) Letra a minúscula con acento circunflejo";
            case 132 -> "(ä) Letra a minúscula con diéresis\n";
            case 133 -> "(à) Letra a minúscula con acento grave\n";
            case 134 -> "(å) Letra a minúscula con anillo\n";
            case 135 -> "(ç) Letra c cedilla minúscula\n";
            case 136 -> "(ê) Letra e minúscula con acento circunflejo";
            case 137 -> "(ë) Letra e minúscula con diéresis\n";
            case 138 -> "(è) Letra e minúscula con acento grave\n";
            case 139 -> "(ï) Letra i minúscula con diéresis";
            case 140 -> "(î) Letra i minúscula con acento circunflejo";
            case 141 -> "(ì) Letra i minúscula con acento grave";
            case 142 -> "(Ä) Letra A mayúscula con diéresis";
            case 143 -> "(Å) Letra A mayúscula con anillo";
            case 144 -> "(É) Letra E mayúscula con acento agudo";
            case 145 -> "(æ) Diptongo latino ae minúscula";
            case 146 -> "(Æ) Diptongo latino AE mayúscula";
            case 147 -> "(ô) Letra o minúscula con acento circunflejo";
            case 148 -> "(ö) Letra o minúscula con diéresis";
            case 149 -> "(ò) Letra o minúscula con acento grave";
            case 150 -> "(û) Letra u minúscula con acento circunflejo";
            case 151 -> "(ù) Letra u minúscula con acento grave";
            case 152 -> "(ÿ) Letra y minúscula con diéresis";
            case 153 -> "(Ö) Letra O mayúscula con diéresis";
            case 154 -> "(Ü) Letra U mayúscula con diéresis";
            case 155 -> "(ø) Letra o minúscula con barra inclinada";
            case 156 -> "(£) Signo Libra Esterlina";
            case 157 -> "(Ø) Letra O mayúscula con barra inclinada";
            case 158 -> "(×) Signo de multiplicación";
            case 159 -> "(ƒ) Símbolo de función, florín neerlandés";
            case 160 -> "(á) Letra a minúscula con acento agudo";
            case 161 -> "(í) Letra i minúscula con acento agudo";
            case 162 -> "(ó) Letra o minúscula con acento agudo";
            case 163 -> "(ú) Letra u minúscula con acento agudo";
            case 164 -> "(ñ) Letra eñe minúscula - letra n con tilde - enie";
            case 165 -> "(Ñ) Letra EÑE mayúscula - letra N con tilde - ENIE";
            case 166 -> "(ª) Ordinal femenino, indicador de genero femenino";
            case 167 -> "(º) Ordinal masculino, indicador de genero masculino";
            case 168 -> "(¿) Abre signo interrogacion";
            case 169 -> "(®) Símbolo de Marca Registrada";
            case 170 -> "(¬) Signo de negacion";
            case 171 -> "(½) Un medio, mitad, fracción";
            case 172 -> "(¼) Un cuarto, cuarta parte, fracción";
            case 173 -> "(¡) Abre signos de exclamacion, signo de admiracion";
            case 174 -> "(«) Abre comillas bajas, angulares, latinas o españolas";
            case 175 -> "(») Cierra comillas bajas, angulares, latinas o españolas";
            case 176 -> "(░) Bloque color tramado densidad baja, carácter gráfico";
            case 177 -> "(▒) Bloque color tramado densidad media, gráfico";
            case 178 -> "(▓) Bloque color tramado densidad alta, carácter gráfico";
            case 179 -> "(│) Línea simple vertical de recuadro gráfico";
            case 180 -> "(┤) Línea vertical con empalme de recuadro gráfico";
            case 181 -> "(Á) Letra a mayúscula con acento agudo";
            case 182 -> "(Â) Letra A mayúscula con acento circunflejo";
            case 183 -> "(À) Letra A mayúscula con acento grave";
            case 184 -> "(©) Símbolo Copyright, bajo derecho de autor";
            case 185 -> "(╣) Doble línea vertical empalme izquierdo, gráfico";
            case 186 -> "(║) Líneas doble vertical de recuadro gráfico, verticales";
            case 187 -> "(╗) Línea doble esquina superior derecha de recuadro";
            case 188 -> "(╝) Línea doble esquina inferior derecha de recuadro";
            case 189 -> "(¢) Signo centavo, céntimo o centésimo";
            case 190 -> "(¥) Signo monetario YEN japonés, YUAN chino";
            case 191 -> "(┐) Línea simple esquina de recuadro gráfico";
            case 192 -> "(└) Línea simple esquina de recuadro gráfico";
            case 193 -> "(┴) Línea horizontal con empalme de recuadro gráfico";
            case 194 -> "(┬) Línea horizontal con empalme de recuadro gráfico";
            case 195 -> "(├) Línea vertical con empalme de recuadro gráfico";
            case 196 -> "(─) Línea simple horizontal de recuadro gráfico";
            case 197 -> "(┼) Líneas simples empalmes de recuadro gráfico";
            case 198 -> "(ã) Letra a minúscula con tilde";
            case 199 -> "(Ã) Letra A mayúscula con tilde";
            case 200 -> "(╚) Línea doble esquina inferior izquierda de recuadro";
            case 201 -> "(╔) Línea doble esquina superior izquierda de recuadro";
            case 202 -> "(╩) Doble línea horizontal empalme arriba, recuadro";
            case 203 -> "(╦) Doble línea horizontal empalme abajo, recuadro";
            case 204 -> "(╠) Doble línea vertical empalme derecho, recuadro";
            case 205 -> "(═) Líneas doble horizontales de recuadro gráfico";
            case 206 -> "(╬) Líneas dobles cruce de líneas de recuadro gráfico";
            case 207 -> "(¤) Signo monetario - divisa general";
            case 208 -> "(ð  Letra eth latina minúscula";
            case 209 -> "(Ð) Letra eth latina mayúscula";
            case 210 -> "(Ê) Letra E mayúscula con acento circunflejo";
            case 211 -> "(Ë) Letra E mayúscula con diéresis";
            case 212 -> "(È) Letra E mayúscula con acento grave";
            case 213 -> "(ı) Letra minuscula i sin punto";
            case 214 -> "(Í) Letra i mayúscula con acento agudo";
            case 215 -> "(Î) Letra I mayúscula con acento circunflejo";
            case 216 -> "(Ï) Letra i mayúscula con diéresis";
            case 217 -> "(┘) Línea simple esquina de recuadro gráfico";
            case 218 -> "(┌) Línea simple esquina de recuadro gráfico";
            case 219 -> "(█) Bloque color pleno solido, carácter gráfico";
            case 220 -> "(▄) Medio bloque negro, mitad inferior, carácter gráfico";
            case 221 -> "(¦) Barra vertical partida";
            case 222 -> "(Ì) Letra I mayúscula con acento grave";
            case 223 -> "(▀) Medio bloque negro, mitad superior, carácter gráfico";
            case 224 -> "(Ó) Letra o mayúscula con acento agudo";
            case 225 -> "(ß) Letra alemana eszett o ese-zeta";
            case 226 -> "(Ô) Letra O mayúscula con acento circunflejo";
            case 227 -> "(Ò) Letra O mayúscula con acento grave";
            case 228 -> "(õ) Letra o minúscula con tilde";
            case 229 -> "(Õ) Letra O mayúscula con tilde";
            case 230 -> "(µ) Signo micro";
            case 231 -> "(þ) Letra latina thorn minúscula";
            case 232 -> "(Þ) Letra latina thorn mayúscula";
            case 233 -> "(Ú) Letra U mayúscula con acento agudo";
            case 234 -> "(Û) Letra U mayúscula con acento circunflejo";
            case 235 -> "(Ù) Letra U mayúscula con acento grave";
            case 236 -> "(ý) Letra y minúscula con acento agudo";
            case 237 -> "(Ý) Letra Y mayúscula con acento agudo";
            case 238 -> "(¯) Macron (marca larga), superguión, guión alto";
            case 239 -> "(´) Acento agudo";
            case 240 -> "(≡) Símbolo matemático de congruencia, equivalencia";
            case 241 -> "(±) Signo mas menos";
            case 242 -> "(‗) ASCII 242";
            case 243 -> "(¾) Tres cuartos, fracción";
            case 244 -> "(¶) Fin de párrafo - signo de calderón";
            case 245 -> "(§) Signo de sección";
            case 246 -> "(÷) Signo de división";
            case 247 -> "(¸) Cedilla , virgulilla baja";
            case 248 -> "(°) Signo de grado, anillo";
            case 249 -> "(¨) Diéresis";
            case 250 -> "(·) Punto centrado, punto medio, coma georgiana";
            case 251 -> "(¹) Superíndice uno";
            case 252 -> "(³) Superíndice tres , potencia tres , al cubo";
            case 253 -> "(²) Superíndice dos , al cuadrado";
            case 254 -> "(■) Cuadrado negro, caracter gráfico";
            case 255 -> "(nbsp) Espacio sin separación - non breaking space";
            default -> "Unknown";
        };
    }
}
