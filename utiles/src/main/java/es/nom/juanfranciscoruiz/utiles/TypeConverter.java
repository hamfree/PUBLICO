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
import es.nom.juanfranciscoruiz.utiles.exception.TypeConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility that converts some types of objects into others, extracts numeric 
 * values ​​from a string and converts bytes and characters to their hexadecimal 
 * representation.
 * 
 * @author hamfree
 */
public class TypeConverter {

    /**
     * For debugging.
     */
    private final static Logger logger = LoggerFactory.getLogger(TypeConverter.class);

    /**
     * Converts a generic type collection to a generic type ArrayList
     *
     * @param <T> the type of the collection
     * @param clazz The collection type class
     * @param c the collection to be converted
     * @return An ArrayList with the contents of the collection converted.
     */
    public static <T> List<T> collection2List(Class<? extends T> clazz, Collection<?> c) {
        List<T> r = new ArrayList<>(c.size());
        for (Object o : c) {
            r.add(clazz.cast(o));
        }
        return r;
    }

    /**
     * Converts a generic map into a generic list (losing the keys, and 
     * obtaining the values)
     *
     * @param <T> El tipo genérico tanto del mapa como de la lista
     * @param clazz la clase del tipo genérico T
     * @param m El mapa a convertir
     * @return a list containing the contents of the converted map (their 
     * values).
     */
    public static <T> List<T> map2List(Class<? extends T> clazz, Map<String, Object> m) {
        List<T> r = new ArrayList<>(m.size());

        for (Map.Entry<String, Object> entry : m.entrySet()) {
            r.add(clazz.cast(entry.getValue()));
        }
        return r;
    }

    /**
     * Extracts a Long object from an arbitrary string
     *
     * @param src the string that can contain digits
     * @return a long from the existing digits in the string.
     * @throws a TypeConverterException in case a NumberFormatException or 
     * ParseException is generated when attempting the extraction.
     */
    public static Long extractLongFromString(String src) throws TypeConverterException {
        Long numLargo = (long) -1;
        try {
            if (src != null) {
                // We 'sanitize' the input, because it may come with characters 
                // that are not digits...
                src = src.trim();
                
                // regular expression that only allows digits to pass
                src = src.replaceAll("\\D+", ""); 
                
                // Makes the conversion
                Number number = NumberFormat.getInstance().parse(src); 
                numLargo = number.longValue();
            }

        } catch (NumberFormatException | ParseException ex) {
            logger.error(ex.getMessage());
            throw new TypeConverterException(ex.getMessage(), ex.getCause());
        }
        return numLargo;
    }

    /**
     * Extracts a Double object from an arbitrary string
     *
     * @param src a string that can contain digits that can be extracted to 
     * generate a Double object
     * @return a Double object if it can be extracted from the string
     * @throws  a TypeConverterException in case a NumberFormatException or 
     * ParseException is generated when attempting the extraction.
     */
    public static Double extractDoubleFromString(String src) {
        Double numDecimal = Double.valueOf(-1);

        if (src != null) {
            // We 'sanitize' the input, because it may come with characters 
            // that are not digits, decimal separator o minus sign...
            src = src.trim();
            src = src.replaceAll("[^\\d.-]", "");

            try {
                // Makes the conversion
                numDecimal = Double.valueOf(src);
            } catch (NumberFormatException ex) {
                logger.error(ex.getMessage());
                throw new TypeConverterException(ex.getMessage(), ex.getCause());
            }
        }
        return numDecimal;
    }

    /**
     * Returns a textual representation of an array.
     *
     * @param obj The matrix whose textual representation is desired.
     * @return a string with the textual representation of the array or the
     * NULL constant ("null") in case the array points to null.
     */
    public static String array2String(Object obj) {
        StringBuilder result;
        if (obj == null) {
            return IO.getNULL();
        }
        if (isArray(obj)) {
            result = new StringBuilder(IO.getCHAR_INI());
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
            result.append(IO.getCHAR_FIN());
        } else {
            return IO.getNULL();
        }
        return result.toString();
    }

    /**
     * Returns a textual representation of the bytes in an array.
     *
     * @param array the byte vector
     * @param showLength If true, it will also display the length of the vector.
     * @param showIndex If true, it will also display the array index.
     * @return a textual representation of the byte vector.
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
     * Returns a textual representation of the map.
     *
     * @param map A map of any two types
     * @return a string with the textual representation of the keys and values
     * of the map
     */
    public static String map2String(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                sb.append(key).append(IO.getSEP()).append(value).append(IO.getLS());
            }
        }
        return sb.toString();
    }

    /**
     * Returns a string with the hexadecimal representation of the byte
     *
     * @param b a byte
     * @return a String with the hexadecimal representation of the byte
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
     * Returns a string with the hexadecimal representation of the char c
     *
     * @param c a char
     * @return a String with the hexadecimal representation of the char
     */
    public static String charToHex(char c) {
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }
    
    /**
     * Extracts the existing digits in a String and returns them in a string.
     *
     * @param src the string that can contain digits.
     * @return a string containing only digits.
     */
    public static String extractDigits(String src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.isBlank()){
            return builder.toString();
        }
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    // Utility methods for array2String()
    
    /**
     * If the object passed is an array and is not null it will return true, 
     * otherwise false
     *
     * @param obj An Object to check
     * @return a boolean that will be true if the object is an array and is not null.
     */
    private static boolean isNotNullArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }


    /**
     * Returns true if index is the last element
     *
     * @param index entero con el valor del índice
     * @param length integer with the length of elements of the object
     * @return boolean which will be true if 'index' is the last element
     */
    private static boolean isLastElement(int index, int length) {
        return (index == length - 1);
    }
}
