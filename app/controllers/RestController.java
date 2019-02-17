package controllers;

import api.Company;
import com.fasterxml.jackson.databind.JsonNode;

import models.CompanyEntity;
import play.libs.Json;
import play.mvc.*;
import repository.CompanyRepository;

import javax.inject.Inject;

public class RestController extends Controller {

    private final CompanyRepository companyRepository;

    @Inject
    public RestController(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the identification
    	
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
