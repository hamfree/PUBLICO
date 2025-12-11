package es.nom.juanfranciscoruiz.utiles.exceptions;

/**
 * Thrown to indicate that the TypeConverter class has attempted to extract
 * from a string a Long or Double object, but that the string does not have the
 * appropriate format.
 *
 * @author juanf
 */
public class TypeConverterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for TypeConverterException
     */
    public TypeConverterException() {
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message
     */
    public TypeConverterException(String message) {
        super(message);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message
     * @param cause
     */
    public TypeConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param cause
     */
    public TypeConverterException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public TypeConverterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
