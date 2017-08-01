package manager;

import java.util.List;

import models.Identification;
import models.IdentificationDTO;

/**
 * Interface for identification management.
 *
 * @author Markus Panholzer <markus.panholzer@eforce21.com>
 * @since 31.09.2017
 */
public interface IdentificationManager {

	/**
	 * Adds an giver identification to the managed identification list.
	 * 
	 * @param request
	 *            the requested identification.
	 * @return Result the http response message.
	 */
	void addIdentificationRequest(Identification request);

	/**
	 * Retrieves the pending identifications list sorted by priority.
	 * 
	 * @return Result the http response message.
	 */
	List<IdentificationDTO> getIdentificationList();

}
