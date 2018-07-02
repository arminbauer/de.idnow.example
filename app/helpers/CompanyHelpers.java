package helpers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import models.Company;


public class CompanyHelpers{
	
	private static CompanyHelpers companyObject;
	private Map<Integer, Company> companies = new HashMap<>();
	
	//Instantiates company object
	public static CompanyHelpers getObject() {
		if (companyObject == null) {
			companyObject = new CompanyHelpers();
		}
		return companyObject;
	}
	
	//Creates company
	public Company createCompany(Company company) {
		if (!companies.containsKey(company.getId())) {
			companies.put(company.getId(), company);
			System.out.println(companies.size());
			return company;
		} else {
			return null;
		}
	}
	
	//Helper method to get all compies
	public Map<Integer, Company> getAllComapnies() {
		return new HashMap<Integer, Company>(companies);
	}
	
	//Helper method to get company by id
	public Company getCompanyById(Integer companyid) {
		return companies.get(companyid);
	}
	
	
	//Helper method to sort companies based on SLA_Percentage and Current_SLA_percentage
	public Company[] getSortedCompanies(Map<Integer, Company> companies) {
		Company[] companyList = new Company[companies.size()];
    	int i = 0;
     	for (Entry<Integer, Company> entry : companies.entrySet()) {
    		companyList[i] = entry.getValue();
    		i++;
    	}
     	if(companyList.length > 1) {
     		Arrays.sort(companyList);
     	}
     	return companyList;
	}
}
