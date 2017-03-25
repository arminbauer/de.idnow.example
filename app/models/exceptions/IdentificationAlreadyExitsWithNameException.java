package models.exceptions;
public class IdentificationAlreadyExitsWithNameException extends Exception {

	private static final long serialVersionUID = -5422473627556194598L;

	public IdentificationAlreadyExitsWithNameException(String name) {
        super("Identification already exits with name"+name+".");
    }

}