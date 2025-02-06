package es.nom.juanfranciscoruiz.utiles;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for type checking
 *
 * @author hamfree
 */
public class Types {

    private static final Logger logger = LoggerFactory.getLogger(Types.class.getName());

    /**
     * Indicates whether the passed object is null or empty (in the case of a
     * collection, or other object containing objects.
     *
     * @param obj the object to be checked
     * @return true if the object is null or empty, false otherwise.
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Is null");
            }
            return true;
        } else if (obj.getClass().isAssignableFrom(Object.class)) {
            if (logger.isDebugEnabled()) {
                logger.debug("It is of type Object");
                logger.debug("Is its value null? {}", String.valueOf(Objects.isNull(obj)));
            }
            return Objects.isNull(obj);
        } else if (obj.getClass().isAssignableFrom(String.class)) {
            if (logger.isDebugEnabled()) {
                logger.debug("It is of type String");
                logger.debug("Is it empty? {}",String.valueOf(obj.toString().isEmpty()));
            }
            return obj.toString().isEmpty();
        } else if (obj.getClass().isPrimitive()) {
            if (logger.isDebugEnabled()) {
                logger.debug("It is a primitive value");
            }
            return obj.toString().isEmpty();
        } else if (obj.getClass().isArray()) {
            int l = Array.getLength(obj);
            if (logger.isDebugEnabled()) {
                logger.debug("It is an array");
                logger.debug("Is it empty? {}", String.valueOf(l > 0));
            }
            return l <= 0;
        } else {
            Class<?>[] interfaces = obj.getClass().getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                if (interfaces[i].equals(List.class)) {
                    Collection<?> col = (Collection<?>) obj;
                    if (logger.isDebugEnabled()) {
                        logger.debug("Implements the List interface");
                        logger.debug("Is it empty? {}", String.valueOf(col.isEmpty()));
                    }
                    return col.isEmpty();
                }
                if (interfaces[i].equals(Map.class)) {
                    Map<?, ?> col = (Map<?, ?>) obj;
                    if (logger.isDebugEnabled()) {
                        logger.debug("Implements the Map interface");
                        logger.debug("Is it empty? {}", String.valueOf(col.isEmpty()));
                    }
                    return col.isEmpty();
                }
            }
        }
        if (logger.isDebugEnabled()){
            logger.debug("Type of the object passed: {}", obj.getClass().getCanonicalName());
        }
        return false;
    }

    /**
     * If the object can be converted to an Integer it will return true.
     *
     * @param obj an object that can hold digits convertible to Integer
     * @return true if the object can be converted to an Integer and false 
     * otherwise.
     */
    public static boolean isInteger(Object obj) {
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
     * If the object can be converted to a Long it will return true.
     * @param obj an object that can hold digits convertible to Long
     * @return true if the object can be converted to a Long and false 
     * otherwise.
     */
    public static boolean isLong(Object obj) {
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
     * If the object can be converted to a Float it will return true. Otherwise 
     * it will return false.
     *
     * @param obj an object that can hold digits convertible to Float
     * @return true if the object can be converted to a Double or false 
     * otherwise.
     */
    public static boolean isFloat(Object obj) {
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
     * If the object can be converted to a Double it will return true. Otherwise 
     * it will return false.
     *
     * @param obj an object that can hold digits convertible to Double
     * @return true if the object can be converted to a Double or false 
     * otherwise.
     */
    public static boolean isDouble(Object obj) {
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
     * Indicates whether the passed object is an array. Otherwise it will 
     * return false.
     *
     * @param obj the object we are going to check if it is an array.
     * @return true if the object is an array or false otherwise.
     */
    public static boolean isArray(Object obj) {
        if (isNullOrEmpty(obj)) {
            return false;
        } else {
            return obj.getClass().isArray();
        }
    }

    /**
     * Checks if the passed object is of the type passed to it. It will return 
     * true if it is and false otherwise.
     *
     * @param obj the object to be checked if it is of the indicated type.
     * @param clazz the type we are going to check in the object
     * @return true if the object is of the indicated type and false otherwise.
     * @throws IllegalArgumentException in case the 'clazz' and/or 'obj' 
     * parameters are null.
     */
    public static boolean isFromType(Object obj, Class<?> clazz)  
            throws IllegalArgumentException {
        if (isNullOrEmpty(obj)) {
            String msg = "ERROR: Argument 'obj' cannot be null.";
                if (logger.isErrorEnabled()) {
                    logger.error(msg);
                }
                throw new IllegalArgumentException(msg);
        } else {
            if (isNullOrEmpty(clazz)) {
                String msg = "ERROR: Argument 'clazz' cannot be null.";
                if (logger.isErrorEnabled()) {
                    logger.error(msg);
                }
                throw new IllegalArgumentException(msg);
            } else {
                return obj.getClass().isAssignableFrom(clazz);
            }
        }
    }
}
