package Exceptions;
@SuppressWarnings("all")
public class BadRequestException extends ClashException {

    public BadRequestException(String message) {
        super(message);
    }
}
