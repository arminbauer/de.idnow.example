package controllers;

import com.fasterxml.jackson.databind.JsonNode;

public class Company {
	final public int id;
	final public String name;
	final public int SLA_time;
	final public double SLA_percetage;
	final public double current_SLA_percentage;
	private static final CompanyFactory COMPANYFACTORYINSTANCE = new CompanyFactory();
	
	
	/** Ensures that all generated Company objects are valid.
	 * 
	 * @author Peter Schnitzler
	 *
	 */
	public static class CompanyFactory {
		private CompanyFactory() {
			
		}		
		
		public Company createCompanyFromJson(JsonNode json) throws DupplicateIdException, InvalidJsonDataException {
			if (!(json.has("id") && json.has("name") && json.has("sla_time") && json.has("sla_percentage") && json.has("current_sla_percentage"))) {
				//one or more data fields are missing
				throw new InvalidJsonDataException();
			}  
			int id = json.get("id").asInt();
			synchronized(DataStorage.getDataStorage().getCompaniesById()) {
				if (DataStorage.getDataStorage().getCompaniesById().containsKey(id))
					//the id of the new company is not unique 
					throw new DupplicateIdException();
			}
			
			return new Company(id, json.get("name").asText(), json.get("sla_time").asInt(), json.get("sla_percentage").asDouble(), json.get("current_sla_percentage").asDouble());

		}
	}
	
	static CompanyFactory getCompanyFactory () {
		return COMPANYFACTORYINSTANCE;				
	}
	
	private Company (int i, String n, int st, double sp, double csp) {
		id = i;
		name = n;
		SLA_time = st;
		SLA_percetage = sp;
		current_SLA_percentage = csp;
	}
}