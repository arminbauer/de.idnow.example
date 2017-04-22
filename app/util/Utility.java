package util;

import models.Company;
import models.Identification;

/**
 * Created by Catalin on 3/27/2017.
 */
public final class Utility {

    private Utility(){

    }

    /**
     * This utility method should not exists, but since I have some issues with setting up foreign keys (using @ManyToOne) I decided to create thi method.
     * @param identifications
     * @param companies
     */
    public static void setCompaniesForeignKeys(List<Identification> identifications, List<Company> companies) {
        for(Identification iden:identifications){
            iden.setCompany(getCompany(companies, iden.getCompanyId()));
        }
    }

    /**
     * Returns the company by id.
     *
     * @param companies
     * @param companyId
     * @return
     */
    public static Company getCompany(List<Company> companies, String companyId){
        for(Company company:companies){
            if(companyId.equalsIgnoreCase(company.getId())){
                return company;
            }
        }
        return null;
    }
}
