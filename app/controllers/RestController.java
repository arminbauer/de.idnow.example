package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import models.Company;
import models.Identification;
import play.libs.Json;
import play.mvc.*;
import repositories.GenericRepository;
import services.IdentificationService;

import javax.inject.Inject;

public class RestController extends Controller {

    @Inject
    private GenericRepository<Company> companyRepository;

    @Inject
    private IdentificationService identificationService;

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
        Identification identification = Json.fromJson(json, Identification.class);

        identificationService.save(identification, json.get("companyid").asInt());

        return created(Json.toJson(identification));
    }

    public Result addCompany() {
    	JsonNode json = request().body().asJson();
        Company company = Json.fromJson(json, Company.class);

        companyRepository.save(company);

        return created(Json.toJson(company));
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
