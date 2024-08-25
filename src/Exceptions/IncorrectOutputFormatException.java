package Exceptions;

/**
 * Custom exception for an incorrect format in the "output" command.
 */
public class IncorrectOutputFormatException extends Exception {
    /**
     * Constructs an IncorrectOutputFormatException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public IncorrectOutputFormatException(String msg) {
        super(msg);
    }
}
