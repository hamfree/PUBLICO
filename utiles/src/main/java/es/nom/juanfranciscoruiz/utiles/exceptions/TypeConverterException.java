package es.nom.juanfranciscoruiz.utiles.exceptions;

/**
 * Thrown to indicate that the TypeConverter class has attempted to extract
 * from a string a Long or Double object, but that the string does not have the
 * appropriate format.
 * @author Juan F. Ruiz
 */
public class TypeConverterException extends RuntimeException {
    /**
     * Generated serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for TypeConverterException
     */
    public TypeConverterException() {
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message the detail message. The detail message is saved for later
     */
    public TypeConverterException(String message) {
        super(message);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message the detail message. The detail message is saved for later
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *              (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public TypeConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *              (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public TypeConverterException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor for TypeConverterException
     *
     * @param message the detail message. The detail message is saved for later
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *              (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    public TypeConverterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
