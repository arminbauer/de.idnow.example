package models;

import java.util.ArrayList;
import java.util.List;

public class PendingIdentifications {

    private List<Identification> pendingIdentifications;

    public PendingIdentifications() {
    	pendingIdentifications = new ArrayList<Identification>();
    }

    public boolean addIdentification(Identification ident) {
    	return pendingIdentifications.add(ident);
    }

	public List<Integer> getAllCompanyIds() {
		List<Integer> ids = new ArrayList<Integer>();
		
		for (Identification ident: pendingIdentifications) {
			ids.add(ident.getId());
		}
		return ids;
	}

	public List<Identification> getIdents() {
		return pendingIdentifications;		
	}
}