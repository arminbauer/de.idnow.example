package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.*;

import models.*;
import java.util.*;

public class RestController extends Controller {

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the identification
    	
        // read the JsonNode as an Identification
        Identification identification = Json.fromJson(json, Identification.class);
        Models.identificationList.add(identification);

        return ok(new Boolean(identification == null).toString());
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the company
    	
        // read the JsonNode as a Company
        Company company = Json.fromJson(json, Company.class);
        Models.companyList.put(company.getId(),company);

        return ok();
    }

    public Result identifications() {
    	JsonNode identifications = Json.newArray();
    	
    	//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list 
    	
        Collections.sort(Models.identificationList);
 
      // return ok(identifications);
      return ok(Json.toJson(Models.identificationList));
    }

}
