package Exceptions;

/**
 * Custom exception for an incorrect format in the "add" command.
 */
public class IncorrectAddFormatException extends Exception {
    /**
     * Constructs an IncorrectAddFormatException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public IncorrectAddFormatException(String msg) {
        super(msg);
    }
}
