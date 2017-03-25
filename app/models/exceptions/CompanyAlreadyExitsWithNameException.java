package models.exceptions;
public class CompanyAlreadyExitsWithNameException extends Exception {

	private static final long serialVersionUID = -5422473627556194598L;

	public CompanyAlreadyExitsWithNameException(String name) {
        super("Identification already exits with name"+name+".");
    }

}