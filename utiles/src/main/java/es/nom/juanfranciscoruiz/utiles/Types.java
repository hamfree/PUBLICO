package es.nom.juanfranciscoruiz.utiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static es.nom.juanfranciscoruiz.utiles.model.TypeConstants.*;

/**
 * Utility class for type checking and validation.
 *
 * @author Juan F. Ruiz
 */
public class Types {

    /**
     * For debugging.
     */
    private static final Logger logger = LoggerFactory.getLogger(Types.class.getName());

    /**
     * Singleton instance of the {@code Types} class.
     */
    private static final Types INSTANCE = new Types();

    /**
     * A utility class containing methods for type-related operations and checks.
     * This class provides static methods to evaluate object types, ensure type
     * safety, and execute various type validations.
     * <p>
     * The constructor is private to prevent instantiation.
     */
    private Types() {
    }

    /**
     * Returns the singleton instance of the Types class. This method ensures that
     * the same instance is reused across the application, adhering to the singleton
     * design pattern.
     *
     * @return the singleton instance of the Types class.
     */
    public static Types getInstance() {
        return INSTANCE;
    }

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
                logger.debug(IS_NULL);
            }
            return true;
        } else if (obj.getClass().isAssignableFrom(Object.class)) {
            if (logger.isDebugEnabled()) {
                logger.debug(TYPE_OBJECT);
                logger.debug(IS_NULL_ASK + " {}", obj.toString() == null);
            }
            return false;
        } else if (obj.getClass().isAssignableFrom(String.class)) {
            if (logger.isDebugEnabled()) {
                logger.debug(TYPE_STRING);
                logger.debug(IS_EMPTY + " {}", obj.toString().isEmpty());
            }
            return obj.toString().isEmpty();
        } else if (obj.getClass().isPrimitive()) {
            if (logger.isDebugEnabled()) {
                logger.debug(IS_PRIMITIVE);
            }
            return obj.toString().isEmpty();
        } else if (obj.getClass().isArray()) {
            int l = Array.getLength(obj);
            if (logger.isDebugEnabled()) {
                logger.debug(IS_AN_ARRAY);
                logger.debug(IS_EMPTY + " {}", l > 0);
            }
            return l <= 0;
        } else {
            Class<?>[] interfaces = obj.getClass().getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (anInterface.equals(List.class)) {
                    Collection<?> col = (Collection<?>) obj;
                    if (logger.isDebugEnabled()) {
                        logger.debug(TYPE_LIST);
                        logger.debug(IS_EMPTY + " {}", col.isEmpty());
                    }
                    return col.isEmpty();
                }
                if (anInterface.equals(Map.class)) {
                    Map<?, ?> col = (Map<?, ?>) obj;
                    if (logger.isDebugEnabled()) {
                        logger.debug(TYPE_MAP);
                        logger.debug(IS_EMPTY + " {}", col.isEmpty());
                    }
                    return col.isEmpty();
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug(TYPE_OF_OBJ + " {}", obj.getClass().getCanonicalName());
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
     *
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
        return true;
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
        return true;
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
     * @param obj   the object to be checked if it is of the indicated type.
     * @param clazz the type we are going to check in the object
     * @return true if the object is of the indicated type and false otherwise.
     * @throws IllegalArgumentException in case the 'clazz' and/or 'obj'
     *                                  parameters are null.
     */
    public static boolean isFromType(Object obj, Class<?> clazz)
            throws IllegalArgumentException {
        if (isNullOrEmpty(obj)) {
            if (logger.isErrorEnabled()) {
                logger.error(ERR_ARG);
            }
            throw new IllegalArgumentException(ERR_ARG);
        } else {
            if (isNullOrEmpty(clazz)) {
                if (logger.isErrorEnabled()) {
                    logger.error(ERR_CLAZZ);
                }
                throw new IllegalArgumentException(ERR_CLAZZ);
            } else {
                return obj.getClass().isAssignableFrom(clazz);
            }
        }
    }
}
