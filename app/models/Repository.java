package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import play.Logger;


import models.exceptions.CompanyAlreadyExitsWithIdException;
import models.exceptions.CompanyNotFoundWithIdException;
import models.exceptions.IdentificationAlreadyExitsWithIdException;

public class Repository {
	
	
	private static final float MAX_SLA = 0.9999f;
	private static final float MIN_SLA = 0.0001f;	

	private List<Identification> identifications;
	private List<Company> companies;	
	
	public Repository(){		
		Logger.debug("Repository creation");
		identifications = new ArrayList<Identification>();
		companies = new ArrayList<Company>();			
	}
	

	public Company addCompany(int id, String name, int slaTime, float slaPercentage)
			throws CompanyAlreadyExitsWithIdException {
		if (id < 0)
			throw new IllegalArgumentException("value for id. Must be a positive value.");
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("value for name can not be null or empty.");
		if (slaTime < 0)
			throw new IllegalArgumentException("value for slaTime can not be less than zero.");
		if (!(slaPercentage > MIN_SLA && slaPercentage < MAX_SLA))
			throw new IllegalArgumentException(
					"value for slaPercentage must be between " + MIN_SLA + " and " + MAX_SLA + ".");
		Company company = getCompanyById(id);
		if (company != null)
			throw new CompanyAlreadyExitsWithIdException(id);
		Company newCompany = new Company(id, name, slaTime, slaPercentage);
		companies.add(newCompany);
		return newCompany;
	}

	public Company getCompanyById(int companyId) {
		for (Company company : companies) {
			if (company.getId() == companyId)
				return company;
		}
		return null;
	}

	public Company getCompanyByName(String name) {
		for (Company company : companies) {
			if (company.getName().equals(name))
				return company;
		}
		return null;
	}

	public Identification startIdentification(int id, String name, int companyId)
			throws CompanyNotFoundWithIdException, IdentificationAlreadyExitsWithIdException {
		if (id < 0)
			throw new IllegalArgumentException("value for id. Must be a positive value.");
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("value for name and companyId can not be null or empty.");
		Company company = getCompanyById(companyId);
		if (company == null)
			throw new CompanyNotFoundWithIdException(companyId);
		if (getIdentificationById(id) != null)
			throw new IdentificationAlreadyExitsWithIdException(name);
		Identification Identification = new Identification(id, name, company);
		identifications.add(Identification);
		return Identification;
	}

	public Identification getIdentificationByName(String name) {
		for (Identification identification : identifications) {
			if (identification.getName().equals(name))
				return identification;
		}
		return null;
	}

	public Identification getIdentificationById(int id) {
		for (Identification identification : identifications) {
			if (identification.getId() == id)
				return identification;
		}
		return null;
	}

	public List<Identification> pendingIdentifications() throws CompanyNotFoundWithIdException {
		
		List<Identification> pendingList = new ArrayList<Identification>();		
		List<Company> optimalCompanyList = getOptimalCompanyList();		
		for (Company company : optimalCompanyList) {						
			List<Identification> identifications = computeOptimalListForCompany(company.getId());
			pendingList.addAll(identifications);
		}
		Logger.debug("pendingList --"+pendingList);
		return pendingList;
	}

	private List<Company> getOptimalCompanyList() {
		
		Collections.sort(companies, new Comparator<Company>() {
	        @Override
	        public int compare(Company company2, Company company1)
	        {        
	        	float company1Priority = company1.getSlaPercentage() - company1.getCurrentSlaPercentage();
	        	float company2Priority = company2.getSlaPercentage() - company2.getCurrentSlaPercentage();
	        	float diff = (company2Priority - company1Priority);	        	
	        	if(diff == 0.0f) return 0;
	        	else if(diff < 0.0f) return 1;
	        	else return -1;
	        }
	    });
		return companies;
	}

	private List<Identification> computeOptimalListForCompany(int companyId) throws CompanyNotFoundWithIdException {		
		List<Identification> optimalList =  getIdentificationsWithCompanyId(companyId);
		
		Collections.sort(optimalList, new Comparator<Identification>() {
	        @Override
	        public int compare(Identification identification2, Identification identification1)
	        {	        
	            return  (int) (identification1.getWaitingTime() - identification2.getWaitingTime());
	        }
	    });
		return optimalList;
	}
	private List<Identification> getIdentificationsWithCompanyId(int companyId) throws CompanyNotFoundWithIdException {
		if(getCompanyById(companyId) == null ) throw new CompanyNotFoundWithIdException(companyId);
		List<Identification> identificationsOfCompany = new ArrayList<Identification>();
		for (Identification identification : identifications) {
			if (identification.getCompany().getId() == companyId) {
				identificationsOfCompany.add(identification);
			}
		}
		return identificationsOfCompany;
	}

	public List<Company> getCompanies() {		
		return companies;
	}

	public List<Identification> getIdentifications() {		
		return identifications;
	}

	//TODO: This function is only exposed for the development time. Need to manage this with proper configuration.  
	public void resetRepository() {		
		Logger.info("resetRepository");			
		identifications = new ArrayList<Identification>();
		companies = new ArrayList<Company>();		
	}

}