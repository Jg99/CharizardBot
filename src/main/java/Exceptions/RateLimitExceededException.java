package Exceptions;

/**
 * Thrown when rate limit has been exceeded.
 */
@SuppressWarnings("all")
public class RateLimitExceededException extends ClashException {

    public RateLimitExceededException(String message) {
        super(message);
    }
}
