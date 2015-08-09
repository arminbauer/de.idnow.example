package util;

import models.Company;
import models.Identification;

import java.util.*;

/**
 * Created by maria.barbosz on 8/7/2015.
 */
public class DBEmulator {

    private static DBEmulator INSTANCE = new DBEmulator();

    private DBEmulator(){ }

    public static DBEmulator getInstance(){
        return INSTANCE;
    }

    private List<Company> companyList = new ArrayList<>();
    private List<Identification> identificationList = new ArrayList<>();
    private Map<String, List<Identification>> identificationMap = new HashMap<>();

    public void init(int i){
        switch (i) {
            case 1: init_ex1(); break;
            case 2: init_ex2(); break;
            case 3: init_ex3(); break;
            case 4: init_ex4(); break;
            default: break;
        }
    }

    public void init_ex1(){
        // clear data
        clearData();

        // add test companies
        Company company1 = new Company("id1", "Company 1", 60, 0.9, 0.95);
        addCompany(company1);

        // add test identifications
        Identification identification1 = new Identification("id11", "Identification 1", new Date(), 30, "id1");
        addIdentification(identification1);
        Identification identification2 = new Identification("id22", "Identification 2", new Date(), 45, "id2");
        addIdentification(identification2);
    }

    public void init_ex2(){
        // clear data
        clearData();

        // add test companies
        Company company1 = new Company("id1", "Company 1", 60, 0.9, 0.95);
        addCompany(company1);
        Company company2 = new Company("id2", "Company 2", 60, 0.9, 0.90);
        addCompany(company2);

        // add test identifications
        Identification identification1 = new Identification("id11", "Identification 1", new Date(), 30, "id1");
        addIdentification(identification1);
        Identification identification2 = new Identification("id22", "Identification 2", new Date(), 30, "id2");
        addIdentification(identification2);
    }

    public void init_ex3(){
        // clear data
        clearData();

        // add test companies
        Company company1 = new Company("id1", "Company 1", 60, 0.9, 0.95);
        addCompany(company1);
        Company company2 = new Company("id2", "Company 2", 120, 0.8, 0.95);
        addCompany(company2);

        // add test identifications
        Identification identification1 = new Identification("id11", "Identification 1", new Date(), 30, "id1");
        addIdentification(identification1);
        Identification identification2 = new Identification("id22", "Identification 2", new Date(), 30, "id2");
        addIdentification(identification2);
    }

    public void init_ex4(){
        // clear data
        clearData();

        // add test companies
        Company company1 = new Company("id1", "Company 1", 60, 0.9, 0.95);
        addCompany(company1);
        Company company2 = new Company("id2", "Company 2", 120, 0.8, 0.8);
        addCompany(company2);

        // add test identifications
        Identification identification1 = new Identification("id11", "John", new Date(), 45, "id1");
        addIdentification(identification1);
        Identification identification2 = new Identification("id22", "Gigi", new Date(), 30, "id2");
        addIdentification(identification2);
    }

    private void clearData(){
        companyList.clear();
        identificationList.clear();
        identificationMap.clear();
    }

    public void addCompany(Company company){
        companyList.add(company);
    }

    public void addIdentification(Identification identification){

        identificationList.add(identification);

        if (identificationMap.containsKey(identification.getCompanyId())){
            identificationMap.get(identification.getCompanyId()).add(identification);
        }
        else{
            List<Identification> list = new ArrayList<>();
            list.add(identification);
            identificationMap.put(identification.getCompanyId(), list);
        }
    }

    public boolean hasPendingIdentifications(String companyId){
        List companyIdentificationList = identificationMap.get(companyId);

        if (companyIdentificationList != null && companyIdentificationList.size() > 0){
            return true;
        }
        return false;
    }

    public void deleteIdentification(Identification identification){
        identificationList.remove(identification);
        if (identificationMap.get(identification.getCompanyId()).contains(identification)){
            identificationMap.get(identification.getCompanyId()).remove(identification);
        }
    }

    public List<Identification> getPendingIdentifications(String companyId){
        List companyIdentificationList = identificationMap.get(companyId);

        if (companyIdentificationList != null && companyIdentificationList.size() > 0){
            return companyIdentificationList;
        }
        return null;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public List<Identification> getIdentificationList() {
        return identificationList;
    }

    public Map<String, List<Identification>> getIdentificationMap() {
        return identificationMap;
    }
}
