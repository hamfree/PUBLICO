package es.nom.juanfranciscoruiz.utiles.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Thrown to indicate that the TypeConverter class has attempted to extract 
 * from a string a Long or Double object, but that the string does not have the 
 * appropriate format.
 * 
 * @author juanf
 */
public class TypeConverterException extends RuntimeException {

    public TypeConverterException() {
    }

    public TypeConverterException(String message) {
        super(message);
    }

    public TypeConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeConverterException(Throwable cause) {
        super(cause);
    }

    public TypeConverterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
}
