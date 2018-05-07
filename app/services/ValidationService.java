package services;

import models.Identification;

/**
 * @author Paule
 * 
 * Service for validation of data
 */
public class ValidationService {
	
	/**
	 * Validates a Identifications
	 * @param identification
	 * @return true, if valid
	 */
	public boolean validateIdentification(Identification identification) {
		
		if (identification != null && identification.getId() != null  && identification.getName() != null 
				&& identification.getWaitingTime() != null && identification.getCompanyId() != null) {
			return true;
		}
		return false;
		
	}

}
