package controllers;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;

import dto.Company;
import dto.Identification;
import play.libs.Json;
import play.libs.*;
import play.mvc.*;
import service.IdentService;

public class RestController extends Controller {

	IdentService identService = new IdentService();

	public Result startIdentification() {
		//Get the parsed JSON data
		JsonNode json = request().body().asJson();

		//Do something with the identification
		Identification identification = Json.fromJson( json , Identification.class);
		try{
			identService.addIdentification(identification);
		}catch(Exception ex){
			return internalServerError(ex.getMessage());
		}
		return ok();
	}

	public Result addCompany() {
		//Get the parsed JSON data
		JsonNode json = request().body().asJson();
		identService.addCompany(Json.fromJson( json , Company.class));
		return ok();
	}

	public Result identifications() {
		JsonNode identifications = Json.newArray();
		return ok(Json.toJson(identService.getIdentifications()));
	}

}
