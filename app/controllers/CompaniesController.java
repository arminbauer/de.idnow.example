package controllers;


import dao.CompanyStore;
import models.Company;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

import static controllers.Errors.error;
import static controllers.Errors.validationError;

public class CompaniesController extends Controller {

    private final CompanyStore companyStore;

    @Inject
    CompaniesController(CompanyStore companyStore) {
        this.companyStore = companyStore;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result createCompany() {
        ValidJson<Company> jsonObject = new ValidJson<>(request().body().asJson(), Company.class);
        if (jsonObject.hasErrors()) {
            return badRequest(validationError(jsonObject.getErrors()));
        }

        Company company = jsonObject.get();
        if (company.getId() != null) {
            return badRequest((error("This endpoint only for creation!")));
        }
        return created(Json.toJson(companyStore.upsert(company)));
    }

    public Result getAllCompanies() {
        return ok(Json.toJson(companyStore.findAll()));
    }
}
