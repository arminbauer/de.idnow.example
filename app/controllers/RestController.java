package controllers;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import model.Company;
import model.Identification;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class RestController extends Controller {


	private static final CopyOnWriteArrayList<Company> companies = new CopyOnWriteArrayList<>();
	private static final CopyOnWriteArrayList<Identification> idents = new CopyOnWriteArrayList<>();

		
    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	Logger.info("startIdentification recieved json object {}", Json.prettyPrint(json));
    	
    	Identification ident = Json.fromJson(json, Identification.class);
    	//Do something with the identification
    	boolean companyExists = companies.stream().anyMatch(c -> c.getId() == ident.getCompanyid());
    	if(companyExists) {
    		boolean identExists = idents.stream().anyMatch(i -> i.getId() == ident.getId());
    		if(identExists) {
    			return badRequest(getJsonMessage("error", "Identifiation with id : %d is already submitted. Thanks for your patience.", ident.getId()));
    		}
    		idents.add(ident);
    		return created(getJsonMessage("success", "Identification : %d submitted succesfully", ident.getId()));
    	} else {
    		return badRequest(getJsonMessage("error", "Company with id : %d not found. Please check your request.", ident.getCompanyid()));
    	}
    	
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	Logger.info("addCompany recieved json object {}", Json.prettyPrint(json));
    	Company company = Json.fromJson(json, Company.class);
    	//Do something with the company
    	boolean companyExists = companies.stream().anyMatch(c -> c.getId() == company.getId());
    	if(companyExists) {
    		return badRequest(getJsonMessage("error","Company with id: %d already exists", company.getId()));
    	} else {
    		companies.add(company);
    		return created(Json.toJson(company));
    	}
    }
    
    public Result clearData() {
    	Logger.info("Recieved request to clear companies and identifications.");
    	companies.clear();
    	idents.clear();
    	return ok(getJsonMessage("success", "cleared %s and %s.", "companies", "identifications"));
    }

    public Result identifications() {
    	ArrayNode identifications = Json.newArray();
    	
    	//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list 
    	
    	companies.stream()
    		.sorted(RestController::prioritizeCompanies)
    		.flatMap(RestController::getIdents)
    		.forEachOrdered(identifications::add);
    	
    	Logger.info("identifications : {}", Json.prettyPrint(identifications));
        return ok(identifications);
    }
    
    private static int prioritizeCompanies(Company c1, Company c2) {
    	Logger.info("c1 sla {} | c2 sla {}", c1.getSla_time(), c2.getSla_time());
    	if (c1.getSla_time() == c2.getSla_time()) {
    		Logger.info("SLAs are equal");
    		float c1Prio = c1.getSla_percentage() - c1.getCurrent_sla_percentage();
    		float c2Prio = c2.getSla_percentage() - c2.getCurrent_sla_percentage();
    		if(c1Prio == c2Prio) {
    			return 0;
    		} else if(c1Prio > c2Prio) {
    			return -1;
    		} else {
    			return 1;
    		}
    	} else {
    		Logger.info("SLAs are not equal");
    		if(c1.getSla_time() < c2.getSla_time()) {
    			return -1;
    		} else {
    			return 1;
    		}
    	}
    	
    }
    
    private static Stream<JsonNode> getIdents(Company c) {
    	return idents.stream()
    			.filter(i -> i.getCompanyid() == c.getId())
    			.sorted(Comparator.comparingLong(Identification::getWaiting_time).reversed())
    			.map(Json::toJson);
    }

    private static JsonNode getJsonMessage(String key, String value, Object... args) {
    	
    	return Json.newObject().put(key, String.format(value, args));
    }
    
}
