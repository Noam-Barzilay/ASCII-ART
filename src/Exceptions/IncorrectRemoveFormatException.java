package Exceptions;

/**
 * Custom exception for an incorrect format in the "remove" command.
 */
public class IncorrectRemoveFormatException extends Exception {
    /**
     * Constructs an IncorrectRemoveFormatException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public IncorrectRemoveFormatException(String msg) {
        super(msg);
    }
}
