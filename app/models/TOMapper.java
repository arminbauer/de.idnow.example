package models;

/**
 * Created by Wolfgang Ostermeier on 09.10.2017.
 *
 * Class that maps TO-Objects(parsed from Json) into Entity-Objects
 */
public class TOMapper {

    public static Identification map(IdentificationTO identificationTO){

        Identification identification = new Identification();
        identification.setId(identificationTO.getId());
        identification.setName(identificationTO.getName());
        identification.setTime(identificationTO.getTime());
        identification.setWaitingTime(identificationTO.getWaitingTime());

        Company company = Company.find.byId(identificationTO.getCompanyid());

        identification.setCompany(company);

        return identification;
    }

    public static Company map(CompanyTO companyTO){

        Company company = new Company();
        company.setId(companyTO.getId());
        company.setName(companyTO.getName());
        company.setCurrentSlaPercentage(companyTO.getCurrentSlaPercentage());
        company.setSlaPercentage(companyTO.getSlaPercentage());
        company.setSlaTime(companyTO.getSlaTime());

        return company;
    }
}
