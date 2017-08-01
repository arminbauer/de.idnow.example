package manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mapper.IdentificationMapper;
import models.IdentificationDTO;
import models.Identification;

/**
 * Adds new identifications to an list and retrieves a by priority sorted list
 * of all open identifications.
 *
 * @author Markus Panholzer <markus.panholzer@eforce21.com>
 * @since 31.09.2017
 */
public class IdentificationManagerImpl implements IdentificationManager {

	/** List to store all pending identification requests. */
	private final List<Identification> requests;

	public IdentificationManagerImpl() {
		this.requests = new ArrayList<Identification>();
	}

	/**
	 * {@inheritDoc }
	 */
	public void addIdentificationRequest(Identification request) {
		requests.add(request);
	}

	/**
	 * {@inheritDoc }
	 */
	public List<IdentificationDTO> getIdentificationList() {
		List<IdentificationDTO> identifications = new ArrayList<IdentificationDTO>();

		Collections.sort(requests);
		// Add to identification list reverse to preserve the order
		for (int i = requests.size() - 1; i >= 0; i--) {
			identifications.add(IdentificationMapper.map(requests.get(i)));
		}
		return identifications;
	}
}
