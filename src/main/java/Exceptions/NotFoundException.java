package Exceptions;

/**
 * Thrown when the URL is invalid
 */
@SuppressWarnings("all")
public class NotFoundException extends ClashException {

    public NotFoundException(String message) {
        
        super(message);
    }
}
