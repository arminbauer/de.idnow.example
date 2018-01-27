package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.*;
import models.*;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Map;
public class RestController extends Controller {
	private Set<Identification> identifications = Collections
			.synchronizedSet(new TreeSet<Identification>());
	private Map<Integer,Company> mapCompany = Collections
			.synchronizedMap(new HashMap<Integer,Company>());
	public Result startIdentification() {
		// Get the parsed JSON data
		JsonNode json = request().body().asJson();
		if(json==null){
			return badRequest("Expecting Json data");
		}
		Identification identification = Json.fromJson(json, Identification.class);
		Company company = mapCompany.get(identification.getCompanyId());
		if(company==null){
			return badRequest("Company not exist:"+identification.getCompanyId());
		}
		identification.setCompany(company);
		identifications.add(identification); 
		return ok();
	}

	public Result addCompany() {
		JsonNode json = request().body().asJson();
		if(json==null){
			return badRequest("Expecting Json data");
		}
		Company company = Json.fromJson(json, Company.class);
		mapCompany.put(company.getId(),company);
		return ok();
	}

	public Result identifications() {
		JsonNode identifications = Json.newArray();
		
		// Get the current identification
		// Compute correct order
		// Create new identification JSON with JsonNode identification =
		// Json.newObject();
		// Add identification to identifications list

		return ok();
	}
	
	public Result pendingIdentifications() {
		JsonNode idenNodes = Json.newArray();
		if(!identifications.isEmpty()){
			idenNodes = Json.toJson(identifications);
		}
		return ok(idenNodes);
	}

}
