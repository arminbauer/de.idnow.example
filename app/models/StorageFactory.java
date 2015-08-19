package models;

public class StorageFactory {

	private static Persistancy db = new InMemoryPersistancy();
	
	public static Persistancy getPersistancy() {
		return db;
	}
}

class InMemoryPersistancy implements Persistancy {

	private PendingIdentifications pendingidents = new PendingIdentifications();
	private KnownCompanies knownCompanies = new KnownCompanies();
	
	@Override
	public void storePendingIdentifications(PendingIdentifications i) {
		pendingidents = i;
	}

	@Override
	public PendingIdentifications fetchPendingIdentifications() {
		return pendingidents;
	}

	@Override
	public KnownCompanies fetchKnowCompanies() {
		return knownCompanies;
	}

	@Override
	public void storeKnownCompanies(KnownCompanies known) {
		knownCompanies = known;
		
	}
}
