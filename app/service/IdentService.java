package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DatabaseClass;
import dto.Company;
import dto.Identification;

/**
 * Service implementation which contains business logic to perform Identification job operations
 * 
 * @author chanchal.nerkar
 *
 */
public class IdentService {
	
	private Map<Long, Company> companies = DatabaseClass.getCompanies();
	private List<IdentPojo> identifications = DatabaseClass.getIdentifications();
	
	/**
	 * Method to add company
	 * 
	 * @param company
	 */
	public void addCompany(Company company){
		companies.put(company.getId(), company);
	}
	
	
	/**
	 * Method to add Identification job
	 * 
	 * @param ident
	 * @throws Exception
	 */
	public void addIdentification(Identification ident) throws Exception{
		if(companies.keySet().contains(ident.getCompanyId())){
			identifications.add(map(ident));
		}else{
			throw new Exception("Company not found for with ID: "+ident.getCompanyId());
		}
	}
	
	/**
	 * Method to retrieve Identification jobs.  List of Identification jobs will be sorted as per SLA urgency.
	 * 
	 * @return sorted Identification jobs
	 */
	public List<Identification> getIdentifications(){
		identifications.sort(new IdentOptimizer());
		List<Identification> identList = new ArrayList<Identification>(identifications.size());
		for (IdentPojo identPojo : identifications) {
			identList.add(map(identPojo));
		}
		return identList;
	}
		
	/**
	 * Utility Method to map frontend identification dto to backend pojo
	 *  
	 * @param identification
	 * @return
	 */
	public static IdentPojo map(Identification identification){
		IdentPojo identPojo = new IdentPojo();
		Company company = DatabaseClass.getCompanies().get(identification.getCompanyId());
		identPojo.setId(identification.getId());
		identPojo.setName(identification.getName());
		identPojo.setTime(identification.getTime());
		identPojo.setWaitingTime(identification.getWaitingTime());
		identPojo.setCompanyId(identification.getCompanyId());
		
		// Additional fields for sorting algorithm
		identPojo.setSlaPercentageDelta(company.getCurrSlaPercentage() - company.getSlaPercentage());
		identPojo.setRemainingServiceTime(company.getSlaTime() - identification.getWaitingTime());
		
		return identPojo;
	}
	
	/**
	 * Utility Method to map backend pojo to frontend identification dto  
	 * 
	 * @param identPojo
	 * @return
	 */
	public static Identification map(IdentPojo identPojo){
		Identification identification = new Identification();
		identification.setId(identPojo.getId());
		identification.setCompanyId(identPojo.getCompanyId());
		identification.setName(identPojo.getName());
		identification.setTime(identPojo.getTime());
		identification.setWaitingTime(identPojo.getWaitingTime());
		return identification;
	}

}
