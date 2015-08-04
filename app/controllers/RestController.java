package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.*;
import util.IdentificationsComperator;

import java.util.*;


public class RestController extends Controller {

    private HashMap<Integer, JsonNode> identificationHashMap = new LinkedHashMap<>();
    private HashMap<Integer, JsonNode> companyHashMap = new LinkedHashMap<>();

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();

        //Do something with the identification
        identificationHashMap.put(json.get("id").asInt(), json);

        return ok();
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the company
        companyHashMap.put(json.get("id").asInt(), json);

        return ok();
    }

    public Result pendingIdentifications() {
    	//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list
        List<JsonNode> identificationList = new ArrayList<>(identificationHashMap.values());
        Collections.sort(identificationList, new IdentificationsComperator(companyHashMap));

        JsonNode identifications = Json.newArray().addAll(identificationList);
    	
        return ok(identifications);
    }

}
