package models.exceptions;
public class CompanyNotFoundWithIdException extends Exception {

	private static final long serialVersionUID = -5422473627556194598L;

	public CompanyNotFoundWithIdException(int companyId) {
        super("No company found with id:"+companyId+".");
    }

}