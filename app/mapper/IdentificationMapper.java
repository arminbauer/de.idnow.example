package mapper;

import models.IdentificationDTO;
import play.Logger;
import play.Logger.ALogger;
import util.Calculator;
import models.Company;
import models.Identification;

/**
 * Maps between Identifications and Identification Data Transfer Objects.
 *
 * @author Markus Panholzer <markus.panholzer@eforce21.com>
 * @since 31.09.2017
 */
public class IdentificationMapper {

	/** The logger. */
	private static final ALogger log = Logger.of(IdentificationMapper.class);

	/**
	 * Maps an identification DTO object to an identification object.
	 * 
	 * @param identification
	 *            the object to map.
	 * @return request the mapped object.
	 */
	public static Identification map(IdentificationDTO identification) {
		Identification request = new Identification();

		Company company = Company.find(identification.getCompanyId());
		if (company != null) {
			request.setId(identification.getId());
			request.setName(identification.getName());
			request.setTime(identification.getTime());
			request.setWaitingTime(identification.getWaitingTime());
			request.setCompany(company);
			request.setPriority(Calculator.calculatePriority(request));
		} else {
			log.warn("Company was null for identification: " + identification.toString()
					+ ". Identification will be discarded.");
			// Return null to discard the identification.
			return null;
		}
		return request;
	}

	/**
	 * Maps an identification object to an identification DTO object.
	 * 
	 * @param request
	 *            the object to map.
	 * @return identification the mapped object.
	 */
	public static IdentificationDTO map(Identification request) {
		IdentificationDTO identification = new IdentificationDTO();

		identification.setId(request.getId());
		identification.setName(request.getName());
		identification.setTime(request.getTime());
		identification.setWaitingTime(request.getWaitingTime());
		identification.setCompanyId(request.getCompany().getId());

		return identification;
	}

}
