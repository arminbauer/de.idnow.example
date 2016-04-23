package services.exceptions;

/**
 * Created by ebajrami on 4/23/16.
 */
public class InvalidCompanyException extends Exception {
    public InvalidCompanyException() {
    }

    public InvalidCompanyException(String message) {
        super(message);
    }
}
