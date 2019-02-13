package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.Company;
import model.Identification;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.DBService;

import java.io.IOException;
import java.util.List;

public class RestController extends Controller {

    public Result startIdentification() {
        JsonNode json = request().body().asJson();
        Identification identi = Json.fromJson(json, Identification.class);

        //Add identification object to the current list of open identifications
        DBService db = DBService.getInstance();
        db.addToIdentDB(identi);
        Logger.debug("Added Identification. New Size of Identification DB: " + db.getIdentDB().size());

        return ok();
    }

    public Result addCompany() {
        JsonNode json = request().body().asJson();

        Company company = null;
        try {
            company = Json.fromJson(json, Company.class);
        } catch (Exception e) {
            Logger.debug("Adding Company but encountered a problem");
            e.printStackTrace();
        }

        //Add company object to the current list of companies
        DBService db = DBService.getInstance();
        db.addToCompanyDB(company);

        Logger.debug("Added Company. New Size of Company DB: " + db.getIdentDB().size());

        return ok();
    }

    public Result identifications() {
        JsonNode identifications = Json.newArray();

        //Add identification to identifications list
        DBService db = DBService.getInstance();

        List<Identification> current = db.getIdentDB();

        String json = new Gson().toJson(current);

        ObjectMapper mapper = new ObjectMapper();
        try {
            identifications = mapper.readTree(json);
        } catch (IOException e) {
            Logger.debug("Error while GET fetching identifications: ");
            e.printStackTrace();
        }
        return ok(identifications);
    }

    /*
     * Here you can get a list of identifications.
     * The identifications should be ordered in the optimal order regarding
     * the SLA of the company, the waiting time of the ident and the current
     * SLA percentage of that company. More urgent idents come first.
        */
    public Result pendingIdentifications() {
        JsonNode identifications = Json.newArray();

        DBService db = DBService.getInstance();
        List<Identification> current = db.getSortedIdentifications();

        String json = new Gson().toJson(current);

        ObjectMapper mapper = new ObjectMapper();
        try {
            identifications = mapper.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok(identifications);
    }
}
