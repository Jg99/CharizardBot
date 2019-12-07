package Exceptions;

/**
 * Thrown when token is invalid or ip address isn't registered
 */
public class AuthenticationException extends ClashException {

    public AuthenticationException(String message) {
        super(message);
    }
}