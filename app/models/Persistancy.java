package models;

public interface Persistancy {

	public void storePendingIdentifications(PendingIdentifications o);
	public PendingIdentifications fetchPendingIdentifications();
	public KnownCompanies fetchKnowCompanies();
	public void storeKnownCompanies(KnownCompanies knownCompanies);
}
