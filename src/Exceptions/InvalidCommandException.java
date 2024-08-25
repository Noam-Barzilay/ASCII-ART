package Exceptions;

/**
 * Custom exception for an invalid command.
 */
public class InvalidCommandException extends Exception {
    /**
     * Constructs an InvalidCommandException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public InvalidCommandException(String msg) {
        super(msg);
    }
}
