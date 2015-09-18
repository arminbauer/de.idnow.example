package controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/** Singleton class used to store the persistent Data of the Rest Controller 
 * 
 * @author Peter Schnitzler
 *
 */
public class DataStorage {	
	final private HashMap<Integer, Company> companiesById = new HashMap<Integer, Company>();
	final private TreeSet<Ident> allIdentsSorted = new TreeSet<Ident>();
	final private HashSet<Integer> usedIdents = new HashSet<Integer>();
	
	static private final DataStorage DATASTORAGEOBJECT  = new DataStorage();
	
	private DataStorage() {
		
	}

	/** Returns a Map of all companies accessible by company Id.
	 */
	public Map<Integer, Company> getCompaniesById() {
		return companiesById;
	}

	/** Returns a set of all Identifications sorted by urgency.
	 * 
	 */
	public Set<Ident> getAllIdentsSorted() {
		return allIdentsSorted;
	}

	/** Returns a Set of all used identification ids. 
	 * 
	 * No new identifications that use an id already in this set will be created.
	 * 
	 */
	public Set<Integer> getUsedIdents() {
		return usedIdents;
	}
	
	public static DataStorage getDataStorage() {
		return DATASTORAGEOBJECT;
	}

}
