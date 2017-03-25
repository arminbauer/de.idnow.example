package models.exceptions;
public class CompanyAlreadyExitsWithIdException extends Exception {

	private static final long serialVersionUID = -5422473627556194598L;

	public CompanyAlreadyExitsWithIdException(int id) {
        super("Identification already exits with id:"+id+".");
    }

}