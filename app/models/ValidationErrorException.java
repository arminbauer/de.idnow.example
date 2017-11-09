package models;

/**
 * Created by Florian Schmidt on 07.11.2017.
 */
public class ValidationErrorException extends Exception {
    public ValidationErrorException(String message) {
        super(message);
    }
}
