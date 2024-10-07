package es.nom.juanfranciscoruiz.utiles;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static es.nom.juanfranciscoruiz.utiles.Types.isArray;
import static es.nom.juanfranciscoruiz.utiles.Types.isNullOrEmpty;
import java.text.DecimalFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hamfree
 */
public class TypeConverter {

    /**
     * Para la depuracion.
     */
    private final static Logger logger = LoggerFactory.getLogger(TypeConverter.class);

    /**
     * Convierte una coleccion de tipo genérico a una ArrayList de tipo generico
     *
     * @param <T> el tipo de la colección
     * @param clazz La clase del tipo de la colección
     * @param c la colección a convertir
     * @return Un ArrayList con el contenido de la colección convertido.
     */
    public static <T> List<T> collection2List(Class<? extends T> clazz, Collection<?> c) {
        List<T> r = new ArrayList<>(c.size());
        for (Object o : c) {
            r.add(clazz.cast(o));
        }
        return r;
    }

    /**
     * Convierte un mapa genérico en una lista generica
     *
     * @param <T> el tipo genérico tanto del mapa como el de la lista
     * @param clazz la clase del tipo genérico T
     * @param m el mapa a convertir
     * @return una lista con el contenido del mapa convertido.
     */
    public static <T> List<T> map2List(Class<? extends T> clazz, Map<String, Object> m) {
        List<T> r = new ArrayList<>(m.size());

        for (Map.Entry<String, Object> entrada : m.entrySet()) {
            r.add(clazz.cast(entrada.getValue()));
        }
        return r;
    }

    /**
     * Extrae un long de una cadena
     *
     * @param src la cadena que puede contener dígitos
     * @return un long a partir de los dígitos existentes en la cadena.
     */
    public static Long extractLongFromString(String src) {
        Long numLargo = (long) -1;
        try {
            if (src != null) {
                // 'Saneamos' la entrada, porque puede venir con caracteres que
                // no son digitos...
                src = src.trim();
                src = src.replaceAll("\\D+", ""); // expresion regular
                // que 'solo' deja
                // pasar digitos...
                Number number = NumberFormat.getInstance().parse(src); // hace
                // la
                // conversión
                numLargo = number.longValue();
            }

        } catch (NumberFormatException | ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            logger.error(ex.getClass() + " : " + ex.getMessage());
            numLargo = (long) -1;
        }
        return numLargo;
    }

    /**
     * Extrae un double de una cadena
     *
     * @param src una cadena que puede contener dígitos que pueden * extraerse
     * para generar un double
     * @return un double a partir de los dígitos de la cadena o Double.NaN si 
     * no pudo realizar la conversión.
     */
    public static Double extractDoubleFromString(String src) {
        Double numDecimal = Double.valueOf(-1);

        if (src != null) {
            // 'Saneamos' la entrada, porque puede venir con caracteres que
            // no son digitos...
            src = src.trim();
            src = src.replaceAll("[^\\d.-]", "");

            try {
                /* hace la conversión */
                numDecimal = (src != null) ? Double.valueOf(src) : Double.NaN;
            } catch (NumberFormatException ex) {
                logger.error(ex.getLocalizedMessage());
                numDecimal = Double.NaN;
            }
        }
        return numDecimal;
    }

    /**
     * Devuelve una representación textual de una matriz.
     *
     * @param obj La matriz de la que se quiere su representación textual.
     * @return una cadena con la representacion textual de la matriz o la
     * constante NULL ("null").
     */
    public static String array2String(Object obj) {
        StringBuilder result;
        if (obj == null) {
            return IO.getNULL();
        }
        if (isArray(obj)) {
            result = new StringBuilder(IO.getCAR_INI());
            int length = Array.getLength(obj);
            for (int idx = 0; idx < length; ++idx) {
                Object item = Array.get(obj, idx);
                if (isNotNullArray(item)) {
                    //recursive call!
                    result.append(array2String(item));
                } else {
                    result.append(item);
                }
                if (!isLastElement(idx, length)) {
                    result.append(IO.getSEP());
                }
            }
            result.append(IO.getCAR_FIN());
        } else {
            return IO.getNULL();
        }
        return result.toString();
    }

    /**
     * Devuelve una representación textual de los bytes de un array.
     *
     * @param array el vector de bytes
     * @param showLength si es true mostrará además la longitud del vector.
     * @param showIndex si es true mostrará además el índice del array.
     * @return una representacion textual del vector de bytes.
     */
    public static String arrayByte2String(byte[] array, boolean showLength, boolean showIndex) {
        StringBuilder sb = new StringBuilder();
        if (!isNullOrEmpty(array)) {
            if (showLength) {
                sb.append("(").append(array.length).append(" bytes), ");
            }
            for (int i = 0; i < array.length; i++) {
                if (showIndex) {
                    sb.append("[").append(i).append("]=");
                }
                sb.append(array[i]).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        } else {
            return "";
        }
        return sb.toString();
    }

    /**
     * Devuelve una representación textual del mapa.
     *
     * @param map Un mapa de cualquier par de tipos
     * @return una cadena con la representación textual de las claves y valores
     * del mapa
     */
    public static String map2String(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<?, ?> entrada : map.entrySet()) {
                Object key = entrada.getKey();
                Object value = entrada.getValue();
                sb.append(key).append(IO.getSEP()).append(value).append(IO.getSL());
            }
        }
        return sb.toString();
    }

    /**
     * Devuelve una cadena con la representación hexadecimal del byte
     *
     * @param b un byte
     * @return un String con la representación hexadecimal del byte
     */
    public static String byteToHex(byte b) {
        char hexDigit[] = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        char[] array = {hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f]};
        return new String(array);
    }

    /**
     * Devuelve una cadena con la representación hexadecimal del char c
     *
     * @param c un char
     * @return un String con la representación hexadecimal del char
     */
    public static String charToHex(char c) {
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }

    /**
     * Si el objeto pasado es un array y no es nulo devolverá true, en caso
     * contrario false
     *
     * @param obj Un Object a comprobar
     * @return un boolean que será true si el objeto es un array y no es nulo.
     */
    private static boolean isNotNullArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    //Metodos de utilidad y ayuda para array2String()
    /**
     * Devuelve true si el índice es el último elemento
     *
     * @param index entero con el valor del índice
     * @param length entero con la longitud de elementos del objeto
     * @return boolean que será true si 'index' es el último elemento
     */
    private static boolean isLastElement(int index, int length) {
        return (index == length - 1);
    }

    /**
     * Extrae los dígitos existentes en un String y los devuelve en una cadena.
     *
     * @param src la cadena que puede contener dígitos.
     * @return una cadena que sólo contiene digitos.
     */
    public String extractDigits(String src) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

}
