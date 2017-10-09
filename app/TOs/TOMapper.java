package TOs;

import models.Company;
import models.Identification;

public class TOMapper {

    public static Identification map(IdentificationTO identificationTO){

        Identification identification = new Identification();
        identification.setId(identificationTO.getId());
        identification.setName(identificationTO.getName());
        identification.setTime(identificationTO.getTime());
        identification.setWaitingTime(identificationTO.getWaiting_time());

        Company company = Company.find.byId(identificationTO.getCompanyid());

        identification.setCompany(company);

        return identification;
    }

    public static Company map(CompanyTO companyTO){

        Company company = new Company();
        company.setId(companyTO.getId());
        company.setName(companyTO.getName());
        company.setCurrentSlaPercentage(companyTO.getCurrent_sla_percentage());
        company.setSlaPercentage(companyTO.getSla_percentage());
        company.setSlaTime(companyTO.getSla_time());

        return company;
    }
}
