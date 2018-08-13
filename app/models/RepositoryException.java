package models;

/***
 * This exception represents errors on the repository level.
 * 
 * @author leonardo.cruz
 *
 */
public class RepositoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RepositoryException(String msg) {
		super(msg);
		
	}
	
}
