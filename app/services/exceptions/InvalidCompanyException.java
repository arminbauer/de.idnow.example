package services.exceptions;

/**
 * Thrown when company related data is invalid.
 * Created by ebajrami on 4/23/16.
 */
public class InvalidCompanyException extends BadRequestException {
    public InvalidCompanyException() {
    }

    public InvalidCompanyException(String message) {
        super(message);
    }
}
