package database;

import java.util.*;
import java.util.stream.Collectors;

import service.IdentificationComparator;
import model.Company;
import model.Identification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Repository {
	
	private static Map<Integer, Company> companies = new HashMap<Integer, Company>();
	private static List<Identification> identifications = new ArrayList<>();
	private static boolean isSorted = false;
	private static Logger logger = LoggerFactory.getLogger(Repository.class);

	
	public boolean addIdentification(Identification identification) {

		try {
			if(!identification.validData()) {
				logger.error("Incorrect data in Identification : " + identification);
				return false;
			}


			if(getCompanybyID(identification.getCompanyId()) != null) {
				if(!identifications.contains(identification)) {
					identifications.add(identification);
					isSorted = false;
					logger.info("Added Identification : " + identification);
					return true;
				}
				else {
					logger.info("Already Identification is present: " + identification);
					return true;
				}
			}
			else {
				logger.error("Company does not exist for Identification : " + identification);
			}

		} catch (NullPointerException e) {
			logger.error("Incorrect data exception : " + identification);
		}

		return false;
	}

	/**If the same company then repalce it  **/
	public boolean addCompany(Company company) {

		try {
			if (!company.validData()) {
				logger.error("Incorrect data" + company);
				return false;
			}

			companies.put(company.getId(), company);
			logger.info("Added company : " + company);
			return true;
		}catch (NullPointerException nullPtr) {
			logger.error("Company data exception : " + company);
			return false;
		}
	}
	public List<Company> getAllCompanies(){

		return companies.values().stream()
				.collect(Collectors.toList());
    }
	
	public Company getCompanybyID(Integer id) {
		return companies.get(id);
	}

	/**
	 * Sort the list only if new identifications are added to the list
	 *
	 * @return List of identification sorted based on priority
	 */

	public List<Identification> getPendingIdentifications() {
		if(!isSorted) {
			identifications.sort(new IdentificationComparator());
			isSorted = true;
		}
		return identifications;		
	}

	public void reset()
	{
		companies.clear();
		identifications.clear();
	}

}
