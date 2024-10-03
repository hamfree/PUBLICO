package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Enumeración para los colores de fondo con códigos ANSI para la terminal
 * @author juanf
 */
public enum BGColor {
    NEGRO(40),
    ROJO(41),
    VERDE(42),
    AMARILLO(43),
    AZUL(44),
    MAGENTA(45),
    CIAN(46),
    BLANCO(47),
    NEGRO_BRILLANTE(100),
    ROJO_BRILLANTE(101),
    VERDE_BRILLANTE(102),
    AMARILLO_BRILLANTE(103),
    AZUL_BRILLANTE(104),
    MAGENTA_BRILLANTE(105),
    CIAN_BRILLANTE(106),
    BLANCO_BRILLANTE(107),
    DEFECTO(49);

    private final int codigo;

    private BGColor(int codigo) {
        this.codigo = codigo;
    }

    String getAsString() {
        return String.valueOf(this.codigo);
    }
}
