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
            Logger.info("Create instance of AppSingleton.");
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
                .collect(
                        Collectors.toList());
        if (filteredCompanies.size() > 0) {
            return filteredCompanies.get(0);
        }
        return null;
    }

    public void makeIdentificationsOrder(){
        /*
        Comparator<Book> descPriceComp = (Book b1, Book b2) -> (int) (b2.getPrice() - b1.getPrice());

        Collections.sort(listBooks, descPriceComp);
        User match = users.stream().filter((user) -> user.getId() == uid).findAny().get();
         */
        Comparator<Identification> comparator = (Identification i1,Identification i2) -> (int)(i2.getWaiting_time() - i1.getWaiting_time());
        Collections.sort(new ArrayList<>(identifications),comparator);

    }
    /**
     * Use only in test!
     */
    public static void destroySingleton() {
        instance = null;
    }
}
