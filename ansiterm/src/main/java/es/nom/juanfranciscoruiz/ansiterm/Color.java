package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Enumeración para los colores de primer plano con códigos ANSI para la terminal
 * @author juanf
 */
public enum Color {
    NEGRO (30), 
    ROJO (31), 
    VERDE (32), 
    AMARILLO (33), 
    AZUL (34), 
    MAGENTA (35), 
    CIAN (36),
    BLANCO (37),
    DEFECTO (39),
    NEGRO_BRILLANTE (90),
    ROJO_BRILLANTE (91), 
    VERDE_BRILLANTE (92), 
    AMARILLO_BRILLANTE (93), 
    AZUL_BRILLANTE (94), 
    MAGENTA_BRILLANTE (95), 
    CIAN_BRILLANTE (96),
    BLANCO_BRILLANTE (97),;
    
    private final int codigo;
    
    Color(int codigo){
        this.codigo = codigo;
    }
    
    String getAsString() {
        return String.valueOf(this.codigo);
    }
}
