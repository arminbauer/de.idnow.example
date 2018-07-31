package controllers;

import action.CompanyAction;
import action.IdentificationAction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import comparator.IdentificationComparator;
import java.util.List;
import model.Company;
import model.Identification;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class RestController extends Controller {

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("The identification details is empty.");
        }

    	//Do something with the identification
        IdentificationAction identificationAction = new IdentificationAction();
        Identification identification = identificationAction.addIdentification(Json.fromJson(json, Identification.class));
        if(null == identification){
            return badRequest("The identification details are already exists.");
        }
        Logger.info("Identification "+identification.getId()+" is added successfully.");
        return ok();
    }


    /***
     *  This method helps to add a new Company
     *
     * @return
     */
    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	if(json == null){
    	    return badRequest("The company details is empty.");
        }
        //Do something with the company
        CompanyAction companyAction = new CompanyAction();
    	Company company = companyAction.createCompany(Json.fromJson(json,Company.class));
    	if(null == company){
    	    return badRequest("The company details are already exists.");
        }
        Logger.info("Company "+ company.getName() +" is created successfully.");
        return ok();
    }

    public Result pendingIdentifications() {
    	JsonNode identifications = Json.newArray();

        //Get the current identification
    	IdentificationAction identificationAction = new IdentificationAction();
    	List<Identification> identificationList = identificationAction.pendingIdentifications();

    	//Compute correct order
        identificationList.sort(new IdentificationComparator());
    	//Create new identification JSON with JsonNode identification = Json.newObject();
        //Add identification to identifications list
        for (Identification identification : identificationList)
        {
            ((ArrayNode) identifications).add(Json.toJson(identification));
        }
        return ok(identifications);
    }

}
