package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;

/**Contains Identification Information. 
 * 
 * @author Peter Schnitzler
 *
 */
public class Ident implements Comparable<Ident>{
	
	/** Contains all primitive data types (plus a string) for easier JSON conversion.
	 * 
	 * @author Peter Schnitzler
	 *
	 */
	public class IdentData {
		public final String name;
		public final long startTime; 
		public final long waitingTime;
		public final int id;
		public final int companyId;

		private IdentData( String n, long st, long wt, int i, int ci) {
			name = n;
			startTime = st;
			waitingTime = wt;
			id = i;
			companyId = ci;
		}
	}
	
	/** Prevents the creation of invalid identifications. 
	 *
	 */
	public static class IdentFactory {
		private IdentFactory() {
			
		}
		
		public Ident createIdentFromJson(JsonNode json) throws DupplicateIdException, InvalidJsonDataException {
			if (!(json.has("id") && json.has("name") && json.has("time") && json.has("waiting_time") && json.has("companyid"))) {
				//one or more data fields are missing
				throw new InvalidJsonDataException();
			}  
			
			DataStorage dataSingelton = DataStorage.getDataStorage();
			
	    	int companyId = json.get("companyid").asInt();
	    	Company parentCompany;
	    	
	    	synchronized (dataSingelton.getCompaniesById()) {
	    		// only Identifications for known companies are valid
	    		if (!dataSingelton.getCompaniesById().containsKey(companyId))
	    			throw new DupplicateIdException();
				parentCompany = dataSingelton.getCompaniesById().get(companyId);
			}
	    	// Identification Id must be unique
	    	int identId = json.get("id").asInt();
	    	synchronized (dataSingelton.getAllIdentsSorted()) {
	    		if (dataSingelton.getUsedIdents().contains(identId))
	    			throw new DupplicateIdException();
	    	}
	    	return new Ident(parentCompany, json.get("name").asText(), json.get("time").asLong(), json.get("waiting_time").asLong(), identId);
		}
	}
	
	final private IdentData identData;
	private JsonNode json = null;
	private final Company company;
	
	private final static IdentFactory FACTORY = new IdentFactory();
	
	
	private Ident(Company c, String n, long st, long wt, int i) {
		identData = new IdentData (n, st, wt, i, c.id);	
		company = c;
	}
	
	/** Heuristic to estimate the urgency of the identification request. 
	 * 
	 */
	private double getWaitBudget() {
		//as the time to SLA violation and the SLA violation budget approaches zero the wait budget
		//decreases thereby giving this identification a higher priority.
		
		double waitingTimeLeft = company.SLA_time - identData.waitingTime;
		double slaPercentageLeft = company.current_SLA_percentage - company.SLA_percetage;
		
		if (waitingTimeLeft <= 0 || slaPercentageLeft <= 0)
			//process this identification ASAP
			return Long.MIN_VALUE;
		
		return waitingTimeLeft * slaPercentageLeft;
	}
	

	@Override
	/** Sorted by the remaining time to SLA violation weighted by remaining SLA violation budget.
	*	
	*	Id used as tie breaker.
	*/
	public int compareTo(Ident other) {
		int result = Double.compare(getWaitBudget(), other.getWaitBudget());
		if (result != 0)
			return result;		
		
		return identData.id - other.identData.id;		
	}
	
	@Override 
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		
		if (other == null) {
			return false;
		}
		
		if (!(other instanceof Ident)) {
			return false;
		}
		
		if (getId() == ((Ident)other).getId()) {
			return true;
		}
		
		return false;
	}
	
	/** Returns the data of this identification as a JSON node.
	 */
	public JsonNode toJson() {
		if (json == null) {
			//json conversion is done lazily. 
			synchronized (this) {				
				if (json == null) {
					json = Json.toJson(identData);
				}
			}
		}
		return json;
	}
	
	public long getWaitingTime() {
		return identData.waitingTime;
	}

	public int getId() {
		return identData.id;
	}

	public long getStartTime() {
		return identData.startTime;
	}

	public String getName() {
		return identData.name;
	}

	public Company getCompany() {
		return company;
	}


	public static IdentFactory getFactory() {
		return FACTORY;
	}
	
}