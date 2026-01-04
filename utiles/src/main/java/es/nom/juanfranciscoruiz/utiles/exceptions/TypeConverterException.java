package es.nom.juanfranciscoruiz.utiles.exceptions;

import java.io.Serial;

/**
 * Thrown to indicate that the TypeConverter class has attempted to extract
 * from a string a Long or Double object, but that the string does not have the
 * appropriate format.
 *
 * @author juanf
 */
public class TypeConverterException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for TypeConverterException
     */
    public TypeConverterException() {
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message the error message
     */
    public TypeConverterException(String message) {
        super(message);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message the error message
     * @param cause   the cause
     */
    public TypeConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param cause the cause
     */
    public TypeConverterException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message            the error message
     * @param cause              the cause
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public TypeConverterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
