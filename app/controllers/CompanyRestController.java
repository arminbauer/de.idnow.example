package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Company;
import play.mvc.Controller;
import play.mvc.Result;
import service.CompanyService;

import javax.inject.Inject;

import static util.json.JsonHelper.parseJson;

public class CompanyRestController extends Controller {

    @Inject
    private CompanyService companyService;

    public Result addCompany() {
        // Get the parsed JSON data
        JsonNode json = request().body().asJson();
        // Parse JSON data to Company instance
        Company company = parseJson(json, Company.class);
        // Save company
        companyService.save(company);
        // Return ok result
        return ok();
    }
}
