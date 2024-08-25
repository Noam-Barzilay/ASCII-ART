package Exceptions;

/**
 * Custom exception for an empty character set.
 */
public class EmptySetException extends Exception {
    /**
     * Constructs an EmptySetException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public EmptySetException(String msg) {
        super(msg);
    }
}
