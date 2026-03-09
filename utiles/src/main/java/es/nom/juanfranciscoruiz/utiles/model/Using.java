package es.nom.juanfranciscoruiz.utiles.model;

/**
 * An enumeration representing different modes or contexts of usage.
 * <p>
 * The `Using` enum defines a set of predefined constants that can be used
 * to specify which approach, standard, or technology is being utilized
 * in a particular context.
 */
public enum Using {
    /**
     * The technology to clear the screen will be a command of Operation System.
     */
    OS,
    /**
     * The technology to clear the scren will use an ANSI escape sequence.
     */
    ANSI,
    /**
     * Represents a mode or technology using standard Java methods or libraries.
     */
    JAVA
}
