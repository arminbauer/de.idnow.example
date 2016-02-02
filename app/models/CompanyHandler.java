package models;

/**
 * Created by sebastian.walter on 02.02.2016.
 */
public class CompanyHandler {

    public Company map(AddCompanyRequest addCompanyRequest) {
        Company company = new Company();
        company.setName(addCompanyRequest.getName());
        company.setSlaTime(addCompanyRequest.getSlaTime());
        company.setSlaPercentage(addCompanyRequest.getSlaPercentage());
        company.setCurrentSlaPercentage(addCompanyRequest.getCurrentPercentage());
        return company;
    }
}
