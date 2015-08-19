package sorters;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import models.Identification;
import models.PendingIdentifications;

public class PriorityList {
	private Map<Identification, Integer> list = new HashMap<Identification, Integer>();
	
	public PriorityList(PendingIdentifications pendingIdents, int initialPriority) {
		for (Identification ident: pendingIdents.getIdents()) {
			list.put(ident, initialPriority);
		}
	}

	public Set<Identification> toOrderedList(PendingIdentifications pendingIdents) {
		//TODO: go through the priority map and return a list ordered by the priority
		return list.keySet();
	}

}
