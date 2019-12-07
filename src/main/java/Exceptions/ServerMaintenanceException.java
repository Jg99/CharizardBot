package Exceptions;

/**
 * Thrown if an unknown error occurs
 */
@SuppressWarnings("all")
public class ServerMaintenanceException extends ClashException {

    public ServerMaintenanceException(String message) {
		super(message);
	}
}
