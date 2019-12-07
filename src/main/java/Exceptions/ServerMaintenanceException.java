package Exceptions;

/**
 * Thrown if an unknown error occurs
 */
public class ServerMaintenanceException extends ClashException {

    public ServerMaintenanceException(String message) {
		super(message);
	}
}
