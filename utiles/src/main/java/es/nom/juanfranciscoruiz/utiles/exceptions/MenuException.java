package es.nom.juanfranciscoruiz.utiles.exceptions;

/**
 * Customized Exception class for Menu class
 *
 * @author Juan F. Ruiz
 */
public class MenuException extends Exception {

    /**
     * Constructor for MenuException
     */
    public MenuException() {
    }

    /**
     * Constructor for MenuException
     *
     * @param message the detail message. The detail message is saved for later
     */
    public MenuException(String message) {
        super(message);
    }

    /**
     * Constructor for MenuException
     *
     * @param message the detail message. The detail message is saved for later
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *              (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public MenuException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for MenuException
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *              (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public MenuException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor for MenuException
     *
     * @param message the detail message. The detail message is saved for later
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *              (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    public MenuException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Overrides the initCause() method from Exception Class
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @return a Throwable with the specified cause
     */
    @Override
    public synchronized Throwable initCause(Throwable cause) {
        return super.initCause(cause);
    }

    /**
     * Overrides the getCause() method from Exception Class
     *
     * @return a Throwable with the specified cause
     */
    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    /**
     * Overrides the getMessage() method from Exception Class
     *
     * @return the detail message string of this throwable
     */
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    /**
     * Overrides the getMessage() method from Exception Class
     *
     * @return the detail message string of this throwable
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
