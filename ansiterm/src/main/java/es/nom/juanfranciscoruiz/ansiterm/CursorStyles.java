package es.nom.juanfranciscoruiz.ansiterm;

/**
 * Contiene las constantes ANSI para el cambio de los estilos del cursor
 * @author juanf
 */
public class CursorStyles {

    /**
     * Secuencia de escape ANSI con la que comienzan todas las secuencias CSI
     * Más información en:
     * https://invisible-island.net/xterm/ctlseqs/ctlseqs.html
     */
    public static final String ESC = "\033";
    /**
     * Forma del cursor establecido por el usuario
     */
    public final static String CUR_USU = ESC + "[0 q";
    /**
     * Forma de cursor de bloque parpadeante
     */
    public final static String CUR_BLO_PAR = ESC + "[1 q";
    /**
     * Forma de cursor de bloque estable
     */
    public final static String CUR_BLO_EST = ESC + "[2 q";
    /**
     * Forma de cursor de subrayado parpadeante
     */
    public final static String CUR_SUB_PAR = ESC + "[3 q";
    /**
     * Forma de cursor de subrayado estable
     */
    public final static String CUR_SUB_EST  = ESC + "[4 q";
    /**
     * Forma de cursor de barra parpadeante
     */
    public final static String CUR_BAR_PAR  = ESC + "[5 q";
    /**
     * Forma de cursor de barra estable
     */
    public final static String CUR_BAR_ES  = ESC + "[6 q";

    private CursorStyles() {

    }
}
