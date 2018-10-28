package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.inject.Inject;
import exception.NoCompanyAssociatedException;
import model.Company;
import model.Identification;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.IdentificationProcessor;

public class RestController extends Controller {

    private ObjectMapper mapper = new ObjectMapper();

    private final IdentificationProcessor identificationProcessor;

    @Inject
    public RestController(IdentificationProcessor identificationProcessor) {
        this.identificationProcessor = identificationProcessor;
    }

    public Result startIdentification() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();
        try {

            //Do something with the identification
            Identification identification = mapper.treeToValue(json, Identification.class);
            identificationProcessor.addNewIdentification(identification);
            return ok();
        } catch (JsonProcessingException e) {
            Logger.error("Couldn't parse json to identification " + json, e);
            return badRequest("Invalid identification format");
        } catch (NoCompanyAssociatedException e) {
            Logger.error("No company associated with the identification " + e.getCompanyId(), e);
            return badRequest("No company associated with the identification, can't handle request");
        } catch (Exception e) {
            Logger.error("Unknown error happened during adding identifications", e);
            return internalServerError("Internal error happened");
        }
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the company
        try {
            Company company = mapper.treeToValue(json, Company.class);
            identificationProcessor.updateCompanyInformation(company);
            return ok();
        } catch (JsonProcessingException e) {
            Logger.error("Couldn't parse json to company " + json, e);
            return badRequest("Invalid company format");
        } catch (IllegalArgumentException e) {
            Logger.error("Can't process company ", e);
            return badRequest("Invalid company object");
        } catch (Exception e) {
            Logger.error("Unknown error happened during adding companies", e);
            return internalServerError("Internal error happened");
        }
    }

    public Result identifications() {
    	ArrayNode identifications = Json.newArray();
    	
    	//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list
        try {
    	    identificationProcessor.getOrderedPendingIdentifications().stream()
                .map(identification -> mapper.convertValue(identification, JsonNode.class))
                .forEach(identifications::add);
        } catch (Exception e) {
            Logger.error("Unknown error happened during getting pending identifications", e);
            return internalServerError("Internal error happened");
        }
        return ok(identifications);
    }

}
