package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Enumeración para los colores de primer plano con códigos ANSI para la terminal
 * @author juanf
 */
public enum Color {
    /**
     * Color negro para el primer plano.
     */
    NEGRO (30), 
    /**
     * Color rojo para el primer plano.
     */
    ROJO (31), 
    /**
     * Color verde para el primer plano.
     */
    VERDE (32), 
    /**
     * Color amarillo para el primer plano.
     */
    AMARILLO (33), 
    /**
     * Color azul para el primer plano.
     */
    AZUL (34), 
    /**
     * Color magenta para el primer plano.
     */
    MAGENTA (35), 
    /**
     * Color cian para el primer plano.
     */
    CIAN (36),
    /**
     * Color blanco para el primer plano.
     */
    BLANCO (37),
    /**
     * Hace que el color de primer plano vuelva a su valor por defecto.
     */
    DEFECTO (39),
    /**
     *  Color negro brillante para el primer plano.
     */
    NEGRO_BRILLANTE (90),
    /**
     * Color rojo brillante para el primer plano.
     */
    ROJO_BRILLANTE (91), 
    /**
     * Color verde brillante para el primer plano.
     */
    VERDE_BRILLANTE (92), 
    /**
     * Color amarillo brillante para el primer plano.
     */
    AMARILLO_BRILLANTE (93), 
    /**
     * Color azul brillante para el primer plano.
     */
    AZUL_BRILLANTE (94), 
    /**
     * Color magenta brillante para el primer plano.
     */
    MAGENTA_BRILLANTE (95), 
    /**
     * Color cian brillante para el primer plano.
     */
    CIAN_BRILLANTE (96),
    /**
     * Color blanco brillante para el primer plano.
     */
    BLANCO_BRILLANTE (97),;
    
    private final int codigo;
    
    Color(int codigo){
        this.codigo = codigo;
    }
    
    /**
     * Devuelve el código como una cadena.
     * @return una cadena con la representación textual del código
     */
    String getAsString() {
        return String.valueOf(this.codigo);
    }
}
