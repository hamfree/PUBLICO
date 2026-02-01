package es.nom.juanfranciscoruiz.ansiterm.exceptions;

import java.io.Serial;

/**
 * Represents a custom exception for errors related to ANSI terminal operations.
 * This exception serves as a specialized subclass of {@code Exception} and is
 * designed to handle terminal-specific error scenarios.
 * <p>
 * The class provides multiple constructors to accommodate different use cases such as:
 * - Default exception with no detail message or cause.
 * - Exception with a custom detail message.
 * - Exception with both a detail message and a cause.
 * - Exception with only a cause.
 * - Exception with advanced options like enabling suppression and writable stack trace.
 *
 * @author Juan F. Ruiz
 */
public class ANSITermException extends Exception {

    @Serial
    private static final long serialVersionUID = 1396910236421704294L;

    /**
     * Constructs a new ANSITermException with {@code null} as its detail message.
     * The cause is not initialized and may be subsequently initialized by a
     * call to {@link Throwable#initCause(Throwable)}.
     */
    public ANSITermException() {
        super();
    }

    /**
     * Constructs a new ANSITermException with the specified detail message.
     * The cause is not initialized and may be subsequently initialized by a
     * call to {@link Throwable#initCause(Throwable)}.
     * @param message the detailed message explaining the reason for the exception
     */
    public ANSITermException(String message) {
        super(message);
    }

    /**
     * Constructs a new ANSITermException with the specified detail message and cause.
     * <p>
     * This constructor allows for providing a custom error message and a {@code Throwable}
     * cause, enabling the chaining of exceptions for better error tracking.
     *
     * @param message the detailed message explaining the reason for the exception
     * @param cause the underlying cause of the exception (can be {@code null})
     */
    public ANSITermException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ANSITermException with the specified cause.
     * The detail message is automatically constructed as {@code (cause == null ? null : cause.toString())},
     * which typically contains the class and detail message of the cause.
     * <p>
     * This constructor allows for exception chaining, facilitating the propagation
     * of the original cause of the error for enhanced debugging and error reporting.
     *
     * @param cause the underlying cause of the exception (can be {@code null})
     */
    public ANSITermException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new ANSITermException with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     * <p>
     * This constructor provides advanced options for initializing an exception with precise control
     * over its properties. It is typically used by subclasses or in scenarios where specific
     * exception behavior is required.
     *
     * @param message the detailed message explaining the reason for the exception (can be {@code null})
     * @param cause the underlying cause of the exception (can be {@code null})
     * @param enableSuppression whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    protected ANSITermException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
