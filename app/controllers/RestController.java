package controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import models.dto.Company;
import models.dto.Identification;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class RestController extends Controller {

    
	public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	// Null check
    	if(json == null){
    		return badRequest("Null Value");
    	}
    	
    	Identification ident = (Identification) Json.fromJson(json, Identification.class);
    	ident.save();
    	
    	
        return ok();
    }
	
    public Result addCompany() throws SQLException {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	// Null check
    	if(json == null){
    		return badRequest("Null Value");
    	}
    	
		//persist the object into temproary database
    	Company company = (Company) Json.fromJson(json, Company.class);
    	company.save();
    	
    	return ok();
    }
    /*The identifications ordered in the optimal order regarding the SLA of the company,
     *  the waiting time of the ident and the current SLA percentage of that company. More urgent idents come first.
     */
    public Result identifications() {
    	
    	JsonNode identifications = Json.newArray();
    	//Get the Elements from Temp database.
    	List<Identification> identList = Identification.find.all();
    	
    	// Orders the idents as per the urgent idents
    	//overidden comparable interface in identification model
    	// urgent idents = (How much time left to comlete SLA.) - (How much task left to complete) -- calculated in identification object
    	Collections.sort(identList);
    	
    	return ok(Json.toJson(identList));
    }

}
