package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.IdnowHelper;
import models.Company;
import models.Identification;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import views.html.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The type Rest controller.
 */
public class RestController extends Controller {


    /**
     * To add identification to the current list of open identifications.
     *
     * @return returns JSON data or badRequest
     */
    public Result startIdentification() {
    	JsonNode json = request().body().asJson();
    	if (Objects.isNull(json)) {
    	    return badRequest("Please send identification to start");
        }
        Identification fromJson = Json.fromJson(json, Identification.class);
        Optional<Identification> identificationById = IdnowHelper.getInstance().getIdentificationById(fromJson.getId());
        if (identificationById.isPresent()) {
            return badRequest("Identification is not unique!");
        }
        Identification identification = IdnowHelper.getInstance().createIdentification(Json.fromJson(json, Identification.class));
        Optional<Company> company = IdnowHelper.getInstance().getCompanyById(identification.getCompanyId());
        if (company.isPresent()) {
            identification.setCompany(company.get());
            List<Identification> allIdentifications = IdnowHelper.getInstance().getAllIdentifications();
            JsonNode toJson = Json.toJson(allIdentifications);
            return ok(toJson);
        } else {
            return badRequest("Company not found");
        }

    }

    /**
     * Adds Company to the company list
     *
     * @return the created Company
     */
    public Result addCompany() {
    	JsonNode json = request().body().asJson();

        Company fromJson = Json.fromJson(json, Company.class);
        Optional<Company> company = IdnowHelper.getInstance().createCompany(fromJson);
        if (company.isPresent()) {
            return badRequest("Company already exists");
        } else {
            return ok(Json.toJson(company));
        }
    }

    /**
     * Retrieves all Identifications in the optimal order
     *
     * @return the list of Identifications
     */
    public Result identifications() {
        List<Company> companies = IdnowHelper.getInstance().sortCompanies();
        List<List<Identification>> pendingIdentifications = IdnowHelper.getInstance().getPendingIdentifications(companies);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.convertValue(pendingIdentifications, JsonNode.class);
        return ok(json);
    }

    /**
     * Retrieves all the companies
     *
     * @return the list of companies
     */
    public Result companies() {
        List<Company> allCompanies = IdnowHelper.getInstance().getAllCompanies();
        return ok(Json.toJson(allCompanies));
    }

    /**
     * Get company form result.
     *
     * @return the result
     */
    public Result getCompanyForm(){
        Form<Company> companyForm = Form.form(Company.class);
        return ok(views.html.addcompany.render(companyForm));

    }

    /**
     * Add company form result.
     *
     * @return the result
     */
    public Result addCompanyForm(){
        Form<Company> companyForm = Form.form(Company.class).bindFromRequest();
        Company company = companyForm.get();
        Optional<Company> getCompany = IdnowHelper.getInstance().createCompany(company);
        if (!getCompany.isPresent()) {
            IdnowHelper.getInstance().createCompany(company);
            return ok(index.render("Company Successfully Added"));
        }

        return badRequest("Company Id not unique");

    }

    /**
     * Gets identification form.
     *
     * @return the identification form
     */
    public Result getIdentificationForm() {
        Form<Identification> identificationForm = Form.form(Identification.class);
        return ok(views.html.addidentification.render(identificationForm, getAllIdentifications()));
    }

    /**
     * Add identification form result.
     *
     * @return the result
     */
    public Result addIdentificationForm() {
        Form<Identification> identificationForm = Form.form(Identification.class).bindFromRequest();
        Identification identification = identificationForm.get();

        Optional<Identification> getIdent = IdnowHelper.getInstance().getIdentificationById(identification.getId());
        Optional<Company> getCompany = IdnowHelper.getInstance().getCompanyById(identification.getCompanyId());
        if (!getIdent.isPresent() && getCompany.isPresent()) {
            getCompany.ifPresent(identification::setCompany);
            IdnowHelper.getInstance().createIdentification(identification);
            return ok(index.render("Identification Successfully Added"));
        }
        return badRequest("Company and/or Identifications not unique");
    }

    private static List<Identification> getAllIdentifications() {
        return new ArrayList<>(IdnowHelper.getInstance().getAllIdentifications());
    }

    /**
     * Gets pending identification.
     *
     * @return the pending identification
     */
    public Result getPendingIdentification() {
        List<Company> companies = IdnowHelper.getInstance().sortCompanies();
        List<List<Identification>> identifications = IdnowHelper.getInstance().getPendingIdentifications(companies);
        return ok(views.html.identifications.render(identifications));
    }
}
