package Exceptions;

/**
 * Custom exception for exceeding a specified value.
 */
public class ExceedingValueException extends Exception {
    /**
     * Constructs an ExceedingValueException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public ExceedingValueException(String msg) {
        super(msg);
    }
}
