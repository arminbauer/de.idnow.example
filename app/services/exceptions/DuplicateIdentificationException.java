package services.exceptions;

/**
 * Thrown when identification is already defined. Cannot add two identifications with same ID
 * Created by ebajrami on 4/23/16.
 */
public class DuplicateIdentificationException extends BadRequestException {

    public DuplicateIdentificationException(String s) {
        super(s);
    }
}
