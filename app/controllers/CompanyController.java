package controllers;

import api.Company;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.CompanyService;
import service.CompanyValidator;

import javax.inject.Inject;
import java.util.List;

/**
 * Company REST controller
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class CompanyController extends Controller {
    private CompanyService companyService;
    private CompanyValidator companyValidator;

    @Inject
    public CompanyController(CompanyService companyService, CompanyValidator companyValidator) {
        this.companyService = companyService;
        this.companyValidator = companyValidator;
    }

    public Result addCompany() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Company json isn't presented");
        }
        try {
            Company company = Json.fromJson(json, Company.class);
            List<String> validationErrors = companyValidator.validate(company);
            if (validationErrors.size() > 0) {
                return badRequest(String.join(" ", validationErrors));
            }

            if (companyService.addNewCompany(company)) {
                return ok("Company was successfully added");
            }
            return badRequest("Company already exists");

        } catch (Exception ex) {
            return badRequest("Incorrect json was provided. " + ex.getMessage());
        }
    }

}
