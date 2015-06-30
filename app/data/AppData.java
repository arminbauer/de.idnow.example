package data;

import model.Company;
import model.Identification;
import play.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by sleski on 30.06.2015.
 */
public class AppData {

    private static AppData instance = null;

    private Set<Company> companies;
    private Set<Identification> identifications;

    /**
     * To use inside.
     */
    private AppData() {
        companies = new HashSet<>();
        identifications = new HashSet<>();
    }

    /**
     * Static method returns AppSingleton.
     *
     * @return
     */
    public static AppData getInstance() {
        if (instance == null) {
            Logger.info("Create instance of AppSingleton.");
            instance = new AppData();
        }
        return instance;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Set<Identification> getIdentifications() {
        return identifications;
    }

    public boolean isCompanyExist(Company newCompany) {
        return companies.contains(newCompany);
    }

    public boolean isIdentificationExist(Identification newIdentification) {
        return identifications.contains(newIdentification);
    }
    /**
     * Use only in test!
     */
    public static void destroySingleton() {
        instance = null;
    }
}
