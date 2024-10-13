package es.nom.juanfranciscoruiz.utiles;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de utilidad para comprobar tipos (sin usar las API's modernas de JAVA)
 *
 * @author hamfree
 */
public class Types {

    private static final Logger LOG = Logger.getLogger(Types.class.getName());

    /**
     * Indica si el objeto pasado es nulo o está vacío (en el caso de ser una
     * colección, u otro objeto contenedor de objetos.
     *
     * @param obj el objeto a comprobar
     * @return true si el objeto es nulo o está vacío, false en el caso
     * contrario.
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj.getClass().isAssignableFrom(Object.class)) {
            return Objects.isNull(obj);
        } else if (obj.getClass().isAssignableFrom(String.class)) {
            return obj.toString().isEmpty();
        } else if (obj.getClass().isPrimitive()) {
            return obj.toString().isEmpty();
        } else if (obj.getClass().isArray()) {
            Object[] dummy = (Object[]) obj;
            return dummy.length <= 0;
        } else {
            Class<?>[] interfaces = obj.getClass().getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                System.out.println(interfaces[i].getName());
                if (interfaces[i].equals(List.class)) {
                    Collection<?> col = (Collection<?>) obj;
                    return col.isEmpty();
                }
                if (interfaces[i].equals(Map.class)) {
                    Map<?,?> col = (Map<?,?>) obj;
                    return col.isEmpty();
                }
            }
        }
        return false;
    }

    /**
     * Si el objeto puede convertirse en un Integer devolverá verdadero.
     *
     * @param obj un objet que puede contener dígitos convertibles en Integer
     * @return true si el objeto puede convertirse en un Integer.
     */
    public boolean isInteger(Object obj) {
        if (!isNullOrEmpty(obj)) {
            try {
                Integer.valueOf(String.valueOf(obj));
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    /**
     *
     * @param obj
     * @return
     */
    public boolean isLong(Object obj) {
        if (!isNullOrEmpty(obj)) {
            try {
                Long.valueOf((String) obj);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Si el objeto puede convertirse en un Float devolverá verdadero. En caso
     * contrario devolverá falso.
     *
     * @param obj un objeto que puede contener dígitos convertibles en Float
     * @return true si el objeto puede convertirse en un Double.
     */
    public boolean isFloat(Object obj) {
        if (!isNullOrEmpty(obj)) {
            try {
                Float.valueOf(String.valueOf(obj));
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Si el objeto puede convertirse en un Double devolverá verdadero. En caso
     * contrario devolverá falso.
     *
     * @param obj un objeto que puede contener dígitos convertibles en Double
     * @return true si el objeto puede convertirse en un Double.
     */
    public boolean isDouble(Object obj) {
        if (!isNullOrEmpty(obj)) {
            try {
                Double.valueOf(String.valueOf(obj));
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Indica si el objeto pasado es un array. En caso contrario devolverá false
     *
     * @param obj el objeto que puede ser un array.
     * @return true si el objeto es un array.
     */
    public static boolean isArray(Object obj) {
        if (isNullOrEmpty(obj)) {
            return false;
        } else {
            return obj.getClass().isArray();
        }
    }

    /**
     * Comprueba si el objeto pasado es del tipo que le pasamos. Devolverá true
     * si lo es y false en caso contrario.
     *
     * @param obj el objeto a comprobar si es el de tipo indicado.
     * @param clazz el tipo que vamos a comprobar en el objeto
     * @return true si el objeto es del tipo indicado y false en caso contrario.
     */
    public static boolean isFromType(Object obj, Class<?> clazz) {
        if (isNullOrEmpty(obj)) {
            return false;
        } else {
            if (isNullOrEmpty(clazz)) {
                String msg = "ERROR: Argumento clazz no puede ser nulo.";
                if (LOG.isLoggable(Level.SEVERE)) {
                    LOG.log(Level.SEVERE, msg);
                }
                throw new IllegalArgumentException(msg);
            } else {
                return obj.getClass().isAssignableFrom(clazz);
            }
        }
    }
}
