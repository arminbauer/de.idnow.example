package service;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.Company;
import model.Identification;
import play.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DBService {

    private static DBService single_instance = null;

    @JsonProperty("Identification")
    private List<Identification> identDb;
    private List<Company> companyDb;

    private DBService() {
        this.identDb = new ArrayList<Identification>();
        this.companyDb = new ArrayList<Company>();
    }

    public static DBService getInstance() {
        if (single_instance == null)
            single_instance = new DBService();

        return single_instance;
    }

    public void addToIdentDB(Identification addThis) {
        identDb.add(addThis);
    }

    public List<Identification> getIdentDB() {
        return identDb;
    }

    public void addToCompanyDB(Company addThis) {
        companyDb.add(addThis);
    }

    public List<Company> getCompanyDB() {
        return companyDb;
    }

    public void printCompanyDB() {
        for (Company c : companyDb) {
            System.out.println(c.toString());
        }
    }

    public void printIdentificationDB() {
        for (Identification i : identDb) {
            System.out.println(i.toString());
        }
    }

    public Company getCompanyByID(String id) {
        for (Company c : companyDb) {
            if (c.getId().equals(id))
                return c;
        }
        Logger.debug("Cannot find company with id: " + id + " , the id indicated by identifier");
        return null;
    }

    /* Here you can get a list of identifications.
    /* The identifications should be ordered in the optimal order regarding
    /* the SLA of the company, the waiting time of the ident and the current
    /* SLA percentage of that company. More urgent idents come first. */
    public List<Identification> getSortedIdentifications() {
        List<Identification> sortedIdentDb;
        Collections.sort(identDb);
        Collections.reverse(identDb);
        return identDb;
    }

    public void eraseDB() {
        identDb.clear();
        companyDb.clear();
    }
}