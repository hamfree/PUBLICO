package es.nom.juanfranciscoruiz.utiles.exceptions;

import java.io.Serial;

/**
 * Thrown to indicate that the TypeConverter class has attempted to extract
 * from a string a Long or Double object, but that the string does not have the
 * appropriate format.
 *
 * @author Juan F. Ruiz
 */
public class TypeConverterException extends RuntimeException {

    /**
     * Serial version UID for serialization purposes.
     */
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
     * @param message the message to be displayed when the exception is thrown.
     */
    public TypeConverterException(String message) {
        super(message);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message the message to be displayed when the exception is thrown.
     * @param cause   the cause of the exception.
     */
    public TypeConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param cause the cause of the exception.
     */
    public TypeConverterException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message            the message to be displayed when the exception is thrown.
     * @param cause              the cause of the exception.
     * @param enableSuppression  boolean to indicate if suppression is enabled
     * @param writableStackTrace boolean to indicate if stack trace is writable
     */
    public TypeConverterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
