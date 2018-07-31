package action;

import java.util.HashMap;
import java.util.Map;
import model.Company;
import play.Logger;

public class CompanyAction
{
    private static Map<Integer, Company> companies = new HashMap<>();

    public Company createCompany(Company company){
        // Check whether the company is already created
        if(!companies.containsKey(company.getId())){
            companies.put(company.getId(),company);
            return company;
        } else {
            Logger.warn("Company is already exist.");
        }
        return null;
    }

    public Company getCompanybyId(Integer companyId){
        if(!companies.isEmpty() && companies.size() > 0){
            return companies.get(companyId);
        }
        return null;
    }

    public void clearCompanies(){
        companies.clear();
    }
}
