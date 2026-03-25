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
            case 33: return "(!) Exclamation";
            case 34: return "(&) Double Quote";
            case 35: return "(#) Hash";
            case 36: return "($) Dollar";
            case 37: return "(%) Percent";
            case 38: return "(&) Ampersand";
            case 39: return "(') Single Quote";
            case 40: return "(*) Left Parenthesis";
            case 41: return "(+) Right Parenthesis";
            case 42: return "(*) Asterisk";
            case 43: return "(+) Plus";
            case 44: return "(,) Comma";
            case 45: return "(-) Minus";
            case 46: return "(.) Period";
            case 47: return "(/) Slash";
            case 48: return "(0) Zero";
            case 49: return "(1) One";
            case 50: return "(2) Two";
            case 51: return "(3) Three";
            case 52: return "(4) Four";
            case 53: return "(5) Five";
            case 54: return "(6) Six";
            case 55: return "(7) Seven";
            case 56: return "(8) Eight";
            case 57: return "(9) Nine";
            case 58: return "(: Colon";
            case 59: return "(; Semicolon";
            case 60: return "(<) Less Than";
            case 61: return "(=) Equals";
            case 62: return "(>) Greater Than";
            case 63: return "(?) Question";
            case 64: return "(@) At";
            case 65: return "(A) Capital A";
            case 66: return "(B) Capital B";
            case 67: return "(C) Capital C";
            case 68: return "(D) Capital D";
            case 69: return "(E) Capital E";
            case 70: return "(F) Capital F";
            case 71: return "(G) Capital G";
            case 72: return "(H) Capital H";
            case 73: return "(I) Capital I";
            case 74: return "(J) Capital J";
            case 75: return "(K) Capital K";
            case 76: return "(L) Capital L";
            case 77: return "(M) Capital M";
            case 78: return "(N) Capital N";
            case 79: return "(O) Capital O";
            case 80: return "(P) Capital P";
            case 81: return "(Q) Capital Q";
            case 82: return "(R) Capital R";
            case 83: return "(S) Capital S";
            case 84: return "(T) Capital T";
            case 85: return "(U) Capital U";
            case 86: return "(V) Capital V";
            case 87: return "(W) Capital W";
            case 88: return "(X) Capital X";
            case 89: return "(Y) Capital Y";
            case 90: return "(Z) Capital Z";
            case 91: return "([) Left Square Bracket";
            case 92: return "(/) Backslash";
            case 93: return "]) Right Square Bracket";
            case 94: return "(^) Caret";
            case 95: return "(_) Underscore";
            case 96: return "` (Backtick)";
            case 97: return "(a) Small a";
            case 98: return "(b) Small b";
            case 99: return "(c) Small c";
            case 100: return "(d) Small d";
            case 101: return "(e) Small e";
            case 102: return "(f) Small f";
            case 103: return "(g) Small g";
            case 104: return "(h) Small h";
            case 105: return "(i) Small i";
            case 106: return "(j) Small j";
            case 107: return "(k) Small k";
            case 108: return "(l) Small l";
            case 109: return "(m) Small m";
            case 110: return "(n) Small n";
            case 111: return "(o) Small o";
            case 112: return "(p) Small p";
            case 113: return "(q) Small q";
            case 114: return "(r) Small r";
            case 115: return "(s) Small s";
            case 116: return "(t) Small t";
            case 117: return "(u) Small u";
            case 118: return "(v) Small v";
            case 119: return "(w) Small w";
            case 120: return "(x) Small x";
            case 121: return "(y) Small y";
            case 122: return "(z) Small z";
            case 123: return "({) Left Curly Bracket";
            case 124: return "(|) Vertical Bar";
            case 125: return "(}) Right Curly Bracket";
            case 126: return "(~) Tilde";
            case 127: return "(DEL) Delete";
            case 128: return "(Ç) Letra C cedilla mayúscula\n";
            case 129: return "(ü) Letra u minúscula con diéresis";
            case 130: return "(é) Letra e minúscula con acento agudo\n";
            case 131: return "(â) Letra a minúscula con acento circunflejo";
            case 132: return "(ä) Letra a minúscula con diéresis\n";
            case 133: return "(à) Letra a minúscula con acento grave\n";
            case 134: return "(å) Letra a minúscula con anillo\n";
            case 135: return "(ç) Letra c cedilla minúscula\n";
            case 136: return "(ê) Letra e minúscula con acento circunflejo";
            case 137: return "(ë) Letra e minúscula con diéresis\n";
            case 138: return "(è) Letra e minúscula con acento grave\n";
            case 139: return "(ï) Letra i minúscula con diéresis";
            case 140: return "(î) Letra i minúscula con acento circunflejo";
            case 141: return "(ì) Letra i minúscula con acento grave";
            case 142: return "(Ä) Letra A mayúscula con diéresis";
            case 143: return "(Å) Letra A mayúscula con anillo";
            case 144: return "(É) Letra E mayúscula con acento agudo";
            case 145: return "(æ) Diptongo latino ae minúscula";
            case 146: return "(Æ) Diptongo latino AE mayúscula";
            case 147: return "(ô) Letra o minúscula con acento circunflejo";
            case 148: return "(ö) Letra o minúscula con diéresis";
            case 149: return "(ò) Letra o minúscula con acento grave";
            case 150: return "(û) Letra u minúscula con acento circunflejo";
            case 151: return "(ù) Letra u minúscula con acento grave";
            case 152: return "(ÿ) Letra y minúscula con diéresis";
            case 153: return "(Ö) Letra O mayúscula con diéresis";
            case 154: return "(Ü) Letra U mayúscula con diéresis";
            case 155: return "(ø) Letra o minúscula con barra inclinada";
            case 156: return "(£) Signo Libra Esterlina";
            case 157: return "(Ø) Letra O mayúscula con barra inclinada";
            case 158: return "(×) Signo de multiplicación";
            case 159: return "(ƒ) Símbolo de función, florín neerlandés";
            case 160: return "(á) Letra a minúscula con acento agudo";
            case 161: return "(í) Letra i minúscula con acento agudo";
            case 162: return "(ó) Letra o minúscula con acento agudo";
            case 163: return "(ú) Letra u minúscula con acento agudo";
            case 164: return "(ñ) Letra eñe minúscula - letra n con tilde - enie";
            case 165: return "(Ñ) Letra EÑE mayúscula - letra N con tilde - ENIE";
            case 166: return "(ª) Ordinal femenino, indicador de genero femenino";
            case 167: return "(º) Ordinal masculino, indicador de genero masculino";
            case 168: return "(¿) Abre signo interrogacion";
            case 169: return "(®) Símbolo de Marca Registrada";
            case 170: return "(¬) Signo de negacion";
            case 171: return "(½) Un medio, mitad, fracción";
            case 172: return "(¼) Un cuarto, cuarta parte, fracción";
            case 173: return "(¡) Abre signos de exclamacion, signo de admiracion";
            case 174: return "(«) Abre comillas bajas, angulares, latinas o españolas";
            case 175: return "(») Cierra comillas bajas, angulares, latinas o españolas";
            case 176: return "(░) Bloque color tramado densidad baja, carácter gráfico";
            case 177: return "(▒) Bloque color tramado densidad media, gráfico";
            case 178: return "(▓) Bloque color tramado densidad alta, carácter gráfico";
            case 179: return "(│) Línea simple vertical de recuadro gráfico";
            case 180: return "(┤) Línea vertical con empalme de recuadro gráfico";
            case 181: return "(Á) Letra a mayúscula con acento agudo";
            case 182: return "(Â) Letra A mayúscula con acento circunflejo";
            case 183: return "(À) Letra A mayúscula con acento grave";
            case 184: return "(©) Símbolo Copyright, bajo derecho de autor";
            case 185: return "(╣) Doble línea vertical empalme izquierdo, gráfico";
            case 186: return "(║) Líneas doble vertical de recuadro gráfico, verticales";
            case 187: return "(╗) Línea doble esquina superior derecha de recuadro";
            case 188: return "(╝) Línea doble esquina inferior derecha de recuadro";
            case 189: return "(¢) Signo centavo, céntimo o centésimo";
            case 190: return "(¥) Signo monetario YEN japonés, YUAN chino";
            case 191: return "(┐) Línea simple esquina de recuadro gráfico";
            case 192: return "(└) Línea simple esquina de recuadro gráfico";
            case 193: return "(┴) Línea horizontal con empalme de recuadro gráfico";
            case 194: return "(┬) Línea horizontal con empalme de recuadro gráfico";
            case 195: return "(├) Línea vertical con empalme de recuadro gráfico";
            case 196: return "(─) Línea simple horizontal de recuadro gráfico";
            case 197: return "(┼) Líneas simples empalmes de recuadro gráfico";
            case 198: return "(ã) Letra a minúscula con tilde";
            case 199: return "(Ã) Letra A mayúscula con tilde";
            case 200: return "(╚) Línea doble esquina inferior izquierda de recuadro";
            case 201: return "(╔) Línea doble esquina superior izquierda de recuadro";
            case 202: return "(╩) Doble línea horizontal empalme arriba, recuadro";
            case 203: return "(╦) Doble línea horizontal empalme abajo, recuadro";
            case 204: return "(╠) Doble línea vertical empalme derecho, recuadro";
            case 205: return "(═) Líneas doble horizontales de recuadro gráfico";
            case 206: return "(╬) Líneas dobles cruce de líneas de recuadro gráfico";
            case 207: return "(¤) Signo monetario - divisa general";
            case 208: return "(ð  Letra eth latina minúscula";
            case 209: return "(Ð) Letra eth latina mayúscula";
            case 210: return "(Ê) Letra E mayúscula con acento circunflejo";
            case 211: return "(Ë) Letra E mayúscula con diéresis";
            case 212: return "(È) Letra E mayúscula con acento grave";
            case 213: return "(ı) Letra minuscula i sin punto";
            case 214: return "(Í) Letra i mayúscula con acento agudo";
            case 215: return "(Î) Letra I mayúscula con acento circunflejo";
            case 216: return "(Ï) Letra i mayúscula con diéresis";
            case 217: return "(┘) Línea simple esquina de recuadro gráfico";
            case 218: return "(┌) Línea simple esquina de recuadro gráfico";
            case 219: return "(█) Bloque color pleno solido, carácter gráfico";
            case 220: return "(▄) Medio bloque negro, mitad inferior, carácter gráfico";
            case 221: return "(¦) Barra vertical partida";
            case 222: return "(Ì) Letra I mayúscula con acento grave";
            case 223: return "(▀) Medio bloque negro, mitad superior, carácter gráfico";
            case 224: return "(Ó) Letra o mayúscula con acento agudo";
            case 225: return "(ß) Letra alemana eszett o ese-zeta";
            case 226: return "(Ô) Letra O mayúscula con acento circunflejo";
            case 227: return "(Ò) Letra O mayúscula con acento grave";
            case 228: return "(õ) Letra o minúscula con tilde";
            case 229: return "(Õ) Letra O mayúscula con tilde";
            case 230: return "(µ) Signo micro";
            case 231: return "(þ) Letra latina thorn minúscula";
            case 232: return "(Þ) Letra latina thorn mayúscula";
            case 233: return "(Ú) Letra U mayúscula con acento agudo";
            case 234: return "(Û) Letra U mayúscula con acento circunflejo";
            case 235: return "(Ù) Letra U mayúscula con acento grave";
            case 236: return "(ý) Letra y minúscula con acento agudo";
            case 237: return "(Ý) Letra Y mayúscula con acento agudo";
            case 238: return "(¯) Macron (marca larga), superguión, guión alto";
            case 239: return "(´) Acento agudo";
            case 240: return "(≡) Símbolo matemático de congruencia, equivalencia";
            case 241: return "(±) Signo mas menos";
            case 242: return "(‗) ASCII 242";
            case 243: return "(¾) Tres cuartos, fracción";
            case 244: return "(¶) Fin de párrafo - signo de calderón";
            case 245: return "(§) Signo de sección";
            case 246: return "(÷) Signo de división";
            case 247: return "(¸) Cedilla , virgulilla baja";
            case 248: return "(°) Signo de grado, anillo";
            case 249: return "(¨) Diéresis";
            case 250: return "(·) Punto centrado, punto medio, coma georgiana";
            case 251: return "(¹) Superíndice uno";
            case 252: return "(³) Superíndice tres , potencia tres , al cubo";
            case 253: return "(²) Superíndice dos , al cuadrado";
            case 254: return "(■) Cuadrado negro, caracter gráfico";
            case 255: return "(nbsp) Espacio sin separación - non breaking space";
            default:
                return "Unknown";
        }
    }
}
