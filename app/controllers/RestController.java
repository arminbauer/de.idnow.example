package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import converter.CompanyConverter;
import data.AppData;
import model.Company;
import model.Identification;
import play.libs.Json;
import play.mvc.*;

public class RestController extends Controller {

    private AppData appData = AppData.getInstance();

    public Result startIdentification() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();
        Identification identification = Json.fromJson(json, Identification.class);
        //Do something with the identification
        if (appData.isIdentificationExist(identification)) {
            return status(409);
        }
        Company company = appData.findCompanyById(identification.getCompanyid());
        if (company == null) {
            return status(412);
        }
        appData.getIdentifications().add(identification);
        return ok();
    }

    public Result addCompany() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();
        //Do something with the company
        Company newCompany = CompanyConverter.jsonConvert(json);
        if (appData.isCompanyExist(newCompany)) {
            return status(409);
        }
        appData.getCompanies().add(newCompany);
        return ok();
    }

    public Result identifications() {
        JsonNode identifications = Json.newArray();

        //Get the current identification
        //Compute correct order
        //Create new identification JSON with JsonNode identification = Json.newObject();
        //Add identification to identifications list

        return ok(identifications);
    }

}
