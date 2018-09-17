package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import models.*;
import play.libs.Json;
import play.mvc.*;
import service.IndentificationServiceI;
import service.IndetificationServiceImpl;

public class RestController extends Controller {

    public Result startIdentification() {
    	
    	  
    	IndentificationServiceI identifcationService = new IndetificationServiceImpl();
    	List<Identification> idList =  identifcationService.getOptimalOrder(request().body().asJson());
    	//here only the sorted Identiy objects are returned.
    	JsonNode personJson = Json.toJson(idList); 
        return ok(personJson).as("application/json");
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the company
    	
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
