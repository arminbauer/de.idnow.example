package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.dto.CompanyDTO;
import service.ifaces.ICompanyService;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by sreenath on 15.07.16.
 */
public class CompanyController extends Controller {

    @Inject
    ICompanyService companyService;

    /**
     * Controller request for adding a company.
     * This function sets the Id and passes the object to the service layer for persistence
     * It also does some basic validation
     *
     * @return
     */
    public Result addCompany() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();
        CompanyDTO companyDTO = Json.fromJson(json, CompanyDTO.class);
        // Validate the Data
        Form<CompanyDTO> form = Form.form(CompanyDTO.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest("Invalid JSON");
        }
        // Persist the data
        companyDTO.setId(UUID.randomUUID().toString());
        companyService.create(companyDTO);
        return ok(Json.toJson(companyDTO));
    }

    /**
     * Returns the complete list of companies
     *
     * @return
     */
    public Result getAllCompanies() {
        Collection<Object> companyDTOs = companyService.getAll();
        return ok(Json.toJson(companyDTOs));
    }
}
