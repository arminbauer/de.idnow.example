package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Company;
import models.CompanyJSON;
import models.CompanyStore;
import models.DataStoreException;
import models.Identification;
import models.IdentificationJSON;
import models.IdentificationStore;
import models.JsonParser;
import models.JsonSerializer;
import models.ValidationErrorException;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class RestController extends Controller {

    public Result startIdentification() {
        JsonNode json = request().body().asJson();
        IdentificationJSON identificationJSON = Json.fromJson(json, IdentificationJSON.class);

        Identification identification;
        try {
            identification = JsonParser.parseIdentification(identificationJSON);
        } catch(ValidationErrorException ex) {
            return badRequest(ex.getMessage());
        }

        try {
            IdentificationStore.add(identification);
        } catch (DataStoreException ex) {
            return badRequest(ex.getMessage());
        }

        return ok("Addition of new identification successful.");
    }

    public Result startIdentificationWithForm() {
        Form<IdentificationJSON> identificationForm = Form.form(IdentificationJSON.class).bindFromRequest();
        if(identificationForm.hasErrors()) {
            flash("error", "Please correct the errors in the form below.");
            return badRequest(views.html.identification.render(identificationForm));
        }

        Identification identification;
        try {
            identification = JsonParser.parseIdentification(identificationForm.get());
        } catch(ValidationErrorException ex) {
            flash("error", ex.getMessage());
            return badRequest(views.html.identification.render(identificationForm));
        }

        try {
                IdentificationStore.add(identification);
        } catch (DataStoreException ex) {
            flash("error", ex.getMessage());
            return badRequest(views.html.identification.render(identificationForm));
        }

        flash("success", "Addition of new identification successful.");
        return ok(views.html.identification.render(identificationForm));
    }

    public Result addCompany() {
    	JsonNode json = request().body().asJson();
    	Company company;

    	try {
    	    company = JsonParser.parseCompany(json);
        } catch(ValidationErrorException ex) {
    	    return badRequest(ex.getMessage());
        }

        try {
            CompanyStore.add(company);
        } catch(DataStoreException ex) {
            return badRequest(ex.getMessage());
        }
    	
        return ok();
    }

    public Result addCompanyWithForm() {
        Form<CompanyJSON> companyForm = Form.form(CompanyJSON.class).bindFromRequest();
        if(companyForm.hasErrors()) {
            flash("error", "Please correct the errors in the form below.");
            return badRequest(views.html.company.render(companyForm));
        }

        Company company;
        try {
            company = JsonParser.parseCompany(companyForm.get());
        } catch(ValidationErrorException ex) {
            flash("error", ex.getMessage());
            return badRequest(views.html.company.render(companyForm));
        }

        try {
            CompanyStore.add(company);
        } catch (DataStoreException ex) {
            flash("error", ex.getMessage());
            return badRequest(views.html.company.render(companyForm));
        }

        flash("success", "Addition of new company successful.");
        return ok(views.html.company.render(companyForm));
    }

    public Result identifications() {
        List<Identification> identifications = IdentificationStore.getPrioritizedIdentifications();
        ArrayNode identificationsJson = JsonSerializer.serializeIdentifications(identifications);
        return ok(identificationsJson);
    }
}
