package Exceptions;

/**
 * Parent class for all exceptions related to the clash API
 */
public class ClashException extends Exception {

    public ClashException(String message) {
        super(message);
    }
}
