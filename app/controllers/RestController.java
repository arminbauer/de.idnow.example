package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import engine.IdentificationSystem;
import entities.Company;
import entities.Identification;
import play.Logger;
import play.libs.Json;
import play.mvc.*;

import java.util.List;

public class RestController extends Controller {

    @Inject
    private IdentificationSystem identificationSystem;

    public Result startIdentification() throws JsonProcessingException {
    	JsonNode json = request().body().asJson();
        ObjectMapper objectMapper = new ObjectMapper();

        Identification identification = objectMapper.treeToValue(json, Identification.class);
        Logger.info("Starting identification of user {}", identification.getName());
        identificationSystem.queueIdentificaton(identification);
        return ok();
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();

        Company company = Json.fromJson(json, Company.class);
        identificationSystem.registerCompany(company);
        return ok();
    }

    public Result identifications() {
      	//Get the current identification
        List<Identification> identifications = identificationSystem.getPendingIdentifications();
        JsonNode identificationsJson = Json.toJson(identifications);
        return ok(identificationsJson);
    }

}
