package Exceptions;

/**
 * Thrown if an unknown error occurs
 */
public class UnknownErrorException extends ClashException {

    public UnknownErrorException(String message) {
        super(message);
    }
}
