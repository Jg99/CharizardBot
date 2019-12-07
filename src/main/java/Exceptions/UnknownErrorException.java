package Exceptions;

/**
 * Thrown if an unknown error occurs
 */
@SuppressWarnings("all")
public class UnknownErrorException extends ClashException {

    public UnknownErrorException(String message) {
        super(message);
    }
}
