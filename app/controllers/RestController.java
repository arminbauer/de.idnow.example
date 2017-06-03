package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import identification.Company;
import identification.Identification;
import identification.IdentificationPrioritizer;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class RestController extends Controller {

    // normally this should be handled by IoC container, like Spring.
    private static IdentificationPrioritizer identificationPrioritizer = new IdentificationPrioritizer();
    private IdentificationJsonMapper identificationJsonMapper = new IdentificationJsonMapper();
    private CompanyJsonMapper companyJsonMapper = new CompanyJsonMapper();

    public Result startIdentification() {
    	JsonNode json = request().body().asJson();
        Identification identification = identificationJsonMapper.fromJson(json);
        identificationPrioritizer.add(identification);
    	
        return ok();
    }

    public Result addCompany() {
    	JsonNode json = request().body().asJson();
    	Company company = companyJsonMapper.fromJson(json);
    	identificationPrioritizer.add(company);
    	
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
