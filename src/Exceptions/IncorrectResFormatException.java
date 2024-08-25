package Exceptions;

/**
 * Custom exception for an incorrect format in the "res" command.
 */
public class IncorrectResFormatException extends Exception {
    /**
     * Constructs an IncorrectResFormatException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public IncorrectResFormatException(String msg) {
        super(msg);
    }
}
