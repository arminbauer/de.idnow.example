package services.exceptions;

/**
 * Base exception for client initated bad request (missing params, invalid references, etc)
 * Created by ebajrami on 4/23/16.
 */
public class BadRequestException extends Exception {
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}
