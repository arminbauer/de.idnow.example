package data;

import model.Company;
import model.Identification;
import play.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sleski on 30.06.2015.
 */
public class AppData {

    private static AppData instance = null;

    private List<Company> companies;
    private List<Identification> identifications;

    /**
     * To use inside.
     */
    private AppData() {
        companies = new ArrayList<>();
        identifications = new ArrayList<>();
    }

    /**
     * Static method returns AppSingleton.
     *
     * @return
     */
    public static AppData getInstance() {
        if (instance == null) {
            Logger.info("Create instance of AppData.");
            instance = new AppData();
        }
        return instance;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public List<Identification> getIdentifications() {
        return identifications;
    }

    public boolean isCompanyExist(Company newCompany) {
        return companies.contains(newCompany);
    }

    public boolean isIdentificationExist(Identification newIdentification) {
        return identifications.contains(newIdentification);
    }

    public Company findCompanyById(long companyId) {
        List<Company> filteredCompanies = companies.stream().filter((company) -> company.getId() == companyId).limit(2)
                .collect(Collectors.toList());
        if (filteredCompanies.size() > 0) {
            return filteredCompanies.get(0);
        }
        return null;
    }

    public List<Identification> makeIdentificationsOrder(){
        Comparator<Identification> byWaitingTime = Comparator.comparing(identification -> identification.getWaiting_time());
        Comparator<Identification> byCurrentSLAPercentage = Comparator
                .comparing(identification -> identification.getCompany().getCurrent_sla_percentage());
        Comparator<Identification> bySLADifference = Comparator.comparing(
                identification -> (identification.getCompany().getSla_time() - identification.getCompany().getSla_time()));
        return identifications.stream().sorted(byWaitingTime.reversed().thenComparing(byCurrentSLAPercentage)
                .thenComparing(bySLATime.reversed())).collect(Collectors.toList());
    }
    /**
     * Use only in test!
     */
    public static void destroySingleton() {
        instance = null;
    }
}
