package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.*;
import service.IndentificationServiceI;
import service.IndetificationServiceImpl;

public class RestController extends Controller {

    public Result startIdentification() {
    	
    	  
    	IndentificationServiceI identifcationService = new IndetificationServiceImpl();
    	 identifcationService.getOptimalOrder(request().body().asJson());
    	
    	
        return ok(request().body().asJson()).as("application/json");
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
