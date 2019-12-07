package Exceptions;

/**
 * Parent class for all exceptions related to the clash API
 */
@SuppressWarnings("all")
public class ClashException extends Exception {

    public ClashException(String message) {
        super(message);
    }
}
