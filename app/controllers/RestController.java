package controllers;


import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;


import database.Repository;
import model.Company;
import model.Identification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.identifications.*;

public class RestController extends Controller {

    @Inject
    private Repository repo;

    private static Logger logger = LoggerFactory.getLogger(Repository.class);

    /**
     * @return List of all companies to render on UI using view
     */

    public Result indexCompanies() {
        List<Company> companies = repo.getAllCompanies();
        return ok(views.html.identifications.companies.render(companies));
    }

    /**
     * @return Form to create company to render on UI using view
     */

    public Result createCompany() {
        Form<Company> companyForm = Form.form(Company.class);
        return ok(createComp.render(companyForm));
    }

    /**
     * Save company in the data store
     */

    public Result saveCompany() {
        Form<Company> companyForm = Form.form(Company.class).bindFromRequest();
        if (companyForm.hasErrors()) {
            flash("danger", "Please Correct the Form Below");
            return badRequest(createComp.render(companyForm));
        }
        Company company = companyForm.get();
        if (repo.addCompany(company)) {
            flash("success", "Created Successfully");
        } else {
            flash("danger", "Please Correct the Form Below");
            return badRequest(createComp.render(companyForm));
        }

        return redirect(routes.RestController.indexCompanies());
    }

    public Result indexIdentifications() {
        List<Identification> identifications = repo.getPendingIdentifications();
        return ok(views.html.identifications.identificationsIndex.render(identifications));
    }

    // to create Identifications

    public Result createIdentification() {
        Form<Identification> identificationForm = Form.form(Identification.class);
        return ok(createIdent.render(identificationForm));
    }

    // to save Identifications

    public Result saveIdentifications() {
        Form<Identification> identForm = Form.form(Identification.class).bindFromRequest();
        if (identForm.hasErrors()) {
            flash("danger", "Please Correct the Form Below");
            return badRequest(createIdent.render(identForm));
        }
        Identification identification = identForm.get();
        if (repo.addIdentification(identification))
            flash("success", "Created Successfully");
        else {
            flash("danger", "Please Correct the data in the Form Below");
            return badRequest(createIdent.render(identForm));
        }

        return redirect(routes.RestController.indexIdentifications());
    }



    public Result startIdentification() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();
        try {
            //Add the identification
            Identification identification = Json.fromJson(json, Identification.class);
            if (repo.addIdentification(identification))
                return ok();
        } catch (Exception e) {
            logger.error("Unknown exception");
            return badRequest();
        }
        return badRequest();
    }

    public Result addCompany() {

        //Get the parsed JSON data
        JsonNode json = request().body().asJson();
        try {
            //Add the company
            Company company = Json.fromJson(json, Company.class);
            if (repo.addCompany(company))
                return ok();
        } catch (Exception e) {
            logger.error("Unknown exception");
            return badRequest();
        }

        return badRequest();

    }
    /**
     * @return List of all companies sorted based on the priority
     */
    public Result identifications() {
        JsonNode identifications = Json.newArray();

        //get sorted Identifications
        List<Identification> pendingIdentifications = repo.getPendingIdentifications();

        for (Identification identification : pendingIdentifications) {
            ((ArrayNode) identifications).add(Json.toJson(identification));
        }

        return ok(identifications);
    }


}
