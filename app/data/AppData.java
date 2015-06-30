package data;

import model.Company;
import model.Identification;
import play.Logger;

import java.util.*;

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

    public void makeIdentificationsOrder(){
        /*
        Comparator<Book> descPriceComp = (Book b1, Book b2) -> (int) (b2.getPrice() - b1.getPrice());

        Collections.sort(listBooks, descPriceComp);
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
