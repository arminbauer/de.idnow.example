package models;

import com.avaje.ebean.Model;
import com.google.inject.Inject;
import internal.calculation.PriorityCalculator;

/**
 * Created by sebastian.walter on 02.02.2016.
 */
public class IdentificationHandler {

    @Inject
    private PriorityCalculator calculator;

    public Identification map(IdentificationRequest identificationRequest) {

        Company.Finder<Integer, Company> companyFinder = new Model.Finder<Integer, Company>(Company.class);
        Company company = companyFinder.byId(identificationRequest.getCompanyId());
        Identification identification = new Identification();

        if (null == company) {
            return null;
        }

        identification.setCompany(company);
        identification.setName(identificationRequest.getName());
        identification.setTime(identificationRequest.getTime());
        identification.setWaitingTime(identificationRequest.getWaitingTime());
        return identification;
    }

    public double calcPriority(Identification identification) {
        Company company = identification.getCompany();
        return  calculator.calculate(
                company.getSlaTime(),
                company.getSlaPercentage(),
                company.getcurrentSlaPercentage(),
                identification.getWaitingTime());
    }
}
