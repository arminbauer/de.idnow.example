package models.company.factories;

import models.company.entities.Company;
import models.company.jsons.CompanyJson;

public class CompanyFactory {
    private static CompanyFactory instance;

    public CompanyFactory() {
        if (instance!=null) {
            throw new ExceptionInInitializerError();
        }
    }

    public static CompanyFactory getInstance() {
        return instance == null ? instance = new CompanyFactory() : instance;
    }

    public Company fromCompanyJson(CompanyJson  json) {
        return json == null ? null :
                new Company(json.id, json.name, json.slaTime, json.slaPercentage, json.currentSlaPercentage);
    }
}
